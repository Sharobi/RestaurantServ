package com.sharobi.restaurantapp.webservice.roombook;

import com.sharobi.restaurantapp.model.roomBook.Room;
import com.sharobi.restaurantapp.model.roomBook.RoomInfo;

public interface RoomWS {

	String addRoom(RoomInfo room);

	String updateRoom(RoomInfo room);

	String deleteRoom(RoomInfo room);

	String getAllRoomByHotelId(String hotelId);
	String getRoomById(String hotelId,String roomId);

}
