package org.ets.ereg.domain.interfaces.model.i18n;

import java.io.Serializable;

public interface InternationalContent extends Serializable {

	public Long getInternationalContentId();
	public void setInternationalContentId(Long internationalContentId);
	
	public ContentType getContentTypeCode();
	public void setContentTypeCode(ContentType contentType);
		
//	public Set<Translation> getTranslationTexts();
//	public void setTranslationTexts(Set<Translation> translationTexts);
	
}
