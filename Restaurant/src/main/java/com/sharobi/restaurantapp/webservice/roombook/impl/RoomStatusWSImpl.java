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
import com.sharobi.restaurantapp.model.roomBook.Country;
import com.sharobi.restaurantapp.model.roomBook.RoomStatus;
import com.sharobi.restaurantapp.service.roombook.RoomStatusService;
import com.sharobi.restaurantapp.webservice.roombook.RoomStatusWS;

@Controller
@ResponseBody
@RequestMapping(value = "/roomstatus")
public class RoomStatusWSImpl implements RoomStatusWS {

  @Autowired
	private RoomStatusService roomStatusService;
  
	private final static Logger logger = LogManager.getLogger(RoomStatusWSImpl.class);

	@RequestMapping(value = "/addRoomStatus", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addRoomStatus(@RequestBody RoomStatus roomStatus) {
		String result = "";
		try {
			logger.info("WebSwervice Calling for ADD RoomStatus");
			roomStatusService.addRoomStatus(roomStatus);
			result = "success";
			logger.info("ADD RoomStatus Successfully");
		} catch (Exception x) {
			x.printStackTrace();
			logger.error("ERROR while ADD RoomStatus");
		}
		return result;
	}

	@RequestMapping(value = "/updateRoomStatus", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateRoomService(@RequestBody RoomStatus roomStatus) {
		String result = "";
		try {
			logger.info("WebSwervice Calling for UPDATE RoomStatus");
			roomStatusService.updateRoomStatus(roomStatus);
			result = "success";
			logger.info("UPDATE RoomStatus Successfully");
		} catch (Exception x) {
			x.printStackTrace();
			logger.error("ERROR while UPDATE RoomStatus");
		}
		return result;
	}

	@RequestMapping(value = "/deleteRoomStatus", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String deleteRoomStatus(@RequestBody RoomStatus roomStatus) {
		String result = "";
		try {
			logger.info("WebSwervice Calling for DELETE RoomStatus");
			roomStatusService.updateRoomStatus(roomStatus);
			result = "success";
			logger.info("DELETE RoomStatus Successfully");
		} catch (Exception x) {
			x.printStackTrace();
			logger.error("ERROR while DELETE RoomStatus");
		}
		return result;
	}

	@RequestMapping(value = "/getAllRoomStatus", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllRoomStatus(@RequestParam(value = "hotelId") String hotelId) {
		List<RoomStatus> roomStatusList = null;
		try {
			roomStatusList = roomStatusService.getAllRoomStatus(hotelId);
		} catch (Exception x) {
			System.out.println("in webservice.......");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Country>>() {
		}.getType();
		String json = gson.toJson(roomStatusList, type);

		return json;
	}

	public RoomStatusService getRoomStatusService() {
		return roomStatusService;
	}

	public void setRoomStatusService(RoomStatusService roomStatusService) {
		this.roomStatusService = roomStatusService;
	}

}
