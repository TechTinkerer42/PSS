package org.ets.ereg.domain.interfaces.model.common;

import java.io.Serializable;


import org.ets.ereg.domain.interfaces.model.common.id.CustomerLinkId;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;

public interface CustomerLinkage extends Serializable{
	public String getLinkageKey();
	public void setLinkageKey(String linkageKey);
	public ETSCustomer getCustomer();
	public void setCustomer(ETSCustomer customer);
	public CustomerLinkId getId();
	public CustomerLinkId setId(CustomerLinkId id);
	public LinkageType getLinkageType();
	public void setLinkageType(LinkageType linkageType);
}
