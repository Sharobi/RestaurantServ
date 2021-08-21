package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.RoomType;
import com.sharobi.restaurantapp.model.roomBook.RoomTypeInfo;

public interface RoomTypeDAO {

	Boolean addRoomType(RoomTypeInfo roomType) throws DAOException;

	//void deleteRoomType(RoomType roomType) throws DAOException;

	List<RoomType> getAllRoomTypeByHotelId(String hotelId) throws DAOException;

	List<RoomType> getAllRoomTypeByHotelIdandRoomType(String hotelId, String type)
			throws DAOException;

	//void updateRoomType(RoomType roomType) throws DAOException;

	Boolean deleteRoomType(RoomTypeInfo roomType) throws DAOException;

	Boolean updateRoomType(RoomTypeInfo roomType) throws DAOException;

}
