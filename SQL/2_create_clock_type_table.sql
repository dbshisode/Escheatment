--------------------------------------------------------
--  DDL for Table CLOCK_TYPE
--------------------------------------------------------

  CREATE TABLE "ESCHEATMENT"."CLOCK_TYPE" 
   (	"CLOCK_TYPE_ID" NUMBER(*,0), 
	"DESCRIPTION" VARCHAR2(100 BYTE), 
	"DURATION_IN_DAYS" NUMBER(*,0), 
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
--  DDL for Index CLOCK_TYPE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ESCHEATMENT"."CLOCK_TYPE_PK" ON "ESCHEATMENT"."CLOCK_TYPE" ("CLOCK_TYPE_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table CLOCK_TYPE
--------------------------------------------------------

  ALTER TABLE "ESCHEATMENT"."CLOCK_TYPE" ADD CONSTRAINT "CLOCK_TYPE_PK" PRIMARY KEY ("CLOCK_TYPE_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ESCHEATMENT"."CLOCK_TYPE" MODIFY ("UPDATE_DT" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."CLOCK_TYPE" MODIFY ("UPDATE_USER_ID" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."CLOCK_TYPE" MODIFY ("CREATE_DT" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."CLOCK_TYPE" MODIFY ("CREATE_USER_ID" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."CLOCK_TYPE" MODIFY ("DURATION_IN_DAYS" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."CLOCK_TYPE" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."CLOCK_TYPE" MODIFY ("CLOCK_TYPE_ID" NOT NULL ENABLE);
