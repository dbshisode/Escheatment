package org.occourts.escheatment.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.occourts.escheatment.bo.WorkQueueDataBO;
import org.occourts.escheatment.dao.impl.CommentsDAOImpl;
import org.occourts.escheatment.dao.impl.EscheatmentUserDAOImpl;
import org.occourts.escheatment.dao.impl.FormDataDAOImpl;
import org.occourts.escheatment.dao.impl.LawfulOwnerDAOImpl;
import org.occourts.escheatment.dao.impl.RoaDataDAOImpl;
import org.occourts.escheatment.dao.impl.WorkQueueDataDAOImpl;
import org.occourts.escheatment.model.FormData;
import org.occourts.escheatment.model.LawfulOwner;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.model.WorkQueueData;
import org.occourts.escheatment.Constants;
import org.occourts.escheatment.util.PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
* OperationsController provides mapping and necessary business logic required for users with the Operations role
* $Revision: 4536 $     
* $Author: cbarrington $ 
* $Date: 2018-10-15 10:38:00 -0700 (Mon, 15 Oct 2018) $    
*/

@Controller
public class OperationsController {

	@Autowired
	@Qualifier("messageSourceAccessor")
	private MessageSourceAccessor messageSourceAccessor;
	
	@Autowired
	PdfUtil pdf;	
	
	@Autowired
	LawfulOwnerDAOImpl lawfulownerdao;
	
	@Autowired
	FormDataDAOImpl formdatadao;
	
	@Autowired
	CommentsDAOImpl commentsdao;	
	
	@Autowired
	EscheatmentUserDAOImpl escheatmentuserdao;

	@Autowired
	WorkQueueDataDAOImpl wqdatadao;	
	
	@Autowired
	private WorkQueueDataBO workqueuedatabo;	
	
	@Autowired
	RoaDataDAOImpl roadatadao;	
	
	@RequestMapping(value = "/get-ops-review-table")
	public @ResponseBody String getopsreviewtable(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.OPS_ROLE) {
			List<WorkQueueData> workqueuedata = wqdatadao.fetchOpsReviewData(Constants.MARKED_AS_ACTIVE_NO,Constants.IDENTIFIED_FOR_ESCHEATMENT_STATUS_ID);
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
	
	@RequestMapping(value = "/active")
	public ModelAndView active(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.OPS_ROLE) {
			model.addAttribute("opsCount", workqueuedatabo.getOpsWorkQueueDataCount());
			model.addAttribute("tab1State","btn-default");
			model.addAttribute("tab2State","btn-primary");
			model.addAttribute("tab3State","btn-default");		
			model.addAttribute("func_admin_role",Constants.FUNC_ADMIN_ROLE);
			model.addAttribute("user",loggedinUser);
			return new ModelAndView("active-ops");
		} else {
			return new ModelAndView("active-acct");
		}

	}	
	

	@RequestMapping(value = "/notice-sent")
	public ModelAndView noticesent(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.OPS_ROLE) {
			//List<WorkQueueData> workqueuedata = wqdatadao.fetchOpsReviewData(Constants.MARKED_AS_ACTIVE_NO,Constants.NOTICE_OF_UNCLAIMED_FUNDS_SENT_STATUS_ID);
			model.addAttribute("opsCount", workqueuedatabo.getOpsWorkQueueDataCount());
			//model.addAttribute("workqueuedata", workqueuedata);
			model.addAttribute("tab1State","btn-default");
			model.addAttribute("tab2State","btn-default");
			model.addAttribute("tab3State","btn-primary");
			model.addAttribute("func_admin_role",Constants.FUNC_ADMIN_ROLE);
			model.addAttribute("user",loggedinUser);
			return new ModelAndView("notice-sent-ops");
		} else {
			return new ModelAndView("active-acct");
		}

	}	
	
	@RequestMapping(value = "/get-ops-active-table")
	public @ResponseBody String getopsactivetable(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.OPS_ROLE) {
			List<WorkQueueData> workqueuedata = wqdatadao.fetchOpsReviewData(Constants.MARKED_AS_ACTIVE_YES,Constants.IDENTIFIED_FOR_ESCHEATMENT_STATUS_ID);
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
	
	@RequestMapping(value = "/get-ops-notice-sent-table")
	public @ResponseBody String getopsnoticesenttable(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == Constants.OPS_ROLE) {
			List<WorkQueueData> workqueuedata = wqdatadao.fetchOpsReviewData(Constants.MARKED_AS_ACTIVE_NO,Constants.NOTICE_OF_UNCLAIMED_FUNDS_SENT_STATUS_ID);
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
		
	@RequestMapping(value = "/mark-active",  params = {"trustId"})
	public @ResponseBody String markactive(@ModelAttribute("user") User user, @RequestParam(value="trustId") long trustId, BindingResult result, Model model, HttpSession session) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser != null) {			
			String responseJson = "";
			boolean markAsActiveResult = wqdatadao.MarkAsActive(trustId, loggedinUser.getUserName());
			
			if (markAsActiveResult) {
				responseMap.put("status","SUCCESS");
				responseMap.put("message","Trust number " + wqdatadao.getTrustNumByTrustId(trustId) + " marked as active");				
			} else {
				responseMap.put("status","ERROR");
				responseMap.put("message","Unable to mark trust as active");						
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
	
	@RequestMapping(value = "/mark-identified-for-escheatment",  params = {"trustId"})
	public @ResponseBody String markidentifiedforescheatment(@ModelAttribute("user") User user, @RequestParam(value="trustId") long trustId, BindingResult result, Model model, HttpSession session) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser != null) {			
			String responseJson = "";
			boolean markAsActiveResult = wqdatadao.MarkIdentifiedForEscheatment(trustId, loggedinUser.getUserName());
			
			if (markAsActiveResult) {
				responseMap.put("status","SUCCESS");
				responseMap.put("message","Trust number " + wqdatadao.getTrustNumByTrustId(trustId) + " marked as Identified for Escheatment");				
			} else {
				responseMap.put("status","ERROR");
				responseMap.put("message","Unable to mark trust as Identified for Escheatment");						
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
	
	@RequestMapping(path = "/input-notice-unclaimed-funds", params = {"trustId"})
	public ModelAndView inputnoticeunclaimedfunds(@ModelAttribute("lawfulOwnerForm") LawfulOwner owner, @RequestParam(value="trustId") long trustId, Model model) {
		
		List<LawfulOwner> owners = lawfulownerdao.fetchAllLawfulOwnersByTrustId(trustId);
		
		//if no data is found for this trust, return list of four null owner objects so that four input row is displayed in form
		if (owners.isEmpty()) {
			LawfulOwner nullOwner = new LawfulOwner();
			nullOwner.setNameLine1("");
			owners.add(nullOwner);
		}
		
		model.addAttribute("lawfulOwner", owners);
		
		return new ModelAndView("input-notice-unclaimed-funds");
	}	
	
	
	@RequestMapping(path = "/review-notice-unclaimed-funds")
	public ModelAndView reviewnoticeunclaimedfunds(@ModelAttribute("lawfulOwnerForm") LawfulOwner formOwner,
			@RequestParam("formJSON") String formJson,			
			Model model,
			HttpSession session) throws JsonParseException, JsonMappingException, IOException {
		
		BigDecimal clockIndex = BigDecimal.ONE;
		User loggedinUser = (User) session.getAttribute("user");
		FormData formData = formdatadao.fetchFormDataByTrustId(formOwner.getTrustId().longValue(), loggedinUser.getUserName());

		//parse JSON into List
		ObjectMapper objectMapper = new ObjectMapper();
		List<LawfulOwner> lawfulOwners = objectMapper.readValue(formJson, new TypeReference<List<LawfulOwner>>(){});		
		
		//loop through list
		for (LawfulOwner owner : lawfulOwners) {
			
			//validate data is valid; if not, redirect back to form
			if (owner.getNameLine1().length() == 0 ||
					owner.getNameLine1() == null || 
					owner.getAddressLine1().length() == 0 || 
					owner.getAddressLine1() == null || 
					owner.getAddressLine3().length() == 0 || 
					owner.getAddressLine3() == null) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("trustId",owner.getTrustId());
				return new ModelAndView("redirect:/send-notice-unclaimed-funds",  map);				
			} 
			
			owner.setTrustId(lawfulOwners.get(0).getTrustId());
			owner.setClockIndex(clockIndex);
			clockIndex = clockIndex.add(BigDecimal.ONE);
		}

		//purge existing lawful owner info for this trustId
		lawfulownerdao.deleteAllLawfulOwnersByTrustId(lawfulOwners.get(0).getTrustId().longValue());
		
		//insert new data
		lawfulownerdao.addLawfulOwners(loggedinUser.getUserName(),lawfulOwners);

		pdf.populateNoticeOfUnclaimedFunds(lawfulOwners,formData);		
		
		model.addAttribute("formName","NoticeOfUnclaimedFunds");
		return new ModelAndView("review-notice-unclaimed-funds");
	}	


}