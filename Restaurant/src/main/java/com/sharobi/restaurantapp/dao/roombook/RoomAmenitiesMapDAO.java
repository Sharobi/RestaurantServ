package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.RoomAmenitiesMap;
import com.sharobi.restaurantapp.model.roomBook.RoomAmenitiesMapInfo;

public interface RoomAmenitiesMapDAO {

	List<RoomAmenitiesMap> getAllAmenitiesToRoomTypeById(String hotelId,
			int roomTypeId) throws DAOException;

	String assignAmenitiesToRoomType(
			RoomAmenitiesMapInfo roomAmenitiesMapInfoObj) throws DAOException;

	String updateAmenitiesToRoomType(
			RoomAmenitiesMapInfo roomAmenitiesMapInfoObj) throws DAOException;

}
