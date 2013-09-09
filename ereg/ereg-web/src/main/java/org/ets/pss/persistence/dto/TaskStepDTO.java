package org.ets.pss.persistence.dto;

import java.util.Set;

/**
 * @author asampath
 *
 */

public class TaskStepDTO extends BaseDTO implements Comparable<TaskStepDTO> {
	
	private static final long serialVersionUID = -4850105791053218438L;
	private Long stepID;
	private String title;
	private String instructions;
	
	Set<PromptDTO> promptDTOs;
	
	public Long getStepID() {
		return stepID;
	}
	public void setStepID(Long stepID) {
		this.stepID = stepID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	@Override
	public int compareTo(TaskStepDTO o) {
		return (int)(this.stepID - o.stepID);
	}
	public Set<PromptDTO> getPromptDTOs() {
		return promptDTOs;
	}
	public void setPromptDTOs(Set<PromptDTO> promptDTOs) {
		this.promptDTOs = promptDTOs;
	}
	
	

}
