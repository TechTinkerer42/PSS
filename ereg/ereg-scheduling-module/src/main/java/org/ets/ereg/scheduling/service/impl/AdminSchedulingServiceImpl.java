package org.ets.ereg.scheduling.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.ets.ereg.common.business.scheduling.service.SchedulingService;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.util.Constant;
import org.ets.ereg.domain.interfaces.common.scheduling.SchedulingException;
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
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.scheduling.response.AvailabilityStatusImpl;
import org.ets.ereg.scheduling.response.ExtendHoldSeatResponseImpl;
import org.ets.ereg.scheduling.response.FindSeatResponseImpl;
import org.ets.ereg.scheduling.response.HeldSeatImpl;
import org.ets.ereg.scheduling.response.HoldSeatResponseImpl;
import org.ets.ereg.scheduling.response.ReleaseSeatResponseImpl;
import org.ets.ereg.scheduling.response.SeatImpl;
import org.ets.ereg.scheduling.service.TestCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("adminSchedulingService")
public class AdminSchedulingServiceImpl implements SchedulingService {

	public static final Logger LOG = LoggerFactory.getLogger(AdminSchedulingServiceImpl.class);
	
	@Resource(name = "testCenterService")
    private TestCenterService testCenterService;

    @Resource(name = "applicationConfigurationService")
    private ApplicationConfigurationService applicationConfigurationService;

    private int seatHour;

    @Override
    public FindSeatResponse findSeat(FindSeatRequest request)
            throws SchedulingException {
    	LOG.debug("inside admin scheduling find seat");
        FindSeatResponse response = new FindSeatResponseImpl();
        Calendar testDate;
        int seatIndex = 1;
        Calendar calDate = new GregorianCalendar();
        calDate.set(Calendar.HOUR_OF_DAY, 0);
        calDate.set(Calendar.MINUTE, 0);
        calDate.set(Calendar.SECOND, 0);
        calDate.set(Calendar.MILLISECOND, 0);
        for (testDate = (Calendar) request.getSearchFromDate().clone(); !testDate.after(request.getSearchToDate()); testDate.add(Calendar.DATE, 1)) {
            if(testDate.before(calDate)){
                continue;
            }
            seatIndex = addSeats(request, response, testDate, seatIndex, calDate);
            seatIndex++;
        }

        if(response.getSeats()!=null && response.getSeats().size() > 0){
            response.setSuccessful(Boolean.TRUE);
        }

        return response;
    }

    private int addSeats(FindSeatRequest request, FindSeatResponse response, Calendar testDate, int seatIndex, Calendar calDate) {
        TCFindSeatRequest tcFindRequest = null;
        Long testCenterId = null;
        if(request instanceof TCFindSeatRequest){
            tcFindRequest = (TCFindSeatRequest) request;
            testCenterId = tcFindRequest.getTestCenterIds().get(0);
        }
        seatHour = Integer.parseInt(getApplicationConfigurationvalue(Constant.SEAT_HOUR));
        TestCenter testCenter = testCenterService.readTestCenterById(testCenterId);
        DateFormat dateFormat = new SimpleDateFormat("HH");
        String roundhour = dateFormat.format(DateUtils.round(new Date(), Calendar.HOUR));
        Integer dateInt = Integer.parseInt(roundhour);
        if(testDate.equals(calDate) && seatHour < dateInt){
            return seatIndex;
        }
        Seat seat = new SeatImpl(seatIndex);
        seat.setAvailabilityStatus(new AvailabilityStatusImpl());
        seat.setDeliveryModeCode(request.getDeliveryModeCode());
        Calendar startDateTime = (Calendar) testDate.clone();
        startDateTime.set(Calendar.HOUR_OF_DAY, seatHour);
        startDateTime.set(Calendar.MINUTE, 0);
        seat.setLocalStartDateTime(startDateTime);
        seat.setTestCenter(testCenter);
        seat.setTestVariation(request.getTestVariation());
        response.addSeat(seat);
        return seatIndex;
    }

    @Override
    public HoldSeatResponse holdSeat(HoldSeatRequest request)
            throws SchedulingException {
    	LOG.debug("inside admin scheduling hold seat");
        HoldSeatResponse response = new HoldSeatResponseImpl();
        Seat seat = request.getSeat();
        HeldSeat heldSeat = new HeldSeatImpl(seat);
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
        response.setHeldSeat(heldSeat);
        response.setSuccessful(Boolean.TRUE);
        return response;
    }

    private String getHoldCode(Seat seat) {
        StringBuilder holdCode = new StringBuilder();

        if (seat.getLocalStartDateTime() != null) {
            holdCode.append(String.valueOf(seat.getLocalStartDateTime().getTimeInMillis()));
        }
        if (seat.getTestVariation().getId().getTestId() != null) {
            //to check later
           /* holdCode.append(seat.getTestId().getProgramCode());
            holdCode.append(seat.getTestId().getTestCode());*/
        }
        if (seat.getTestCenter() != null) {
            holdCode.append(seat.getTestCenter().getId());
        }
        holdCode.append(new Date().getTime());
        holdCode.append(1000000 + (int)(Math.random() * 9000000));

        return holdCode.toString().substring(0, 32);
    }

    private String getApplicationConfigurationvalue(String key) {
        return applicationConfigurationService.findApplicationConfigurationValue(key);
    }

    @Override
    public ReleaseSeatResponse releaseSeat(ReleaseSeatRequest request)
            throws SchedulingException {
    	LOG.debug("inside admin scheduling release seat");
        ReleaseSeatResponse response = new ReleaseSeatResponseImpl();
        response.setSuccessful(true);

        return response;
    }




    @Override
    public ReserveSeatResponse reserveSeat(ReserveSeatRequest request)
            throws SchedulingException {
    	LOG.debug("inside admin scheduling reserve seat");
    	// TODO Auto-generated method stub
        return null;
    }




    @Override
    public ReserveSeatResponse reserveSeatWithoutHold(
            ReserveSeatWithoutHoldRequest request) throws SchedulingException {
    	LOG.debug("inside admin scheduling reserve seat without hold");
    	// TODO Auto-generated method stub
        return null;
    }




    @Override
    public CancelSeatResponse cancelSeat(CancelSeatRequest request)
            throws SchedulingException {
    	LOG.debug("inside admin scheduling cancel seat");
    	// TODO Auto-generated method stub
        return null;
    }

	@Override
	public ExtendHoldSeatResponse extendHold(
			ExtendHoldSeatRequest extendHoldSeatRequest)
			throws SchedulingException {
		LOG.debug("inside admin scheduling extend hold seat");
		ExtendHoldSeatResponse extendHoldSeatResponse = new ExtendHoldSeatResponseImpl();
		extendHoldSeatResponse.setSuccessful(true);
		return extendHoldSeatResponse;
	}

}
