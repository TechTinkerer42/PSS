package org.ets.ereg.common.business.vo.biq;


public class DemographicResponseVO {
	private String possibleResponse; // response text	
	private Long seqNo; // response seqno
	private Long respNo;	
		
	public Long getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}
	public String getPossibleResponse() {
		return possibleResponse;
	}
	public void setPossibleResponse(String possibleResponse) {
		this.possibleResponse = possibleResponse;
	}	
	
	
	public Long getRespNo() {
		return respNo;
	}
	public void setRespNo(Long respNo) {
		this.respNo = respNo;
	}
	
}
