package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.CheckInOut;

public interface CheckInOutDAO {

	Boolean addCheckInOut(CheckInOut checkInOut) throws DAOException;

	Boolean updateCheckInOut(CheckInOut checkInOut) throws DAOException;

	Boolean deleteCheckInOut(CheckInOut checkInOut) throws DAOException;

	List<CheckInOut> getAllCheckInOutByHotelId(String hotelId)
			throws DAOException;

}
