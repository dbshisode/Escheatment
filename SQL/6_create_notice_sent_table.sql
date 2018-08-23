
  CREATE TABLE "ESCHEATMENT"."NOTICE_SENT" 
   (	"NOTICE_SENT_ID" NUMBER(10,0) NOT NULL ENABLE, 
	"NOTICE_ID" NUMBER(*,0) NOT NULL ENABLE, 
	"OWNER_ID" NUMBER(10,0) NOT NULL ENABLE, 
	"NOTICE_SEND_DT" DATE NOT NULL ENABLE, 
	"CREATE_USER_ID" VARCHAR2(40 BYTE) NOT NULL ENABLE, 
	"CREATE_DT" DATE NOT NULL ENABLE, 
	"UPDATE_USER_ID" VARCHAR2(40 BYTE) NOT NULL ENABLE, 
	"UPDATE_DT" DATE NOT NULL ENABLE, 
	 CONSTRAINT "NOTICE_SENT_PK" PRIMARY KEY ("NOTICE_SENT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
