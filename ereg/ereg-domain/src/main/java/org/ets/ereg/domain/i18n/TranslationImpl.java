package org.ets.ereg.domain.i18n;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.common.LanguageTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.i18n.InternationalContent;
import org.ets.ereg.domain.interfaces.model.i18n.Translation;
import org.ets.ereg.domain.interfaces.model.i18n.id.TranslationId;

@Entity
@Table(name="TRNSLTN")
public class TranslationImpl implements Translation, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "languageCode", column = @Column(name = "LANG_CDE", nullable = false, length=5)),
			@AttributeOverride(name = "internationalConentId", column = @Column(name = "INTL_CNTNT_ID", nullable = false))
	})	
	private TranslationId translationId = new TranslationId();
	
	@Column(name="TRNSLTN_TXT",nullable=false)
	@Lob
	private String translationTxt;

	@ManyToOne(fetch = FetchType.LAZY,targetEntity=LanguageTypeImpl.class)
	@JoinColumn(name = "LANG_CDE", nullable = false, insertable = false, updatable = false)
	private LanguageType languageTypeCode;
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=InternationalContentImpl.class)
	@JoinColumn(name = "INTL_CNTNT_ID", nullable = false, insertable = false, updatable = false)
	private InternationalContent internationalConentId;	
	
	@Override
	public TranslationId getTranslationId() {
		return translationId;
	}

	@Override
	public void setTranslationId(TranslationId translationId) {
		this.translationId = translationId;
	}

	@Override
	public String getTranslationTxt() {
		return translationTxt;
	}

	@Override
	public void setTranslationTxt(String translationTxt) {
		this.translationTxt = translationTxt;
	}

}
