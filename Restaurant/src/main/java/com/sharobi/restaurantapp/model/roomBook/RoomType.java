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
 * Entity implementation class for Entity: RoomType
 * 
 */
@XmlRootElement
@Entity
@Table(name = "rb_m_room_type")
public class RoomType implements Serializable{

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
	@Column(name = "room_type")
	private String roomType;
	
	/*@Expose
	@Column(name = "ac")
	private String ac;*/
	
	/*@Expose
	@Column(name = "room_price")
	private float roomPrice;*/
	
	/*@Expose
	@Column(name = "room_size")
	private String roomSize;
	
	@Expose
	@Column(name = "room_size_unit")
	private String roomSizeUnit;
	
	@Expose
	@Column(name = "room_desc")
	private String roomDesc;
	
	@Expose
	@Column(name = "tax_percengae")
	private float taxPercentage;
	
	@Expose
	@OneToOne
	@JoinColumn(name = "tax_id")
	private TaxForRoomBook taxId;
*/
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

	public float getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(float taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	
	public TaxForRoomBook getTaxId() {
		return taxId;
	}

	public void setTaxId(TaxForRoomBook taxId) {
		this.taxId = taxId;
	}
*/
}
