/**
 * 
 */
package org.ets.pss.persistence.dao;

import java.util.List;
import java.util.Set;

import org.ets.pss.persistence.dto.Artifact;
import org.ets.pss.persistence.model.Doc;

/**
 * @author SSINGH007
 *
 */
public interface DocDao extends GenericDao<Doc> {

/*	void addArtifact(Doc a);
	void removeArtifact(long artifactId) throws Exception;
	Artifact getArtifact(long artifactId);
	void saveArtifact(Doc a);*/
	Set<Doc> findAll();
	List<Doc> getCustomerArtifacts(long customerId);
	//Set<Artifact> getUserArtifactsForTask(long userId,long taskId);
}
