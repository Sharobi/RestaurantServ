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
@Table(name = "rb_m_guest")
public class RoomBookingGuest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Expose
	@Column(name="hotel_id")
	private int hotelId;
	
	@ManyToOne
	@JoinColumn(name = "booking_id")
	private RoomBooking roomBooking;
	
	@Expose
	@Column(name = "booking_no")
	private String bookingNo;
	
	@Expose
	@Column(name = "name")
	private String name;
	
	@Expose
	@Column(name = "contact_no")
	private String contactNo;
	
	@Expose
	@Column(name = "email_id")
	private String emailId;
	
	@Expose
	@Column(name = "address")
	private String address;
	
	@Expose
	@Column(name = "gender")
	private String gender;
	
	@Expose
	@Temporal(TemporalType.DATE)
	@Column(name = "dob ")
	private Date dob;
	
	@Expose
	@Column(name = "uniqueid_type")
	private int uniqueidType=0;
	 
	@Expose
	@Column(name = "uniqueid_no")
	private String uniqueidNo;
	
	@Expose
	@Column(name = "type")
	private String type;
	
	@Expose
	@Column(name = "delete_flag")
	private String deleteFlag="N";
	
	@Expose
	@Column(name = "created_by")
	private int createdBy;
	
	@Expose
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

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

	public RoomBooking getRoomBooking() {
		return roomBooking;
	}

	public void setRoomBooking(RoomBooking roomBooking) {
		this.roomBooking = roomBooking;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getUniqueidType() {
		return uniqueidType;
	}

	public void setUniqueidType(int uniqueidType) {
		this.uniqueidType = uniqueidType;
	}

	public String getUniqueidNo() {
		return uniqueidNo;
	}

	public void setUniqueidNo(String uniqueidNo) {
		this.uniqueidNo = uniqueidNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	@Override
	public String toString() {
		return "RoomBookingGuest [id=" + id + ", hotelId=" + hotelId + ", roomBooking=" + roomBooking + ", bookingNo="
				+ bookingNo + ", name=" + name + ", contactNo=" + contactNo + ", emailId=" + emailId + ", address="
				+ address + ", gender=" + gender + ", dob=" + dob + ", uniqueidType=" + uniqueidType + ", uniqueidNo="
				+ uniqueidNo + ", type=" + type + ", deleteFlag=" + deleteFlag + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + "]";
	}
	
	
	

}
