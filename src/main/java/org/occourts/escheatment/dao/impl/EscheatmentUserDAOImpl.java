package org.occourts.escheatment.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.*;
import org.occourts.escheatment.dao.EscheatmentUserDAO;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.model.WorkQueueData;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

/**
* EscheatmentUserDAOImpl provides methods to return User data
* $Revision: 4519 $     
* $Author: cbarrington $ 
* $Date: 2018-09-04 13:38:45 -0700 (Tue, 04 Sep 2018) $    
*/

public class EscheatmentUserDAOImpl implements EscheatmentUserDAO {
	private DataSource dataSource;
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public JdbcTemplate getJdbcTemplate() {
		return template;
	}
	
	/*public int fetchUserRole(String userName) {
		
		Integer iUserRole = 0;
		String SQL = "select max(user_role) as user_role from escheatment.users where UPPER(user_name) = '"
				+ userName.toUpperCase() + "'";
		iUserRole = (Integer) this.template.queryForObject(SQL, Integer.class);
		Logger log = Logger.getLogger(EscheatmentUserDAOImpl.class);
		log.info("fetchUserRole method was called");		

		if (iUserRole > 0) {
			return iUserRole;			
		} else {
			return 0;
		}

	}*/	
	
	public User fetchUser(int userId) {
		String SQL = "SELECT u.user_name FROM escheatment.users u WHERE u.user_id = ?";
		User user = new User();
		Map row = DataAccessUtils.singleResult(template.queryForList(SQL, new Object[] { userId }));
		if (row != null) {
			user.setUserName((row.get("user_name") != null ? row.get("user_name") : "-").toString());						
		}
		
		return fetchUser(user.getUserName());
	}	

	public User fetchUser(String userName) {
		String SQL = "SELECT u.user_name,\n" + 
				"  u.user_id,\n" +
				"  u.first_name,\n" + 
				"  u.last_name,\n" + 
				"  u.middle_name,\n" + 
				"  u.create_dt,\n" + 
				"  u.create_user_id,\n" + 
				"  u.update_dt,\n" + 
				"  u.update_user_id,\n" + 
				"  u.active,\n" +
				"  (select max(ura.role_id) from escheatment.user_role_assoc ura where ura.user_id = u.user_id and ura.role_id in (1,2)) as functional_area,\n" +
				"  (select max(ura.role_id) from escheatment.user_role_assoc ura where ura.user_id = u.user_id and ura.role_id in (3,4)) as admin_level\n" + 
				"FROM escheatment.users u \n" + 
				"WHERE UPPER(u.user_name) = ?";	
		
		User user = new User();
		Map row = DataAccessUtils.singleResult(template.queryForList(SQL, new Object[] { userName.toUpperCase() }));
		if (row != null) {
			user.setUserName((row.get("user_name") != null ? row.get("user_name") : "-").toString());
			user.setUserId((BigDecimal) (row.get("user_id") != null ? row.get("user_id") : BigDecimal.ZERO));
			user.setUserFunctionalArea((BigDecimal) (row.get("functional_area") != null ? row.get("functional_area") : BigDecimal.ZERO));
			user.setUserRoleAdmin((BigDecimal) (row.get("admin_level") != null ? row.get("admin_level") : BigDecimal.ZERO));
			user.setCreateDate((Timestamp) (row.get("create_dt")));
			user.setCreatedBy((String) (row.get("create_user_id")));
			user.setFirstName((String) (row.get("first_name")));
			user.setMiddleName((row.get("middle_name") != null ? row.get("middle_name") : "").toString());
			user.setLastName((String) (row.get("last_name")));
			user.setActive((String) (row.get("active")));
			user.setUpdateDate((Timestamp) (row.get("update_dt")));
			user.setUpdatedBy((String) (row.get("update_user_id")));			
		}
		Logger log = Logger.getLogger(EscheatmentUserDAOImpl.class);
		log.info("fetchUser method was called");
		return user;
	}

	public List<User> fetchAllUserData() {
		List<User> userDataList = new ArrayList<User>();
		String SQL = "SELECT u.user_name,\n" + 
				"  u.user_id,\n" +
				"  u.first_name,\n" + 
				"  u.last_name,\n" + 
				"  u.middle_name,\n" + 
				"  u.create_dt,\n" + 
				"  u.create_user_id,\n" + 
				"  u.update_dt,\n" + 
				"  u.update_user_id,\n" + 
				"  u.active,\n" +
				"  (select max(ura.role_id) from escheatment.user_role_assoc ura where ura.user_id = u.user_id and ura.role_id in (1,2)) as functional_area,\n" +
				"  (select max(ura.role_id) from escheatment.user_role_assoc ura where ura.user_id = u.user_id and ura.role_id in (3,4)) as admin_level\n" + 
				"FROM escheatment.users u \n" +
				"ORDER BY u.last_name";
		List<Map<String, Object>> rows = template.queryForList(SQL);
		User userData = new User();
		for (Map row : rows) {
			userData = new User();
			userData.setUserName((row.get("user_name") != null ? row.get("user_name") : "-").toString());
			userData.setUserId((BigDecimal) (row.get("user_id") != null ? row.get("user_id") : BigDecimal.ZERO));
			userData.setUserFunctionalArea((BigDecimal) (row.get("functional_area") != null ? row.get("functional_area") : BigDecimal.ZERO));
			userData.setUserRoleAdmin((BigDecimal) (row.get("admin_level") != null ? row.get("admin_level") : BigDecimal.ZERO));
			userData.setCreateDate((Timestamp) (row.get("create_dt")));
			userData.setCreatedBy((String) (row.get("create_user_id")));
			userData.setFirstName((String) (row.get("first_name")));
			userData.setLastName((String) (row.get("last_name")));
			userData.setActive((String) (row.get("active")));
			userData.setMiddleName((row.get("middle_name") != null ? row.get("middle_name") : "").toString());
			userData.setUpdateDate((Timestamp) (row.get("update_dt")));
			userData.setUpdatedBy((String) (row.get("update_user_id")));	
			userDataList.add(userData);
		}
		return userDataList;
	}

	public boolean addUser(final User user, final String createdByUserName) {

		//check that all values are valid
		
		//check if user already exists
		User userCheck = fetchUser(user.getUserName());
		
		if (userCheck.getUserName() == null) {
			
			//user does not already exist, so add them
			final String userInsertSql = "INSERT INTO escheatment.users (user_id,user_name,first_name,middle_name,last_name,active,create_user_id,create_dt,update_user_id,update_dt) values (escheatment.users_seq.nextval,?,?,?,?,?,?,?,?,?)";
			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
			final String idColumn = "USER_ID";
		
			template.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(userInsertSql, new String[] {idColumn});
					ps.setString(1, user.getUserName());
					ps.setString(2, user.getFirstName());
					ps.setString(3, user.getMiddleName());
					ps.setString(4, user.getLastName());
					ps.setString(5, user.getActive());
					ps.setString(6, createdByUserName);
					ps.setDate(7, new java.sql.Date(System.currentTimeMillis()));
					ps.setString(8, createdByUserName);
					ps.setDate(9, new java.sql.Date(System.currentTimeMillis()));				
					return ps;					
				}
				
			}, keyHolder);			
			
			//store user_id assigned to user
			BigDecimal newUserId = (BigDecimal) keyHolder.getKeys().get(idColumn);
			final int newUserIdInt = newUserId.intValue();
			
						
			//now add selected roles
			ArrayList<Integer> roles = new ArrayList<Integer>();
			roles.add(user.getUserFunctionalArea().intValue());
			
			if (user.getUserRoleAdmin() != null) {
				roles.add(user.getUserRoleAdmin().intValue());
			}
			
			addUserRoles(createdByUserName, newUserIdInt, roles);
			
			return true;
		} else {
			//user already exists
			return false;
		}
		
	}

	private void addUserRoles(final String createdByUserName, final int userIdInt, ArrayList<Integer> roles) {
		final String userRoleAssocInsertSql = "INSERT INTO escheatment.user_role_assoc (role_assoc_id,user_id,role_id,create_user_id,create_dt,update_user_id,update_dt) values (escheatment.users_role_assoc_seq.nextval,?,?,?,?,?,?)";
		//loop through to insert to DB
		for (final int role: roles) {
			template.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(userRoleAssocInsertSql);
					ps.setLong(1, userIdInt);
					ps.setInt(2, role);
					ps.setString(3, createdByUserName);
					ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
					ps.setString(5, createdByUserName);
					ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));				
					return ps;					
				}
				
			});
		}
	}
	
	private void deleteUserRoles(final int userIdInt) {
		final String userRoleAssocDeleteSql = "DELETE escheatment.user_role_assoc WHERE user_id = ?";

		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(userRoleAssocDeleteSql);
				ps.setLong(1, userIdInt);				
				return ps;					
			}
			
		});
		
	}	

	public boolean editUser(final User user, final String updatedByUserName) {
		
		final User userCheck = fetchUser(user.getUserId().intValue());
		
		if (userCheck.getUserId().intValue() > 0) {
			
			//user exists, so edit them
			final String userUpdateSql = "UPDATE escheatment.users\n" + 
					"SET user_name = ?,\n" + 
					"    first_name = ?,\n" + 
					"    middle_name = ?,\n" + 
					"    last_name = ?,\n" + 
					"    active = ?,\n" + 
					"    update_dt = ?,\n" + 
					"    update_user_id = ?\n" + 
					"WHERE user_id = ?";
			
			template.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(userUpdateSql);
					ps.setString(1, user.getUserName());
					ps.setString(2, user.getFirstName());
					ps.setString(3, user.getMiddleName());
					ps.setString(4, user.getLastName());
					ps.setString(5, user.getActive());					
					ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));	
					ps.setString(7, updatedByUserName);
					ps.setLong(8, userCheck.getUserId().intValue());
					return ps;					
				}
				
			});		
		}
		
		//delete current user roles
		deleteUserRoles(user.getUserId().intValue());
		
		//now add back only selected roles
		ArrayList<Integer> roles = new ArrayList<Integer>();
		roles.add(user.getUserFunctionalArea().intValue());
		
		if (user.getUserRoleAdmin() != null) {
			roles.add(user.getUserRoleAdmin().intValue());
		}
		
		addUserRoles(updatedByUserName, user.getUserId().intValue(), roles);		
		
		return true;
	}
}