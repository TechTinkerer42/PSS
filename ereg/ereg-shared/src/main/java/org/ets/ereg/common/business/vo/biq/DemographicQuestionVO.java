package org.ets.ereg.common.business.vo.biq;



import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionRespType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemographicQuestionVO{
	
	private static Logger LOG = LoggerFactory.getLogger(DemographicQuestionVO.class);
	private Long setId;
	private Long seqNo; //seq number based on customer sort flag
 	private Long qstnNo;	
	private String responseType; //question resp type code
	private boolean responseRequired; 
	private String displayText;	//actual question text
	private List<DemographicResponseVO> demographicResponses;	
	private List<DmgrphQstnTriggerVO> dependentQuestionVO;
	
	
	private String[] selectedResponseIds;
	
	private boolean gotTriggered;
	
	
	public String[] getSelectedResponseIds() {
		return selectedResponseIds;
	}

	public void setSelectedResponseIds(String[] selectedResponseIds) {
		if(selectedResponseIds != null)
		{
			this.selectedResponseIds = selectedResponseIds.clone();
		}
	}

	private String freeFormAnswer;
	private boolean independent;
	

	public String getTriggerArray(){
		boolean firstComma=false;
		StringBuffer returnString=new StringBuffer("[");
		if(null!=dependentQuestionVO)
			{for(Iterator<DmgrphQstnTriggerVO> i=dependentQuestionVO.iterator(); i.hasNext();){
				if(firstComma){
					returnString.append(",");
				}
				returnString.append(i.next().getTriggerArrayElement());
				firstComma=true;
			}
		}
		returnString.append("]");
		
		return returnString.toString();
	}
	
	public String getFirstSelectedResponse(){
		if (null != selectedResponseIds && selectedResponseIds.length > 0)
		return selectedResponseIds[0];
		else
			return null;

	}
	
	public String setFirstSelectedResponse(String responseId){
		LOG.debug("setFirstSelectedResponse called to set:{}",responseId);
		return selectedResponseIds[0] =responseId;
	}
	
	public Long getSetId() {
		return setId;
	}
	public void setSetId(Long setId) {
		this.setId = setId;
	}
	public String getFreeFormAnswer() {
		return freeFormAnswer;
	}
	public void setFreeFormAnswer(String freeFormAnswer) {
		this.freeFormAnswer = freeFormAnswer;
	}
	
	
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public boolean isResponseRequired() {
		return responseRequired;
	}
	public void setResponseRequired(boolean responseRequired) {
		this.responseRequired = responseRequired;
	}
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}	
	public Long getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}
	
	public boolean isIndependent() {
		return independent;
	}
	
	public boolean getIndependent() {
		return independent;
	}
	public void setIndependent(boolean independent) {
		this.independent = independent;
	}
	
	public List<DemographicResponseVO> getDemographicResponses() {
		return demographicResponses;
	}
	public void setDemographicResponses(
			List<DemographicResponseVO> demographicResponses) {
		this.demographicResponses = demographicResponses;
	}	
	

	public List<DmgrphQstnTriggerVO> getDependentQuestionVO() {
		return dependentQuestionVO;
	}
	public void setDependentQuestionVO(List<DmgrphQstnTriggerVO> dependentQuestionVO) {
		this.dependentQuestionVO = dependentQuestionVO;
	}
	public Long getQstnNo() {
		return qstnNo;
	}
	public void setQstnNo(Long qstnNo) {
		this.qstnNo = qstnNo;
	}
	
	public boolean isDisplayed(){
        return (!independent && isAnswered()) || independent||isGotTriggered();
	}
	
	public boolean isAnswered(){
		if( (selectedResponseIds!=null 
				&& selectedResponseIds.length>0
				&& StringUtils.isNotBlank(selectedResponseIds[0])
				||(StringUtils.isNotBlank(freeFormAnswer)))
				){
			return true;	
		}
		return false;
	}

	public boolean isGotTriggered() {
		return gotTriggered;
	}

	public void setGotTriggered(boolean gotTriggered) {
		this.gotTriggered = gotTriggered;
	}
	
	
	

	public String getResponse(){
		StringBuffer answers=  new StringBuffer();//= new String[1];
		if(this.isAnswered()){
			if(DemographicQuestionRespType.respType.FREEFORM.
					getResponseType().equals(this.getResponseType())){
				answers=  new StringBuffer(displayText);				
				
			}
			else {
				for(DemographicResponseVO resp:demographicResponses){
					for(int i=0;i<this.selectedResponseIds.length;i++){
						if(0==resp.getRespNo().compareTo(Long.parseLong(this.selectedResponseIds[i]))){
							answers.append(resp.getPossibleResponse());
							answers.append("</br>");
						}
					}
				
				}
			
			}
	
		}
		return answers.toString();
	}
	
	
	
}
