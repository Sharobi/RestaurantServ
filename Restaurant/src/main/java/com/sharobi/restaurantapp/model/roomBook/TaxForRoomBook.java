package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: Tax
 * 
 */
@XmlRootElement
@Entity
@Table(name = "rb_tax")
public class TaxForRoomBook implements Serializable {

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
	@Column(name = "name")
	private String name;
	
	@Expose
	@Column(name = "percentage")
	private double percentage;

	@Expose
	@Column(name = "effective_date")
	private String effective_Date;

	@Expose
	@ManyToOne
	@JoinColumn(name = "Country_Id")
	private Country country;
	
	@Expose
	@Column(name = "is_active")
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

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String getEffective_Date() {
		return effective_Date;
	}

	public void setEffective_Date(String effective_Date) {
		this.effective_Date = effective_Date;
	}

	public Country getCountryId() {
		return country;
	}

	public void setCountryId(Country countryId) {
		this.country = countryId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
