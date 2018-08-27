package org.occourts.escheatment.validator;

import javax.servlet.http.HttpSession;

import org.occourts.escheatment.dao.impl.EscheatmentUserDAOImpl;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.util.ActiveDirectory;
import org.occourts.escheatment.util.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
* UserValidator contains methods used by Spring to validate form data.  This 
* validator is tied to the index login form
* $Revision: 4512 $     
* $Author: cbarrington $ 
* $Date: 2018-08-24 15:52:51 -0700 (Fri, 24 Aug 2018) $    
*/

@Component
public class UserValidator implements Validator {
	
	@Autowired
	EscheatmentUserDAOImpl escheatmentuserdao;	

	// @Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	// @Override
	public void validate(Object obj, Errors error) {

		ValidationUtils.rejectIfEmpty(error, "userName", "user.userName.empty");
		ValidationUtils.rejectIfEmpty(error, "password", "user.password.empty");
		ValidationUtils.rejectIfEmpty(error, "firstName", "addUser.firstName.empty");
		ValidationUtils.rejectIfEmpty(error, "lastName", "addUser.lastName.empty");

		User user = (User) obj;
		boolean userValid = false;

		if (!error.hasErrors()) {

			Boolean result = false;
			ActiveDirectory ad = new ActiveDirectory();
			char[] pass = user.getPassword().toCharArray();
			String userName = user.getUserName();					
			
			try {
				//validate user and password is valid
				
				result = ad.authenticate(userName, pass);
								
				//if user name and password is valid, verify user has a role defined (default value should be zero, not null)
				if (result == true) {
					User userValidate = escheatmentuserdao.fetchUser(userName);
					if (userValidate.getUserFunctionalArea() == null || userValidate.getActive() == null) {
						result = false;
					}					
				}

				
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result == false && user.getPassword().length() > 0) {
				error.rejectValue("userName", "user.userName.invalid");
			} else {								
				userValid = true;
			}
		}
	}
}
