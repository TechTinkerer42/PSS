package org.ets.ereg.domain.interfaces.scheduling.response;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum NonAvailabilityReason {
	
	RETAKE_TEST_MAX_TEST_ATTEMPT_RULE("MTA",200,"scheduling.findSeatRequest.retakeRule.maxTestAtempt", "Max No. of Test taken in a give period"),
	RETAKE_TEST_WAIT_PERIOD_RULE("WPR",100,"scheduling.findSeatRequest.retakeRule.waitPeriod","Wait Period Rule"),
	SEAT_NOT_AVAILABLE("SEAT_NOT_AVAILABLE",0,"scheduling.findSeatRequest.retakeRule.seatNotAvailable","Seat Not Available");	
	
	/**
	 * A mapping between the non availability reason and its corresponding label to facilitate
	 * lookup by operation.
	 */
	private static Map<String, NonAvailabilityReason> nonAvailabilityReasonsMapping = new HashMap<String, NonAvailabilityReason>();

	static {
		for (NonAvailabilityReason nonAvailabilityReason : EnumSet.allOf(NonAvailabilityReason.class)){
			nonAvailabilityReasonsMapping.put(nonAvailabilityReason.getNonAvailabilityReason(),
					nonAvailabilityReason);
		}
	}

	private String nonAvailabilityReason;
	private String label;
	private int priority;
	private String i18nCode;
	

	private NonAvailabilityReason(String nonAvailabilityReason, int priority,String i18nCode,String label) {
		this.nonAvailabilityReason = nonAvailabilityReason;
		this.label = label;
		this.priority  = priority;
		this.i18nCode = i18nCode;

	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static NonAvailabilityReason getNonAvailabilityReason(String nonAvailabilityReasonCode) {
		return nonAvailabilityReasonsMapping.get(nonAvailabilityReasonCode) != null ? nonAvailabilityReasonsMapping
				.get(nonAvailabilityReasonCode) : null;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Non Availability Reason");
		sb.append("{Non Availability Reason=").append(nonAvailabilityReason);
		sb.append(", Priority =").append(priority);
		sb.append(", i18nCode=").append(i18nCode);
		sb.append(", label='").append(label).append('\'');
		sb.append('}');
		return sb.toString();
	}
	
	public String getNonAvailabilityReason() {
		return nonAvailabilityReason;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getI18nCode() {
		return i18nCode;
	}

	public void setI18nCode(String i18nCode) {
		this.i18nCode = i18nCode;
	}

}
