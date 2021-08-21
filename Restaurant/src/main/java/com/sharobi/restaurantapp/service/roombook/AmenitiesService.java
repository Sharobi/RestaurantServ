package com.sharobi.restaurantapp.service.roombook;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.dao.roombook.AmenitiesDAO;
import com.sharobi.restaurantapp.model.roomBook.Amenities;
import com.sharobi.restaurantapp.service.exception.ServiceException;

public class AmenitiesService {

	private AmenitiesDAO amenitiesDAO=null;
	private final static Logger logger = LogManager
			.getLogger(AmenitiesService.class);
	public AmenitiesService() {
		
	}
	
	public Boolean addAmenities(Amenities amenities) throws ServiceException {
		Boolean status=false;
		try {
			logger.info("Servicve Call for Adding Amenities ");
			amenitiesDAO.addAmenities(amenities);
			logger.info("Amenities Saved Successfully ");
			status=true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add Amenities", e);
		}
		return status;
	}
	
	public Boolean updateAmenities(Amenities amenities) throws ServiceException {
		Boolean status=false;
		try {
			logger.info("Servicve Call for Update Amenities ");
			amenitiesDAO.updateAmenities(amenities);
			logger.info("Amenities UPDATED Successfully ");
			status=true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to UPDATE Amenities", e);
		}
		return status;
	}
	
	public Boolean deleteAmenities(Amenities amenities) throws ServiceException {
		Boolean status=false;
		try {
			logger.info("Servicve Call for DELETE Amenities ");
			amenitiesDAO.deleteAmenities(amenities);
			logger.info("Amenities DELETE Successfully ");
			status=true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to DELETE Amenities", e);
		}
		return status;
	}
	
	public List<Amenities> getAllAmenitiesByHotelId(String hotelId) throws ServiceException {
		List<Amenities> amenitiesList = null;
		try {
			logger.info("Servicve Call for getAllAmenitiesByHotelId ");
			amenitiesList=amenitiesDAO.getAllAmenitiesByHotelId(hotelId);
			logger.info("getAllAmenitiesByHotelId Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllAmenitiesByHotelId", e);
		}
		return amenitiesList;
	}
	
	public List<Amenities> getAllAmenitiesByHotelIdandName(String hotelId,String amenitiesName) throws ServiceException {
		List<Amenities> amenitiesList = null;
		try {
			logger.info("Servicve Call for getAllTaxesByHotelIdandType ");
			amenitiesList=amenitiesDAO.getAllAmenitiesByHotelIdandName(hotelId,amenitiesName);
			logger.info("getAllTaxesByHotelIdandType Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllTaxesByHotelIdandType", e);
		}
		return amenitiesList;
	}

	public AmenitiesDAO getAmenitiesDAO() {
		return amenitiesDAO;
	}

	public void setAmenitiesDAO(AmenitiesDAO amenitiesDAO) {
		this.amenitiesDAO = amenitiesDAO;
	}
	
	
}
