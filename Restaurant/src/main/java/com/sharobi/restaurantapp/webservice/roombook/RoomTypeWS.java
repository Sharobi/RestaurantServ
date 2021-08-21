package com.sharobi.restaurantapp.webservice.roombook;

import com.sharobi.restaurantapp.model.roomBook.RoomTypeInfo;

public interface RoomTypeWS {

	String addRoomType(RoomTypeInfo roomType);

	//Boolean updateRoomType(RoomType roomType);

	//Boolean deleteRoomType(RoomType roomType);

	String getAllRoomTypeByHotelIdandRoomType(String hotelId, String type);

	String getAllRoomTypeByHotelId(String hotelId);

	String deleteRoomType(RoomTypeInfo roomType);

	String updateRoomType(RoomTypeInfo roomType);

}
