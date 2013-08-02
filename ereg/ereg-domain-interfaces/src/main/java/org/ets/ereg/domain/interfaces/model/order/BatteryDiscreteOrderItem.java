package org.ets.ereg.domain.interfaces.model.order;

import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

import java.util.Date;

public interface BatteryDiscreteOrderItem extends ETSDiscreteOrderItem {

    public Agency getAgency();

    public void setAgency(Agency agency);

    public Date getStartDate();

    public void setStartDate(Date startDate);

    public Date getEndDate();

    public void setEndDate(Date endDate);

    public Integer getTotalTakes();

    public void setTotalTakes(Integer totalTakes);

}
