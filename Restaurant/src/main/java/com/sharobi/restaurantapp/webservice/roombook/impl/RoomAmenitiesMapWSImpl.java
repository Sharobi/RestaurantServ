package com.sharobi.restaurantapp.webservice.roombook.impl;

import java.util.List;

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
import com.sharobi.restaurantapp.model.roomBook.RoomAmenitiesMap;
import com.sharobi.restaurantapp.model.roomBook.RoomAmenitiesMapInfo;
import com.sharobi.restaurantapp.service.roombook.RoomAmenitiesMapService;
import com.sharobi.restaurantapp.webservice.roombook.RoomAmenitiesMapWS;

@Controller
@ResponseBody
@RequestMapping(value = "/roomAmenitiesMap")
public class RoomAmenitiesMapWSImpl implements RoomAmenitiesMapWS {

  @Autowired
	private RoomAmenitiesMapService roomAmenitiesMapService;
  
	private static Gson gson = new Gson();
	
	@Override
	@RequestMapping(value = "/assignAmenitiesToRoomType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String assignAmenitiesToRoomType(@RequestBody String roomAmenitiesMapInfo) {
		String status = null;
		try {
			RoomAmenitiesMapInfo serviceChargesFortAllItemsJson[] = gson
					.fromJson(roomAmenitiesMapInfo,
							RoomAmenitiesMapInfo[].class);
			for (RoomAmenitiesMapInfo roomAmenitiesMapInfoObj : serviceChargesFortAllItemsJson) {
			
				roomAmenitiesMapService
				.assignAmenitiesToRoomType(roomAmenitiesMapInfoObj);
			}
			
			status = "success";
		} catch (Exception x) {
			x.printStackTrace();
		}
		return status;
	}

	@Override
	@RequestMapping(value = "/updateAmenitiesToRoomType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateAmenitiesToRoomType(@RequestBody String roomAmenitiesMapInfo) {
		String status = null;
		try {
			RoomAmenitiesMapInfo serviceChargesFortAllItemsJson[] = gson
					.fromJson(roomAmenitiesMapInfo,
							RoomAmenitiesMapInfo[].class);
			for (RoomAmenitiesMapInfo roomAmenitiesMapInfoObj : serviceChargesFortAllItemsJson) {
			
				roomAmenitiesMapService
				.updateAmenitiesToRoomType(roomAmenitiesMapInfoObj);
			}
			status = "success";
		} catch (Exception x) {
			x.printStackTrace();
		}
		return status;
	}

	@RequestMapping(value = "/getAllAmenitiesToRoomTypeById", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<RoomAmenitiesMap> getAllAmenitiesToRoomTypeById(
			@RequestParam(value = "hotelId") String hotelId,
			@RequestParam(value = "roomTypeId") int roomTypeId) {
		List<RoomAmenitiesMap> amenitiesList = null;
		try {
			amenitiesList = roomAmenitiesMapService
					.getAllAmenitiesToRoomTypeById(hotelId, roomTypeId);
		} catch (Exception x) {
			System.out.println("in webservice.......");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomAmenitiesMap>>() {
		}.getType();
		String json = gson.toJson(amenitiesList, type);

		return amenitiesList;
	}

	public RoomAmenitiesMapService getRoomAmenitiesMapService() {
		return roomAmenitiesMapService;
	}

	public void setRoomAmenitiesMapService(
			RoomAmenitiesMapService roomAmenitiesMapService) {
		this.roomAmenitiesMapService = roomAmenitiesMapService;
	}
}
