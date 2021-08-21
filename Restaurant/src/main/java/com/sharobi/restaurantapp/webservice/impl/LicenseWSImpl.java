package com.sharobi.restaurantapp.webservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sharobi.license.struts.key.KeyBean;
import com.sharobi.restaurantapp.service.LicenseService;
import com.sharobi.restaurantapp.webservice.LicenseWS;

@Controller
@ResponseBody
@RequestMapping(value = "/license")
public class LicenseWSImpl implements LicenseWS {

  @Autowired
	private LicenseService licenseService;

	@Override
	@RequestMapping(value = "/addStoreLicenseInformation",
	method = RequestMethod.POST,
	consumes = "application/json",
	produces = "text/plain")
	public String addStoreLicenseInformation(@RequestBody String licenseKey) {
		
		KeyBean keyBean=null;
		try {
			keyBean = licenseService.addStoreLicenseInformation(licenseKey);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(keyBean, KeyBean.class);
		System.out.println(json);
		return json;
		
	}
	
	@Override
	@RequestMapping(value = "/aboutProduct", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
	public String aboutProduct(@RequestParam(value = "storeId") String storeId) {
		KeyBean keyBean=null;
		try {
			keyBean = licenseService.aboutProduct(storeId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		String json = gson.toJson(keyBean, KeyBean.class);
		System.out.println(json);
		return json;
		
	}

	public LicenseService getLicenseService() {
		return licenseService;
	}

	public void setLicenseService(LicenseService licenseService) {
		this.licenseService = licenseService;
	}

}
