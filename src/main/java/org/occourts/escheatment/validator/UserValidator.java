package org.occourts.escheatment.validator;

import org.occourts.escheatment.dao.impl.EscheatmentUserDAOImpl;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.util.ActiveDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author
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

		User user = (User) obj;
		boolean userValid = false;

		if (!error.hasErrors()) {

			Boolean result = false;
			ActiveDirectory ad = new ActiveDirectory();
			char[] pass = user.getPassword().toCharArray();
			try {
				//validate user and password is valid
				//result = ad.authenticate(user.getUserName(), pass);
				result = true;
								
				//if user name and password is valid, verify user has a role defined
				//int userRole = user.getUserRole();
				//if (userRole == 0) {
				//	result = false;
				//}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result == false) {
				error.rejectValue("userName", "user.userName.invalid");
			} else {								
				userValid = true;
			}
		}
	}
}
