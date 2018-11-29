package org.occourts.escheatment.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.lang3.StringUtils;
import org.occourts.escheatment.Constants;
import org.occourts.escheatment.dao.CaseHistoryDAO;
import org.occourts.escheatment.dao.impl.CaseHistoryDAOImpl;
import org.occourts.escheatment.model.CaseHistory;
import org.occourts.escheatment.model.EServiceData;
import org.occourts.escheatment.model.FormData;
import org.occourts.escheatment.model.LawfulOwner;
import org.occourts.escheatment.model.User;
import org.occourts.ws.eservice.ObjectFactory;
import org.occourts.ws.eservice.SubmitForBatchProcessing;
import org.occourts.ws.eservice.SubmitForBatchProcessingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class CaseHistoryService {
	private static Logger LOGGER = LoggerFactory.getLogger(CaseHistoryService.class);
	private static final ObjectFactory ESERVICE_WS_CLIENT_FACTORY = new ObjectFactory();

	@Resource(name = "eserviceWebServiceTemplate")
	private WebServiceTemplate eserviceWebServiceTemplate;

	@Autowired
	private CaseHistoryDAOImpl caseHistoryDao;
	
	//returns document_id
	public Long addCaseHistoryEntry(FormData formData, EServiceData eServiceData, LawfulOwner lawfulOwner, HttpSession session) {
		//TODO: stub data
		//String docId = CollageUtil.getInstance().uploadDocument("civil", "c:/temp/test.pdf");
		User loggedinUser = (User) session.getAttribute("user");
		Boolean eServiceResult = false;
		Long documentId = null;
		String docId = CollageUtil.getInstance().uploadDocument(formData.getCaseCategory(), formData.getFormPdfFullPath());
		if(StringUtils.isNotBlank(docId)) {
			
			//add Case History entry, which returns document_id value required by eService API
			documentId = caseHistoryDao.addCaseHistoryEntry(formData.getCaseId(), Double.valueOf(docId), formData.getFormDisplayName(), lawfulOwner, loggedinUser.getUserName());			
			
			//establish eServiceData object							
			//TODO what to do about addressLine2 and nameLine2
			eServiceData.setCaseId(formData.getCaseId());
			eServiceData.setCollageDocumentId(Double.valueOf(docId));
			eServiceData.setDocumentDate(Calendar.getInstance().getTime());
			eServiceData.setDocumentName(formData.getFormDisplayName());
			eServiceData.setRecipientAddressLine1(lawfulOwner.getAddressLine1());
			eServiceData.setRecipientAddressLine2(lawfulOwner.getAddressLine3());
			eServiceData.setRecipientName(lawfulOwner.getNameLine1());
			eServiceData.setUserName(loggedinUser.getUserName());			
			
			eServiceResult = submitCaseToEService(eServiceData);
		}
		if (eServiceResult) {			
			return documentId;
		} else {
			return null;
		}
		
	}
	
	//Sample data for eService
	private EServiceData testCreateEserviceData() {
		EServiceData eServiceData = new EServiceData();
		double d = 203269314d;
		eServiceData.setCaseId(100d);
		eServiceData.setCollageDocumentId(d);
		eServiceData.setDocumentDate(Calendar.getInstance().getTime());
		eServiceData.setDocumentName("AAA");
		eServiceData.setRecipientAddressLine1("Ad1");
		eServiceData.setRecipientAddressLine2("Ad2");
		eServiceData.setRecipientName("Ferdi");
		eServiceData.setUserName("flabriaga");
		return eServiceData;
	}
	
	public boolean submitCaseToEService(EServiceData eServiceData) {
		SubmitForBatchProcessing submitForBatchProcessing = transFormToESericeRequest(eServiceData);
		try {
			SubmitForBatchProcessingResponse response = (SubmitForBatchProcessingResponse) eserviceWebServiceTemplate
					.marshalSendAndReceive(SpringBeanUtil.getInstance().getProperty(Constants.ESERVICE_WS_URL),
							submitForBatchProcessing);
			LOGGER.info("eService call [{}].", response.isReturn() ? "Successful" : "Failed");
			return response.isReturn();
		} catch (Exception e) {
			LOGGER.info("An exception has occured while calling eService => [{}]", e.getMessage());
		}
		return false;
	}

	private SubmitForBatchProcessing transFormToESericeRequest(EServiceData eServiceData) {
		if (eServiceData == null || eServiceData.getCaseId() == null || eServiceData.getCollageDocumentId() == null
				|| eServiceData.getDocumentDate() == null) {
			LOGGER.warn("eServiceData is not valid.");
			return null;
		}
		SubmitForBatchProcessing submitForBatchProcessing = ESERVICE_WS_CLIENT_FACTORY.createSubmitForBatchProcessing();
		submitForBatchProcessing.setCaseId(eServiceData.getCaseId());
		submitForBatchProcessing.setCollageDocumentId(eServiceData.getCollageDocumentId());

		GregorianCalendar documentDate = new GregorianCalendar();
		documentDate.setTime(eServiceData.getDocumentDate());

		try {
			submitForBatchProcessing
					.setDocumentDate(ESERVICE_WS_CLIENT_FACTORY.createSubmitForBatchProcessingDocumentDate(
							DatatypeFactory.newInstance().newXMLGregorianCalendar(documentDate)));
		} catch (DatatypeConfigurationException e) {
			LOGGER.error("Exception has occured while setting document date. ", e);
			e.printStackTrace();
		}
		submitForBatchProcessing.setDocumentName(
				ESERVICE_WS_CLIENT_FACTORY.createSubmitForBatchProcessingDocumentName(eServiceData.getDocumentName()));
		submitForBatchProcessing.setRecipientAddressLine1(ESERVICE_WS_CLIENT_FACTORY
				.createSubmitForBatchProcessingRecipientAddressLine1(eServiceData.getRecipientAddressLine1()));
		submitForBatchProcessing.setRecipientAddressLine2(ESERVICE_WS_CLIENT_FACTORY
				.createSubmitForBatchProcessingRecipientAddressLine2(eServiceData.getRecipientAddressLine2()));
		submitForBatchProcessing.setRecipientName(ESERVICE_WS_CLIENT_FACTORY
				.createSubmitForBatchProcessingRecipientName(eServiceData.getRecipientName()));
		submitForBatchProcessing.setUserName(
				ESERVICE_WS_CLIENT_FACTORY.createSubmitForBatchProcessingUserName(eServiceData.getUserName()));
		return submitForBatchProcessing;
	}

}
