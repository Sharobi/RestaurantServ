package com.sharobi.restaurantapp.dao.roombook;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.roomBook.Room;
import com.sharobi.restaurantapp.model.roomBook.RoomInfo;
import com.sharobi.restaurantapp.model.roomBook.RoomType;
import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBook;
import com.sharobi.restaurantapp.model.util.PersistenceListener;

@Component("roomDAO")
public class RoomDAOImpl implements RoomDAO {

	private EntityManagerFactory entityManagerFactory;
	private final static Logger logger = LogManager
			.getLogger(RoomDAOImpl.class);

	@Override
	public Boolean addRoom(RoomInfo room) throws DAOException {
		Boolean status = false;
		EntityManager em = null;
		EntityManager em2 = null;
		Room roomObj=new Room();
		RoomType roomTypeObj=new RoomType();
		TaxForRoomBook taxForRoomBook =new TaxForRoomBook();
		try {
			int roomTypeId=room.getRoomTypeId();
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			//==================================================
			em2 = entityManagerFactory.createEntityManager();
			em2.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT f FROM RoomType f WHERE f.id=:id");
			qry.setParameter("id", roomTypeId);
			roomTypeObj=(RoomType) qry.getSingleResult();
			//==================================================
			int roomTaxId=room.getRoomTax();				//fetching tax object wrt tax_id
			Query q = em2
					.createQuery("SELECT t FROM TaxForRoomBook t WHERE t.id=:id");
			q.setParameter("id", roomTaxId);
			taxForRoomBook=(TaxForRoomBook) q.getSingleResult();
			
			roomObj.setHotelId(room.getHotelId());
			roomObj.setFloor(room.getFloor());
			roomObj.setRoomNo(room.getRoomNo());
			roomObj.setRoomTypeId(roomTypeObj);
			roomObj.setRoomCategory(room.getRoomCategory());
			roomObj.setRoomCapacity(room.getRoomCapacity());
		//	roomObj.setRoomType(room.getRoomType());
			roomObj.setRoomPrice(room.getRoomPrice());
			roomObj.setRoomName(room.getRoomName());
			roomObj.setRoomDesc(room.getRoomDesc());
			roomObj.setRoomSize(room.getRoomSize());
			roomObj.setRoomSizeUnit(room.getRoomSizeUnit());
			roomObj.setStatus(room.getStatus());
			roomObj.setTaxId(taxForRoomBook);				// Tax type object added for room
			em.getTransaction().begin();
			
			em.persist(roomObj);
			em.getTransaction().commit();
			logger.info("Room saved successfully...");
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em2.close();
			em.close();
		}
		return status;
	}

	@Override
	public Boolean updateRoom(RoomInfo room) throws DAOException {
		Boolean status = false;
		RoomType roomTypeObj=new RoomType();
		TaxForRoomBook taxForRoomBook =new TaxForRoomBook();
		try {
			int roomTypeId=room.getRoomTypeId();
			entityManagerFactory = PersistenceListener.getEntityManager();
			EntityManager em = entityManagerFactory.createEntityManager();
			//==============================================
			Query qry = em
					.createQuery("SELECT f FROM RoomType f WHERE f.id=:id");
			qry.setParameter("id", roomTypeId);
			roomTypeObj=(RoomType) qry.getSingleResult();
			//==============================================
			EntityManager em2 = null;
			em2 = entityManagerFactory.createEntityManager();
			em2.getTransaction().begin();
			int roomTaxId=room.getRoomTax();				//fetching tax object wrt tax_id
			Query q = em2
					.createQuery("SELECT t FROM TaxForRoomBook t WHERE t.id=:id");
			q.setParameter("id", roomTaxId);
			taxForRoomBook=(TaxForRoomBook) q.getSingleResult();
			
			
			em.getTransaction().begin();
			Room room1 = em.find(Room.class, room.getId());
			room1.setHotelId(room.getHotelId());
			room1.setFloor(room.getFloor());
			room1.setRoomNo(room.getRoomNo());
			room1.setRoomTypeId(roomTypeObj);
			room1.setRoomCategory(room.getRoomCategory());
			room1.setRoomCapacity(room.getRoomCapacity());
		//	room1.setRoomType(room.getRoomType());
			room1.setRoomPrice(room.getRoomPrice());
			room1.setRoomName(room.getRoomName());
			room1.setRoomDesc(room.getRoomDesc());
			room1.setRoomSize(room.getRoomSize());
			room1.setRoomSizeUnit(room.getRoomSizeUnit());
			room1.setStatus(room.getStatus());
			room1.setTaxId(taxForRoomBook);	
		
			em.merge(room1);
			em.getTransaction().commit();
			System.out.print("Room updated successfully....");
			status = true;
		} catch (Exception e) {
			throw new DAOException("Check data to be inserted", e);
		}
		return status;
	}

	@Override
	public Boolean deleteRoom(RoomInfo room) throws DAOException {
		Boolean status = false;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Room room1 = em.find(Room.class, room.getId());
			em.remove(room1);
			em.getTransaction().commit();
			System.out.print("Room data deleted successfully....");
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
	public List<Room> getAllRoomByHotelId(String hotelId) throws DAOException {

		List<Room> roomList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM Room f WHERE f.hotelId=:Id order by f.id desc");
			qry.setParameter("Id", hotelId);
			roomList = (List<Room>) qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return roomList;
	}
	
	@Override
	public Room getRoomById(String hotelId,String roomId) throws DAOException {

		Room room = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM Room f WHERE f.hotelId=:Id and f.id=:roomId order by f.id desc");
			qry.setParameter("Id", hotelId);
			qry.setParameter("roomId", Integer.parseInt(roomId));
			room = (Room) qry.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return room;
	}
// Testing..
	@Override
	public List<List<Room>> getAllRoomByFloor(String hotelId) throws DAOException {
		List<Room> roomList = null;
		List<List<Room>> finalList = new ArrayList<List<Room>>();
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			Query qry = em
					.createQuery("SELECT f FROM Room f WHERE f.hotelId=:Id order by f.id desc limit 20");
			qry.setParameter("Id", hotelId);
			roomList = (List<Room>) qry.getResultList();
			List<Room> grFloor = new ArrayList<Room>();
			List<Room> frstFloor = new ArrayList<Room>();
			List<Room> secndFloor = new ArrayList<Room>();
			List<Room> thirdFloor = new ArrayList<Room>();
			List<Room> fourthFloor = new ArrayList<Room>();
			List<Room> fifthFloor = new ArrayList<Room>();
			
		//	List<Room> finalList = new ArrayList<Room>();
			for(Room room : roomList) {
				  System.out.println(room.getFloor());
				  if(room.getFloor()==0)
				  {
					  grFloor.add(room);
					  finalList.add(grFloor);
				  }
				  else if(room.getFloor()==1)
				  {
					  frstFloor.add(room);
					  finalList.add(frstFloor);
				  }
				  else if(room.getFloor()==2)
				  {
					  secndFloor.add(room);
					  finalList.add(secndFloor);
				  }
				  else if(room.getFloor()==3)
				  {
					  thirdFloor.add(room);
					  finalList.add(thirdFloor);
				  }
				  else if(room.getFloor()==4)
				  {
					  fourthFloor.add(room);
					  finalList.add(fourthFloor);
				  }
				  else if(room.getFloor()==5)
				  {
					  fifthFloor.add(room);
					  finalList.add(fifthFloor);
				  }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			em.close();
		}
		return finalList;
	}
}
