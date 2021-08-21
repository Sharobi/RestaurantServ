/**
 * 
 */
package com.sharobi.restaurantapp.dao.roombook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
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


/**
 * @author ChanchalN
 *
 */
public interface RoomSearchDAO {
	
	public List<RoomBookingType> getRoomBookingTypes(String hotelId) throws DAOException;
	public StoreCustomer createOrUpdateStorecustomer(StoreCustomer customer) throws DAOException;
	/*
	 * Function to search Rooms available. Implementation class is RoomSearchDaoImpl.
	 */
	public List<Room> searchRoom(String fromDate, String toDate, String hotelId) throws DAOException;
	/* Reserve Room / Book Room. Generates reserve Id and return to UI. */
	public String reserveRoom(RoomBookingInfo rb) throws DAOException;
	
	public List<RoomBooking> getAllReserveIdByDateHotelId(String fromDate, String hotelId) throws DAOException;
	public List<RoomBooking> getAllReserveIdByDatePeriod(String fromDate, String toDate, String hotelId,String bookingNo) throws DAOException;
	/* Cancel Booking for any particular reserveId and hotelId. */
	public String cancelBookingByReserveId(RoomBooking rb) throws DAOException;
	/* Advance Payment for a particular reservation. */
	public String advancePaymentByReserveId(RoomBookingPayment rp) throws DAOException;
	/* Direct Booking */
	public String roomBooking(RoomBooking rb) throws DAOException;
	public RoomBookingCustomer getCustomerDetailsByReserveId(String reserveId, String hotelId) throws DAOException;
	
	public List<RoomBookingCustomer> getCustomerDetailsByName(String name, String hotelId, String fromDate, String toDate,String func) throws DAOException;
	public List<RoomBookingCustomer> getCustomerDetailsByPhoneNo(String phoneNo, String hotelId, String fromDate, String toDate,String func) throws DAOException;
	
	public List<RoomBooking> getAllReserveIdByFromDateToDateCustId(String fromDate, String toDate, String custId, String hotelId) throws DAOException;
	public List<RoomBookingInfo> getAllReserveDetailsByReserveId(String reserveId, String hotelId) throws DAOException;
	
	public List<RoomBookingInfo> getReserveIdListByReserveId(String reserveId, String hotelId, String fromDate, String toDate) throws DAOException;
	/* Customer Check-In for both booking. */
	public List<RoomBooking> getAllCheckIn(String fromDate, String toDate, String hotelId,String bookingNo) throws DAOException;
	public List<RoomBooking> getAllBookingIdByCustId(String fromDate, String toDate, String hotelId, String custId) throws DAOException;
	
	public List<RoomBooking> getAllBookingDetailsByBookingId(String bookingId, String hotelId) throws DAOException;
	public List<RoomBooking> getAllBookingDetailsByBookingIdForBill(String bookingId, String hotelId) throws DAOException;
	public List<RoomBooking> getAllBookingByBookingId(String bookingId, String hotelId, String fromDate, String toDate) throws DAOException;
	/* Check-Out */
	public String checkOut(RoomBooking rb) throws DAOException;
	public String checkIn(RoomBooking rb) throws DAOException;
	public String extendcheckOutDate(RoomBooking rb) throws DAOException;
	public String changeRoom(String reserveId,String bookingId,String hotelId,String fromRoom,String toRoom,String roomRate,String taxId,String taxPer) throws DAOException;
	
	public String addDiscount(RoomBooking rb) throws DAOException;
	public String setNonTaxable(RoomBooking rb) throws DAOException;
	//added on 25.06.2019
	public List<RoomBooking> getAllCheckOut(String fromDate, String toDate, String hotelId) throws DAOException;
	public List<RoomBookingPayment> getPaymentInfoByBookingId(String bookingId) throws DAOException;
	//added on 05.08.2019
	public String addRoomBookingServices(RoomBooking rb) throws DAOException;
	public List<RoomBookingServices> getRoomServicesByBookingId(String bookingId,String hotelId) throws DAOException;
	//added on 25.10.2019
	public String addRoomBookingGuest(RoomBooking rb) throws DAOException;
	public List<RoomBookingGuest> getRoomGuestByBookingId(String bookingId,String hotelId) throws DAOException;
	//added on 01.11.2019
	public String convertToCreditBooking(RoomBooking rb) throws DAOException;
	public List<RoomBooking> getCreditBookingByCustomerId(String storeId,String custId) throws DAOException;
	public List<FlashReportData> getFlashReportData(String fromDate, String hotelId) throws DAOException;
	//added on 20.11.2019
	public String addMoreRoom(RoomBooking rb) throws DAOException;
	//added on 17.01.2020
	public String uploadCustImage(String custId, String fileName,InputStream inputStream) throws Exception;
	//added on 11.02.2020
	public List<RbAppHomeData> getRbAppHomeData(String fromDate, String toDate, String hotelId) throws DAOException;
	public RbSnapShotData getRbSnapShotData(String date, String hotelId) throws DAOException;
}
