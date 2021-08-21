package com.sharobi.restaurantapp.webservice.roombook.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBook;
import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBookInfo;
import com.sharobi.restaurantapp.service.roombook.TaxForRoomBookingService;
import com.sharobi.restaurantapp.webservice.roombook.TaxForRoomBookWS;

@Controller
@ResponseBody
@RequestMapping(value = "/taxForRoomBook")
public class TaxForRoomBookWSImpl implements TaxForRoomBookWS {

  @Autowired
	private TaxForRoomBookingService taxForRoomBookingService;

	//@Override
	@RequestMapping(value = "/addTaxForRoomBook", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addTaxForRoomBook(@RequestBody TaxForRoomBookInfo taxForRoomBook, HttpServletRequest request) {
		boolean result = false;
		
		try {
			result = taxForRoomBookingService.addTaxForRoomBook(taxForRoomBook,request);
		} catch (Exception x) {
			x.printStackTrace();
		}

    return result ? "success" : null;
	}

	@Override
	@RequestMapping(value = "/updateTaxForRoomBook", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateTaxForRoomBook(@RequestBody TaxForRoomBookInfo taxForRoomBook) {
		boolean result = false;
		
		try {
			result = taxForRoomBookingService.updateTaxForRoomBook(taxForRoomBook);
		} catch (Exception x) {
			x.printStackTrace();
		}

    return result ? "success" : null;
	}

	@Override
	@RequestMapping(value = "/deleteTaxForRoomBook", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String deleteTaxForRoomBook(@RequestBody TaxForRoomBookInfo taxForRoomBook) {
		boolean result = false;
		try {
			result=taxForRoomBookingService.deleteTaxForRoomBook(taxForRoomBook);
		} catch (Exception x) {
			x.printStackTrace();
		}

    return result ? "success" : null;
	}

	@Override
	@RequestMapping(value = "/getAllTaxesByHotelIdnCountryId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllTaxesByHotelIdandCountryId(
			@RequestParam(value = "hotelId") String hotelId,
			@RequestParam(value = "countryId") String countryId) {
		List<TaxForRoomBook> taxForRoomBookList = null;
		try {
			taxForRoomBookList=taxForRoomBookingService.getAllTaxesByHotelIdandCountryId(hotelId, countryId);
		} catch (Exception x) {
			System.out.println("in webservice.......");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<TaxForRoomBook>>() {
		}.getType();
		String json = gson.toJson(taxForRoomBookList, type);

		return json;
	}

	@Override
	@RequestMapping(value = "/getAllTaxesByHotelIdandName", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllTaxesByHotelIdandName(
			@RequestParam(value = "hotelId") String hotelId,
			@RequestParam(value = "name") String name) {
		List<TaxForRoomBook> taxForRoomBookList = null;
		try {
			taxForRoomBookList=taxForRoomBookingService.getAllTaxesByHotelIdandName(hotelId,name);
		} catch (Exception x) {
			System.out.println("in webservice.......");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<TaxForRoomBook>>() {
		}.getType();
		String json = gson.toJson(taxForRoomBookList, type);

		return json;
	}
	
	@Override
	@RequestMapping(value = "/getAllTaxesByHotelId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllTaxesByHotelId(
			@RequestParam(value = "hotelId") String hotelId) {
		List<TaxForRoomBook> taxForRoomBookList = null;
		try {
			taxForRoomBookList=taxForRoomBookingService.getAllTaxesByHotelId(hotelId);
		} catch (Exception x) {
			System.out.println("in webservice.......");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<TaxForRoomBook>>() {
		}.getType();
		String json = gson.toJson(taxForRoomBookList, type);

		return json;
	}
	
	public TaxForRoomBookingService getTaxForRoomBookingService() {
		return taxForRoomBookingService;
	}

	public void setTaxForRoomBookingService(
			TaxForRoomBookingService taxForRoomBookingService) {
		this.taxForRoomBookingService = taxForRoomBookingService;
	}

}
