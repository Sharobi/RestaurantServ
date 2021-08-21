package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class RoomAmenitiesMapInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	private int id;
	@Expose
	private int roomType_id;
	@Expose
	private String hotel_id;
	@Expose
	private int amenities_id;
	@Expose
	private int isEnable;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoomType_id() {
		return roomType_id;
	}
	public void setRoomType_id(int roomType_id) {
		this.roomType_id = roomType_id;
	}
	public String getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(String hotel_id) {
		this.hotel_id = hotel_id;
	}
	public int getAmenities_id() {
		return amenities_id;
	}
	public void setAmenities_id(int amenities_id) {
		this.amenities_id = amenities_id;
	}
	public int getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}
	
	
}
