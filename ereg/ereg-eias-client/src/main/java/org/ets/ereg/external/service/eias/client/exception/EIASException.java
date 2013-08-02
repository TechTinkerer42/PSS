/**
 * 
 */
package org.ets.ereg.external.service.eias.client.exception;

import java.util.List;

import org.ets.ereg.external.service.eias.client.types.ArrayOfXsdString;

/**
 * 
 *
 */
public class EIASException extends RuntimeException{
		
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getResponseResult() {
		return responseResult;
	}
	public void setResponseResult(String responseResult) {
		this.responseResult = responseResult;
	}
	public String getGUID() {
		return guid;
	}
	public void setGUID(String gUID) {
		guid = gUID;
	}
	public List<String> getUserIDSuggestion() {
		return userIDSuggestion;
	}
	public void setgetUserIDSuggestion(ArrayOfXsdString userIDSuggestion) {
		this.userIDSuggestion = userIDSuggestion.getItem();
	}
	
	public String getResponseAttribute() {
		return responseAttribute;
	}
	
	public void setResponseAttribute(String responseAttribute) {
		this.responseAttribute = responseAttribute;
	}

	public EIASException(String responseCode, String responseMessage,
			String responseResult, String gUID, String responseAttribute,
			List<String> userIDSuggestion) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.responseResult = responseResult;
		guid = gUID;
		this.responseAttribute = responseAttribute;
		this.userIDSuggestion = userIDSuggestion;
	}
	
	public EIASException()
	{		
		
	}
	
	private String responseCode;
	private String responseMessage;
	private String responseResult;
	private String guid;
	private String responseAttribute;
	private List<String> userIDSuggestion;

}
