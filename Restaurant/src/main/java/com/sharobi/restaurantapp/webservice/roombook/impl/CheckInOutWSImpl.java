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
import com.sharobi.restaurantapp.model.roomBook.CheckInOut;
import com.sharobi.restaurantapp.service.roombook.CheckInOutService;
import com.sharobi.restaurantapp.webservice.roombook.CheckInOutWS;

@Controller
@ResponseBody
@RequestMapping(value = "/checkInOut")
public class CheckInOutWSImpl implements CheckInOutWS {

  @Autowired
	private CheckInOutService checkInOutService;
	
	private final static Logger logger = LogManager.getLogger(CheckInOutWSImpl.class);

	@RequestMapping(value = "/addCheckInOut", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Boolean addCheckInOut(@RequestBody CheckInOut checkInOut) {
		Boolean result = false;
		try {
			checkInOutService.addCheckInOut(checkInOut);
			result = true;
			logger.info("CheckinOut Add Successfully");
		} catch (Exception x) {
			x.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/updateCheckInOut", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Boolean updateCheckInOut(@RequestBody CheckInOut checkInOut) {
		Boolean result = false;
		try {
			checkInOutService.updateCheckInOut(checkInOut);
			logger.info("CheckinOut UPDATE Successfully");
			result = true;
		} catch (Exception x) {
			x.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/deleteCheckInOut", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Boolean deleteCheckInOut(@RequestBody CheckInOut checkInOut) {
		Boolean result = false;
		try {
			checkInOutService.deleteCheckInOut(checkInOut);
			logger.info("CheckinOut DELETE Successfully");
			result = true;
		} catch (Exception x) {
			x.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/getAllCheckInOutByHotelId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllAmenitiesByHotelId(@RequestParam(value = "hotelId") String hotelId) {
		List<CheckInOut> checkInOutList = null;
		try {
			checkInOutList = checkInOutService
					.getAllCheckInOutByHotelId(hotelId);
			logger.info("Get All CheckinOut List");
		} catch (Exception x) {
			System.out.println("in webservice.......");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<CheckInOut>>() {
		}.getType();
		String json = gson.toJson(checkInOutList, type);

		return json;
	}

	public CheckInOutService getCheckInOutService() {
		return checkInOutService;
	}

	public void setCheckInOutService(CheckInOutService checkInOutService) {
		this.checkInOutService = checkInOutService;
	}

}
