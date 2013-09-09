/**
 * 
 */
package org.ets.pss.persistence.dto;

/**
 * @author asampath
 *
 */
public class Prompt {

	private long promptId;
	private long taskId;
	private String instructions;

	private String essayContent;
	
	/**
	 * @return the promptId
	 */
	public long getPromptId() {
		return promptId;
	}
	/**
	 * @param promptId the promptId to set
	 */
	public void setPromptId(long promptId) {
		this.promptId = promptId;
	}
	/**
	 * @return the taskId
	 */
	public long getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}	
	/**
	 * @return the instructions
	 */
	public String getInstructions() {
		return instructions;
	}
	/**
	 * @param instructions the instructions to set
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	/**
	 * @return the essayContent
	 */
	public String getEssayContent() {
		return essayContent;
	}
	/**
	 * @param essayContent the essayContent to set
	 */
	public void setEssayContent(String essayContent) {
		this.essayContent = essayContent;
	}	

}
