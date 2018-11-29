package org.occourts.escheatment.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.occourts.escheatment.dao.CommentsDAO;
import org.occourts.escheatment.model.Comments;
import org.occourts.escheatment.model.LawfulOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

/**
* CommentsDAOImpl contains methods that query the database for all data necessary to 
* display the content of the comments for a trust
* $Revision: 4523 $     
* $Author: cbarrington $ 
* $Date: 2018-09-14 09:26:52 -0700 (Fri, 14 Sep 2018) $    
*/

public class CommentsDAOImpl implements CommentsDAO {

	private DataSource dataSource;
	JdbcTemplate template;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public List<Comments> fetchCommentData(Long trustId) {		
		
		String SQL = "select c.comment_id, \n" + 
				"       c.comment_dt, \n" + 
				"       c.comment_text, \n" + 
				"       c.trust_id, \n" + 
				"       c.user_id, \n" + 
				"       u.first_name || ' ' || u.last_name as user_name, \n" + 
				"       (select ura.role_id from escheatment.user_role_assoc ura where ura.user_id = u.user_id and ura.role_id in (1,2)) role," +
				"       c.create_user_id, \n" + 
				"       c.create_dt, \n" + 
				"       c.update_user_id, \n" + 
				"       c.update_dt \n" + 
				"from escheatment.comments c, \n" + 
				"     escheatment.users u \n" + 
				"where c.user_id = u.user_id and \n" + 
				"      c.trust_id = ? \n" +
				"order by c.comment_id desc";
		List<Map<String, Object>> rows = template.queryForList(SQL, new Object[] { trustId });
		Comments commentData = new Comments();
		List<Comments> commentList = new ArrayList<Comments>();
		for (Map row : rows) {
			commentData = new Comments();
			commentData.setCommentId((BigDecimal)(row.get("comment_id")));			
			commentData.setCommentDate((Timestamp)(row.get("comment_dt")));
			commentData.setCommentText((String)(row.get("comment_text")));
			commentData.setTrustId((BigDecimal)(row.get("trust_id")));
			commentData.setUserId((BigDecimal)(row.get("user_id")));
			commentData.setUserCommentRole((BigDecimal)(row.get("role")));			
			commentData.setUserName((String)(row.get("user_name")));
			commentData.setCreateUserId((String)(row.get("create_user_id")));
			commentData.setCreateDt((Timestamp) (row.get("create_dt")));
			commentData.setUpdateUserId((String)(row.get("update_user_id")));
			commentData.setUpdateDt((Timestamp) (row.get("update_dt")));
			commentList.add(commentData);
		}
		return commentList;
	}

	public boolean addComment(final Comments comment) {
		
		final String commentInsertSql = "Insert into ESCHEATMENT.COMMENTS (COMMENT_ID,COMMENT_DT,COMMENT_TEXT,TRUST_ID,USER_ID,CREATE_USER_ID,CREATE_DT,UPDATE_USER_ID,UPDATE_DT) values (escheatment.comments_seq.nextval,?,?,?,?,?,?,?,?)";

		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(commentInsertSql);
				ps.setTimestamp(1,comment.getCommentDate());
				ps.setString(2, comment.getCommentText());
				ps.setBigDecimal(3, comment.getTrustId());
				ps.setBigDecimal(4, comment.getUserId());
				ps.setString(5, comment.getCreateUserId());
				ps.setString(5, comment.getUserName());
				ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(7, comment.getUserName());
				ps.setDate(8, new java.sql.Date(System.currentTimeMillis()));								
				return ps;					
			}				
		});
		
		
		return true;		
		
	}	

}
