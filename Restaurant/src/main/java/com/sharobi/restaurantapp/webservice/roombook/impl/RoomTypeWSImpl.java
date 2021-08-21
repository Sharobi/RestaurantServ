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
import com.sharobi.restaurantapp.model.roomBook.RoomType;
import com.sharobi.restaurantapp.model.roomBook.RoomTypeInfo;
import com.sharobi.restaurantapp.service.roombook.RoomTypeService;
import com.sharobi.restaurantapp.webservice.roombook.RoomTypeWS;

@Controller
@ResponseBody
@RequestMapping(value = "/roomType")
public class RoomTypeWSImpl implements RoomTypeWS {

  @Autowired
	private RoomTypeService roomTypeService;

	@Override
	@RequestMapping(value = "/addRoomType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addRoomType(@RequestBody RoomTypeInfo roomType) {
		
		String status="";
		try {
			roomTypeService.addRoomType(roomType);
			status="success";
		} catch (Exception x) {
			x.printStackTrace();
		}
		return status;
	}

	@Override
	@RequestMapping(value = "/updateRoomType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateRoomType(@RequestBody RoomTypeInfo roomType) {
		Boolean result = false;
		
		try {
			result=roomTypeService.updateRoomType(roomType);
			
		} catch (Exception x) {
			x.printStackTrace();
		}
    return result ? "success" : null;
	}

	@Override
	@RequestMapping(value = "/deleteRoomType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String deleteRoomType(@RequestBody RoomTypeInfo roomType) {
		
		boolean result = false;
		try {
			result = roomTypeService.deleteRoomType(roomType);
		} catch (Exception x) {
			x.printStackTrace();
		}

    return result ? "success" : null;
	}

	@Override
	@RequestMapping(value = "/getAllRoomTypeByHotelId", method = RequestMethod.GET, produces = "application/json")
	public String getAllRoomTypeByHotelId(
			@RequestParam(value = "hotelId") String hotelId) {
		List<RoomType> roomTypeList = null;
		try {
			roomTypeList = roomTypeService.getAllRoomTypeByHotelId(hotelId);
		} catch (Exception x) {
			System.out.println("in webservice.......");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type1 = new TypeToken<List<RoomType>>() {
		}.getType();
		String json = gson.toJson(roomTypeList, type1);

		return json;
	}

	@Override
	@RequestMapping(value = "/getAllRoomTypeByHotelIdandRoomType", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllRoomTypeByHotelIdandRoomType(
			@RequestParam(value = "hotelId") String hotelId,
			@RequestParam(value = "type") String type) {
		List<RoomType> roomTypeList = null;
		try {
			roomTypeList = roomTypeService.getAllRoomTypeByHotelIdandRoomType(
					hotelId, type);
		} catch (Exception x) {
			System.out.println("in webservice.......");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type1 = new TypeToken<List<RoomType>>() {
		}.getType();
		String json = gson.toJson(roomTypeList, type1);

		return json;
	}

	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}

	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}

}
