/**
 * 
 */
package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;


/**
 * @author Habib
 *
 */
public class RbSnapShotData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int noOfBooking;
	private int noOfCheckIn;
	private int noOfCheckOut;
	private int noOfCancelBooking;
	private int actRoomAvl;
	private int roomUnderMaint;
	private int roomAvl;
	private int roomBooked;
	private double occupancy;
	private double roomRev;
	private double arr;
	private double revPar;
	private double roomDinning;
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
	public int getActRoomAvl() {
		return actRoomAvl;
	}
	public void setActRoomAvl(int actRoomAvl) {
		this.actRoomAvl = actRoomAvl;
	}
	public int getRoomUnderMaint() {
		return roomUnderMaint;
	}
	public void setRoomUnderMaint(int roomUnderMaint) {
		this.roomUnderMaint = roomUnderMaint;
	}
	public int getRoomAvl() {
		return roomAvl;
	}
	public void setRoomAvl(int roomAvl) {
		this.roomAvl = roomAvl;
	}
	public int getRoomBooked() {
		return roomBooked;
	}
	public void setRoomBooked(int roomBooked) {
		this.roomBooked = roomBooked;
	}
	public double getOccupancy() {
		return occupancy;
	}
	public void setOccupancy(double occupancy) {
		this.occupancy = occupancy;
	}
	public double getRoomRev() {
		return roomRev;
	}
	public void setRoomRev(double roomRev) {
		this.roomRev = roomRev;
	}
	public double getArr() {
		return arr;
	}
	public void setArr(double arr) {
		this.arr = arr;
	}
	public double getRevPar() {
		return revPar;
	}
	public void setRevPar(double revPar) {
		this.revPar = revPar;
	}
	public double getRoomDinning() {
		return roomDinning;
	}
	public void setRoomDinning(double roomDinning) {
		this.roomDinning = roomDinning;
	}
	@Override
	public String toString() {
		return "RbSnapShotData [noOfBooking=" + noOfBooking + ", noOfCheckIn=" + noOfCheckIn + ", noOfCheckOut="
				+ noOfCheckOut + ", noOfCancelBooking=" + noOfCancelBooking + ", actRoomAvl=" + actRoomAvl
				+ ", roomUnderMaint=" + roomUnderMaint + ", roomAvl=" + roomAvl + ", roomBooked=" + roomBooked
				+ ", occupancy=" + occupancy + ", roomRev=" + roomRev + ", arr=" + arr + ", revPar=" + revPar
				+ ", roomDinning=" + roomDinning + "]";
	}
	
	

}
