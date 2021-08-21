package com.sharobi.restaurantapp.service.roombook;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.dao.roombook.RoomStatusDAO;
import com.sharobi.restaurantapp.model.roomBook.RoomStatus;
import com.sharobi.restaurantapp.service.exception.ServiceException;

@Service
public class RoomStatusService {

  @Autowired
	private RoomStatusDAO roomStatusDAO;

  private final static Logger logger = LogManager.getLogger(RoomStatusService.class);

	public Boolean addRoomStatus(RoomStatus roomStatus) throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Servicve Call for Adding RoomStatus ");
			roomStatusDAO.addRoomStatus(roomStatus);
			logger.info("RoomStatus Saved Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to add RoomStatus", e);
		}
		return status;
	}

	public Boolean updateRoomStatus(RoomStatus roomStatus)
			throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Servicve Call for Update RoomStatus ");
			roomStatusDAO.addRoomStatus(roomStatus);
			logger.info("RoomStatus Update Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to Update RoomStatus", e);
		}
		return status;
	}

	public Boolean deleteRoomStatus(RoomStatus roomStatus)
			throws ServiceException {
		Boolean status = false;
		try {
			logger.info("Servicve Call for Delete RoomStatus ");
			roomStatusDAO.addRoomStatus(roomStatus);
			logger.info("RoomStatus Delete Successfully ");
			status = true;
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to Delete RoomStatus", e);
		}
		return status;
	}

	public List<RoomStatus> getAllRoomStatus(String hotelId)
			throws ServiceException {
		List<RoomStatus> roomStatusList = null;
		try {
			logger.info("Servicve Call for getAllRoomStatus ");
			roomStatusList = roomStatusDAO.getAllRoomStatus(hotelId);
			logger.info("getAllCountry Successfully ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while trying to getAllRoomStatus", e);
		}
		return roomStatusList;
	}

	public RoomStatusService() {
	}

	public RoomStatusDAO getRoomStatusDAO() {
		return roomStatusDAO;
	}

	public void setRoomStatusDAO(RoomStatusDAO roomStatusDAO) {
		this.roomStatusDAO = roomStatusDAO;
	}

}
