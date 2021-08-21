/**
 * 
 */
package com.sharobi.restaurantapp.model.roomBook;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * @author ChanchalN
 * This class is take the booking information.
 */
@XmlRootElement
public class RoomBookingInfo {
	@Expose
	private String reserveId;
	@Expose
	private RoomBookingCustomer customer;
	@Expose
	private List<Room> roomid;
	@Expose
	private String fromDate;
	@Expose
	private String toDate;
	@Expose
	private int hotelId;
	@Expose
	private String isCheckIn;
	@Expose
	private String isCheckOut;
	@Expose
	private RoomBookingPayment payment;
	
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public List<Room> getRoomid() {
		return roomid;
	}
	public void setRoomid(List<Room> roomid) {
		this.roomid = roomid;
	}
	public RoomBookingCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(RoomBookingCustomer customer) {
		this.customer = customer;
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
	
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
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
	public RoomBookingPayment getPayment() {
		return payment;
	}
	public void setPayment(RoomBookingPayment payment) {
		this.payment = payment;
	}
	@Override
	public String toString() {
		return "RoomBookingInfo [reserveId=" + reserveId + ", customer=" + customer + ", roomid=" + roomid
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", hotelId=" + hotelId + ", isCheckIn=" + isCheckIn
				+ ", isCheckOut=" + isCheckOut + ", payment=" + payment + "]";
	}
}
