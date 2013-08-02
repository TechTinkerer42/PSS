package org.ets.ereg.domain.accommodation;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationTypeValue;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.accommodation.ProgramAccommodationDeliveryMode;
import org.ets.ereg.domain.interfaces.model.accommodation.id.CustomerAccommodationTestId;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.ets.ereg.domain.test.TestImpl;

@Entity
@Table(name = "CUST_ACMDTN_TST")
public class CustomerAccommodationTestImpl implements Serializable,
CustomerAccommodationTest {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "customerId", column = @Column(name = "CUSTOMER_ID", nullable = false)),
        @AttributeOverride(name = "testId", column = @Column(name = "TST_ID", nullable = false)),
        @AttributeOverride(name = "deliveryModeCode", column = @Column(name = "DLVY_MDE_CDE", nullable = false)),
        @AttributeOverride(name = "accommodationTypeCode", column = @Column(name = "ACMDTN_TYP_CDE", nullable = false)) })
    private CustomerAccommodationTestId customerAccommodationTestId = new CustomerAccommodationTestId();

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ETSCustomerImpl.class)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false, insertable = false, updatable = false)
    private ETSCustomer customer;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = TestImpl.class)
    @JoinColumn(name = "TST_ID", referencedColumnName = "TST_ID", nullable = false, insertable = false, updatable = false)
    private Test test;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = AccommodationTypeValueImpl.class)
    @JoinColumn(name = "ACMDTN_TYP_VAL_ID_NO", referencedColumnName = "ACMDTN_TYP_VAL_ID_NO")
    private AccommodationTypeValue accommodationTypeValue;


    @Temporal(TemporalType.DATE)
    @Column(name = "EXPRTN_DTE" , nullable = false)
    private Date expiryDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "APRVD_DTE",  nullable = false)
    private Date approvedDate;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = ProgramAccommodationDeliveryModeImpl.class)
    @JoinColumns({
        @JoinColumn(name = "PGM_CDE", referencedColumnName = "PGM_CDE", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "DLVY_MDE_CDE", referencedColumnName = "DLVY_MDE_CDE", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "ACMDTN_TYP_CDE", referencedColumnName = "ACMDTN_TYP_CDE", nullable = false, insertable = false, updatable = false) })
    private ProgramAccommodationDeliveryMode programAccommodationDeliveryMode;

    @Column(name = "OTHR_ACMDTN_TYP_TXT")
    private String otherAccommodationTypeText;

    @Override
    public CustomerAccommodationTestId getCustomerAccommodationTestId() {
        return customerAccommodationTestId;
    }

    @Override
    public void setCustomerAccommodationTestId(
            CustomerAccommodationTestId customerAccommodationTestId) {
        this.customerAccommodationTestId = customerAccommodationTestId;
    }

    @Override
    public ETSCustomer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(ETSCustomer customer) {
        this.customer = customer;
    }

    @Override
    public Test getTest() {
        return test;
    }

    @Override
    public void setTest(Test test) {
        this.test = test;
    }


    @Override
    public Date getExpiryDate() {
        return expiryDate;
    }

    @Override
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public Date getApprovedDate() {
        return approvedDate;
    }

    @Override
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    @Override
    public AccommodationTypeValue getAccommodationTypeValue() {
        return accommodationTypeValue;
    }

    @Override
    public void setAccommodationTypeValue(
            AccommodationTypeValue accommodationTypeValue) {
        this.accommodationTypeValue = accommodationTypeValue;
    }

    @Override
    public ProgramAccommodationDeliveryMode getProgramAccommodationDeliveryMode() {
        return programAccommodationDeliveryMode;
    }

    @Override
    public void setProgramAccommodationDeliveryMode(
            ProgramAccommodationDeliveryMode programAccommodationDeliveryMode) {

        this.programAccommodationDeliveryMode = programAccommodationDeliveryMode;

    }

    @Override
    public String getOtherAccommodationTypeText() {
        return otherAccommodationTypeText;
    }

    @Override
    public void setOtherAccommodationTypeText(String otherAccommodationTypeText) {
        this.otherAccommodationTypeText = otherAccommodationTypeText;
    }

    @Override
    public AccommodationType getAccommodationType() {
        return getProgramAccommodationDeliveryMode().getAccommodationType();
    }


}
