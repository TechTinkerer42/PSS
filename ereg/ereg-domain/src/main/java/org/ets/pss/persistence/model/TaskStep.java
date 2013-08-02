package org.ets.pss.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="TSK_STP")
public class TaskStep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="STP_ID", nullable=false)
	private Long stepId;
	
	@Column(name="TSK_ID", nullable=false)
	private Long taskId;
	
	@Column(name="STP_TITLE", nullable=true)
	private String title;
	
	@Column(name="STP_INSTR", nullable=true)
	private String instructions;

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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
	
	
	
	
}
