package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.StoreMaster;
import com.sharobi.restaurantapp.model.roomBook.Country;
import com.sharobi.restaurantapp.model.util.PersistenceListener;

@Component("countryDAO")
public class CountryDAOImpl implements CountryDAO {

	private EntityManagerFactory entityManagerFactory;
	private final static Logger logger = LogManager
			.getLogger(CountryDAOImpl.class);

	@Override
	public void addCountry(Country country) throws DAOException {

		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(country);
			em.getTransaction().commit();
			logger.info("Country saved successfully...");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void updateCountry(Country country) throws DAOException {

		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Country country1 = em.find(Country.class, country.getId());
			country1.setCountryname(country.getCountryname());
			country1.setCourency(country.getCourency());
			em.merge(country1);
			em.getTransaction().commit();
			System.out.print("Country updated successfully....");

		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
	}

	@Override
	public void deleteCountry(Country country) throws DAOException {

		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Country country1 = em.find(Country.class, country.getId());
			em.remove(country1);
			em.getTransaction().commit();
			System.out.print("Country data deleted successfully....");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}
	}

	@Override
	public Country getCountryById(int countryId) throws DAOException {

		Country countryObj = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM Country f WHERE f.id=:id");
			qry.setParameter("id", countryId);
			countryObj = (Country) qry.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return countryObj;
	}

	@Override
	public Country getCountryByName(String countryName) throws DAOException {

		Country countryObj = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM Country f WHERE f.countryname=:name");
			qry.setParameter("name", countryName);
			countryObj = (Country) qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return countryObj;
	}
	
	@Override
	public List<Country> getAllCountry() throws DAOException {

		List<Country> countryList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT c FROM Country c ");
			countryList =  qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return countryList;
	}
	
	@Override
	public String saveCountryIdinRestaurant(int storeId, int countryId) throws DAOException {

		StoreMaster storeMaster = null;
		EntityManager em = null;
		EntityManager em2 = null;
		String status=null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM StoreMaster f WHERE f.id=:id");
			qry.setParameter("id", storeId);
			storeMaster = (StoreMaster) qry.getSingleResult();
			
			em2 = entityManagerFactory.createEntityManager();
			em2.getTransaction().begin();
			storeMaster.setCountryId(countryId);
			em2.merge(storeMaster);
			em2.getTransaction().commit();
			logger.info("Country saved in Restaurant Successfully...");
			status="success";
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return status;
	}
}
