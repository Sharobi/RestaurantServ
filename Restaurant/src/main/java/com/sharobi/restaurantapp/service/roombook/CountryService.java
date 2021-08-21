package com.sharobi.restaurantapp.service.roombook;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.dao.roombook.CountryDAO;
import com.sharobi.restaurantapp.model.roomBook.Country;
import com.sharobi.restaurantapp.service.exception.ServiceException;

@Service
public class CountryService {

  @Autowired
	private CountryDAO countryDAO;
  
	private final static Logger logger = LogManager.getLogger(CountryService.class);
	
	public CountryService() {
		
	}
	
	public Boolean addCountry(Country country) throws ServiceException {
		Boolean status=false;
		try {
			logger.info("Servicve Call for Adding Country ");
			countryDAO.addCountry(country);
			logger.info("Country Saved Successfully ");
			status=true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add Country", e);
		}
		return status;
	}
	
	public Boolean updateCountry(Country country) throws ServiceException {
		Boolean status=false;
		try {
			logger.info("Servicve Call for UPDATE Country ");
			countryDAO.updateCountry(country);
			logger.info("Country UPDATE Successfully ");
			status=true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to UPDATE Country", e);
		}
		return status;
	}
	
	public Boolean deleteCountry(Country country) throws ServiceException {
		Boolean status=false;
		try {
			logger.info("Servicve Call for DELETE Country ");
			countryDAO.deleteCountry(country);
			logger.info("Country DELETE Successfully ");
			status=true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to DELETE Country", e);
		}
		return status;
	}
	
	public Country getCountryByName(String countryName) throws ServiceException {
		Country countryNameList = null;
		try {
			logger.info("Servicve Call for getCountryByName ");
			countryNameList=countryDAO.getCountryByName(countryName);
			logger.info("getCountryByName Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getCountryByName", e);
		}
		return countryNameList;
	}
	
	public List<Country> getAllCountry() throws ServiceException {
		List<Country> countryList = null;
		try {
			logger.info("Servicve Call for getAllCountry ");
			countryList=countryDAO.getAllCountry();
			logger.info("getAllCountry Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllCountry", e);
		}
		return countryList;
	}
	public String saveCountryIdinRestaurant(int storeId, int countryId) throws ServiceException {
		String status=null;
		try {
			logger.info("Servicve Call for Adding Country saved in Restaurant ");
			countryDAO.saveCountryIdinRestaurant(storeId,countryId);
			logger.info("Country saved in Restaurant Successfully... ");
			status="success";
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add Country saved in Restaurant", e);
		}
		return status;
	}
	
	public CountryDAO getCountryDAO() {
		return countryDAO;
	}

	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}
	
}
