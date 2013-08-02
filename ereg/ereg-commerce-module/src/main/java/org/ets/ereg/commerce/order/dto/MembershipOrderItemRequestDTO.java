package org.ets.ereg.commerce.order.dto;

import java.util.Date;

import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

public class MembershipOrderItemRequestDTO extends ETSDiscreteOrderItemRequestDTO {

    private Agency agency;

    private Date startDate;

    private Date endDate;

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
