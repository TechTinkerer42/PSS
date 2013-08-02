package org.ets.pss.persistence.dto;

import java.util.Map;

public class PromptDTO extends BaseDTO implements Comparable<PromptDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3123943076827877335L;
	private Long promptId;
	private String title;
	private String instructions;
	private String activity;
	private Map<Integer,String> guides;
	private String media;
	

	/**
	 * @return the media
	 */
	public String getMedia() {
		return media;
	}

	/**
	 * @param media the media to set
	 */
	public void setMedia(String media) {
		this.media = media;
	}

	public Map<Integer, String> getGuides() {
		return guides;
	}

	public void setGuides(Map<Integer, String> guides) {
		this.guides = guides;
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

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Long getPromptId() {
		return promptId;
	}

	public void setPromptId(Long promptId) {
		this.promptId = promptId;
	}

	@Override
	public int compareTo(PromptDTO o) {
		
		return (int)(this.promptId - o.promptId);
		
	}

	

}
