/**
 * 
 */
package org.ets.pss.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author SSINGH007
 *
 */
public class PSSTask implements Serializable {

	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	

	private long taskId;
	
	private String instructions;
	
	private String essay;
	
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
	 * @return the essay
	 */
	public String getEssay() {
		return essay;
	}
	/**
	 * @param essay the essay to set
	 */
	public void setEssay(String essay) {
		this.essay = essay;
	}
	
}
