package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.Amenities;
import com.sharobi.restaurantapp.model.util.PersistenceListener;

@Component("amenitiesDAO")
public class AmenitiesDAOImpl implements AmenitiesDAO {

	private EntityManagerFactory entityManagerFactory;
	private final static Logger logger = LogManager
			.getLogger(AmenitiesDAOImpl.class);

	@Override
	public Boolean addAmenities(Amenities amenities) throws DAOException {
		Boolean status = false;
		EntityManager em = null;
		Amenities amenities1 = new Amenities();
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			amenities1.setAmenities(amenities.getAmenities());
			amenities1.setHotelId(amenities.getHotelId());
			amenities1.setFlag(amenities.getFlag());
			em.persist(amenities);
			em.getTransaction().commit();
			logger.info("Amenities saved successfully...");
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return status;
	}

	@Override
	public Boolean updateAmenities(Amenities amenities) throws DAOException {
		Boolean status = false;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Amenities amenities1 = em.find(Amenities.class, amenities.getId());
			amenities1.setHotelId(amenities.getHotelId());
			amenities1.setAmenities(amenities.getAmenities());
			amenities1.setFlag(amenities.getFlag());
			em.merge(amenities1);
			em.getTransaction().commit();
			System.out.print("Amenities updated successfully....");
			status = true;
		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
		return status;
	}

	@Override
	public Boolean deleteAmenities(Amenities amenities) throws DAOException {
		Boolean status = false;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Amenities amenities1 = em.find(Amenities.class, amenities.getId());
			em.remove(amenities1);
			em.getTransaction().commit();
			System.out.print("amenities data deleted successfully....");
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}
		return status;
	}

	@Override
	public List<Amenities> getAllAmenitiesByHotelId(String hotelId)
			throws DAOException {

		List<Amenities> amenitiesList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM Amenities f WHERE f.hotelId=:Id order by f.id desc limit 20");
			qry.setParameter("Id", hotelId);
			amenitiesList = (List<Amenities>) qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return amenitiesList;
	}

	@Override
	public List<Amenities> getAllAmenitiesByHotelIdandName(String hotelId,
			String amenitiesName) throws DAOException {

		List<Amenities> amenitiesList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM Amenities f WHERE f.hotelId=:Id and f.amenities=:name order by f.id desc limit 20");
			qry.setParameter("Id", hotelId);
			qry.setParameter("name", amenitiesName);
			amenitiesList = (List<Amenities>) qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return amenitiesList;
	}
}
