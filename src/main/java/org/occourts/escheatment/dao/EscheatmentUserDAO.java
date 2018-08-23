package org.occourts.escheatment.dao;

import java.util.List;
import org.occourts.escheatment.model.User;

public interface EscheatmentUserDAO {
	public User fetchUser(String userName) throws Exception;
}
