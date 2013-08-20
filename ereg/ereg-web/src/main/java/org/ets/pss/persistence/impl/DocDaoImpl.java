/**
 * 
 */
package org.ets.pss.persistence.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ets.pss.persistence.dao.DocDao;
import org.ets.pss.persistence.dto.Artifact;
import org.ets.pss.persistence.model.Doc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.io.comparator.NameFileComparator;

/**
 * @author SSINGH007
 *
 */
@Repository
@Qualifier("artifactDao")
public class DocDaoImpl extends GenericDaoImpl<Doc> implements DocDao {
	@PersistenceContext(unitName="pss")
	private EntityManager em;
	
/*	@PersistenceContext
	private EntityManager em;
	
	 (non-Javadoc)
	 * @see org.ets.pss.persistence.dao.ArtifactDao#addArtifact(org.ets.pss.persistence.model.Artifact)
	 
	public void addArtifact(Doc a) {
		// TODO Auto-generated method stub
		em.persist(a);
		//em.flush();

	}

	 (non-Javadoc)
	 * @see org.ets.pss.persistence.dao.ArtifactDao#removeArtifact(org.ets.pss.persistence.model.Artifact)
	 
	public void removeArtifact(long artifactId) throws Exception {
		Artifact a = getArtifact(artifactId);
		if(a != null)
		{
			em.remove(a);
			em.flush();
		}
		else
			throw new Exception("Entity not found");
		
		
	}

	 (non-Javadoc)
	 * @see org.ets.pss.persistence.dao.ArtifactDao#getArtifact(java.lang.String)
	 
	public Artifact getArtifact(long artifactId) {
		Query q = em.createQuery("SELECT x FROM Artifact x where x.id = ?1");
		q.setParameter(1, artifactId);
		@SuppressWarnings("unchecked")
		List<Artifact> artifacts = (List<Artifact>) q.getResultList();
		
		if(artifacts != null && artifacts.size()>0)
		{
			return artifacts.get(0);
		}
		else
		{
			return null;
		}
	}

	 (non-Javadoc)
	 * @see org.ets.pss.persistence.dao.ArtifactDao#saveArtifact(org.ets.pss.persistence.model.Artifact)
	 
	public void saveArtifact(Doc a) {
		// TODO Auto-generated method stub
		em.merge(a);

	}*/
	
	public List<Doc> getCustomerArtifacts(long customerId)
	{
		Query q = em.createQuery("SELECT x FROM Doc x where x.etsCust.customerId=?1");
		q.setParameter(1,customerId);
		@SuppressWarnings("unchecked")
		List<Doc> userTaskArtifacts = (List<Doc>) q.getResultList();
		
		 //Collections.sort(userTaskArtifacts, NameFileComparator.NAME_INSENSITIVE_COMPARATOR);
		
		/*System.out.println("**** before sorting  ascending ***");
		 for(Doc doc: userTaskArtifacts){
			 System.out.println("before sort doc is "+doc.getRspSrcLctnNam());
	        }*/
		
        //Sorting using Anonymous inner class type
        Collections.sort(userTaskArtifacts, new Comparator<Doc>() {
          

			@Override
			public int compare(Doc e1, Doc e2) {
				//parameter are of type Object, so we have to downcast it to Doc objects
				
				String id1 = ((Doc) e1).getRspSrcLctnNam();
                String id2 = ((Doc) e2).getRspSrcLctnNam();
 
                // ascending order
             return id1.toLowerCase().compareTo(id2.toLowerCase());
               
            // descending order
               //return id2.toLowerCase().compareTo(id1.toLowerCase());
                
              
            }
			
			
			
			
			
        });
       /* System.out.println("**** After sorting id accending ***");
        for(Doc doc: userTaskArtifacts){
            System.out.println(" after sort doc is "+doc.getRspSrcLctnNam());
        }*/
		
		
		return new ArrayList<Doc>(userTaskArtifacts);
	}	
	
	public Set<Artifact> getUserArtifactsForTask(long userId,long taskId)
	{
		Query q = em.createQuery("SELECT x FROM Doc x where x.customerId=?1 and x.taskId=?2");
		q.setParameter(1, ""+userId).setParameter(2, taskId);
		@SuppressWarnings("unchecked")
		Set<Artifact> userTaskArtifacts = (Set<Artifact>) q.getResultList();
		return userTaskArtifacts;
	}

	@SuppressWarnings("unchecked")
	public Set<Doc> findAll() {
		Query q = em.createQuery("SELECT x FROM Doc x");
		List<Doc> userTaskArtifacts = (List<Doc>) q.getResultList();
		return new HashSet<Doc>(userTaskArtifacts);
	}


}
