package org.occourts.escheatment.dao;

import org.occourts.escheatment.model.Publication;

public interface PublicationDAO {

	public long addPublication(Publication publication);

	public Publication findPublication(long publicationId);

}
