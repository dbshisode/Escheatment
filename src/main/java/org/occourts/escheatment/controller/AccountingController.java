package org.occourts.escheatment.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.occourts.escheatment.bo.WorkQueueDataBO;
import org.occourts.escheatment.dao.impl.FormDataDAOImpl;
import org.occourts.escheatment.dao.impl.LawfulOwnerDAOImpl;
import org.occourts.escheatment.dao.impl.WorkQueueDataDAOImpl;
import org.occourts.escheatment.model.FormData;
import org.occourts.escheatment.model.LawfulOwner;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.model.WorkQueueData;
import org.occourts.escheatment.Constants;
import org.occourts.escheatment.util.PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AccountingController {
	
	@Autowired
	WorkQueueDataDAOImpl wqdatadao;
	
	@Autowired
	LawfulOwnerDAOImpl lawfulownerdao;	
	
	@Autowired
	FormDataDAOImpl formdatadao;	
	
	@Autowired
	PdfUtil pdf;		
	
	@Autowired
	private WorkQueueDataBO workqueuedatabo;	
	
	@RequestMapping(value = "/acct-under-review")
	public ModelAndView active(Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.ACCT_ROLE) {
			model.addAttribute("acctCount", workqueuedatabo.getAcctWorkQueueDataCount());
			model.addAttribute("tab1State","btn-default");
			model.addAttribute("tab2State","btn-default");
			model.addAttribute("tab3State","btn-default");		
			model.addAttribute("tab4State","btn-primary");
			model.addAttribute("tab5State","btn-default");		
			model.addAttribute("func_admin_role",Constants.FUNC_ADMIN_ROLE);
			return new ModelAndView("acct-under-review");
		} else {
			return new ModelAndView("index");
		}

	}	
	
	@RequestMapping(value = "/acct-pending")
	public ModelAndView pending(Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.ACCT_ROLE) {
			
			int pubTabCount = 1;
			pubTabCount += wqdatadao.getPublicationPendingCount();
			model.addAttribute("acctCount", workqueuedatabo.getAcctWorkQueueDataCount());
			model.addAttribute("tab1State","btn-default");
			model.addAttribute("tab2State","btn-primary");
			model.addAttribute("tab3State","btn-default");		
			model.addAttribute("tab4State","btn-default");
			model.addAttribute("tab5State","btn-default");	
			model.addAttribute("func_admin_role",Constants.FUNC_ADMIN_ROLE);
			model.addAttribute("pubTabCount",pubTabCount);
			return new ModelAndView("acct-pending");
		} else {
			return new ModelAndView("index");
		}

	}	

	@RequestMapping(value = "/get-review-acct-table")
	public @ResponseBody String getacctreviewtable(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.ACCT_ROLE) {
			List<WorkQueueData> workqueuedata = wqdatadao.fetchAcctReviewData(Constants.MARKED_ON_HOLD_NO,Constants.MARKED_UNDER_REVIEW_NO,Constants.NOTICE_OF_UNCLAIMED_FUNDS_SENT_STATUS_ID);
			String responseJson = "";
			ObjectMapper objectMapper = new ObjectMapper();			
			try {
				responseJson = objectMapper.writeValueAsString(workqueuedata);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;
		} else {
			return null;
		}

	}
	
	@RequestMapping(value = "/get-pending-acct-table")
	public @ResponseBody String getacctpendingtable(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.ACCT_ROLE) {
			List<WorkQueueData> workqueuedata = wqdatadao.fetchAcctReviewData(Constants.MARKED_ON_HOLD_NO,Constants.MARKED_UNDER_REVIEW_NO,Constants.READY_FOR_PUBLICATION_STATUS_ID);
			String responseJson = "";
			ObjectMapper objectMapper = new ObjectMapper();			
			try {
				responseJson = objectMapper.writeValueAsString(workqueuedata);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;
		} else {
			return null;
		}

	}	
	
	@RequestMapping(value = "/get-pending-acct-batch-table",  params = {"index"})
	public @ResponseBody String getacctbatchpendingtable(@RequestParam(value="index") int index, @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.ACCT_ROLE) {
			List<WorkQueueData> workqueuedata = wqdatadao.fetchPublicationBatchesPendingPubDate(Constants.FINALIZED_YES,index);
			String responseJson = "";
			ObjectMapper objectMapper = new ObjectMapper();			
			try {
				responseJson = objectMapper.writeValueAsString(workqueuedata);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;
		} else {
			return null;
		}

	}		
	
	@RequestMapping(value = "/get-acct-under-review-table")
	public @ResponseBody String getacctunderreviewtable(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.ACCT_ROLE) {
			List<WorkQueueData> workqueuedata = wqdatadao.fetchAcctReviewData(Constants.MARKED_ON_HOLD_NO,Constants.MARKED_UNDER_REVIEW_YES,Constants.NOTICE_OF_UNCLAIMED_FUNDS_SENT_STATUS_ID);
			String responseJson = "";
			ObjectMapper objectMapper = new ObjectMapper();			
			try {
				responseJson = objectMapper.writeValueAsString(workqueuedata);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;
		} else {
			return null;
		}

	}	
	
	@RequestMapping(value = "/add-to-acct-list")
	public ModelAndView addtoacctlist(Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		FormData formData = new FormData();
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.ACCT_ROLE) {
			model.addAttribute("formData", formData);					
			return new ModelAndView("acct-add-to-list-form");
		} else {
			return new ModelAndView("index");
		}

	}		
	
	@RequestMapping(value = "/add-to-acct-list-trust")
	public @ResponseBody String addtoacctlisttrust(@RequestParam(value="trustNumber") String trustNumber, Model model, HttpSession session) {
						
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String responseJson = "";
		ObjectMapper objectMapper = new ObjectMapper();		
		//int existingRecordCnt = 0;
		
		FormData formData = new FormData();
		//existingRecordCnt = wqdatadao.getCountByTrustNum(trustNumber);
		formData = wqdatadao.fetchTrustInfoByTrustNum(trustNumber);
		
		/*
		if (existingRecordCnt > 0) {
			responseMap.put("status","ERROR");
			responseMap.put("message","Trust Number already added");			
		}
		*/
		if (formData.getTrustId() != null) {
			responseMap.put("status","SUCCESS");
			responseMap.put("message","Trust Number found");	
			
			responseMap.put("trustAmount",formData.getTrustAmount());
			responseMap.put("trustNumber",trustNumber);
			responseMap.put("trustType",formData.getTrustType());
			responseMap.put("trustId",formData.getTrustId());
			responseMap.put("trustDate",formData.getTrustDate());
			responseMap.put("caseNumber",formData.getCaseNumber());
			responseMap.put("caseTitle",formData.getCaseTitle());			
		} else {
			responseMap.put("status","ERROR");
			responseMap.put("message","Trust Number not found");	
		}

		
		try {
			responseJson = objectMapper.writeValueAsString(responseMap);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block				
		}
		return responseJson;

	}	
	
	
	@RequestMapping(value = "/add-to-acct-list-save")
	public @ResponseBody String addtoacctlistsave(@RequestParam(value="trustId") Long trustId,
			                                      @RequestParam(value="trustNumber") String trustNumber,	
			                                      @RequestParam(value="nameLine1") String nameLine1,
			                                      @RequestParam(value="nameLine2") String nameLine2,
			                                      @RequestParam(value="addressLine1") String addressLine1,
			                                      @RequestParam(value="addressLine2") String addressLine2,
			                                      @RequestParam(value="addressLine3") String addressLine3,			                                      
			                                      Model model, 
			                                      HttpSession session) {
		
		Map<String,Object> responseMap = new HashMap<String,Object>();		
		String responseJson = "";
		ObjectMapper objectMapper = new ObjectMapper();
		User loggedinUser = (User) session.getAttribute("user");	
		int trustCnt = wqdatadao.getCountByTrustNum(trustNumber);
		int clockIndex = lawfulownerdao.getNextClockIndex(trustId);
		LawfulOwner owner = new LawfulOwner(); 
		List<LawfulOwner> ownerList = new ArrayList<LawfulOwner>();
		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.ACCT_ROLE) {
			
			//validate required data is passed in
			if (nameLine1 == null || addressLine1 == null || addressLine3 == null) {
				responseMap.put("status","ERROR");
				responseMap.put("message","Please enter lawful owner name and address");				
				try {
					responseJson = objectMapper.writeValueAsString(responseMap);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block				
				}
				return responseJson;				
			}
			
			//if trust does not exist, add it
			if (trustCnt == 0) {
				wqdatadao.insertEscheatmentTrust(trustId,trustNumber,Constants.NOTICE_OF_UNCLAIMED_FUNDS_SENT_STATUS_ID,loggedinUser.getUserName());
			}					
			
			//add lawful owner
			owner.setAddressLine1(addressLine1);
			owner.setAddressLine2(addressLine2);
			owner.setAddressLine3(addressLine3);
			owner.setNameLine1(nameLine1);
			owner.setNameLine2(nameLine2);
			owner.setClockIndex(BigDecimal.valueOf(clockIndex));
			owner.setTrustId(BigDecimal.valueOf(trustId));
			ownerList.add(owner);
			lawfulownerdao.addLawfulOwners(loggedinUser.getUserName(), ownerList);
			
			responseMap.put("status","SUCCESS");
			responseMap.put("message","Record added successfully");				
			try {
				responseJson = objectMapper.writeValueAsString(responseMap);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;

			
		} else {
			responseMap.put("status","ERROR");
			responseMap.put("message","You are not authorized to add users");				
			try {
				responseJson = objectMapper.writeValueAsString(responseMap);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;
		}

	}		
	
	@RequestMapping(value = "/mark-on-hold",  params = {"trustId"})
	public @ResponseBody String markonhold(@RequestParam(value="trustId") long trustId, Model model, HttpSession session) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser != null) {			
			String responseJson = "";
			boolean markOnHoldResult = wqdatadao.MarkOnHold(trustId, loggedinUser.getUserName());
			
			if (markOnHoldResult) {
				responseMap.put("status","SUCCESS");
				responseMap.put("message","Trust number " + wqdatadao.getTrustNumByTrustId(trustId) + " marked on hold");				
			} else {
				responseMap.put("status","ERROR");
				responseMap.put("message","Unable to mark trust on hold");						
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
	
	@RequestMapping(value = "/revise-list")
	public @ResponseBody String reviselist(Model model, HttpSession session) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser != null) {			
			String responseJson = "";
			boolean markRevisedResult = wqdatadao.ReturnPendingItemsToReviewStatus(loggedinUser.getUserName());
			
			if (markRevisedResult) {
				responseMap.put("status","SUCCESS");
				responseMap.put("message","Items successfully moved back to Review tab");				
			} else {
				responseMap.put("status","ERROR");
				responseMap.put("message","Unable to move items back to Review tab");						
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
	
	@RequestMapping(value = "/finalize-list")
	public @ResponseBody String finalizelist(Model model, HttpSession session) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser != null) {			
			String responseJson = "";
			boolean finalizeListResult = wqdatadao.FinalizeList(loggedinUser.getUserName());
			
			if (finalizeListResult) {
				responseMap.put("status","SUCCESS");
				responseMap.put("message","Items successfully added to publication PDF");				
			} else {
				responseMap.put("status","ERROR");
				responseMap.put("message","Unable to add to publication PDF");						
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
	
	@RequestMapping(value = "/sent-for-review",  params = {"trustId","lawfulOwnerId"})
	public @ResponseBody String sentforreview(@RequestParam(value="trustId") long trustId, 
			                                  @RequestParam(value="lawfulOwnerId") long lawfulOwnerId,
			                                  Model model, 
			                                  HttpSession session) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser != null) {			
			String responseJson = "";
			boolean markOnHoldResult = wqdatadao.SentForReview(trustId,lawfulOwnerId,loggedinUser.getUserName());
			
			if (markOnHoldResult) {
				responseMap.put("status","SUCCESS");
				responseMap.put("message","Trust number " + wqdatadao.getTrustNumByTrustId(trustId) + " marked sent for review");				
			} else {
				responseMap.put("status","ERROR");
				responseMap.put("message","Unable to mark trust sent for review");						
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
	
	@RequestMapping(value = "/ready-for-publication", params = {"trustIds"})
	public @ResponseBody String readyforpublication(@RequestParam(value="trustIds") String trustIds, Model model, HttpSession session) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		User loggedinUser = (User) session.getAttribute("user");					
		String responseJson = "";
		Boolean markReadyForPubResult = false;		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.ACCT_ROLE) {
			List<String> trustIdList = Arrays.asList(trustIds.split("\\s*,\\s*"));
			List<WorkQueueData> workqueuedata = new ArrayList<WorkQueueData>();
			
			for (String trustId : trustIdList) {
				workqueuedata.add(wqdatadao.fetchWorkQueueDataByTrustId(Long.parseLong(trustId)));
			}
						
			markReadyForPubResult = wqdatadao.MarkReadyForPublication(workqueuedata,loggedinUser.getUserName());
			
			if (markReadyForPubResult) {
				responseMap.put("status","SUCCESS");
				responseMap.put("message","Selected items marked ready for publication");				
			} else {
				responseMap.put("status","ERROR");
				responseMap.put("message","Unable to mark items ready for publicatoin");						
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
	
	@RequestMapping(path = "/input-request-addl-info", params = {"trustId","lawfulOwnerId"})
	public ModelAndView inputrequestaddlinfo(@RequestParam(value="trustId") long trustId,
											 @RequestParam(value="lawfulOwnerId") long lawfulOwnerId,
											 Model model,
											 HttpSession session) {
		
		
		if (session.getAttribute("owner") != null) {
			model.addAttribute("owner",session.getAttribute("owner"));
		} else {
			LawfulOwner owner = lawfulownerdao.fetchLawfulOwnerByLawfulOwnerId(lawfulOwnerId);
			model.addAttribute("owner",owner);
		}
		
		if (session.getAttribute("formData") != null) {
			model.addAttribute("formData",session.getAttribute("formData"));
		} else {
			model.addAttribute("formData",new FormData());
		}
				
		model.addAttribute("sigMissing",Constants.AFFIRMATION_SIG_MISSING);
		model.addAttribute("notarizationMissing",Constants.NOTARIZATION_MISSING);
		model.addAttribute("photoIdMissing",Constants.PHOTO_ID_MISSING);
		model.addAttribute("insufficientDocumentation",Constants.INSUFFICIENT_DOCUMENTATION);
		model.addAttribute("other",Constants.OTHER_REJECT_REASON);
		
		
		return new ModelAndView("input-request-addl-info");
	}		
	
	@RequestMapping(path = "/review-request-addl-info", params = {"trustId","lawfulOwnerId","nameLine1","nameLine2","addressLine1","addressLine2","addressLine3","claimAmount","claimDate","reason","otherReason"})
	public ModelAndView reviewrequestaddlinfo(@RequestParam(value="trustId") long trustId,
											  @RequestParam(value="lawfulOwnerId") BigDecimal lawfulOwnerId,
											  @RequestParam(value="nameLine1") String nameLine1,
											  @RequestParam(value="nameLine2") String nameLine2,
											  @RequestParam(value="addressLine1") String addressLine1,
											  @RequestParam(value="addressLine2") String addressLine2,
											  @RequestParam(value="addressLine3") String addressLine3,
											  @RequestParam(value="claimAmount") BigDecimal claimAmount,
											  @RequestParam(value="claimDate") String claimDate,
											  @RequestParam(value="reason") int reason,
											  @RequestParam(value="otherReason") String otherReason,
										      Model model,
											  HttpSession session) throws JsonParseException, JsonMappingException, IOException {

		User loggedinUser = (User) session.getAttribute("user");
		LawfulOwner owner = new LawfulOwner();
		FormData formData = new FormData();
		
		//validate data is valid; if not, redirect back to form
		if (nameLine1.length() == 0 ||
			nameLine1 == null || 
			addressLine1.length() == 0 || 
			addressLine1 == null || 
			addressLine3.length() == 0 || 
			addressLine3 == null) {
							
			return new ModelAndView("redirect:/review");				
		} 		
		
		//based on trustId get other required information for form
		//owner = lawfulownerdao.fetchLawfulOwnerByLawfulOwnerId(lawfulOwnerId);
		formData = formdatadao.fetchFormDataByTrustId(trustId, loggedinUser.getUserName());
		formData.setTrustAmount(claimAmount);
		formData.setClaimSubmitDate(claimDate);
		formData.setRejectReason(reason);
		formData.setOtherRejectReason(otherReason);
		
		//set owner data from form, in case user had to modify the pre-populated values
		owner.setAddressLine1(addressLine1);
		owner.setAddressLine2(addressLine2);
		owner.setAddressLine3(addressLine3);
		owner.setNameLine1(nameLine1);
		owner.setNameLine2(nameLine2);	
		owner.setTrustId(BigDecimal.valueOf(trustId));
		owner.setId(lawfulOwnerId);
		
		//save info in session, in case they navigate back to input screen
		session.setAttribute("formData", formData);	
		session.setAttribute("owner", owner);	

		//populate form
		pdf.populateRequestForAddlInfo(owner,formData);		
		
		model.addAttribute("formName","RequestForAddlInfo");
		return new ModelAndView("review-request-addl-info");
	}	

}
