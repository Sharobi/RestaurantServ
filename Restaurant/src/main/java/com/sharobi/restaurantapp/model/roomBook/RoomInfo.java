package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class RoomInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Expose
	private int id;
	@Expose
	private String hotelId;
	@Expose
	private int floor;
	@Expose
	private String roomNo;
	@Expose
	private int roomTypeId;
	@Expose
	private String roomCategory;
	
	@Expose
	private String roomCapacity;
	@Expose
	private double roomPrice;
	@Expose
	private String roomName;
	@Expose
	private String roomDesc;
	@Expose
	private int roomTax;
	@Expose
	private String roomSize;
	@Expose
	private String roomSizeUnit;
	@Expose
	private int status;
	
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
	public int getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(int roomTypeId) {
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
	public int getRoomTax() {
		return roomTax;
	}
	public void setRoomTax(int roomTax) {
		this.roomTax = roomTax;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
