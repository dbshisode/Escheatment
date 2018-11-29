package org.occourts.escheatment.dao;

import java.util.List;

import org.occourts.escheatment.model.RoaData;

/**
* RoaDataDAO is an interface for Register of Actions data
* $Revision: 4523 $     
* $Author: cbarrington $ 
* $Date: 2018-09-14 09:26:52 -0700 (Fri, 14 Sep 2018) $    
*/

public interface RoaDataDAO {

	public List<RoaData> fetchRoaData(String caseNum, String date1, String date2);	
	
}