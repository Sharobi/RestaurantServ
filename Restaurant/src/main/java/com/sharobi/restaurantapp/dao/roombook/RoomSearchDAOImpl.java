/**
 * 
 */
package com.sharobi.restaurantapp.dao.roombook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;
import java.util.Calendar;

import com.sharobi.restaurantapp.commonUtil.DateUtil;
import com.sharobi.restaurantapp.commonUtil.ResponseObj;
import com.sharobi.restaurantapp.commonUtil.ReturnConstant;
import com.sharobi.restaurantapp.commonUtil.StoredProcedures;
import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.StoreCustomer;
import com.sharobi.restaurantapp.model.account.JournalEntry;
import com.sharobi.restaurantapp.model.roomBook.CheckInOut;
import com.sharobi.restaurantapp.model.roomBook.FlashReportData;
import com.sharobi.restaurantapp.model.roomBook.RbAppHomeData;
import com.sharobi.restaurantapp.model.roomBook.RbSnapShotData;
import com.sharobi.restaurantapp.model.roomBook.Room;
import com.sharobi.restaurantapp.model.roomBook.RoomBooking;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingCustomer;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingInfo;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingPayment;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingServices;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingType;
import com.sharobi.restaurantapp.model.roomBook.RoomType;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingDetails;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingGuest;
import com.sharobi.restaurantapp.model.roomBook.TaxForRoomBook;
import com.sharobi.restaurantapp.model.util.PersistenceListener;


/**
 * @author ChanchalN
 * Implementation class for RoomSearchDao.
 */
@Component("roomSearchDAO")
public class RoomSearchDAOImpl implements RoomSearchDAO{
	
	private EntityManagerFactory entityManagerFactory;
	private final static Logger logger = LogManager
			.getLogger(RoomDAOImpl.class);

	public double DateDifferent(String fromDate, String toDate) {
		
		String dateStart = fromDate;				//"01/14/2012 09:29:58";
		String dateStop = toDate;					//"01/15/2012 10:31:48";

		if(fromDate.equalsIgnoreCase(toDate))
			return 1.0;
		else {
		double diffDays=0.0;
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			//in milliseconds
			long diff = d2.getTime() - d1.getTime();

			/*double diffSeconds = diff / 1000 % 60;
			double diffMinutes = diff / (60 * 1000) % 60;
			double diffHours = diff / (60 * 60 * 1000) % 24;*/
			 diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			/*System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");*/

		} catch (Exception e) {
			e.printStackTrace();
		}
		return diffDays;
		}
	}
	
	@Override
	public List<RoomBookingType> getRoomBookingTypes(String hotelId)
			throws DAOException {
		List<RoomBookingType> rbtList=null;
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			TypedQuery<RoomBookingType> qry = em
					.createQuery("SELECT rbt FROM RoomBookingType rbt WHERE rbt.storeId=:storeId and rbt.isDeleted=0", RoomBookingType.class);
			qry.setParameter("storeId", Integer.parseInt(hotelId));
			rbtList = qry.getResultList();
			em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot get booking types by store Id.", e);
		}
		finally {
			if(em != null) em.close();
		}
		return rbtList;
	}
	
	@Override
	public StoreCustomer createOrUpdateStorecustomer(StoreCustomer customer) throws DAOException {
		EntityManager em = null;
		StoreCustomer cust = null;
		StoreCustomer newcustomer=null;
		int custId=0;
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			customer.setDeleteFlag("N");
			String contactNo = customer.getContactNo();
			int storeId = customer.getStoreId();
			if(customer.getId()==0)
			{
			// check if customer already exists
			try {
				TypedQuery<StoreCustomer> qry = em
						.createQuery("SELECT c FROM StoreCustomer c WHERE c.contactNo=:contactNo and c.storeId=:storeId",StoreCustomer.class);
				qry.setParameter("contactNo", contactNo);
				qry.setParameter("storeId", storeId);
				cust = qry.getSingleResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (cust == null) {

				em.persist(customer);
				em.getTransaction().commit();
				custId=customer.getId();
				TypedQuery<StoreCustomer> qry = em
						.createQuery("SELECT c FROM StoreCustomer c WHERE c.id=:id",StoreCustomer.class);
				qry.setParameter("id", custId);
				newcustomer=qry.getSingleResult();
				newcustomer.setType("success");
				insertCustomerAccount(customer);
			}

			else {
				newcustomer=new StoreCustomer();
				newcustomer.setId(0);
				newcustomer.setType("duplicate phone no");
			}
			
			
			
			}else
			{
				em.merge(customer);
				em.getTransaction().commit();
				custId=customer.getId();
				TypedQuery<StoreCustomer> qry = em
						.createQuery("SELECT c FROM StoreCustomer c WHERE c.id=:id",StoreCustomer.class);
				qry.setParameter("id", custId);
				newcustomer=qry.getSingleResult();
				newcustomer.setType("success");
				updateCustomerAccount(customer);
			}

		} catch (Exception e) {
			e.printStackTrace();
			newcustomer=new StoreCustomer();
			newcustomer.setId(0);
			newcustomer.setType("failure");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			if(em != null) em.close();
		}

		
		
		return newcustomer;

	}
	
	/*@Override
	public List<Room> searchRoom(String fromDate, String toDate, String hotelId) throws DAOException {
		List<Room> finalroomList = new ArrayList<Room>();
		List<Room> roomList  = null;
		EntityManager em = null;
		EntityManager em1 = null;
		EntityManager em2 = null;
		EntityManager em3 = null;
		EntityManager em4 = null;
		EntityManager em5 = null;
		
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em1 = entityManagerFactory.createEntityManager();
		em2 = entityManagerFactory.createEntityManager();
		em3 = entityManagerFactory.createEntityManager();
		em4 = entityManagerFactory.createEntityManager();
		em5 = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em1.getTransaction().begin();
		em2.getTransaction().begin();
		em3.getTransaction().begin();
		em4.getTransaction().begin();
		em5.getTransaction().begin();
		try {
			Query q = em.createQuery("SELECT r FROM Room r WHERE r.hotelId=:hotelId");
			q.setParameter("hotelId", hotelId);
			roomList = q.getResultList();
			System.out.println("Length: "+roomList.size());
			
			for (Room room : roomList) {
				int rid = room.getId();
				List<Room> roomListByRoomIdFromRoomUnavail = null;   //List to get the list of room from RoomBookingDetails table.
				String query = "SELECT DISTINCT x.room_id FROM ( SELECT RU.* from rb_room_unavailability RU "
						+ " inner join rb_m_check_in_out CIO on RU.hotel_id=CIO.store_id "
						+ " WHERE ( ADDTIME( STR_TO_DATE('"+fromDate+"', '%Y-%m-%d %h:%i'), CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) )  >= ADDTIME( STR_TO_DATE(RU.fromDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) ) " 
						+ " and "
						+ " ADDTIME( STR_TO_DATE('"+fromDate+"', '%Y-%m-%d %h:%i'), CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) )  <= ADDTIME( STR_TO_DATE(RU.toDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_out_time,1,5),'00:00')) )  ) "
						+ " AND RU.hotel_id="+ hotelId +" AND RU.room_id="+ rid +" AND RU.isCancelled='N' "
						+ " union all "
						+ " SELECT RU.* from rb_room_unavailability RU " 
						+ " inner join rb_m_check_in_out CIO on RU.hotel_id=CIO.store_id "
						+ " WHERE ( ADDTIME( STR_TO_DATE('"+toDate+"', '%Y-%m-%d %h:%i'), CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) )  >= ADDTIME( STR_TO_DATE(RU.fromDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) ) " 
						+ " and "
						+ " ADDTIME( STR_TO_DATE('"+toDate+"', '%Y-%m-%d %h:%i'), CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) )  <= ADDTIME( STR_TO_DATE(RU.toDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_out_time,1,5),'00:00')) )  ) "
						+ " AND RU.hotel_id="+ hotelId +" AND RU.room_id="+ rid +" AND RU.isCancelled='N' "
						+ " union all "
						+ " SELECT RU.* from rb_room_unavailability RU " 
						+ " inner join rb_m_check_in_out CIO on RU.hotel_id=CIO.store_id "
						+ " WHERE (ADDTIME( STR_TO_DATE(RU.toDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_out_time,1,5),'00:00')) )  >= ADDTIME( STR_TO_DATE('"+fromDate+"', '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) ) "
						+ " and " 
						+ " ADDTIME( STR_TO_DATE(RU.toDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_out_time,1,5),'00:00')) )   <= ADDTIME( STR_TO_DATE('"+toDate+"', '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_out_time,1,5),'00:00')) ) ) "
						
						+ " AND RU.hotel_id="+ hotelId +" AND RU.room_id="+ rid +" AND RU.isCancelled='N' "
						+ " )x;";
			 
				System.out.println("Query1::searchRoom "+query);
				Query qry = em.createNativeQuery(query);
				roomListByRoomIdFromRoomUnavail =  qry.getResultList();
				if(roomListByRoomIdFromRoomUnavail.size()>0) {
					// Reserved Room
					System.out.println("Inside If.");
					Room roomobj = null;
					String query1 = "SELECT DISTINCT x.* FROM ( SELECT RU.*,c.name,c.phone from rb_room_unavailability RU "
							+ " inner join rb_m_check_in_out CIO on RU.hotel_id=CIO.store_id "
							+ " inner join rb_customer c on RU.customerId=c.id "
							+ " WHERE ( ADDTIME( STR_TO_DATE('"+fromDate+"', '%Y-%m-%d %h:%i'), CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) )  >= ADDTIME( STR_TO_DATE(RU.fromDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) ) " 
							+ " and "
							+ " ADDTIME( STR_TO_DATE('"+fromDate+"', '%Y-%m-%d %h:%i'), CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) )  <= ADDTIME( STR_TO_DATE(RU.toDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_out_time,1,5),'00:00')) )  ) "
							+ " AND RU.hotel_id="+ hotelId +" AND RU.room_id="+ rid +" AND RU.isCancelled='N' "
							+ " union all "
							+ " SELECT RU.*,c.name,c.phone from rb_room_unavailability RU " 
							+ " inner join rb_m_check_in_out CIO on RU.hotel_id=CIO.store_id "
							+ " inner join rb_customer c on RU.customerId=c.id "
							+ " WHERE ( ADDTIME( STR_TO_DATE('"+toDate+"', '%Y-%m-%d %h:%i'), CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) )  >= ADDTIME( STR_TO_DATE(RU.fromDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) ) " 
							+ " and "
							+ " ADDTIME( STR_TO_DATE('"+toDate+"', '%Y-%m-%d %h:%i'), CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) )  <= ADDTIME( STR_TO_DATE(RU.toDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_out_time,1,5),'00:00')) )  ) "
							+ " AND RU.hotel_id="+ hotelId +" AND RU.room_id="+ rid +" AND RU.isCancelled='N' "
							+ " union all "
							+ " SELECT RU.*,c.name,c.phone from rb_room_unavailability RU " 
							+ " inner join rb_m_check_in_out CIO on RU.hotel_id=CIO.store_id "
							+ " inner join rb_customer c on RU.customerId=c.id "
							+ " WHERE (ADDTIME( STR_TO_DATE(RU.toDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_out_time,1,5),'00:00')) )  >= ADDTIME( STR_TO_DATE('"+fromDate+"', '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_in_time,1,5),'00:00')) ) "
							+ " and " 
							+ " ADDTIME( STR_TO_DATE(RU.toDate, '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_out_time,1,5),'00:00')) )   <= ADDTIME( STR_TO_DATE('"+toDate+"', '%Y-%m-%d %h:%i') , CONCAT(ifnull(SUBSTR(RU.check_out_time,1,5),'00:00')) ) ) "
							
							+ " AND RU.hotel_id="+ hotelId +" AND RU.room_id="+ rid +" AND RU.isCancelled='N' "
							+ " )x;";
					
					System.out.println("query1:: "+query1);
					
					Query qry1 = em1.createNativeQuery(query1);
					
					//qry1.setParameter("rid", rid);
					List<Object> roomObj =  qry1.getResultList();
					List<RoomBookingDetails> roomUn = new ArrayList<RoomBookingDetails>();
					
					Iterator<Object> it=roomObj.iterator();
					while (it.hasNext()) {
						Object[] roomUnDetls = (Object[]) it.next();
						
						String checkIn=(String)roomUnDetls[6];
						String checkOut=(String)roomUnDetls[10];
						String fromDt=(String)roomUnDetls[3];
						String toDt=(String)roomUnDetls[4];
						String resId=(String)roomUnDetls[8];
						Date bookingDt=(Date)roomUnDetls[11];
						String custName=(String)roomUnDetls[22];
						String custPh=(String)roomUnDetls[23];
						double advAmt=(double)roomUnDetls[7];
						RoomBookingDetails roomUnavlbl=new RoomBookingDetails();
						roomUnavlbl.setIsCheckIn(checkIn);
						roomUnavlbl.setIsCheckOut(checkOut);
						roomUnavlbl.setFromDate(fromDt);
						roomUnavlbl.setToDate(toDt);
						roomUnavlbl.setReserveId(resId);
						roomUnavlbl.setBookingDate(bookingDt);
						roomUnavlbl.setCustName(custName);
						roomUnavlbl.setCustPh(custPh);
						roomUnavlbl.setAdvancePayment(advAmt);
						
						roomUn.add(roomUnavlbl);
						
					}
					
					Query qry2 = em3.createQuery("SELECT r FROM Room r WHERE r.id=:rid");
					qry2.setParameter("rid", rid);
					roomobj = (Room) qry2.getSingleResult();
					roomobj.setIsStatus("Y");
					if(roomUn.size()>0) {
					RoomBookingDetails RoomBookingDetails=roomUn.get(roomUn.size()-1);
					if(roomUn.get(roomUn.size()-1).getIsCheckOut().equalsIgnoreCase("Y") && roomUn.get(roomUn.size()-1).getIsCheckIn().equalsIgnoreCase("Y")) {
							roomobj.setIsCheckIn("N");
							roomobj.setIsBooked("N");
					}
					else if(roomUn.get(roomUn.size()-1).getIsCheckOut().equalsIgnoreCase("N") && roomUn.get(roomUn.size()-1).getIsCheckIn().equalsIgnoreCase("Y")) {
						roomobj.setIsCheckIn("Y");
						roomobj.setIsBooked("Y");
						//set values
						roomobj.setAdvPayment(RoomBookingDetails.getAdvancePayment());
						roomobj.setCustName(RoomBookingDetails.getCustName());
						roomobj.setCustPh(RoomBookingDetails.getCustPh());
						roomobj.setReserveId(RoomBookingDetails.getReserveId());
						roomobj.setFromDate(RoomBookingDetails.getFromDate());
						roomobj.setToDate(RoomBookingDetails.getToDate());
						roomobj.setBookingDate(RoomBookingDetails.getBookingDate());
					}
					else if(roomUn.get(roomUn.size()-1).getIsCheckOut().equalsIgnoreCase("N") && roomUn.get(roomUn.size()-1).getIsCheckIn().equalsIgnoreCase("N")) {
						roomobj.setIsCheckIn("N");
						roomobj.setIsBooked("Y");
						roomobj.setAdvPayment(RoomBookingDetails.getAdvancePayment());
						roomobj.setCustName(RoomBookingDetails.getCustName());
						roomobj.setCustPh(RoomBookingDetails.getCustPh());
						roomobj.setReserveId(RoomBookingDetails.getReserveId());
						roomobj.setFromDate(RoomBookingDetails.getFromDate());
						roomobj.setToDate(RoomBookingDetails.getToDate());
						roomobj.setBookingDate(RoomBookingDetails.getBookingDate());
					}
					}
					else {
						roomobj.setIsCheckIn("N");
						roomobj.setIsBooked("N");
					}
					if(roomobj.getStatus()==1){
						roomobj.setIsStatus("N");
					}
					finalroomList.add(roomobj);
				}else {
					System.out.println("Inside Else.");
					List<Room> roomListByRoomIdFromRoomUnavailManagement = null;
					     //// Search in RoomManagementUnavailability table
					
					String query1 = "SELECT DISTINCT x.room_id FROM ( SELECT * from rb_room_management_unavailability RU "
							+ " WHERE ( '" + fromDate + "' >= RU.fromDate and '"+ fromDate +"'<=RU.toDate  ) "
							+ " AND RU.hotel_id="+ hotelId +" AND RU.room_id="+ rid +" AND RU.isComplete='Y' "
							+ " union all "
							+ " SELECT * from rb_room_management_unavailability RU " 
							+ " WHERE ( '"+ toDate +"' >= RU.fromDate and '"+ toDate +"'<=RU.toDate  ) "
							+ " AND RU.hotel_id="+ hotelId +" AND RU.room_id="+ rid +" AND RU.isComplete='Y' "
							+ " union all "
							+ " SELECT * from rb_room_management_unavailability RU " 
							+ " WHERE ( RU.toDate >='" + fromDate + "' and RU.toDate  <= '"+ toDate +"' ) "
							+ " AND RU.hotel_id="+ hotelId +" AND RU.room_id="+ rid +" AND RU.isComplete='Y' "
							+ " )x;";
					System.out.println("Room manaegement unavailability Query:: "+query1);
					Query qry1 = em2.createNativeQuery(query1);
					roomListByRoomIdFromRoomUnavailManagement = qry1.getResultList();
					if(roomListByRoomIdFromRoomUnavailManagement.size()==0) {
						Room roomobj1 = null;
						Query qry4 = em4.createQuery("SELECT r FROM Room r WHERE r.id=:rid");
						qry4.setParameter("rid", rid);
						roomobj1 = (Room) qry4.getSingleResult();
						roomobj1.setIsBooked("N");
						roomobj1.setIsStatus("Y");
						roomobj1.setIsCheckIn("N");
						if(roomobj1.getStatus()==1){
							roomobj1.setIsStatus("N");
						}
						finalroomList.add(roomobj1);
					}
					else {
						Room roomobj2 = null;
						
						Query qry5 = em5.createQuery("SELECT r FROM Room r WHERE r.id=:rid");
						qry5.setParameter("rid", rid);
						roomobj2 = (Room) qry5.getSingleResult();
						roomobj2.setIsBooked("N");
						roomobj2.setIsStatus("N");
						roomobj2.setIsCheckIn("N");
						if(roomobj2.getStatus()==1){
							roomobj2.setIsStatus("N");
						}
						finalroomList.add(roomobj2);
						
					}
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Searching failed", e);
		}
		finally {
			em.close();
			em1.close();
			em2.close();
			em3.close();
			em4.close();
			em5.close();
		}
		
		return finalroomList;
	}*/
	
	@Override
	public List<Room> searchRoom(String fromDate, String toDate, String hotelId) throws DAOException {
		List<Room> finalroomList = new ArrayList<Room>();
		EntityManager em = null;
		EntityManager em1 = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em1 = entityManagerFactory.createEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em1.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			
			java.sql.Date frmDate = DateUtil.convertStringDateToSqlDate(fromDate, "yyyy-MM-dd");
			java.sql.Date tDate = DateUtil.convertStringDateToSqlDate(toDate, "yyyy-MM-dd");
			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_ROOM_STATUS_BY_DATE);
			callableStatement.setInt(1, Integer.parseInt(hotelId));
			callableStatement.setDate(2, frmDate);
			callableStatement.setDate(3, tDate);
			callableStatement.execute();
			rs = callableStatement.getResultSet();

			//ReflectionResultSetMapper<Room> resultSetMapper1 = new ReflectionResultSetMapper<Room>(Room.class);

			while (rs.next()) {
				Room room=new Room();
				room.setId(rs.getInt(1));
				room.setHotelId(rs.getString(2));
				room.setFloor(rs.getInt(3));
				room.setRoomNo(rs.getString(4));
				//room = resultSetMapper1.mapRow(rs);
				RoomType rt=em1.find(RoomType.class,rs.getInt(5));
				room.setRoomTypeId(rt);
				room.setRoomCategory(rs.getString(6));
				room.setRoomCapacity(rs.getString(7));
				room.setRoomPrice(rs.getDouble(8));
				TaxForRoomBook tx=em1.find(TaxForRoomBook.class,rs.getInt(9));
				room.setTaxId(tx);
				room.setRoomName(rs.getString(10));
				room.setRoomDesc(rs.getString(11));
				room.setRoomSize(rs.getString(12));
				room.setRoomSizeUnit(rs.getString(13));
				room.setStatus(rs.getInt(14));
				room.setIsStatus(rs.getString(17));
				room.setIsBooked(rs.getString(18));
				room.setIsCheckIn(rs.getString(19));
				room.setFromDate(rs.getString(21));
				room.setToDate(rs.getString(22));
				room.setReserveId(rs.getString(23));
				room.setBookingDate(rs.getDate(24));
				room.setCustName(rs.getString(25));
				room.setCustPh(rs.getString(26));
				room.setAdvPayment(rs.getDouble(27));
				room.setBookingId(rs.getInt(28));
				room.setBookingNo(rs.getString(29));
				finalroomList.add(room);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				rs.close();
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em1.close();
			em.close();
		}
		
		return finalroomList;
	}

	@Override
	public String reserveRoom(RoomBookingInfo rb) throws DAOException {
		String reserveId = "";
		EntityManager em = null;
		EntityManager em1 = null;
		EntityManager em2 = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em1 = entityManagerFactory.createEntityManager();
		em2 = entityManagerFactory.createEntityManager();
		em1.getTransaction().begin();
		try {
			CheckInOut checkInOut = null;
			RoomBookingCustomer roombookingcustomer = new RoomBookingCustomer();
			String hotelId=Integer.toString(rb.getHotelId());
			Query q = em.createQuery("SELECT c FROM CheckInOut c WHERE c.hotelId=:hotelId");
			q.setParameter("hotelId", hotelId);
			checkInOut = (CheckInOut) q.getSingleResult();
			
			try {
				//get customer by phone
				Query q4=em2.createQuery("Select c from RoomBookingCustomer c where c.phone=:phone");
				q4.setParameter("phone", rb.getCustomer().getPhone());
				roombookingcustomer = (RoomBookingCustomer) q4.getSingleResult();
				roombookingcustomer.setName(rb.getCustomer().getName());
				roombookingcustomer.setPhone(rb.getCustomer().getPhone());
				roombookingcustomer.setEmail(rb.getCustomer().getEmail());
				roombookingcustomer.setStore_id(rb.getCustomer().getStore_id());
				roombookingcustomer.setDeleteflag(rb.getCustomer().getDeleteflag());
				roombookingcustomer.setAddress(rb.getCustomer().getAddress());
				roombookingcustomer.setGender(rb.getCustomer().getGender());
				roombookingcustomer.setDob(rb.getCustomer().getDob());
				roombookingcustomer.setLocation(rb.getCustomer().getLocation());
				roombookingcustomer.setHouseno(rb.getCustomer().getHouseno());
				roombookingcustomer.setStreet(rb.getCustomer().getStreet());
				roombookingcustomer.setState(rb.getCustomer().getState());
				roombookingcustomer.setUniqueidNo(rb.getCustomer().getUniqueidNo());
				roombookingcustomer.setUniqueidType(rb.getCustomer().getUniqueidType());
				em2.merge(roombookingcustomer);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				roombookingcustomer.setName(rb.getCustomer().getName());
				roombookingcustomer.setPhone(rb.getCustomer().getPhone());
				roombookingcustomer.setEmail(rb.getCustomer().getEmail());
				roombookingcustomer.setStore_id(rb.getCustomer().getStore_id());
				roombookingcustomer.setDeleteflag(rb.getCustomer().getDeleteflag());
				roombookingcustomer.setAddress(rb.getCustomer().getAddress());
				roombookingcustomer.setGender(rb.getCustomer().getGender());
				roombookingcustomer.setDob(rb.getCustomer().getDob());
				roombookingcustomer.setLocation(rb.getCustomer().getLocation());
				roombookingcustomer.setHouseno(rb.getCustomer().getHouseno());
				roombookingcustomer.setStreet(rb.getCustomer().getStreet());
				roombookingcustomer.setState(rb.getCustomer().getState());
				roombookingcustomer.setUniqueidNo(rb.getCustomer().getUniqueidNo());
				roombookingcustomer.setUniqueidType(rb.getCustomer().getUniqueidType());
				em1.persist(roombookingcustomer);
				em1.flush();
				em1.getTransaction().commit();
			}
			
			Integer id=roombookingcustomer.getId();
			Calendar calendar = Calendar.getInstance();
			int hr=calendar.get(Calendar.HOUR_OF_DAY);
			int min=calendar.get(Calendar.MINUTE);
			String hrstr=String.format("%02d", hr);
			String minstr=String.format("%02d", min);
			System.out.println("Hour: " + hr+":HourStr:"+hrstr);
	        System.out.println("Minute: " + min+":MinuteStr:"+minstr);

			
			
			reserveId = rb.getCustomer().getName().substring(0, 1)+rb.getFromDate().replaceAll("[\\s\\-()]", "")+hrstr+minstr+id;
			System.out.println("Reserve Id for the booking: "+reserveId);
			
			double final_amount = 0.0;
			double tottaxamount = 0.0;
			double totdiscamt=0.0;
			double datediff = DateDifferent(rb.getFromDate(),rb.getToDate());
			for (int i=0;i<rb.getRoomid().size();i++) {
				
				// Added for Payment
				Room roomobj = null;
				int rid = rb.getRoomid().get(i).getId();
				Query qry1 = em.createQuery("SELECT r FROM Room r WHERE r.id=:rid");
				qry1.setParameter("rid", rid);
				roomobj =  (Room) qry1.getSingleResult();
				
				double discper=0.0;
				//double taxpercentage = roomobj.getTaxId().getPercentage();
				double taxpercentage = rb.getRoomid().get(i).getTaxId().getPercentage();
				System.out.println("Tax percentage: "+taxpercentage);
				//double roomprice = Double.parseDouble(roomobj.getRoomPrice())*datediff;
				double roomprice = rb.getRoomid().get(i).getRoomPrice()*datediff;
				double discamt=roomprice*discper;
				double taxamount=(roomprice-discamt)*(taxpercentage/100);
				System.out.println("roomprice: "+roomprice);
				final_amount+=roomprice;
				tottaxamount+=taxamount;
				totdiscamt+=discamt;
				System.out.println("indv gross: "+roomprice+":tax:"+taxamount+":disc:"+discamt+":netamt:"+(roomprice-discamt+taxamount));
				
				RoomBookingDetails RoomBookingDetails = new RoomBookingDetails();
				RoomBookingDetails.setHotelId(rb.getHotelId());
				RoomBookingDetails.setRoomId(rb.getRoomid().get(i));
				RoomBookingDetails.setFromDate(rb.getFromDate());
				RoomBookingDetails.setToDate(rb.getToDate());
				RoomBookingDetails.setCustomerId(id);

				RoomBookingDetails.setReserveId(reserveId.toUpperCase());  
				
				RoomBookingDetails.setIsCancelled("N");
				RoomBookingDetails.setIsCheckIn("N");
				RoomBookingDetails.setIsCheckOut("N");
				RoomBookingDetails.setBookingDate(new Date());
				RoomBookingDetails.setCheckInTime(checkInOut.getCheckIn());
				RoomBookingDetails.setCheckOutTime(checkInOut.getCheckOut());
				RoomBookingDetails.setRoomRate(rb.getRoomid().get(i).getRoomPrice());
				RoomBookingDetails.setRoomTotal(roomprice);
				RoomBookingDetails.setTaxId(rb.getRoomid().get(i).getTaxId().getId());
				RoomBookingDetails.setTaxRate(taxpercentage);
				RoomBookingDetails.setTaxAmt(taxamount);
				RoomBookingDetails.setDiscPer(discper);
				RoomBookingDetails.setDiscAmt(discamt);
				RoomBookingDetails.setNetTotal(roomprice-discamt+taxamount);
				//RoomBookingDetails.setCheckoutTimeId(checkOut);
				em.persist(RoomBookingDetails);
				
				
			}
			double total = final_amount-totdiscamt+tottaxamount;
			System.out.println("net total:  "+total);
			em2.getTransaction().begin();
			RoomBookingPayment roomBookingPayment = new RoomBookingPayment();
			roomBookingPayment.setHotelId(hotelId);
			roomBookingPayment.setReserveId(reserveId.toUpperCase());
			roomBookingPayment.setAmount(total);			// Amount is set inclusive of tax
			roomBookingPayment.setAmt_to_pay(total);
			roomBookingPayment.setIsCancelled("N");
			System.out.println("Payment object :"+roomBookingPayment.toString());
			em2.persist(roomBookingPayment);
			em2.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Room booking failed", e);
		}finally {
			em.getTransaction().commit();
			em.close();
			em1.close();
			em2.close();
		}
		return reserveId.toUpperCase();
	}
	
	//this is used for check in page loading
	@Override
	public List<RoomBooking> getAllReserveIdByDateHotelId(String fromDate, String hotelId) throws DAOException {
		
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		List<RoomBooking> roomBookingList = new ArrayList<RoomBooking>();
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//Date date=format.parse(fromDate);
			TypedQuery<RoomBooking> qry=em.createQuery("SELECT r FROM RoomBooking r WHERE r.hotelId=:hotelId and r.checkInDate=:fromDate  and r.isCheckedIn='N' and r.isCancelled='N'",RoomBooking.class);
			qry.setParameter("hotelId", Integer.parseInt(hotelId));
			qry.setParameter("fromDate", fromDate);
			roomBookingList=qry.getResultList();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Operation failed in getAllReserveIdByDateHotelId.", e);
		}
		finally {
			em.close();
		}
		return roomBookingList;
	}

	@Override
	public String cancelBookingByReserveId(RoomBooking rb) throws DAOException {
		EntityManager em = null;
		EntityManager em1 = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em1 = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		String status="failure";
		try {
			TypedQuery<RoomBooking> qry=em.createQuery("Select rb from RoomBooking rb where rb.id=:bookingId and rb.hotelId=:hotelId",RoomBooking.class);
			qry.setParameter("bookingId",rb.getId());
			qry.setParameter("hotelId",rb.getHotelId());
			RoomBooking booking=qry.getSingleResult();
			booking.setIsCancelled("Y");
			booking.setCancelBy(rb.getCancelBy());
			booking.setCancelDate(new Date());
			booking.setCancelRemarks(rb.getCancelRemarks());
			List<RoomBookingDetails> bookingdetailsList=booking.getBookingDetail();
			Iterator<RoomBookingDetails> iterator=bookingdetailsList.iterator();
			while(iterator.hasNext())
			{
				RoomBookingDetails rbd=(RoomBookingDetails)iterator.next();
				rbd.setIsCancelled("Y");
			}
			em.getTransaction().commit();
			em1.getTransaction().begin();
			TypedQuery<RoomBookingPayment> q1 = em1.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.booking_id=:bookingId AND r.hotelId=:hotelId",RoomBookingPayment.class);
			q1.setParameter("bookingId",rb.getId());
			q1.setParameter("hotelId",String.valueOf(rb.getHotelId()));
			List<RoomBookingPayment> roomBookingPaymentlist = q1.getResultList();
			Iterator<RoomBookingPayment> iterator1=roomBookingPaymentlist.iterator();
			while(iterator1.hasNext())
			{
				RoomBookingPayment rbp=(RoomBookingPayment)iterator1.next();
				rbp.setIsCancelled("Y");
			}
			em1.getTransaction().commit();
			status="success";
		}catch(Exception e) {
			logger.error("Error while cancel Booking. ",e);
			e.printStackTrace();
		}
		finally {
			em.close();
			em1.close();
		}
		return status;
	}

	//this is used for check in page search button
	@Override
	public List<RoomBooking> getAllReserveIdByDatePeriod(String fromDate, String toDate, String hotelId,String bookingNo)
			throws DAOException {
		
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		List<RoomBooking> roomBookingList = new ArrayList<RoomBooking>();
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//Date dateFrom=format.parse(fromDate);
			//Date dateTo=format.parse(toDate);
			if("0".equalsIgnoreCase(bookingNo))
			{
			TypedQuery<RoomBooking> qry=em.createQuery("SELECT r FROM RoomBooking r WHERE r.hotelId=:hotelId and r.checkInDate between :fromDate and :toDate and r.isCheckedIn='N' and r.isCancelled='N'",RoomBooking.class);
			qry.setParameter("hotelId", Integer.parseInt(hotelId));
			qry.setParameter("fromDate", fromDate);
			qry.setParameter("toDate", toDate);
			roomBookingList=qry.getResultList();
			}else
			{
				TypedQuery<RoomBooking> qry=em.createQuery("SELECT r FROM RoomBooking r WHERE r.hotelId=:hotelId and r.checkInDate between :fromDate and :toDate and r.bookingNo=:bookingNo and r.isCheckedIn='N' and r.isCancelled='N'",RoomBooking.class);
				qry.setParameter("hotelId", Integer.parseInt(hotelId));
				qry.setParameter("fromDate", fromDate);
				qry.setParameter("toDate", toDate);
				qry.setParameter("bookingNo", bookingNo);
				roomBookingList=qry.getResultList();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Operation failed in getAllReserveIdByDatePeriod.", e);
		}
		finally {
			em.close();
		}
		return roomBookingList;
		
	}

	@Override
	public String advancePaymentByReserveId(RoomBookingPayment rp) throws DAOException {
		String stat="failure";
		EntityManager em = null;
		EntityManager em1 = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em1 = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			int bookingId=rp.getBooking_id();
			int hotelId=Integer.parseInt(rp.getHotelId());
			TypedQuery<RoomBooking> qry=em.createQuery("Select rb from RoomBooking rb where rb.id=:bookingId and rb.hotelId=:hotelId",RoomBooking.class);
			qry.setParameter("bookingId",bookingId);
			qry.setParameter("hotelId",hotelId);
			RoomBooking booking=qry.getSingleResult();
			List<RoomBookingDetails> bookingdetailsList=booking.getBookingDetail();
			Iterator<RoomBookingDetails> iterator=bookingdetailsList.iterator();
			while(iterator.hasNext())
			{
				RoomBookingDetails rbd=(RoomBookingDetails)iterator.next();
				rbd.setAdvancePayment(rbd.getAdvancePayment()+rp.getTenderamount());
			}
			em.getTransaction().commit();
			em1.getTransaction().begin();
			TypedQuery<RoomBookingPayment> q2 = em1.createQuery("SELECT rp FROM RoomBookingPayment rp WHERE rp.booking_id=:bookingId AND rp.hotelId=:hotelId",RoomBookingPayment.class);
			q2.setParameter("bookingId", bookingId);
			q2.setParameter("hotelId", Integer.toString(hotelId));
			List<RoomBookingPayment> roompayments = q2.getResultList();
			double paidAmt=0.0;
			double amt=0.0;
			Iterator<RoomBookingPayment> iterator1=roompayments.iterator();
			while(iterator1.hasNext())
			{
				RoomBookingPayment rbp=(RoomBookingPayment)iterator1.next();
				paidAmt+=rbp.getPaidamount();
				amt=rbp.getAmount();
			}
			paidAmt=paidAmt+rp.getTenderamount();
			rp.setAmount(amt);
			rp.setPaidamount(rp.getTenderamount());
			rp.setAmt_to_pay(amt-paidAmt);
			rp.setIsCancelled("N");
			em1.persist(rp);
			em1.getTransaction().commit();
			stat="success";
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Payment failed.", e);
		}finally {
			em.close();
			em1.close();
		}
		if(("y").equalsIgnoreCase(rp.getIs_account())) {
			CustomerPaymentjournalEntryRb(rp);
		}
		
		return stat;
	}

	@Override
	public String roomBooking(RoomBooking rb) throws DAOException {
		String bookingId="0";
		int generatedId=0;
		EntityManager em = null;
		EntityManager em1 = null;
		/*EntityManager em2 = null;
		EntityManager em3 = null;*/
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em1 = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		
		try {
			
			double final_amount = 0.0;
			double tottaxamount = 0.0;
			double totdiscamt=0.0;
			double discper=0.0;
			double totnettotal=0.0;
			TypedQuery<CheckInOut> q1=em.createQuery("Select c from CheckInOut c where c.hotelId=:hotelId",CheckInOut.class);
			q1.setParameter("hotelId",Integer.toString(rb.getHotelId()));
			CheckInOut chkInOut=q1.getSingleResult();
			rb.setIsCancelled("N");
			rb.setCreditFlag("N");
			rb.setCheckinTimeId(chkInOut.getCheckIn());
			rb.setCheckoutTimeId(chkInOut.getCheckOut());
			if("Y".equalsIgnoreCase(rb.getIsCheckedIn()))
			{
				String s = new SimpleDateFormat("HH:mm").format(new Date());
				rb.setActualCheckinTime(s);
			}
			
			List<RoomBookingDetails> bookingdetailsList=rb.getBookingDetail();
			Iterator<RoomBookingDetails> iterator=bookingdetailsList.iterator();
			while(iterator.hasNext())
			{
				RoomBookingDetails rbd=(RoomBookingDetails)iterator.next();
				double datediff = DateDifferent(rbd.getFromDate(),rbd.getToDate());
				double taxpercentage = rbd.getTaxRate();
				double roomprice = rbd.getRoomRate()*datediff;
				double discamt=roomprice*discper;
				double taxamount=(roomprice-discamt)*(taxpercentage/100);
				final_amount+=roomprice;
				tottaxamount+=taxamount;
				totdiscamt+=discamt;
				totnettotal+=(roomprice-discamt+taxamount);
				
			}
			
			rb.setBookingDate(new Date());
			rb.setGrossAmt(final_amount);
			rb.setDiscPer(0.0);
			rb.setDiscAmt(totdiscamt);
			rb.setTaxAmt(tottaxamount);
			rb.setRoundOff(Math.round(totnettotal)-totnettotal);
			rb.setNetAmt(Math.round(totnettotal));
			rb.setIsTaxable("Y");
			rb.setCreatedDate(new Date());
			em.persist(rb);
			generatedId=rb.getId();
			em.getTransaction().commit();
			em1.getTransaction().begin();
			TypedQuery<RoomBooking> createdbookingqry=em1.createQuery("Select rb from RoomBooking rb where rb.id=:bookingId",RoomBooking.class);
			createdbookingqry.setParameter("bookingId",generatedId);
			RoomBooking createdbooking=createdbookingqry.getSingleResult();
			em.getTransaction().begin();
			Iterator<RoomBookingDetails> iterator1=bookingdetailsList.iterator();
			while(iterator1.hasNext())
			{
				RoomBookingDetails rbd=(RoomBookingDetails)iterator1.next();
				double datediff = DateDifferent(rbd.getFromDate(),rbd.getToDate());
				double taxpercentage = rbd.getTaxRate();
				double roomprice = rbd.getRoomRate()*datediff;
				double discamt=roomprice*discper;
				double taxamount=(roomprice-discamt)*(taxpercentage/100);
				RoomBooking roombooking = new RoomBooking();
				roombooking.setId(createdbooking.getId());
				rbd.setBookingId(roombooking);
				rbd.setBookingNo(createdbooking.getBookingNo());
				rbd.setIsCancelled("N");
				rbd.setBookingDate(new Date());
				rbd.setCheckInTime(chkInOut.getCheckIn());
				rbd.setCheckOutTime(chkInOut.getCheckOut());
				rbd.setRoomTotal(roomprice);
				rbd.setTaxAmt(taxamount);
				rbd.setDiscPer(discper);
				rbd.setDiscAmt(discamt);
				rbd.setNetTotal(roomprice-discamt+taxamount);
				em.persist(rbd);
				
			}
			
			RoomBookingPayment paymentobj=new RoomBookingPayment();
			paymentobj.setBooking_id(rb.getId());
			paymentobj.setHotelId(Integer.toString(rb.getHotelId()));
			paymentobj.setAmount(Math.round(totnettotal));
			paymentobj.setPaidamount(0.0);
			paymentobj.setAmt_to_pay(Math.round(totnettotal));
			paymentobj.setIsCancelled("N");
			em.persist(paymentobj);
			
			if("Y".equalsIgnoreCase(rb.getIsCheckedIn()))
			{
				StoreCustomer storeCust=createdbooking.getCustId();
				RoomBookingGuest guest=new RoomBookingGuest();
				guest.setHotelId(createdbooking.getHotelId());
				guest.setRoomBooking(createdbooking);
				guest.setBookingNo(createdbooking.getBookingNo());
				guest.setName(storeCust.getName());
				guest.setContactNo(storeCust.getContactNo());
				guest.setEmailId(storeCust.getEmailId());
				guest.setAddress(storeCust.getAddress());
				guest.setGender(storeCust.getGender());
				guest.setDob(storeCust.getDob());
				guest.setUniqueidType(storeCust.getUniqueidType());
				guest.setUniqueidNo(storeCust.getUniqueidNo());
				guest.setType("H");
				guest.setDeleteFlag("N");
				guest.setCreatedBy(0);
				guest.setCreatedDate(new Date());
				em.persist(guest);
			}
			em.getTransaction().commit();
			bookingId=createdbooking.getBookingNo();
			em1.getTransaction().commit();
			
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot Book Room.", e);
		}
		finally {
			em.close();
			em1.close();
		}
		System.out.println("Booking Id::::: "+bookingId);
		return bookingId;
	}

	@Override
	public RoomBookingCustomer getCustomerDetailsByReserveId(String reserveId, String hotelId)
			throws DAOException {
		RoomBookingCustomer customerList=null;
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			//int id=Integer.parseInt(reserveId.substring(reserveId.length()-2, reserveId.length()));
			Query qry1 = em.createQuery("SELECT r.customerId FROM RoomBookingDetails r WHERE r.reserveId=:resId");
			qry1.setParameter("resId", reserveId);
			String s = (String) qry1.getSingleResult();
			
			Query q= em.createQuery("SELECT cust from RoomBookingCustomer cust where cust.id=:id AND cust.store_id='" + hotelId + "'");
			q.setParameter("id", Integer.parseInt(s));
			customerList = (RoomBookingCustomer) q.getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot get Customer by reserve Id.", e);
		}
		finally {
			em.close();
		}
		return customerList;
	}

	@Override
	public List<RoomBookingCustomer> getCustomerDetailsByName(String name, String hotelId, String fromDate, String toDate, String func) throws DAOException {

		List<RoomBookingCustomer> customerListFinal=new ArrayList<RoomBookingCustomer>();
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			List<RoomBookingDetails> RoomBookingDetailsobjList=new ArrayList<RoomBookingDetails>();
			if(func.equalsIgnoreCase("CI")) {
			Query q1= em.createQuery("SELECT r FROM RoomBookingDetails r WHERE r.hotelId=:hotelId"
					+ " and r.isCheckIn='N' AND r.fromDate>='" + fromDate + "'"
					+ " AND r.fromDate<='" + toDate + "' and r.isCancelled='N'");
			q1.setParameter("hotelId", Integer.parseInt(hotelId));
			RoomBookingDetailsobjList =  q1.getResultList();
			}else if(func.equalsIgnoreCase("CO")) {
				Query q1= em.createQuery("SELECT r FROM RoomBookingDetails r WHERE r.hotelId=:hotelId"
						+ " and r.isCheckIn='Y' and r.isCheckOut='N' AND r.fromDate>='" + fromDate + "'"
						+ " AND r.fromDate<='" + toDate + "' and r.isCancelled='N'");
				q1.setParameter("hotelId", Integer.parseInt(hotelId));
				RoomBookingDetailsobjList =  q1.getResultList();
			}
			
			String new_name = name+"%";
			for(int i=0;i<RoomBookingDetailsobjList.size();i++) {
				Integer custId=RoomBookingDetailsobjList.get(i).getCustomerId();
				Query q= em.createQuery("SELECT cust FROM RoomBookingCustomer cust WHERE cust.id=:custId");
				q.setParameter("custId",custId);
				RoomBookingCustomer customer = (RoomBookingCustomer) q.getSingleResult();
				if(customer.getName().substring(0, name.length()).equalsIgnoreCase(name)) {
					customerListFinal.add(customer);
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot get Customer by Name.", e);
		}
		finally {
			em.close();
		}
		return customerListFinal;
	}

	@Override
	public List<RoomBookingCustomer> getCustomerDetailsByPhoneNo(String phoneNo, String hotelId, String fromDate, String toDate, String func) throws DAOException {

		List<RoomBookingCustomer> customerListFinal=new ArrayList<RoomBookingCustomer>();
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			List<RoomBookingDetails> RoomBookingDetailsobjList=new ArrayList<RoomBookingDetails>();
			if(func.equalsIgnoreCase("CI")) {
			Query q1= em.createQuery("SELECT r FROM RoomBookingDetails r WHERE r.hotelId=:hotelId"
					+ " and r.isCheckIn='N' AND r.fromDate>='" + fromDate + "'"
					+ " AND r.fromDate<='" + toDate + "' and r.isCancelled='N'");
			q1.setParameter("hotelId", Integer.parseInt(hotelId));
			RoomBookingDetailsobjList =  q1.getResultList();
			}else if(func.equalsIgnoreCase("CO")) {
				Query q1= em.createQuery("SELECT r FROM RoomBookingDetails r WHERE r.hotelId=:hotelId"
						+ " and r.isCheckIn='Y' and r.isCheckOut='N' AND r.fromDate>='" + fromDate + "'"
						+ " AND r.fromDate<='" + toDate + "' and r.isCancelled='N'");
				q1.setParameter("hotelId", Integer.parseInt(hotelId));
				RoomBookingDetailsobjList =  q1.getResultList();
			}
			for(int i=0;i<RoomBookingDetailsobjList.size();i++) {
				Integer custId=RoomBookingDetailsobjList.get(i).getCustomerId();
				//cust.name LIKE '"+new_name+"' and 
				Query q= em.createQuery("SELECT cust FROM RoomBookingCustomer cust WHERE cust.id=:custId");
				q.setParameter("custId", custId);
				RoomBookingCustomer customer = (RoomBookingCustomer) q.getSingleResult();
				if(customer.getPhone().substring(0, phoneNo.length()).equalsIgnoreCase(phoneNo)) {
					customerListFinal.add(customer);
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot get Customer by reserve Id.", e);
		}
		finally {
			em.close();
		}
		return customerListFinal;
	}

	@Override
	public List<RoomBookingInfo> getReserveIdListByReserveId(String reserveId, String hotelId, String fromDate, String toDate) throws DAOException {

		List<RoomBookingInfo> roomBookingInfoList=new ArrayList<RoomBookingInfo>();
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			List<String> RoomBookingDetailsList = null;
			String reserve_Id = reserveId+"%";
			
			Query q= em.createQuery("SELECT distinct(r.reserveId) FROM RoomBookingDetails r WHERE r.reserveId LIKE '"+reserve_Id+"' and r.hotelId=:hotelId"
					+ " and r.isCheckIn='N' AND r.fromDate>='" + fromDate + "'"
					+ " AND r.fromDate<='" + toDate + "' and r.isCancelled='N'");
			q.setParameter("hotelId", Integer.parseInt(hotelId));
			
			RoomBookingDetailsList = q.getResultList();
			for(int i=0;i<RoomBookingDetailsList.size();i++) {
				RoomBookingInfo rbi = new RoomBookingInfo();
				rbi.setReserveId(RoomBookingDetailsList.get(i));
				roomBookingInfoList.add(rbi);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot get ReserveIdList by reserveId.", e);
		}
		finally {
			em.close();
		}
		return roomBookingInfoList;
	}
	
	//this is used for check in page customer auto complete search
	@Override
	public List<RoomBooking> getAllReserveIdByFromDateToDateCustId(String fromDate, String toDate, String custId,
			String hotelId) throws DAOException {
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		List<RoomBooking> roomBookingList = new ArrayList<RoomBooking>();
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			
			//Date dateFrom=format.parse(fromDate);
			//Date dateTo=format.parse(toDate);
			TypedQuery<RoomBooking> qry=em.createQuery("SELECT r FROM RoomBooking r WHERE r.hotelId=:hotelId and r.checkInDate between :fromDate and :toDate and r.custId.id=:custId and r.isCheckedIn='N' and r.isCancelled='N'",RoomBooking.class);
			qry.setParameter("hotelId", Integer.parseInt(hotelId));
			qry.setParameter("fromDate", fromDate);
			qry.setParameter("toDate", toDate);
			qry.setParameter("custId", Integer.parseInt(custId));
			roomBookingList=qry.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot fetch any result.", e);
		}
		finally {
			em.close();
		}
		return roomBookingList;
	}

	@Override
	public List<RoomBookingInfo> getAllReserveDetailsByReserveId(String reserveId, String hotelId) throws DAOException {
		List<RoomBookingInfo> roombookinginfoList=new ArrayList<RoomBookingInfo>();
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			RoomBookingInfo roombookinginfo = new RoomBookingInfo();
			List<RoomBookingDetails> RoomBookingDetails = null;
			List<Room> room = new ArrayList<Room>();
			RoomBookingCustomer roomBookingCustomer = null;
			RoomBookingPayment roomBookingPayment = null; 
			
			Query q = em.createQuery("Select RU from RoomBookingDetails RU WHERE "
					+ "RU.hotelId=:hotelId and RU.reserveId='"+reserveId+"' AND RU.isCheckIn='N'");
			q.setParameter("hotelId", Integer.parseInt(hotelId));
			RoomBookingDetails =  q.getResultList();
			
			Query qry = em.createQuery("Select c from RoomBookingCustomer c where id=:custId");
			qry.setParameter("custId", RoomBookingDetails.get(0).getCustomerId());
			roomBookingCustomer = (RoomBookingCustomer) qry.getSingleResult();
			for (int i=0;i<RoomBookingDetails.size();i++) {
				Room roomobj = null;
				int rid = RoomBookingDetails.get(i).getRoomId().getId();
				Query qry1 = em.createQuery("SELECT r FROM Room r WHERE r.id=:rid");
				qry1.setParameter("rid", rid);
				roomobj =  (Room) qry1.getSingleResult();
				roomobj.setRoomPrice(RoomBookingDetails.get(i).getRoomRate());
				room.add(roomobj);
			}
			
			Query qry1 = em.createQuery("Select p from RoomBookingPayment p where p.reserveId='"+reserveId+"' AND p.hotelId='"+hotelId+"'");
			roomBookingPayment = (RoomBookingPayment) qry1.getSingleResult();
			roombookinginfo.setPayment(roomBookingPayment);
			roombookinginfo.setRoomid(room);
			roombookinginfo.setReserveId(reserveId);
			roombookinginfo.setCustomer(roomBookingCustomer);
			roombookinginfo.setFromDate(RoomBookingDetails.get(0).getFromDate());
			roombookinginfo.setToDate(RoomBookingDetails.get(0).getToDate());
			roombookinginfo.setIsCheckIn(RoomBookingDetails.get(0).getIsCheckIn());
			roombookinginfo.setIsCheckOut(RoomBookingDetails.get(0).getIsCheckOut());
			roombookinginfoList.add(roombookinginfo);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot fetch any result.", e);
		}
		finally {
			em.close();
		}
		return roombookinginfoList;
	}

	//this is used for check out page search button and page loading
	@Override
	public List<RoomBooking> getAllCheckIn(String fromDate, String toDate, String hotelId,String bookingNo) throws DAOException {
		List<RoomBooking> roomBookingList = new ArrayList<RoomBooking>();
		List<RoomBooking> roomBookingListNew = new ArrayList<RoomBooking>();
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			if("0".equalsIgnoreCase(bookingNo))
			{
			TypedQuery<RoomBooking> qry=em.createQuery("SELECT r FROM RoomBooking r WHERE r.hotelId=:hotelId and r.checkInDate between :fromDate and :toDate and r.isCheckedIn='Y' and r.isCheckedOut='N' and r.isCancelled='N'",RoomBooking.class);
			qry.setParameter("hotelId", Integer.parseInt(hotelId));
			qry.setParameter("fromDate", fromDate);
			qry.setParameter("toDate", toDate);
			roomBookingList=qry.getResultList();
			}else
			{
				TypedQuery<RoomBooking> qry=em.createQuery("SELECT r FROM RoomBooking r WHERE r.hotelId=:hotelId and r.checkInDate between :fromDate and :toDate and r.bookingNo=:bookingNo and r.isCheckedIn='Y' and r.isCheckedOut='N' and r.isCancelled='N'",RoomBooking.class);
				qry.setParameter("hotelId", Integer.parseInt(hotelId));
				qry.setParameter("fromDate", fromDate);
				qry.setParameter("toDate", toDate);
				qry.setParameter("bookingNo", bookingNo);
				roomBookingList=qry.getResultList();
			}

			Iterator<RoomBooking> iterator=roomBookingList.iterator();
			while(iterator.hasNext())
			{
				RoomBooking rb=(RoomBooking)iterator.next();
				TypedQuery<RoomBookingPayment> q1 = em.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.booking_id=:bookingId AND r.hotelId=:hotelId",RoomBookingPayment.class);
				q1.setParameter("bookingId",rb.getId());
				q1.setParameter("hotelId",Integer.toString(rb.getHotelId()));
				List<RoomBookingPayment> roomBookingPaymentlist = q1.getResultList();
				rb.setPayment(roomBookingPaymentlist);
				roomBookingListNew.add(rb);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot fetch any result.", e);
		}finally {
			em.close();
		}
		return roomBookingListNew;
	}

	//this is used for check out page customer auto complete search
	@Override
	public List<RoomBooking> getAllBookingIdByCustId(String fromDate, String toDate, String hotelId, String custId)
			throws DAOException {
		List<RoomBooking> roomBookingReturnList=new ArrayList<RoomBooking>();
		List<RoomBooking> roomBookingReturnListNew=new ArrayList<RoomBooking>();
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			TypedQuery<RoomBooking> qry=em.createQuery("SELECT r FROM RoomBooking r WHERE r.hotelId=:hotelId and r.checkInDate between :fromDate and :toDate and r.custId.id=:custId and r.isCheckedIn='Y' and r.isCheckedOut='N' and r.isCancelled='N'",RoomBooking.class);
			qry.setParameter("hotelId", Integer.parseInt(hotelId));
			qry.setParameter("fromDate", fromDate);
			qry.setParameter("toDate", toDate);
			qry.setParameter("custId", Integer.parseInt(custId));
			roomBookingReturnList=qry.getResultList();
			Iterator<RoomBooking> iterator=roomBookingReturnList.iterator();
			while(iterator.hasNext())
			{
				RoomBooking rb=(RoomBooking)iterator.next();
				TypedQuery<RoomBookingPayment> q1 = em.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.booking_id=:bookingId AND r.hotelId=:hotelId",RoomBookingPayment.class);
				q1.setParameter("bookingId",rb.getId());
				q1.setParameter("hotelId",Integer.toString(rb.getHotelId()));
				List<RoomBookingPayment> roomBookingPaymentlist = q1.getResultList();
				rb.setPayment(roomBookingPaymentlist);
				roomBookingReturnListNew.add(rb);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot fetch any result.", e);
		}finally {
			em.close();
		}
		return roomBookingReturnListNew;
	}

	@Override
	public List<RoomBooking> getAllBookingDetailsByBookingId(String bookingId, String hotelId) throws DAOException {
		List<RoomBooking> roombookingList=new ArrayList<RoomBooking>();
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			
			TypedQuery<RoomBooking> qry=em.createQuery("SELECT r FROM RoomBooking r WHERE r.hotelId=:hotelId and r.id=:bookingId and r.isCancelled='N'",RoomBooking.class);
			qry.setParameter("hotelId", Integer.parseInt(hotelId));
			qry.setParameter("bookingId", Integer.parseInt(bookingId));
			roombookingList=qry.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot fetch any result.", e);
		}
		finally {
			em.close();
		}
		return roombookingList;
	}
	
	@Override
	public List<RoomBooking> getAllBookingDetailsByBookingIdForBill(String bookingId, String hotelId) throws DAOException {
		List<RoomBooking> roombookingList=new ArrayList<RoomBooking>();
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			TypedQuery<RoomBooking> qry=em.createQuery("SELECT r FROM RoomBooking r WHERE r.hotelId=:hotelId and r.id=:bookingId and r.isCancelled='N'",RoomBooking.class);
			qry.setParameter("hotelId", Integer.parseInt(hotelId));
			qry.setParameter("bookingId", Integer.parseInt(bookingId));
			RoomBooking rb=qry.getSingleResult();
			
			TypedQuery<RoomBookingPayment> q1 = em.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.booking_id=:bookingId AND r.hotelId=:hotelId",RoomBookingPayment.class);
			q1.setParameter("bookingId",Integer.parseInt(bookingId));
			q1.setParameter("hotelId",hotelId);
			List<RoomBookingPayment> roomBookingPaymentlist = q1.getResultList();
			rb.setPayment(roomBookingPaymentlist);
			roombookingList.add(rb);
			em.getTransaction().commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot fetch any result.", e);
		}
		finally {
			em.close();
		}
		return roombookingList;
	}

	@Override
	public List<RoomBooking> getAllBookingByBookingId(String bookingId, String hotelId, String fromDate, String toDate) throws DAOException {
		List<RoomBooking> bookingList=null;
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			List<String> RoomBookingDetailsList = null;
			String booking_Id = bookingId+"%";
			
			Query q= em.createQuery("SELECT r FROM RoomBooking r WHERE r.id LIKE '"+booking_Id+"' and r.hotelId=:hotelId"
					+ " and r.isCheckedOut='N' AND r.checkInDate>='" + fromDate + "'"
					+ " AND r.checkInDate<='" + toDate + "'");
			q.setParameter("hotelId", Integer.parseInt(hotelId));
			
			bookingList = q.getResultList();
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot get booking by Id.", e);
		}
		finally {
			em.close();
		}
		return bookingList;
	}

	@Override
	public String checkOut(RoomBooking rb) throws DAOException {
		
		String status="failure";
		EntityManager em = null;
		EntityManager em1 = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em1 = entityManagerFactory.createEntityManager();
		try {
			
			if("F".equalsIgnoreCase(rb.getCheckoutType()))
			{
				TypedQuery<RoomBooking> qry=em.createQuery("Select rb from RoomBooking rb where rb.id=:bookingId and rb.hotelId=:hotelId",RoomBooking.class);
				qry.setParameter("bookingId",rb.getId());
				qry.setParameter("hotelId",rb.getHotelId());
				RoomBooking booking=qry.getSingleResult();
				booking.setIsCheckedOut("Y");
				String s = new SimpleDateFormat("HH:mm").format(new Date());
				booking.setActualCheckoutTime(s);
				List<RoomBookingDetails> bookingdetailsList=booking.getBookingDetail();
				Iterator<RoomBookingDetails> iterator=bookingdetailsList.iterator();
				while(iterator.hasNext())
				{
					RoomBookingDetails rbd=(RoomBookingDetails)iterator.next();
					rbd.setIsCheckOut("Y");
				}
			}
			else
			{
				String s = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				//String s1 = new SimpleDateFormat("HH:mm").format(new Date());
				List<RoomBookingDetails> bookingdetails=rb.getBookingDetail();
				Iterator<RoomBookingDetails> iterator1=bookingdetails.iterator();
				while(iterator1.hasNext())
				{
					RoomBookingDetails rbd1=(RoomBookingDetails)iterator1.next();
					RoomBookingDetails details=em.find(RoomBookingDetails.class, rbd1.getId());
					details.setIsCheckOut("Y");
					details.setToDate(s);
				}
			}
			
			em.getTransaction().commit();
			if("P".equalsIgnoreCase(rb.getTakePType()))
			{
				em1.getTransaction().begin();
				TypedQuery<RoomBooking> qry1=em1.createQuery("Select rb from RoomBooking rb where rb.id=:bookingId and rb.hotelId=:hotelId",RoomBooking.class);
				qry1.setParameter("bookingId",rb.getId());
				qry1.setParameter("hotelId",rb.getHotelId());
				RoomBooking booking1=qry1.getSingleResult();
				double final_amount = 0.0;
				double tottaxamount = 0.0;
				double totservnetamt = 0.0;
				double totservamt=0.0;
				double totservtaxamt=0.0;
				double totdiscamt=0.0;
				double totservdiscamt=0.0;
				List<RoomBookingDetails> bookingdetailsList1=booking1.getBookingDetail();
				Iterator<RoomBookingDetails> iterator3=bookingdetailsList1.iterator();
				while(iterator3.hasNext())
				{
					RoomBookingDetails rbd2=(RoomBookingDetails)iterator3.next();
					double datediff = DateDifferent(rbd2.getFromDate(),rbd2.getToDate());
					double taxpercentage = rbd2.getTaxRate();
					double roomprice = rbd2.getRoomRate()*datediff;
					double discamt=roomprice*rbd2.getDiscPer()/100;
					double taxamount=(roomprice-discamt)*(taxpercentage/100);
					final_amount+=roomprice;
					tottaxamount+=taxamount;
					totdiscamt+=discamt;
					rbd2.setRoomTotal(roomprice);
					rbd2.setTaxAmt(taxamount);
					rbd2.setDiscPer(rbd2.getDiscPer());
					rbd2.setDiscAmt(discamt);
					rbd2.setNetTotal(roomprice+taxamount);
					em1.merge(rbd2);
				}
				
				//for room service
				List<RoomBookingServices> servicedetailsList=booking1.getBookingServices();
				Iterator<RoomBookingServices> serviterator=servicedetailsList.iterator();
				while(serviterator.hasNext())
				{
					double servtotal=0.0;
					double taxamt=0.0;
					double netamt=0.0;
					double discamt=0.0;
					RoomBookingServices rbs=(RoomBookingServices)serviterator.next();
					servtotal=rbs.getGrossAmount();
					discamt=servtotal*rbs.getDiscPer()/100;
					taxamt=(servtotal-discamt)*rbs.getTaxRate()/100;
					netamt=servtotal-discamt+taxamt;
					
					rbs.setTaxAmount(taxamt);
					rbs.setDiscPer(rbs.getDiscPer());
					rbs.setDiscAmount(0.00);
					rbs.setNetAmount(netamt);
					totservnetamt+=netamt;
					totservamt+=servtotal;
					totservtaxamt+=taxamt;
					totservdiscamt+=discamt;
					em1.merge(rbs);
				}
				
				double total = final_amount-totdiscamt+tottaxamount;
				//booking1.setCheckoutDate(rb.getCheckoutDate());
				booking1.setGrossAmt(final_amount);
				booking1.setDiscPer(0.0);
				booking1.setDiscAmt(0.0);
				booking1.setTaxAmt(tottaxamount);
				booking1.setRoundOff(Math.round(total)-total);
				booking1.setNetAmt(Math.round(total));
				
				booking1.setRoomServiceGross(totservamt);
				booking1.setRoomServiceDiscount(totservdiscamt);
				booking1.setRoomServiceTax(totservtaxamt);
				em1.merge(booking1);
				TypedQuery<RoomBookingPayment> q1 = em1.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.booking_id=:bookingId AND r.hotelId=:hotelId",RoomBookingPayment.class);
				q1.setParameter("bookingId",rb.getId());
				q1.setParameter("hotelId",Integer.toString(rb.getHotelId()));
				List<RoomBookingPayment> roomBookingPaymentlist = q1.getResultList();
				Iterator<RoomBookingPayment> iterator4=roomBookingPaymentlist.iterator();
				double paidamt=0;
				double payableamt=total+totservnetamt;
				while(iterator4.hasNext())
				{
					RoomBookingPayment rbp=(RoomBookingPayment)iterator4.next();
					rbp.setAmount(Math.round(payableamt));
					paidamt+=rbp.getPaidamount();
					rbp.setAmt_to_pay(Math.round(payableamt)-paidamt);
					em1.merge(rbp);
				}
				em1.getTransaction().commit();
			}
			
			
			status="success";
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Check Out unsuccessfull.", e);
		}finally {
			em.close();
			em1.close();
		}
		return status;
	}
	
	@Override
	public String checkIn(RoomBooking rb) throws DAOException {
		String status="failure";
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			TypedQuery<RoomBooking> qry=em.createQuery("Select rb from RoomBooking rb where rb.id=:bookingId and rb.hotelId=:hotelId",RoomBooking.class);
			qry.setParameter("bookingId",rb.getId());
			qry.setParameter("hotelId",rb.getHotelId());
			RoomBooking booking=qry.getSingleResult();
			StoreCustomer storeCust=booking.getCustId();
			booking.setIsCheckedIn("Y");
			String s = new SimpleDateFormat("HH:mm").format(new Date());
			booking.setActualCheckinTime(s);
			List<RoomBookingDetails> bookingdetailsList=booking.getBookingDetail();
			Iterator<RoomBookingDetails> iterator=bookingdetailsList.iterator();
			while(iterator.hasNext())
			{
				RoomBookingDetails rbd=(RoomBookingDetails)iterator.next();
				rbd.setIsCheckIn("Y");
			}
			RoomBookingGuest guest=new RoomBookingGuest();
			guest.setHotelId(booking.getHotelId());
			guest.setRoomBooking(booking);
			guest.setBookingNo(booking.getBookingNo());
			guest.setName(storeCust.getName());
			guest.setContactNo(storeCust.getContactNo());
			guest.setEmailId(storeCust.getEmailId());
			guest.setAddress(storeCust.getAddress());
			guest.setGender(storeCust.getGender());
			guest.setDob(storeCust.getDob());
			guest.setUniqueidType(storeCust.getUniqueidType());
			guest.setUniqueidNo(storeCust.getUniqueidNo());
			guest.setType("H");
			guest.setDeleteFlag("N");
			guest.setCreatedBy(0);
			guest.setCreatedDate(new Date());
			em.persist(guest);
			em.getTransaction().commit();
			status="success";
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Check in unsuccessfull.", e);
		}finally {
			em.close();
		}
		return status;
	}
	@Override
	public String extendcheckOutDate(RoomBooking rb) throws DAOException {
		
		String status="failure";
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			double final_amount = 0.0;
			double tottaxamount = 0.0;
			double totservnetamt = 0.0;
			double totservamt=0.0;
			double totservtaxamt=0.0;
			double datediff = DateDifferent(rb.getCheckInDate(),rb.getCheckoutDate());
			TypedQuery<RoomBooking> qry=em.createQuery("Select rb from RoomBooking rb where rb.id=:bookingId and rb.hotelId=:hotelId",RoomBooking.class);
			qry.setParameter("bookingId",rb.getId());
			qry.setParameter("hotelId",rb.getHotelId());
			RoomBooking booking=qry.getSingleResult();
			List<RoomBookingDetails> bookingdetailsList=booking.getBookingDetail();
			Iterator<RoomBookingDetails> iterator=bookingdetailsList.iterator();
			while(iterator.hasNext())
			{
				RoomBookingDetails rbd=(RoomBookingDetails)iterator.next();
				if("N".equalsIgnoreCase(rbd.getIsCheckOut()))
				{
				datediff = DateDifferent(rb.getCheckInDate(),rb.getCheckoutDate());
				double taxpercentage = rbd.getTaxRate();
				double roomprice = rbd.getRoomRate()*datediff;
				double taxamount=roomprice*(taxpercentage/100);
				final_amount+=roomprice;
				tottaxamount+=taxamount;
				rbd.setToDate(rb.getCheckoutDate());
				rbd.setRoomTotal(roomprice);
				rbd.setTaxAmt(taxamount);
				rbd.setDiscPer(0.0);
				rbd.setDiscAmt(0.0);
				rbd.setNetTotal(roomprice+taxamount);
				}
				else
				{
					datediff = DateDifferent(rbd.getFromDate(),rbd.getToDate());
					double taxpercentage = rbd.getTaxRate();
					double roomprice = rbd.getRoomRate()*datediff;
					double taxamount=roomprice*(taxpercentage/100);
					final_amount+=roomprice;
					tottaxamount+=taxamount;
					rbd.setRoomTotal(roomprice);
					rbd.setTaxAmt(taxamount);
					rbd.setDiscPer(0.0);
					rbd.setDiscAmt(0.0);
					rbd.setNetTotal(roomprice+taxamount);
				}
				
			}
			
			//for room service
			List<RoomBookingServices> servicedetailsList=booking.getBookingServices();
			Iterator<RoomBookingServices> serviterator=servicedetailsList.iterator();
			while(serviterator.hasNext())
			{
				double servtotal=0.0;
				double taxamt=0.0;
				double netamt=0.0;
				RoomBookingServices rbs=(RoomBookingServices)serviterator.next();
				servtotal=rbs.getGrossAmount();
				taxamt=servtotal*rbs.getTaxRate()/100;
				netamt=servtotal+taxamt;
				rbs.setTaxAmount(taxamt);
				rbs.setDiscPer(0.00);
				rbs.setDiscAmount(0.00);
				rbs.setNetAmount(netamt);
				totservnetamt+=netamt;
				totservamt+=servtotal;
				totservtaxamt+=taxamt;
			}
			
			double total = final_amount+tottaxamount;
			booking.setCheckoutDate(rb.getCheckoutDate());
			booking.setGrossAmt(final_amount);
			booking.setDiscPer(0.0);
			booking.setDiscAmt(0.0);
			booking.setTaxAmt(tottaxamount);
			booking.setRoundOff(Math.round(total)-total);
			booking.setNetAmt(Math.round(total));
			booking.setIsTaxable("Y");
			booking.setRoomServiceGross(totservamt);
			booking.setRoomServiceDiscount(0.00);
			booking.setRoomServiceTax(totservtaxamt);
			
			TypedQuery<RoomBookingPayment> q1 = em.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.booking_id=:bookingId AND r.hotelId=:hotelId",RoomBookingPayment.class);
			q1.setParameter("bookingId",rb.getId());
			q1.setParameter("hotelId",Integer.toString(rb.getHotelId()));
			List<RoomBookingPayment> roomBookingPaymentlist = q1.getResultList();
			Iterator<RoomBookingPayment> iterator1=roomBookingPaymentlist.iterator();
			double paidamt=0;
			double payableamt=total+totservnetamt;
			while(iterator1.hasNext())
			{
				RoomBookingPayment rbp=(RoomBookingPayment)iterator1.next();
				rbp.setAmount(Math.round(payableamt));
				paidamt+=rbp.getPaidamount();
				rbp.setAmt_to_pay(Math.round(payableamt)-paidamt);	
			}
			em.getTransaction().commit();
			status="success";
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("extendcheckOutDate unsuccessfull.", e);
		}finally {
			em.close();
		}
		return status;
	}
	
	
	@Override
	public String addDiscount(RoomBooking rb) throws DAOException {
		
		String status="failure";
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			double totroomamt=0.0;
			double totdiscamt=0.0;
			double tottaxamt=0.0;
			double totnetamt=0.0;
			double totservamt=0.0;
			double totservdiscamt=0.0;
			double totservtaxamt=0.0;
			double totservnetamt=0.0;
			TypedQuery<RoomBooking> qry=em.createQuery("Select rb from RoomBooking rb where rb.id=:bookingId and rb.hotelId=:hotelId",RoomBooking.class);
			qry.setParameter("bookingId",rb.getId());
			qry.setParameter("hotelId",rb.getHotelId());
			RoomBooking booking=qry.getSingleResult();
			List<RoomBookingDetails> bookingdetailsList=booking.getBookingDetail();
			Iterator<RoomBookingDetails> iterator=bookingdetailsList.iterator();
			while(iterator.hasNext())
			{
				double roomtotal=0.0;
				double discamt=0.0;
				double taxamt=0.0;
				double netamt=0.0;
				RoomBookingDetails rbd=(RoomBookingDetails)iterator.next();
				roomtotal=rbd.getRoomTotal();
				discamt=roomtotal*rb.getDiscPer()/100;
				taxamt=(roomtotal-discamt)*rbd.getTaxRate()/100;
				netamt=roomtotal-discamt+taxamt;
				rbd.setTaxAmt(taxamt);
				rbd.setDiscPer(rb.getDiscPer());
				rbd.setDiscAmt(discamt);
				rbd.setNetTotal(netamt);
				totroomamt+=roomtotal;
				totdiscamt+=discamt;
				tottaxamt+=taxamt;
				totnetamt+=netamt;
			}
			//for room services
			List<RoomBookingServices> servicedetailsList=booking.getBookingServices();
			Iterator<RoomBookingServices> serviterator=servicedetailsList.iterator();
			while(serviterator.hasNext())
			{
				double servtotal=0.0;
				double discamt=0.0;
				double taxamt=0.0;
				double netamt=0.0;
				RoomBookingServices rbs=(RoomBookingServices)serviterator.next();
				servtotal=rbs.getGrossAmount();
				discamt=servtotal*rb.getDiscPer()/100;
				taxamt=(servtotal-discamt)*rbs.getTaxRate()/100;
				netamt=servtotal-discamt+taxamt;
				rbs.setTaxAmount(taxamt);
				rbs.setDiscPer(rb.getDiscPer());
				rbs.setDiscAmount(discamt);
				rbs.setNetAmount(netamt);
				totservamt+=servtotal;
				totservdiscamt+=discamt;
				totservtaxamt+=taxamt;
				totservnetamt+=netamt;
			}
			
			booking.setGrossAmt(totroomamt);
			booking.setDiscPer(rb.getDiscPer());
			booking.setDiscAmt(totdiscamt);
			booking.setTaxAmt(tottaxamt);
			booking.setRoundOff(Math.round(totnetamt)-totnetamt);
			booking.setNetAmt(Math.round(totnetamt));
			
			booking.setRoomServiceGross(totservamt);
			booking.setRoomServiceDiscount(totservdiscamt);
			booking.setRoomServiceTax(totservtaxamt);
			
			TypedQuery<RoomBookingPayment> q1 = em.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.booking_id=:bookingId AND r.hotelId=:hotelId",RoomBookingPayment.class);
			q1.setParameter("bookingId",rb.getId());
			q1.setParameter("hotelId",Integer.toString(rb.getHotelId()));
			List<RoomBookingPayment> roomBookingPaymentlist = q1.getResultList();
			Iterator<RoomBookingPayment> iterator1=roomBookingPaymentlist.iterator();
			double paidamt=0;
			double payableamt=totnetamt+totservnetamt;
			while(iterator1.hasNext())
			{
				RoomBookingPayment rbp=(RoomBookingPayment)iterator1.next();
				rbp.setAmount(Math.round(payableamt));
				paidamt+=rbp.getPaidamount();
				rbp.setAmt_to_pay(Math.round(payableamt)-paidamt);	
			}
			em.getTransaction().commit();
			status="success";
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("addDiscount unsuccessfull.", e);
		}finally {
			em.close();
		}
		return status;
	}
	
	@Override
	public String changeRoom(String reserveId,String bookingId,String hotelId,String fromRoom,String toRoom,String roomRate,String taxId,String taxPer) throws DAOException {
		
		String status="failure";
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			
			int bookingID = Integer.parseInt(bookingId);
			int hotelID = Integer.parseInt(hotelId);
			int fromRoomId = Integer.parseInt(fromRoom);
			int toRoomId = Integer.parseInt(toRoom);
			double final_amount = 0.0;
			double tottaxamount = 0.0;
			double totservnetamt = 0.0;
			double totservamt=0.0;
			double totservtaxamt=0.0;
			TypedQuery<RoomBookingDetails> q1=em.createQuery("SELECT R FROM RoomBookingDetails R where R.hotelId=:hotelId and R.bookingId.id=:bookingID",RoomBookingDetails.class);
			q1.setParameter("hotelId", hotelID);
			q1.setParameter("bookingID", bookingID);
			List<RoomBookingDetails> RoomBookingDetails = q1.getResultList();
			for(int i=0;i<RoomBookingDetails.size();i++) {
				
				double datediff = DateDifferent(RoomBookingDetails.get(i).getFromDate(),RoomBookingDetails.get(i).getToDate());
				double roomprice=0.0;
				double taxamount=0.0;
				double newroomprice=0.0; 
				double newroomtax=0.0;
				if(RoomBookingDetails.get(i).getRoomId().getId()==fromRoomId)
				{
					double taxpercentage = Double.parseDouble(taxPer);
					newroomprice = Double.parseDouble(roomRate)*datediff;
					newroomtax=newroomprice*(taxpercentage/100);
					Room r=new Room();
					r.setId(toRoomId);
					RoomBookingDetails.get(i).setRoomId(r);
					RoomBookingDetails.get(i).setRoomRate(Double.parseDouble(roomRate));
					RoomBookingDetails.get(i).setRoomTotal(newroomprice);
					RoomBookingDetails.get(i).setTaxId(Integer.parseInt(taxId));
					RoomBookingDetails.get(i).setTaxRate(taxpercentage);
					RoomBookingDetails.get(i).setTaxAmt(newroomtax);
					RoomBookingDetails.get(i).setDiscPer(0.0);
					RoomBookingDetails.get(i).setDiscAmt(0.0);
					RoomBookingDetails.get(i).setNetTotal(newroomprice+newroomtax);
				}
				else
				{
					double taxpercentage = RoomBookingDetails.get(i).getTaxRate();
					roomprice = RoomBookingDetails.get(i).getRoomRate()*datediff;
					taxamount=roomprice*(taxpercentage/100);
					RoomBookingDetails.get(i).setTaxAmt(taxamount);
					RoomBookingDetails.get(i).setDiscPer(0.0);
					RoomBookingDetails.get(i).setDiscAmt(0.0);
					RoomBookingDetails.get(i).setNetTotal(roomprice+taxamount);
				}
				final_amount+=roomprice+newroomprice;
				tottaxamount+=taxamount+newroomtax;
			
			}
			
			
			
			double total = final_amount+tottaxamount;
			if(bookingID>0)
			{
				TypedQuery<RoomBooking> q2=em.createQuery("SELECT R FROM RoomBooking R where R.hotelId=:hotelId AND R.id=:bookingId",RoomBooking.class);
				q2.setParameter("hotelId", hotelID);
				q2.setParameter("bookingId", bookingID);
				RoomBooking booking= q2.getSingleResult();
				booking.setGrossAmt(final_amount);
				booking.setDiscPer(0.0);
				booking.setDiscAmt(0.0);
				booking.setTaxAmt(tottaxamount);
				booking.setRoundOff(Math.round(total)-total);
				booking.setNetAmt(Math.round(total));
				booking.setIsTaxable("Y");
				//for room service
				List<RoomBookingServices> servicedetailsList=booking.getBookingServices();
				Iterator<RoomBookingServices> serviterator=servicedetailsList.iterator();
				while(serviterator.hasNext())
				{
					double servtotal=0.0;
					double taxamt=0.0;
					double netamt=0.0;
					RoomBookingServices rbs=(RoomBookingServices)serviterator.next();
					servtotal=rbs.getGrossAmount();
					taxamt=servtotal*rbs.getTaxRate()/100;
					netamt=servtotal+taxamt;
					rbs.setTaxAmount(taxamt);
					rbs.setDiscPer(0.00);
					rbs.setDiscAmount(0.00);
					rbs.setNetAmount(netamt);
					totservnetamt+=netamt;
					totservamt+=servtotal;
					totservtaxamt+=taxamt;
				}
				
				booking.setRoomServiceGross(totservamt);
				booking.setRoomServiceDiscount(0.00);
				booking.setRoomServiceTax(totservtaxamt);
				
			}
			
			TypedQuery<RoomBookingPayment> q3 = em.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.hotelId=:hotelId  AND r.booking_id=:reserveId",RoomBookingPayment.class);
			q3.setParameter("hotelId",hotelId);
			q3.setParameter("reserveId", bookingID);
			List<RoomBookingPayment> roomBookingPayment = q3.getResultList();
			Iterator<RoomBookingPayment> iterator1=roomBookingPayment.iterator();
			double paidamt=0;
			double payableamt=total+totservnetamt;
			while(iterator1.hasNext())
			{
				RoomBookingPayment rbp=(RoomBookingPayment)iterator1.next();
				rbp.setAmount(Math.round(payableamt));
				paidamt+=rbp.getPaidamount();
				rbp.setAmt_to_pay(Math.round(payableamt)-paidamt);	
			}
			em.getTransaction().commit();
			//em.flush();
			status="success";
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("changeRoom unsuccessfull.", e);
		}finally {
			em.close();
		}
		return status;
	}
	
	@Override
	public String setNonTaxable(RoomBooking rb) throws DAOException {
		
		String status="failure";
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			double totroomamt=0.0;
			double totdiscamt=0.0;
			double tottaxamt=0.0;
			double totnetamt=0.0;
			double totservamt=0.0;
			double totservdiscamt=0.0;
			double totservtaxamt=0.0;
			double totservnetamt=0.0;
			TypedQuery<RoomBooking> qry=em.createQuery("Select rb from RoomBooking rb where rb.id=:bookingId and rb.hotelId=:hotelId",RoomBooking.class);
			qry.setParameter("bookingId",rb.getId());
			qry.setParameter("hotelId",rb.getHotelId());
			RoomBooking booking=qry.getSingleResult();
			List<RoomBookingDetails> bookingdetailsList=booking.getBookingDetail();
			Iterator<RoomBookingDetails> iterator=bookingdetailsList.iterator();
			while(iterator.hasNext())
			{
				double roomtotal=0.0;
				double discamt=0.0;
				double taxamt=0.0;
				double netamt=0.0;
				double taxarte=0.0;
				RoomBookingDetails rbd=(RoomBookingDetails)iterator.next();
				roomtotal=rbd.getRoomTotal();
				discamt=roomtotal*rbd.getDiscPer()/100;
				taxamt=(roomtotal-discamt)*taxarte/100;
				netamt=roomtotal-discamt+taxamt;
				rbd.setTaxRate(taxarte);
				rbd.setTaxAmt(taxamt);
				rbd.setDiscPer(rbd.getDiscPer());
				rbd.setDiscAmt(discamt);
				rbd.setNetTotal(netamt);
				totroomamt+=roomtotal;
				totdiscamt+=discamt;
				tottaxamt+=taxamt;
				totnetamt+=netamt;
			}
			//for room services
			List<RoomBookingServices> servicedetailsList=booking.getBookingServices();
			Iterator<RoomBookingServices> serviterator=servicedetailsList.iterator();
			while(serviterator.hasNext())
			{
				double servtotal=0.0;
				double discamt=0.0;
				double taxamt=0.0;
				double netamt=0.0;
				RoomBookingServices rbs=(RoomBookingServices)serviterator.next();
				servtotal=rbs.getGrossAmount();
				discamt=servtotal*rb.getDiscPer()/100;
				taxamt=(servtotal-discamt)*rbs.getTaxRate()/100;
				netamt=servtotal-discamt+taxamt;
				rbs.setTaxAmount(taxamt);
				rbs.setDiscPer(rb.getDiscPer());
				rbs.setDiscAmount(discamt);
				rbs.setNetAmount(netamt);
				totservamt+=servtotal;
				totservdiscamt+=discamt;
				totservtaxamt+=taxamt;
				totservnetamt+=netamt;
			}
			
			booking.setGrossAmt(totroomamt);
			booking.setDiscPer(booking.getDiscPer());
			booking.setDiscAmt(totdiscamt);
			booking.setTaxAmt(tottaxamt);
			booking.setRoundOff(Math.round(totnetamt)-totnetamt);;
			booking.setNetAmt(Math.round(totnetamt));
			booking.setIsTaxable("N");
			
			booking.setRoomServiceGross(totservamt);
			booking.setRoomServiceDiscount(totservdiscamt);
			booking.setRoomServiceTax(totservtaxamt);
			
			TypedQuery<RoomBookingPayment> q1 = em.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.booking_id=:bookingId AND r.hotelId=:hotelId",RoomBookingPayment.class);
			q1.setParameter("bookingId",rb.getId());
			q1.setParameter("hotelId",Integer.toString(rb.getHotelId()));
			List<RoomBookingPayment> roomBookingPaymentlist = q1.getResultList();
			Iterator<RoomBookingPayment> iterator1=roomBookingPaymentlist.iterator();
			double paidamt=0;
			double payableamt=totnetamt+totservnetamt;
			while(iterator1.hasNext())
			{
				RoomBookingPayment rbp=(RoomBookingPayment)iterator1.next();
				rbp.setAmount(Math.round(payableamt));
				paidamt+=rbp.getPaidamount();
				rbp.setAmt_to_pay(Math.round(payableamt)-paidamt);	
			}
			em.getTransaction().commit();
			status="success";
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("setNonTaxable unsuccessfull.", e);
		}finally {
			em.close();
		}
		return status;
	}
	
	@Override
	public List<RoomBooking> getAllCheckOut(String fromDate, String toDate, String hotelId) throws DAOException {
		List<RoomBooking> roomBookingReturnList=new ArrayList<RoomBooking>();
		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			TypedQuery<RoomBooking> qry=em.createQuery("SELECT r FROM RoomBooking r WHERE r.hotelId=:hotelId and r.checkInDate between :fromDate and :toDate and r.isCheckedIn='Y' and r.isCheckedOut='Y' and r.isCancelled='N'",RoomBooking.class);
			qry.setParameter("hotelId", Integer.parseInt(hotelId));
			qry.setParameter("fromDate", fromDate);
			qry.setParameter("toDate", toDate);
			roomBookingReturnList=qry.getResultList();
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot fetch any result.", e);
		}finally {
			em.close();
		}
		return roomBookingReturnList;
	}
	
	@Override
	public List<RoomBookingPayment> getPaymentInfoByBookingId(String bookingId) throws DAOException {
		List<RoomBookingPayment> roomBookingPaymentList=null;

		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			TypedQuery<RoomBookingPayment> q1 = em.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.booking_id=:bookingId",RoomBookingPayment.class);
			q1.setParameter("bookingId",Integer.parseInt(bookingId));
		    roomBookingPaymentList = q1.getResultList();
			em.getTransaction().commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot fetch any result.", e);
		}finally {
			em.close();
		}
		return roomBookingPaymentList;
	}
	
	//added on 05.08.2019
	@Override
	public String addRoomBookingServices(RoomBooking rb) throws DAOException {
		
		String status="failure";
		EntityManager em = null;
		EntityManager em1 = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em1 = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em1.getTransaction().begin();
		try {
			double final_amount = 0.0;
			double rbs_gross = 0.0;
			double rbs_tax = 0.0;
			double rbs_disc = 0.0;
			int booking_id=rb.getId();
			int hotelId=rb.getHotelId();
			RoomBooking rb1=em.find(RoomBooking.class,booking_id);
				try {
					Query query1 = em1.createNativeQuery("delete from rb_room_booking_services  where booking_id=? and hotel_id=?");
					query1.setParameter(1, booking_id);
					query1.setParameter(2, hotelId);
					query1.executeUpdate();
					em1.getTransaction().commit();
				}catch(Exception ex) {	
				}
			List<RoomBookingServices> bookingservicesList=rb.getBookingServices();
			Iterator<RoomBookingServices> iterator=bookingservicesList.iterator();
			while(iterator.hasNext())
			{
				RoomBookingServices rbs1=(RoomBookingServices)iterator.next();
				final_amount+=rbs1.getNetAmount();
				rbs_gross+=rbs1.getGrossAmount();
				rbs_tax+=rbs1.getTaxAmount();
				rbs_disc+=rbs1.getDiscAmount();
				em.persist(rbs1);
			}
			rb1.setRoomServiceGross(rbs_gross);
			rb1.setRoomServiceTax(rbs_tax);
			rb1.setRoomServiceDiscount(rbs_disc);
			TypedQuery<RoomBookingPayment> q1 = em.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.booking_id=:bookingId AND r.hotelId=:hotelId",RoomBookingPayment.class);
			q1.setParameter("bookingId",booking_id);
			q1.setParameter("hotelId",Integer.toString(hotelId));
			List<RoomBookingPayment> roomBookingPaymentlist = q1.getResultList();
			Iterator<RoomBookingPayment> iterator1=roomBookingPaymentlist.iterator();
			double paidamt=0;
			double totamt=rb1.getNetAmt();
			while(iterator1.hasNext())
			{
				RoomBookingPayment rbp=(RoomBookingPayment)iterator1.next();
				double tot=totamt+final_amount;
				rbp.setAmount(Math.round(tot));
				paidamt+=rbp.getPaidamount();
				rbp.setAmt_to_pay(Math.round(tot)-paidamt);
				
			}
			em.getTransaction().commit();
			status="success";
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("addRoomBookingServices unsuccessfull.", e);
		}finally {
			em.close();
			em1.close();
		}
		return status;
	}
	
	@Override
	public List<RoomBookingServices> getRoomServicesByBookingId(String bookingId,String hotelId) throws DAOException {
		List<RoomBookingServices> roomservicesList=null;

		EntityManager em = null;
		entityManagerFactory = PersistenceListener.getEntityManager();
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try {
			TypedQuery<RoomBookingServices> q1 = em.createQuery("SELECT r FROM RoomBookingServices r WHERE r.roomBooking.id=:bookingId and r.hotelId=:hotelId",RoomBookingServices.class);
			q1.setParameter("bookingId",Integer.parseInt(bookingId));
			q1.setParameter("hotelId",Integer.parseInt(hotelId));
			roomservicesList = q1.getResultList();
			em.getTransaction().commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("Cannot fetch any result.", e);
		}finally {
			em.close();
		}
		return roomservicesList;
	}
	
	//added on 25.10.2019
		@Override
		public String addRoomBookingGuest(RoomBooking rb) throws DAOException {
			
			String status="failure";
			EntityManager em = null;
			EntityManager em1 = null;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em1 = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em1.getTransaction().begin();
			try {
				int booking_id=rb.getId();
				int hotelId=rb.getHotelId();
					try {
						Query query1 = em1.createNativeQuery("delete from rb_m_guest where booking_id=? and hotel_id=? and type='N'");
						query1.setParameter(1, booking_id);
						query1.setParameter(2, hotelId);
						query1.executeUpdate();
						em1.getTransaction().commit();
					}catch(Exception ex) {	
					}
				List<RoomBookingGuest> bookingguestList=rb.getBookingGuest();
				Iterator<RoomBookingGuest> iterator=bookingguestList.iterator();
				while(iterator.hasNext())
				{
					RoomBookingGuest rbg1=(RoomBookingGuest)iterator.next();
					rbg1.setType("N");
					rbg1.setDeleteFlag("N");
					rbg1.setCreatedDate(new Date());
					em.persist(rbg1);
				}
				em.getTransaction().commit();
				status="success";
			}catch(Exception e) {
				e.printStackTrace();
				throw new DAOException("addRoomBookingGuest unsuccessfull.", e);
			}finally {
				em.close();
				em1.close();
			}
			return status;
		}
		
		@Override
		public List<RoomBookingGuest> getRoomGuestByBookingId(String bookingId,String hotelId) throws DAOException {
			List<RoomBookingGuest> roomguestList=null;

			EntityManager em = null;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			try {
				TypedQuery<RoomBookingGuest> q1 = em.createQuery("SELECT r FROM RoomBookingGuest r WHERE r.roomBooking.id=:bookingId and r.hotelId=:hotelId",RoomBookingGuest.class);
				q1.setParameter("bookingId",Integer.parseInt(bookingId));
				q1.setParameter("hotelId",Integer.parseInt(hotelId));
				roomguestList = q1.getResultList();
				em.getTransaction().commit();
				
			}catch(Exception e) {
				e.printStackTrace();
				throw new DAOException("Cannot fetch any result.", e);
			}finally {
				em.close();
			}
			return roomguestList;
		}
		
		@Override
		public String convertToCreditBooking(RoomBooking rb) throws DAOException {
			EntityManager em = null;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			String status="failure";
			try {
				TypedQuery<RoomBooking> qry=em.createQuery("Select rb from RoomBooking rb where rb.id=:bookingId and rb.hotelId=:hotelId",RoomBooking.class);
				qry.setParameter("bookingId",rb.getId());
				qry.setParameter("hotelId",rb.getHotelId());
				RoomBooking booking=qry.getSingleResult();
				booking.setCreditFlag("Y");
				em.getTransaction().commit();
				
				status="success";
			}catch(Exception e) {
				logger.error("Error while converting credit Booking. ",e);
				e.printStackTrace();
			}
			finally {
				em.close();
			}
			return status;
		}
		
		@Override
		public List<RoomBooking> getCreditBookingByCustomerId(String storeId,
				String custId) throws DAOException {

			List<RoomBooking> bookingList = null;
			List<Integer> bookingIdListTemp = new ArrayList<Integer>();
			List<RoomBookingPayment> paymentList = null;
			EntityManager em = null;
			entityManagerFactory = PersistenceListener.getEntityManager();
			int custId1 = Integer.parseInt(custId);
			int storeId1 = Integer.parseInt(storeId);
			try {
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				TypedQuery<RoomBooking> qry = em
						.createQuery("SELECT rb FROM RoomBooking rb WHERE rb.hotelId=:storeId and rb.custId.id=:storeCustomerId and rb.isCancelled='N' and rb.creditFlag='Y'",
								RoomBooking.class);
				qry.setParameter("storeId", storeId1);
				qry.setParameter("storeCustomerId", custId1);
				bookingList = qry.getResultList();
				if (bookingList != null && bookingList.size() > 0) {
					List<Integer> bookingIdList = new ArrayList<Integer>();
					Iterator<RoomBooking> iterator = bookingList.iterator();
					while (iterator.hasNext()) {
						RoomBooking bookingMaster = (RoomBooking) iterator.next();
						bookingIdList.add(bookingMaster.getId());
					}
					
					// New method
					paymentList = getPaymentInfoByBookingList(bookingIdList);
					Iterator<Integer> iterator2 = bookingIdList.iterator();
					while (iterator2.hasNext()) {
						List<RoomBookingPayment> paymentListTemp = new ArrayList<RoomBookingPayment>();
						Integer orderId = (Integer) iterator2.next();
						Iterator<RoomBookingPayment> paymentItr = paymentList.iterator();
						while (paymentItr.hasNext()) {
							RoomBookingPayment payment = (RoomBookingPayment) paymentItr.next();
							int orderIdPayment = payment.getBooking_id();
							if (orderId == orderIdPayment) {
								paymentListTemp.add(payment);
								 double amtToPay = payment.getAmt_to_pay();
								 if (amtToPay == 0.00) {
									 bookingIdListTemp.add(orderId);
								 }
							}
						}
					}
						for (int j = 0; j < bookingList.size(); j++) {
							RoomBooking bookingMaster = bookingList.get(j);
							int bookingid = bookingMaster.getId();
							if (bookingIdListTemp.contains(bookingid)) {
								bookingList.remove(j);
							}
						}
				}

				em.getTransaction().commit();

			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Check data to be shown", e);
			} finally {
	      if(em != null) em.close();
			}
			return bookingList;
		}
		
		@Override
		public String addMoreRoom(RoomBooking rb) throws DAOException {
			
			String status="failure";
			EntityManager em = null;
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			try {
				double final_amount = 0.0;
				double tottaxamount = 0.0;
				double totservnetamt = 0.0;
				double totservamt=0.0;
				double totservtaxamt=0.0;
				double datediff = DateDifferent(rb.getCheckInDate(),rb.getCheckoutDate());
				TypedQuery<RoomBooking> qry=em.createQuery("Select rb from RoomBooking rb where rb.id=:bookingId and rb.hotelId=:hotelId",RoomBooking.class);
				qry.setParameter("bookingId",rb.getId());
				qry.setParameter("hotelId",rb.getHotelId());
				RoomBooking booking=qry.getSingleResult();
				List<RoomBookingDetails> bookingdetailsList=rb.getBookingDetail();
				Iterator<RoomBookingDetails> iterator=bookingdetailsList.iterator();
				while(iterator.hasNext())
				{
					RoomBookingDetails rbd=(RoomBookingDetails)iterator.next();
					
					datediff = DateDifferent(rbd.getFromDate(),rbd.getToDate());
					double taxpercentage = rbd.getTaxRate();
					double roomprice = rbd.getRoomRate()*datediff;
					double taxamount=roomprice*(taxpercentage/100);
					final_amount+=roomprice;
					tottaxamount+=taxamount;
					rbd.setCheckInTime(booking.getCheckinTimeId());
					rbd.setCheckOutTime(booking.getCheckoutTimeId());
					rbd.setRoomTotal(roomprice);
					rbd.setTaxAmt(taxamount);
					rbd.setDiscPer(0.0);
					rbd.setDiscAmt(0.0);
					rbd.setNetTotal(roomprice+taxamount);
					rbd.setIsCancelled("N");
					em.persist(rbd);
				}
				
				//for room service
				List<RoomBookingServices> servicedetailsList=booking.getBookingServices();
				Iterator<RoomBookingServices> serviterator=servicedetailsList.iterator();
				while(serviterator.hasNext())
				{
					double servtotal=0.0;
					double taxamt=0.0;
					double netamt=0.0;
					RoomBookingServices rbs=(RoomBookingServices)serviterator.next();
					servtotal=rbs.getGrossAmount();
					taxamt=servtotal*rbs.getTaxRate()/100;
					netamt=servtotal+taxamt;
					rbs.setTaxAmount(taxamt);
					rbs.setDiscPer(0.00);
					rbs.setDiscAmount(0.00);
					rbs.setNetAmount(netamt);
					totservnetamt+=netamt;
					totservamt+=servtotal;
					totservtaxamt+=taxamt;
				}
				final_amount=final_amount+booking.getGrossAmt();
				tottaxamount=tottaxamount+booking.getTaxAmt();
				double total = final_amount+tottaxamount;
				booking.setNoOfPersons(rb.getNoOfPersons());
				booking.setGrossAmt(final_amount);
				booking.setDiscPer(0.0);
				booking.setDiscAmt(0.0);
				booking.setTaxAmt(tottaxamount);
				booking.setRoundOff(Math.round(total)-total);
				booking.setNetAmt(Math.round(total));
				booking.setIsTaxable("Y");
				booking.setRoomServiceGross(totservamt);
				booking.setRoomServiceDiscount(0.00);
				booking.setRoomServiceTax(totservtaxamt);
				
				TypedQuery<RoomBookingPayment> q1 = em.createQuery("SELECT r FROM RoomBookingPayment r WHERE r.booking_id=:bookingId AND r.hotelId=:hotelId",RoomBookingPayment.class);
				q1.setParameter("bookingId",rb.getId());
				q1.setParameter("hotelId",Integer.toString(rb.getHotelId()));
				List<RoomBookingPayment> roomBookingPaymentlist = q1.getResultList();
				Iterator<RoomBookingPayment> iterator1=roomBookingPaymentlist.iterator();
				double paidamt=0;
				double payableamt=total+totservnetamt;
				while(iterator1.hasNext())
				{
					RoomBookingPayment rbp=(RoomBookingPayment)iterator1.next();
					rbp.setAmount(Math.round(payableamt));
					paidamt+=rbp.getPaidamount();
					rbp.setAmt_to_pay(Math.round(payableamt)-paidamt);	
				}
				em.getTransaction().commit();
				status="success";
			}catch(Exception e) {
				e.printStackTrace();
				throw new DAOException("addMoreRoom unsuccessfull.", e);
			}finally {
				em.close();
			}
			return status;
		}
		
		@Override
		public String uploadCustImage(String custId, String fileName,
				InputStream inputStream) throws IOException {
			System.out.println("enter uploadCustImage");
			String status = "";
			// Extract file name from content-disposition header of file part
			if (inputStream != null) {
				// String fileName = getFileName(part);
				System.out.println("***** fileName: " + fileName);

				// String extension=afterDot;
				int custid = Integer.parseInt(custId);
				String changedFileName = "cust_" + custid + "." + "jpeg";
				String basePath = "/home/ubuntu/.resturant/rb";
				String ops = System.getProperty("os.name").toLowerCase();
				System.out.println("operating system is: " + ops);
				if (ops.startsWith("windows")) {
					basePath = "C:/restaurantImages/rb";
				}
				try {
					createFolderIfNotExists(basePath);
				}catch (SecurityException se) {
					System.out.println("cant create folder"+basePath+" : " + se);
				}
				System.out.println("basePath :" + basePath);
				String fullPath=basePath + "/" + changedFileName;
				File outputFilePath = new File(fullPath);
				OutputStream outputStream = null;
				try {
					// inputStream = part.getInputStream();
					outputStream = new FileOutputStream(outputFilePath);

					int read = 0;
					final byte[] bytes = new byte[1024];
					while ((read = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, read);
					}
					outputStream.flush();
					status = "Success";
				} catch (IOException e) {
					e.printStackTrace();
					status = "Failure";
				} finally {
					if (outputStream != null) {
						outputStream.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
				}
			}
			return status;
		}
		private void createFolderIfNotExists(String dirName)
		            throws SecurityException {
		        File theDir = new File(dirName);
		        if (!theDir.exists()) {
		            theDir.mkdir();
		        }
		}
		
		@Override
		public List<RbAppHomeData> getRbAppHomeData(String fromDate, String toDate, String hotelId) throws DAOException {
			List<RbAppHomeData> homeDataList=new ArrayList<RbAppHomeData>();
			EntityManager em = null;
			EntityManager em1 = null;
			CallableStatement callableStatement = null;
			Connection connection = null;
			ResultSet rs = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em1 = entityManagerFactory.createEntityManager();
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				em1.getTransaction().begin();

				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();
				connection = sessionFactory.getConnectionProvider().getConnection();
				
				java.sql.Date frmDate = DateUtil.convertStringDateToSqlDate(fromDate, "yyyy-MM-dd");
				java.sql.Date tDate = DateUtil.convertStringDateToSqlDate(toDate, "yyyy-MM-dd");
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_GET_RB_APP_HOME_DATA);
				callableStatement.setInt(1, Integer.parseInt(hotelId));
				callableStatement.setDate(2, frmDate);
				callableStatement.setDate(3, tDate);
				callableStatement.execute();
				rs = callableStatement.getResultSet();

				//ReflectionResultSetMapper<Room> resultSetMapper1 = new ReflectionResultSetMapper<Room>(Room.class);

				while (rs.next()) {
					RbAppHomeData homeData=new RbAppHomeData();
					homeData.setNoOfBooking(rs.getInt(1));
					homeData.setNoOfCheckIn(rs.getInt(2));
					homeData.setNoOfCheckOut(rs.getInt(3));
					homeData.setNoOfCancelBooking(rs.getInt(4));
					homeDataList.add(homeData);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("In DAOException", e);

			} finally {
				try {
					rs.close();
					callableStatement.close();
					connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				em1.close();
				em.close();
			}
			
			return homeDataList;
		}
		
		@Override
		public RbSnapShotData getRbSnapShotData(String date, String hotelId) throws DAOException {
			RbSnapShotData snapshData=null;
			EntityManager em = null;
			EntityManager em1 = null;
			CallableStatement callableStatement = null;
			Connection connection = null;
			ResultSet rs = null;
			
			try {
				entityManagerFactory = PersistenceListener.getEntityManager();
				em1 = entityManagerFactory.createEntityManager();
				em = entityManagerFactory.createEntityManager();
				em.getTransaction().begin();
				em1.getTransaction().begin();

				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();
				connection = sessionFactory.getConnectionProvider().getConnection();
				
				java.sql.Date tdate = DateUtil.convertStringDateToSqlDate(date, "yyyy-MM-dd");
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_GET_RB_SNAP_SHOT_DATA);
				callableStatement.setInt(1, Integer.parseInt(hotelId));
				callableStatement.setDate(2, tdate);
				callableStatement.execute();
				rs = callableStatement.getResultSet();

				//ReflectionResultSetMapper<Room> resultSetMapper1 = new ReflectionResultSetMapper<Room>(Room.class);

				while (rs.next()) {
					RbSnapShotData snapData=new RbSnapShotData();
					snapData.setNoOfBooking(rs.getInt(1));
					snapData.setNoOfCheckIn(rs.getInt(2));
					snapData.setNoOfCheckOut(rs.getInt(3));
					snapData.setNoOfCancelBooking(rs.getInt(4));
					snapData.setActRoomAvl(rs.getInt(5));
					snapData.setRoomUnderMaint(rs.getInt(6));
					snapData.setRoomAvl(rs.getInt(7));
					snapData.setRoomBooked(rs.getInt(8));
					snapData.setOccupancy(rs.getDouble(9));
					snapData.setRoomRev(rs.getDouble(10));
					snapData.setArr(rs.getDouble(11));
					snapData.setRevPar(rs.getDouble(12));
					snapData.setRoomDinning(rs.getDouble(13));
					snapshData=snapData;
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("In DAOException", e);

			} finally {
				try {
					rs.close();
					callableStatement.close();
					connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				em1.close();
				em.close();
			}
			
			return snapshData;
		}
		
	public ResponseObj insertCustomerAccount(StoreCustomer customer)
			throws DAOException {
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_ONLY_INSERT_ACCOUNT);
				callableStatement.setString(1, customer.getName());
				callableStatement.setString(2, "");
				callableStatement.setInt(3, 0);
				callableStatement.setString(4, "SDE");
				callableStatement.setInt(5, 0);
				callableStatement.setInt(6, 0);
				callableStatement.setInt(7, 0);
				callableStatement.setString(8, customer.getAddress());
				callableStatement.setString(9, "");
				callableStatement.setString(10, customer.getContactNo());
				callableStatement.setString(11, customer.getEmailId());
				callableStatement.setString(12, "");
				callableStatement.setString(13, customer.getCust_vat_reg_no());
				callableStatement.setString(14, "");
				callableStatement.setString(15, "");
				callableStatement.setDouble(16, 0);
				callableStatement.setDouble(17, customer.getCreditLimit());
				callableStatement.setInt(18, 1);
				callableStatement.setInt(19, 0);
				callableStatement.setDouble(20, 0);
				callableStatement.setString(21, customer.getCreatedDate());
				callableStatement.setInt(22, 0);
				callableStatement.setInt(23, customer.getStoreId());
				callableStatement.setInt(24, 0);
				callableStatement.setInt(25, customer.getCreatedByid());
				callableStatement.setDouble(26, 0);
				callableStatement.setInt(27, 0);
				callableStatement.setInt(28, customer.getId());
				callableStatement.registerOutParameter(29,
						java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(29);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setReason("Record save successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setReason("Record not saved successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;

	}
	
	public ResponseObj updateCustomerAccount(StoreCustomer customer)
			throws DAOException {
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResponseObj responseObj=new ResponseObj();

		try {
			System.out.println("updateCustomerAccount:"+customer.toString());
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();

			try {
				
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_ONLY_UPDATE_ACCOUNT);
				callableStatement.setString(1, customer.getName());
				callableStatement.setString(2, "");
				callableStatement.setInt(3, 0);
				callableStatement.setString(4, "SDE");
				callableStatement.setInt(5, 0);
				callableStatement.setInt(6, 0);
				callableStatement.setInt(7, 0);
				callableStatement.setString(8, customer.getAddress());
				callableStatement.setString(9, "");
				callableStatement.setString(10, customer.getContactNo());
				callableStatement.setString(11, customer.getEmailId());
				callableStatement.setString(12, "");
				callableStatement.setString(13, customer.getCust_vat_reg_no());
				callableStatement.setString(14, "");
				callableStatement.setString(15, "");
				callableStatement.setDouble(16, 0);
				callableStatement.setDouble(17, customer.getCreditLimit());
				callableStatement.setInt(18, 1);
				callableStatement.setInt(19, 0);
				callableStatement.setDouble(20, 0);
				callableStatement.setString(21, customer.getCreatedDate());
				callableStatement.setInt(22, 0);
				callableStatement.setInt(23, customer.getStoreId());
				callableStatement.setInt(24, 0);
				callableStatement.setInt(25, customer.getCreatedByid());
				callableStatement.setDouble(26, 0);
				callableStatement.setInt(27, 0);
				callableStatement.setInt(28, customer.getId());
				callableStatement.registerOutParameter(29,
						java.sql.Types.INTEGER);

				callableStatement.execute();
				status = callableStatement.getInt(29);
				
				if (status >0) {
					
					responseObj.setStatus(ReturnConstant.SUCCESS);
					responseObj.setId(status);
					responseObj.setReason("Record save successfully.");
					
				} else {
					
					responseObj.setStatus(ReturnConstant.FAILURE);
					responseObj.setId(status);
					responseObj.setReason("Record not saved successfully.");
					
				}
				
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			catch (Exception e) {
				e.printStackTrace();
				responseObj.setStatus(ReturnConstant.FAILURE);
				responseObj.setId(0);
				responseObj.setReason("Record not saved successfully.");
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setStatus(ReturnConstant.FAILURE);
			responseObj.setId(0);
			responseObj.setReason("Record not saved successfully.");
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			em.close();
		}
		return responseObj;

	}
	public Double getTotalPaid(int bookingId , String paymode)
	{
		EntityManager em = null;
		BigDecimal paidAmt = null;
		try {
			
			em = PersistenceListener.getEntityManager().createEntityManager();
			Query query = em.createNativeQuery("select sum(p.paidamount) from rb_payment p where p.booking_id=:bookingId and p.payment_mode=:paymentMode group by p.payment_mode");
			System.out.println("com.sharobi.restaurantapp.dao.RoomSearchDAOImpl.getTotalPaid(int, String):: Query = " + query);
			paidAmt =  (BigDecimal)query.setParameter("bookingId", bookingId)
					  .setParameter("paymentMode", paymode).getSingleResult();
			
			System.out.println("Total Pay = " + paidAmt);
			//em.getTransaction().commit();
		} catch (Exception e) {
			//em.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			if(em!=null)
				em.close();
		}
		if(paidAmt==null)
			return 0.0;
		else 
			return paidAmt.doubleValue();
	}
	
	public List<RoomBookingPayment> getPaymentInfoByBookingList(List<Integer> bookingIdList)
			throws DAOException {
		EntityManager em = null;
		List<RoomBookingPayment> payList = new ArrayList<RoomBookingPayment>();

		try {
			em = PersistenceListener.getEntityManager().createEntityManager();
			em.getTransaction().begin();

			TypedQuery<RoomBookingPayment> qry = em
					.createQuery("SELECT p FROM RoomBookingPayment p WHERE p.booking_id IN (:bookingIdList)", RoomBookingPayment.class);
			qry.setParameter("bookingIdList", bookingIdList);
			payList = qry.getResultList();

			// get the bill
			/*List<Bill> billList = getBillListByOrderIdList(orderIdList);
			Iterator<Bill> iteratorBill = billList.iterator();
			while (iteratorBill.hasNext()) {
				Bill bill2 = (Bill) iteratorBill.next();
				int orderIdBill = bill2.getOrderbill().getId();
				Double custDiscount = bill2.getCustomerDiscount();
				Iterator<Payment> iteratorPayment = payList.iterator();
				while (iteratorPayment.hasNext()) {
					Payment payment = (Payment) iteratorPayment.next();
					OrderMaster order = payment.getOrderPayment();
					int orderIdPayment = order.getId();
					// check if order ids are equal
					if (orderIdBill == orderIdPayment) {
						payment.setOrderId(order.getId());
						payment.setCustomerDiscount(custDiscount);

					}

				}

			}*/

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(
					"Problem occured in getPaymentInfoByBookingList", e);
		} finally {
			if(em != null) em.close();
		}
		return payList;
	}
	
	@Override
	public List<FlashReportData> getFlashReportData(String fromDate,String hotelId) throws DAOException {
		List<FlashReportData> finalList = new ArrayList<FlashReportData>();
		EntityManager em = null;
		EntityManager em1 = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		
		try {
			entityManagerFactory = PersistenceListener.getEntityManager();
			em1 = entityManagerFactory.createEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em1.getTransaction().begin();

			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			
			java.sql.Date frmDate = DateUtil.convertStringDateToSqlDate(fromDate, "yyyy-MM-dd");
			callableStatement = connection
					.prepareCall(StoredProcedures.PROC_GET_FLASH_REPORT_DATA);
			callableStatement.setInt(1, Integer.parseInt(hotelId));
			callableStatement.setDate(2, frmDate);
			callableStatement.execute();
			rs = callableStatement.getResultSet();
			double totdtData=0.0;
			double totmtd=0.0;
			double totytd=0.0;
			while (rs.next()) {
				FlashReportData data=new FlashReportData();
				String cat=rs.getString(1);
				String category = null;
				if("act_room_avbl".equals(cat))
				{
					category="Actual Room Night Available";
				}
				if("room_under_maintain".equals(cat))
				{
					category="Room Under Maintainance";
				}
				if("room_avbl".equals(cat))
				{
					category="Room Night Available";
				}
				if("room_booked".equals(cat))
				{
					category="Room Booked";
				}
				if("occupancy".equals(cat))
				{
					category="Occupancy %";
				}
				if("room_revenue".equals(cat))
				{
					category="Room Revenue";
					totdtData+=rs.getDouble(2);
					totmtd+=rs.getDouble(3);
					totytd+=rs.getDouble(4);
				}
				if("arr".equals(cat))
				{
					category="ARR";
				}
				if("rev_arr".equals(cat))
				{
					category="REV PAR";
				}
				if("in_room_dining".equals(cat))
				{
					category="Room Dining";
					totdtData+=rs.getDouble(2);
					totmtd+=rs.getDouble(3);
					totytd+=rs.getDouble(4);
				}
				data.setCategory(category);
				data.setDtdata(rs.getDouble(2));
				data.setMtd(rs.getDouble(3));
				data.setYtd(rs.getDouble(4));
				/*totdtData+=rs.getDouble(2);
				totmtd+=rs.getDouble(3);
				totytd+=rs.getDouble(4);*/
				finalList.add(data);

			}
			FlashReportData lastdata=new FlashReportData();
			lastdata.setCategory("Total");
			lastdata.setDtdata(totdtData);
			lastdata.setMtd(totmtd);
			lastdata.setYtd(totytd);
			finalList.add(lastdata);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("In DAOException", e);

		} finally {
			try {
				rs.close();
				callableStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			em1.close();
			em.close();
		}
		
		return finalList;
	}
	
	public int CustomerPaymentjournalEntryRb(RoomBookingPayment payment)
			throws DAOException {
		System.out.println("CustomerPaymentjournalEntry InventoryInvoicePayment payment = "+payment);
		EntityManager em = null;
		int status = 0;
		Connection connection = null;
		CallableStatement callableStatement = null;
		
		int nocusacc = 0;
		int nosaleacc = 0;
		
		try {
			
			//Date createddate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse((payment.getCreationDate()));
			RoomBookingPayment stockxml = new RoomBookingPayment();
			
			//Customer Payment Journal Entry
			if(payment.getIs_account().equalsIgnoreCase("y") && payment.getCr_legder_id()!=0 && payment.getDr_legder_id()!=0)
			{
			
				List<JournalEntry> jes = new ArrayList<JournalEntry>();
				
				if(payment.getCr_legder_id()!=0 && payment.getCr_amount()>0)
				{
					jes.add(new JournalEntry("cr", payment.getCr_legder_id(), payment.getCr_amount()));
				}
				if(payment.getDr_legder_id()!=0 && payment.getDr_amount()>0)
				{
					jes.add(new JournalEntry("dr", payment.getDr_legder_id(), payment.getDr_amount()));
				}
				stockxml.setJes(jes);
				System.out.println("cust pay : jes = "+jes);
				
				nocusacc = 0;
			}
			else {
				nocusacc = 1;
			}
			
			
			//For Sale Journal entry
			if(payment.getIs_account().equalsIgnoreCase("y") && payment.getSale_ledger_id()!=0 && payment.getDuties_ledger_id()!=0)
			{
				double roundoff = 0;
				double cashAmt = getTotalPaid(payment.getBooking_id(),"cash");
				double cardAmt = getTotalPaid(payment.getBooking_id(),"card");
				cardAmt += getTotalPaid(payment.getBooking_id(),"Paytm");
				cardAmt += getTotalPaid(payment.getBooking_id(),"Online");
				
				List<JournalEntry> jes = new ArrayList<>();
				if(payment.getSale_ledger_id()!=0 && payment.getGrossAmt()!=0)
				{
					jes.add(new JournalEntry("cr", payment.getSale_ledger_id(), payment.getGrossAmt()));
					roundoff += payment.getGrossAmt();
				}
				if(payment.getDuties_ledger_id()!=0)
				{
					jes.add(new JournalEntry("cr", payment.getDuties_ledger_id(), payment.getTaxVatAmt()));
					roundoff += payment.getTaxVatAmt();
				}
				if(payment.getService_charge_ledger_id()!=0 && payment.getServiceChargeAmt()>0)
				{
					jes.add(new JournalEntry("cr", payment.getService_charge_ledger_id(), payment.getServiceChargeAmt()));
					roundoff += payment.getServiceChargeAmt();
				}
				
				
				
				if(payment.getDebitor_ledger_id()!=0)
				{
					if(payment.getPaymentMode().equalsIgnoreCase("nopay") && payment.getAmt_to_pay()>0)
					{
						
						jes.add(new JournalEntry("dr", payment.getDebitor_ledger_id(), payment.getAmt_to_pay()));
						roundoff -= payment.getAmt_to_pay();
					}
					else
					{
						double amt = payment.getAmt_to_pay();
						jes.add(new JournalEntry("dr", payment.getDebitor_ledger_id(), amt ));
						roundoff -= amt;
					}
				}
				/*else if(payment.getPaymentMode().equalsIgnoreCase("cash"))
				{
					if(payment.getDebitor_cash_ledger_id()!=0 && payment.getPaidAmount()>0)
					{
						//double amt = getTotalPaid(payment.getOrderPayment().getId(),payment.getPaymentMode());
						jes.add(new JournalEntry("dr", payment.getDebitor_cash_ledger_id(), cashAmt));
						roundoff -= amt;
					}
				}
				else if(payment.getPaymentMode().equalsIgnoreCase("card"))
				{
					if(payment.getCard_ledger_id()!=0 && payment.getPaidAmount()>0)
					{
						//double amt = getTotalPaid(payment.getOrderPayment().getId(),payment.getPaymentMode());
						//jes.add(new JournalEntry("dr", payment.getDebitor_cash_ledger_id(), payment.getPaidAmount()));
						jes.add(new JournalEntry("dr", payment.getCard_ledger_id(), cardAmt));
						roundoff -= amt;
					}
				}*/
				
				
				if(payment.getDebitor_cash_ledger_id()!=0 && cashAmt>0)
				{
					jes.add(new JournalEntry("dr", payment.getDebitor_cash_ledger_id(), cashAmt));
					roundoff -= cashAmt;
				}
				if(payment.getCard_ledger_id()!=0 && cardAmt>0)
				{
					//jes.add(new JournalEntry("dr", payment.getDebitor_cash_ledger_id(), payment.getPaidAmount()));
					jes.add(new JournalEntry("dr", payment.getCard_ledger_id(), cardAmt));
					roundoff -= cardAmt;
				}
				
				
				if(payment.getDiscount_ledger_id()!=0 && payment.getDiscAmt()>0)
				{
					jes.add(new JournalEntry("dr", payment.getDiscount_ledger_id(), payment.getDiscAmt()));
					roundoff -= payment.getDiscAmt();
				}
				if(payment.getRound_ledger_id()!=0 && Math.abs(roundoff)<1) //as during part payment roundoff will differ
				{
					String type="";
					if(roundoff<0)
						type="cr";
					else
						type="dr";
					
					jes.add(new JournalEntry(type, payment.getRound_ledger_id(), Math.abs(roundoff)));
				}
				
				stockxml.setJes(jes);
				
				nosaleacc = 0;
			}
			else
			{
				nosaleacc = 1;
			}

			
			if(nocusacc==1 && nosaleacc==1)
			{
				return 0;
			}
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();
			connection = sessionFactory.getConnectionProvider().getConnection();
			
			try {
				StringWriter sw = new StringWriter();
				//File file = new File("D:\\2017-10-04\\file_purchase_invoice_creation.xml");
				JAXBContext jaxbContext = JAXBContext
						.newInstance(RoomBookingPayment.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						true);
				//jaxbMarshaller.marshal(InventoryInvoicePayment, file);
				// jaxbMarshaller.marshal(InventoryInvoicePayment, System.out);
				jaxbMarshaller.marshal(stockxml, sw);
				String result = sw.toString();
				System.out.println("Rb Customer Payment xml:: "+result);
				callableStatement = connection
						.prepareCall(StoredProcedures.PROC_INSERT_XMLJOURNAL_TRANSACTION_RB);
				callableStatement.setString(1, result);
				callableStatement.setString(2, "jes");
				callableStatement.setString(3, payment.getQs());
				callableStatement.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				callableStatement.setInt(5, payment.getBooking_id());//(5, payment.getOrderPayment().getId());
				callableStatement.setString(6, "");
				callableStatement.setInt(7, 0);
				callableStatement.setInt(8, Integer.parseInt(payment.getHotelId()));
				callableStatement.setInt(9, 0);
				callableStatement.setString(10, "");
				callableStatement.setString(11, payment.getCreatedBy());
				
				System.out.println("callable "+callableStatement.toString());
				callableStatement.execute();
				//invNo = callableStatement.getString(4);
				status = 1;
				/*if (status == 0) {
					result1 = "" + status;
				} else if (status == 1) {
					result1 = invNo;
				}*/
				// result=result.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
				// System.out.println("formatted output::: "+result);

			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);
		} finally {
			try {
				if(callableStatement!=null)
					callableStatement.close();
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(em!=null)
				em.close();
		}
		return status;

	}
}
