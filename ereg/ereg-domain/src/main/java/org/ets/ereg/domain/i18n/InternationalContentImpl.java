package org.ets.ereg.domain.i18n;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.i18n.ContentType;
import org.ets.ereg.domain.interfaces.model.i18n.InternationalContent;


@Entity
@Table(name="INTL_CNTNT")
public class InternationalContentImpl implements InternationalContent,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="INTL_CNTNT_ID",nullable=false)
	private Long internationalContentId;
	
	@JoinColumn(name="CNTNT_TYP_CDE")
	@ManyToOne(fetch = FetchType.LAZY,targetEntity = ContentTypeImpl.class, optional=true)
	private ContentType contentType;
	
//	@OneToMany(mappedBy="internationalConentId", targetEntity= TranslationImpl.class,fetch = FetchType.LAZY,cascade=CascadeType.ALL)	
//	private Set<Translation> translationTexts;
	
	@Override
	public Long getInternationalContentId() {
		return internationalContentId;
	}

	@Override
	public void setInternationalContentId(Long internationalContentId) {
		this.internationalContentId = internationalContentId;
	}

	@Override
	public ContentType getContentTypeCode() {
		return contentType;
	}

	@Override
	public void setContentTypeCode(ContentType contentType) {
		this.contentType = contentType;
	}

//	@Override
//	public Set<Translation> getTranslationTexts() {
//		return translationTexts;
//	}
//
//	@Override
//	public void setTranslationTexts(Set<Translation> translationTexts) {
//		this.translationTexts = translationTexts;
//	}	

}
