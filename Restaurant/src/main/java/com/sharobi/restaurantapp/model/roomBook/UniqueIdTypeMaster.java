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
@Table(name = "rb_m_uniqueid_type")
public class UniqueIdTypeMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Expose
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Expose
	@Column(name="name")
	private String name;
	
	@Expose
	@Column(name="store_id")
	private int storeId;
	
	@Expose
	@Column(name="delete_flag")
	private String deleteFlag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
}
