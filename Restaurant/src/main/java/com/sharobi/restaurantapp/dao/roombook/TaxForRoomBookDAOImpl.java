package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.Country;
import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBook;
import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBookInfo;
import com.sharobi.restaurantapp.model.util.PersistenceListener;

@Component("taxForRoomBookDAO")
public class TaxForRoomBookDAOImpl implements TaxForRoomBookDAO {

	private EntityManagerFactory entityManagerFactory;
	private final static Logger logger = LogManager.getLogger(TaxForRoomBookDAOImpl.class);

	@Override
	public void addTaxForRoomBook(TaxForRoomBookInfo tax, HttpServletRequest request) throws DAOException {

		EntityManager em = null;
		EntityManager em2 = null;
		TaxForRoomBook taxForRoomBook=new TaxForRoomBook();
		//CountryDAO countryDAO=null;
		Country countryObj=null;
		try {
			int countryid=tax.getCountry();
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			
			em2 = entityManagerFactory.createEntityManager();
			em2.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT f FROM Country f WHERE f.id=:id");
			qry.setParameter("id", countryid);
			countryObj = (Country) qry.getSingleResult();
			//countryObj=countryDAO.getCountryById(countryid);
			em2.getTransaction().commit();
			
			taxForRoomBook.setCountryId(countryObj);
			taxForRoomBook.setEffective_Date(tax.getEffective_Date());
			taxForRoomBook.setHotelId(tax.getHotelId());
			taxForRoomBook.setIsActive(tax.getIsActive());
			taxForRoomBook.setPercentage(tax.getPercentage());
			taxForRoomBook.setName(tax.getName());
			
			em.getTransaction().begin();
			em.persist(taxForRoomBook);
			em.getTransaction().commit();
			logger.info("Tax saved successfully...");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
	}

	@Override
	public void updateTaxForRoomBook(TaxForRoomBookInfo tax) throws DAOException {
		
		EntityManager em2 = null;
		//TaxForRoomBook taxForRoomBook=new TaxForRoomBook();
		//CountryDAO countryDAO=null;
		Country countryObj=null;
		try {
			int countryid=tax.getCountry();
			entityManagerFactory = PersistenceListener.getEntityManager();
			EntityManager em = entityManagerFactory.createEntityManager();
			
			em2 = entityManagerFactory.createEntityManager();
			em2.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT f FROM Country f WHERE f.id=:id");
			qry.setParameter("id", countryid);
			countryObj = (Country) qry.getSingleResult();
			em2.getTransaction().commit();
			
			em.getTransaction().begin();
			TaxForRoomBook tax1 = em.find(TaxForRoomBook.class, tax.getId());
			tax1.setHotelId(tax.getHotelId());
			tax1.setPercentage(tax.getPercentage());
			tax1.setEffective_Date(tax.getEffective_Date());
			tax1.setCountryId(countryObj);
			tax1.setIsActive(tax.getIsActive());
			tax1.setName(tax.getName());
			em.merge(tax1);
			em.getTransaction().commit();
			System.out.print("Country updated successfully....");

		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
	}

	@Override
	public void deleteTaxForRoomBook(TaxForRoomBookInfo tax) throws DAOException {

		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			TaxForRoomBook tax1 = em.find(TaxForRoomBook.class, tax.getId());
			em.remove(tax1);
			em.getTransaction().commit();
			System.out.print("Tax data deleted successfully....");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be deleted", e);
		} finally {
			em.close();
		}
	}

	@Override
	public List<TaxForRoomBook> getAllTaxesByHotelIdandCountryId(String hotelId,String countryId) throws DAOException {

		List<TaxForRoomBook> taxList = null;
		EntityManager em = null;
		int countryID=0;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			countryID=Integer.parseInt(countryId);
			Query qry = em
					.createQuery("SELECT f FROM TaxForRoomBook f WHERE f.hotelId=:Id and f.country.id=:country ");
			qry.setParameter("Id", hotelId);
			qry.setParameter("country", countryID);
			taxList = (List<TaxForRoomBook>) qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return taxList;
	}
	
	@Override
	public List<TaxForRoomBook> getAllTaxesByHotelIdandName(String hotelId,String name2) throws DAOException {

		List<TaxForRoomBook> taxList = null;
		EntityManager em = null;
		int countryID=0;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM TaxForRoomBook f WHERE f.hotelId=:Id and f.name=:nametax");
			qry.setParameter("Id", hotelId);
			qry.setParameter("nametax", name2);
			taxList = (List<TaxForRoomBook>) qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return taxList;
	}
	
	@Override
	public List<TaxForRoomBook> getAllTaxesByHotelId(String hotelId) throws DAOException {

		List<TaxForRoomBook> taxList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM TaxForRoomBook f WHERE f.hotelId=:Id");
			qry.setParameter("Id", hotelId);
			taxList = (List<TaxForRoomBook>) qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return taxList;
	}
}
