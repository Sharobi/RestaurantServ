package com.sharobi.restaurantapp.webservice;

import com.sharobi.restaurantapp.model.Customer;
import com.sharobi.restaurantapp.model.User;

public interface LoginWS {

	String authenticateUser(Customer user);

	String getAllWaiters(String storeId);

	String getPosModulesByUserId(String userId, String storeId);
	
	String getReportByStore(String userId, String storeId);

	String loginAdminPOS(User user);

}
