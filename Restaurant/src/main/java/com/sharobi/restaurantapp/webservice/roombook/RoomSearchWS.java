package com.sharobi.restaurantapp.webservice.roombook;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.StoreCustomer;
import com.sharobi.restaurantapp.model.roomBook.RoomBooking;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingInfo;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingPayment;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingServices;

/**
 * @author ChanchalN
 * Implementation class is RoomSearchWSImpl.java
 */
public interface RoomSearchWS {
	
	public String getRoomBookingTypes(String hotelId) throws DAOException;
	public String createOrUpdateStorecustomer(StoreCustomer customer) throws DAOException;
	/* search room between a date range */
	public String searchRoom(String fromDate, String toDate, String hotelId) throws DAOException;  
	/* Reserve Room / Book Room. Generates reserve Id and return to UI. */
	public String reserveRoom(RoomBookingInfo rb) throws DAOException;
	
	public String getAllreserveIdbyDateHotelId(String fromDate, String hotelId) throws DAOException;
	public String getAllreserveIdbyDatePeriod(String fromDate, String toDate, String hotelId,String bookingNo) throws DAOException;
	/* Cancel Booking for any particular reserveId and hotelId. */
	public String cancelBookingByReserveId(RoomBooking rb) throws DAOException;
	/* Advance Payment for a particular reservation. */
	public String advancePaymentByReserveId(RoomBookingPayment rp) throws DAOException;
	
	/* Direct Booking */
	public String roomBooking(RoomBooking rb) throws DAOException;
	public String getCustomerDetailsByReserveId(String reserveId, String hotelId) throws DAOException;
	
	public String getCustomerDetailsByName(String name, String hotelId,String fromDate, String toDate, String func) throws DAOException;
	public String getCustomerDetailsByPhoneNo(String phoneNo, String hotelId,String fromDate, String toDate, String func) throws DAOException;
	
	public String getAllReserveIdByFromDateToDateCustId(String fromDate, String toDate, String custId, String hotelId) throws DAOException;
	public String getAllReserveDetailsByReserveId(String reserveId, String hotelId) throws DAOException;
	
	public String getReserveIdListByReserveId(String reserveId, String hotelId,String fromDate, String toDate) throws DAOException;
	/* Customer Check-In */
	public String getAllCheckInByDatePeriod(String fromDate, String toDate, String hotelId,String bookingNo) throws DAOException;
	
	public String getAllBookingIdByCustId(String fromDate, String toDate, String hotelId, String custId) throws DAOException;
	public String getAllBookingDetailsByBookingId(String bookingId, String hotelId) throws DAOException;
	public String getAllBookingDetailsByBookingIdForBill(String bookingId, String hotelId) throws DAOException;
	
	public String getAllBookingByBookingId(String bookingId, String hotelId,String fromDate, String toDate) throws DAOException;
	/* Check-Out */
	public String checkOut(RoomBooking rb) throws DAOException;
	public String checkIn(RoomBooking rb) throws DAOException;
	public String extendcheckOutDate(RoomBooking rb) throws DAOException;
	public String changeRoom(String reserveId,String bookingId,String hotelId,String fromRoom,String toRoom,String roomRate,String taxId,String taxPer) throws DAOException;
	public String addDiscount(RoomBooking rb) throws DAOException;
	public String setNonTaxable(RoomBooking rb) throws DAOException;
	//added on 25.06.2019
	public String getAllCheckOutByDatePeriod(String fromDate, String toDate, String hotelId) throws DAOException;
	public String getPaymentInfoByBookingId(String bookingId) throws DAOException;
	//added on 05.08.2019
	public String addRoomBookingServices(RoomBooking rb) throws DAOException;  
	public String getRoomServicesByBookingId(String bookingId,String hotelId) throws DAOException;
	//added on 25.10.2019
	public String addRoomBookingGuest(RoomBooking rb) throws DAOException;  
	public String getRoomGuestByBookingId(String bookingId,String hotelId) throws DAOException;
	//added on 01.11.2019
	public String convertToCreditBooking(RoomBooking rb) throws DAOException;
	public String getCreditBookingByCustomerId(String storeId, String custId);
	//added on 20.11.2019
	public String addMoreRoom(RoomBooking rb) throws DAOException;
	//added on 17.01.2020
	public String uploadCustImage(MultipartFile  file,String emp) throws DAOException;
	//added on 11.02.2020
	public String getRbAppHomeData(String fromDate, String toDate, String hotelId) throws DAOException;
	//added on 14.02.2020
	public String getRbDashBoardData(String fromDate, String toDate, String hotelId) throws DAOException;
}
