package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.RoomServices;

public interface RoomServicesDAO {

	Boolean addRoomService(RoomServices roomStatus) throws DAOException;

	void updateRoomService(RoomServices roomStatus) throws DAOException;

	void deleteRoomService(RoomServices roomStatus) throws DAOException;

	RoomServices getRoomServiceById(int roomStatusId) throws DAOException;

	List<RoomServices> getAllRoomService(String hotelId) throws DAOException;

}
