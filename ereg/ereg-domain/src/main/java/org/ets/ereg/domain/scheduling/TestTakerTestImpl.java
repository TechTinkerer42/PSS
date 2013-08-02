package org.ets.ereg.domain.scheduling;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;
import org.ets.ereg.domain.booking.BookingImpl;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.test.TestStatusType;
import org.ets.ereg.domain.test.TestStatusTypeImpl;

@Entity
@Table(name = "TST_TKR_TST")
public class TestTakerTestImpl implements TestTakerTest, Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "TestTakerTestID", strategy = GenerationType.TABLE)
    @TableGenerator(name = "TestTakerTestID", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "TestTakerTestImpl", allocationSize = 50)
    @Column(name = "TST_TKR_TST_ID")
    private Long TTTid;
    
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = CustomerImpl.class)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
       
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = TestStatusTypeImpl.class)
    @JoinColumn(name = "TST_STS_TYP_CDE")
    private TestStatusType code;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "testTakerTest",cascade={CascadeType.ALL}, orphanRemoval=true, targetEntity = BookingImpl.class)	
    private Set<Booking> bookings = new HashSet<Booking>(0);
    
	@Override
	public void setTestTakerTestId (Long TTTid) {
		this.TTTid = TTTid;
	}

	@Override
	public Long getTestTakerTestId () {
		return TTTid;
	}

	@Override
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public Customer getCustomer() {
		return customer;
	}

	@Override
	public void setTestStatusCode(TestStatusType testStatusCode) {
		this.code = testStatusCode;
	}

	@Override
	public TestStatusType getTestStatusCode() {
		return code;
	}
	
	public Set<Booking> getBookings() 
	{		
		return this.bookings;	
	}	
	
	public void setBookings(Set<Booking> bookings) 
	{		
		this.bookings = bookings;	
	}
	
	@Override
	public void setTTScoreStatusTypeCode(String ttScoreStatusTypeCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTTScoreStatusTypeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTTScoreStatusReasonCode(String ttScoreStatusReasonCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTTScoreStatusReasonCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
