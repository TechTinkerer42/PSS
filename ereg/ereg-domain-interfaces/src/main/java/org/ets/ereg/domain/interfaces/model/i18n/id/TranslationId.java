package org.ets.ereg.domain.interfaces.model.i18n.id;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TranslationId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="LANG_CDE",nullable=false)
	private String languageCode;
	
	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Long getInternationalConentId() {
		return internationalConentId;
	}

	public void setInternationalConentId(Long internationalConentId) {
		this.internationalConentId = internationalConentId;
	}

	@Column(name="INTL_CNTNT_ID",nullable=false)
	private Long internationalConentId;
	
	@Override
	public int hashCode(){
		
		return new HashCodeBuilder().append(languageCode)
					.append(internationalConentId).hashCode();
	}
	
	@Override
	public boolean equals(final Object obj){
		
		if(obj instanceof TranslationId){
			
			final TranslationId other = (TranslationId) obj;
			
			return new EqualsBuilder().append(languageCode, other.languageCode)
					.append(internationalConentId, other.internationalConentId)
					.isEquals();
			
		}else{
			return false;
		}
	}
	
	

}
