/**
 * 
 */
package com.sharobi.restaurantapp.service.roombook;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.dao.roombook.AssetMgntDAO;
import com.sharobi.restaurantapp.model.roomBook.AssetCategory;
import com.sharobi.restaurantapp.model.roomBook.AssetItem;
import com.sharobi.restaurantapp.service.exception.ServiceException;

/**
 * @author Habib
 *
 */
@Service
public class AssetMgntService {
	private final static Logger logger = LogManager .getLogger(AssetMgntService.class);
	
	@Autowired
	private AssetMgntDAO assetMgntDAO;
	
	public List<AssetCategory> getAssetCategory(String hotelId) throws DAOException{
		List<AssetCategory> astcatList=null;
		try {
			astcatList=assetMgntDAO.getAssetCategory(hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return astcatList;
	}
	
	public List<AssetItem> getAssetItems(String hotelId) throws DAOException{
		List<AssetItem> astitemList=null;
		try {
			astitemList=assetMgntDAO.getAssetItems(hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return astitemList;
	}
	
	public AssetItem getAssetItemById(String hotelId,String id) throws DAOException{
		AssetItem astitem=null;
		try {
			astitem=assetMgntDAO.getAssetItemById(hotelId,id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return astitem;
	}
	
	public String addAssetItem(AssetItem assetItem)
			throws ServiceException {
		String status = "";
		try {
			status = assetMgntDAO.addAssetItem(assetItem);

		} catch (DAOException e) {
			throw new ServiceException("error creating assetitem", e);

		}
		return status;
	}
	
	public String updateAssetItem(AssetItem assetItem)
			throws ServiceException {
		String status = "";
		try {
			status = assetMgntDAO.updateAssetItem(assetItem);

		} catch (DAOException e) {
			throw new ServiceException("error updating assetitem", e);

		}
		return status;
	}
	
	public String deleteAssetItem(String id, String hotelId)
			throws ServiceException {
		String message = "";
		try {

			message = assetMgntDAO.deleteAssetItem(id, hotelId);

		} catch (DAOException e) {
			throw new ServiceException("assetitem delete error", e);

		}
		return message;
	}

	public AssetMgntDAO getAssetMgntDAO() {
		return assetMgntDAO;
	}

	public void setAssetMgntDAO(AssetMgntDAO assetMgntDAO) {
		this.assetMgntDAO = assetMgntDAO;
	}
	
	

}
