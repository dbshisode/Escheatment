CREATE OR REPLACE PROCEDURE "ESCHEATMENT"."CLOCK_STOP_EXPIRE" 
 (
    out_return_code      OUT      VARCHAR2,
    out_return_message   OUT      VARCHAR2
  )
AS
  NOTICE_OF_UNCLAIMED_FUNDS     int    := 1;
  REQUEST_FOR_ADDL_INFO         int    := 2;
  ESCHEATMENT_FOR_PUBLICATION   int    := 3;
  RESPONSE_UNDER_REVIEW         int    := 4;

  v_responseCnt        int             := 0;  
  v_clockCnt           int             := 0;
  v_caseId             number               ;
  v_feeId              number               ;
  v_ErrorMsg           VARCHAR2(100)   := '';
  v_ErrorTrace         VARCHAR2(4000)  := '';

 /******************************************************************************
  NAME:       CLOCK_STOP_EXPIRE
  PURPOSE:
  Procedure should be called once daily to ensure clocks used to track trusts
  in Escheatment process are marked stopped or expired as appropriate.
  
  REVISIONS:
  Ver        Date        Author           Description
  ---------  ----------  ---------------  ------------------------------------
  1.0        08/13/2018  cbarrington      1. Created this procedure.

 ******************************************************************************/

CURSOR outstanding_clocks IS
  select c.clock_id,
         c.clock_index,
         lo.owner_name,
         c.clock_start,
         c.clock_end,
         ct.duration_in_days,
         c.trust_id
  from ESCHEATMENT.clock c,
       ESCHEATMENT.clock_type ct,
       ESCHEATMENT.lawful_owner lo
  where c.clock_expire_ind = 'N' and
        c.clock_stop_ind = 'N' and
        c.clock_type_id = ct.clock_type_id and
        c.lawful_owner_id = lo.owner_id; 
        
BEGIN

  FOR clock IN outstanding_clocks
  LOOP
  
    --has response been filed?
    select count(*)
    into v_responseCnt
    from v3owner01.trust t,
         v3owner01.fee f,
         v3owner01.case c
    where f.trust_id = t.trust_id and
          f.case_id = c.case_id and      
          exists (select 'x' from v3owner01.filing fl where fl.case_id = f.case_id and fl.filing_name_id = 90023689 and fl.other_name = clock.clock_index and fl.filing_dt >= clock.clock_start and fl.filing_dt <= clock.clock_end) and
          t.trust_id = clock.trust_id;  
       
    if v_responseCnt > 0 then
      --response filed, so stop clock
      update escheatment.clock
      set clock_stop_ind = 'Y',
          update_dt = sysdate, 
          update_user_id = 'CLOCK_STOP_EXPIRE'
      where clock_id = clock.clock_id;
      
      commit;
          
      INSERT INTO escheatment.log (log_id,category,proc_name,proc_status,message,create_dt)
      values (escheatment.log_seq.nextval,'PROC','CLOCK_STOP_EXPIRE','INPROCESS','clock_id ' || clock.clock_id || ' stopped',sysdate);
       
      commit;       
  
    else 
      --response not filed (or not filed timely), so expire clock
      update escheatment.clock
      set clock_expire_ind = 'Y',
          update_dt = sysdate, 
          update_user_id = 'CLOCK_STOP_EXPIRE'
      where clock_id = clock.clock_id; 
      
      commit;
      
      INSERT INTO escheatment.log (log_id,category,proc_name,proc_status,message,create_dt)
      values (escheatment.log_seq.nextval,'PROC','CLOCK_STOP_EXPIRE','INPROCESS','clock_id ' || clock.clock_id || ' expired',sysdate);
       
      commit;        
      
      --if there are no remaining outstanding clocks for this trust, populate WQ
      select count(*)
      into v_clockCnt
      from ESCHEATMENT.clock c
      where c.clock_expire_ind = 'N' and
            c.clock_stop_ind = 'N' and
            c.trust_id = clock.trust_id; 
            
      if v_clockCnt = 0 then
      
        select c.case_id,
               f.fee_id
        into v_caseId,
             v_feeId
        from v3owner01.trust t,
             v3owner01.fee f,
             v3owner01.case c
        where f.trust_id = t.trust_id and
              f.case_id = c.case_id and              
              t.trust_id = clock.trust_id;        
      
        insert into v3owner01.wq_task (TASK_ID,EXTERNAL_SOURCE_CD,EXTERNAL_ID,WORK_QUEUE_ID,CASE_ID,STATUS_ID,PRIORITY_ID,COMMENTS,VERSION,ASSIGNED_TO_USER_ID,CREATE_DT,CREATE_USER_ID,UPDATE_DT,UPDATE_USER_ID,COMPLETION_REASON,CREATE_REASON,CANCELLATION_REASON,MAC_ADDR,TASK_CREATE_USER_ID,TASK_UPDATE_USER_ID,COMPLETION_DT)
        values (v3owner01.wq_task_seq.nextval,'FEE',v_feeId,(select work_queue_id from v3owner01.work_queue where name = 'Trusts to Escheat - Civil' and eff_end_dt is null),v_caseId,10200,10207,null,1,null,sysdate,'ESCH_APP',sysdate,'ESCH_APP',null,'Trust to be Escheated',null,null,null,null,null);
        commit;
        
        INSERT INTO escheatment.log (log_id,category,proc_name,proc_status,message,create_dt)
        values (escheatment.log_seq.nextval,'PROC','CLOCK_STOP_EXPIRE','INPROCESS','fee_id ' || v_feeId || ' added to work queue',sysdate);    
        commit;          
      
      end if;
      
    end if;
    
  END LOOP;
  
  out_return_code    := '1';
  out_return_message := 'success';
  
  INSERT INTO escheatment.log (log_id,category,proc_name,proc_status,message,create_dt)
  values (escheatment.log_seq.nextval,'PROC','CLOCK_STOP_EXPIRE','COMPLETE',null,sysdate);
   
  commit;  
  
EXCEPTION
WHEN NO_DATA_FOUND THEN
  out_return_code    := '0';
  out_return_message := 'No records found.';
WHEN OTHERS THEN
  v_ErrorMsg := SUBSTR
  (
    DBMS_UTILITY.FORMAT_ERROR_STACK,1,100
  )
  ;
  v_ErrorTrace := SUBSTR
  (
    DBMS_UTILITY.format_error_backtrace,1,295
  )
  ;
  out_return_message := v_ErrorMsg || ' - ' || v_ErrorTrace; 
  out_return_code := '-1';
  
  INSERT INTO escheatment.log (log_id,category,proc_name,proc_status,message,create_dt)
  values (escheatment.log_seq.nextval,'PROC','CLOCK_STOP_EXPIRE','FAILED',out_return_message,sysdate);
   
  commit;  
  
END CLOCK_STOP_EXPIRE;