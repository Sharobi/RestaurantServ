/**
 * 
 */
package com.sharobi.restaurantapp.webservice.roombook.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sharobi.restaurantapp.dao.exception.DAOException;

import com.sharobi.restaurantapp.model.roomBook.AssetCategory;
import com.sharobi.restaurantapp.model.roomBook.AssetItem;
import com.sharobi.restaurantapp.service.roombook.AssetMgntService;
import com.sharobi.restaurantapp.webservice.roombook.AssetMgntWS;

/**
 * @author Habib
 *
 */
@Controller
@ResponseBody
@RequestMapping(value = "/asset")
public class AssetMgntWSImpl implements AssetMgntWS{
	private final static Logger logger = LogManager.getLogger(AssetMgntWSImpl.class);
	private AssetMgntService assetMgntService;
	
	
	@Override
	@RequestMapping(value="/getAssetCategory", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAssetCategory(@RequestParam(value="hotelId") String hotelId) throws DAOException {
		List<AssetCategory> astcatList=null;
		try {
			astcatList=assetMgntService.getAssetCategory(hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<AssetCategory>>() {
		}.getType();
		String json = gson.toJson(astcatList, type);
		return json;
	}
	
	@Override
	@RequestMapping(value="/getAssetItems", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAssetItems(@RequestParam(value="hotelId") String hotelId) throws DAOException {
		List<AssetItem> astitemList=null;
		try {
			astitemList=assetMgntService.getAssetItems(hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<AssetItem>>() {
		}.getType();
		String json = gson.toJson(astitemList, type);
		return json;
	}
	
	@Override
	@RequestMapping(value="/getAssetItemById", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAssetItemById(@RequestParam(value="hotelId") String hotelId,@RequestParam(value="id") String id) throws DAOException {
		AssetItem astitem=null;
		try {
			astitem=assetMgntService.getAssetItemById(hotelId,id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<AssetItem>() {
		}.getType();
		String json = gson.toJson(astitem, type);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/addAssetItem", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addAssetItem(@RequestBody AssetItem assetItem) {
		String status = "";
		try {
			status = assetMgntService.addAssetItem(assetItem);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/updateAssetItem", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updateAssetItem(@RequestBody AssetItem assetItem) {
		String status = "";
		try {
			status = assetMgntService.updateAssetItem(assetItem);
		} catch (Exception x) {
			status = "Failure";
			x.printStackTrace();
		}

		return status;
	}

	@Override
	@RequestMapping(value = "/deleteAssetItem", method = RequestMethod.GET, produces = "text/plain")
	public String deleteAssetItem(@RequestParam(value = "id") String id,
			@RequestParam(value = "hotelId") String hotelId) {
		String message = "";
		try {
			message = assetMgntService.deleteAssetItem(id, hotelId);
		} catch (Exception x) {
			message = "Failure";
			x.printStackTrace();
		}

		return message;
	}
	
	public AssetMgntService getAssetMgntService() {
		return assetMgntService;
	}
	public void setAssetMgntService(AssetMgntService assetMgntService) {
		this.assetMgntService = assetMgntService;
	}
	
	

}
