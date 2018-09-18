package org.occourts.escheatment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.occourts.escheatment.bo.WorkQueueDataBO;
import org.occourts.escheatment.dao.impl.EscheatmentUserDAOImpl;
import org.occourts.escheatment.dao.impl.WorkQueueDataDAOImpl;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.model.WorkQueueData;
import org.occourts.escheatment.util.UserConstants;
import org.occourts.escheatment.util.EscheatmentObject;
import org.occourts.escheatment.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
* MainController provides mapping and necessary business logic required for navigation 
* within the application, as well as user validation and session management
* $Revision: 4523 $     
* $Author: cbarrington $ 
* $Date: 2018-09-14 09:26:52 -0700 (Fri, 14 Sep 2018) $    
*/

@Controller
public class MainController {

	@Autowired
	@Qualifier("messageSourceAccessor")
	private MessageSourceAccessor messageSourceAccessor;
	
	@Autowired
	EscheatmentUserDAOImpl escheatmentuserdao;

	@Autowired
	WorkQueueDataDAOImpl wqdatadao;	

	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private WorkQueueDataBO workqueuedatabo;	

	@InitBinder("user")
	protected void initUserBinder(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}

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
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == UserConstants.OPS_ROLE) {
			List<WorkQueueData> workqueuedata = wqdatadao.fetchOpsReviewData();
			model.addAttribute("opsCount", workqueuedatabo.getWorkQueueDataCount());
			model.addAttribute("workqueuedata", workqueuedata);
			model.addAttribute("tab1State","btn-primary");
			model.addAttribute("tab2State","btn-default");
			model.addAttribute("tab3State","btn-default");			
			return new ModelAndView("review-ops");
		} else {
			return new ModelAndView("review-acct");
		}

	}
	
	@RequestMapping(value = "/get-ops-table")
	public @ResponseBody String getopstable(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (EscheatmentObject.isAdmin(loggedinUser)) {
			List<WorkQueueData> workqueuedata = wqdatadao.fetchOpsReviewData();
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
		
		if (loggedinUser!=null && loggedinUser.getUserFunctionalArea()!=null && loggedinUser.getUserFunctionalArea().intValue() == UserConstants.OPS_ROLE) {
			List<WorkQueueData> workqueuedata = wqdatadao.fetchOpsReviewData();
			model.addAttribute("opsCount", workqueuedatabo.getWorkQueueDataCount());
			model.addAttribute("workqueuedata", workqueuedata);
			model.addAttribute("tab1State","btn-default");
			model.addAttribute("tab2State","btn-primary");
			model.addAttribute("tab3State","btn-default");			
			return new ModelAndView("active-ops");
		} else {
			return new ModelAndView("active-acct");
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
	
	@RequestMapping(value = "/admin")
	public ModelAndView admin(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (EscheatmentObject.isAdmin(loggedinUser)) {
			
			//JSP (non-ajax) way of displaying data
			//List<User> userdata = escheatmentuserdao.fetchAllUserData();			
			//model.addAttribute("userdata", userdata);							
			//model.addAttribute("adduser", user);		
			//model.addAttribute("opsRoleValue",UserConstants.OPS_ROLE);
			//model.addAttribute("acctRoleValue",UserConstants.ACCT_ROLE);
			//model.addAttribute("funcAdminRoleValue",UserConstants.FUNC_ADMIN_ROLE);	
			
			return new ModelAndView("admin");
		} else {
			return new ModelAndView("index");
		}

	}
	
	@RequestMapping(value = "/get-user-table")
	public @ResponseBody String getusertable(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (EscheatmentObject.isAdmin(loggedinUser)) {
			List<User> userdata = escheatmentuserdao.fetchAllUserData();	
			String responseJson = "";
			ObjectMapper objectMapper = new ObjectMapper();			
			try {
				responseJson = objectMapper.writeValueAsString(userdata);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;
		} else {
			return null;
		}

	}	
	
	@RequestMapping(value = "/admin-add")
	public ModelAndView adminadd(User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (EscheatmentObject.isAdmin(loggedinUser)) {
			model.addAttribute("adduser", user);		
			model.addAttribute("opsRoleValue",UserConstants.OPS_ROLE);
			model.addAttribute("acctRoleValue",UserConstants.ACCT_ROLE);
			model.addAttribute("funcAdminRoleValue",UserConstants.FUNC_ADMIN_ROLE);			
			return new ModelAndView("admin-add-form");
		} else {
			return new ModelAndView("index");
		}

	}	
	
	@RequestMapping(value = "/admin-add-save")
	public @ResponseBody String adminaddsave(@ModelAttribute("user") @Validated @RequestBody User user, BindingResult result, Model model, HttpSession session) {
		
		Map<String,Object> responseMap = new HashMap<String,Object>();
		Map<String,Object> responseErrorMap = new HashMap<String,Object>();
		String responseJson = "";
		ObjectMapper objectMapper = new ObjectMapper();
				
		if (result.hasErrors()) {
			
			responseMap.put("status","VALIDATION_ERROR");			
			
			List<FieldError> errors = result.getFieldErrors();
		    for (FieldError error : errors ) {
		    	responseErrorMap.put("errorMsg" + error.hashCode(),messageSourceAccessor.getMessage(error));		    	
		    }
		    responseMap.put("errorMsgs",responseErrorMap);
		    
			try {
				responseJson = objectMapper.writeValueAsString(responseMap);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;
		}		
		
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (EscheatmentObject.isAdmin(loggedinUser)) {
			
			boolean addUserResult = escheatmentuserdao.addUser(user, loggedinUser.getUserName());			
			
			model.addAttribute("userdata", user);						
			if (addUserResult == true) {
				
				responseMap.put("status","SUCCESS");
				responseMap.put("message","User added successfully");				
				try {
					responseJson = objectMapper.writeValueAsString(responseMap);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block				
				}
				return responseJson;
			} else {
				responseMap.put("status","ERROR");
				responseMap.put("message","User name already exists");				
				try {
					responseJson = objectMapper.writeValueAsString(responseMap);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block				
				}
				return responseJson;
			}
			
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
	
	@RequestMapping(value = "/admin-edit", params = {"userId"})
	public ModelAndView adminedit(@ModelAttribute("user") User user, @RequestParam(value="userId") int userId, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (EscheatmentObject.isAdmin(loggedinUser)) {
			User userdata = escheatmentuserdao.fetchUser(userId);
			model.addAttribute("user", userdata);	
			
			model.addAttribute("opsRoleValue",UserConstants.OPS_ROLE);
			model.addAttribute("acctRoleValue",UserConstants.ACCT_ROLE);
			model.addAttribute("funcAdminRoleValue",UserConstants.FUNC_ADMIN_ROLE);
			model.addAttribute("activeRoleValue",UserConstants.ACTIVE_STATUS);		
			
			return new ModelAndView("admin-edit-form");
		} else {
			return new ModelAndView("index");
		}

	}		
		
	@RequestMapping(value = "/admin-edit-save")
	public @ResponseBody String admineditsave(@ModelAttribute("user") @Validated @RequestBody User user, BindingResult result, Model model, HttpSession session) {
		
		Map<String,Object> responseMap = new HashMap<String,Object>();
		Map<String,Object> responseErrorMap = new HashMap<String,Object>();
		String responseJson = "";
		ObjectMapper objectMapper = new ObjectMapper();
				
		if (result.hasErrors()) {
			
			responseMap.put("status","VALIDATION_ERROR");			
			
			List<FieldError> errors = result.getFieldErrors();
		    for (FieldError error : errors ) {
		    	responseErrorMap.put("errorMsg" + error.hashCode(),messageSourceAccessor.getMessage(error));		    	
		    }
		    responseMap.put("errorMsgs",responseErrorMap);
		    
			try {
				responseJson = objectMapper.writeValueAsString(responseMap);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block				
			}
			return responseJson;
		}			
		
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (EscheatmentObject.isAdmin(loggedinUser)) {
			
			boolean editUserResult = escheatmentuserdao.editUser(user, loggedinUser.getUserName());			
			
			model.addAttribute("userdata", user);						
			if (editUserResult == true) {
				
				responseMap.put("status","SUCCESS");
				responseMap.put("message","User updated successfully");				
				try {
					responseJson = objectMapper.writeValueAsString(responseMap);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block				
				}
				return responseJson;
			} else {
				responseMap.put("status","ERROR");
				responseMap.put("message","User name already exists");				
				try {
					responseJson = objectMapper.writeValueAsString(responseMap);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block				
				}
				return responseJson;
			}
			
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
	
	@RequestMapping(path = "/send-notice-unclaimed-funds")
	public String sendnoticeunclaimedfunds(@ModelAttribute("user") User user, Model model) {
		return "send-notice-unclaimed-funds";
	}		
	
	
	@RequestMapping(path = "/review-notice-unclaimed-funds")
	public String reviewnoticeunclaimedfunds(@ModelAttribute("user") User user, Model model) {
		return "review-notice-unclaimed-funds";
	}	
	
	@RequestMapping(path = "/comments")
	public String comments(@ModelAttribute("user") User user, Model model) {
		return "comments";
	}
	
	@RequestMapping(path = "/roa")
	public String roa(@ModelAttribute("user") User user, Model model) {
		return "roa";
	}	
	
	@RequestMapping(path = "/roa-left-frame")
	public String roaleftframe(@ModelAttribute("user") User user, Model model) {
		return "roa-left-frame";
	}	
	
	@RequestMapping(path = "/roa-right-frame")
	public String roarightframe(@ModelAttribute("user") User user, Model model) {
		return "roa-right-frame";
	}	
	
	@RequestMapping(path = "/roa-get-data")
	public String roagetdata(@ModelAttribute("user") User user, Model model) {
		return "roa-get-data";
	}	
	

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView displayLogoutPage(@ModelAttribute("user") User user, Model model, HttpSession session) {

		session.invalidate();
		return new ModelAndView("logout");

	}

}