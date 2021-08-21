package com.sharobi.restaurantapp.model.roomBook;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;
@XmlRootElement
public class RoomTypeInfo {

	@Expose
	private int id;

	@Expose
	private String hotelId;

	@Expose
	private String roomType;

	/*@Expose
	private String ac;

	@Expose
	private float roomPrice;

	@Expose
	private String roomSize;

	@Expose
	private String roomSizeUnit;

	@Expose
	private String roomDesc;

	@Expose
	private int taxId;
	
	@Expose
	private float taxPercentage;*/

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

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	/*public String getAc() {
		return ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
	}

	public float getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(float roomPrice) {
		this.roomPrice = roomPrice;
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

	public String getRoomDesc() {
		return roomDesc;
	}

	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}

	public int getTaxId() {
		return taxId;
	}

	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}

	public float getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(int taxPercentage) {
		this.taxPercentage = taxPercentage;
	}*/
	
}
