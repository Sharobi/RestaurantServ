/**
 * 
 */
package com.sharobi.restaurantapp.dao;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.DeliveryBoy;

/**
 * @author ChanchalN
 *
 */
public interface DeliveryBoyDAO {
	
	public String createDeliveryBoy(DeliveryBoy deliveryBoy) throws DAOException;
	public String updateDeliveryBoy(DeliveryBoy deliveryBoy) throws DAOException;
	public String deleteDeliveryBoy(int id, String storeId) throws DAOException;
	public DeliveryBoy getDeliveryBoyById(int id) throws DAOException;
	public List<DeliveryBoy> getAllDeliveryBoy(String id) throws DAOException;

}
