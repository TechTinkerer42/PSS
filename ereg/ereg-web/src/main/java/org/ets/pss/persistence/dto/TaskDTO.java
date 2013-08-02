package org.ets.pss.persistence.dto;

import java.util.Set;

public class TaskDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6700955423413537197L;
	private Long taskId;
	private String instructions;
	private String title;
	private boolean readOnly;
	private int status;
	
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	private String testName;

	Set<TaskStepDTO> stepDTOs;
	
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Set<TaskStepDTO> getStepDTOs() {
		return stepDTOs;
	}

	public void setStepDTOs(Set<TaskStepDTO> stepDTOs) {
		this.stepDTOs = stepDTOs;
	}

	
}
