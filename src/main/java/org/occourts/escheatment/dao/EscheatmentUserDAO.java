package org.occourts.escheatment.dao;

import java.util.List;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.model.WorkQueueData;

/**
* EscheatmentUserDAO is an interface for User data
* $Revision: 4511 $     
* $Author: cbarrington $ 
* $Date: 2018-08-24 08:26:17 -0700 (Fri, 24 Aug 2018) $    
*/

public interface EscheatmentUserDAO {
	public User fetchUser(String userName);
	public User fetchUser(int userId);
	public List<User> fetchAllUserData();
	public boolean addUser(User user, String createdByUserName);
	public boolean editUser(User user, String updatedByUserName);
}
