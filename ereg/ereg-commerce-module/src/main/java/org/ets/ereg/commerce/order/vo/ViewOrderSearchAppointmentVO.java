/**
 * 
 */
package org.ets.ereg.commerce.order.vo;


public class ViewOrderSearchAppointmentVO {

	private String testName;
	private String appointmentDate;
	
	
	
	public ViewOrderSearchAppointmentVO(String testName,
			String appointmentDate) {
		super();
		this.testName = testName;
		this.appointmentDate = appointmentDate;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	
}