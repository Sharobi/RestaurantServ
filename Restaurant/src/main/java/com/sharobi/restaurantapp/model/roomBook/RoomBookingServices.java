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
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * @author Habib
 *
 */
@XmlRootElement
@Entity
@Table(name = "rb_room_booking_services")
public class RoomBookingServices implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Expose
	@Column(name="hotel_id")
	private int hotelId;
	
	@ManyToOne
	@JoinColumn(name = "booking_id")
	private RoomBooking roomBooking;
	
	@Expose
	@Column(name = "booking_no")
	private String bookingNo;
	
	@Expose
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;
	
	@Expose
	@ManyToOne
	@JoinColumn(name = "service_id")
	private RoomServices roomServices;
	
	@Expose
	@Column(name = "service_note")
	private String serviceNote;
	
	@Expose
	@Column(name = "service_rate")
	private double serviceRate;
	
	@Expose
	@Column(name = "service_qty")
	private double serviceQty;
	
	@Expose
	@Column(name = "gross_amount")
	private double grossAmount;
	
	@Expose
	@Column(name = "disc_per")
	private double discPer;
	
	@Expose
	@Column(name = "disc_amount")
	private double discAmount;
	
	@Expose
	@Column(name = "tax_rate")
	private double taxRate;
	
	@Expose
	@Column(name = "tax_amount")
	private double taxAmount;
	
	@Expose
	@Column(name = "service_tax_rate")
	private double serviceChargeRate;
	
	@Expose
	@Column(name = "service_tax_amount")
	private double serviceChargeAmount;
	
	@Expose
	@Column(name = "net_amount")
	private double netAmount;
	
	@Expose
	@Column(name = "is_deleted")
	private int isDeleted;
	
	@Expose
	@Column(name = "created_by")
	private int createdBy;
	
	@Expose
	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	@Expose
	@Column(name = "updated_by")
	private int updatedBy;
	
	@Expose
	@Column(name = "updated_date")
	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public RoomBooking getRoomBooking() {
		return roomBooking;
	}

	public void setRoomBooking(RoomBooking roomBooking) {
		this.roomBooking = roomBooking;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public RoomServices getRoomServices() {
		return roomServices;
	}

	public void setRoomServices(RoomServices roomServices) {
		this.roomServices = roomServices;
	}

	public String getServiceNote() {
		return serviceNote;
	}

	public void setServiceNote(String serviceNote) {
		this.serviceNote = serviceNote;
	}

	public double getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(double serviceRate) {
		this.serviceRate = serviceRate;
	}

	public double getServiceQty() {
		return serviceQty;
	}

	public void setServiceQty(double serviceQty) {
		this.serviceQty = serviceQty;
	}

	public double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public double getDiscPer() {
		return discPer;
	}

	public void setDiscPer(double discPer) {
		this.discPer = discPer;
	}

	public double getDiscAmount() {
		return discAmount;
	}

	public void setDiscAmount(double discAmount) {
		this.discAmount = discAmount;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getServiceChargeRate() {
		return serviceChargeRate;
	}

	public void setServiceChargeRate(double serviceChargeRate) {
		this.serviceChargeRate = serviceChargeRate;
	}

	public double getServiceChargeAmount() {
		return serviceChargeAmount;
	}

	public void setServiceChargeAmount(double serviceChargeAmount) {
		this.serviceChargeAmount = serviceChargeAmount;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "RoomBookingServices [id=" + id + ", hotelId=" + hotelId + ", roomBooking=" + roomBooking
				+ ", bookingNo=" + bookingNo + ", room=" + room + ", roomServices=" + roomServices + ", serviceNote="
				+ serviceNote + ", serviceRate=" + serviceRate + ", serviceQty=" + serviceQty + ", grossAmount="
				+ grossAmount + ", discPer=" + discPer + ", discAmount=" + discAmount + ", taxRate=" + taxRate
				+ ", taxAmount=" + taxAmount + ", serviceChargeRate=" + serviceChargeRate + ", serviceChargeAmount="
				+ serviceChargeAmount + ", netAmount=" + netAmount + ", isDeleted=" + isDeleted + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + "]";
	}
	
	

}
