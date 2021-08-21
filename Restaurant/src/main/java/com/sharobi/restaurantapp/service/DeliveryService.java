package com.sharobi.restaurantapp.service;

import java.util.List;

import com.sharobi.restaurantapp.dao.DeliveriesDAO;
import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.OrderMaster;
import com.sharobi.restaurantapp.service.exception.ServiceException;

public class DeliveryService {

	DeliveriesDAO deliveryDao = null;

	public DeliveryService() {
		
	}

	public void updateDeliveryStatus(List<OrderMaster> orderList)
			throws ServiceException {

		try {

			System.out.println("Enter DeliveryService.updateDeliveryStatus ");
			// update delivery status
			deliveryDao.updateDeliveryStatus(orderList);
			System.out.println("exit DeliveryService.updateDeliveryStatus ");

		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to update delivery status", e);

		}

	}

}
