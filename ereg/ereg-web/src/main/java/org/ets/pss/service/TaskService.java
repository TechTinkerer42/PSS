/**
 * 
 */
package org.ets.pss.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ets.ereg.security.user.ERegUser;
import org.ets.pss.persistence.dto.Artifact;
import org.ets.pss.persistence.dto.ContentManagementDTO;
import org.ets.pss.persistence.dto.TaskDTO;
import org.ets.pss.persistence.dto.TaskDraft;
import org.ets.pss.persistence.model.AsgndTsk;
import org.ets.pss.persistence.model.Doc;
import org.ets.pss.persistence.model.EtsCust;
import org.ets.pss.persistence.model.MdaMimeTyp;
import org.ets.pss.persistence.model.Task;
import org.ets.pss.persistence.dto.Prompt;
import org.ets.pss.persistence.model.UserTask;

/**
 * @author SSINGH007
 *
 */
public interface TaskService {
	
	void saveDoc(Doc doc);
	void saveEssay(String s,long customerId,long tskId, long promptId);
	void saveVideoEntry(String entryID,long customerId,long tskId, long promptId);
	boolean getVideoEntries(ContentManagementDTO dto,long customerId,long tskId, long promptId);
	String saveDraft(TaskDraft taskDraft,long custId);
	String submitTask(long taskId,long custId);
	List<Doc> getCustomerArtifacts(long customerId);
	EtsCust getCustomer(long customerId);
	List<UserTask> getUserTasks(long customerId);
	MdaMimeTyp lookupMime(String mime);
	 Map<Long, String> getPromptForTask(long customerId,long lTaskId);
	AsgndTsk getAssignedTask(long customerId,long lTaskId);

	List<AsgndTsk> generateTasksForAccessCode(ERegUser loggedInUser,Long customerId, String accessCode);
	
	TaskDTO getTask(Long taskId);
	
	 Map<Long, String> getCustomerPromptsForTask(Long customerId, Long taskId);
	String getVideoEntryKey(long customerId,long tskId, long promptId);
	 Map<String, String> getVideosForTask(long customerId,long lTaskId);
	ContentManagementDTO uploadContent(boolean exists,ContentManagementDTO contentManagementVO);
	String getAdminTasks();
	
}
