package org.ets.ereg.commerce.order.dto;

import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

public class BatteryOrderItemRequestDTO extends OrderItemRequestDTO {

    private Agency agency;

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }
    
}
