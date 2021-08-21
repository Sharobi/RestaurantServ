package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.Room;
import com.sharobi.restaurantapp.model.roomBook.RoomInfo;

public interface RoomDAO {

	Boolean addRoom(RoomInfo room) throws DAOException;

	Boolean deleteRoom(RoomInfo room) throws DAOException;

	Boolean updateRoom(RoomInfo room) throws DAOException;

	List<Room> getAllRoomByHotelId(String hotelId) throws DAOException;
	
	Room getRoomById(String hotelId,String roomId) throws DAOException;

	List<List<Room>> getAllRoomByFloor(String hotelId) throws DAOException;

}
