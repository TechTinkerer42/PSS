package org.ets.ereg.common.business.scheduling.service;

import org.ets.ereg.domain.interfaces.common.scheduling.SchedulingException;
import org.ets.ereg.domain.interfaces.scheduling.request.CancelSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.HoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatWithoutHoldRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.CancelSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ExtendHoldSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.HoldSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReleaseSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReserveSeatResponse;

public interface SchedulingService {
	
	FindSeatResponse findSeat(FindSeatRequest request) throws SchedulingException;
	HoldSeatResponse holdSeat(HoldSeatRequest request) throws SchedulingException;
	ReleaseSeatResponse releaseSeat(ReleaseSeatRequest request) throws SchedulingException;
	ReserveSeatResponse reserveSeat(ReserveSeatRequest request) throws SchedulingException;
	ReserveSeatResponse reserveSeatWithoutHold(ReserveSeatWithoutHoldRequest request) throws SchedulingException;
	CancelSeatResponse cancelSeat(CancelSeatRequest request) throws SchedulingException;
	ExtendHoldSeatResponse extendHold(ExtendHoldSeatRequest extendHoldSeatRequest) throws SchedulingException;
	
}
