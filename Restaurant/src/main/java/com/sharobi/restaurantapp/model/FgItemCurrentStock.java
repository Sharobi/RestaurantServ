/**
 * 
 */
package com.sharobi.restaurantapp.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import net.sf.resultsetmapper.MapToData;

/**
 * @author Habib
 *
 */
public class FgItemCurrentStock implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose 
	@MapToData(columnAliases = { "item_id"})
	private Integer itemId;
	@Expose
	@MapToData(columnAliases = { "name"})
	private String name;
	@Expose
	@MapToData(columnAliases = { "rate"})
	private Double rate;
	@Expose
	@MapToData(columnAliases = { "unit"})
	private String unit;
	@Expose 
	@MapToData(columnAliases = { "cur_stock"})
	private Integer curStock;
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getCurStock() {
		return curStock;
	}
	public void setCurStock(Integer curStock) {
		this.curStock = curStock;
	}
	
	

}
