/**
 * 
 */
package org.ets.pss.persistence.dao;

import java.util.List;

import org.ets.pss.persistence.model.CustCrAsgndTskDoc;

/**
 * @author asampath
 *
 */
public interface CustCrAsgndTskDocDao extends GenericDao<CustCrAsgndTskDoc> {
		List<Long> getCurrentDocsForPrompt(long promptId,long taskId,long custId);
		
		 int removeCurrentDocsForPrompt(long promptId,long taskId,long custId,long docId);
}
