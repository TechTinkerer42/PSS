/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ets.ereg.domain.audit;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class ETSAuditable implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String ADMIN_GROUP = "Auditable_Audit";
    
    @Column(name = "CREATED_BY_ADMIN_USR_ID", updatable = false)
    @AdminPresentation(friendlyName = "Auditable_Created_By_Admin_User", group = ADMIN_GROUP , visibility = VisibilityEnum.HIDDEN_ALL, readOnly=true)
    private Long createdByAdminUser;

    @Column(name = "CREATED_BY_CUSTOMER_ID", updatable = false)
    @AdminPresentation(friendlyName = "Auditable_Created_By_Customer", group = ADMIN_GROUP, visibility = VisibilityEnum.HIDDEN_ALL, readOnly=true)
    private Long createdByCustomer;

    @Column(name = "UPDATED_BY_ADMIN_USR_ID")
    @AdminPresentation(friendlyName = "Auditable_Updated_By_Admin_User", group = ADMIN_GROUP, visibility = VisibilityEnum.HIDDEN_ALL, readOnly = true)
    private Long updatedByAdminUser;

    @Column(name = "UPDATED_BY_CUSTOMER_ID")
    @AdminPresentation(friendlyName = "Auditable_Updated_By_Customer", group = ADMIN_GROUP, visibility = VisibilityEnum.HIDDEN_ALL, readOnly = true)
    private Long updatedByCustomer;

    @Column(name = "DATE_CREATED", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @AdminPresentation(friendlyName = "Auditable_Date_Created", group = ADMIN_GROUP, groupOrder=1000, readOnly=true)
    private Date etsDateCreated;

    @Column(name = "DATE_UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    @AdminPresentation(friendlyName = "Auditable_Date_Updated", group = ADMIN_GROUP, readOnly = true)
    private Date etsDateUpdated;

    public Long getCreatedByAdminUser() {
		return createdByAdminUser;
	}

	public void setCreatedByAdminUser(Long createdByAdminUser) {
		this.createdByAdminUser = createdByAdminUser;
	}

	public Long getCreatedByCustomer() {
		return createdByCustomer;
	}

	public void setCreatedByCustomer(Long createdByCustomer) {
		this.createdByCustomer = createdByCustomer;
	}

	public Long getUpdatedByAdminUser() {
		return updatedByAdminUser;
	}

	public void setUpdatedByAdminUser(Long updatedByAdminUser) {
		this.updatedByAdminUser = updatedByAdminUser;
	}

	public Long getUpdatedByCustomer() {
		return updatedByCustomer;
	}

	public void setUpdatedByCustomer(Long updatedByCustomer) {
		this.updatedByCustomer = updatedByCustomer;
	}

	public Date getEtsDateCreated() {
        return etsDateCreated;
    }

    public void setEtsDateCreated(Date etsDateCreated) {
        this.etsDateCreated = etsDateCreated;
    }

    public Date getEtsDateUpdated() {
        return etsDateUpdated;
    }

    public void setEtsDateUpdated(Date etsDateUpdated) {
        this.etsDateUpdated = etsDateUpdated;
    }
}
