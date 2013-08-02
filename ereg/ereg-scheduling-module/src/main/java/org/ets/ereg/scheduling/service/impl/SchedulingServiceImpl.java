package org.ets.ereg.scheduling.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import javax.annotation.Resource;

import org.ets.ereg.common.business.scheduling.service.SchedulingService;
import org.ets.ereg.common.business.service.RulesService;
import org.ets.ereg.common.business.vo.RetakeRulesRequestVo;
import org.ets.ereg.common.business.vo.RulesResponseVo;
import org.ets.ereg.domain.interfaces.common.scheduling.SchedulingException;
import org.ets.ereg.domain.interfaces.common.scheduling.SchedulingRequestValidationException;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.scheduling.request.CancelSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.HoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatWithoutHoldRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.SchedulingRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.TCFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.AvailabilityStatus;
import org.ets.ereg.domain.interfaces.scheduling.response.CancelSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ExtendHoldSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.HoldSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.NonAvailabilityReason;
import org.ets.ereg.domain.interfaces.scheduling.response.ReleaseSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReserveSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.scheduling.response.AvailabilityStatusImpl;
import org.ets.ereg.scheduling.response.CancelSeatResponseImpl;
import org.ets.ereg.scheduling.response.ExtendHoldSeatResponseImpl;
import org.ets.ereg.scheduling.response.FindSeatResponseImpl;
import org.ets.ereg.scheduling.response.HoldSeatResponseImpl;
import org.ets.ereg.scheduling.response.ReleaseSeatResponseImpl;
import org.ets.ereg.scheduling.response.ReserveSeatResponseImpl;
import org.ets.ereg.scheduling.service.TestCenterService;
import org.ets.ereg.scheduling.util.SchedulingTypeEnum;
import org.ets.ereg.scheduling.validator.CancelSeatRequestValidator;
import org.ets.ereg.scheduling.validator.FindSeatRequestValidator;
import org.ets.ereg.scheduling.validator.HeldSeatValidator;
import org.ets.ereg.scheduling.validator.HoldSeatRequestValidator;
import org.ets.ereg.scheduling.validator.ReleaseSeatRequestValidator;
import org.ets.ereg.scheduling.validator.ReservSeatRequestValidator;
import org.ets.ereg.scheduling.validator.ReservedSeatValidator;
import org.ets.ereg.scheduling.validator.SeatValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

@Service("schedulingService")
public class SchedulingServiceImpl implements SchedulingService{

	private static Logger logger = LoggerFactory.getLogger(SchedulingServiceImpl.class);

	@Resource(name="mockSchedulingService")
	private SchedulingService mockSchedulingService;

	@Resource(name = "reTakeTestRulesService")
	private RulesService<RetakeRulesRequestVo> reTakeTestRulesService;

    @Resource(name="adminSchedulingService")
    private SchedulingService adminSchedulingService;

    @Resource(name = "testCenterService")
    private TestCenterService testCenterService;

    @Resource(name = "ssaSchedulingService")
	private  SchedulingService ssaSchedulingService;
    private String useMockSchedulingService;



	@Override
	public FindSeatResponse findSeat(FindSeatRequest request){
		FindSeatResponse findSeatResponse = new FindSeatResponseImpl();

		if(validate(request)){			
		    findSeatResponse = getSchedulingService(request).findSeat(request);
			if(findSeatResponse.isSuccessful()){
				List<RulesResponseVo> rulesResponseVOList = getAnyRetakeRuleConflicts(request);
				setAvailabilityStatus(findSeatResponse,rulesResponseVOList);
			}
		}

		return findSeatResponse;
	}

	@Override
	public HoldSeatResponse holdSeat(HoldSeatRequest request){

		HoldSeatResponse holdSeatResponse = new HoldSeatResponseImpl();
		if(validate(request)){
			holdSeatResponse = getSchedulingService(request).holdSeat(request);
		}

		return holdSeatResponse;
	}

	@Override
	public ReleaseSeatResponse releaseSeat(ReleaseSeatRequest request) {

		ReleaseSeatResponse releaseSeatResponse = new ReleaseSeatResponseImpl();
		if(validate(request)){
			releaseSeatResponse=getSchedulingService(request).releaseSeat(request);
		}
		return releaseSeatResponse;
	}

	@Override
	public ReserveSeatResponse reserveSeat(ReserveSeatRequest request){
		ReserveSeatResponse reserveSeatResponse = new ReserveSeatResponseImpl();
		if(validate(request)){
			reserveSeatResponse = getSchedulingService(request).reserveSeat(request);
		}
		return reserveSeatResponse;
	}

	@Override
	public ReserveSeatResponse reserveSeatWithoutHold(
			ReserveSeatWithoutHoldRequest request){

		return getSchedulingService(request).reserveSeatWithoutHold(request);
	}

	@Override
	public CancelSeatResponse cancelSeat(CancelSeatRequest request){

		CancelSeatResponse cancelSeatResponse = new CancelSeatResponseImpl();

		if(validate(request)){
			cancelSeatResponse = getSchedulingService(request).cancelSeat(request);
		}
		return cancelSeatResponse;

	}

	private SchedulingService getSchedulingService(SchedulingRequest schedulingRequest){
	    SchedulingService schedulingService = null;
	    if(schedulingRequest instanceof TCFindSeatRequest){
            TCFindSeatRequest tcFindRequest = (TCFindSeatRequest) schedulingRequest;
            TestCenter testCenter = getTestCenterById(tcFindRequest.getTestCenterIds().get(0));
            schedulingService = getSchedulingService(useAdminSchedulingService(testCenter,
            		tcFindRequest.getTestVariation().getTest().getProgramType().getCode(), tcFindRequest.getDeliveryModeCode()));
        }else if(schedulingRequest instanceof HoldSeatRequest){
        	HoldSeatRequest holdSeatRequest = (HoldSeatRequest) schedulingRequest;
        	schedulingService = getSchedulingService(useAdminSchedulingService(holdSeatRequest.getSeat().getTestCenter(),
        			holdSeatRequest.getSeat().getTestVariation().getTest().getProgramType().getCode(), holdSeatRequest.getSeat().getDeliveryModeCode()));
        } else {
        	schedulingService = getSchedulingService(false);
        }
        return schedulingService;
	}

	private List<RulesResponseVo> getAnyRetakeRuleConflicts(FindSeatRequest request){

		RetakeRulesRequestVo retakeRulesRequestVo = new RetakeRulesRequestVo();
		retakeRulesRequestVo.setCustomerId(request.getCustomer().getId());
		retakeRulesRequestVo.setDeliveryTypeCode(request.getDeliveryModeCode());
		retakeRulesRequestVo.setTestId(request.getTestVariation().getId().getTestId());
		retakeRulesRequestVo.setRuleStartDate(new Timestamp(request.getSearchFromDate().getTimeInMillis()));
		retakeRulesRequestVo.setRuleEndDate(new Timestamp(request.getSearchToDate().getTimeInMillis()));

		return reTakeTestRulesService.applyRules(retakeRulesRequestVo);

	}

	private boolean useMockSchedulingService(){
	    // TODO have to fix this to use ssa service
		return (getUseMockSchedulingService()!=null && "yes".equalsIgnoreCase(getUseMockSchedulingService()));
	    //return Boolean.TRUE;
	}

	private FindSeatResponse setAvailabilityStatus(FindSeatResponse response,List<RulesResponseVo> rulesResponseVOList){

		if(rulesResponseVOList!=null && !rulesResponseVOList.isEmpty()){

			Calendar cal = null;
			AvailabilityStatus availabilityStatus=null;
			List<String> conflictRuleCoes=Collections.emptyList();
			List<NonAvailabilityReason> nonAvailabilityReasons = Collections.emptyList();
			SortedSet<Seat> seats =null;
			for(RulesResponseVo rulesResponseVo:rulesResponseVOList){

				cal = Calendar.getInstance();
				cal.setTimeInMillis(rulesResponseVo.getRuleAppliedDate().getTime());

				seats = response.getSeats(cal.getTime());
				if(seats!=null && !seats.isEmpty()){
					conflictRuleCoes = rulesResponseVo.getConflictRuleCode();
					if(!conflictRuleCoes.isEmpty()){
						nonAvailabilityReasons = new ArrayList<NonAvailabilityReason>();
						for(String ruleCode:conflictRuleCoes){
							nonAvailabilityReasons.add(NonAvailabilityReason.getNonAvailabilityReason(ruleCode));
						}
						availabilityStatus = new AvailabilityStatusImpl();
						availabilityStatus.setNonAvailabilityReasons(nonAvailabilityReasons);
						Iterator<Seat> iterator = seats.iterator();
						while(iterator.hasNext()){
							iterator.next().setAvailabilityStatus(availabilityStatus);
						}
					}

				}

			}

		}
		return response;

	}

    private boolean validate(Object obj) throws SchedulingRequestValidationException{
    	if(obj == null){
            logger.error("scheduling request object is null");
            return false;
    	}

        BindException errors = new BindException(obj, "request");

        if(obj instanceof FindSeatRequest){
        	new FindSeatRequestValidator().validate(obj, errors);
        }else if(obj instanceof HoldSeatRequest){
        	 new HoldSeatRequestValidator(new SeatValidator()).validate(obj, errors);
        }else if(obj instanceof ReserveSeatRequest){
        	new ReservSeatRequestValidator(new HeldSeatValidator()).validate(obj, errors);
        }else if(obj instanceof ReleaseSeatRequest){
        	new ReleaseSeatRequestValidator(new HeldSeatValidator()).validate(obj, errors);
        }else if(obj instanceof CancelSeatRequest){
        	new CancelSeatRequestValidator(new ReservedSeatValidator()).validate(obj, errors);
        }

        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                  logger.error("Error Code: {}",error.getCode());
            }
            throw new SchedulingRequestValidationException(errors);
        }

        return true;
    }

    private TestCenter getTestCenterById(Long id) {
        return testCenterService.readTestCenterById(id);
    }

	@Override
	public ExtendHoldSeatResponse extendHold(
			ExtendHoldSeatRequest extendHoldSeatRequest)
			throws SchedulingException {

		ExtendHoldSeatResponse holdSeatResponse = new ExtendHoldSeatResponseImpl();
		if(validate(extendHoldSeatRequest)){
			holdSeatResponse = getSchedulingService(extendHoldSeatRequest).extendHold(extendHoldSeatRequest);
		}

		return holdSeatResponse;
	}

	public String getUseMockSchedulingService() {
		return useMockSchedulingService;
	}

	public void setUseMockSchedulingService(String useMockSchedulingService) {
		this.useMockSchedulingService = useMockSchedulingService;
	}

	private boolean useAdminSchedulingService(TestCenter testCenter, String programCode, String deliveryMode){
		return (testCenter.getSchedulingType(programCode).getCode().equals(
						SchedulingTypeEnum.TCA_MODEL.getCode())
		&& deliveryMode.equalsIgnoreCase(DeliveryModeType.PBT));
	}

	private SchedulingService getSchedulingService(boolean useAdminScheduling){
        if (useAdminScheduling) {
            return  adminSchedulingService;
        } else {
        	 if(useMockSchedulingService()){
        		return  mockSchedulingService;
        	}else{
        		return ssaSchedulingService;
        	}
        }
	}

	public SchedulingService getMockSchedulingService() {
		return mockSchedulingService;
	}

	public void setMockSchedulingService(SchedulingService mockSchedulingService) {
		this.mockSchedulingService = mockSchedulingService;
	}

	public RulesService<RetakeRulesRequestVo> getReTakeTestRulesService() {
		return reTakeTestRulesService;
	}

	public void setReTakeTestRulesService(
			RulesService<RetakeRulesRequestVo> reTakeTestRulesService) {
		this.reTakeTestRulesService = reTakeTestRulesService;
	}

	public SchedulingService getAdminSchedulingService() {
		return adminSchedulingService;
	}

	public void setAdminSchedulingService(SchedulingService adminSchedulingService) {
		this.adminSchedulingService = adminSchedulingService;
	}

	public TestCenterService getTestCenterService() {
		return testCenterService;
	}

	public void setTestCenterService(TestCenterService testCenterService) {
		this.testCenterService = testCenterService;
	}

	public SchedulingService getSsaSchedulingService() {
		return ssaSchedulingService;
	}

	public void setSsaSchedulingService(SchedulingService ssaSchedulingService) {
		this.ssaSchedulingService = ssaSchedulingService;
	}

}
