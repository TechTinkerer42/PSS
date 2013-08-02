package org.ets.ereg.domain.interfaces.model.i18n;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.i18n.id.TranslationId;

public interface Translation extends Serializable{

	public TranslationId getTranslationId();
	public void setTranslationId(TranslationId translationId);
	
	public String getTranslationTxt();
	public void setTranslationTxt(String translationTxt);
	
}
