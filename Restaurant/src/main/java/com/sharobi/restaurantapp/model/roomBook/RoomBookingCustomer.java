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
@Table(name = "rb_customer")
public class RoomBookingCustomer implements Serializable {

	private static final long serialVersionUID = 1L;
	@Expose
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Expose
	@Column
	private String name;
	@Expose
	@Column
	private String address;
	@Expose
	@Column
	private String phone;
	@Expose
	@Column
	private String email;
	@Expose
	@Column
	private String gender;
	@Expose
	@Column
	private String store_id;
	@Expose
	@Column
	private String dob;
	@Expose
	@Column
	private String deleteflag;
	@Expose
	@Column
	private String location;
	@Expose
	@Column
	private String houseno;
	@Expose
	@Column
	private String street;
	@Expose
	@Column
	private String state;
	/*@Expose
	@Column
	private String identity;*/
	
	@Expose
	@Column(name="uniqueid_type")
	private int uniqueidType;
	
	@Expose
	@Column(name="uniqueid_no")
	private String uniqueidNo;
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getHouseno() {
		return houseno;
	}
	public void setHouseno(String houseno) {
		this.houseno = houseno;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	/*public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}*/
	@Override
	public String toString() {
		return "RoomBookingCustomer [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone
				+ ", email=" + email + ", gender=" + gender + ", store_id=" + store_id + ", dob=" + dob
				+ ", deleteflag=" + deleteflag + ", location=" + location + ", houseno=" + houseno + ", street="
				+ street + ", state=" + state + "]";
	}
}
