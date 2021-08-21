package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class TaxForRoomBookInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private int id;

	@Expose
	private String hotelId;
	
	@Expose
	private String name;
	
	@Expose
	private float percentage;

	@Expose
	private String effective_Date;

	@Expose
	private int country;
	
	@Expose
	private String isActive;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public String getEffective_Date() {
		return effective_Date;
	}

	public void setEffective_Date(String effective_Date) {
		this.effective_Date = effective_Date;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}
	
}
