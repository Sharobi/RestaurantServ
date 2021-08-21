package com.sharobi.restaurantapp.dao;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.MenuItemNote;

public interface SpecialNoteDao {
	
	public List<MenuItemNote> getSpecialNoteByFoodItem(String foodItemIds, String storeId)
			throws DAOException;
	
	
	public String addSpecialItem(String foodItemIds, String foodItemNoteId)
			throws DAOException ;

}
