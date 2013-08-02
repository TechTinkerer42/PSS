package org.ets.ereg.scheduling.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.ets.ereg.common.business.scheduling.service.SchedulingService;
import org.ets.ereg.domain.interfaces.common.scheduling.SchedulingException;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.scheduling.request.CancelSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.HoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatWithoutHoldRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.CancelSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ExtendHoldSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.HoldSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReleaseSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReserveSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReservedSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.scheduling.response.AvailabilityStatusImpl;
import org.ets.ereg.scheduling.response.CancelSeatResponseImpl;
import org.ets.ereg.scheduling.response.ExtendHoldSeatResponseImpl;
import org.ets.ereg.scheduling.response.FindSeatResponseImpl;
import org.ets.ereg.scheduling.response.HeldSeatImpl;
import org.ets.ereg.scheduling.response.HoldSeatResponseImpl;
import org.ets.ereg.scheduling.response.ReleaseSeatResponseImpl;
import org.ets.ereg.scheduling.response.ReserveSeatResponseImpl;
import org.ets.ereg.scheduling.response.ReservedSeatImpl;
import org.ets.ereg.scheduling.response.SeatImpl;
import org.ets.ereg.scheduling.service.TestCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("mockSchedulingService")
public class MockSchedulingServiceImpl implements SchedulingService {

    public static final Logger LOG = LoggerFactory.getLogger(MockSchedulingServiceImpl.class);
	
	@Resource(name = "testCenterService")
    private TestCenterService testCenterService;

    @Override
    public FindSeatResponse findSeat(FindSeatRequest request)
            throws SchedulingException {
    	LOG.debug("inside mock scheduling find seat");
        TCFindSeatRequest tcFindRequest = null;
        if(request instanceof TCFindSeatRequest){
            tcFindRequest = (TCFindSeatRequest) request;
        }
        FindSeatResponse response = new FindSeatResponseImpl();
        Calendar testDate;
        int seatIndex = 1;
        Calendar calDate = new GregorianCalendar();
        calDate.set(Calendar.HOUR_OF_DAY, 0);
        calDate.set(Calendar.MINUTE, 0);
        calDate.set(Calendar.SECOND, 0);
        calDate.set(Calendar.MILLISECOND, 0);
        boolean isCBT = false;
        if(request.getDeliveryModeCode().equalsIgnoreCase(DeliveryModeType.CBT)){
            isCBT = true;
        }
        for (testDate = (Calendar) tcFindRequest.getSearchFromDate().clone(); !testDate.after(tcFindRequest.getSearchToDate()); testDate.add(Calendar.DATE, 1)) {
            if(testDate.before(calDate)){
                continue;
            }
            if (testDate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                seatIndex = addSeats(tcFindRequest, response, testDate, seatIndex, calDate, isCBT, true);
            } else if (testDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                seatIndex = addSeats(tcFindRequest, response, testDate, seatIndex, calDate, false, true);
                seatIndex = addSeats(tcFindRequest, response, testDate, seatIndex, calDate, false, false);
            } else {
                seatIndex = addSeats(tcFindRequest, response, testDate, seatIndex, calDate, false, true);
                seatIndex = addSeats(tcFindRequest, response, testDate, seatIndex, calDate, false, false);
            }

        }

        if(response.getSeats()!=null && response.getSeats().size() > 0){
            response.setSuccessful(Boolean.TRUE);

        }

        return response;
    }




    private int addSeats(TCFindSeatRequest request, FindSeatResponse response, Calendar testDate, int seatIndex, Calendar calDate, boolean isCBT, boolean isMorning) {
        if(isCBT){
            return seatIndex;
        }
        /*TCFindSeatRequest tcFindRequest = null;
        Long testCenterId = null;
        if(request instanceof TCFindSeatRequest){
            tcFindRequest = (TCFindSeatRequest) request;
            testCenterId = tcFindRequest.getTestCenterIds().get(0);
        }*/
        Long testCenterId = request.getTestCenterIds().get(0);;

        int startHour = isMorning ? 9 : 12;
        int endHour = isMorning ? 12 : 16;
        TestCenter testCenter = testCenterService.readTestCenterById(testCenterId);
        DateFormat dateFormat = new SimpleDateFormat("HH");
        String roundhour = dateFormat.format(DateUtils.round(new Date(), Calendar.HOUR));
        Integer dateInt = Integer.parseInt(roundhour);
        if(testDate.equals(calDate) && endHour < dateInt){
            return seatIndex;
        }

        for (int i = startHour; i < endHour; i++) {
            if(testDate.equals(calDate) && i < dateInt) {
                continue;
            }
            Seat seat = new SeatImpl(seatIndex);
            seat.setAvailabilityStatus(new AvailabilityStatusImpl());
            seat.setDeliveryModeCode(request.getDeliveryModeCode());
            Calendar startDateTime = (Calendar) testDate.clone();
            startDateTime.set(Calendar.HOUR_OF_DAY, i);
            startDateTime.set(Calendar.MINUTE, 0);
            seat.setLocalStartDateTime(startDateTime);
            seat.setTestCenter(testCenter);
            seat.setTestVariation(request.getTestVariation());
            response.addSeat(seat);
            seatIndex++;
        }
        return seatIndex;
    }

    @Override
    public HoldSeatResponse holdSeat(HoldSeatRequest request)
            throws SchedulingException {
    	LOG.debug("inside mock scheduling hold seat");
        HoldSeatResponse response = new HoldSeatResponseImpl();
        Seat seat = request.getSeat();
        HeldSeat heldSeat = null;

        if (seat.getLocalStartDateTime().get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
        	heldSeat = new HeldSeatImpl(seat);

            heldSeat.setHoldCode(getHoldCode(heldSeat));
            heldSeat.setHoldDuration("30 min");
            Calendar expiration = Calendar.getInstance();
            expiration.setTime(new Date());
            expiration.add(Calendar.MINUTE, 20);
            heldSeat.setHoldExpiration(expiration);
            heldSeat.setHoldSource("some source");  // ?
            heldSeat.setLabCode("some lab code");
            heldSeat.setSeatCode("some seat code");
            heldSeat.setSiteCode("some site code");

            response.setHeldSeat(heldSeat);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
        }

        return response;
    }

    private String getHoldCode(Seat seat) {
        StringBuilder holdCode = new StringBuilder();

        if (seat.getLocalStartDateTime() != null) {
            holdCode.append(String.valueOf(seat.getLocalStartDateTime().getTimeInMillis()));
        }
        if (seat.getTestVariation().getId().getTestId() != null) {
            //to do later
            /*
            holdCode.append(seat.getTestId().getProgramCode());
            holdCode.append(seat.getTestId().getTestCode());*/
        }
        if (seat.getTestCenter() != null) {
            holdCode.append(seat.getTestCenter().getId());
        }
        holdCode.append(new Date().getTime());
        holdCode.append(1000000 + (int)(Math.random() * 9000000));

        return holdCode.toString().substring(0, 32); // we can only have 36 characters
    }

    @Override
    public ReleaseSeatResponse releaseSeat(ReleaseSeatRequest request)
            throws SchedulingException {
    	LOG.debug("inside mock scheduling release seat");
        ReleaseSeatResponse response = new ReleaseSeatResponseImpl();
        response.setSuccessful(true);

        return response;
    }

    @Override
    public ReserveSeatResponse reserveSeat(ReserveSeatRequest request)
            throws SchedulingException {
    	LOG.debug("inside mock scheduling reserve seat");
        ReserveSeatResponse response = new ReserveSeatResponseImpl();
        response.setHoldExpired(false);  // ?

        ReservedSeat reservedSeat = new ReservedSeatImpl();
        HeldSeat heldSeat = request.getHeldSeat();
        reservedSeat.setApproxDistance(heldSeat.getApproxDistance());
        reservedSeat.setAvailabilityStatus(heldSeat.getAvailabilityStatus());
        reservedSeat.setDeliveryModeCode(heldSeat.getDeliveryModeCode());
        reservedSeat.setHoldCode(heldSeat.getHoldCode());
        reservedSeat.setHoldExpiration(heldSeat.getHoldExpiration());
        reservedSeat.setHoldSource(heldSeat.getHoldSource());
        reservedSeat.setLocalStartDateTime(heldSeat.getLocalStartDateTime());
        reservedSeat.setTestCenter(heldSeat.getTestCenter());
        reservedSeat.setTestVariation(heldSeat.getTestVariation());

        reservedSeat.setEtsReservationID("ERID" + heldSeat.getHoldCode());

        response.setReservedSeat(reservedSeat);
        response.setSuccessful(true);
        return response;
    }

    @Override
    public ReserveSeatResponse reserveSeatWithoutHold(
            ReserveSeatWithoutHoldRequest request) throws SchedulingException {
    	LOG.debug("inside mock scheduling reserve seat without hold");
        ReserveSeatResponse response = new ReserveSeatResponseImpl();
        response.setHoldExpired(false); // TODO check

        //request.getCustomerId(); // TODO find out about customer id.
        request.getCustomer();

        ReservedSeat reservedSeat = new ReservedSeatImpl();
        Seat seat = request.getSeat();
        reservedSeat.setApproxDistance(seat.getApproxDistance());
        reservedSeat.setAvailabilityStatus(seat.getAvailabilityStatus());
        reservedSeat.setDeliveryModeCode(seat.getDeliveryModeCode());
        reservedSeat.setLocalStartDateTime(seat.getLocalStartDateTime());
        reservedSeat.setTestCenter(seat.getTestCenter());
        reservedSeat.setTestVariation(seat.getTestVariation());

        reservedSeat.setEtsReservationID("ERID" + getHoldCode(seat));

        response.setReservedSeat(reservedSeat);
        response.setSuccessful(true);
        return response;
    }

    @Override
    public CancelSeatResponse cancelSeat(CancelSeatRequest request)
            throws SchedulingException {
    	LOG.debug("inside mock scheduling cancel seat");
        CancelSeatResponse response = new CancelSeatResponseImpl();
        response.setSuccessful(true);
        return response;
    }




	@Override
	public ExtendHoldSeatResponse extendHold(
			ExtendHoldSeatRequest extendHoldSeatRequest)
			throws SchedulingException {
		LOG.debug("inside mock scheduling extend hold seat");
		ExtendHoldSeatResponse extendHoldSeatResponse = new ExtendHoldSeatResponseImpl();
		extendHoldSeatResponse.setSuccessful(true);
		return extendHoldSeatResponse;
	}




	public TestCenterService getTestCenterService() {
		return testCenterService;
	}




	public void setTestCenterService(TestCenterService testCenterService) {
		this.testCenterService = testCenterService;
	}

}
