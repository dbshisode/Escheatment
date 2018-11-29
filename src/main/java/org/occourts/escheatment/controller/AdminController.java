package org.occourts.escheatment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.occourts.escheatment.dao.impl.EscheatmentUserDAOImpl;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.Constants;
import org.occourts.escheatment.util.EscheatmentObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
* AdminController provides mapping and necessary business logic required for the Admin 
* section of the application
* $Revision: 4536 $     
* $Author: cbarrington $ 
* $Date: 2018-10-15 10:38:00 -0700 (Mon, 15 Oct 2018) $    
*/

@Controller
public class AdminController {

	@Autowired
	@Qualifier("messageSourceAccessor")
	private MessageSourceAccessor messageSourceAccessor;

	@Autowired
	EscheatmentUserDAOImpl escheatmentuserdao;
	
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
			model.addAttribute("opsRoleValue",Constants.OPS_ROLE);
			model.addAttribute("acctRoleValue",Constants.ACCT_ROLE);
			model.addAttribute("funcAdminRoleValue",Constants.FUNC_ADMIN_ROLE);			
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
			
			model.addAttribute("opsRoleValue",Constants.OPS_ROLE);
			model.addAttribute("acctRoleValue",Constants.ACCT_ROLE);
			model.addAttribute("funcAdminRoleValue",Constants.FUNC_ADMIN_ROLE);
			model.addAttribute("activeRoleValue",Constants.ACTIVE_STATUS);		
			
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
	
}