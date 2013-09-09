package org.ets.pss.persistence.dto;

import java.util.Map;
/**
 * @author asampath
 *
 */
public class TaskDraft {
	
	String hasLinks;
	
	Map<String,String[]> removeDocs;
	
	/**
	 * @return the removeDocs
	 */
	public Map<String, String[]> getRemoveDocs() {
		return removeDocs;
	}

	/**
	 * @param removeDocs the removeDocs to set
	 */
	public void setRemoveDocs(Map<String, String[]> removeDocs) {
		this.removeDocs = removeDocs;
	}

	/**
	 * @return the hasLink
	 */
	public String getHasLinks() {
		return hasLinks;
	}

	/**
	 * @param hasLink the hasLink to set
	 */
	public void setHasLinks(String hasLinks) {
		this.hasLinks = hasLinks;
	}

	public Map<String,String> essayMap;

	/**
	 * @return the essayMap
	 */
	public Map<String, String> getEssayMap() {
		return essayMap;
	}

	/**
	 * @param essayMap the essayMap to set
	 */
	public void setEssayMap(Map<String, String> essayMap) {
		this.essayMap = essayMap;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	String taskId;
	/**
	 * @return the promptDocs
	 */
	public Map<String, String[]> getPromptDocs() {
		return promptDocs;
	}

	/**
	 * @param promptDocs the promptDocs to set
	 */
	public void setPromptDocs(Map<String, String[]> promptDocs) {
		this.promptDocs = promptDocs;
	}

	Map<String,String[]> promptDocs;


}
