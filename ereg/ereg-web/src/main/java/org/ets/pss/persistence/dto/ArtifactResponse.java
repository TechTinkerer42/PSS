package org.ets.pss.persistence.dto;

import java.util.List;

public class ArtifactResponse {
	
	String message;
	List<Artifact> artifacts;
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the artifacts
	 */
	public List<Artifact> getArtifacts() {
		return artifacts;
	}
	/**
	 * @param artifacts the artifacts to set
	 */
	public void setArtifacts(List<Artifact> artifacts) {
		this.artifacts = artifacts;
	}


}
