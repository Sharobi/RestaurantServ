package com.sharobi.restaurantapp.print;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
@Entity
@Table(name = "pr_m_kot")
public class PrintKotMaster implements Serializable {
	
	/**
	 * 
	 */
	


	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	
	@Expose
	@Column(name="store_id")
	private int storeId;
	
	@Expose
	@Column(name = "printer_ip")
	private String printerIp;
	
	
	@Expose
	@Column(name="cuisine_type_id")
	private int cuisineTypeId;
	
	@Expose
	@Column(name = "name")
	private String name;
	
	
	@Expose
	@Column(name = "kitchen_location")
	private String kitchenLocation;
	
	
	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag;
	
	@Expose
	@Column(name = "created_by")
	private String createdBy;
	
	@Expose
	@Column(name = "created_date")
	private String createdDate;
	
	@Expose
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Expose
	@Column(name = "updated_date")
	private String updatedDate;
	
	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getPrinterIp() {
		return printerIp;
	}

	public void setPrinterIp(String printerIp) {
		this.printerIp = printerIp;
	}

	public int getCuisineTypeId() {
		return cuisineTypeId;
	}

	public void setCuisineTypeId(int cuisineTypeId) {
		this.cuisineTypeId = cuisineTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKitchenLocation() {
		return kitchenLocation;
	}

	public void setKitchenLocation(String kitchenLocation) {
		this.kitchenLocation = kitchenLocation;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
	
	
	
	

}
