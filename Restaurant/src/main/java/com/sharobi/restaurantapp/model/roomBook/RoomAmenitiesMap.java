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
 * Entity implementation class for Entity: Room
 * 
 */
@XmlRootElement
@Entity
@Table(name = "rb_m_room_amenities_map")
public class RoomAmenitiesMap implements Serializable{
	
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
	@Column(name = "roomtype_id")
	private int roomTypeId;
	
	@Expose
	@Column(name = "amenities_id")
	private int amenitiesId;
	
	@Expose
	@Column(name = "is_enable")
	private int isEnable;

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

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public int getAmenitiesId() {
		return amenitiesId;
	}

	public void setAmenitiesId(int amenitiesId) {
		this.amenitiesId = amenitiesId;
	}

	public int getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}	
}
