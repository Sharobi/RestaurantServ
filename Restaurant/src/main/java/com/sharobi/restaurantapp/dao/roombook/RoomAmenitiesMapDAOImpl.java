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
import com.sharobi.restaurantapp.model.roomBook.RoomAmenitiesMap;
import com.sharobi.restaurantapp.model.roomBook.RoomAmenitiesMapInfo;
import com.sharobi.restaurantapp.model.util.PersistenceListener;

@Component("roomAmenitiesMapDAO")
public class RoomAmenitiesMapDAOImpl implements RoomAmenitiesMapDAO {

	private EntityManagerFactory entityManagerFactory;
	private final static Logger logger = LogManager
			.getLogger(RoomAmenitiesMapDAOImpl.class);

	@Override
	public String assignAmenitiesToRoomType(
			RoomAmenitiesMapInfo roomAmenitiesMapInfoObj) throws DAOException {
		logger.info("DAO Call for assignAmenitiesToRoomType");
		EntityManager em = null;
		String status = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			RoomAmenitiesMap roomAmenitiesMap = new RoomAmenitiesMap();
			// List<RoomAmenitiesMapInfo>
			// roomAmenitiesMapInfo=roomAmenitiesMapInfoList.getRoomAmenitiesMapInfo();
			/*
			 * for(int i=0; i<=roomAmenitiesMapInfo.getAmenities_id().length;
			 * i++){ RoomAmenitiesMap roomAmenitiesMap=new RoomAmenitiesMap();
			 * roomAmenitiesMap.setHotelId(roomAmenitiesMapInfo.getHotel_id());
			 * roomAmenitiesMap
			 * .setRoomTypeId(roomAmenitiesMapInfo.getRoomType_id());
			 * roomAmenitiesMap
			 * .setAmenitiesId(Integer.parseInt(roomAmenitiesMapInfo
			 * .getAmenities_id()[i]));
			 * roomAmenitiesMap.setIsEnable(Integer.parseInt
			 * (roomAmenitiesMapInfo.getIsEnable()[i]));
			 * em.persist(roomAmenitiesMap); em.getTransaction().commit();
			 * logger.info("Room_Amenities_Map saved successfully");
			 * status="success"; }
			 */
			/*
			 * for(int i=0;i<roomAmenitiesMapInfo.size();i++){
			 * 
			 * roomAmenitiesMap.setHotelId(roomAmenitiesMapInfo.get(i).getHotel_id
			 * ()); roomAmenitiesMap.setRoomTypeId(roomAmenitiesMapInfo.get(i).
			 * getRoomType_id());
			 * roomAmenitiesMap.setAmenitiesId(roomAmenitiesMapInfo
			 * .get(i).getAmenities_id());
			 * roomAmenitiesMap.setIsEnable(roomAmenitiesMapInfo
			 * .get(i).getIsEnable()); em.persist(roomAmenitiesMap);
			 * logger.info("Room_Amenities_Map saved successfully");
			 * status="success"; }
			 */
			roomAmenitiesMap.setHotelId(roomAmenitiesMapInfoObj.getHotel_id());
			roomAmenitiesMap.setRoomTypeId(roomAmenitiesMapInfoObj
					.getRoomType_id());
			roomAmenitiesMap.setAmenitiesId(roomAmenitiesMapInfoObj
					.getAmenities_id());
			roomAmenitiesMap.setIsEnable(roomAmenitiesMapInfoObj.getIsEnable());
			em.persist(roomAmenitiesMap);
			em.getTransaction().commit();
			logger.info("Room_Amenities_Map saved successfully");
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		}
		return status;
	}

	@Override
	public String updateAmenitiesToRoomType(
			RoomAmenitiesMapInfo roomAmenitiesMapInfoObj) throws DAOException {
		String status = null;
		EntityManager em = null;
		EntityManager em2 = null;
		try {
			String hotel_id=roomAmenitiesMapInfoObj.getHotel_id();
			int roomType_Id=roomAmenitiesMapInfoObj.getRoomType_id();
			//int ameities_id=roomAmenitiesMapInfoObj.getAmenities_id();
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			RoomAmenitiesMap roomAmenitiesMap = new RoomAmenitiesMap();
			List<RoomAmenitiesMap> roomAmenitiesMapList=new ArrayList<>();
			/*
			 * for(int i=0; i<=roomAmenitiesMapInfo.getAmenities_id().length;
			 * i++){ String hotelId=roomAmenitiesMapInfo.getHotel_id(); int
			 * roomTypeId=roomAmenitiesMapInfo.getRoomType_id(); int
			 * amenitiesId=
			 * (Integer.parseInt(roomAmenitiesMapInfo.getAmenities_id()[i]));
			 * int
			 * IsEnable=(Integer.parseInt(roomAmenitiesMapInfo.getIsEnable()[
			 * i])); Query qry = em .createQuery(
			 * "UPDATE RoomAmenitiesMap f SET f.isEnable=:isenable WHERE f.hotelId=:hotelid and f.roomTypeId=:roomtypeid and f.amenitiesId=:amenitiesid"
			 * ); qry.setParameter("isenable", IsEnable);
			 * qry.setParameter("hotelid", hotelId);
			 * qry.setParameter("roomtypeid", roomTypeId);
			 * qry.setParameter("amenitiesid", amenitiesId);
			 * qry.executeUpdate(); em.getTransaction().commit();
			 * logger.info("Room_Amenities_Map UPDATED successfully");
			 * status="success"; }
			 */
			/*
			 * for (int i = 0; i < roomAmenitiesMapInfo.size(); i++) { String
			 * hotelId = roomAmenitiesMapInfo.get(i).getHotel_id(); int
			 * roomTypeId = roomAmenitiesMapInfo.get(i).getRoomType_id(); int
			 * amenitiesId = (roomAmenitiesMapInfo.get(i) .getAmenities_id());
			 * int IsEnable = (roomAmenitiesMapInfo.get(i).getIsEnable()); Query
			 * qry = em .createQuery(
			 * "UPDATE RoomAmenitiesMap f SET f.isEnable=:isenable WHERE f.hotelId=:hotelid and f.roomTypeId=:roomtypeid and f.amenitiesId=:amenitiesid"
			 * ); qry.setParameter("isenable", IsEnable);
			 * qry.setParameter("hotelid", hotelId);
			 * qry.setParameter("roomtypeid", roomTypeId);
			 * qry.setParameter("amenitiesid", amenitiesId);
			 * qry.executeUpdate(); em.getTransaction().commit();
			 * logger.info("Room_Amenities_Map UPDATED successfully"); status =
			 * "success"; }
			 */
			String hotelId = roomAmenitiesMapInfoObj.getHotel_id();
			int roomTypeId = roomAmenitiesMapInfoObj.getRoomType_id();
			int amenitiesId = roomAmenitiesMapInfoObj.getAmenities_id();
			int IsEnable = roomAmenitiesMapInfoObj.getIsEnable();
			Query qry = em
					.createQuery("SELECT f FROM RoomAmenitiesMap f WHERE f.hotelId=:id and f.roomTypeId=:rid and f.amenitiesId=:aid");
			qry.setParameter("id", hotel_id);
			qry.setParameter("rid", roomType_Id);
			qry.setParameter("aid", amenitiesId);
			roomAmenitiesMap=(RoomAmenitiesMap) qry.getSingleResult();
			roomAmenitiesMap.setHotelId(hotelId);
			roomAmenitiesMap.setRoomTypeId(roomTypeId);
			roomAmenitiesMap.setAmenitiesId(amenitiesId);
			roomAmenitiesMap.setIsEnable(IsEnable);
			em.merge(roomAmenitiesMap);
			em.getTransaction().commit();
			//roomAmenitiesMapList=(List<RoomAmenitiesMap>) qry.getResultList();
			/*for (RoomAmenitiesMap roomAmenitiesMap2 : roomAmenitiesMapList) {
				if(roomAmenitiesMap2.getAmenitiesId()==amenitiesId){
					
					Query qry1 = em .createQuery( "UPDATE RoomAmenitiesMap f SET f.isEnable=:isenable WHERE f.hotelId=:hotelid and f.roomTypeId=:roomtypeid and f.amenitiesId=:amenitiesid" );
					qry1.setParameter("isenable", IsEnable);
					qry1.setParameter("hotelid", hotelId);
					qry1.setParameter("roomtypeid", roomTypeId);
					qry1.setParameter("amenitiesid", amenitiesId);
					qry1.executeUpdate();
					em2.getTransaction().commit();
					roomAmenitiesMap.setHotelId(hotelId);
					roomAmenitiesMap.setRoomTypeId(roomTypeId);
					roomAmenitiesMap.setAmenitiesId(amenitiesId);
					roomAmenitiesMap.setIsEnable(IsEnable);
					em.merge(roomAmenitiesMap);
					em2.getTransaction().commit();
					em.getTransaction().commit();
				}
			}*/
			
			/*Query qry = em
					.createQuery("UPDATE RoomAmenitiesMap f SET f.isEnable=:isenable WHERE f.hotelId=:hotelid and f.roomTypeId=:roomtypeid and f.amenitiesId=:amenitiesid");
			qry.setParameter("isenable", IsEnable);
			qry.setParameter("hotelid", hotelId);
			qry.setParameter("roomtypeid", roomTypeId);
			qry.setParameter("amenitiesid", amenitiesId);
			qry.executeUpdate();*/
			
			logger.info("Room_Amenities_Map UPDATED successfully");
			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		}
		return status;
	}

	@Override
	public List<RoomAmenitiesMap> getAllAmenitiesToRoomTypeById(String hotelId,
			int roomTypeId2) throws DAOException {
		List<RoomAmenitiesMap> roomAmenitiesMapList = null;
		EntityManager em = null;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Query qry = em
					.createQuery("SELECT f FROM RoomAmenitiesMap f WHERE f.hotelId=:Id and f.roomTypeId=:roomtypeid");
			qry.setParameter("Id", hotelId);
			qry.setParameter("roomtypeid", roomTypeId2);
			roomAmenitiesMapList = (List<RoomAmenitiesMap>) qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		}
		return roomAmenitiesMapList;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
}
