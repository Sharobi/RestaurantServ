package com.sharobi.restaurantapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.restaurantapp.dao.MaintenenceJobDAO;
import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.service.exception.ServiceException;

@Service
public class MaintenenceJobService {

  @Autowired
	private MaintenenceJobDAO maintenenceJobDAO;

	public MaintenenceJobService() {

	}

	public String cleanLogByPeriod(String tomcatDir, String days)
			throws ServiceException {
		String status = "";
		try {

			status = maintenenceJobDAO.cleanLogByPeriod(tomcatDir, days);

		} catch (DAOException e) {
			status="failure";
			throw new ServiceException("error cleaning logs", e);

		}
		return status;
	}

	public MaintenenceJobDAO getMaintenenceJobDAO() {
		return maintenenceJobDAO;
	}

	public void setMaintenenceJobDAO(MaintenenceJobDAO maintenenceJobDAO) {
		this.maintenenceJobDAO = maintenenceJobDAO;
	}

}
