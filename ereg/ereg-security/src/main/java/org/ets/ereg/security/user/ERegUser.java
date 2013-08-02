package org.ets.ereg.security.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.common.enums.RoleEnumGroup;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ERegUser extends User {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstName;
	private String lastName;
	private String guId;
	private String emailAdress;
	private boolean isActive;
	private String userType;
	private Set<RoleEnum> eregUserRoles;

	public ERegUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGuId() {
		return guId;
	}

	public void setGuId(String guId) {
		this.guId = guId;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the eregUserRoles
	 */
	public Set<RoleEnum> getEregUserRoles() {
		return eregUserRoles;
	}

	/**
	 * @param eregUserRoles
	 *            the eregUserRoles to set
	 */
	public void setEregUserRoles(Set<RoleEnum> eregUserRoles) {
		this.eregUserRoles = eregUserRoles;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		ERegUser rhs = (ERegUser) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(id, rhs.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).toHashCode();
	}

	@Override
	public String toString() {
		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this,
				ToStringStyle.MULTI_LINE_STYLE);
		reflectionToStringBuilder.setExcludeFieldNames(new String[] { "password" });
		return reflectionToStringBuilder.toString();
	}

	public boolean hasCSRRole() {
		return isUserInAnyRole(RoleEnumGroup.CSR_GROUP.getRolesInGroup());
	}

	public boolean hasTCARole() {
		return isUserInRole(RoleEnum.ROLE_TEST_CENTER_ADMIN);
	}

	public boolean isCustomer() {
		return isUserInRole(RoleEnum.ROLE_CUSTOMER);
	}

	public boolean isUserInAnyRole(Set<RoleEnum> roles) {
		if (CollectionUtils.isEmpty(roles)) {
			return false;
		}
		return isUserInAnyRole(new ArrayList<RoleEnum>(roles));
	}

	public boolean isUserInAllRoles(Set<RoleEnum> roles) {
		if (CollectionUtils.isEmpty(roles)) {
			return false;
		}
		return isUserInAllRoles(new ArrayList<RoleEnum>(roles));
	}

	public boolean isUserInAnyRole(List<RoleEnum> roles) {
		if (CollectionUtils.isEmpty(roles)) {
			return false;
		}
		boolean isInAnyRole = isUserInRole(roles.get(0));
		for (RoleEnum re : roles) {
			isInAnyRole = isInAnyRole | isUserInRole(re);
		}
		return isInAnyRole;
	}

	public boolean isUserInAllRoles(List<RoleEnum> roles) {
		if (CollectionUtils.isEmpty(roles) || CollectionUtils.isEmpty(eregUserRoles)) {
			return false;
		}
		return eregUserRoles.containsAll(roles);
	}

	public boolean isUserInRole(RoleEnum role) {
		if (CollectionUtils.isEmpty(eregUserRoles)) {
			return false;
		}
		return eregUserRoles.contains(role);
	}

	public boolean isUserInRole(String role) {
		if (StringUtils.isEmpty(role)) {
			return false;
		}
		return isUserInRole(RoleEnum.get(role));
	}

	public boolean isUserInAnyRoles(String... roles) {
		return isUserInAnyRole(RoleEnum.get(roles));
	}

	public boolean isUserInAllRoles(String... roles) {
		return isUserInAllRoles(RoleEnum.get(roles));
	}

	public boolean isInternalUser() {
		return StringUtils.equalsIgnoreCase(userType, "INTERNAL");
	}

}
