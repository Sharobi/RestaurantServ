package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.RoomStatus;
import com.sharobi.restaurantapp.model.util.PersistenceListener;

@Component("roomStatusDAO")
public class RoomStatusDAOImpl implements RoomStatusDAO {

	private EntityManagerFactory entityManagerFactory;
	private final static Logger logger = LogManager
			.getLogger(RoomStatusDAOImpl.class);
	@Override
	public Boolean addRoomStatus(RoomStatus roomStatus) throws DAOException {
		Boolean status = false;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(roomStatus);
			em.getTransaction().commit();
			logger.info("RoomStatus saved successfully...");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return status;
	}

	@Override
	public void updateRoomService(RoomStatus roomStatus) throws DAOException {

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			RoomStatus roomStatusObj = em.find(RoomStatus.class,
					roomStatus.getId());
			roomStatusObj.setHotelId(roomStatus.getHotelId());
			roomStatusObj.setRoomStatus(roomStatus.getRoomStatus());
			em.merge(roomStatusObj);
			em.getTransaction().commit();
			System.out.print("RoomStatus updated successfully....");

		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
	}

	@Override
	public void deleteRoomStatus(RoomStatus roomStatus) throws DAOException {

		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			RoomStatus roomStatusObj = em.find(RoomStatus.class,
					roomStatus.getId());
			em.remove(roomStatusObj);
			em.getTransaction().commit();
			System.out.print("RoomStatus data deleted successfully....");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}
	}

	@Override
	public List<RoomStatus> getAllRoomStatus(String hotelId)
			throws DAOException {

		List<RoomStatus> roomStatusList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT c FROM RoomStatus c WHERE c.hotelId=:Id");
			qry.setParameter("Id", hotelId);
			roomStatusList = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return roomStatusList;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

}
