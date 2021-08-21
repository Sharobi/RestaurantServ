/**
 * 
 */
package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * @author ChanchalN
 *
 */
@XmlRootElement
@Entity
@Table(name = "rb_room_booking_details")
public class RoomBookingDetails implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	@Expose
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Expose
	//@OneToOne
	@Column(name="hotel_id")
	private int hotelId;
	
	@Expose
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room roomId;
	
	@Expose
	@Column
	private String fromDate;
	@Expose
	@Column
	private String toDate;
	@Expose
	@Column
	private String isCancelled;
	@Expose
	@Column
	private String isCheckIn;
	@Expose
	@Column
	private double advancePayment;
	@Expose
	@Column
	private String reserveId;
	@Expose
	@Column
	private int customerId;
	@Expose
	@Column
	private String isCheckOut;
	
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "booking_date")
	private Date bookingDate;
	
	@Expose
	@Column(name = "check_in_time")
	private String checkInTime;
	
	@Expose
	@Column(name = "check_out_time")
	private String checkOutTime;
	
	@Expose
	@Column(name = "room_rate")
	private double roomRate;
	
	@Expose
	@Column(name = "room_total")
	private double roomTotal;
	
	@Expose
	@Column(name = "tax_id")
	private int taxId;
	
	@Expose
	@Column(name = "tax_rate")
	private double taxRate;
	
	@Expose
	@Column(name = "tax_amt")
	private double taxAmt;
	
	@Expose
	@Column(name = "disc_per")
	private double discPer;
	
	@Expose
	@Column(name = "disc_amt")
	private double discAmt;
	
	@Expose
	@Column(name = "net_total")
	private double netTotal;
	
	@ManyToOne
	@JoinColumn(name = "booking_id")
	private RoomBooking bookingId;
	
	@Expose
	@Column(name = "booking_no")
	private String bookingNo;
	
	public double getRoomRate() {
		return roomRate;
	}
	public void setRoomRate(double roomRate) {
		this.roomRate = roomRate;
	}
	public double getRoomTotal() {
		return roomTotal;
	}
	public void setRoomTotal(double roomTotal) {
		this.roomTotal = roomTotal;
	}
	public int getTaxId() {
		return taxId;
	}
	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	public double getTaxAmt() {
		return taxAmt;
	}
	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
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
	public double getNetTotal() {
		return netTotal;
	}
	public void setNetTotal(double netTotal) {
		this.netTotal = netTotal;
	}
	@Expose
	@Transient
	private String custName;
	
	@Expose
	@Transient
	private String custPh;
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustPh() {
		return custPh;
	}
	public void setCustPh(String custPh) {
		this.custPh = custPh;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}
	public String getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/*public StoreMaster getHotelId() {
		return hotelId;
	}
	public void setHotelId(StoreMaster hotelId) {
		this.hotelId = hotelId;
	}*/
	/*public Room getRoomId() {
		return roomId;
	}
	public void setRoomId(Room roomId) {
		this.roomId = roomId;
	}*/
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getIsCancelled() {
		return isCancelled;
	}
	public void setIsCancelled(String isCancelled) {
		this.isCancelled = isCancelled;
	}
	
	public double getAdvancePayment() {
		return advancePayment;
	}
	public void setAdvancePayment(double advancePayment) {
		this.advancePayment = advancePayment;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	
	
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getIsCheckIn() {
		return isCheckIn;
	}
	public void setIsCheckIn(String isCheckIn) {
		this.isCheckIn = isCheckIn;
	}
	public String getIsCheckOut() {
		return isCheckOut;
	}
	public void setIsCheckOut(String isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	
	public String getBookingNo() {
		return bookingNo;
	}
	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}
	public Room getRoomId() {
		return roomId;
	}
	public void setRoomId(Room roomId) {
		this.roomId = roomId;
	}
	public RoomBooking getBookingId() {
		return bookingId;
	}
	public void setBookingId(RoomBooking bookingId) {
		this.bookingId = bookingId;
	}
	@Override
	public String toString() {
		return "RoomBookingDetails [id=" + id + ", hotelId=" + hotelId + ", roomId=" + roomId + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", isCancelled=" + isCancelled + ", isCheckIn=" + isCheckIn
				+ ", advancePayment=" + advancePayment + ", reserveId=" + reserveId + ", customerId=" + customerId
				+ ", isCheckOut=" + isCheckOut + ", bookingDate=" + bookingDate + ", checkInTime=" + checkInTime
				+ ", checkOutTime=" + checkOutTime + ", roomRate=" + roomRate + ", roomTotal=" + roomTotal + ", taxId="
				+ taxId + ", taxRate=" + taxRate + ", taxAmt=" + taxAmt + ", discPer=" + discPer + ", discAmt="
				+ discAmt + ", netTotal=" + netTotal + ", bookingId=" + bookingId + ", bookingNo=" + bookingNo
				+ ", custName=" + custName + ", custPh=" + custPh + "]";
	}
	
	
	
}
