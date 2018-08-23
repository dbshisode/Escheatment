
  CREATE TABLE "ESCHEATMENT"."PUBLICATION_TRUST_ASSOC" 
   (	"PUB_ASSOC_ID" NUMBER(10,0) NOT NULL ENABLE, 
	"TRUST_ID" NUMBER(10,0) NOT NULL ENABLE, 
	"PUBLICATION_ID" NUMBER(10,0) NOT NULL ENABLE, 
	 CONSTRAINT "PUBLICATION_TRUST_ASSOC_PK" PRIMARY KEY ("PUB_ASSOC_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "USERS"  ENABLE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
