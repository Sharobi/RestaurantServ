package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Where;

import com.google.gson.annotations.Expose;
import com.sharobi.restaurantapp.model.StoreCustomer;

/**
 * @author ChanchalN
 */
@XmlRootElement
@Entity
@Table(name = "rb_room_booking")
public class RoomBooking implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Expose
	@Column(name = "booking_no")
	private String bookingNo;
	@Expose
	@Column
	private String frontDeskName;
	@Expose
	@Column
	private String checkInDate;
	@Expose
	@Column
	private String checkinTimeId; 				// One time entry for each hotel.
	@Expose
	@Column
	private String actualCheckinTime;
	@Column
	@Expose
	private String checkoutDate;
	@Expose
	@Column
	private String checkoutTimeId;				// One time entry for each hotel.
	@Expose
	@Column
	private String actualCheckoutTime;
	@Expose
	@Column
	private String isCheckedOut;
	@Expose
	@Column
	private String reserveId;
	@Expose
	@Column
	private int hotelId;
	
	@Expose
	@ManyToOne
	@JoinColumn(name = "custId")
	private StoreCustomer custId;
	
	@Expose
	@Transient
	private RoomBookingInfo roombookinginfo;
	
	@Expose
	@Column(name = "gross_amt")
	private double grossAmt;
	
	@Expose
	@Column(name = "disc_per")
	private double discPer;
	
	@Expose
	@Column(name = "disc_amt")
	private double discAmt;
	
	@Expose
	@Column(name = "tax_amt")
	private double taxAmt;
	
	@Expose
	@Column(name = "net_amt")
	private double netAmt;
	
	@Expose
	@Column(name = "is_taxable")
	private String isTaxable="Y";
	
	@Expose
	@Column
	private String isCheckedIn;
	
	@Expose
	@Column
	private String isCancelled;
	
	@Expose
	@Column(name = "credit_flag")
	private String creditFlag="N";
	
	@Expose
	@Column(name = "cancel_by")
	private String cancelBy;
	
	@Expose
	@Temporal(TemporalType.DATE)
	@Column(name = "cancel_date")
	private Date cancelDate;
	
	@Expose
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "booking_date")
	private Date bookingDate;
	
	@Expose
	@ManyToOne
	@JoinColumn(name = "booking_type_id")
	private RoomBookingType bookingType;
	
	@Expose
	@Column(name = "round_off")
	private double roundOff;
	
	@Expose
	@OneToMany(mappedBy = "bookingId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Where(clause = "isCancelled='N'")
	private List<RoomBookingDetails> bookingDetail;
	
	@Expose
	@OneToMany(mappedBy = "roomBooking", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Where(clause = "service_qty>0")
	private List<RoomBookingServices> bookingServices;
	
	@Expose
	@OneToMany(mappedBy = "roomBooking", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Where(clause = "delete_flag='N'")
	private List<RoomBookingGuest> bookingGuest;
	
	@Expose
	@Transient
	private List<RoomBookingPayment> payment;
	
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;
	
	@Expose
	@Column(name = "room_service_gross")
	private double roomServiceGross=0.0;
	
	@Expose
	@Column(name = "room_service_tax")
	private double roomServiceTax=0.0;
	
	@Expose
	@Column(name = "room_service_discount")
	private double roomServiceDiscount=0.0;
	
	@Expose
	@Column(name = "no_of_persons")
	private int noOfPersons;
	
	@Expose
	@Transient
	private String checkoutType;
	
	@Expose
	@Transient
	private String takePType;
	
	@Expose
	@Column(name = "ref_no")
	private String refNo;
	
	@Expose
	@Column(name = "remarks")
	private String remarks;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFrontDeskName() {
		return frontDeskName;
	}
	public void setFrontDeskName(String frontDeskName) {
		this.frontDeskName = frontDeskName;
	}
	public String getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	public String getCheckinTimeId() {
		return checkinTimeId;
	}
	public void setCheckinTimeId(String checkinTimeId) {
		this.checkinTimeId = checkinTimeId;
	}
	public String getActualCheckinTime() {
		return actualCheckinTime;
	}
	public void setActualCheckinTime(String actualCheckinTime) {
		this.actualCheckinTime = actualCheckinTime;
	}
	public String getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public String getCheckoutTimeId() {
		return checkoutTimeId;
	}
	public void setCheckoutTimeId(String checkoutTimeId) {
		this.checkoutTimeId = checkoutTimeId;
	}
	public String getActualCheckoutTime() {
		return actualCheckoutTime;
	}
	public void setActualCheckoutTime(String actualCheckoutTime) {
		this.actualCheckoutTime = actualCheckoutTime;
	}
	public String getIsCheckedOut() {
		return isCheckedOut;
	}
	public void setIsCheckedOut(String isCheckedOut) {
		this.isCheckedOut = isCheckedOut;
	}
	public RoomBookingInfo getRoombookinginfo() {
		return roombookinginfo;
	}
	public void setRoombookinginfo(RoomBookingInfo roombookinginfo) {
		this.roombookinginfo = roombookinginfo;
	}
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	
	public double getGrossAmt() {
		return grossAmt;
	}
	public void setGrossAmt(double grossAmt) {
		this.grossAmt = grossAmt;
	}
	public double getDiscPer() {
		return discPer;
	}
	public void setDiscPer(double discPer) {
		this.discPer = discPer;
	}
	public double getDiscAmt() {
		return discAmt;
	}
	public void setDiscAmt(double discAmt) {
		this.discAmt = discAmt;
	}
	public double getTaxAmt() {
		return taxAmt;
	}
	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}
	public double getNetAmt() {
		return netAmt;
	}
	public void setNetAmt(double netAmt) {
		this.netAmt = netAmt;
	}
	
	
	public String getIsTaxable() {
		return isTaxable;
	}
	public void setIsTaxable(String isTaxable) {
		this.isTaxable = isTaxable;
	}
	
	public String getBookingNo() {
		return bookingNo;
	}
	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}
	public StoreCustomer getCustId() {
		return custId;
	}
	public void setCustId(StoreCustomer custId) {
		this.custId = custId;
	}
	public String getIsCheckedIn() {
		return isCheckedIn;
	}
	public void setIsCheckedIn(String isCheckedIn) {
		this.isCheckedIn = isCheckedIn;
	}
	public String getIsCancelled() {
		return isCancelled;
	}
	public void setIsCancelled(String isCancelled) {
		this.isCancelled = isCancelled;
	}
	
	public String getCreditFlag() {
		return creditFlag;
	}
	public void setCreditFlag(String creditFlag) {
		this.creditFlag = creditFlag;
	}
	public String getCancelBy() {
		return cancelBy;
	}
	public void setCancelBy(String cancelBy) {
		this.cancelBy = cancelBy;
	}
	
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	public String getCancelRemarks() {
		return cancelRemarks;
	}
	public void setCancelRemarks(String cancelRemarks) {
		this.cancelRemarks = cancelRemarks;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public RoomBookingType getBookingType() {
		return bookingType;
	}
	public void setBookingType(RoomBookingType bookingType) {
		this.bookingType = bookingType;
	}
	public double getRoundOff() {
		return roundOff;
	}
	public void setRoundOff(double roundOff) {
		this.roundOff = roundOff;
	}
	public List<RoomBookingDetails> getBookingDetail() {
		return bookingDetail;
	}
	public void setBookingDetail(List<RoomBookingDetails> bookingDetail) {
		this.bookingDetail = bookingDetail;
	}
	public List<RoomBookingPayment> getPayment() {
		return payment;
	}
	public void setPayment(List<RoomBookingPayment> payment) {
		this.payment = payment;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public List<RoomBookingServices> getBookingServices() {
		return bookingServices;
	}
	public void setBookingServices(List<RoomBookingServices> bookingServices) {
		this.bookingServices = bookingServices;
	}
	
	
	public double getRoomServiceGross() {
		return roomServiceGross;
	}
	public void setRoomServiceGross(double roomServiceGross) {
		this.roomServiceGross = roomServiceGross;
	}
	public double getRoomServiceTax() {
		return roomServiceTax;
	}
	public void setRoomServiceTax(double roomServiceTax) {
		this.roomServiceTax = roomServiceTax;
	}
	public double getRoomServiceDiscount() {
		return roomServiceDiscount;
	}
	public void setRoomServiceDiscount(double roomServiceDiscount) {
		this.roomServiceDiscount = roomServiceDiscount;
	}
	
	public int getNoOfPersons() {
		return noOfPersons;
	}
	public void setNoOfPersons(int noOfPersons) {
		this.noOfPersons = noOfPersons;
	}
	
	
	public String getCheckoutType() {
		return checkoutType;
	}
	public void setCheckoutType(String checkoutType) {
		this.checkoutType = checkoutType;
	}
	
	public String getTakePType() {
		return takePType;
	}
	public void setTakePType(String takePType) {
		this.takePType = takePType;
	}
	
	public List<RoomBookingGuest> getBookingGuest() {
		return bookingGuest;
	}
	public void setBookingGuest(List<RoomBookingGuest> bookingGuest) {
		this.bookingGuest = bookingGuest;
	}
	
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "RoomBooking [id=" + id + ", bookingNo=" + bookingNo + ", frontDeskName=" + frontDeskName
				+ ", checkInDate=" + checkInDate + ", checkinTimeId=" + checkinTimeId + ", actualCheckinTime="
				+ actualCheckinTime + ", checkoutDate=" + checkoutDate + ", checkoutTimeId=" + checkoutTimeId
				+ ", actualCheckoutTime=" + actualCheckoutTime + ", isCheckedOut=" + isCheckedOut + ", reserveId="
				+ reserveId + ", hotelId=" + hotelId + ", custId=" + custId + ", roombookinginfo=" + roombookinginfo
				+ ", grossAmt=" + grossAmt + ", discPer=" + discPer + ", discAmt=" + discAmt + ", taxAmt=" + taxAmt
				+ ", netAmt=" + netAmt + ", isTaxable=" + isTaxable + ", isCheckedIn=" + isCheckedIn + ", isCancelled="
				+ isCancelled + ", creditFlag=" + creditFlag + ", cancelBy=" + cancelBy + ", cancelDate=" + cancelDate
				+ ", cancelRemarks=" + cancelRemarks + ", bookingDate=" + bookingDate + ", bookingType=" + bookingType
				+ ", roundOff=" + roundOff + ", bookingDetail=" + bookingDetail + ", bookingServices=" + bookingServices
				+ ", bookingGuest=" + bookingGuest + ", payment=" + payment + ", createdDate=" + createdDate
				+ ", roomServiceGross=" + roomServiceGross + ", roomServiceTax=" + roomServiceTax
				+ ", roomServiceDiscount=" + roomServiceDiscount + ", noOfPersons=" + noOfPersons + ", checkoutType="
				+ checkoutType + ", takePType=" + takePType + ", refNo=" + refNo + ", remarks=" + remarks + "]";
	}
	
	
	
}
