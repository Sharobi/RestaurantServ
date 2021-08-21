package com.sharobi.restaurantapp.dao;

import com.sharobi.license.struts.key.KeyBean;
import com.sharobi.restaurantapp.dao.exception.DAOException;

public interface LicenseDAO {

	public KeyBean addStoreLicenseInformation(String licenseKey) throws DAOException;
	
	public KeyBean aboutProduct(String storeId) throws DAOException;
	
}
