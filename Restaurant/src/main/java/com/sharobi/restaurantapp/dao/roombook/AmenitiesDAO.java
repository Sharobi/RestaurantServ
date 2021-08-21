package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.Amenities;

public interface AmenitiesDAO {

	Boolean addAmenities(Amenities amenities) throws DAOException;

	Boolean deleteAmenities(Amenities amenities) throws DAOException;

	Boolean updateAmenities(Amenities amenities) throws DAOException;

	List<Amenities> getAllAmenitiesByHotelId(String hotelId)
			throws DAOException;

	List<Amenities> getAllAmenitiesByHotelIdandName(String hotelId,
			String amenitiesName) throws DAOException;

	//Boolean addAmenities(AmenitiesInfo amenities) throws DAOException;

	//Boolean updateAmenities(AmenitiesInfo amenities) throws DAOException;

}
