package org.occourts.escheatment;

/**
* Constants contains constants relating to various areas of the application
* $Revision: 4512 $     
* $Author: cbarrington $ 
* $Date: 2018-08-24 15:52:51 -0700 (Fri, 24 Aug 2018) $    
*/

public final class Constants {
	public static final String LDAP_HOST = "ldap.host";
	public static final String LDAP_PORT = "ldap.port";
	public static final String LDAP_SECURE = "ldap.secure";
	public static final String LDAP_UID = "ldap.uid";
	public static final String LDAP_SEARCH_ROOT = "ldap.searchRoot";
	public static final String AD_NTDOMAIN = "ad.ntDomain";
	public static final String AD_ADMIN_ID = "ad.admin.id";
	public static final String AD_ADMIN_PASS = "ad.admin.pass";
	public static final String PDF_OUTPUT_PATH = "pdf.outputPath";

	public static final String COLLAGE_HEADER_AUTHENTICATE = "WWW-Authenticate";
	public static final String COLLAGE_HEADER_AUTHORIZATION = "Authorization";
	public static final String COLLAGE_ENV = "collage.environment";
	public static final String COLLAGE_AUTHENTICATE = "collage.authenticate";
	public static final String COLLAGE_AUTHORIZE = "collage.authorize";
	public static final String COLLAGE_UPLOAD_URL = "collage.upload.url";

	public static final String COLLAGE_RESPONSE_DOC_ID = "collageDocumentId";
	public static final String COLLAGE_RESPONSE_MESSAGE = "message";
	public static final String ESERVICE_WS_URL = "eservice.ws.url";

	public static final int	OPS_ROLE = 1;	
	public static final int	ACCT_ROLE = 2;	
	public static final int	FUNC_ADMIN_ROLE = 3;		
	public static final int	APP_ADMIN_ROLE = 4;		
	public static final int	ALLOWED = 1;		
	public static final int	NOT_ALLOWED = 0;		
	public static final String	ACTIVE_STATUS = "Y";
	
	public static final int IDENTIFIED_FOR_ESCHEATMENT_STATUS_ID = 1;
	public static final int NOTICE_OF_UNCLAIMED_FUNDS_SENT_STATUS_ID = 2;
	public static final int MARKED_AS_ACTIVE_STATUS_ID = 3;
	public static final int READY_FOR_PUBLICATION_STATUS_ID = 4;
	public static final int FINALIZED_STATUS_ID = 5;
	
	public static final int NOTICE_OF_UNCLAIMED_FUNDS_NOTICE_ID = 1;
	public static final int REQUEST_FOR_ADDL_INFO_NOTICE_ID = 2;
	public static final int RESPONSE_APPROVED_NOTICE_ID = 3;
	public static final int RESPONSE_REJECTED_NOTICE_ID = 4;
	
	public static final String MARKED_AS_ACTIVE_YES = "Y";
	public static final String MARKED_AS_ACTIVE_NO = "N";
	
	public static final String MARKED_ON_HOLD_YES = "Y";
	public static final String MARKED_ON_HOLD_NO = "N";	
	
	public static final String MARKED_UNDER_REVIEW_YES = "Y";
	public static final String MARKED_UNDER_REVIEW_NO = "N";		
	
	public static final String FINALIZED_YES = "Y";
	public static final String FINALIZED_NO = "N";
	
	public static final int AFFIRMATION_SIG_MISSING = 1;
	public static final int NOTARIZATION_MISSING = 2;
	public static final int PHOTO_ID_MISSING = 3;
	public static final int INSUFFICIENT_DOCUMENTATION = 4;
	public static final int OTHER_REJECT_REASON = 5;	
}
