package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

/**
 * Entity implementation class for Entity: Room
 * 
 */
@XmlRootElement
@Entity
@Table(name = "rb_m_room")
public class Room implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "store_id")
	private String hotelId;

	@Expose
	@Column(name = "floor")
	private int floor;

	@Expose
	@Column(name = "room_no")
	private String roomNo;

	@Expose
	@OneToOne
	@JoinColumn(name = "room_type_id")
	private RoomType roomTypeId;
	
	/*
	 * Added By Chanchal 
	 */
	@Expose
	@Column(name ="room_category")
	private String roomCategory;  // 
	/*@Expose
	@Column(name ="room_type")
	private String roomType;*/
	@Expose
	@Column(name ="room_capacity")
	private String roomCapacity;
	
	@Expose
	@Column(name ="room_price")
	private double roomPrice;
	@Expose
	@Column(name ="room_name")
	private String roomName;
	@Expose
	@Column(name ="room_desc")
	private String roomDesc;
	
	@Expose
	@Column(name ="room_size")
	private String roomSize;
	@Expose
	@Column(name ="room_size_unit")
	private String roomSizeUnit;
	@Expose
	@Column(name="status")
	private Integer status;
	
	@Expose
	@OneToOne
	@JoinColumn(name = "tax_id")
	private TaxForRoomBook taxId;
	
	@Expose
	@Transient
	private String isBooked;
	
	@Expose
	@Transient
	private String isStatus;
	
	@Expose
	@Transient
	private String isCheckIn;
	
	@Expose
	@Transient
	private String custName;
	
	@Expose
	@Transient
	private String custPh;
	
	@Expose
	@Transient
	private double advPayment;
	
	@Expose
	@Transient
	private String fromDate;
	
	@Expose
	@Transient
	private String toDate;
	
	@Expose
	@Transient
	private String reserveId;
	
	@Expose
	@Transient
	private Date bookingDate;
	
	@Expose
	@Transient
	private int bookingId;
	
	@Expose
	@Transient
	private String bookingNo;
	
	
	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

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

	public double getAdvPayment() {
		return advPayment;
	}

	public void setAdvPayment(double advPayment) {
		this.advPayment = advPayment;
	}

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

	public String getReserveId() {
		return reserveId;
	}

	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public RoomType getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(RoomType roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getRoomCategory() {
		return roomCategory;
	}

	public void setRoomCategory(String roomCategory) {
		this.roomCategory = roomCategory;
	}

	public String getRoomCapacity() {
		return roomCapacity;
	}

	public void setRoomCapacity(String roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	
	public double getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(double roomPrice) {
		this.roomPrice = roomPrice;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomDesc() {
		return roomDesc;
	}

	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}

	public String getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(String roomSize) {
		this.roomSize = roomSize;
	}

	public String getRoomSizeUnit() {
		return roomSizeUnit;
	}

	public void setRoomSizeUnit(String roomSizeUnit) {
		this.roomSizeUnit = roomSizeUnit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public TaxForRoomBook getTaxId() {
		return taxId;
	}

	public void setTaxId(TaxForRoomBook taxId) {
		this.taxId = taxId;
	}

	public String getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(String isBooked) {
		this.isBooked = isBooked;
	}

	public String getIsStatus() {
		return isStatus;
	}

	public void setIsStatus(String isStatus) {
		this.isStatus = isStatus;
	}

	
	public String getIsCheckIn() {
		return isCheckIn;
	}

	public void setIsCheckIn(String isCheckIn) {
		this.isCheckIn = isCheckIn;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", hotelId=" + hotelId + ", floor=" + floor + ", roomNo=" + roomNo + ", roomTypeId="
				+ roomTypeId + ", roomCategory=" + roomCategory + ", roomCapacity=" + roomCapacity + ", roomPrice="
				+ roomPrice + ", roomName=" + roomName + ", roomDesc=" + roomDesc + ", roomSize=" + roomSize
				+ ", roomSizeUnit=" + roomSizeUnit + ", status=" + status + ", taxId=" + taxId + ", isBooked="
				+ isBooked + ", isStatus=" + isStatus + ", isCheckIn=" + isCheckIn + ", custName=" + custName
				+ ", custPh=" + custPh + ", advPayment=" + advPayment + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", reserveId=" + reserveId + ", bookingDate=" + bookingDate + ", bookingId=" + bookingId
				+ ", bookingNo=" + bookingNo + "]";
	}

	
}
