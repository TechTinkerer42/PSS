package org.ets.ereg.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.broadleafcommerce.common.audit.Auditable;
import org.broadleafcommerce.common.audit.AuditableListener;
import org.ets.ereg.domain.interfaces.model.common.ETSApplicationUser;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
	
	@Entity
	@Table(name = "ETS_APLCTN_USR")
	@EntityListeners(value = { AuditableListener.class })
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	public class ETSApplicationUserImpl implements ETSApplicationUser, Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@Column(name = "USER_ID", nullable = false, length = 32)
		private String userID;

		@Column(name = "USER_NAME", nullable = false, length = 50)
		private String userName;

		@Column(name = "PASSWORD", nullable = false, length = 255)
		private String password;

		public String getUserID() {
			return userID;
		}

		public void setUserID(String userID) {
			this.userID = userID;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(userID).append(userName).append(password)
					.toHashCode();
		}

		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof ETSApplicationUserImpl) {
				final ETSApplicationUserImpl other = (ETSApplicationUserImpl) obj;
				return new EqualsBuilder().append(userID, other.userID)
						.append(userName, other.userName)
						.append(password, other.password)
						.isEquals();
			} else {
				return false;
			}
		}
		
	}
