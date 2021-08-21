package com.sharobi.restaurantapp.webservice;

import com.sharobi.restaurantapp.model.Feedback;
import com.sharobi.restaurantapp.model.StatusMessage;

public interface FeedbackWS {

	String getFeedback();

	String getFeedbackByStore(String storeid);

	StatusMessage createFeedback(Feedback feedback);
}
