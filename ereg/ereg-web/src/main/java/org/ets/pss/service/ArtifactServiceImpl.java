/**
 * 
 */
package org.ets.pss.service;

import java.util.List;
import java.util.Set;

import org.ets.pss.persistence.dao.DocDao;
import org.ets.pss.persistence.model.Doc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SSINGH007
 *
 */
@Transactional(propagation= Propagation.REQUIRED, readOnly=true)
@Service("artifactService")
public class ArtifactServiceImpl implements ArtifactService {

	private static final Logger logger = LoggerFactory
			.getLogger(ArtifactServiceImpl.class);
	


	@Autowired
	@Qualifier("artifactDao")
	DocDao artifactDaoImpl;

	/* (non-Javadoc)
	 * @see org.ets.pss.service.ArtifactService#getCustomerArtifacts(long customerId)
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> getCustomerArtifacts(long customerId) {		
		return  artifactDaoImpl.getCustomerArtifacts(customerId);
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	public String removeArtifact(long docId) {
		Doc doc = artifactDaoImpl.get(Doc.class,docId);
		if(doc != null && doc.getCustCrAsgndTskDocs() != null && doc.getCustCrAsgndTskDocs().size()>0)
		{
			return "Artifact is linked in an Essay. You cannot remove this Artifact.";
		}
		else
		{
			artifactDaoImpl.delete(doc);
			return "success";
		}				
		
	}

	public Doc getDoc(long docId) {
		// TODO Auto-generated method stub		
		Doc doc = artifactDaoImpl.get(Doc.class, docId);
		doc.getMdaMimeTyp().getMimeTypNam();
		return doc;
	}

}
