package com.sharobi.restaurantapp.dao;

import com.sharobi.restaurantapp.dao.exception.DAOException;

public interface MaintenenceJobDAO {

	public String cleanLogByPeriod(String tomcatDir, String days)
			throws DAOException;

}
