package com.sharobi.restaurantapp.dao;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.OrderMaster;

public interface DeliveriesDAO {

	public void updateDeliveryStatus(List<OrderMaster> orderList) throws DAOException;
	
	
	
}
