CREATE OR REPLACE PROCEDURE "ESCHEATMENT"."GET_TRUSTS_IDENT_FOR_ESCH" 
 (
    out_return_code      OUT      VARCHAR2,
    out_return_message   OUT      VARCHAR2,
    out_return_cursor    OUT      sys_refcursor
  )
AS
     
  v_ReturnCode         VARCHAR2(20)    := '';
  v_ReturnMessage      VARCHAR2(4000)  := '';
  v_ErrorMsg           VARCHAR2(100)   := '';
  v_ErrorTrace         VARCHAR2(4000)  := '';

 /******************************************************************************
  NAME:       GET_TRUSTS_IDENT_FOR_ESCH
  PURPOSE:
  Procedure is called from the Escheatment application to return trust data that
  meets the criteria provided by Operations/Accounting.
  
  REVISIONS:
  Ver        Date        Author           Description
  ---------  ----------  ---------------  ------------------------------------
  1.0        08/03/2018  cbarrington      1. Created this procedure.
  1.1        08/14/2018  cbarrington      2. Added log table entries

 ******************************************************************************/

BEGIN

  out_return_code    := '1';
  out_return_message := 'success';
  
  open out_return_cursor for 
    select t.trust_id, 
           t.trust_num, 
           '1' as status_id
    from v3owner01.trust t,
         v3owner01.fee f,
         v3owner01.case c,
         v3owner01.filing_name_fee_type_lkp fnftl
    where t.status_cd = 'ACTIVE' and
          t.bal > 20 and
          f.tran_dt <= sysdate - 1096 and
          t.trust_id = f.trust_id and
          f.case_id = c.case_id and
          fnftl.filing_fee_type_id = f.filing_fee_type_id and
          c.case_status_id in (70305,70301,70302,70300,70306,9003,9004,70303,70304,99129,9005,200015,200016,200018,200017,200019,65566,9010,99246,99150,90013148,99867,99151,9006) and
          fnftl.fee_type_id in (103532,9000045,103530,103533,9000069,901000,901001,9000047,9000049,103538,103539,220580,220583) and
          not exists (select et.trust_id from escheatment.trust et where et.trust_id = t.trust_id)
    union
    select t.trust_id, 
           t.trust_num, 
           '1' as status_id
    from v3owner01.trust t,
         v3owner01.fee f,
         v3owner01.case c,
         v3owner01.filing_name_fee_type_lkp fnftl
    where t.status_cd = 'ACTIVE' and
          t.bal > 20 and
          f.tran_dt <= sysdate - 1096 and
          t.trust_id = f.trust_id and
          f.case_id = c.case_id and
          fnftl.filing_fee_type_id = f.filing_fee_type_id and
          fnftl.fee_type_id in (220573) and
          not exists (select et.trust_id from escheatment.trust et where et.trust_id = t.trust_id)
    union
    select t.trust_id, 
           t.trust_num, 
           '2' as status_id 
    from v3owner01.trust t,
         v3owner01.fee f,
         v3owner01.case c,
         v3owner01.filing_name_fee_type_lkp fnftl
    where t.status_cd = 'ACTIVE' and
          t.bal > 20 and 
          f.tran_dt <= sysdate - 1096 and
          t.trust_id = f.trust_id and
          f.case_id = c.case_id and
          fnftl.filing_fee_type_id = f.filing_fee_type_id and
          fnftl.fee_type_id in (9000070) and
          not exists (select et.trust_id from escheatment.trust et where et.trust_id = t.trust_id);   
          
   INSERT INTO escheatment.log (log_id,category,proc_name,proc_status,message,create_dt)
   values (escheatment.log_seq.nextval,'PROC','GET_TRUSTS_IDENT_FOR_ESCH','COMPLETE',null,sysdate);
   
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
  values (escheatment.log_seq.nextval,'PROC','GET_TRUSTS_IDENT_FOR_ESCH','FAILED',out_return_message,sysdate);
   
  commit;   
  
END GET_TRUSTS_IDENT_FOR_ESCH;