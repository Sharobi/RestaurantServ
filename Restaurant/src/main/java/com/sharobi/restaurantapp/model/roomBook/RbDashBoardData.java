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
public class RbDashBoardData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	private String category;
	@Expose
	private double dtdata;
	@Expose
	private double mtd;
	@Expose
	private double ytd;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getDtdata() {
		return dtdata;
	}
	public void setDtdata(double dtdata) {
		this.dtdata = dtdata;
	}
	public double getMtd() {
		return mtd;
	}
	public void setMtd(double mtd) {
		this.mtd = mtd;
	}
	public double getYtd() {
		return ytd;
	}
	public void setYtd(double ytd) {
		this.ytd = ytd;
	}
	@Override
	public String toString() {
		return "RbDashBoardData [category=" + category + ", dtdata=" + dtdata + ", mtd=" + mtd + ", ytd=" + ytd + "]";
	}
	
	

}
