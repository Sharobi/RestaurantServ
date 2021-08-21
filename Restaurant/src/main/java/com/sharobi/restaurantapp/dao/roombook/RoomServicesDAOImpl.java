package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.RoomServices;
import com.sharobi.restaurantapp.model.util.PersistenceListener;

@Component("roomServicesDAO")
public class RoomServicesDAOImpl implements RoomServicesDAO {

	private EntityManagerFactory entityManagerFactory;
	private final static Logger logger = LogManager
			.getLogger(RoomServicesDAOImpl.class);
	
	@Override
	public Boolean addRoomService(RoomServices roomService) throws DAOException {
		Boolean status = false;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(roomService);
			em.getTransaction().commit();
			logger.info("RoomServices saved successfully...");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return status;
	}
	
	@Override
	public void updateRoomService(RoomServices roomService) throws DAOException {

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			RoomServices roomServicesObj = em.find(RoomServices.class, roomService.getId());
			roomServicesObj.setHotelId(roomService.getHotelId());
			roomServicesObj.setName(roomService.getName());
			roomServicesObj.setDescription(roomService.getDescription());
			roomServicesObj.setRate(roomService.getRate());
			roomServicesObj.setIsTaxable(roomService.getIsTaxable());
			roomServicesObj.setTaxRate(roomService.getTaxRate());
			roomServicesObj.setIsServiceChargable(roomService.getIsServiceChargable());
			roomServicesObj.setServiceChargeRate(roomService.getServiceChargeRate());
			roomServicesObj.setUpdatedBy(roomService.getUpdatedBy());
			roomServicesObj.setUpdatedDate(roomService.getUpdatedDate());
			em.merge(roomServicesObj);
			em.getTransaction().commit();

		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
	}
	
	@Override
	public void deleteRoomService(RoomServices roomService) throws DAOException {

		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			RoomServices roomServicesObj = em.find(RoomServices.class, roomService.getId());
			roomServicesObj.setIsDeleted(1);
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}
	}
	
	@Override
	public RoomServices getRoomServiceById(int roomServiceId) throws DAOException {

		RoomServices roomServicesObj = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			TypedQuery<RoomServices> qry = em
					.createQuery("SELECT f FROM RoomServices f WHERE f.id=:id",RoomServices.class);
			qry.setParameter("id", roomServiceId);
			roomServicesObj = qry.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return roomServicesObj;
	}
	
	@Override
	public List<RoomServices> getAllRoomService(String hotelId) throws DAOException {

		List<RoomServices> roomServiceList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			TypedQuery<RoomServices> qry = em
					.createQuery("SELECT c FROM RoomServices c WHERE c.hotelId=:Id and c.isDeleted=0",RoomServices.class);
			qry.setParameter("Id", Integer.parseInt(hotelId));
			roomServiceList =  qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return roomServiceList;
	}
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
}
