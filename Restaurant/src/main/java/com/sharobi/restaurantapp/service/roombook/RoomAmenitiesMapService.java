package com.sharobi.restaurantapp.service.roombook;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.RoomAmenitiesMap;
import com.sharobi.restaurantapp.model.roomBook.RoomAmenitiesMapInfo;
import com.sharobi.restaurantapp.service.exception.ServiceException;

@Service
public class RoomAmenitiesMapService {

  @Autowired
	private com.sharobi.restaurantapp.dao.roombook.RoomAmenitiesMapDAO roomAmenitiesMapDAO;
  
	private final static Logger logger = LogManager.getLogger(RoomAmenitiesMapService.class);

	public RoomAmenitiesMapService() {

	}

	public String assignAmenitiesToRoomType(
			RoomAmenitiesMapInfo roomAmenitiesMapInfoObj) throws ServiceException {
		String status = null;
		try {
			logger.info("Servicve Call for Adding assignAmenitiesToRoomType ");
			roomAmenitiesMapDAO.assignAmenitiesToRoomType(roomAmenitiesMapInfoObj);
			logger.info("assignAmenitiesToRoomType Saved Successfully ");
			status = "success";
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add assignAmenitiesToRoomType", e);
		}
		return status;
	}

	public String updateAmenitiesToRoomType(
			RoomAmenitiesMapInfo roomAmenitiesMapInfo) throws ServiceException {
		String status = null;
		try {
			logger.info("Servicve Call for Adding assignAmenitiesToRoomType ");
			roomAmenitiesMapDAO.updateAmenitiesToRoomType(roomAmenitiesMapInfo);
			logger.info("assignAmenitiesToRoomType Saved Successfully ");
			status = "success";
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add assignAmenitiesToRoomType", e);
		}
		return status;
	}
	
	public List<RoomAmenitiesMap> getAllAmenitiesToRoomTypeById(String hotelId,int roomTypeId)
			throws ServiceException {
		List<RoomAmenitiesMap> roomAmenitiesMapList = null;
		try {
			logger.info("Servicve Call for getAllAmenitiesToRoomTypeById ");
			roomAmenitiesMapList = roomAmenitiesMapDAO.getAllAmenitiesToRoomTypeById(hotelId,roomTypeId);
			logger.info("getAllAmenitiesToRoomTypeById Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllAmenitiesToRoomTypeById", e);
		}
		return roomAmenitiesMapList;
	}

	public com.sharobi.restaurantapp.dao.roombook.RoomAmenitiesMapDAO getRoomAmenitiesMapDAO() {
		return roomAmenitiesMapDAO;
	}

	public void setRoomAmenitiesMapDAO(
			com.sharobi.restaurantapp.dao.roombook.RoomAmenitiesMapDAO roomAmenitiesMapDAO) {
		this.roomAmenitiesMapDAO = roomAmenitiesMapDAO;
	}
	
}
