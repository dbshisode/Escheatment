package org.occourts.escheatment.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.occourts.escheatment.dao.EscheatmentUserDAO;
import org.occourts.escheatment.model.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
/**
 * $Rev$:     Revision of last commit
 * $Author$:  Author of last commit
 * $Date$:    Date of last commit
 */

public class EscheatmentUserDAOImpl implements EscheatmentUserDAO {
	static String mockedUser = "{\r\n" + 
			"    \"user\": [\r\n" + 
			"         {\r\n" + 
			"              \"user_name\": \"dshisode\",\r\n" + 
			"              \"role_id\": \"1\",\r\n" + 
			"              \"create_user_id\": \"51\",\r\n" + 
			"              \"create_dt\": \"01-01-2018\",\r\n" + 
			"              \"first_name\": \"Deepak\",\r\n" + 
			"              \"last_name\": \"Shisode\",\r\n" + 
			"              \"middle_name\": \"B\",\r\n" + 
			"              \"update_dt\": \"01-01-2018\",\r\n" + 
			"              \"update_user_id\": \"51\"\r\n" + 
			"         }\r\n" + 
			"    ]\r\n" + 
			"}";
	private DataSource dataSource;
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public JdbcTemplate getJdbcTemplate() {
		return template;
	}
	
/**
 * 
 */
	public User fetchUser(String userName) throws Exception {
		/*
		String SQL = "SELECT ura.role_id,\n" + 
				 "  u.user_name,\n" + 
				 "  u.first_name,\n" + 
				 "  u.last_name,\n" + 
				 "  u.middle_name,\n" + 
				 "  u.create_dt,\n" + 
				 "  u.create_user_id,\n" + 
				 "  u.update_dt,\n" + 
				 "  u.update_user_id\n" + 
				 "FROM escheatment.users u,\n" + 
				 "     escheatment.user_role_assoc ura\n" + 
				 "WHERE ura.user_id = u.user_id and\n" + 
				 "      UPPER(u.user_name) = ?";
	User user = new User();
	Map row = DataAccessUtils.singleResult(template.queryForList(SQL, new Object[] { userName.toUpperCase() }));
	if (row != null) {
		user.setUserName((row.get("user_name") != null ? row.get("user_name") : "-").toString());
		user.setUserRole((BigDecimal) row.get("role_id"));
		user.setCreateDate((Timestamp) (row.get("create_dt")));
		user.setCreatedBy((String) (row.get("create_user_id")));
		user.setFirstName((String) (row.get("first_name")));
		user.setLastName((String) (row.get("last_name")));
		user.setMiddleName((String) (row.get("middle_name")));
		user.setUpdateDate((Timestamp) (row.get("update_dt")));
		user.setUpdatedBy((String) (row.get("update_user_id")));			
	}
	*/
		User user = new User();
		JSONObject obj = new JSONObject(mockedUser);
		JSONArray arr = obj.getJSONArray("user");

		if (arr.length() > 0) {
			user.setUserName((arr.getJSONObject(0).getString("user_name") != null ? arr.getJSONObject(0).getString("user_name") : "-").toString());
			user.setUserRole(arr.getJSONObject(0).getBigDecimal("role_id"));
			user.setCreateDate(new Timestamp(0));
			user.setCreatedBy((String) (arr.getJSONObject(0).getString("create_user_id")));
			user.setFirstName((String) (arr.getJSONObject(0).getString("first_name")));
			user.setLastName((String) (arr.getJSONObject(0).getString("last_name")));
			user.setMiddleName((String) (arr.getJSONObject(0).getString("middle_name")));
			user.setUpdateDate(new Timestamp(0));
			user.setUpdatedBy((String) (arr.getJSONObject(0).getString("update_user_id")));			
		}
	Logger log = Logger.getLogger(EscheatmentUserDAOImpl.class);
	log.info("fetchUser method was called");
	return user;
	}
}