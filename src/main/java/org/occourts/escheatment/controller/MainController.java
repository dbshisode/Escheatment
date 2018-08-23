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
import org.occourts.escheatment.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}

	@RequestMapping(path = "/")
	public String index(@ModelAttribute("user") User user, Model model) {
		return "index";
	}

	@RequestMapping(value = "/review")
	public ModelAndView review(@ModelAttribute("user") @Validated User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		try {
			if (loggedinUser == null) {
				if (result.hasErrors()) {
					return new ModelAndView("index");
				}
				loggedinUser = escheatmentuserdao.fetchUser(user.getUserName());
				if (loggedinUser!=null && loggedinUser.getUserRole()!=null && loggedinUser.getUserRole().intValue() > 0) {
					session.setAttribute("user", loggedinUser);
				}else {
					return new ModelAndView("index");
				}
			}
			if (loggedinUser!=null && loggedinUser.getUserRole()!=null && loggedinUser.getUserRole().intValue() == UserConstants.OPS_ROLE) {
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
		}catch(Exception E) {
			//log the Error
			return new ModelAndView("genericerror");
		}

	}
	
	@RequestMapping(value = "/active")
	public ModelAndView active(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		User loggedinUser = (User) session.getAttribute("user");		
		
		if (loggedinUser!=null && loggedinUser.getUserRole()!=null && loggedinUser.getUserRole().intValue() == UserConstants.OPS_ROLE) {
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