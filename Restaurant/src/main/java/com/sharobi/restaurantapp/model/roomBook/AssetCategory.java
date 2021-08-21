/**
 * 
 */
package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * @author Habib
 *
 */
@XmlRootElement
@Entity
@Table(name = "rb_m_asset_items_category")
public class AssetCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	
	@Expose
	@Column(name = "name")
	private String name;
	
	@Expose
	@Column(name = "is_delete")
	private int isDelete;
	
	@Expose
	@Column(name = "hotel_id")
	private int hotelId;
	
	@Expose
	@Column(name = "created_by")
	private int createdBy;
	
	@Expose
	@Column(name = "created_date")
	private String createdDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "AssetCategory [id=" + id + ", name=" + name + ", isDelete=" + isDelete + ", hotelId=" + hotelId
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + "]";
	}
	
	
	
	

}
