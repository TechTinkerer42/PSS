/**
 * 
 */
package org.ets.pss.persistence.model;

import java.io.Serializable;
import java.util.Date;




/**
 * @author SSINGH007
 *
 */
/*@Entity
@Table(name="usertasks")*/
public class UserTask implements Serializable,Comparable<UserTask> {

	
	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private long taskId;
	
	private int displaySequenceId;

	

	//@Id
	//@Column(name = "userId")
	private String userId;
	
	//@Id
	//@Column(name = "name")
	private String name;
	
	private Date openDate;


	//@Column(name = "deadline")
	private Date deadline;
	
	//@Column(name = "status")
	private String status;
	
	//@Column(name = "lastSaved")
	private Date lastSaved;
	
	private boolean showAsLink;
	
	
	/**
	 * @return the displaySequenceId
	 */
	public int getDisplaySequenceId() {
		return displaySequenceId;
	}

	/**
	 * @param displaySequenceId the displaySequenceId to set
	 */
	public void setDisplaySequenceId(int displaySequenceId) {
		this.displaySequenceId = displaySequenceId;
	}
	
	/**
	 * @return the showAsLink
	 */
	public boolean isShowAsLink() {
		return showAsLink;
	}

	/**
	 * @param showAsLink the showAsLink to set
	 */
	public void setShowAsLink(boolean showAsLink) {
		this.showAsLink = showAsLink;
	}
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the openDate
	 */
	public Date getOpenDate() {
		return openDate;
	}

	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	/**
	 * @return the deadline
	 */
	public Date getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the lastSaved
	 */
	public Date getLastSaved() {
		return lastSaved;
	}

	/**
	 * @param lastSaved the lastSaved to set
	 */
	public void setLastSaved(Date lastSaved) {
		this.lastSaved = lastSaved;
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
	
	@Override
	public int compareTo(UserTask o) {
		
	    
	return (int)(this.displaySequenceId -o.displaySequenceId);
		
	}

		
}
