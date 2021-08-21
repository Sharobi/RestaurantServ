/**
 * 
 */
package com.sharobi.restaurantapp.commonUtil;



import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sharobi.restaurantapp.dao.StoreAddressDAO;
import com.sharobi.restaurantapp.dao.StoreAddressDAOImpl;
import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.dao.roombook.RoomSearchDAO;
import com.sharobi.restaurantapp.dao.roombook.RoomSearchDAOImpl;
import com.sharobi.restaurantapp.dao.roombook.RoomServicesDAO;
import com.sharobi.restaurantapp.model.DashBoardData;
import com.sharobi.restaurantapp.model.StoreMaster;
import com.sharobi.restaurantapp.model.roomBook.RbSnapShotData;
import com.sharobi.restaurantapp.service.StoreAddressService;
import com.sharobi.restaurantapp.service.exception.ServiceException;
import com.sharobi.restaurantapp.service.roombook.RoomSearchService;

/**
 * @author Habib
 *
 */
public class AutoEmailSender {
	
	private String user;
    private String password;
    private String host;
    private String port;
	
	public AutoEmailSender(String user, String password,String port,String host) {
		// TODO Auto-generated constructor stub
		this.user = user;
        this.password = password;
        this.port = port;
        this.host = host;
	}
	
	public synchronized void sendMail(String subject, String body, String recipients) throws Exception
    {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		//properties.put("mail.smtp.socketFactory.port", port);
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", port);
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		try
		{
        MimeMessage message = new MimeMessage(session);
        //DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
        message.setFrom(new InternetAddress(user));
        message.setSubject(subject);
        //message.setDataHandler(handler);
        message.setText(body);
        if (recipients.indexOf(',') > 0)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
        Transport.send(message);
        System.out.println("Sent message successfully....");
		}catch(MessagingException mex)
		{
			mex.printStackTrace();
		}
    }
	
	public String createBodyData(String id,String type,String date,String name) throws ServiceException, DAOException
	{
		String body="";
		if("Y".equals(type))
		{
			//RoomSearchService roomservice=new RoomSearchService();
			RoomSearchDAO roomDAO=new RoomSearchDAOImpl();
			RbSnapShotData snapdata=roomDAO.getRbSnapShotData(date, id);
			StringBuffer sbf=new StringBuffer();
			sbf.append("Hello,"+name);
			sbf.append(System.getProperty("line.separator"));
			sbf.append(System.getProperty("line.separator"));
			sbf.append("House Status ======= ");
			sbf.append(System.getProperty("line.separator"));
			sbf.append("No of Booking           : "+snapdata.getNoOfBooking());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("No of CheckIn           : "+snapdata.getNoOfCheckIn());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("No of CheckOut          : "+snapdata.getNoOfCheckOut());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("No of Cancel Booking    : "+snapdata.getNoOfCancelBooking());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Actual Room Available   : "+snapdata.getActRoomAvl());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Room Under Maintainance : "+snapdata.getRoomUnderMaint());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Room Available          : "+snapdata.getRoomAvl());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Occupancy %             : "+snapdata.getOccupancy());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Average room Rate %     : "+snapdata.getArr());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Revenue Per Room        : "+snapdata.getRevPar());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Sales ======= ");
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Room Revenue            : "+snapdata.getRoomRev());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Room Dinning            : "+snapdata.getRoomDinning());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Total                   : "+(snapdata.getRoomRev()+snapdata.getRoomDinning()));
			sbf.append(System.getProperty("line.separator"));
			sbf.append(System.getProperty("line.separator"));
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Regards ");
			sbf.append(System.getProperty("line.separator"));
			sbf.append("YewPos");
     		body = sbf.toString();
		}
		else
		{
			StoreAddressService addressServ = new StoreAddressService();
			DashBoardData dData=addressServ.getDashBoardData(Integer.parseInt(id), date, date);
			StringBuffer sbf=new StringBuffer();
			sbf.append("Hello,"+name);
			sbf.append(System.getProperty("line.separator"));
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Order Status ======= ");
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Paid Order Amount           : "+(dData.getPaidAmt()+dData.getCreditAmt()));
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Unpaid Order amount           : "+dData.getUnpaidAmt());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Cancel Order Amount          : "+dData.getCancelAmt());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("----------------------------------------");
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Total   : "+dData.getPaidAmt()+dData.getCreditAmt()+dData.getUnpaidAmt()+dData.getCancelAmt());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Sales Status======= ");
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Cash Sales            : "+dData.getCashAmt());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Card Sales            : "+dData.getCardAmt());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Credit Sales            : "+dData.getCreditAmt());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Other Sales            : "+dData.getOtherAmt());
			sbf.append(System.getProperty("line.separator"));
			sbf.append("----------------------------------------");
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Total                   : "+(dData.getPaidAmt()+dData.getCreditAmt()));
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Refund Amount           : "+(dData.getRefundAmt()));
			sbf.append(System.getProperty("line.separator"));
			sbf.append(System.getProperty("line.separator"));
			sbf.append(System.getProperty("line.separator"));
			sbf.append("Regards ");
			sbf.append(System.getProperty("line.separator"));
			sbf.append("YewPos");
     		body = sbf.toString();
		}
		System.out.println("mail Body="+body);
		return body;
	}
	
	public StoreMaster getStoreData(int id) throws DAOException
	{
		StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
		StoreMaster store = addressDAO.getStoreByStoreId(id);
		return store;
		
	}

}
