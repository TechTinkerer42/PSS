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
 * @author ASAMPATH
 *
 */
@Entity
@Table(name="CUST_PRMPT")
public class CustomerPrompt implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	
	@Id
	@Column(name="PRMPT_ID")
	private Long promptId;
	
	@Column(name="PRMPT_RSP")
	private String promptResponse;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getPromptId() {
		return promptId;
	}

	public void setPromptId(Long promptId) {
		this.promptId = promptId;
	}

	public String getPromptResponse() {
		return promptResponse;
	}

	public void setPromptResponse(String promptResponse) {
		this.promptResponse = promptResponse;
	}
	
	
	
	
}
