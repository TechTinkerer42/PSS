package org.ets.ereg.domain.booking;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.ets.ereg.domain.form.FormImpl;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.BookingAccommodation;
import org.ets.ereg.domain.interfaces.model.booking.BookingStatusType;
import org.ets.ereg.domain.interfaces.model.booking.EventOutComeStatus;
import org.ets.ereg.domain.interfaces.model.booking.HeldBooking;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.order.TestDiscreteOrderItemImpl;
import org.ets.ereg.domain.scheduling.TestCenterImpl;
import org.ets.ereg.domain.scheduling.TestTakerTestImpl;
import org.ets.ereg.domain.test.TestVariationImpl;

@Entity
@Table(name = "BKNG")
public class BookingImpl implements Booking,Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "BookingId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "BookingId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "BookingImpl", allocationSize = 50)
    @Column(name = "BKNG_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = TestTakerTestImpl.class)
    @JoinColumn(name = "TST_TKR_TST_ID")
    private TestTakerTest testTakerTest;
    
	//TODO Uncomment when implemented correctly
    /*
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TestDiscreteOrderItemImpl.class)
    @JoinTable(name = "TST_DSCRT_ORD_ITM_BKNG", joinColumns = @JoinColumn(name = "BKNG_ID"), inverseJoinColumns = @JoinColumn(name = "ORDER_ITEM_ID"))
    private TestDiscreteOrderItem testDiscreteOrderItem;
*/
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = FormImpl.class)
    @JoinColumn(name = "FRM_ID")
    private Form form;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = TestVariationImpl.class)
    @JoinColumns({
        @JoinColumn(name = "TST_ID", referencedColumnName = "TST_ID", nullable = false),
        @JoinColumn(name = "LANG_CDE", referencedColumnName = "LANG_CDE", nullable = false),
        @JoinColumn(name = "DLVY_MDE_CDE", referencedColumnName = "DLVY_MDE_CDE", nullable = false)})
    private TestVariation testVariation;

	@Column(name = "APNTMT_DT", nullable = false)
    private Date apptDate;
    
    @Column(name = "TM_ZN_OFST_QTY", nullable = false)
    private Float timeZoneOffsetQuantity;

    @Column(name = "ETS_APNTMT_ID" )
    private String  etsApptID;

    @Column(name = "EXTRNL_APNTMT_ID" )
    private String  extrnlApptID;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = BookingStatusTypeImpl.class)
    @JoinColumn(name = "BKNG_STS_TYP_CDE")
    private BookingStatusType bookingStatusType;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = TestCenterImpl.class)
    @JoinColumn(name = "TST_CNTR_ID_NO")
    private TestCenter testCenter;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = EventOutComeStatusImpl.class)
    @JoinColumn(name = "EVNT_OTCM_STS_TYP_CDE")
    private EventOutComeStatus evntOutComeStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="booking", targetEntity=BookingAccommodationImpl.class, cascade={CascadeType.ALL})
    private Set<BookingAccommodation> bookingAccommodations = new HashSet<BookingAccommodation>();

    @Column(name = "CUST_FST_NAM")
    private String firstName;

    @Column(name = "CUST_MID_NAM")
    private String midName;

    @Column(name = "CUST_LST_NAM")
    private String lastName;

    @Column(name = "CUST_BRTH_DT")
    private Date  DOB;

    @Column(name = "TST_LNCH_TM")
    private Date  tstLnchTime;

    @Column(name = "TST_PKG_ID")
    private String tstPackageId;

    @Column(name = "TST_DURN")
    private Long duration;

    @Column(name = "CMNT")
    private String strComments;

    @Column(name = "REGN_SYS_ID")
    private String registrationSystemId;

    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY,targetEntity=HeldBookingImpl.class)
    @PrimaryKeyJoinColumn
    private HeldBooking heldBooking;

    @Column(name="APPT_DTE_STR")
    private String appointmentDateString;

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setTestTakerTestId (TestTakerTest tttId) {
		this.testTakerTest = tttId;
	}

	@Override
	public TestTakerTest getTestTakerTestId () {
		return testTakerTest;
	}

	@Override
	public void setForm(Form form) {
		this.form = form;
	}

	@Override
	public Form getForm() {
		return form;
	}


	@Override
	public Date getAppointmentDateTime() {
		return apptDate;
	}

	@Override
	public void setAppointmentDateTime(Date apptDate) {
		this.apptDate = apptDate;
	}
	
	@Override
	public Float getTimeZoneOffsetQuantity() {
		return timeZoneOffsetQuantity;
	}

	@Override
	public void setTimeZoneOffsetQuantity(Float timeZoneOffsetQuantity) {
		this.timeZoneOffsetQuantity = timeZoneOffsetQuantity;
	}

	@Override
	public String getEtsApptID() {
        return etsApptID;
    }

	@Override
    public void setEtsApptID(String etsApptID) {
        this.etsApptID = etsApptID;
    }

	@Override
	public TestVariation getTestVariation() {
        return testVariation;
    }

	@Override
    public void setTestVariation(TestVariation testVariation) {
        this.testVariation = testVariation;
    }

	@Override
	public void setExtrnlAppointmentID(String extrnlApptID) {
		this.extrnlApptID = extrnlApptID;
	}

	    @Override
	public String getExtrnlAppointmentID() {
		return extrnlApptID;
	}

	@Override
	public void setBookingStatusType(BookingStatusType bookingStatusType) {
		this.bookingStatusType = bookingStatusType;
	}

	@Override
	public BookingStatusType getBookingStatusType() {
		return bookingStatusType;
	}

	@Override
	public void setTestCenter(TestCenter testCenter) {
		this.testCenter = testCenter;
	}

	@Override
	public TestCenter getTestCenter() {
		return testCenter;
	}

	@Override
	public void setEvntOutComeStatus(EventOutComeStatus evntOutComeStatus) {
		this.evntOutComeStatus = evntOutComeStatus;
	}

	@Override
	public EventOutComeStatus getEvntOutComeStatus() {
		return evntOutComeStatus;
	}

	@Override
	public void setCustFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getCustFirstName() {
		return firstName;
	}

	@Override
	public void setCustMidtname(String midName) {
		this.midName = midName;
	}

	@Override
	public String getCustMidName() {
		return midName;
	}

	@Override
	public void setCustLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getCustLastName() {
		return lastName;
	}

	@Override
	public void setCustDOB(Date DOB) {
		this.DOB = DOB;
	}

	@Override
	public Date getCustDOB() {
		return DOB;
	}

	@Override
	public void setTstLnchTime(Date tstLnchTime) {
		this.tstLnchTime = tstLnchTime;
	}

	@Override
	public Date getTstLnchTime() {
		return tstLnchTime;
	}

	@Override
	public void setTstPackageId(String tstPackageId) {
		this.tstPackageId = tstPackageId;
	}

	@Override
	public String getTstPackageId() {
		return tstPackageId;
	}

	@Override
	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Override
	public Long getDuration() {
		return duration;
	}

	@Override
	public void setComments(String strComments) {
		this.strComments = strComments;
	}

	@Override
	public String getComments() {
		return strComments;
	}

	@Override
    public Set<BookingAccommodation> getBookingAccommodations() {
        return bookingAccommodations;
    }

	@Override
    public void setBookingAccommodations(
            Set<BookingAccommodation> bookingAccommodations) {
        this.bookingAccommodations = bookingAccommodations;
    }

	@Override
    public String getRegistrationSystemId() {
        return registrationSystemId;
    }

	@Override
    public void setRegistrationSystemId(String registrationSystemId) {
        this.registrationSystemId = registrationSystemId;
    }

	@Override
	public HeldBooking getHeldBooking() {
		return heldBooking;
	}

	@Override
	public void setHeldBooking(HeldBooking heldBooking) {
		this.heldBooking = heldBooking;
	}


	public Agency getAgency(String programCode) {
		return((getTestCenter() == null)? null : getTestCenter().getAgency(programCode));
	}

	@Override
	public String getAppointmentDateString() {
		return appointmentDateString;
	}

	@Override
	public void setAppointmentDateString(String appointmentDateString) {
		this.appointmentDateString = appointmentDateString;
	}

	/**
	 * @return the testDiscreteOrderItems
	 */
	//TODO Uncomment when implemented correctly
	/*
	public TestDiscreteOrderItem getTestDiscreteOrderItem() {
		return testDiscreteOrderItem;
	}
*/
	/**
	 * @param testDiscreteOrderItems the testDiscreteOrderItems to set
	 */
	//TODO Uncomment when implemented correctly
	/*
	public void setTestDiscreteOrderItem(
			TestDiscreteOrderItem testDiscreteOrderItem) {
		this.testDiscreteOrderItem = testDiscreteOrderItem;
	}
	*/

}
