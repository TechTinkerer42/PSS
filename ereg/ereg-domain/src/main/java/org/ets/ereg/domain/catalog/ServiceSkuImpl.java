package org.ets.ereg.domain.catalog;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.model.catalog.ServiceSku;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SVC_SKU")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="etsStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Service SKU")
public class ServiceSkuImpl extends ETSSkuImpl implements ServiceSku {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = ProgramTypeImpl.class)
    @JoinColumn(name = "PGM_CDE", nullable = false, insertable = false, updatable = false)
    private ProgramType programCode;

    @Override
    public ProgramType getProgramCode() {
        return programCode;
    }

    @Override
    public void setProgramCode(ProgramType programCode) {
        this.programCode = programCode;
    }
}
