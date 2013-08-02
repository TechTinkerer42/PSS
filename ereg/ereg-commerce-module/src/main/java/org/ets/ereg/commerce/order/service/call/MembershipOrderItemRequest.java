package org.ets.ereg.commerce.order.service.call;

import java.util.Date;

import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

public class MembershipOrderItemRequest extends ETSDiscreteOrderItemRequest {

    private Agency agency;

    private Date startDate;

    private Date endDate;

    public MembershipOrderItemRequest() {
        super();
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
