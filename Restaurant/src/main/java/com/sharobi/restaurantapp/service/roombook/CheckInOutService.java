package com.sharobi.restaurantapp.service.roombook;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.dao.roombook.CheckInOutDAO;
import com.sharobi.restaurantapp.model.roomBook.CheckInOut;
import com.sharobi.restaurantapp.service.exception.ServiceException;

@Service
public class CheckInOutService {

  @Autowired
  private CheckInOutDAO CheckInOutDAO;
  
	private final static Logger logger = LogManager.getLogger(CheckInOutService.class);
	
	public CheckInOutService() {
		
	}
	public Boolean addCheckInOut(CheckInOut checkInOut) throws ServiceException {
		Boolean status=false;
		try {
			logger.info("Servicve Call for Adding CheckInOut ");
			CheckInOutDAO.addCheckInOut(checkInOut);
			logger.info("CheckInOut Saved Successfully ");
			status=true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add CheckInOut", e);
		}
		return status;
	}
	
	public Boolean updateCheckInOut(CheckInOut checkInOut) throws ServiceException {
		Boolean status=false;
		try {
			logger.info("Servicve Call for UPDATE CheckInOut ");
			CheckInOutDAO.updateCheckInOut(checkInOut);
			logger.info("CheckInOut UPDATE Successfully ");
			status=true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add CheckInOut", e);
		}
		return status;
	}
	
	public Boolean deleteCheckInOut(CheckInOut checkInOut) throws ServiceException {
		Boolean status=false;
		try {
			logger.info("Servicve Call for DELETE CheckInOut ");
			CheckInOutDAO.deleteCheckInOut(checkInOut);
			logger.info("CheckInOut DELETE Successfully ");
			status=true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add DELETE", e);
		}
		return status;
	}
	
	public List<CheckInOut> getAllCheckInOutByHotelId(String hotelId) throws ServiceException {
		
		List<CheckInOut> checkInOut=null;
		try {
			logger.info("Servicve Call for getAllAmenitiesByHotelId ");
			checkInOut=CheckInOutDAO.getAllCheckInOutByHotelId(hotelId);
			logger.info("CheckInOut getAllAmenitiesByHotelId ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllAmenitiesByHotelId", e);
		}
		return checkInOut;
	}
	
	public CheckInOutDAO getCheckInOutDAO() {
		return CheckInOutDAO;
	}

	public void setCheckInOutDAO(CheckInOutDAO checkInOutDAO) {
		CheckInOutDAO = checkInOutDAO;
	}
	
}
