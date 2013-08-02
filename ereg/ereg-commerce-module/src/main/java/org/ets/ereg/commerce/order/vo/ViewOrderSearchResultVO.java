/**
 * 
 */
package org.ets.ereg.commerce.order.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.ets.ereg.common.business.annotation.SelectColumn;

public class ViewOrderSearchResultVO {

	@SelectColumn(order = 0)
	private String orderNumber;
	@SelectColumn(order = 1)
	private BigDecimal orderTotal;
	@SelectColumn(order = 2)
	private Timestamp orderDate;
	@SelectColumn(order = 3)
	private String testName;
	@SelectColumn(order = 4)
	private Timestamp appointmentDate;

	private List<ViewOrderSearchAppointmentVO> listAppointment = new ArrayList<ViewOrderSearchAppointmentVO>();

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Timestamp getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Timestamp appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getFormattedAppointmentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MMMMM dd, yyyy hh:mm aaa");
		if (appointmentDate != null) {
			return dateFormat.format(appointmentDate);
		} else {
			return "";
		}
	}

	public String getFormattedOrderDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");
		if (orderDate != null) {
			return dateFormat.format(orderDate);
		} else {
			return "";
		}
	}

	public List<ViewOrderSearchAppointmentVO> getListAppointment() {
		return listAppointment;
	}

	
}