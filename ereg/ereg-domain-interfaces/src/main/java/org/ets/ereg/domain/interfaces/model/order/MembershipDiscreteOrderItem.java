package org.ets.ereg.domain.interfaces.model.order;

import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

import java.util.Date;

public interface MembershipDiscreteOrderItem extends ETSDiscreteOrderItem {

    public Agency getAgency();

    public void setAgency(Agency agency);

    public Date getStartDate();

    public void setStartDate(Date startDate);

    public Date getEndDate();

    public void setEndDate(Date endDate);

}
