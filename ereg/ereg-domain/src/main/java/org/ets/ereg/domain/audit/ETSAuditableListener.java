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

import java.lang.reflect.Field;
import java.util.Calendar;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.broadleafcommerce.common.time.SystemTime;
import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ETSAuditableListener {
	private static final Logger LOG = LoggerFactory.getLogger(ETSAuditableListener.class);
	
    //Assumes that these objects are on the request prior to invocation
    public static final String CUSTOMER_REQ_ATTR_NAME = "customer";
    public static final String ADMIN_REQ_ATTR_NAME = "adminUser";

    @PrePersist
    public void setAuditCreatedBy(Object entity) throws Exception {
        if (entity.getClass().isAnnotationPresent(Entity.class)) {
        	setFields(entity, "createdByCustomer", "createdByAdminUser", "etsDateCreated");
        }
    }

    @PreUpdate
    public void setAuditUpdatedBy(Object entity) throws Exception {
    	if (entity.getClass().isAnnotationPresent(Entity.class)) {
        	setFields(entity, "updatedByCustomer", "updatedByAdminUser", "etsDateUpdated");
        }
    }
    
    private void setFields(Object entity, String customerField, String adminField, String dateField) 
    		throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
    	boolean needsDates = false;
    	Field field = getSingleField(entity.getClass(), "etsAuditableWithoutDates");
    	if(field == null) {
    		needsDates = true;
    		field = getSingleField(entity.getClass(), "etsAuditable");
    	}
        field.setAccessible(true);
        if (field.isAnnotationPresent(Embedded.class)) {
            Object auditable = field.get(entity);
            if (auditable == null && needsDates) {
                field.set(entity, new ETSAuditable());
                auditable = field.get(entity);
            } else if (auditable == null && !needsDates) {
                field.set(entity, new ETSAuditableWithoutDates());
                auditable = field.get(entity);
            }
            Field agentFieldCustomer = auditable.getClass().getDeclaredField(customerField);
            Field agentFieldAdminUser = auditable.getClass().getDeclaredField(adminField);
            setAuditValueAgent(agentFieldCustomer, agentFieldAdminUser, auditable);
            
            if(needsDates) {
            	Field temporalField = auditable.getClass().getDeclaredField(dateField);
            	setAuditValueTemporal(temporalField, auditable);
            }            
        }
    }

    protected void setAuditValueTemporal(Field field, Object entity) throws IllegalArgumentException, IllegalAccessException {
        Calendar cal = SystemTime.asCalendar();
        field.setAccessible(true);
        field.set(entity, cal.getTime());
    }

    protected void setAuditValueAgent(Field agentCustomer, Field agentAdmin, Object entity) throws IllegalArgumentException, IllegalAccessException {
        Long customerId = null;
        Long adminUserId = null;

        try {
            BroadleafRequestContext requestContext = BroadleafRequestContext.getBroadleafRequestContext();
            if (requestContext != null) {
                Object customer = requestContext.getRequest().getAttribute(CUSTOMER_REQ_ATTR_NAME);
                Object adminUser = requestContext.getRequest().getAttribute(ADMIN_REQ_ATTR_NAME);

                if (customer != null) {
                	LOG.debug("Auditing action by customer");
                    Class<?> customerClass = customer.getClass();
                    Field userNameField = getSingleField(customerClass, "username");
                    userNameField.setAccessible(true);
                    String username = (String) userNameField.get(customer);
                    if (username != null) {
                        //the customer has been persisted
                        Field idField = getSingleField(customerClass, "id");
                        idField.setAccessible(true);
                        customerId = (Long) idField.get(customer);
                    }
                }

                if (adminUser != null) {
                	LOG.debug("Auditing action by admin");
                    Class<?> adminUserClass = adminUser.getClass();
                    Field userNameField = getSingleField(adminUserClass, "name");
                    userNameField.setAccessible(true);
                    String username = (String) userNameField.get(adminUser);
                    if (username != null) {
                        //the admin user has been persisted
                        Field idField = getSingleField(adminUserClass, "id");
                        idField.setAccessible(true);
                        adminUserId = (Long) idField.get(adminUser);
                    }
                }

            }
        } catch (Exception e) {
            LOG.error("Error setting audit user info: ", e);
        }

        agentCustomer.setAccessible(true);
        agentCustomer.set(entity, customerId);

        agentAdmin.setAccessible(true);
        agentAdmin.set(entity, adminUserId);
    }

    private Field getSingleField(Class<?> clazz, String fieldName) throws IllegalStateException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException nsf) {
            // Try superclass
            if (clazz.getSuperclass() != null) {
                return getSingleField(clazz.getSuperclass(), fieldName);
            }

            return null;
        }
    }
}
