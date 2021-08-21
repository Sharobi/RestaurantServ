package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: Room
 * 
 */
@XmlRootElement
@Entity
@Table(name = "rb_m_room_services")
public class RoomServices implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Expose
	@Column(name = "hotel_id")
	private int hotelId;
	
	@Expose
	@Column(name = "name")
	private String name;
	
	@Expose
	@Column(name = "description")
	private String description;
	
	@Expose
	@Column(name = "rate")
	private double rate;
	
	@Expose
	@Column(name = "is_taxable")
	private int isTaxable;
	
	@Expose
	@Column(name = "tax_rate")
	private double taxRate;
	
	@Expose
	@Column(name = "is_service_taxable")
	private int isServiceChargable;
	
	@Expose
	@Column(name = "service_tax_rate")
	private double serviceChargeRate;
	
	@Expose
	@Column(name = "disc_rate")
	private double discRate;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getIsTaxable() {
		return isTaxable;
	}

	public void setIsTaxable(int isTaxable) {
		this.isTaxable = isTaxable;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public int getIsServiceChargable() {
		return isServiceChargable;
	}

	public void setIsServiceChargable(int isServiceChargable) {
		this.isServiceChargable = isServiceChargable;
	}

	public double getServiceChargeRate() {
		return serviceChargeRate;
	}

	public void setServiceChargeRate(double serviceChargeRate) {
		this.serviceChargeRate = serviceChargeRate;
	}

	public double getDiscRate() {
		return discRate;
	}

	public void setDiscRate(double discRate) {
		this.discRate = discRate;
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
		return "RoomServices [id=" + id + ", hotelId=" + hotelId + ", name=" + name + ", description=" + description
				+ ", rate=" + rate + ", isTaxable=" + isTaxable + ", taxRate=" + taxRate + ", isServiceChargable="
				+ isServiceChargable + ", serviceChargeRate=" + serviceChargeRate + ", discRate=" + discRate
				+ ", isDeleted=" + isDeleted + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + "]";
	}

	
	
}
