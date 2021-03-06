--------------------------------------------------------
--  DDL for Table STATUS
--------------------------------------------------------

  CREATE TABLE "ESCHEATMENT"."STATUS" 
   (	"STATUS_ID" NUMBER(*,0), 
	"DESCRIPTION" VARCHAR2(100 BYTE), 
	"CREATE_USER_ID" VARCHAR2(40 BYTE), 
	"CREATE_DT" DATE, 
	"UPDATE_USER_ID" VARCHAR2(40 BYTE), 
	"UPDATE_DT" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index STATUS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ESCHEATMENT"."STATUS_PK" ON "ESCHEATMENT"."STATUS" ("STATUS_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table STATUS
--------------------------------------------------------

  ALTER TABLE "ESCHEATMENT"."STATUS" ADD CONSTRAINT "STATUS_PK" PRIMARY KEY ("STATUS_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ESCHEATMENT"."STATUS" MODIFY ("UPDATE_DT" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."STATUS" MODIFY ("UPDATE_USER_ID" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."STATUS" MODIFY ("CREATE_DT" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."STATUS" MODIFY ("CREATE_USER_ID" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."STATUS" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."STATUS" MODIFY ("STATUS_ID" NOT NULL ENABLE);
