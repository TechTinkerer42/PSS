package org.ets.ereg.domain.interfaces.model.profile;

import java.util.List;

public class DupCheckResponseObject {
	
	public boolean isDuplicate() {
		return isDuplicate;
	}
	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}
	public List<String> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	public DupCheckResponseObject(List<String> result)
	{
		messageList = result;
	}
	
	private List<String> messageList;
	private boolean isDuplicate;
}
