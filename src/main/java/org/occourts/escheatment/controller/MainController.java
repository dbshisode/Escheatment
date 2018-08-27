package org.occourts.escheatment.controller;

import java.util.List;

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
import org.occourts.escheatment.validator.AddUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
* MainController provides mapping and necessary business logic required for navigation 
* within the application, as well as user validation and session management
* $Revision: 4512 $     
* $Author: cbarrington $ 
* $Date: 2018-08-24 15:52:51 -0700 (Fri, 24 Aug 2018) $    
*/

@Controller
public class MainController {

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
	
	@RequestMapping(value = "/admin")
	public ModelAndView admin(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (EscheatmentObject.isAdmin(loggedinUser)) {
			List<User> userdata = escheatmentuserdao.fetchAllUserData();			
			model.addAttribute("userdata", userdata);						
			return new ModelAndView("admin");
		} else {
			return new ModelAndView("index");
		}

	}
	
	@RequestMapping(value = "/admin-add")
	public @ResponseBody ModelAndView adminadd(User user, BindingResult result, Model model, HttpSession session) {
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
	public @ResponseBody ModelAndView adminaddsave(@ModelAttribute("user") @Validated User user, BindingResult result, Model model, HttpSession session) {
		
		if (result.hasErrors() && user.getPassword().length() > 0) {
			model.addAttribute("opsRoleValue",UserConstants.OPS_ROLE);
			model.addAttribute("acctRoleValue",UserConstants.ACCT_ROLE);
			model.addAttribute("funcAdminRoleValue",UserConstants.FUNC_ADMIN_ROLE);					
			return new ModelAndView("admin-add-form");
		}		
		
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (EscheatmentObject.isAdmin(loggedinUser)) {
			
			boolean addUserResult = escheatmentuserdao.addUser(user, loggedinUser.getUserName());			
			
			model.addAttribute("userdata", user);						
			if (addUserResult == true) {
				return new ModelAndView("admin-add-save-success");
			} else {
				return new ModelAndView("admin-add-save-error");
			}
			
		} else {
			return new ModelAndView("index");
		}

	}		
	
	@RequestMapping(value = "/admin-edit", params = {"userId"})
	public @ResponseBody ModelAndView adminedit(@ModelAttribute("user") User user, @RequestParam(value="userId") int userId, BindingResult result, Model model, HttpSession session) {
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
	public @ResponseBody ModelAndView admineditsave(@ModelAttribute("user") @Validated User user, BindingResult result, Model model, HttpSession session) {
		
		if (result.hasErrors() && user.getPassword().length() > 0) {
			model.addAttribute("opsRoleValue",UserConstants.OPS_ROLE);
			model.addAttribute("acctRoleValue",UserConstants.ACCT_ROLE);
			model.addAttribute("funcAdminRoleValue",UserConstants.FUNC_ADMIN_ROLE);		
			model.addAttribute("activeRoleValue",UserConstants.ACTIVE_STATUS);
			return new ModelAndView("admin-add-form");
		}			
		
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (EscheatmentObject.isAdmin(loggedinUser)) {
			
			boolean editUserResult = escheatmentuserdao.editUser(user, loggedinUser.getUserName());			
			
			model.addAttribute("userdata", user);						
			if (editUserResult == true) {
				return new ModelAndView("admin-edit-save-success");
			} else {
				return new ModelAndView("admin-edit-save-error");
			}
			
		} else {
			return new ModelAndView("index");
		}

	}	
	
	

	/*@RequestMapping(value = "/reviewForm", method = RequestMethod.POST)
	public ModelAndView processReviewForm(@RequestParam(value = "reviewForm") WorkQueueData reviewForm,
			HttpServletRequest request, Model model, HttpSession session) {

	    //TODO: perform action; create reviewForm.jsp

		return displayReviewPage(model, session);

	}*/

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView displayLogoutPage(@ModelAttribute("user") User user, Model model, HttpSession session) {

		session.invalidate();
		return new ModelAndView("logout");

	}

}