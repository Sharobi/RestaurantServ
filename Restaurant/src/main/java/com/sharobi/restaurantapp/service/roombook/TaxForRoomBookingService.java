package com.sharobi.restaurantapp.service.roombook;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.dao.roombook.TaxForRoomBookDAO;
import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBook;
import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBookInfo;
import com.sharobi.restaurantapp.service.exception.ServiceException;

@Service
public class TaxForRoomBookingService {

  @Autowired
	private TaxForRoomBookDAO taxForRoomBookDAO;
  
	private final static Logger logger = LogManager.getLogger(TaxForRoomBookingService.class);

	public TaxForRoomBookingService() {

	}

	public Boolean addTaxForRoomBook(TaxForRoomBookInfo taxForRoomBook, HttpServletRequest request)
			throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Servicve Call for Adding TaxForRoomBook ");
			taxForRoomBookDAO.addTaxForRoomBook(taxForRoomBook,request);
			logger.info("TaxForRoomBook Saved Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add TaxForRoomBook", e);
		}
		return status;
	}

	public Boolean updateTaxForRoomBook(TaxForRoomBookInfo taxForRoomBook)
			throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Servicve Call for UPDATE TaxForRoomBook ");
			taxForRoomBookDAO.updateTaxForRoomBook(taxForRoomBook);
			logger.info("TaxForRoomBook UPDATE Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to UPDATE TaxForRoomBook", e);
		}
		return status;
	}

	public Boolean deleteTaxForRoomBook(TaxForRoomBookInfo taxForRoomBook)
			throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Servicve Call for DELETE TaxForRoomBook ");
			taxForRoomBookDAO.deleteTaxForRoomBook(taxForRoomBook);
			logger.info("TaxForRoomBook DELETE Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to UPDATE TaxForRoomBook", e);
		}
		return status;
	}

	public List<TaxForRoomBook> getAllTaxesByHotelIdandCountryId(String hotelId,
			String countryId) throws ServiceException {
		List<TaxForRoomBook> taxForRoomBookList = null;
		try {
			logger.info("Servicve Call for getAllTaxesByHotelIdand CountryId ");
			taxForRoomBookList=taxForRoomBookDAO.getAllTaxesByHotelIdandCountryId(hotelId, countryId);
			logger.info("getAllTaxesByHotelIdandCountryId Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllTaxesByHotelIdandCountryid", e);
		}
		return taxForRoomBookList;
	}

	public List<TaxForRoomBook> getAllTaxesByHotelIdandName(String hotelId,String name) throws ServiceException {
		List<TaxForRoomBook> taxForRoomBookList = null;
		try {
			logger.info("Servicve Call for getAllTaxesByHotelId ");
			taxForRoomBookList=taxForRoomBookDAO.getAllTaxesByHotelIdandName(hotelId,name);
			logger.info("getAllTaxesByHotelId Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllTaxesByHotelId", e);
		}
		return taxForRoomBookList;
	}
	
	public List<TaxForRoomBook> getAllTaxesByHotelId(String hotelId) throws ServiceException {
		List<TaxForRoomBook> taxForRoomBookList = null;
		try {
			logger.info("Servicve Call for getAllTaxesByHotelId ");
			taxForRoomBookList=taxForRoomBookDAO.getAllTaxesByHotelId(hotelId);
			logger.info("getAllTaxesByHotelId Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllTaxesByHotelId", e);
		}
		return taxForRoomBookList;
	}
	public TaxForRoomBookDAO getTaxForRoomBookDAO() {
		return taxForRoomBookDAO;
	}

	public void setTaxForRoomBookDAO(TaxForRoomBookDAO taxForRoomBookDAO) {
		this.taxForRoomBookDAO = taxForRoomBookDAO;
	}
}
