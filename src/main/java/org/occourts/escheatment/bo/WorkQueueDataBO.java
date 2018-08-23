package org.occourts.escheatment.bo;

import org.occourts.escheatment.dao.impl.WorkQueueDataDAOImpl;
import org.occourts.escheatment.model.OpsCount;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkQueueDataBO {
	@Autowired
	WorkQueueDataDAOImpl wqdatadao;
	
	public OpsCount getWorkQueueDataCount() {
		OpsCount opsCount = new OpsCount();
		int opsReviewCount = wqdatadao.getOpsReviewCount();
		int opsActiveCount = wqdatadao.getOpsActiveCount();
		int opsSentCount = wqdatadao.getOpsSentCount();
		opsCount.setOpsActiveCount(opsActiveCount);
		opsCount.setOpsReviewCount(opsReviewCount);
		opsCount.setOpsSentCount(opsSentCount);
		return opsCount;
	}

}
