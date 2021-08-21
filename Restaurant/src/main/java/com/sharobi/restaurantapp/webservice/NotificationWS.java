package com.sharobi.restaurantapp.webservice;

import java.util.List;

public interface NotificationWS {

	List<String> getNotifications(long lastSyncTime);
}
