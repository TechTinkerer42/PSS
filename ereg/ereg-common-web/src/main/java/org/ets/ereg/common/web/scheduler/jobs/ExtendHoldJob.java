package org.ets.ereg.common.web.scheduler.jobs;

import java.util.List;

import org.ets.ereg.common.business.scheduling.service.SchedulingService;
import org.ets.ereg.domain.interfaces.scheduling.response.ExtendHoldSeatResponse;
import org.ets.ereg.scheduling.vo.ExtendHoldSeatInfo;
import org.ets.ereg.scheduling.vo.ExtendHoldSeatStore;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ExtendHoldJob extends QuartzJobBean  {
	private static final Logger LOG = LoggerFactory.getLogger(ExtendHoldJob.class);
	private SchedulingService schedulingService;
	private ExtendHoldSeatStore extendHoldSeatStore;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		LOG.debug("inside executeInternal");
		List<ExtendHoldSeatInfo> allExtendHoldSeats = extendHoldSeatStore.getAllExtendHoldSeats();
		if(allExtendHoldSeats != null){
			LOG.info("number of holds in store {}", allExtendHoldSeats.size());
			int numberOfExtendsSuccessful = 0;
			int numberOfExtendsFailed = 0;
			int numberOfExtendsSkipped = 0;
			for(ExtendHoldSeatInfo extendHoldSeatInfo : allExtendHoldSeats){
				long timeSinceFirstHold = System.currentTimeMillis() - extendHoldSeatInfo.getFirstHoldTime(); 
				long timeSinceLastHold = System.currentTimeMillis() - extendHoldSeatInfo.getLastHoldTime();
				// if the last hold is more than 15 min and the first hold is less than 6 hours
				if( timeSinceLastHold > 900000 && timeSinceFirstHold < 21600000){
					LOG.debug("calling extend hold seat");
					try{
						ExtendHoldSeatResponse response = schedulingService.extendHold(extendHoldSeatInfo.getExtendHoldSeatRequest());
						if(response != null){
							if(response.isSuccessful()){
								LOG.debug("extend hold seat is sucessful");
								extendHoldSeatInfo.setLastHoldTime(System.currentTimeMillis());
								numberOfExtendsSuccessful++;
							}else{
								LOG.error("extend hold seat failed");
								numberOfExtendsFailed++;
							}
						}
						
					}catch(Exception e){
						LOG.error("error in extending hold seat", e);
					}
				}else{
					numberOfExtendsSkipped++;
				}
			}
			LOG.info("number of hold seat extends called : {}", numberOfExtendsSuccessful+numberOfExtendsFailed);
			LOG.info("number of hold seat extends successful : {}", numberOfExtendsSuccessful);
			LOG.info("number of hold seat extends failed : {}", numberOfExtendsFailed);
			LOG.info("number of hold seat extends skipped : {}", numberOfExtendsSkipped);
		}
	}

	public SchedulingService getSchedulingService() {
		return schedulingService;
	}

	public void setSchedulingService(SchedulingService schedulingService) {
		this.schedulingService = schedulingService;
	}

	public ExtendHoldSeatStore getExtendHoldSeatStore() {
		return extendHoldSeatStore;
	}

	public void setExtendHoldSeatStore(ExtendHoldSeatStore extendHoldSeatStore) {
		this.extendHoldSeatStore = extendHoldSeatStore;
	}
	
}
