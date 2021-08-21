/**
 * 
 */
package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * @author Habib
 *
 */
@XmlRootElement
public class RbAppHomeData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	private int noOfBooking;
	@Expose
	private int noOfCheckIn;
	@Expose
	private int noOfCheckOut;
	@Expose
	private int noOfCancelBooking;
	public int getNoOfBooking() {
		return noOfBooking;
	}
	public void setNoOfBooking(int noOfBooking) {
		this.noOfBooking = noOfBooking;
	}
	public int getNoOfCheckIn() {
		return noOfCheckIn;
	}
	public void setNoOfCheckIn(int noOfCheckIn) {
		this.noOfCheckIn = noOfCheckIn;
	}
	public int getNoOfCheckOut() {
		return noOfCheckOut;
	}
	public void setNoOfCheckOut(int noOfCheckOut) {
		this.noOfCheckOut = noOfCheckOut;
	}
	public int getNoOfCancelBooking() {
		return noOfCancelBooking;
	}
	public void setNoOfCancelBooking(int noOfCancelBooking) {
		this.noOfCancelBooking = noOfCancelBooking;
	}
	@Override
	public String toString() {
		return "RbAppHomeData [noOfBooking=" + noOfBooking + ", noOfCheckIn=" + noOfCheckIn + ", noOfCheckOut="
				+ noOfCheckOut + ", noOfCancelBooking=" + noOfCancelBooking + "]";
	}
	
	

}
