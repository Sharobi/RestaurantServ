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
import com.sharobi.restaurantapp.model.roomBook.RoomServices;
import com.sharobi.restaurantapp.service.roombook.RoomServicesService;
import com.sharobi.restaurantapp.webservice.roombook.RoomServiceWS;


@Controller
@ResponseBody
@RequestMapping(value = "/roomservice")
public class RoomServiceWSImpl implements RoomServiceWS {

  @Autowired
	private RoomServicesService roomServicesService;
  
	private final static Logger logger = LogManager.getLogger(RoomServiceWSImpl.class);
	
	@Override
	@RequestMapping(value = "/addRoomservice", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addRoomService(@RequestBody RoomServices roomService) {
		String result = "";
		try {
			roomServicesService.addRoomService(roomService);
			result = "success";
		} catch (Exception x) {
			x.printStackTrace();
		}
		return result;
	}

	@Override
	@RequestMapping(value = "/updateRoomService", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateRoomService(@RequestBody RoomServices roomService) {
		String result = "";
		try {
			roomServicesService.updateRoomService(roomService);
			result = "success";
		} catch (Exception x) {
			x.printStackTrace();
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/deleteRoomService", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String deleteRoomService(@RequestBody RoomServices roomService) {
		String result = "";
		try {
			roomServicesService.deleteRoomService(roomService);
			result = "success";
		} catch (Exception x) {
			x.printStackTrace();
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/getAllRoomServices", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllRoomServices(@RequestParam(value = "hotelId") String hotelId) {
		List<RoomServices> roomServiceList = null;
		try {
			roomServiceList = roomServicesService.getAllRoomServices(hotelId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomServices>>() {
		}.getType();
		String json = gson.toJson(roomServiceList, type);

		return json;
	}

	public RoomServicesService getRoomServicesService() {
		return roomServicesService;
	}

	public void setRoomServicesService(RoomServicesService roomServicesService) {
		this.roomServicesService = roomServicesService;
	}
	
}
