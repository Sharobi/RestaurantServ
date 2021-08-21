package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * Entity implementation class for Entity: Country
 * 
 */
@XmlRootElement
@Entity
@Table(name = "rb_m_country")
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Expose
	@Column(name = "name")
	private String countryname;

	@Expose
	@Column(name = "courency")
	private String courency;

	@OneToMany(mappedBy = "country")
	private List<TaxForRoomBook> taxForRoomBook;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountryname() {
		return countryname;
	}

	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}

	public String getCourency() {
		return courency;
	}

	public void setCourency(String courency) {
		this.courency = courency;
	}

	public List<TaxForRoomBook> getTaxForRoomBook() {
		return taxForRoomBook;
	}

	public void setTaxForRoomBook(List<TaxForRoomBook> taxForRoomBook) {
		this.taxForRoomBook = taxForRoomBook;
	}
	

}
