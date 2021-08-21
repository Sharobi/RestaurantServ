package com.sharobi.restaurantapp.webservice.roombook;

import com.sharobi.restaurantapp.model.roomBook.RoomServices;

public interface RoomServiceWS {

	String addRoomService(RoomServices roomService);

	String updateRoomService(RoomServices roomService);

	String deleteRoomService(RoomServices roomService);

	String getAllRoomServices(String hotelId);

}
