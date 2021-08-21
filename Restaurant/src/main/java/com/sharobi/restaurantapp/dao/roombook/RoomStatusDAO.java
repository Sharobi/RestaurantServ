package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.RoomStatus;

public interface RoomStatusDAO {

	Boolean addRoomStatus(RoomStatus roomStatus) throws DAOException;

	void updateRoomService(RoomStatus roomStatus) throws DAOException;

	void deleteRoomStatus(RoomStatus roomStatus) throws DAOException;

	List<RoomStatus> getAllRoomStatus(String hotelId) throws DAOException;

}
