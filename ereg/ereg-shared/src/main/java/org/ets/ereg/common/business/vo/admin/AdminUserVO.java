package org.ets.ereg.common.business.vo.admin;

import java.io.Serializable;

public class AdminUserVO implements Serializable {
	/**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long adminId;
	private String lastName;
	private String firstName;
	private String middleName;
	private String email;
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
    public Long getAdminId() {
        return adminId;
    }
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


}
