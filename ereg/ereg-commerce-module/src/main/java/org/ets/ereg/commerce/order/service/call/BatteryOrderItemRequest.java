package org.ets.ereg.commerce.order.service.call;

import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

public class BatteryOrderItemRequest extends ETSDiscreteOrderItemRequest {

    private Agency agency;

    public BatteryOrderItemRequest() {
        super();
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }
    
}

