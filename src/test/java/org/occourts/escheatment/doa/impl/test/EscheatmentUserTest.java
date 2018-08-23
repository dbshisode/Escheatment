package org.occourts.escheatment.doa.impl.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.occourts.escheatment.dao.EscheatmentUserDAO;
import org.occourts.escheatment.dao.impl.EscheatmentUserDAOImpl;
import org.occourts.escheatment.model.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class EscheatmentUserTest {

	@Test
	public void fetchUserRole_should_return_user_object() {

		// setup
		JdbcTemplate mockTemplate = Mockito.mock(JdbcTemplate.class);
		EscheatmentUserDAOImpl escheatmentuserdao = new EscheatmentUserDAOImpl();
		escheatmentuserdao.setTemplate(mockTemplate);		
		User user = Mockito.mock(User.class);
		user.setUserRole(BigDecimal.ONE);
		List<User> list = new ArrayList<User>();
		//list.add(user);
		
		Mockito.when(mockTemplate.queryForList(anyString(),eq(User.class))).thenReturn(list);

		// execution
		list.add(escheatmentuserdao.fetchUser("test"));
		
		// verification
		Assert.isNonEmpty(list);
		Assert.valueIsAtLeast(list.get(0).getUserRole().intValue(), 1);
	}

}
