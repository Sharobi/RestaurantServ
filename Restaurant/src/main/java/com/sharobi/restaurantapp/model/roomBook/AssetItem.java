/**
 * 
 */
package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * @author Habib
 *
 */
@XmlRootElement
@Entity
@Table(name = "rb_m_asset_items")
public class AssetItem implements Serializable{

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
	@Column(name = "code")
	private String code;
	@Expose
	@Column(name = "unit")
	private String unit;
	@Expose
	@Column(name = "size")
	private String size;
	@Expose
	@Column(name = "color")
	private String color;
	@Expose
	@Column(name = "rate")
	private double rate;
	@Expose
	@Column(name = "tax_rate")
	private double taxRate;
	@Expose
	@Column(name = "is_tax_exclusive")
	private String isTaxExclusive;
	@Expose
	@Column(name = "disc_per")
	private double discPer;
	@Expose
	@ManyToOne
	@JoinColumn(name = "category_id")
	private AssetCategory assetCategory;
	@Expose
	@Column(name = "is_reusable")
	private String isReusable;
	@Expose
	@Column(name = "is_permanent")
	private String isPermanent;
	@Expose
	@Column(name = "hotel_id")
	private int hotelId;
	@Expose
	@Column(name = "is_delete")
	private int isDelete;
	@Expose
	@Column(name = "created_by")
	private int createdBy;
	@Expose
	@Temporal(TemporalType.DATE)
	@Column(name = "created_date")
	private Date createdDate;
	@Expose
	@Column(name = "updated_by")
	private int updatedBy;
	@Expose
	@Temporal(TemporalType.DATE)
	@Column(name = "updated_date")
	private Date updatedDate;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	public String getIsTaxExclusive() {
		return isTaxExclusive;
	}
	public void setIsTaxExclusive(String isTaxExclusive) {
		this.isTaxExclusive = isTaxExclusive;
	}
	public double getDiscPer() {
		return discPer;
	}
	public void setDiscPer(double discPer) {
		this.discPer = discPer;
	}
	public AssetCategory getAssetCategory() {
		return assetCategory;
	}
	public void setAssetCategory(AssetCategory assetCategory) {
		this.assetCategory = assetCategory;
	}
	public String getIsReusable() {
		return isReusable;
	}
	public void setIsReusable(String isReusable) {
		this.isReusable = isReusable;
	}
	public String getIsPermanent() {
		return isPermanent;
	}
	public void setIsPermanent(String isPermanent) {
		this.isPermanent = isPermanent;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
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
		return "AssetItem [id=" + id + ", name=" + name + ", code=" + code + ", unit=" + unit + ", size=" + size
				+ ", color=" + color + ", rate=" + rate + ", taxRate=" + taxRate + ", isTaxExclusive=" + isTaxExclusive
				+ ", discPer=" + discPer + ", assetCategory=" + assetCategory + ", isReusable=" + isReusable
				+ ", isPermanent=" + isPermanent + ", hotelId=" + hotelId + ", isDelete=" + isDelete + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + "]";
	}
	
	

}
