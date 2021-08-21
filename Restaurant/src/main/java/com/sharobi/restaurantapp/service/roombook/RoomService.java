package com.sharobi.restaurantapp.service.roombook;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.dao.roombook.RoomDAO;
import com.sharobi.restaurantapp.model.roomBook.Room;
import com.sharobi.restaurantapp.model.roomBook.RoomInfo;
import com.sharobi.restaurantapp.service.exception.ServiceException;

@Service
public class RoomService {

  @Autowired
	private RoomDAO roomDAO;
	
	private final static Logger logger = LogManager.getLogger(RoomService.class);

	public Boolean addRoom(RoomInfo room) throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Service Call for Adding Room ");
			//roomDAO.addRoom(room);
			roomDAO.addRoom(room);
			logger.info("Room Saved Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add Room", e);
		}
		return status;
	}

	public Boolean updateRoom(RoomInfo room) throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Servicve Call for UPDATE Room ");
			roomDAO.updateRoom(room);
			logger.info("Room UPDATE Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to UPDATE Room", e);
		}
		return status;
	}

	public Boolean deleteRoom(RoomInfo room) throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Servicve Call for DELETE Room ");
			roomDAO.deleteRoom(room);
			logger.info("Room DELETE Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to DELETE Room", e);
		}
		return status;
	}

	public List<Room> getAllRoomByHotelId(String hotelId)
			throws ServiceException {
		List<Room> roomList = null;
		try {
			logger.info("Service Call for getAllRoomByHotelId ");
			roomList = roomDAO.getAllRoomByHotelId(hotelId);
			logger.info("getAllRoomByHotelId Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllRoomByHotelId", e);
		}
		return roomList;
	}
	
	public Room getRoomById(String hotelId,String roomId)
			throws ServiceException {
		Room room = null;
		try {
			room = roomDAO.getRoomById(hotelId,roomId);
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getRoomById", e);
		}
		return room;
	}

	public RoomDAO getRoomDAO() {
		return roomDAO;
	}

	public void setRoomDAO(RoomDAO roomDAO) {
		this.roomDAO = roomDAO;
	}

}
