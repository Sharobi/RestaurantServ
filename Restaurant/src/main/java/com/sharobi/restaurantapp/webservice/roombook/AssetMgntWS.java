/**
 * 
 */
package com.sharobi.restaurantapp.webservice.roombook;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.AssetItem;

/**
 * @author Habib
 *
 */
public interface AssetMgntWS {
	
	public String getAssetCategory(String hotelId) throws DAOException;
	public String getAssetItems(String hotelId) throws DAOException;
	public String getAssetItemById(String hotelId,String id) throws DAOException;
	public String addAssetItem(AssetItem astItem) throws DAOException;
	public String updateAssetItem(AssetItem astItem) throws DAOException;
	public String deleteAssetItem(String id,String hotelId) throws DAOException;

}
