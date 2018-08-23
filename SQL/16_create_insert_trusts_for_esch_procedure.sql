CREATE OR REPLACE PROCEDURE "ESCHEATMENT"."INSERT_TRUSTS_IDENT_FOR_ESCH" 
 (
    in_user_name         IN       VARCHAR2,
    out_return_code      OUT      VARCHAR2,
    out_return_message   OUT      VARCHAR2
  )
AS
     
  v_ReturnCode         VARCHAR2(20)    := '';
  v_ReturnMessage      VARCHAR2(4000)  := '';
  v_ErrorMsg           VARCHAR2(100)   := '';
  v_ErrorTrace         VARCHAR2(4000)  := '';  
  v_trustId            NUMBER;
  v_trustNum           NUMBER;
  v_statusId           NUMBER;
  v_rowsToInsert       sys_refcursor;

 /******************************************************************************
  NAME:       INSERT_TRUSTS_IDENT_FOR_ESCH
  PURPOSE:
  Procedure is called from the Escheatment application to insert trust data that
  meets the criteria provided by Operations/Accounting.
  
  REVISIONS:
  Ver        Date        Author           Description
  ---------  ----------  ---------------  ------------------------------------
  1.0        08/03/2018  cbarrington      1. Created this procedure.

 ******************************************************************************/

BEGIN

  out_return_code    := '1';
  out_return_message := 'success';
  
  --get data to insert by calling procedure that queries required data
  get_trusts_ident_for_esch(v_ReturnCode,v_ReturnMessage,v_rowsToInsert);   
  
    -- Loop through the resulting data
    IF (v_rowsToInsert IS NOT NULL)
    THEN
        LOOP
            FETCH v_rowsToInsert INTO v_trustId,v_trustNum,v_statusId;
            EXIT WHEN v_rowsToInsert%NOTFOUND;
            insert into escheatment.trust (TRUST_ID,TRUST_NUM,MARKED_AS_ACTIVE,STATUS_ID,DELETE_IND,ON_HOLD_IND,CREATE_USER_ID,CREATE_DT,UPDATE_USER_ID,UPDATE_DT)
            values (v_trustId,v_trustNum,'N',v_statusId,'N','N',in_user_name,sysdate,in_user_name,sysdate);    
            commit;
        END LOOP;
        CLOSE v_rowsToInsert;
    END IF;
    
  
EXCEPTION

WHEN OTHERS THEN
  v_ErrorMsg := SUBSTR
  (
    DBMS_UTILITY.FORMAT_ERROR_STACK,1,100
  )
  ;
  v_ErrorTrace := SUBSTR
  (
    DBMS_UTILITY.format_error_backtrace,1,4000
  )
  ;
  out_return_message := v_ErrorMsg || ' - ' || v_ErrorTrace; 
  out_return_code := '-1';
  
END INSERT_TRUSTS_IDENT_FOR_ESCH;