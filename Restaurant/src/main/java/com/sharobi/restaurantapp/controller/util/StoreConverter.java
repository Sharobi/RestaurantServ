package com.sharobi.restaurantapp.controller.util;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sharobi.restaurantapp.dao.StoreAddressDAO;
import com.sharobi.restaurantapp.dao.StoreAddressDAOImpl;
import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.StoreMaster;

@ManagedBean(name = "storeConverter")
//@FacesConverter(value="orderConverter") // TODO: need to check, if it is required.
public class StoreConverter implements Converter {
	private final static Logger logger = LogManager.getLogger(StoreConverter.class);

	public StoreConverter() {

	}

	private StoreAddressDAO storeAddressDAO = new StoreAddressDAOImpl();
	private StoreMaster storeMaster = null;

	@Override
	public Object getAsObject(	FacesContext context,
								UIComponent component,
								String value) {
		System.out.println("In StoreConverter: getAsObject");
		logger.debug("In getAsObject");
		if (value == null || value.isEmpty()) {
			System.out.println("value: " + value);
			logger.debug("vlue:" + value);
			return null;
		}

		String storeId = value.toString();
		System.out.println("store id: " + storeId);
		logger.debug("store id:" + storeId);
		int store_Id = Integer.parseInt(storeId);
		logger.debug("store id:" + store_Id);
		try {
			storeMaster = storeAddressDAO.getStoreByStoreId(store_Id);
		} catch (DAOException e) {
			logger.error("store id: " + store_Id, e);
		}
		return storeMaster;
	}

	@Override
	public String getAsString(	FacesContext context,
								UIComponent component,
								Object value) {
		System.out.println("In StoreConverter: getAsString");
		logger.debug("In getAsString");
		if (value == null) {
			return null;
		}
		String id = Integer.toString(((StoreMaster) value).getId());
		System.out.println("id is: "+ id);
		return id;
	}
}
