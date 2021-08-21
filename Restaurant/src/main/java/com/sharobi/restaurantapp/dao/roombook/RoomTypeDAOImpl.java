package com.sharobi.restaurantapp.dao.roombook;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.RoomType;
import com.sharobi.restaurantapp.model.roomBook.RoomTypeInfo;
import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBook;
import com.sharobi.restaurantapp.model.util.PersistenceListener;

@Component("roomTypeDAO")
public class RoomTypeDAOImpl implements RoomTypeDAO {

	private EntityManagerFactory entityManagerFactory;
	private final static Logger logger = LogManager
			.getLogger(RoomTypeDAOImpl.class);

	@Override
	public Boolean addRoomType(RoomTypeInfo roomType) throws DAOException {

		Boolean status = false;
		EntityManager em = null;
		EntityManager em2 = null;
		TaxForRoomBook taxForRoomBook = null;
		RoomType roomTypeObj = new RoomType();
		try {
			//int taxId = roomType.getTaxId();
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			em2 = entityManagerFactory.createEntityManager();
			em2.getTransaction().begin();
			/*if(roomType.getTaxId()!=0) {
				Query qry = em
						.createQuery("SELECT f FROM TaxForRoomBook f WHERE f.id=:id");
				qry.setParameter("id", taxId);
				taxForRoomBook = (TaxForRoomBook) qry.getSingleResult();
				roomTypeObj.setTaxId(taxForRoomBook);
			}*/
		
			em2.getTransaction().commit();
			em.getTransaction().begin();
			//roomTypeObj.setAc(roomType.getAc());
			roomTypeObj.setHotelId(roomType.getHotelId());
			//roomTypeObj.setRoomDesc(roomType.getRoomDesc());
			//roomTypeObj.setRoomPrice(roomType.getRoomPrice());
			//roomTypeObj.setRoomSize(roomType.getRoomSize());
			//roomTypeObj.setRoomSizeUnit(roomType.getRoomSizeUnit());
			roomTypeObj.setRoomType(roomType.getRoomType());
			
			// roomTypeObj.setTaxPercentage(roomType.getTaxPercentage());

			em.persist(roomTypeObj);
			em.getTransaction().commit();
			logger.info("RoomType saved successfully...");
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
	public Boolean updateRoomType(RoomTypeInfo roomType) throws DAOException {

		Boolean status = false;
		try {
			EntityManager em = null;
			EntityManager em2 = null;
			TaxForRoomBook taxForRoomBook = null;
			RoomType roomTypeObj = new RoomType();
			//int taxId = roomType.getTaxId();
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			em2 = entityManagerFactory.createEntityManager();
			em2.getTransaction().begin();
			/*Query qry = em
					.createQuery("SELECT f FROM TaxForRoomBook f WHERE f.id=:id");
			qry.setParameter("id", taxId);
			taxForRoomBook = (TaxForRoomBook) qry.getSingleResult();*/
			em2.getTransaction().commit();

			em.getTransaction().begin();
			RoomType roomType1 = em.find(RoomType.class, roomType.getId());
			roomType1.setHotelId(roomType.getHotelId());
			roomType1.setRoomType(roomType.getRoomType());
			//roomType1.setAc(roomType.getAc());
			//roomType1.setRoomDesc(roomType.getRoomDesc());
			//roomType1.setRoomPrice(roomType.getRoomPrice());
			//roomType1.setRoomSize(roomType.getRoomSize());
			//roomType1.setRoomSizeUnit(roomType.getRoomSizeUnit());
			//roomType1.setTaxId(taxForRoomBook);
			// roomType1.setTaxPercentage(roomType.getTaxPercentage());
			em.merge(roomType1);
			em.getTransaction().commit();
			System.out.print("RoomType updated successfully....");
			status = true;
		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
		return status;
	}

	@Override
	public Boolean deleteRoomType(RoomTypeInfo roomType) throws DAOException {

		Boolean status = false;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			RoomType roomType1 = em.find(RoomType.class, roomType.getId());
			em.remove(roomType1);
			em.getTransaction().commit();
			System.out.print("Tax data deleted successfully....");
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
	public List<RoomType> getAllRoomTypeByHotelId(String hotelId)
			throws DAOException {

		List<RoomType> roomTypeList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM RoomType f WHERE f.hotelId=:Id order by f.id desc");
			qry.setParameter("Id", hotelId);
			roomTypeList = (List<RoomType>) qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return roomTypeList;
	}

	@Override
	public List<RoomType> getAllRoomTypeByHotelIdandRoomType(String hotelId,
			String type) throws DAOException {

		List<RoomType> roomTypeList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM RoomType f WHERE f.hotelId=:Id and f.roomType=rType order by f.id desc limit 20");
			qry.setParameter("Id", hotelId);
			qry.setParameter("rType", type);
			roomTypeList = (List<RoomType>) qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return roomTypeList;
	}
}
