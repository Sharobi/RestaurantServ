package com.sharobi.restaurantapp.webservice.roombook;

import javax.servlet.http.HttpServletRequest;

import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBookInfo;

public interface TaxForRoomBookWS {

	//Boolean addTaxForRoomBook(TaxForRoomBook taxForRoomBook);

	//Boolean updateTaxForRoomBook(TaxForRoomBook taxForRoomBook);

	//Boolean deleteTaxForRoomBook(TaxForRoomBook taxForRoomBook);

	String getAllTaxesByHotelIdandCountryId(String hotelId, String countryId);

	String addTaxForRoomBook(TaxForRoomBookInfo taxForRoomBook,
			HttpServletRequest request);

	String updateTaxForRoomBook(TaxForRoomBookInfo taxForRoomBook);

	String deleteTaxForRoomBook(TaxForRoomBookInfo taxForRoomBook);

	String getAllTaxesByHotelIdandName(String hotelId, String name);

	String getAllTaxesByHotelId(String hotelId);

}
