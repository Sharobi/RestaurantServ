package com.sharobi.restaurantapp.service.roombook;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.dao.roombook.RoomTypeDAO;
import com.sharobi.restaurantapp.model.roomBook.RoomType;
import com.sharobi.restaurantapp.model.roomBook.RoomTypeInfo;
import com.sharobi.restaurantapp.service.exception.ServiceException;

@Service
public class RoomTypeService {

  @Autowired
	private RoomTypeDAO roomTypeDAO;
  
	private final static Logger logger = LogManager.getLogger(RoomTypeService.class);

	private RoomTypeService() {

	}

	public Boolean addRoomType(RoomTypeInfo roomType) throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Servicve Call for Adding RoomType ");
			roomTypeDAO.addRoomType(roomType);
			logger.info("Amenities Saved Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add RoomType", e);
		}
		return status;
	}

	public Boolean updateRoomType(RoomTypeInfo roomType) throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Servicve Call for UPDATE RoomType ");
			roomTypeDAO.updateRoomType(roomType);
			logger.info("Amenities UPDATED Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to UPDATE RoomType", e);
		}
		return status;
	}

	public Boolean deleteRoomType(RoomTypeInfo roomType) throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Servicve Call for UPDATE RoomType ");
			roomTypeDAO.deleteRoomType(roomType);
			logger.info("Amenities UPDATED Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to UPDATE RoomType", e);
		}
		return status;
	}

	public List<RoomType> getAllRoomTypeByHotelId(String hotelId) throws ServiceException {
		List<RoomType> roomTypeList = null;
		try {
			logger.info("Servicve Call for getAllRoomTypeByHotelId ");
			roomTypeList = roomTypeDAO.getAllRoomTypeByHotelId(hotelId);
			logger.info("getAllRoomTypeByHotelId Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllRoomTypeByHotelId",
					e);
		}
		return roomTypeList;
	}
	public List<RoomType> getAllRoomTypeByHotelIdandRoomType(String hotelId,
			String type) throws ServiceException {
		List<RoomType> roomTypeList = null;
		try {
			logger.info("Servicve Call for getAllRoomTypeByHotelIdandRoomType ");
			roomTypeList = roomTypeDAO.getAllRoomTypeByHotelIdandRoomType(
					hotelId, type);
			logger.info("getAllRoomTypeByHotelIdandRoomType Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllRoomTypeByHotelIdandRoomType",
					e);
		}
		return roomTypeList;
	}

	public RoomTypeDAO getRoomTypeDAO() {
		return roomTypeDAO;
	}

	public void setRoomTypeDAO(RoomTypeDAO roomTypeDAO) {
		this.roomTypeDAO = roomTypeDAO;
	}
	
}
