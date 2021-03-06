--------------------------------------------------------
--  DDL for Table PUBLICATION
--------------------------------------------------------

  CREATE TABLE "ESCHEATMENT"."PUBLICATION" 
   (	"PUBLICATION_ID" NUMBER(10,0), 
	"FINALIZED" VARCHAR2(1 BYTE) DEFAULT 'N', 
	"PUBLICATION_DT" DATE, 
	"OUTPUT_PDF" BLOB, 
	"CREATE_USER_ID" VARCHAR2(40 BYTE), 
	"CREATE_DT" DATE, 
	"UPDATE_USER_ID" VARCHAR2(40 BYTE), 
	"UPDATE_DT" DATE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" 
 LOB ("OUTPUT_PDF") STORE AS BASICFILE (
  TABLESPACE "USERS" ENABLE STORAGE IN ROW CHUNK 8192 RETENTION 
  NOCACHE LOGGING ) ;
--------------------------------------------------------
--  DDL for Index PUBLICATION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ESCHEATMENT"."PUBLICATION_PK" ON "ESCHEATMENT"."PUBLICATION" ("PUBLICATION_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table PUBLICATION
--------------------------------------------------------

  ALTER TABLE "ESCHEATMENT"."PUBLICATION" ADD CONSTRAINT "PUBLICATION_PK" PRIMARY KEY ("PUBLICATION_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "ESCHEATMENT"."PUBLICATION" MODIFY ("UPDATE_DT" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."PUBLICATION" MODIFY ("UPDATE_USER_ID" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."PUBLICATION" MODIFY ("CREATE_DT" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."PUBLICATION" MODIFY ("CREATE_USER_ID" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."PUBLICATION" MODIFY ("FINALIZED" NOT NULL ENABLE);
  ALTER TABLE "ESCHEATMENT"."PUBLICATION" MODIFY ("PUBLICATION_ID" NOT NULL ENABLE);
