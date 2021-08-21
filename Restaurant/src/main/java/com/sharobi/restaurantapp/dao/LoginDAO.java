package com.sharobi.restaurantapp.dao;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.Customer;
import com.sharobi.restaurantapp.model.PosModules;

public interface LoginDAO {

	Customer getUserByCredential(Customer customer) throws DAOException;

	public Customer getUserByContantNo(Customer customer) throws DAOException;

	public List<Customer> getAllWaiters(String storeId) throws DAOException;

	public List<PosModules> getPosModulesByUserId(String userId,
			String storeId) throws DAOException;
	
	public List<PosModules> getReportByStore(String userId,
			String storeId) throws DAOException;
}
