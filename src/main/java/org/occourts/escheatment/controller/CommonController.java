package org.occourts.escheatment.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.occourts.escheatment.bo.WorkQueueDataBO;
import org.occourts.escheatment.dao.impl.CaseHistoryDAOImpl;
import org.occourts.escheatment.dao.impl.CommentsDAOImpl;
import org.occourts.escheatment.dao.impl.EscheatmentUserDAOImpl;
import org.occourts.escheatment.dao.impl.FormDataDAOImpl;
import org.occourts.escheatment.dao.impl.LawfulOwnerDAOImpl;
import org.occourts.escheatment.dao.impl.RoaDataDAOImpl;
import org.occourts.escheatment.dao.impl.WorkQueueDataDAOImpl;
import org.occourts.escheatment.model.Comments;
import org.occourts.escheatment.model.EServiceData;
import org.occourts.escheatment.model.FormData;
import org.occourts.escheatment.model.LawfulOwner;
import org.occourts.escheatment.model.RoaData;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.util.CaseHistoryService;
import org.occourts.escheatment.util.CollageUtil;
import org.occourts.escheatment.util.EscheatmentObject;
import org.occourts.escheatment.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
* CommonController provides mapping and necessary business logic required for logging into 
* the application, user validation, session management, and common functionality shared 
* accross multiple roles within the applicaiton
* $Revision: 4536 $     
* $Author: cbarrington $ 
* $Date: 2018-10-15 10:38:00 -0700 (Mon, 15 Oct 2018) $    
*/

@Controller
public class CommonController {

	@Autowired
	@Qualifier("messageSourceAccessor")
	private MessageSourceAccessor messageSourceAccessor;
	
	@Autowired
	CommentsDAOImpl commentsdao;	
	
	@Autowired
	EscheatmentUserDAOImpl escheatmentuserdao;
	
	@Autowired
	RoaDataDAOImpl roadatadao;	
	
	@Autowired
	private WorkQueueDataBO workqueuedatabo;	

	@Autowired
	WorkQueueDataDAOImpl wqdatadao;
	
	@Autowired
	LawfulOwnerDAOImpl lawfulownerdao;	
	
	@Autowired
	FormDataDAOImpl formdata;	
	
	@Autowired
	CaseHistoryService casehistory;	

	@RequestMapping(path = "/")
	public String index(@ModelAttribute("user") User user, Model model) {
		return "index";
	}

	@RequestMapping(value = "/review")
	public ModelAndView review(@ModelAttribute("user") @Validated User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		if (loggedinUser == null) {
			if (result.hasErrors()) {
				return new ModelAndView("index");
			}
			loggedinUser = escheatmentuserdao.fetchUser(user.getUserName());
			if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getActive()!=null && loggedinUser.getActive().equals("Y")) {
				session.setAttribute("user", loggedinUser);
			}else {
				return new ModelAndView("index");
			}
		}
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.OPS_ROLE) {
			model.addAttribute("opsCount", workqueuedatabo.getOpsWorkQueueDataCount());
			model.addAttribute("tab1State","btn-primary");
			model.addAttribute("tab2State","btn-default");
			model.addAttribute("tab3State","btn-default");		
			model.addAttribute("func_admin_role",Constants.FUNC_ADMIN_ROLE);
			model.addAttribute("user",loggedinUser);
			return new ModelAndView("review-ops");
		} else {			
			model.addAttribute("acctCount", workqueuedatabo.getAcctWorkQueueDataCount());
			model.addAttribute("tab1State","btn-primary");
			model.addAttribute("tab2State","btn-default");
			model.addAttribute("tab3State","btn-default");		
			model.addAttribute("tab4State","btn-default");
			model.addAttribute("tab5State","btn-default");
			model.addAttribute("func_admin_role",Constants.FUNC_ADMIN_ROLE);
			model.addAttribute("user",loggedinUser);
			return new ModelAndView("review-acct");
		}

	}	
	
	@RequestMapping(value = "/get-addl-names",  params = {"trustId"})
	public @ResponseBody String getaddlnames(@RequestParam(value="trustId") long trustId, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null) {
			List<LawfulOwner> addlNames = wqdatadao.fetchAdditionalLawfulOwners(trustId);
			String responseJson = "";
			ObjectMapper objectMapper = new ObjectMapper();			
			try {
				responseJson = objectMapper.writeValueAsString(addlNames);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;
		} else {
			return null;
		}

	}		
	
	@RequestMapping(path = "/comments-save")
	public String commentssave(@ModelAttribute("comment") Comments comment, User user, Model model, HttpSession session) {
		
		User loggedinUser = (User) session.getAttribute("user");
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		comment.setCommentDate(timestamp);
		comment.setUserId(loggedinUser.getUserId());
		comment.setUserName(loggedinUser.getUserName());
		
		commentsdao.addComment(comment);

		return "comments";
	}
		
	
	@RequestMapping(path = "/comments")
	public String comments(@RequestParam(value="trustId") long trustId,@ModelAttribute("comment") Comments comment, Model model) {
		List<Comments> comments = commentsdao.fetchCommentData(trustId);

		model.addAttribute("opsRoleValue",Constants.OPS_ROLE);
		model.addAttribute("comments", comments);
		return "comments";
	}
	
	@RequestMapping(path = "/roa")
	public String roa(@RequestParam(value="caseNum") String caseNum, Model model, HttpSession session) {
		session.setAttribute("caseNum", caseNum);
		return "roa";
	}	
	
	@RequestMapping(path = "/roa-left-frame")
	public String roaleftframe(Model model, HttpSession session) {
		model.addAttribute("caseNum",session.getAttribute("caseNum"));
		return "roa-left-frame";
	}	
	
	@RequestMapping(path = "/roa-right-frame")
	public String roarightframe(Model model, HttpSession session) {		
		return "roa-right-frame";
	}	
	
	@RequestMapping(path = "/roa-get-data")
	public String roagetdata(@RequestParam(value="date1", required=false) String date1,
							 @RequestParam(value="date2", required=false) String date2,
							 Model model, HttpSession session) throws ParseException {;
		
		if (date1 == null || date1.length() == 0) {			
			date1 = "01/01/1900";			
		} 
		if (date2 == null || date2.length() == 0) {			
			date2 = "12/31/5000";			
		}
		
		List<RoaData> roa = roadatadao.fetchRoaData(session.getAttribute("caseNum").toString(),date1,date2);
		
		model.addAttribute("roadata", roa);
		return "roa-get-data";
	}	
	

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView displayLogoutPage(@ModelAttribute("user") User user, Model model, HttpSession session) {

		session.invalidate();
		return new ModelAndView("logout");

	}
	
	@RequestMapping(value = "/send-notice-to-all",  params = {"trustId","formName"})
	public @ResponseBody String sendnoticetoall(@RequestParam(value="trustId") long trustId, 
			                                    @RequestParam(value="formName") String formName,
			                                    Model model, 
			                                    HttpSession session) {
		
		Map<String,Object> responseMap = new HashMap<String,Object>();
		User loggedinUser = (User) session.getAttribute("user");
		boolean caseHistoryServiceResult = false;
		boolean noticeSentResult = false;
		int counter = 0;		
		Long documentId;
		
		if (loggedinUser != null) {			
			String responseJson = "";
			
			//TODO based on trustId, query all lawful owners and loop through each
			List<LawfulOwner> owners =  lawfulownerdao.fetchAllLawfulOwnersByTrustId(trustId);						
			FormData formData = new FormData();
			EServiceData eServiceData = new EServiceData();
			formData = formdata.fetchFormDataByTrustId(trustId, loggedinUser.getUserName());
			formData.setTrustId(trustId);
			
			switch (formName) {
				case "NoticeOfUnclaimedFunds": formData.setFormDisplayName("Notice of Unclaimed Funds");
				formData.setNoticeId(Constants.NOTICE_OF_UNCLAIMED_FUNDS_NOTICE_ID);
				break;
				case "RequestForAddlInfo": formData.setFormDisplayName("Request for Additional Information");
				formData.setNoticeId(Constants.REQUEST_FOR_ADDL_INFO_NOTICE_ID);
				break;				
			}
			
			for (LawfulOwner owner : owners) {
								
				counter += 1;
				String pdfLocation =  EscheatmentObject.getBaseFilePath() + trustId + "\\" + formName + counter + ".pdf";	
				formData.setFormPdfFullPath(pdfLocation);

				//case history entry
				caseHistoryServiceResult = false;
				documentId = casehistory.addCaseHistoryEntry(formData, eServiceData, owner, session);	
				formData.setDocumentId(documentId);
				caseHistoryServiceResult = true;
				
				//log in escheatment.notice_sent table so image can be retrieved from DMS
				noticeSentResult = formdata.insertIntoNoticeSent(formData,owner,loggedinUser.getUserName());

			}

			
			if (caseHistoryServiceResult && noticeSentResult) {
				responseMap.put("status","SUCCESS");
				responseMap.put("message","Notice(s) sent successfully");				
			} else {
				responseMap.put("status","ERROR");
				responseMap.put("message","Unable to send notice(s)");						
			}
			
			ObjectMapper objectMapper = new ObjectMapper();			
			try {
				responseJson = objectMapper.writeValueAsString(responseMap);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;
		} else {
			return null;
		}

	}		

	@RequestMapping(value = "/send-notice-to-one",  params = {"trustId","formName"})
	public @ResponseBody String sendnoticetoone(@RequestParam(value="trustId") long trustId, 
			                                    @RequestParam(value="formName") String formName,
			                                    @RequestParam(value="lawfulOwnerId") long lawfulOwnerId,
			                                    Model model, 
			                                    HttpSession session) {
		
		Map<String,Object> responseMap = new HashMap<String,Object>();
		User loggedinUser = (User) session.getAttribute("user");
		boolean caseHistoryServiceResult = false;
		boolean noticeSentResult = false;		
		Long documentId;
		
		if (loggedinUser != null) {			
			String responseJson = "";
			
			//TODO based on trustId, query all lawful owners and loop through each
			LawfulOwner owner =  lawfulownerdao.fetchLawfulOwnerByLawfulOwnerId(lawfulOwnerId);			
			FormData formData = new FormData();
			formData = formdata.fetchFormDataByTrustId(trustId, loggedinUser.getUserName());		
			formData.setTrustId(trustId);
			
			switch (formName) {
				case "NoticeOfUnclaimedFunds": formData.setFormDisplayName("Notice of Unclaimed Funds");
				formData.setNoticeId(Constants.NOTICE_OF_UNCLAIMED_FUNDS_NOTICE_ID);
				break;
				case "RequestForAddlInfo": formData.setFormDisplayName("Request for Additional Information");
				formData.setNoticeId(Constants.REQUEST_FOR_ADDL_INFO_NOTICE_ID);
				break;				
			}
											
			String pdfLocation = EscheatmentObject.getBaseFilePath() + trustId + "\\" + formName + ".pdf";	
			formData.setFormPdfFullPath(pdfLocation);

			//case history entry
			caseHistoryServiceResult = false;
			documentId = casehistory.addCaseHistoryEntry(formData, new EServiceData(), owner, session);	
			formData.setDocumentId(documentId);
			caseHistoryServiceResult = true;
			
			//log in escheatment.notice_sent table so image can be retrieved from DMS
			noticeSentResult = formdata.insertIntoNoticeSent(formData,owner,loggedinUser.getUserName());
			
			if (caseHistoryServiceResult && noticeSentResult) {
				responseMap.put("status","SUCCESS");
				responseMap.put("message","Notice(s) sent successfully");				
			} else {
				responseMap.put("status","ERROR");
				responseMap.put("message","Unable to send notice");						
			}
			
			ObjectMapper objectMapper = new ObjectMapper();			
			try {
				responseJson = objectMapper.writeValueAsString(responseMap);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;
		} else {
			return null;
		}

	}		
	
}