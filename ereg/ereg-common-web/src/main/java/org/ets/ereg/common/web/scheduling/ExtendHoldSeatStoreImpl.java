package org.ets.ereg.common.web.scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest;
import org.ets.ereg.scheduling.vo.ExtendHoldSeatInfo;
import org.ets.ereg.scheduling.vo.ExtendHoldSeatStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This stores the list of holds in a spring application context for extending the hold
 * @author ZABDUL
 *
 */
public class ExtendHoldSeatStoreImpl implements ExtendHoldSeatStore {

	private static final Logger LOG = LoggerFactory.getLogger(ExtendHoldSeatStoreImpl.class);

	private final Map<String, List<ExtendHoldSeatInfo>> extendHoldsMap = new HashMap<String, List<ExtendHoldSeatInfo>>();

	/* (non-Javadoc)
	 * @see org.ets.ereg.scheduling.vo.ExtendHoldSeatStore#addExtendHold(org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest)
	 */
	@Override
	public void addExtendHold(ExtendHoldSeatRequest extendHoldSeatRequest){
		ExtendHoldSeatInfo extendHoldSeatInfo = new ExtendHoldSeatInfo(extendHoldSeatRequest);
		String sessionId = getSessionId();
		List<ExtendHoldSeatInfo> extendHolds;
		if(sessionId != null){
			extendHolds = extendHoldsMap.get(sessionId);
			if(extendHolds == null){
				extendHolds = new ArrayList<ExtendHoldSeatInfo>();
				extendHoldsMap.put(sessionId, extendHolds);
			}
			LOG.debug("{} added to the hold seat store", extendHoldSeatRequest);
			extendHolds.add(extendHoldSeatInfo);
		}
	}

	/* (non-Javadoc)
	 * @see org.ets.ereg.scheduling.vo.ExtendHoldSeatStore#removeExtendHold(long)
	 */
	@Override
	public void removeExtendHold(String programCode, Long testId, String siteCode, String holdCode){
		LOG.debug("removeExtendHold " +  programCode + "," + testId + "," + siteCode + ","  + holdCode);
		String sessionId = getSessionId();
		List<ExtendHoldSeatInfo> extendHolds;
		if(sessionId != null){
			extendHolds = extendHoldsMap.get(sessionId);
			int matchingIndex = -1;
			if(extendHolds != null){
				for(ExtendHoldSeatInfo extendHold : extendHolds){
					if(programCode.equals(extendHold.getExtendHoldSeatRequest().getTestVariation().getTest().getProgramType().getCode())
							&& testId.equals(extendHold.getExtendHoldSeatRequest().getTestVariation().getTest().getTestId())
							&& siteCode.equals(extendHold.getExtendHoldSeatRequest().getSiteCode())
							&& holdCode.equals(extendHold.getExtendHoldSeatRequest().getHoldCode())){
						matchingIndex = extendHolds.indexOf(extendHold);
					}
				}
				LOG.debug("matchingIndex is {}", matchingIndex);
				if(matchingIndex != -1){
					extendHolds.remove(matchingIndex);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.ets.ereg.scheduling.vo.ExtendHoldSeatStore#getAllExtendHoldSeats()
	 */
	@Override
	public List<ExtendHoldSeatInfo> getAllExtendHoldSeats(){
		List<ExtendHoldSeatInfo> allExtendHolds = new ArrayList<ExtendHoldSeatInfo>();
		for(List<ExtendHoldSeatInfo> extendHolds : extendHoldsMap.values()){
			allExtendHolds.addAll(extendHolds);
		}
		return allExtendHolds;
	}


	/* (non-Javadoc)
	 * @see org.ets.ereg.scheduling.vo.ExtendHoldSeatStore#removeExtendHoldsForCurrentSession()
	 */
	@Override
	public void removeExtendHoldsForCurrentSession(){
		String sessionId = getSessionId();
		LOG.debug(" remove session {} from ExtendHolds", sessionId);
		if(sessionId != null){
			extendHoldsMap.remove(sessionId);
		}
	}

	@Override
	public void removeExtendHoldsForSession(String sessionId){
		LOG.debug(" remove session {} from ExtendHolds", sessionId);
		if(sessionId != null){
			extendHoldsMap.remove(sessionId);
		}
	}

	public String getSessionId() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session = attr.getRequest().getSession(false);
	    if(session != null){
	    	return session.getId();
	    }else{
	    	// this should not happen
	    	LOG.error("session id is null");
	    }
	    return null;
	}
}
