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
import com.sharobi.restaurantapp.model.roomBook.Country;
import com.sharobi.restaurantapp.service.roombook.CountryService;
import com.sharobi.restaurantapp.webservice.roombook.CountryWS;

@Controller
@ResponseBody
@RequestMapping(value = "/country")
public class CountryWSImpl implements CountryWS {

  @Autowired
	private CountryService countryService;

	@RequestMapping(value = "/addCountry", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addCountry(@RequestBody Country country) {
		Boolean result = false;
		
		try {
			result = countryService.addCountry(country);
		} catch (Exception x) {
			x.printStackTrace();
		}
		return result ? "success" : null;
	}

	@RequestMapping(value = "/updateCountry", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateCountry(@RequestBody Country country) {
		Boolean result = false;
		try {
			result = countryService.updateCountry(country);
		} catch (Exception x) {
			x.printStackTrace();
		}

    return result ? "success" : null;
	}

	@RequestMapping(value = "/deleteCountry", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String deleteCountry(@RequestBody Country country) {
		Boolean result = false;
		
		try {
			result = countryService.deleteCountry(country);
		} catch (Exception x) {
			x.printStackTrace();
		}

    return result ? "success" : null;
	}

	@RequestMapping(value = "/getCountryByName", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getCountryByName(@RequestParam(value = "name") String name) {
		Country countryObj = null;
		try {
			countryObj = countryService.getCountryByName(name);
		} catch (Exception x) {
			System.out.println("in webservice.......");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Country>>() {
		}.getType();
		String json = gson.toJson(countryObj, type);

		return json;
	}

	@RequestMapping(value = "/getAllCountry", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllCountry() {
		List<Country> countryList = null;
		try {
			countryList = countryService.getAllCountry();
		} catch (Exception x) {
			System.out.println("in webservice.......");
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Country>>() {
		}.getType();
		String json = gson.toJson(countryList, type);

		return json;
	}

	@RequestMapping(value = "/saveCountryIdinRestaurant", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String saveCountryIdinRestaurant(
			@RequestParam(value = "storeId") String storeId,
			@RequestParam(value = "countryId") String countryId) {
		String status = null;
		try {
			int store_id=Integer.parseInt(storeId);
			int country_id=Integer.parseInt(countryId);
			countryService.saveCountryIdinRestaurant(store_id, country_id);
			status = "success";
		} catch (Exception x) {
			x.printStackTrace();
		}
		return status;
	}

	public CountryService getCountryService() {
		return countryService;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

}
