package org.ets.ereg.domain.interfaces.scheduling.request;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SchedulingOperation {

	FIND_SEAT("FIND_SEAT", "Find Seat"),
	HOLD_SEAT("HOLD_SEAT", "Hold Seat"),
	RELEASE_SEAT("RELEASE_SEAT", "Hold Seat"),
	RESERVE_SEAT("RESERVE_APPOINTMENT", "Reserve Seat"),
	RESERVE_SEAT_WITHOUT_HOLD("RESERVE_SEAT_WITHOUT_HOLD", "Reserve Seat Without Hold"),
	CANCEL_SEAT("CANCEL_SEAT", "Cancel Seat"),
	EXTEND_HOLD("EXTEND_HOLD", "Extend Hold"),
	RESCHEDULE_APPOINTMENT("RESCHEDULE_APPOINTMENT", "Reschedule Appoinment");

	/**
	 * A mapping between the code and its corresponding label to facilitate
	 * lookup by operation.
	 */
	private static Map<String, SchedulingOperation> codeToOperationMapping = new HashMap<String, SchedulingOperation>();

	static {
		for (SchedulingOperation schedulingOperation : EnumSet.allOf(SchedulingOperation.class)){
			codeToOperationMapping.put(schedulingOperation.getOperation(),
					schedulingOperation);
		}
	}

	private String operation;
	private String label;

	private SchedulingOperation(String operation, String label) {
		this.operation = operation;
		this.label = label;

	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static SchedulingOperation getOperation(String operation) {
		return codeToOperationMapping.get(operation) != null ? codeToOperationMapping
				.get(operation) : null;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Operation");
		sb.append("{operation=").append(operation);
		sb.append(", label='").append(label).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public String getOperation() {
		return operation;
	}
}
