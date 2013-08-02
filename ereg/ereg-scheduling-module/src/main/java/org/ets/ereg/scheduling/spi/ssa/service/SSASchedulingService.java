package org.ets.ereg.scheduling.spi.ssa.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.ets.ereg.common.business.scheduling.service.SchedulingService;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.util.Constant;
import org.ets.ereg.domain.interfaces.common.scheduling.SchedulingException;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.scheduling.request.CancelSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.HoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.MapFindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatWithoutHoldRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.SchedulingRequest;
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
import org.ets.ereg.scheduling.dao.TestCenterDao;
import org.ets.ereg.scheduling.request.ExtendHoldSeatRequestImpl;
import org.ets.ereg.scheduling.response.CancelSeatResponseImpl;
import org.ets.ereg.scheduling.response.FindSeatResponseImpl;
import org.ets.ereg.scheduling.response.HoldSeatResponseImpl;
import org.ets.ereg.scheduling.response.ReleaseSeatResponseImpl;
import org.ets.ereg.scheduling.response.ReserveSeatResponseImpl;
import org.ets.ereg.scheduling.spi.ssa.converter.SSACancelSeatConverter;
import org.ets.ereg.scheduling.spi.ssa.converter.SSAExtendHoldSeatConverter;
import org.ets.ereg.scheduling.spi.ssa.converter.SSAFindSeatConverter;
import org.ets.ereg.scheduling.spi.ssa.converter.SSAHoldSeatConverter;
import org.ets.ereg.scheduling.spi.ssa.converter.SSAReleaseSeatConverter;
import org.ets.ereg.scheduling.spi.ssa.converter.SSAReserveSeatConverter;
import org.ets.ereg.scheduling.util.Constants;
import org.ets.ereg.scheduling.vo.ExtendHoldSeatStore;
import org.ets.ereg.security.user.ERegUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("ssaSchedulingService")
public class SSASchedulingService extends BaseSSAService implements SchedulingService,InitializingBean {

	private static Logger log = LoggerFactory.getLogger(SSASchedulingService.class);

	private Integer transactionPrefix;

	@Resource(name="testCenterDao")
	private TestCenterDao testCenterDao;

	@Resource(name="extendHoldSeatStore")
	private ExtendHoldSeatStore extendHoldSeatStore;

	public Integer getTransactionPrefix() {
		return transactionPrefix;
	}

	public void setTransactionPrefix(Integer transactionPrefix) {
		this.transactionPrefix = transactionPrefix;
	}


	@Resource(name = "applicationConfigurationService")
	private ApplicationConfigurationService applicationConfigurationService;

    @Override
    public void afterPropertiesSet() throws Exception {
    	setProtocol(getApplicationConfigurationvalue(Constant.SSA_PROTOCOL));
    	setServerPath(getApplicationConfigurationvalue(Constant.SSA_URL));
    	setSource(getApplicationConfigurationvalue(Constant.SSA_SOURCE_SYSTEM));
    	setRequestSystemId(getApplicationConfigurationvalue(Constant.SSA_REQ_SYSTEM));
    	transactionPrefix = Integer.valueOf(getApplicationConfigurationvalue(Constant.SSA_CONN_TIMEOUT));
    }

    private String getApplicationConfigurationvalue(String key) {
        return applicationConfigurationService.findApplicationConfigurationValue(key);
    }


	@Override
	public FindSeatResponse findSeat(FindSeatRequest findSeatRequest)
			throws SchedulingException {

		log.info("inside SSA scheduling find seat");

		populateSSARequest(findSeatRequest);

		FindSeatResponse findSeatResponse = new FindSeatResponseImpl();


		TestCenter testCenter = readTestCenterById(((TCFindSeatRequest)findSeatRequest).getTestCenterIds().get(0));

		if(findSeatRequest instanceof TCFindSeatRequest){
			 	populateTestCenterAndTest((TCFindSeatRequest)findSeatRequest);
		}else if(findSeatRequest instanceof MapFindSeatRequest){
			 throw new RuntimeException("No MapFindSeatRequest is supported at this time");
		}

		filterAccommodations(findSeatRequest);
		String reqxml = SSAFindSeatConverter.getRequestXML(findSeatRequest);
		log.info("find seat request : " + singleLineXML(reqxml) );

		Map<String, String> params = new HashMap<String, String>();
		params.put("xmlInput", reqxml );
		String resp =  null;

		try{
			resp = this.getHttpPostService().doPost(getProtocol(), getServerPath(), params, null, false);
		}catch(Exception e ){
			handleError("findSeat", e);
		}
		if( null != resp ){
			try{
				log.info("find seat response : " + removeMeta(resp) );
				findSeatResponse= SSAFindSeatConverter.getFindSeatResponse(resp,findSeatRequest,testCenter);
				findSeatResponse.setSuccessful(true);
				log.info("Number of test dates with seats available: " + findSeatResponse.getTestDatesCount());
				log.info("Number of seats available: " + findSeatResponse.getSeats().size());
				return findSeatResponse;

			}catch(Exception e){
				log.error("",e);
			}
		}

		return findSeatResponse;
	}



	@Override
	public HoldSeatResponse holdSeat(HoldSeatRequest holdSeatRequest)
			throws SchedulingException {

		log.info("inside SSA scheduling hold seat");
		populateSSARequest(holdSeatRequest);
		populateTestCenterAndTestOnSeat(holdSeatRequest.getSeat());

		HoldSeatResponse holdSeatResponse= new HoldSeatResponseImpl();
		String reqxml = SSAHoldSeatConverter.getRequestXML(holdSeatRequest);
		log.info("request : " + singleLineXML(reqxml) );

		Map<String, String> params = new HashMap<String, String>();
		params.put("xmlInput", reqxml );
		String resp =  null;
		try{
			resp = this.getHttpPostService().doPost(getProtocol(), getServerPath(), params, null, false);
		}catch(Exception e ){
			log.error("Exception while holding seat: ",e);
		}
		
		if( null != resp ){
			try{
				log.info("response : " + removeMeta(resp) );
				HeldSeat heldSeat = SSAHoldSeatConverter.getResponse(holdSeatRequest.getSeat(), resp);
				addtoExtendHoldSeatStore(heldSeat);
				holdSeatResponse.setHeldSeat(heldSeat);
				holdSeatResponse.setSuccessful(true);
				return holdSeatResponse;
			}catch(Exception e){
				log.error("", e);
				throw new RuntimeException("error.ereg.scheduling.holdseat.failed");
			}
		}else{
			throw new RuntimeException("error.ereg.scheduling.holdseat.failed");
		}
	}

	@Override
    public ExtendHoldSeatResponse extendHold(ExtendHoldSeatRequest extendHoldSeatRequest){
		log.debug("inside SSA scheduling extend hold seat");
		populateSSARequest(extendHoldSeatRequest);


		String reqxml = SSAExtendHoldSeatConverter.getRequestXML(extendHoldSeatRequest);
		log.debug("request : " + singleLineXML(reqxml) );

		Map<String, String> params = new HashMap<String, String>();
		params.put("xmlInput", reqxml );
		String resp =  null;

		try{
			resp = this.getHttpPostService().doPost(getProtocol(), getServerPath(), params, null, false);
		}catch(Exception e ){
			log.error("Error in extendHold method while calling SSA : ",e);
		}

		if( null != resp ){
			try{
				log.debug("response : " + removeMeta(resp) );
				ExtendHoldSeatResponse exteHoldSeatResponse = SSAExtendHoldSeatConverter.getResponse(extendHoldSeatRequest,resp);
				return exteHoldSeatResponse;

			}catch(Exception e){
				log.error("Error in extendHold method while parsing extend holdseat response", e);
				throw new RuntimeException("error.ereg.scheduling.extendhold.failed");
			}
		}else{
			throw new RuntimeException("error.ereg.scheduling.extendhold.failed");
		}

	}

	@Override
	public ReleaseSeatResponse releaseSeat(ReleaseSeatRequest releaseSeatRequest)
			throws SchedulingException {
		log.debug("inside SSA scheduling release seat");
		populateSSARequest(releaseSeatRequest);
		populateTestCenterAndTestOnSeat(releaseSeatRequest.getHeldSeat());

		ReleaseSeatResponse releaseSeatResponse = new ReleaseSeatResponseImpl();

		String reqxml = SSAReleaseSeatConverter.getRequestXML(releaseSeatRequest);
		log.debug("request : " + singleLineXML(reqxml) );

		Map<String, String> params = new HashMap<String, String>();
		params.put("xmlInput", reqxml );
		String resp =  null;
		try{
			resp = this.getHttpPostService().doPost(getProtocol(), getServerPath(), params, null, false);
		}catch(Exception e )
		{
			log.error("Error in releaseSeat while calling SSA ",e);
		}
		
		if( null != resp ){
			try{
				log.debug("response : " + removeMeta(resp) );
				releaseSeatResponse.setSuccessful(SSAReleaseSeatConverter.getResponse(releaseSeatRequest.getHeldSeat(), resp));
				removeFromExtendHoldSeatStore(releaseSeatRequest.getHeldSeat());
				return releaseSeatResponse;

			}catch(Exception e){
				log.error("Error in releaseSeat while parsing SSA ", e);
				throw new RuntimeException("error.ereg.scheduling.release.failed");
			}
		}else{
			throw new RuntimeException("error.ereg.scheduling.release.failed");
		}
	}

	@Override
	public ReserveSeatResponse reserveSeat(ReserveSeatRequest reserveSeatRequest)
			throws SchedulingException {
		log.debug("inside SSA scheduling reserve seat");
		populateSSARequest(reserveSeatRequest);
		populateTestCenterAndTestOnSeat(reserveSeatRequest.getHeldSeat());

		ReserveSeatResponse reserveSeatResponse = new ReserveSeatResponseImpl();

		String reqxml = SSAReserveSeatConverter.getRequestXML(reserveSeatRequest);
		log.debug("reserve seat request : " + singleLineXML(reqxml) );

		Map<String, String> params = new HashMap<String, String>();
		params.put("xmlInput", reqxml );

		String resp =  null;
		try{
			resp = this.getHttpPostService(1).doPost(getProtocol(), getServerPath(), params, null, false);
		}catch(Exception e ){
			log.error("Error in reserveSeat while calling SSA",e);
		}
		if( null != resp ){
			try{
				log.debug("reserve seat response : " + removeMeta(resp) );
				ReservedSeat reservedSeat = SSAReserveSeatConverter.getResponse(reserveSeatRequest.getHeldSeat(), resp);
				reserveSeatResponse.setReservedSeat(reservedSeat);
				reserveSeatResponse.setSuccessful(true);			
				return reserveSeatResponse;

			}catch(Exception e){
				log.error("Error in reserveSeat while parsing SSA",e);
				throw new RuntimeException("error.ereg.scheduling.reserve.failed");
			}
		}else{
			throw new RuntimeException("error.ereg.scheduling.reserve.failed");
		}

	}

	@Override
	public ReserveSeatResponse reserveSeatWithoutHold(
			ReserveSeatWithoutHoldRequest request) throws SchedulingException {
		log.debug("inside SSA scheduling reserve seat without hold");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CancelSeatResponse cancelSeat(CancelSeatRequest cancelSeatRequest)
			throws SchedulingException {
		log.debug("inside SSA scheduling cancel seat");
		populateSSARequest(cancelSeatRequest);
		populateTestCenterAndTestOnSeat(cancelSeatRequest.getReservedSeat());
		CancelSeatResponse cancelSeatResponse = new CancelSeatResponseImpl();


		String reqxml = SSACancelSeatConverter.getRequestXML(cancelSeatRequest);
		log.debug("cancel seat request : " + singleLineXML(reqxml) );

		Map<String, String> params = new HashMap<String, String>();
		params.put("xmlInput", reqxml );

		String resp =  null;
		try{
			resp = this.getHttpPostService().doPost(getProtocol(), getServerPath(), params, null, false);
		}catch(Exception e ){
			log.error("Error in cancelSeat while calling SSA",e);
		}

		if( null != resp ){
			try{
				log.debug("cancel seat response : " + removeMeta(resp) );
				cancelSeatResponse.setSuccessful(SSACancelSeatConverter.getResponse(cancelSeatRequest.getReservedSeat(), resp));
				return cancelSeatResponse;

			}catch(Exception e){
				log.error("Error in cancelSeat while parsing SSA",e);
				throw new RuntimeException("error.ereg.scheduling.cancel.failed");
			}
		}else{
			throw new RuntimeException("error.ereg.scheduling.cancel.failed");
		}

	}

	private void populateSSARequest(SchedulingRequest schedulingRequest){

		//source and requesting systemid are fields required for SSA only
		// so this two fields are not coming from front end and these are
		//configured as properties of this service using spring
		if( null == schedulingRequest.getSource()){
			schedulingRequest.setSource(this.getSource());
		}
		if ( null == schedulingRequest.getRequestSystemId()){
			schedulingRequest.setRequestSystemId(this.getRequestSystemId());
		}

		 schedulingRequest.setRequesterRoleTypeCode(getRequesterRoleTypeCode());

		 schedulingRequest.setTransactionID("" + transactionPrefix + System.nanoTime());

	}

	private void populateTestCenterAndTest(TCFindSeatRequest tcFindSeatRequest){		
		 tcFindSeatRequest.setDuration((short)tcFindSeatRequest.getTestVariation().getTest().getSchedulingDuration());
		 tcFindSeatRequest.setSchedulingTestCode(tcFindSeatRequest.getTestVariation().getScheduleTestCode());
		 
		 TestCenter testCenter = readTestCenterById(tcFindSeatRequest.getTestCenterIds().get(0));
		 tcFindSeatRequest.setSchedulingTestSiteId(testCenter.getExternalTestCenterId());
	}

	private void populateTestCenterAndTestOnSeat(Seat seat){		
		 seat.setDuration((short)seat.getTestVariation().getTest().getSchedulingDuration());
		 seat.setSchedulingTestCode(seat.getTestVariation().getScheduleTestCode());
	}

	public String singleLineXML (final String xml){

	    final StringWriter sw;

	    try {
	        final OutputFormat format = OutputFormat.createCompactFormat();
	        final org.dom4j.Document document = DocumentHelper.parseText(xml);
	        sw = new StringWriter();
	        final XMLWriter writer = new XMLWriter(sw, format);
	        writer.write(document);
	    }
	    catch (Exception e) {
	        return xml;
	    }

	    return removeMeta( sw.toString());
	}

	private String removeMeta(String xml ){

	    if( xml.contains("<SSA_Scheduling_SOA_Message")){
	    	xml = xml.substring(xml.indexOf("<SSA_Scheduling_SOA_Message"));
	    }

	    return xml;
	}

	private TestCenter readTestCenterById(Long testCenterId){
		 TestCenter testCenter = testCenterDao.readTestCenterById(testCenterId);
		 return testCenter;

	 }

	private void filterAccommodations(FindSeatRequest findSeatRequest){
		 if(findSeatRequest.isHasAccommodations() && findSeatRequest.getCustomerAccommodations()!=null){
			 List<CustomerAccommodationTest> customerAccommodations = findSeatRequest.getCustomerAccommodations();
			 List<CustomerAccommodationTest> customerAccommodationsTest = new ArrayList<CustomerAccommodationTest>();

			 for(CustomerAccommodationTest customerAccommodation : customerAccommodations) {

				 if( (findSeatRequest.getSearchToDate().getTime().compareTo(customerAccommodation.getExpiryDate())<=0)
                                                 && (customerAccommodation.getExpiryDate().compareTo(findSeatRequest.getSearchFromDate().getTime())>=0 )){
					 customerAccommodationsTest.add(customerAccommodation);
				 }else{
					 log.info("Accommodation code: {} description: {} is not falling between search start and end dates",
					 			customerAccommodation.getAccommodationType().getCode(),
					 			customerAccommodation.getAccommodationType().getDescription());
				 }
			 }

			 if(customerAccommodationsTest.size()==0){
				 log.info("No Active Accommodations are falling between search start and end dates");
				 findSeatRequest.setHasAccommodations(false);
			 }
			 findSeatRequest.setCustomerAccommodations(customerAccommodationsTest);
		 }
	}
	private String getRequesterRoleTypeCode(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if(securityContext!=null){
			Authentication authentication = securityContext.getAuthentication();
			if(authentication!=null && authentication.getPrincipal()!=null){
				ERegUser eregUser  = (ERegUser)authentication.getPrincipal();
				if(eregUser!=null){
					if(eregUser.isCustomer()){
						return Constants.STR_CANDIDATE;
					}
					else if (eregUser.hasCSRRole()){
						return Constants.STR_CSR;
					}else if(eregUser.hasTCARole()){
						return Constants.STR_TCA;
					}
				}else{
					log.info("Ereg user is null");
				}
			}
		}
		return Constants.STR_OTHER;
	}

	private void addtoExtendHoldSeatStore(HeldSeat heldSeat){
		log.debug("addtoExtendHoldSeatStore {}", heldSeat);
		ExtendHoldSeatRequest extendHoldSeatRequest = new ExtendHoldSeatRequestImpl();
		extendHoldSeatRequest.setHoldCode(heldSeat.getHoldCode());
		extendHoldSeatRequest.setHoldDuration(heldSeat.getHoldDuration());
		extendHoldSeatRequest.setHoldSource(heldSeat.getHoldSource());
		extendHoldSeatRequest.setSiteCode(heldSeat.getSiteCode());
		//TODO confirm we shd use test variation
		//extendHoldSeatRequest.setTestId(heldSeat.getTestId());
		extendHoldSeatRequest.setTestVariation(heldSeat.getTestVariation());
		extendHoldSeatRequest.setSchedulingTestCode(heldSeat.getSchedulingTestCode());

		extendHoldSeatStore.addExtendHold(extendHoldSeatRequest);
	}

	private void removeFromExtendHoldSeatStore(HeldSeat heldSeat){
		log.debug("removeFromExtendHoldSeatStore {}", heldSeat);
		extendHoldSeatStore.removeExtendHold(heldSeat.getTestVariation().getTest().getProgramType().getCode(), heldSeat.getTestVariation().getTest().getTestId(), heldSeat.getSiteCode(), heldSeat.getHoldCode());
	}

}
