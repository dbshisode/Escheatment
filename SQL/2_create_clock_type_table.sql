
  CREATE TABLE "ESCHEATMENT"."CLOCK_TYPE" 
   (	"CLOCK_TYPE_ID" NUMBER(*,0) NOT NULL ENABLE, 
	"DESCRIPTION" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"DURATION_IN_DAYS" NUMBER(*,0) NOT NULL ENABLE, 
	"CREATE_USER_ID" VARCHAR2(40 BYTE) NOT NULL ENABLE, 
	"CREATE_DT" DATE NOT NULL ENABLE, 
	"UPDATE_USER_ID" VARCHAR2(40 BYTE) NOT NULL ENABLE, 
	"UPDATE_DT" DATE NOT NULL ENABLE, 
	 CONSTRAINT "CLOCK_TYPE_PK" PRIMARY KEY ("CLOCK_TYPE_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
