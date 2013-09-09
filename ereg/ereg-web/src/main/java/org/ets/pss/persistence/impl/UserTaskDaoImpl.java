/**
 * 
 */
package org.ets.pss.persistence.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ets.pss.persistence.dao.DocDao;
import org.ets.pss.persistence.dao.UserTaskDao;
import org.ets.pss.persistence.dto.Artifact;
import org.ets.pss.persistence.model.UserTask;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author asampath
 *
 */
@Repository
@Qualifier("userTaskDao")
public class UserTaskDaoImpl implements UserTaskDao {

	@PersistenceContext(unitName="pss")
	private EntityManager em;
	
	/* (non-Javadoc)
	 * @see org.ets.pss.persistence.dao.ArtifactDao#addArtifact(org.ets.pss.persistence.model.Artifact)
	 */
	public void addUserTask(UserTask a) {
		// TODO Auto-generated method stub
		em.persist(a);
		em.flush();

	}

	/* (non-Javadoc)
	 * @see org.ets.pss.persistence.dao.ArtifactDao#removeArtifact(org.ets.pss.persistence.model.Artifact)
	 */
	public void removeUserTask(UserTask a) {
		// TODO Auto-generated method stub
		em.remove(a);
	}

	/* (non-Javadoc)
	 * @see org.ets.pss.persistence.dao.ArtifactDao#getArtifact(java.lang.String)
	 */
	public List<UserTask> getUserTasks(String userId) {
		
		Query q = em.createQuery("SELECT x FROM UserTask x where x.userId=?1");
		q.setParameter(1, userId);
		@SuppressWarnings("unchecked")
		List<UserTask> userTasks = (List<UserTask>) q.getResultList();
		return userTasks;

	}

	/* (non-Javadoc)
	 * @see org.ets.pss.persistence.dao.ArtifactDao#saveArtifact(org.ets.pss.persistence.model.Artifact)
	 */
	public void saveUserTask(UserTask a) {
		// TODO Auto-generated method stub
		em.merge(a);

	}

}
