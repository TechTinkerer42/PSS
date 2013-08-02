/*
 * --------------------------------------------------------------------------
 * Copyright 2012 Educational Testing Service. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF EDUCATIONAL TESTING SERVICE AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * PURPOSE OTHER THAN THAT WHICH IT IS SPECIFICALLY PROVIDED WITHOUT THE WRITTEN
 * PERMISSION OF THE SAID COMPANY
 * --------------------------------------------------------------------------
 * Created on Apr 10, 2012
 */
package org.ets.ereg.domain.interfaces.scheduling.request;

import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;


public interface SchedulingRequest {

	String getRequesterRoleTypeCode();
	void setRequesterRoleTypeCode(String requesterRoleTypeCode);

	ETSCustomer getCustomer();
	void setCustomer(ETSCustomer customer);

	SchedulingOperation getOperation();

	String getTransactionID();
	void setTransactionID(String transactionID);


	String getRequestSystemId();
	void setRequestSystemId(String requestSystemId);


	String getSource();
	void setSource(String source);

	String getAlternateForm();
	void setAlternateForm(String alternateForm);

    TestVariation getTestVariation();
    void setTestVariation(TestVariation testVariation);
}

