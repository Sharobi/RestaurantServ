package com.sharobi.restaurantapp.service.roombook;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.dao.roombook.RoomServicesDAO;
import com.sharobi.restaurantapp.model.roomBook.RoomServices;
import com.sharobi.restaurantapp.service.exception.ServiceException;

@Service
public class RoomServicesService {

  @Autowired
	private RoomServicesDAO roomServicesDAO ;
	
	private final static Logger logger = LogManager.getLogger(RoomServicesService.class);

	public RoomServicesService() {
	}

	public Boolean addRoomService(RoomServices roomService) throws ServiceException {
		Boolean status = false;
		try {
			roomServicesDAO.addRoomService(roomService);
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add RoomServices", e);
		}
		return status;
	}
	
	public Boolean updateRoomService(RoomServices roomService) throws ServiceException {
		Boolean status = false;
		try {
			roomServicesDAO.updateRoomService(roomService);
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to update RoomServices", e);
		}
		return status;
	}
	
	public Boolean deleteRoomService(RoomServices roomService) throws ServiceException {
		Boolean status = false;
		try {
			roomServicesDAO.deleteRoomService(roomService);
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to delete RoomServices", e);
		}
		return status;
	}
	
	public List<RoomServices> getAllRoomServices(String hotelId) throws ServiceException {
		List<RoomServices> roomServiceList = null;
		try {
			roomServiceList=roomServicesDAO.getAllRoomService(hotelId);
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllRoomServices", e);
		}
		return roomServiceList;
	}
	
	public RoomServicesDAO getRoomServicesDAO() {
		return roomServicesDAO;
	}

	public void setRoomServicesDAO(RoomServicesDAO roomServicesDAO) {
		this.roomServicesDAO = roomServicesDAO;
	}

}
