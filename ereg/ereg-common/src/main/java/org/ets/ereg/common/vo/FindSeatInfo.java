package org.ets.ereg.common.vo;

import java.io.Serializable;
import java.util.Map;


public class FindSeatInfo implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String nonAvailabilityText;
    private Map<String, Integer> timeMap;

    public String getNonAvailabilityText() {
        return nonAvailabilityText;
    }
    public void setNonAvailabilityText(String nonAvailabilityText) {
        this.nonAvailabilityText = nonAvailabilityText;
    }
    public Map<String, Integer> getTimeMap() {
        return timeMap;
    }
    public void setTimeMap(Map<String, Integer> timeMap) {
        this.timeMap = timeMap;
    }



}
