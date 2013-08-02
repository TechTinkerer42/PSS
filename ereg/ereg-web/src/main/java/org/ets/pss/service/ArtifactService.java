/**
 * 
 */
package org.ets.pss.service;

import java.util.List;
import java.util.Set;

import org.ets.pss.persistence.dto.Artifact;
import org.ets.pss.persistence.model.AsgndTsk;
import org.ets.pss.persistence.model.Doc;
import org.ets.pss.persistence.model.EtsCust;
import org.ets.pss.persistence.model.MdaMimeTyp;
import org.ets.pss.persistence.dto.Prompt;
import org.ets.pss.persistence.model.UserTask;

/**
 * @author SSINGH007
 *
 */
public interface ArtifactService {
	Set<Doc> getCustomerArtifacts(long customerId);
	String removeArtifact(long docId);
	Doc getDoc(long docId);

}
