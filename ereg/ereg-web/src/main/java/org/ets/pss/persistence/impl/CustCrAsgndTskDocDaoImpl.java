/**
 * 
 */
package org.ets.pss.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import org.ets.pss.persistence.dao.CustCrAsgndTskDocDao;
import org.ets.pss.persistence.model.CrBlb;
import org.ets.pss.persistence.model.CustCrAsgndTskDoc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author SSINGH007
 *
 */
@Repository
@Qualifier("custCrAsgndTskDocDao")
public class CustCrAsgndTskDocDaoImpl extends GenericDaoImpl<CustCrAsgndTskDoc> implements CustCrAsgndTskDocDao {

	public List<Long> getCurrentDocsForPrompt(long promptId,long taskId,long custId)
	{
		List<Long> docList = new ArrayList<Long>();
		
		@SuppressWarnings("unchecked")
		List<CustCrAsgndTskDoc> custCrAsgndTskDocList = 
	    		(List<CustCrAsgndTskDoc>) em().createNativeQuery("select * from cust_cr_asgnd_tsk_doc c where customer_id=?1 and prmpt_id=?2 and tsk_id=?3")
	                      .setParameter(1,custId).setParameter(2, promptId).setParameter(3,taskId).getResultList();
		
		if(custCrAsgndTskDocList != null && custCrAsgndTskDocList.size()>0)
		{
			for(CustCrAsgndTskDoc custCrAsgndTskDoc:custCrAsgndTskDocList)
			{
				docList.add(custCrAsgndTskDoc.getDoc().getDocId());
			}
		}

		return docList;
	}
	public int removeCurrentDocsForPrompt(long promptId,long taskId,long custId,long docId)
	{
		
		return em().createQuery("delete from CustCrAsgndTskDoc c  where customer_id=?1 and prmpt_id=?2 and tsk_id=?3 and doc_id=?4")
	                      .setParameter(1,custId).setParameter(2, promptId).setParameter(3,taskId).setParameter(4,docId).executeUpdate();
		
		
	}
	
	
}
