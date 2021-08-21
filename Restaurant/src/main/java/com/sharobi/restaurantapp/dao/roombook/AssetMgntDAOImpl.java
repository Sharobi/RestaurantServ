/**
 * 
 */
package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.AssetCategory;
import com.sharobi.restaurantapp.model.roomBook.AssetItem;
import com.sharobi.restaurantapp.model.util.PersistenceListener;

/**
 * @author Habib
 *
 */
@Component("assetMgntDAO")
public class AssetMgntDAOImpl implements AssetMgntDAO{
	
	private EntityManagerFactory entityManagerFactory;
	private final static Logger logger = LogManager.getLogger(AssetMgntDAOImpl.class);
	
	@Override
	public List<AssetCategory> getAssetCategory(String hotelId) throws DAOException
	{ 
		List<AssetCategory> astcatList=null;
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			TypedQuery<AssetCategory>qry = em
					.createQuery("SELECT astcat FROM AssetCategory astcat WHERE astcat.isDelete=0", AssetCategory.class);
			astcatList = qry.getResultList();
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Cannot get getAssetCategory by store Id.", e);
		}
		finally {
			if(em != null) em.close();
		}
		return astcatList;
		
	}
	
	@Override
	public List<AssetItem> getAssetItems(String hotelId) throws DAOException
	{ 
		List<AssetItem> astitemList=null;
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			TypedQuery<AssetItem>qry = em
					.createQuery("SELECT astitem FROM AssetItem astitem WHERE astitem.hotelId=:hotelId and astitem.isDelete=0", AssetItem.class);
			qry.setParameter("hotelId", Integer.parseInt(hotelId));
			astitemList = qry.getResultList();
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Cannot get getAssetitems by store Id.", e);
		}
		finally {
			if(em != null) em.close();
		}
		return astitemList;
		
	}
	
	@Override
	public AssetItem getAssetItemById(String hotelId,String id) throws DAOException
	{ 
		AssetItem astitem=null;
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			TypedQuery<AssetItem>qry = em
					.createQuery("SELECT astitem FROM AssetItem astitem WHERE astitem.hotelId=:hotelId and astitem.id=:id and astitem.isDelete=0", AssetItem.class);
			qry.setParameter("hotelId", Integer.parseInt(hotelId));
			qry.setParameter("id", Integer.parseInt(id));
			astitem = qry.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Cannot get getAssetitem by Id.", e);
		}
		finally {
			if(em != null) em.close();
		}
		return astitem;
	}
	
	@Override
	public String addAssetItem(AssetItem assetItem) throws DAOException {
		EntityManager em = null;
		String status = "";
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(assetItem);
			em.getTransaction().commit();
			status = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return status;
	}
	
	@Override
	public String updateAssetItem(AssetItem assetItem)
			throws DAOException {
		EntityManager em = null;
		AssetItem ast=null;
		String messaString = "";
		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			ast=em.find(AssetItem.class, assetItem.getId());
			assetItem.setCreatedBy(ast.getCreatedBy());
			assetItem.setCreatedDate(ast.getCreatedDate());
			em.merge(assetItem);
			em.getTransaction().commit();
			messaString = "Success";

		} catch (Exception e) {
			e.printStackTrace();
			messaString = "Failure";
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}
		return messaString;
	}
	
	@Override
	public String deleteAssetItem(String id, String hotelId)
			throws DAOException {

		int itemId = Integer.parseInt(id);
		int hotelid = Integer.parseInt(hotelId);
		EntityManager em = null;
		AssetItem astItem = null;
		String message = "";

		try {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<AssetItem> qry = em
					.createQuery("SELECT astitm FROM AssetItem astitm WHERE astitm.hotelId=:hotelId and astitm.id=:id",
							AssetItem.class);
			qry.setParameter("hotelId", hotelid);
			qry.setParameter("id", itemId);
			astItem = qry.getSingleResult();
			astItem.setIsDelete(1);
			em.getTransaction().commit();
			message = "Success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "Failure";
			throw new DAOException("Check data to be deleted", e);
		} finally {
			if(em != null) em.close();
		}
		return message;

	}

}
