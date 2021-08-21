package com.sharobi.restaurantapp.model.roomBook;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class IdentityFile implements Serializable {

	@Expose
	private String fileName;

	@Expose
	private byte[] data;

	@Expose
	private int storeId;
	
	@Expose
	private String bytes;

	private static final long serialVersionUID = 1L;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getBytes() {
		return bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}
	
	

}