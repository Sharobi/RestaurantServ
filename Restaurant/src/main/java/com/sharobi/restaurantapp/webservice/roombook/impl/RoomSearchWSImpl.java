/**
 * 
 */
package com.sharobi.restaurantapp.webservice.roombook.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sharobi.restaurantapp.dao.StoreAddressDAO;
import com.sharobi.restaurantapp.dao.StoreAddressDAOImpl;
import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.OrderMaster;
import com.sharobi.restaurantapp.model.StoreCustomer;
import com.sharobi.restaurantapp.model.StoreMaster;
import com.sharobi.restaurantapp.model.roomBook.FlashReportData;
import com.sharobi.restaurantapp.model.roomBook.RbAppHomeData;
import com.sharobi.restaurantapp.model.roomBook.Room;
import com.sharobi.restaurantapp.model.roomBook.RoomBooking;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingCustomer;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingGuest;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingInfo;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingPayment;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingServices;
import com.sharobi.restaurantapp.model.roomBook.RoomBookingType;
import com.sharobi.restaurantapp.model.util.PersistenceListener;
import com.sharobi.restaurantapp.service.exception.ServiceException;
import com.sharobi.restaurantapp.service.roombook.RoomSearchService;
import com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 * @author ChanchalN
 * DAO implementation: RoomSearchDAOImpl.java
 * Service Implementation: RoomSearchService.java
 */
@Controller
@ResponseBody
@RequestMapping(value = "/roomsearch")
public class RoomSearchWSImpl implements RoomSearchWS{
	
  @Autowired
	private RoomSearchService roomSearchService;
	
	private final static Logger logger = LogManager.getLogger(RoomSearchWSImpl.class);
	
	public RoomSearchService getRoomSearchService() {
		return roomSearchService;
	}
	public void setRoomSearchService(RoomSearchService roomSearchService) {
		this.roomSearchService = roomSearchService;
	}
	
	@Override
	@RequestMapping(value="/getRoomBookingTypes", method = RequestMethod.GET, produces = "application/json")
	public String getRoomBookingTypes(@RequestParam(value="hotelId") String hotelId) throws DAOException {
		List<RoomBookingType> rbtList=null;
		try {
			rbtList=roomSearchService.getRoomBookingTypes(hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBookingType>>() {
		}.getType();
		String json = gson.toJson(rbtList, type);
		return json;
	}
	
	@Override
	@RequestMapping(value = "/createOrUpdateStorecustomer", method = RequestMethod.POST, /*consumes = "application/json",*/ produces = "application/json")
	public String createOrUpdateStorecustomer(@RequestBody StoreCustomer customer) {
		StoreCustomer cust=null;
		try {
			cust = roomSearchService.createOrUpdateStorecustomer(customer);

		} catch (Exception x) {
			cust=new StoreCustomer();
			cust.setId(0);
			cust.setType("failure");
			x.printStackTrace();

		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<StoreCustomer>() {
		}.getType();
		String json = gson.toJson(cust, type);
		return json;

	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#searchRoom(java.lang.String, java.lang.String, java.lang.String)
	 * Webservice to search room on the basis of fromDate,toDate,hotelId.
	 * @Returns List<Room> of available rooms.
	 */
	@Override
	@RequestMapping(value = "/searchRoomByDatePaxRoomType", method = RequestMethod.GET, /*consumes = "application/json",*/ produces = "application/json")
	public String searchRoom(@RequestParam(value = "fromDate") String fromDate,
			@RequestParam(value = "toDate") String toDate, @RequestParam(value = "hotelId") String hotelId) throws DAOException {
		
		List<Room> roomList = null;
		try {
			roomList = roomSearchService.searchRoom(fromDate, toDate, hotelId);
		} catch (Exception x) {
			x.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<Room>>() {
		}.getType();
		String json = gson.toJson(roomList, type);
		System.out.println("Return String response in searchRoomByDatePaxRoomType: " +json);
		return json;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#reserveRoom(com.sharobi.restaurantapp.model.roomBook.RoomBookingInfo)
	 * Function to reserve room. Unique ReserveId is generated.
	 * Returns Generated ReserveId
	 */
	//depricated
	@Override
	@RequestMapping(value="/reserveRoom", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String reserveRoom(@RequestBody RoomBookingInfo rb) throws DAOException {
		String reserveId="";
		try {
			logger.info("Web service call for booking room.");
			reserveId=roomSearchService.reserveRoom(rb);
		}
		catch( Exception e ) {
			logger.error("Error found while booking room. ");
			e.printStackTrace();
		}
		return reserveId;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getAllreserveIdbyDateHotelId(java.lang.String, java.lang.String)
	 * WebService to search reserve id by date & hotel id.
	 * @Returns  List<RoomBookingInfo>
	 * this is used for check in page loading
	 */
	@Override
	@RequestMapping(value="/getAllreserveIdbyDateHotelId", method = RequestMethod.GET, produces = "application/json")
	public String getAllreserveIdbyDateHotelId(@RequestParam(value = "fromDate") String fromDate,
		@RequestParam(value = "hotelId") String hotelId) throws DAOException {
		List<RoomBooking> roomBookingList = null;
		long st=System.currentTimeMillis();
		try {
			roomBookingList=roomSearchService.getAllReserveIdByDateHotelId(fromDate,hotelId);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBooking>>() {
		}.getType();
		String json = gson.toJson(roomBookingList, type);
		long et=System.currentTimeMillis();
		System.out.println("Time diff in getAllreserveIdbyDateHotelId:"+(et-st));
		return json;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getAllreserveIdbyDatePeriod(java.lang.String, java.lang.String, java.lang.String)
	 * Search all reserveId by a date period.
	 * Returns List<RoomBookingInfo> by date period.
	 * this is used for check in page search button
	 */
	@Override
	@RequestMapping(value="/getAllreserveIdbyDatePeriod", method = RequestMethod.GET, produces = "application/json")
	public String getAllreserveIdbyDatePeriod(@RequestParam(value = "fromDate") String fromDate, @RequestParam(value = "toDate") String toDate, 
		@RequestParam(value = "hotelId") String hotelId,@RequestParam(value = "bookingNo") String bookingNo) throws DAOException {
		List<RoomBooking> roomBookingList = null;
		try {
			roomBookingList=roomSearchService.getAllReserveIdByDatePeriod(fromDate,toDate,hotelId,bookingNo);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBooking>>() {
		}.getType();
		String json = gson.toJson(roomBookingList, type);
		return json;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#cancelBookingByReserveId(java.lang.String, java.lang.String)
	 * Function to implement cancel as per reserve Id.
	 * Return String as success/failure.
	 */
	@Override
	@RequestMapping(value="/cancelBookingByReserveId", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String cancelBookingByReserveId(@RequestBody RoomBooking rb) throws DAOException{
		String status = "failure";
		try {
			status=roomSearchService.cancelBookingByReserveId(rb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#advancePaymentByReserveId(java.lang.String, java.lang.String, java.lang.String)
	 * Payment by reserve Id
	 * Returns String as success/failure
	 */
	@Override
	@RequestMapping(value="/advancePaymentByReserveId", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String advancePaymentByReserveId(@RequestBody RoomBookingPayment rp) throws DAOException {
		String status = "failure";
		try {
			status=roomSearchService.advancePaymentByReserveId(rp);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#bookingRoom(com.sharobi.restaurantapp.model.roomBook.RoomBooking)
	 * Function to Book Room directly
	 */
	@Override
	@RequestMapping(value="/roomBooking", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String roomBooking(@RequestBody RoomBooking rb) throws DAOException {
		String BookingId = "0";
		try {
			logger.info("WebService call for RoomBooking.");
			BookingId=roomSearchService.roomBooking(rb);
		}catch(Exception e) {
			logger.error("Error found in booking Room.");
			e.printStackTrace();
		}
		return BookingId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getCustomerDetailsByReserveId(java.lang.String, java.lang.String)
	 * Function to get customer details of a particular reserveId
	 */
	//depricated
	@Override
	@RequestMapping(value="/getCustomerDetailsByReserveId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getCustomerDetailsByReserveId(@RequestParam(value="reserveId") String reserveId, @RequestParam(value="hotelId") String hotelId) throws DAOException {
		RoomBookingCustomer customerList=null;
		try {
			logger.info("WebService call for RoomBooking.");
			customerList=roomSearchService.getCustomerDetailsByReserveId(reserveId,hotelId);
		}catch(Exception e) {
			logger.error("Error found in booking Room.");
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<RoomBookingCustomer>() {
		}.getType();
		String json = gson.toJson(customerList, type);
		System.out.println("Return String response : " +json);
		return json;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getCustomerDetailsByName(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * Function to get customer details by name.
	 * Returns List<RoomBookingCustomer>
	 */
	//depricated
	@Override
	@RequestMapping(value="/getCustomerDetailsByName", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getCustomerDetailsByName(@RequestParam(value="name") String name, @RequestParam(value="hotelId") String hotelId,
			@RequestParam(value="fromDate") String fromDate,@RequestParam(value="toDate") String toDate, @RequestParam(value="func") String func) throws DAOException {
		List<RoomBookingCustomer> customerList=null;
		try {
			logger.info("WebService call for to get Customer details by name.");
			customerList=roomSearchService.getCustomerDetailsByName(name,hotelId,fromDate,toDate,func);
		}catch(Exception e) {
			logger.error("Error found in booking Room.");
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBookingCustomer>>() {
		}.getType();
		String json = gson.toJson(customerList, type);
		System.out.println("Return String response : " +json);
		return json;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getCustomerDetailsByPhoneNo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * Function to get customer details by phone no. Used in search page auto-complete.
	 * Returns List<RoomBookingCustomer>
	 */
	//depricated
	@Override
	@RequestMapping(value="/getCustomerDetailsByPhoneNo", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getCustomerDetailsByPhoneNo(@RequestParam(value="phoneNo") String phoneNo, @RequestParam(value="hotelId") String hotelId,
			@RequestParam(value="fromDate") String fromDate, @RequestParam(value="toDate") String toDate, @RequestParam(value="func") String func) throws DAOException {
		List<RoomBookingCustomer> customerList=null;
		try {
			logger.info("WebService call to get customer details by phone no.");
			customerList=roomSearchService.getCustomerDetailsByPhoneNo(phoneNo,hotelId, fromDate,  toDate,func);
		}catch(Exception e) {
			logger.error("Error found in booking Room.");
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBookingCustomer>>() {
		}.getType();
		String json = gson.toJson(customerList, type);
		System.out.println("Return String response : " +json);
		return json;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getAllReserveIdByFromDateToDateCustId(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * Function to get all reserve Id within a date period , hotelId, custId.
	 * Returns List<RoomBookingInfo>
	 * this is used for check in page customer auto complete search
	 */
	@Override
	@RequestMapping(value="/getAllReserveIdByFromDateToDateCustId", method = RequestMethod.GET,/* consumes = "application/json",*/ produces = "application/json")
	public String getAllReserveIdByFromDateToDateCustId(@RequestParam(value="fromDate") String fromDate, @RequestParam(value="toDate") String toDate,
			@RequestParam(value="custId") String custId, @RequestParam(value="hotelId") String hotelId)
			throws DAOException {
		List<RoomBooking> roombookinglist=null;
		try {
			roombookinglist=roomSearchService.getAllReserveIdByFromDateToDateCustId(fromDate,toDate,custId,hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBooking>>() {
		}.getType();
		String json = gson.toJson(roombookinglist, type);
		return json;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getAllReserveDetailsByReserveId(java.lang.String, java.lang.String)
	 * Function to get reserve details of a particular Reserve Id
	 * @Returns List<RoomBookingInfo>
	 */
	//depricated
	@Override
	@RequestMapping(value="/getAllReserveDetailsByReserveId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllReserveDetailsByReserveId(@RequestParam(value="reserveId") String reserveId, @RequestParam(value="hotelId") String hotelId) throws DAOException {

		List<RoomBookingInfo> roombookinginfo = null;
		try {
			logger.info("WebService call to get reserve id by reserveId and hotelid.");
			roombookinginfo=roomSearchService.getAllReserveDetailsByReserveId(reserveId,hotelId);
		}catch(Exception e) {
			logger.error("Error found in getAllReserveDetailsByReserveId.");
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBookingInfo>>() {
		}.getType();
		String json = gson.toJson(roombookinginfo, type);
		System.out.println("Return String response : " +json);
		return json;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getReserveIdListByReserveId(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * @Returns List<RoomBookingInfo>
	 */
	//depricated
	@Override
	@RequestMapping(value="/getReserveIdListByReserveId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getReserveIdListByReserveId(@RequestParam(value="reserveId") String reserveId, @RequestParam(value="hotelId") String hotelId,
			@RequestParam(value="fromDate") String fromDate, @RequestParam(value="toDate") String toDate) throws DAOException {
		List<RoomBookingInfo> roomBookingInfoList=null;
		try {
			logger.info("WebService call for to getReserveIdListByReserveId.");
			roomBookingInfoList=roomSearchService.getReserveIdListByReserveId(reserveId,hotelId,fromDate,toDate);
		}catch(Exception e) {
			logger.error("Error found in getReserveIdListByReserveId.");
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBookingInfo>>() {
		}.getType();
		String json = gson.toJson(roomBookingInfoList, type);
		System.out.println("Return String response : " +json);
		return json;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getAllCheckInByDatePeriod(java.lang.String, java.lang.String, java.lang.String)
	 * WebService to fetch all checkIn within a date period. This function is for checkIn.
	 * this is used for check out page search button and page loading
	 */
	@Override
	@RequestMapping(value="/getAllCheckInByDatePeriod", method = RequestMethod.GET, /*consumes = "application/json",*/ produces = "application/json")
	public String getAllCheckInByDatePeriod(@RequestParam(value="fromDate") String fromDate,
			@RequestParam(value="toDate") String toDate, @RequestParam(value="hotelId") String hotelId,String bookingNo) throws DAOException {
		List<RoomBooking> roomBookingList=null;
		try {
			roomBookingList=roomSearchService.getAllCheckInByDatePeriod(fromDate,toDate,hotelId,bookingNo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBooking>>() {
		}.getType();
		String json = gson.toJson(roomBookingList, type);
		return json;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getAllBookingIdByCustId(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * Webservice call to fetch all booking details by single customer Id. Generally used to check for payment, billing for a particular customer.
	 * Returns List<RoomBooking> as String.
	 * this is used for check out page customer auto complete search
	 */
	@Override
	@RequestMapping(value="/getAllBookingIdByCustId", method = RequestMethod.GET, /*consumes = "application/json",*/ produces = "application/json")
	public String getAllBookingIdByCustId(@RequestParam(value="fromDate") String fromDate, @RequestParam(value="toDate") String toDate, 
			@RequestParam(value="hotelId") String hotelId, @RequestParam(value="custId") String custId)
			throws DAOException {
		List<RoomBooking> roomBookingList=null;
		try {
			roomBookingList=roomSearchService.getAllBookingIdByCustId(fromDate,toDate,hotelId,custId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBooking>>() {
		}.getType();
		String json = gson.toJson(roomBookingList, type);
		return json;
	}
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getAllBookingDetailsByBookingId(java.lang.String, java.lang.String)
	 * Webservice call to booking details by booking Id.
	 */
	@Override
	@RequestMapping(value="/getAllBookingDetailsByBookingId", method = RequestMethod.GET, /*consumes = "application/json",*/ produces = "application/json")
	public String getAllBookingDetailsByBookingId(@RequestParam(value="bookingId") String bookingId, @RequestParam(value="hotelId") String hotelId) throws DAOException {
		List<RoomBooking> roomBookingList=null;
		try {
			roomBookingList=roomSearchService.getAllBookingDetailsByBookingId(bookingId,hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBooking>>() {
		}.getType();
		String json = gson.toJson(roomBookingList, type);
		System.out.println("Return String response : " +json);
		return json;
	}
	
	@Override
	@RequestMapping(value="/getAllBookingDetailsByBookingIdForBill", method = RequestMethod.GET, /*consumes = "application/json",*/ produces = "application/json")
	public String getAllBookingDetailsByBookingIdForBill(@RequestParam(value="bookingId") String bookingId, @RequestParam(value="hotelId") String hotelId) throws DAOException {
		List<RoomBooking> roomBookingList=null;
		try {
			roomBookingList=roomSearchService.getAllBookingDetailsByBookingIdForBill(bookingId,hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBooking>>() {
		}.getType();
		String json = gson.toJson(roomBookingList, type);
		return json;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#getAllBookingByBookingId(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * Webservice call to search all booking id by booking id, used in auto-suggestion in search page.
	 * @Return List<RoomBooking> as Json.
	 */
	@Override
	@RequestMapping(value="/getAllBookingByBookingId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllBookingByBookingId(@RequestParam(value="bookingId") String bookingId, @RequestParam(value="hotelId") String hotelId,
			@RequestParam(value="fromDate") String fromDate, @RequestParam(value="toDate") String toDate) throws DAOException {
		List<RoomBooking> roomBookingList=null;
		try {
			logger.info("WebService call for to getAllBookingDetailsByBookingId.");
			roomBookingList=roomSearchService.getAllBookingByBookingId(bookingId,hotelId,fromDate, toDate);
		}catch(Exception e) {
			logger.error("Error found in getAllBookingDetailsByBookingId.");
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBooking>>() {
		}.getType();
		String json = gson.toJson(roomBookingList, type);
		System.out.println("Return String response : " +json);
		return json;
	}
/*
 * 	(non-Javadoc)
 * @see com.sharobi.restaurantapp.webservice.roombook.RoomSearchWS#checkOut(com.sharobi.restaurantapp.model.roomBook.RoomBooking)
 * WebService call to CheckOut.
 * @Param RoomBooking object
 * @Return String 
 */
	@Override
	@RequestMapping(value="/checkOut", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String checkOut(@RequestBody RoomBooking rb) throws DAOException {
		String status="failure";
		try {
			status=roomSearchService.checkOut(rb);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	@RequestMapping(value="/checkIn", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String checkIn(@RequestBody RoomBooking rb) throws DAOException {
		String status="failure";
		try {
			status=roomSearchService.checkIn(rb);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	@RequestMapping(value="/extendcheckOutDate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String extendcheckOutDate(@RequestBody RoomBooking rb) throws DAOException {
		String status="failure";
		try {
			status=roomSearchService.extendcheckOutDate(rb);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	@RequestMapping(value="/addDiscount", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addDiscount(@RequestBody RoomBooking rb) throws DAOException {
		String status="failure";
		try {
			status=roomSearchService.addDiscount(rb);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	@RequestMapping(value="/changeRoom", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String changeRoom(@RequestParam(value="reserveId") String reserveId,@RequestParam(value="bookingId") String bookingId, @RequestParam(value="hotelId") String hotelId,
			@RequestParam(value="fromRoom") String fromRoom, @RequestParam(value="toRoom") String toRoom,@RequestParam(value="roomRate") String roomRate,@RequestParam(value="taxId") String taxId,@RequestParam(value="taxPer") String taxPer) throws DAOException {
		String status="failure";
		try {
			status=roomSearchService.changeRoom(reserveId,bookingId,hotelId,fromRoom,toRoom,roomRate,taxId,taxPer);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	@RequestMapping(value="/setNonTaxable", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String setNonTaxable(@RequestBody RoomBooking rb) throws DAOException {
		String status="failure";
		try {
			status=roomSearchService.setNonTaxable(rb);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return status;
	}
	
	//added on 25.06.2019
	@Override
	@RequestMapping(value="/getAllCheckOutByDatePeriod", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getAllCheckOutByDatePeriod(@RequestParam(value="fromDate") String fromDate,
			@RequestParam(value="toDate") String toDate, @RequestParam(value="hotelId") String hotelId) throws DAOException {
		List<RoomBooking> roomBookingList=null;
		try {
			roomBookingList=roomSearchService.getAllCheckOutByDatePeriod(fromDate,toDate,hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBooking>>() {
		}.getType();
		String json = gson.toJson(roomBookingList, type);
		return json;
	}
	
	@Override
	@RequestMapping(value="/getPaymentInfoByBookingId", method = RequestMethod.GET/*, consumes = "application/json"*/, produces = "application/json")
	public String getPaymentInfoByBookingId( @RequestParam(value="bookingId") String bookingId) throws DAOException {
		List<RoomBookingPayment> roomBookingList=null;
		try {
			roomBookingList=roomSearchService.getPaymentInfoByBookingId(bookingId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBookingPayment>>() {
		}.getType();
		String json = gson.toJson(roomBookingList, type);
		return json;
	}
	
	//added on 05.08.2019
	@Override
	@RequestMapping(value="/addRoomBookingServices", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String addRoomBookingServices(@RequestBody RoomBooking rb) throws DAOException {
		String status="failure";
		try {
			status=roomSearchService.addRoomBookingServices(rb);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	@RequestMapping(value="/getRoomServicesByBookingId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getRoomServicesByBookingId( @RequestParam(value="bookingId") String bookingId,@RequestParam(value="hotelId") String hotelId) throws DAOException {
		List<RoomBookingServices> roomservicesList=null;
		try {
			roomservicesList=roomSearchService.getRoomServicesByBookingId(bookingId,hotelId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		java.lang.reflect.Type type = new TypeToken<List<RoomBookingServices>>() {
		}.getType();
		String json = gson.toJson(roomservicesList, type);
		return json;
	}
	
	//added on 25.10.2019
		@Override
		@RequestMapping(value="/addRoomBookingGuest", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
		public String addRoomBookingGuest(@RequestBody RoomBooking rb) throws DAOException {
			String status="failure";
			try {
				status=roomSearchService.addRoomBookingGuest(rb);
			}
			catch( Exception e ) {
				e.printStackTrace();
			}
			return status;
		}
		@Override
		@RequestMapping(value="/getRoomGuestByBookingId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
		public String getRoomGuestByBookingId( @RequestParam(value="bookingId") String bookingId,@RequestParam(value="hotelId") String hotelId) throws DAOException {
			List<RoomBookingGuest> roomguestList=null;
			try {
				roomguestList=roomSearchService.getRoomGuestByBookingId(bookingId,hotelId);
			}catch(Exception e) {
				e.printStackTrace();
			}
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			java.lang.reflect.Type type = new TypeToken<List<RoomBookingGuest>>() {
			}.getType();
			String json = gson.toJson(roomguestList, type);
			return json;
		}
	//added on 01.11.2019
		@Override
		@RequestMapping(value="/convertToCreditBooking", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
		public String convertToCreditBooking(@RequestBody RoomBooking rb) throws DAOException{
			String status = "failure";
			try {
				status=roomSearchService.convertToCreditBooking(rb);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return status;
		}
		
		@Override
		@RequestMapping(value = "/getCreditBookingByCustomerId", method = RequestMethod.GET, consumes = "application/json", produces = "text/plain")
		public String getCreditBookingByCustomerId(
				@RequestParam(value = "storeId") String storeId,
				@RequestParam(value = "custId") String custId) {

			List<RoomBooking> bookings = null;
			try {
				bookings = roomSearchService.getCreditBookingByCustomerId(storeId, custId);
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			java.lang.reflect.Type type = new TypeToken<List<RoomBooking>>() {
			}.getType();
			String json = gson.toJson(bookings, type);
			return json;
		}
		
		//added on 20.11.2019
		@Override
		@RequestMapping(value="/addMoreRoom", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
		public String addMoreRoom(@RequestBody RoomBooking rb) throws DAOException {
			System.out.println("addMoreRoom obj:"+rb.toString());
			String status="failure";
			try {
				status=roomSearchService.addMoreRoom(rb);
			}
			catch( Exception e ) {
				e.printStackTrace();
			}
			System.out.println("addMoreRoom return:"+status);
			return status;
		}
		
		//added on 17.01.2020
		@Override
		@RequestMapping(value = "/uploadCustImage", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
		public String uploadCustImage(
		    @RequestPart("file") MultipartFile  file,
		    @RequestPart("cust") String cust) throws DAOException{
			ObjectMapper objectMapper = new ObjectMapper();
			String status = "";
			try {
				JsonNode mf = objectMapper.readTree(cust);
				String custId=mf.get("id").toString();
				String fileName=mf.get("fileName").toString();
				status = roomSearchService.uploadCustImage(custId, fileName, file.getInputStream());
			} catch (Exception x) {
				x.printStackTrace();
			}
			return status;
		}
		
		@RequestMapping(value = "/getCustImage", method = RequestMethod.GET, produces = "text/plain")
		public void getEmpImage(@RequestParam(value = "id") String id,HttpServletRequest request,
				HttpServletResponse response) {
			InputStream is=null;
			try {
				String path = "/home/ubuntu/.resturant/rb";
				String ops = System.getProperty("os.name").toLowerCase();
				if (ops.startsWith("windows")) {
					path = "C:/restaurantImages/rb";
				}
				File file = new File(path + "/"+"cust_" + id + ".jpeg");
				/*InputStream is = new FileInputStream(file);
				List<Byte> buf = new ArrayList<Byte>();
				int ch = -1;
				while ((ch = is.read()) != -1) {
					buf.add((byte) ch);
				}
				byte[] array = new byte[buf.size()];
				for (int i = 0; i < buf.size(); i++) {
					array[i] = buf.get(i);
				}
				ServletOutputStream os = response.getOutputStream();
				os.write(array);
				os.flush();
				os.close();
				is.close();*/
				response.flushBuffer();
			    is = new FileInputStream(file);
			    IOUtils.copy(is, response.getOutputStream());
			} catch (FileNotFoundException e) {
				//e.printStackTrace();
				response.setStatus(404);
				System.out.println("file not found exception! cust_"+id+".jpeg");
			} catch (IOException e) {
				System.out.println("error occurred in get cust image by id");
				//e.printStackTrace();
			}
			finally {
			      try {
			        if(is != null) { is.close(); }
			      } catch (Exception e) {
			        e.printStackTrace();
			      }
			    }
		}
		@Override
		@RequestMapping(value = "/getRbAppHomeData", method = RequestMethod.GET, produces = "application/json")
		public String getRbAppHomeData(@RequestParam(value = "fromDate") String fromDate,
				@RequestParam(value = "toDate") String toDate, @RequestParam(value = "hotelId") String hotelId) throws DAOException {
			long st=System.currentTimeMillis();
			List<RbAppHomeData> homeDataList = null;
			try {
				homeDataList = roomSearchService.getRbAppHomeData(fromDate, toDate, hotelId);
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			java.lang.reflect.Type type = new TypeToken<List<RbAppHomeData>>() {
			}.getType();
			String json = gson.toJson(homeDataList, type);
			long et=System.currentTimeMillis();
			System.out.println("Time diff in getRbAppHomeData:"+(et-st)+" and Return String response : " +json);
			return json;
		}
		@Override
		@RequestMapping(value = "/getRbDashBoardData", method = RequestMethod.GET, produces = "application/json")
		public String getRbDashBoardData(@RequestParam(value = "fromDate") String fromDate,
				@RequestParam(value = "toDate") String toDate, @RequestParam(value = "hotelId") String hotelId) throws DAOException {
			long st=System.currentTimeMillis();
			List<FlashReportData> dashboardDataList = null;
			try {
				dashboardDataList = roomSearchService.getRbDashBoardData(fromDate, toDate, hotelId);
			} catch (Exception x) {
				x.printStackTrace();
			}
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			java.lang.reflect.Type type = new TypeToken<List<FlashReportData>>() {
			}.getType();
			String json = gson.toJson(dashboardDataList, type);
			long et=System.currentTimeMillis();
			System.out.println("Time diff in getRbDashBoardData:"+(et-st)+" and Return String response : " +json);
			return json;
		}
	
	@RequestMapping(value = "/report/bookingsummary", method = RequestMethod.GET)
	public void getRoomReserveReport(@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") Integer storeId,
            @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
            @RequestParam(value = "bookingType") Integer bookingType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		InputStream is = null;
		String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		
		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "booking_summary";
			jasperFile = "booking_summary.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			parameters.put("W_HotelId", String.valueOf(storeId));
			parameters.put("W_BookingType", String.valueOf(bookingType));
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}
	
	@RequestMapping(value = "/report/bookingregister", method = RequestMethod.GET)
	public void getRoomBookingReport(@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") Integer storeId,
            @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
            @RequestParam(value = "bookingType") Integer bookingType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		
		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "booking_register";
			jasperFile = "booking_register.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			parameters.put("W_HotelId", String.valueOf(storeId));
			parameters.put("W_BookingType", String.valueOf(bookingType));
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}
	
	@RequestMapping(value = "/report/roompaymentwisesales", method = RequestMethod.GET)
	public void getRoomBookingPayementwiseSalesReport(@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") Integer storeId,
            @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		
		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "room_sales_paymentwise";
			jasperFile = "room_sales_paymentwise.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			parameters.put("W_HotelId", String.valueOf(storeId));
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}
	//added on 16.10.2019
	@RequestMapping(value = "/report/getguestlist", method = RequestMethod.GET)
	public void getGuestList(@RequestParam(value = "frmdate") String frmdate,
			@RequestParam(value = "todate") String todate,
			@RequestParam(value = "storeId") Integer storeId,
            @RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
			HttpServletRequest request,
			HttpServletResponse response) {
		
    InputStream is = null;
    String baseFileName = null;
		Connection connection = null;
		EntityManagerFactory entityManagerFactory;
		EntityManager em = null;
		String jasperFile = null;
		
		try {
			
			entityManagerFactory = PersistenceListener.getEntityManager();
			em = entityManagerFactory.createEntityManager();

			// get connection object from entity manager
			Session ses = (Session) em.getDelegate();
			SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
					.getSessionFactory();

			connection = sessionFactory.getConnectionProvider().getConnection();

			FacesContext context = FacesContext.getCurrentInstance();
			baseFileName = "guest_list";
			jasperFile = "guest_list.jrxml";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("W_StartDate", frmdate);
			parameters.put("W_EndDate", todate);
			parameters.put("W_StoreId", String.valueOf(storeId));
			parameters.put("W_HotelId", String.valueOf(storeId));
      if(reportType == null) reportType = 1;
      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
      
      if (reportType == 1) { generatePDF(context, request, response, fileName, parameters, connection, jasperFile); }
      else { generateXLSX(context, request, response, fileName, parameters, connection, jasperFile); }

      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + fileName);
      response.reset();
      response.setHeader("Content-Type", contentType);
      response.setHeader("Content-Length", String.valueOf(file.length()));
      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
      
      response.flushBuffer();
      is = new FileInputStream(file);
      IOUtils.copy(is, response.getOutputStream());
      
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    finally {
      if(em != null) em.close();
      try {
        if(is != null) { is.close(); }
        if(connection != null) connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
	}
	
	//added on 13.11.2019
		@RequestMapping(value = "/report/getflashreport", method = RequestMethod.GET)
		public void getFlashReport(@RequestParam(value = "frmdate") String frmdate,
				@RequestParam(value = "storeId") Integer storeId,
				@RequestParam(value = "rptType",required=false,defaultValue = "1") Integer reportType,
				HttpServletRequest request,
				HttpServletResponse response) throws ServiceException, DAOException {
			
	    InputStream is = null;
	    String baseFileName = null;
			Connection connection = null;
			EntityManagerFactory entityManagerFactory;
			EntityManager em = null;
			String jasperFile = null;
			List<FlashReportData> dataList=null;
			try {
				
				entityManagerFactory = PersistenceListener.getEntityManager();
				em = entityManagerFactory.createEntityManager();

				// get connection object from entity manager
				Session ses = (Session) em.getDelegate();
				SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses
						.getSessionFactory();

				connection = sessionFactory.getConnectionProvider().getConnection();
				StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
				StoreMaster store = addressDAO.getStoreByStoreId(storeId);
				dataList=roomSearchService.getFlashReportData(frmdate, String.valueOf(storeId));
				FacesContext context = FacesContext.getCurrentInstance();
				baseFileName = "flash_report";
				jasperFile = "flash_report.jrxml";
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("W_Date", frmdate);
				parameters.put("W_HotelId", String.valueOf(storeId));
				parameters.put("dataList", dataList);
				parameters.put("W_storeName",store.getStoreName());
				parameters.put("W_storeAdd",store.getAddress());
				parameters.put("W_storePhone","Phone: "+store.getPhoneNo());
				parameters.put("W_storeEmail","Email: "+store.getEmailId());
	      if(reportType == null) reportType = 1;
	      String contentType = (reportType == 1) ? "application/pdf" : "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	      String fileName = baseFileName + ((reportType == 1) ? ".pdf" :  ".xlsx");
	      
	      if (reportType == 1) { generatePDFwithData(context, request, response, fileName, parameters, new JREmptyDataSource(), jasperFile); }
	      else { generateXLSXwithData(context, request, response, fileName, parameters, new JREmptyDataSource(), jasperFile); }
	      

	      File file = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + fileName);
	      response.reset();
	      response.setHeader("Content-Type", contentType);
	      response.setHeader("Content-Length", String.valueOf(file.length()));
	      response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	      
	      response.flushBuffer();
	      is = new FileInputStream(file);
	      IOUtils.copy(is, response.getOutputStream());
	      
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    finally {
	      if(em != null) em.close();
	      try {
	        if(is != null) { is.close(); }
	        if(connection != null) connection.close();
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
		}
	
	// Method to generate pdf
		public void generatePDF(FacesContext context, HttpServletRequest request,
				HttpServletResponse response, String fileName,
				Map<String, Object> parameters, Connection connection,
				String jasperFile) throws FileNotFoundException, IOException {

			try {
				JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + jasperFile);
				JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
				JasperExportManager.exportReportToPdfFile(print, request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + fileName);
			}

			catch (JRException e) {
				e.printStackTrace();
			} finally {

			}

		}
		
		 // Method to generate Excel report
		  public void generateXLSX(FacesContext context, HttpServletRequest request,
		      HttpServletResponse response, String fileName,
		      Map<String, Object> parameters, Connection connection,
		      String jasperFile) throws FileNotFoundException, IOException {

		    
		    try {
		      JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + jasperFile);
		      JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
		      
		      File xlsxFile = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + fileName);
		      JRXlsxExporter xlsxExporter = new JRXlsxExporter();
		      
		      xlsxExporter.setExporterInput(new SimpleExporterInput(print));
		      xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsxFile));
		      
		      SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
		      configuration.setDetectCellType(true);
		      configuration.setCollapseRowSpan(false);
		      xlsxExporter.setConfiguration(configuration);
		      
//		      xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.JASPER_PRINT, print);
//		      xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.OUTPUT_FILE, xlsxFile);
//		      xlsxExporter.setParameter(net.sf.jasperreports.engine.export.JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		      
		      xlsxExporter.exportReport(); //File is generated Correctly
		    }
		    catch (JRException e) {
		      
		      e.printStackTrace();
		    } finally {

		    }
		  }
		  
		  public void generatePDFwithData(FacesContext context, HttpServletRequest request,
					HttpServletResponse response, String fileName,
					Map<String, Object> parameters, JREmptyDataSource dataSource,
					String jasperFile) throws FileNotFoundException, IOException {

				try {
					JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + jasperFile);
					JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
					JasperExportManager.exportReportToPdfFile(print, request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + fileName);
				}

				catch (JRException e) {
					e.printStackTrace();
				} finally {

				}

			}
		  
		  public void generateXLSXwithData(FacesContext context, HttpServletRequest request,
			      HttpServletResponse response, String fileName,
			      Map<String, Object> parameters, JREmptyDataSource dataSource,
			      String jasperFile) throws FileNotFoundException, IOException {

			    
			    try {
			      JasperReport report = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + jasperFile);
			      JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
			      
			      File xlsxFile = new File(request.getSession().getServletContext().getRealPath("/") + "/jasper/roombooking/" + fileName);
			      JRXlsxExporter xlsxExporter = new JRXlsxExporter();
			      
			      xlsxExporter.setExporterInput(new SimpleExporterInput(print));
			      xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsxFile));
			      
			      SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
			      configuration.setDetectCellType(true);
			      configuration.setCollapseRowSpan(false);
			      xlsxExporter.setConfiguration(configuration);
			      xlsxExporter.exportReport(); //File is generated Correctly
			    }
			    catch (JRException e) {
			      
			      e.printStackTrace();
			    } finally {

			    }
			  }
			  
	
}
