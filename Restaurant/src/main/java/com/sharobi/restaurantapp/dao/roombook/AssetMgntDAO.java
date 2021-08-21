/**
 * 
 */
package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.AssetCategory;
import com.sharobi.restaurantapp.model.roomBook.AssetItem;

/**
 * @author Habib
 *
 */
public interface AssetMgntDAO {
	
	public List<AssetCategory> getAssetCategory(String hotelId) throws DAOException;
	public List<AssetItem> getAssetItems(String hotelId) throws DAOException;
	public AssetItem getAssetItemById(String hotelId,String id) throws DAOException;
	public String addAssetItem(AssetItem assetItem) throws DAOException;
	public String updateAssetItem(AssetItem assetItem)throws DAOException;
	public String deleteAssetItem(String id, String storeId)throws DAOException;

}
