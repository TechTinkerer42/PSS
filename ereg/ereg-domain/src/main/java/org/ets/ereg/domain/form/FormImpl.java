package org.ets.ereg.domain.form;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.accommodation.AccommodationTypeImpl;
import org.ets.ereg.domain.common.DeliveryModeTypeImpl;
import org.ets.ereg.domain.common.LanguageTypeImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.form.FormType;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.test.TestImpl;

@Entity
@Table(name = "FRM")
public class FormImpl implements Form, Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FRM_ID")
    private Long id;

    /*@ManyToOne(fetch = FetchType.LAZY,targetEntity = TestVariationImpl.class)
    @JoinColumns({
        @JoinColumn(name = "TST_ID", referencedColumnName = "TST_ID", nullable = false),
        @JoinColumn(name = "LANG_CDE", referencedColumnName = "LANG_CDE", nullable = false),
        @JoinColumn(name = "DLVY_MDE_CDE", referencedColumnName = "DLVY_MDE_CDE", nullable = false)})
    private TestVariation testVariation;*/

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = TestImpl.class)
    @JoinColumn(name = "TST_ID", referencedColumnName = "TST_ID", nullable = false)
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = LanguageTypeImpl.class)
    @JoinColumn(name = "LANG_CDE" )
    private LanguageType lang;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DeliveryModeTypeImpl.class)
    @JoinColumn(name = "DLVY_MDE_CDE" )
    private DeliveryModeType dlvyMode;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = FormImpl.class)
    @JoinColumn(name = "PARNT_FRM_ID")
    private Form parentForm;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = FormTypeImpl.class)
    @JoinColumn(name = "FRM_TYP_CDE" )
    private FormType formType;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = AccommodationTypeImpl.class)
	@JoinColumn(name = "ACMDTN_TYP_CDE")
	private AccommodationType accommodation;

	@Column(name = "EFF_DTE",  nullable = false)
	private Date effDate;

	@Column(name = "EXPRTN_DTE")
	private Date expiryDate;

	@Column(name = "FRM_DSC",  nullable = false, length = 175)
	private String formDesc;

	@Column(name = "FRM_CDE", length = 20)
	private String formCode;

	@Override
	public void setFormID(Long id) {
		this.id = id;
	}

	@Override
	public Long getFormID() {
		return id;
	}

	/*@Override
	public TestVariation getTestVariation() {
        return testVariation;
    }

	@Override
    public void setTestVariation(TestVariation testVariation) {
        this.testVariation = testVariation;
    }*/

	@Override
	public void setParentFormID(Form parentForm) {
		this.parentForm = parentForm;
	}

	@Override
	public Form getParentFormID() {
		return parentForm;
	}

	@Override
	public void setFormType(FormType formType) {
		this.formType = formType;
	}

	@Override
	public FormType getFormType() {
		return formType;
	}

	@Override
	public AccommodationType getAccommodation() {
		return accommodation;
	}

	@Override
	public void setAccommodation(AccommodationType accommodation) {
		this.accommodation = accommodation;
	}

	@Override
	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	@Override
	public Date getEffDate() {
		return effDate;
	}

	@Override
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public Date getExpiryDate() {
		return expiryDate;
	}

	@Override
	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

	@Override
	public String getFormDesc() {
		return formDesc;
	}

	@Override
	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	@Override
	public String getFormCode() {
		return formCode;
	}

	@Override
    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public Test getTest() {
        return test;
    }

    @Override
    public void setLangCode(LanguageType lang) {
        this.lang = lang;
    }

    @Override
    public LanguageType getLangCode() {
        return lang;
    }

    @Override
    public void setDlvyMode(DeliveryModeType dlvyMode) {
        this.dlvyMode = dlvyMode;
    }

    @Override
    public DeliveryModeType getDlvyMode() {
        return dlvyMode;
    }
}
