package com.sharobi.restaurantapp.service.roombook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//import javax.ws.rs.QueryParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.dao.roombook.RoomSearchDAO;
import com.sharobi.restaurantapp.model.StoreCustomer;
import com.sharobi.restaurantapp.model.roomBook.FlashReportData;
import com.sharobi.restaurantapp.model.roomBook.RbAppHomeData;
import com.sharobi.restaurantapp.model.roomBook.RbSnapShotData;
import com.sharobi.restaurantapp.model.roomBook.Room;
import com.sharobi.restaurantapp.model.roomBook.RoomBooking;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingCustomer;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingGuest;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingInfo;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingPayment;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingServices;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingType;
import com.sharobi.restaurantapp.service.exception.ServiceException;

/**
 * @author ChanchalN
 * DAO implementation: RoomSearchDAOImpl.java
 */
@Service
public class RoomSearchService {
	private final static Logger logger = LogManager .getLogger(RoomSearchService.class);
	
  @Autowired
	private RoomSearchDAO roomSearchDAO;
  
	public RoomSearchDAO getRoomSearchDao() {
		return roomSearchDAO;
	}
	public void setRoomSearchDao(RoomSearchDAO roomSearchDao) {
		this.roomSearchDAO = roomSearchDao;
	}
	
	public List<RoomBookingType> getRoomBookingTypes(String hotelId) throws DAOException{
		List<RoomBookingType> customerList=null;
		try {
			customerList=roomSearchDAO.getRoomBookingTypes(hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return customerList;
	}
	
	public StoreCustomer createOrUpdateStorecustomer(StoreCustomer customer) throws DAOException{
		StoreCustomer customer1=null;
		try {
			customer1=roomSearchDAO.createOrUpdateStorecustomer(customer);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return customer1;
	}
	

	public List<Room> searchRoom(String fromDate, String toDate, String hotelId) throws ServiceException {
		List<Room> roomList = null;
		try {
			//roomDAO.addRoom(room);
			roomList = roomSearchDAO.searchRoom(fromDate, toDate, hotelId);
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while searching Room", e);
		}
		return roomList;
	}
	
	public String reserveRoom(RoomBookingInfo rb) throws ServiceException{
		String status = "failure";
		
		try {
			logger.info("Service Call to book Room ");
			//roomDAO.addRoom(room);
			status = roomSearchDAO.reserveRoom(rb);
			logger.info("Room Booking Successfull ");
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while booking Room", e);
		}
		return status;
	}
	public List<RoomBooking> getAllReserveIdByDateHotelId(String fromDate, String hotelId) throws ServiceException{
		List<RoomBooking> roomBookingList = null;
		try {
			//roomDAO.addRoom(room);
			roomBookingList = roomSearchDAO.getAllReserveIdByDateHotelId(fromDate, hotelId);
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured in service layer", e);
		}
		return roomBookingList;
	}
	
	public List<RoomBooking> getAllReserveIdByDatePeriod(String fromDate, String toDate, String hotelId,String bookingNo) throws ServiceException{
		List<RoomBooking> roomBookingList = null;
		try {
			roomBookingList = roomSearchDAO.getAllReserveIdByDatePeriod(fromDate, toDate, hotelId,bookingNo);
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured in service layer", e);
		}
		return roomBookingList;
	}
	
	public String cancelBookingByReserveId(RoomBooking rb) throws DAOException{
		String status = "failure";
		try {
			status=roomSearchDAO.cancelBookingByReserveId(rb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public String advancePaymentByReserveId(RoomBookingPayment rp) throws DAOException{
		String status = "failure";
		try {
			status=roomSearchDAO.advancePaymentByReserveId(rp);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public String roomBooking(RoomBooking rb) throws DAOException{
		String status = "0";
		try {
			logger.info("WebService call for RoomBooking.");
			status=roomSearchDAO.roomBooking(rb);
		}catch(Exception e) {
			logger.error("Error found while Booking Room.");
			e.printStackTrace();
		}
		return status;
	}
	
	public RoomBookingCustomer getCustomerDetailsByReserveId(String reserveId, String hotelId) throws DAOException{
		RoomBookingCustomer customerList=null;
		try {
			logger.info("WebService call to get customer details by reserve Id.");
			customerList=roomSearchDAO.getCustomerDetailsByReserveId(reserveId,hotelId);
		}catch(Exception e) {
			logger.error("Error found.");
			e.printStackTrace();
		}
		return customerList;
	}
	
	public List<RoomBookingCustomer> getCustomerDetailsByName(String name, String hotelId,String fromDate, String toDate,String func) throws DAOException{
		List<RoomBookingCustomer> customerList=null;
		try {
			logger.info("WebService call to get customer details by Name.");
			customerList=roomSearchDAO.getCustomerDetailsByName(name , hotelId, fromDate, toDate, func);
		}catch(Exception e) {
			logger.error("Error found.");
			e.printStackTrace();
		}
		return customerList;
	}
	
	public List<RoomBookingCustomer> getCustomerDetailsByPhoneNo(String phoneNo, String hotelId, String fromDate, String toDate, String func) throws DAOException{
		List<RoomBookingCustomer> customerList=null;
		try {
			logger.info("WebService call to get customer details by Phone.");
			customerList=roomSearchDAO.getCustomerDetailsByPhoneNo(phoneNo , hotelId, fromDate,toDate,func);
		}catch(Exception e) {
			logger.error("Error found.");
			e.printStackTrace();
		}
		return customerList;
	}
	
	public List<RoomBooking> getAllReserveIdByFromDateToDateCustId(String fromDate, String toDate, String custId, String hotelId) throws DAOException{
		List<RoomBooking> roombookinglist=null;
		try {
			roombookinglist=roomSearchDAO.getAllReserveIdByFromDateToDateCustId(fromDate, toDate, custId , hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return roombookinglist;
	}
	
	public List<RoomBookingInfo> getAllReserveDetailsByReserveId(String reserveId, String hotelId) throws DAOException{
		List<RoomBookingInfo> roombookinginfo=null;
		try {
			logger.info("WebService call to get getAllReserveDetailsByReserveId.");
			roombookinginfo=roomSearchDAO.getAllReserveDetailsByReserveId(reserveId , hotelId);
		}catch(Exception e) {
			logger.error("Error found.");
			e.printStackTrace();
		}
		return roombookinginfo;
	}
	
	public List<RoomBookingInfo> getReserveIdListByReserveId(String reserveId, String hotelId, String fromDate, String toDate) throws DAOException{
		List<RoomBookingInfo> roombookinginfolist=null;
		try {
			logger.info("WebService call to get getReserveIdListByReserveId.");
			roombookinginfolist=roomSearchDAO.getReserveIdListByReserveId(reserveId, hotelId,fromDate,toDate);
		}catch(Exception e) {
			logger.error("Error found.");
			e.printStackTrace();
		}
		return roombookinginfolist;
	}
	
	public List<RoomBooking> getAllCheckInByDatePeriod(String fromDate, String toDate, String hotelId,String bookingNo) throws DAOException{
		List<RoomBooking> roombookinglist=null;
		try {
			roombookinglist=roomSearchDAO.getAllCheckIn(fromDate, toDate, hotelId,bookingNo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return roombookinglist;
	}
	
	public List<RoomBooking> getAllBookingIdByCustId(String fromDate, String toDate, String hotelId , String custId) throws DAOException{
		List<RoomBooking> roombookinglist=null;
		try {
			roombookinglist=roomSearchDAO.getAllBookingIdByCustId(fromDate, toDate, hotelId, custId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return roombookinglist;
	}
	
	public List<RoomBooking> getAllBookingDetailsByBookingId(String bookingId, String hotelId ) throws DAOException{
		List<RoomBooking> roombookinglist=null;
		try {
			roombookinglist=roomSearchDAO.getAllBookingDetailsByBookingId(bookingId, hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return roombookinglist;
	}
	public List<RoomBooking> getAllBookingDetailsByBookingIdForBill(String bookingId, String hotelId ) throws DAOException{
		List<RoomBooking> roombookinglist=null;
		try {
			roombookinglist=roomSearchDAO.getAllBookingDetailsByBookingIdForBill(bookingId, hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return roombookinglist;
	}
	
	public List<RoomBooking> getAllBookingByBookingId(String bookingId, String hotelId, String fromDate, String toDate) throws DAOException{
		List<RoomBooking> roombookinglist=null;
		try {
			logger.info("WebService call to get getAllBookingByBookingId.");
			roombookinglist=roomSearchDAO.getAllBookingByBookingId(bookingId, hotelId, fromDate, toDate);
		}catch(Exception e) {
			logger.error("Error found.");
			e.printStackTrace();
		}
		return roombookinglist;
	}
	
	public String checkOut(RoomBooking rb) throws DAOException{
		String status="failure";
		try {
			status=roomSearchDAO.checkOut(rb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public String checkIn(RoomBooking rb) throws DAOException{
		String status="failure";
		try {
			status=roomSearchDAO.checkIn(rb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	public String extendcheckOutDate(RoomBooking rb) throws DAOException{
		String status="failure";
		try {
			status=roomSearchDAO.extendcheckOutDate(rb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public String addDiscount(RoomBooking rb) throws DAOException{
		String status="failure";
		try {
			status=roomSearchDAO.addDiscount(rb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public String changeRoom(String reserveId,String bookingId,String hotelId,String fromRoom,String toRoom,String roomRate,String taxId,String taxPer) throws DAOException{
		String status="failure";
		try {
			status=roomSearchDAO.changeRoom(reserveId,bookingId,hotelId,fromRoom,toRoom,roomRate,taxId,taxPer);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public String setNonTaxable(RoomBooking rb) throws DAOException{
		String status="failure";
		try {
			status=roomSearchDAO.setNonTaxable(rb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public List<RoomBooking> getAllCheckOutByDatePeriod(String fromDate, String toDate, String hotelId) throws DAOException{
		List<RoomBooking> roombookinglist=null;
		try {
			roombookinglist=roomSearchDAO.getAllCheckOut(fromDate, toDate, hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return roombookinglist;
	}
	
	public List<RoomBookingPayment> getPaymentInfoByBookingId(String bookingId) throws DAOException{
		List<RoomBookingPayment> roombookinglist=null;
		try {
			roombookinglist=roomSearchDAO.getPaymentInfoByBookingId(bookingId);
		}catch(Exception e) {
			logger.error("Error found.");
			e.printStackTrace();
		}
		return roombookinglist;
	}
	
	public String addRoomBookingServices(RoomBooking rb) throws DAOException{
		String status="failure";
		try {
			status=roomSearchDAO.addRoomBookingServices(rb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public List<RoomBookingServices> getRoomServicesByBookingId(String bookingId,String hotelId) throws DAOException{
		List<RoomBookingServices> roomserviceslist=null;
		try {
			roomserviceslist=roomSearchDAO.getRoomServicesByBookingId(bookingId,hotelId);
		}catch(Exception e) {
			logger.error("Error found.");
			e.printStackTrace();
		}
		return roomserviceslist;
	}
	
	public String addRoomBookingGuest(RoomBooking rb) throws DAOException{
		String status="failure";
		try {
			status=roomSearchDAO.addRoomBookingGuest(rb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public List<RoomBookingGuest> getRoomGuestByBookingId(String bookingId,String hotelId) throws DAOException{
		List<RoomBookingGuest> roomguestlist=null;
		try {
			roomguestlist=roomSearchDAO.getRoomGuestByBookingId(bookingId,hotelId);
		}catch(Exception e) {
			logger.error("Error found.");
			e.printStackTrace();
		}
		return roomguestlist;
	}
	
	public String convertToCreditBooking(RoomBooking rb) throws DAOException{
		String status = "failure";
		try {
			status=roomSearchDAO.convertToCreditBooking(rb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public List<RoomBooking> getCreditBookingByCustomerId(String storeId,
			String custId) throws ServiceException {
		List<RoomBooking> bookings = null;

		try {
			bookings = roomSearchDAO.getCreditBookingByCustomerId(storeId, custId);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Problem occured while trying to get all credit booking by cust id", e);
		}

		return bookings;

	}
	
	public List<FlashReportData> getFlashReportData(String fromDate,String hotelId) throws ServiceException {
		List<FlashReportData> dataList = null;
		try {
			dataList = roomSearchDAO.getFlashReportData(fromDate, hotelId);
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while getting flash report data", e);
		}
		return dataList;
	}
	
	public String addMoreRoom(RoomBooking rb) throws DAOException{
		String status="failure";
		try {
			status=roomSearchDAO.addMoreRoom(rb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public String uploadCustImage(String custId, String fileName,InputStream inputStream) throws Exception {
		String status = "";
		try {
			status = roomSearchDAO.uploadCustImage(custId, fileName, inputStream);
		} catch (DAOException e) {
			throw new ServiceException("problem occurred while trying to upload cust image", e);
		}
		return status;
	}
	
	public List<RbAppHomeData> getRbAppHomeData(String fromDate, String toDate, String hotelId) throws ServiceException {
		 List<RbAppHomeData> homeDataList = null;
		try {
			homeDataList = roomSearchDAO.getRbAppHomeData(fromDate, toDate, hotelId);
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while getting rbapp home data", e);
		}
		return homeDataList;
	}
	
	public List<FlashReportData> getRbDashBoardData(String fromDate, String toDate, String hotelId) throws ServiceException {
		 List<RbAppHomeData> homeDataList = null;
		 List<FlashReportData> flashDataList = null;
		 List<FlashReportData> flashDataListMod = new ArrayList<FlashReportData>();
		 List<FlashReportData> dashboardDataList = new ArrayList<FlashReportData>();;
		try {
			homeDataList = roomSearchDAO.getRbAppHomeData(fromDate, toDate, hotelId);
			flashDataList= roomSearchDAO.getFlashReportData(fromDate, hotelId);
			FlashReportData newFlashdata=new FlashReportData();
			newFlashdata.setCategory("No Of Booking");
			newFlashdata.setDtdata(homeDataList.get(0).getNoOfBooking());
			FlashReportData newFlashdata1=new FlashReportData();
			newFlashdata1.setCategory("No Of Check In");
			newFlashdata1.setDtdata(homeDataList.get(0).getNoOfCheckIn());
			FlashReportData newFlashdata2=new FlashReportData();
			newFlashdata2.setCategory("No Of Check Out");
			newFlashdata2.setDtdata(homeDataList.get(0).getNoOfCheckOut());
			FlashReportData newFlashdata3=new FlashReportData();
			newFlashdata3.setCategory("No Of Cancel Booking");
			newFlashdata3.setDtdata(homeDataList.get(0).getNoOfCancelBooking());
			flashDataListMod.add(newFlashdata);
			flashDataListMod.add(newFlashdata1);
			flashDataListMod.add(newFlashdata2);
			flashDataListMod.add(newFlashdata3);
			dashboardDataList.addAll(flashDataList);
			dashboardDataList.addAll(flashDataListMod);
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while getting rb dash board data", e);
		}
		return dashboardDataList;
	}
	
	public RbSnapShotData getRbSnapShotData(String date, String hotelId) throws ServiceException {
		 RbSnapShotData snapData = null;
		try {
			snapData = roomSearchDAO.getRbSnapShotData(date, hotelId);
		} catch (DAOException e) {
			throw new ServiceException(
					"problem occured while getting rbsnapshot data", e);
		}
		return snapData;
	}
}
