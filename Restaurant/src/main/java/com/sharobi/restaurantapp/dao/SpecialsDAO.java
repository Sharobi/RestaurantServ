package com.sharobi.restaurantapp.dao;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.DaySpecial;

public interface SpecialsDAO {

	public List<DaySpecial> getSpecialItem() throws DAOException;

	public DaySpecial getSpecialItemAsObj() throws DAOException;

	public void addSpeciaItem(List<DaySpecial> specialItemlist)
			throws DAOException;

	public void deleteSpecialItem(List<DaySpecial> specialItemlist)
			throws DAOException;

	public List<DaySpecial> getSpecialItemByStoreId(String storeId, String language)
			throws DAOException;

}
