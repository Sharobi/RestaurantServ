package com.sharobi.restaurantapp.webservice;

public interface DaysSpecialWS {

	String getSpecialItems();

	String getSpecialItemsByStoreId(String storeid,
									String language);
}
