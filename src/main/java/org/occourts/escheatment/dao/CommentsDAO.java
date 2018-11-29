package org.occourts.escheatment.dao;

import java.util.List;

import org.occourts.escheatment.model.Comments;

/**
* CommentsDAO is an interface for comment data
* $Revision: 4523 $     
* $Author: cbarrington $ 
* $Date: 2018-09-14 09:26:52 -0700 (Fri, 14 Sep 2018) $    
*/

public interface CommentsDAO {

	public List<Comments> fetchCommentData(Long trustId);	
	public boolean addComment(Comments comment);
	
}