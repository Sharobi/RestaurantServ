package com.sharobi.restaurantapp.webservice;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.DeliveryBoy;

/**
 * @author ChanchalN
 */
public interface DeliveryBoyWS {
	public String addDeliveryBoy(DeliveryBoy deliveryBoy) throws DAOException;
	public String editDeliveryBoy(DeliveryBoy deliveryBoy) throws DAOException;
	public String deleteDeliveryBoy(int id, String storeId) throws DAOException;
	public String getDeliveryBoyById(int id) throws DAOException;
	//public List<DeliveryBoy> getAllDeliveryBoy() throws DAOException;
	public String getAllDeliveryBoy(String store_id) throws DAOException;
}
