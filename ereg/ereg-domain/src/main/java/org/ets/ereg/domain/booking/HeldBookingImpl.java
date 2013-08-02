package org.ets.ereg.domain.booking;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.HeldBooking;

@Entity
@Table(name = "BKNG_HLD")
public class HeldBookingImpl implements HeldBooking, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
    @Column(name="BKNG_ID",nullable=false)
	private Long id;
	
	@Column(name="HLD_SEAT_ID",nullable=false)
	private String holdSeatId;
	
	@Column(name="HLD_SRC_DSC",nullable=false)
	private String holdSourceDesc;
	
	@Column(name="HLD_DURN",nullable=false)
	private String holdDuration;
	
	@Column(name="SITE_CDE")
	private String siteCode;
	
	@Column(name="LAB_CDE",nullable=false)
	private String labCode;
	
	@Column(name="SEAT_CDE",nullable=false)
	private String seatCode;
	
	@MapsId	   
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY,targetEntity=BookingImpl.class)
	@JoinColumn(name="BKNG_ID",insertable=false,updatable=false)
	private Booking booking;
    
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getHoldSeatId() {
		return holdSeatId;
	}

	@Override
	public void setHoldSeatId(String holdSeatId) {
		this.holdSeatId = holdSeatId;
	}

	@Override
	public String getHoldSourceDesc() {
		return holdSourceDesc;
	}

	@Override
	public void setHoldSourceDesc(String holdSourceDesc) {
		this.holdSourceDesc = holdSourceDesc;
	}

	@Override
	public String getHoldDuration() {
		return holdDuration;
	}

	@Override
	public void setHoldDuration(String holdDuration) {
		this.holdDuration = holdDuration;
	}

	@Override
	public String getSiteCode() {
		return siteCode;
	}

	@Override
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	@Override
	public String getLabCode() {
		return labCode;
	}

	@Override
	public void setLabCode(String labCode) {
		this.labCode = labCode;
	}

	@Override
	public String getSeatCode() {
		return seatCode;
	}

	@Override
	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	@Override
	public Booking getBooking() {
		return booking;
	}

	@Override
	public void setBooking(Booking booking) {
		this.booking = booking;
	}

}
