package com.sharobi.restaurantapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.license.struts.key.KeyBean;
import com.sharobi.restaurantapp.dao.LicenseDAO;
import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.service.exception.ServiceException;

@Service
public class LicenseService {

  @Autowired
	private LicenseDAO licenseDAO;

	public LicenseService() {

	}

	public KeyBean addStoreLicenseInformation(String licenseKey)
			throws ServiceException {

		KeyBean bean = null;

		try {

			bean = licenseDAO.addStoreLicenseInformation(licenseKey);

		} catch (DAOException e) {
			throw new ServiceException("error creating PO", e);

		}
		return bean;
	}

	public KeyBean aboutProduct(String storeId) throws ServiceException {
		
		KeyBean bean = null;

		try {

			bean = licenseDAO.aboutProduct(storeId);

		} catch (DAOException e) {
			throw new ServiceException("error creating PO", e);

		}
		return bean;
	}

	public LicenseDAO getLicenseDAO() {
		return licenseDAO;
	}

	public void setLicenseDAO(LicenseDAO licenseDAO) {
		this.licenseDAO = licenseDAO;
	}

}
