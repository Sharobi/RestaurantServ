package com.sharobi.restaurantapp.webservice.roombook.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sharobi.restaurantapp.model.roomBook.Room;
import com.sharobi.restaurantapp.model.roomBook.RoomInfo;
import com.sharobi.restaurantapp.service.roombook.RoomService;
import com.sharobi.restaurantapp.webservice.roombook.RoomWS;

@Controller
@ResponseBody
@RequestMapping(value = "/room")
public class RoomWSImpl implements RoomWS {

  @Autowired
	private RoomService roomService;
  
	private final static Logger logger = LogManager.getLogger(RoomWSImpl.class);

	@Override
	@RequestMapping(value = "/addRoom", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addRoom(@RequestBody RoomInfo room) {
		String result = "";
		try {
			logger.info("WebService Calling for ADD Room");
			roomService.addRoom(room);
			result = "success";
			logger.info("ADD Room Successfully");
		} catch (Exception x) {
			x.printStackTrace();
			logger.error("ERROR while ADD Room");
		}
		return result;
	}

	@Override
	@RequestMapping(value = "/updateRoom", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateRoom(@RequestBody RoomInfo room) {
		String result = "";
		try {
			logger.info("Web Service Calling For UPDATE Room");
			roomService.updateRoom(room);
			result = "success";
			logger.info("UPDATE Room Successfully");
		} catch (Exception x) {
			x.printStackTrace();
			logger.error("ERROR while UPDATE Room");
		}
		return result;
	}

	@Override
	@RequestMapping(value = "/deleteRoom", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String deleteRoom(@RequestBody RoomInfo room) {
		String result = "";
		try {
			logger.info("Web-Service Calling For DELETE Room");
			roomService.deleteRoom(room);
			result="success";
			logger.info("DELETE Room Successfully");
		} catch (Exception x) {
			x.printStackTrace();
			logger.error("ERROR While DELETE Room");
		}

    return result;
	}

	@Override
	@RequestMapping(value = "/getAllRoomByHotelId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllRoomByHotelId(
			@RequestParam(value = "hotelId") String hotelId) {
		List<Room> roomList = null;
		try {
			logger.info("web-Service calling For Get ALL Room");
			roomList = roomService.getAllRoomByHotelId(hotelId);
		} catch (Exception x) {
			logger.error("ERROR While Get ALL Room");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Room>>() {
		}.getType();
		String json = gson.toJson(roomList, type);

		return json;
	}
	
	@Override
	@RequestMapping(value = "/getRoomById", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getRoomById(@RequestParam(value = "hotelId") String hotelId,@RequestParam(value = "roomId") String roomId) {
		Room room = null;
		try {
			logger.info("web-Service calling For Get Room by id");
			room = roomService.getRoomById(hotelId,roomId);
		} catch (Exception x) {
			logger.error("ERROR While Get Room by id");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<Room>() {
		}.getType();
		String json = gson.toJson(room, type);

		return json;
	}

	public RoomService getRoomService() {
		return roomService;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

}
