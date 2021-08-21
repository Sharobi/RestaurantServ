package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class AmenitiesInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private int id;
	
	@Expose
	private String hotelId;

	@Expose
	private String amenities;

	@Expose
	private String flag;

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getAmenities() {
		return amenities;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
