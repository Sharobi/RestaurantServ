package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.Country;

public interface CountryDAO {

	void addCountry(Country country) throws DAOException;

	void deleteCountry(Country country) throws DAOException;

	Country getCountryById(int countryId) throws DAOException;

	Country getCountryByName(String countryName) throws DAOException;

	void updateCountry(Country country) throws DAOException;

	List<Country> getAllCountry() throws DAOException;

	String saveCountryIdinRestaurant(int storeId, int countryId)
			throws DAOException;

}
