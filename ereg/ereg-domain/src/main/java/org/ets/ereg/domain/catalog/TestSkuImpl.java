package org.ets.ereg.domain.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.ets.ereg.domain.interfaces.model.catalog.TestSku;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.test.TestVariationImpl;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TST_SKU")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="etsStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Test SKU")
public class TestSkuImpl extends ETSSkuImpl implements TestSku {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = TestVariationImpl.class)
	@JoinColumns({
		@JoinColumn(name = "TST_ID", referencedColumnName = "TST_ID", nullable = false),
		@JoinColumn(name = "LANG_CDE", referencedColumnName = "LANG_CDE", nullable = false),
		@JoinColumn(name = "DLVY_MDE_CDE", referencedColumnName = "DLVY_MDE_CDE", nullable = false)})
    private TestVariation testVariation;

    @Override
    public TestVariation getTestVariation() {
        return testVariation;
    }

    @Override
    public void setTestVariation(TestVariation testVariation) {
        this.testVariation = testVariation;
    }




}
