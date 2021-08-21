package com.sharobi.restaurantapp.webservice.roombook;

import com.sharobi.restaurantapp.model.roomBook.Amenities;

public interface AmenitiesWS {

	String addAmenities(Amenities amenities);

	String updateAmenities(Amenities amenities);

	String deleteAmenities(Amenities amenities);

	String getAllTaxesByHotelIdandName(String hotelId, String amenitiesName);

	String getAllAmenitiesByHotelId(String hotelId);

	//Boolean addAmenities(AmenitiesInfo amenities);

	//Boolean updateAmenities(AmenitiesInfo amenities);

}
