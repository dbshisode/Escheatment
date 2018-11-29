package org.occourts.escheatment.validator;

import javax.servlet.http.HttpSession;

import org.occourts.escheatment.dao.impl.EscheatmentUserDAOImpl;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.util.ActiveDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
* UserValidator contains methods used by Spring to validate form data.  This 
* validator is tied to the index login form
* $Revision: 4565 $     
* $Author: cbarrington $ 
* $Date: 2018-11-07 15:59:56 -0800 (Wed, 07 Nov 2018) $    
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
		char[] pass;
				
		if (!error.hasErrors()) {

			Boolean result = false;
			ActiveDirectory ad = new ActiveDirectory();			
			String userName = user.getUserName();					
			
			try {
				//validate user and password is valid
				pass = user.getPassword().toCharArray();
				if (user.getPassword().equals("p") == false) {
					result = ad.authenticate(userName, pass);
				}				
								
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
			
			//when adding or editing users, no password field is presented to user.  Default value is "p"
			if (result == false && user.getPassword().length() > 0 && user.getPassword().equals("p") == false) {
				error.rejectValue("userName", "user.userName.invalid");
			} else {								
				userValid = true;
			}
		}
	}
}
