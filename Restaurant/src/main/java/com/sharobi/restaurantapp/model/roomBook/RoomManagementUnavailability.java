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
 * @author ChanchalN
 *
 */
@XmlRootElement
@Entity
@Table(name = "rb_room_management_unavailability")
public class RoomManagementUnavailability implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	@Expose
	//@OneToOne
	@Column(name="room_id")
	private int roomId;
	@Expose
	//@OneToOne
	@Column(name="hotel_id")
	private int hotelId;
	@Expose 
	@Column
	private String fromDate;
	@Expose 
	@Column
	private String toDate;
	@Expose
	@Column
	private int roomStatusId;
	@Expose
	@Column
	private int isComplete;
	@Expose
	@Column
	private int isTemporaryClose;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/*public Room getRoomId() {
		return roomId;
	}
	public void setRoomId(Room roomId) {
		this.roomId = roomId;
	}
	public StoreMaster getHotelId() {
		return hotelId;
	}
	public void setHotelId(StoreMaster hotelId) {
		this.hotelId = hotelId;
	}*/
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getRoomStatusId() {
		return roomStatusId;
	}
	public void setRoomStatusId(int roomStatusId) {
		this.roomStatusId = roomStatusId;
	}
	public int getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(int isComplete) {
		this.isComplete = isComplete;
	}
	public int getIsTemporaryClose() {
		return isTemporaryClose;
	}
	public void setIsTemporaryClose(int isTemporaryClose) {
		this.isTemporaryClose = isTemporaryClose;
	}
	@Override
	public String toString() {
		return "RoomManagementUnavailability [id=" + id + ", roomId=" + roomId + ", hotelId=" + hotelId + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", roomStatusId=" + roomStatusId + ", isComplete=" + isComplete
				+ ", isTemporaryClose=" + isTemporaryClose + "]";
	}
	
	
}
