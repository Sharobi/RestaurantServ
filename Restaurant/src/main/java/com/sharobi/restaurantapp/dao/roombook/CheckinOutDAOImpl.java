package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.CheckInOut;
import com.sharobi.restaurantapp.model.util.PersistenceListener;

@Component("checkInOutDAO")
public class CheckinOutDAOImpl implements CheckInOutDAO {

	private EntityManagerFactory entityManagerFactory;
	private final static Logger logger = LogManager
			.getLogger(CheckinOutDAOImpl.class);
	
	@Override
	public Boolean addCheckInOut(CheckInOut checkInOut) throws DAOException {
		Boolean status=false;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(checkInOut);
			em.getTransaction().commit();
			logger.info("CheckInOut saved successfully...");
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return status;
	}
	
	@Override
	public Boolean updateCheckInOut(CheckInOut checkInOut) throws DAOException {
		Boolean status=false;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			CheckInOut checkInOut1 = em.find(CheckInOut.class, checkInOut.getId());
			checkInOut1.setHotelId(checkInOut.getHotelId());
			checkInOut1.setCheckIn(checkInOut.getCheckIn());
			checkInOut1.setCheckOut(checkInOut.getCheckOut());
			em.merge(checkInOut1);
			em.getTransaction().commit();
			System.out.print("checkInOut updated successfully....");
			status=true;
		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
		return status;
	}
	
	@Override
	public Boolean deleteCheckInOut(CheckInOut checkInOut) throws DAOException {
		Boolean status=false;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			CheckInOut checkInOut1 = em.find(CheckInOut.class, checkInOut.getId());
			em.remove(checkInOut1);
			em.getTransaction().commit();
			System.out.print("CheckInOut data deleted successfully....");
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}
		return status;
	}
	
	@Override
	public List<CheckInOut> getAllCheckInOutByHotelId(String hotelId)
			throws DAOException {

		List<CheckInOut> checkInOutList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM CheckInOut f WHERE f.hotelId=:Id order by f.id desc limit 20");
			qry.setParameter("Id", hotelId);
			checkInOutList = (List<CheckInOut>) qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return checkInOutList;
	}
}
