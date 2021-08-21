package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBook;
import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBookInfo;


public interface TaxForRoomBookDAO {

	//void addTaxForRoomBook(TaxForRoomBook tax) throws DAOException;

	//void updateTaxForRoomBook(TaxForRoomBook tax) throws DAOException;

	//void deleteTaxForRoomBook(TaxForRoomBook tax) throws DAOException;

	List<TaxForRoomBook> getAllTaxesByHotelIdandCountryId(String hotelId, String countryId)
			throws DAOException;

	void addTaxForRoomBook(TaxForRoomBookInfo tax, HttpServletRequest request)
			throws DAOException;

	void updateTaxForRoomBook(TaxForRoomBookInfo tax) throws DAOException;

	void deleteTaxForRoomBook(TaxForRoomBookInfo tax) throws DAOException;

	List<TaxForRoomBook> getAllTaxesByHotelIdandName(String hotelId, String name)
			throws DAOException;

	List<TaxForRoomBook> getAllTaxesByHotelId(String hotelId)
			throws DAOException;

	

}
