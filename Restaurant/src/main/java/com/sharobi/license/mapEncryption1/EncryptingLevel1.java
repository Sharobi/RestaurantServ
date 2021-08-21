package com.sharobi.license.mapEncryption1;

import java.util.Calendar;

import com.sharobi.license.struts.key.KeyBean;
import com.sharobi.license.util.Constants;

/**
 *
 * @author Susim Maiti
 */
public class EncryptingLevel1 {

	public String noOfMonths = "";
	public String activationKey = "";
	public String mappingKey = "";
	public String date = "";
	public String time = "";
	
	public String clientCode = "";
	public String userCode = "";
	public String noOfUsers = "";
		
	public static String[] mappingYY = {"B", "d", "F", "J", "K", "m", "p", "s", "u", "z"};
	public static String[] mappingMM = {"Az", "Ex", "Io", "Os", "Us", "Km", "Zs", "Dw", "Jr", "Dg", "Pw", "Tr"};
	public static String[] mappingDD = {"A", "D", "F", "H", "K", "n", "o", "s", "y", "z"};
	public static String[] mappingHH = {"B", "D", "J", "H", "K", "o", "p", "q", "v", "y"};
	public static String[] mappingMIN = {"C", "D", "F", "G", "K", "m", "n", "r", "w", "z"};
	public static String[] mappingSEC = {"E", "g", "I", "J", "N", "O", "R", "t", "V", "Y"};
	public static String[] mappingClientCode = {"a", "E", "g", "d", "K", "z", "m", "D", "r", "N"};
	public static String[] mappingPeriods = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "j", "K", "L", 
			   				   				 "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X"};
	
	public String decodedYear;
	public String decodedMonth;
	public String decodedDay;
	public String decodedHour;
	public String decodedMinute;
	public String decodedSecond;
	public String decodedClientCode;
	public String decodedUserCode;
	public String decodedPeriod;
	public String decodedSpecialKey;
	
	Calendar calendar;
	
	public EncryptingLevel1(){
		
	}
	
	public EncryptingLevel1(String clientCode, String userCode, String months, String noOfUsers){
		calendar = Calendar.getInstance();
		if(Constants.devMode) System.out.println(calendar.getTime());
		this.clientCode = clientCode;//store_id
		this.userCode = userCode;//username
		this.noOfMonths = months;
		this.noOfUsers = noOfUsers; //no_of_user
	}
	
	public static void main(String[] args){
		EncryptingLevel1 encrypting = new EncryptingLevel1("0029", "02", "16", "05"); //(store_id, username, months, no_of_users)
		String key = encrypting.encryptedActivationKey1(encrypting);
		encrypting.decodingActivationKey1(key);
	}
	
	private String encryptingYY(){
		 String year = Integer.toString(calendar.get(Calendar.YEAR));
		 String yearEncrypted = "";
		 for(int i = 2; i < 4; i ++){
			 String test = "" + year.charAt(i);
			 yearEncrypted = yearEncrypted + mappingYY[Integer.parseInt(test)];
		 }
		 return yearEncrypted;
	 }
	 
	 private String encryptingMM(){
		 int monthNo = calendar.get(Calendar.MONTH);
		 String monthEncrypted = mappingMM[monthNo];
		 return monthEncrypted;
	 }
	 
	 private String encryptingDD(){
		 String day = Integer.toString(calendar.get(Calendar.DATE));
		 String dayEncrypted = "";
		 for(int i = 0; i < day.length(); i ++){
			 String test = "" + day.charAt(i);
			 dayEncrypted = dayEncrypted + mappingDD[Integer.parseInt(test)];
		 }
		 if(dayEncrypted.length() == 1 )dayEncrypted = "*" + dayEncrypted;
		 return dayEncrypted;
	 }
	 
	 /*
	 private String encryptingHH(){
		 String hour = Integer.toString(calendar.get(Calendar.HOUR));
		 String hourEncrypted = "";
		 for(int i = 0; i < hour.length(); i ++){
			 String test = "" + hour.charAt(i);
			 hourEncrypted = hourEncrypted + mappingHH[Integer.parseInt(test)];
		 }
		 if(hourEncrypted.length() == 1 )hourEncrypted = "#" + hourEncrypted;
		 return hourEncrypted;
	 }
	 
	 private String encryptingMIN(){
		 String minute = Integer.toString(calendar.get(Calendar.MINUTE));
		 String minuteEncrypted = "";
		 for(int i = 0; i < minute.length(); i ++){
			 String test = "" + minute.charAt(i);
			 minuteEncrypted = minuteEncrypted + mappingMIN[Integer.parseInt(test)];
		 }
		 if(minuteEncrypted.length() == 1 )minuteEncrypted = "*" + minuteEncrypted;
		 return minuteEncrypted;
	 }
	 
	 private String encryptingSEC(){
		 String second = Integer.toString(calendar.get(Calendar.SECOND));
		 String secondEncrypted = "";
		 for(int i = 0; i < second.length(); i ++){
			 String test = "" + second.charAt(i);
			 secondEncrypted = secondEncrypted + mappingSEC[Integer.parseInt(test)];
		 }
		 if(secondEncrypted.length() == 1 )secondEncrypted = "#" + secondEncrypted;
		 return secondEncrypted;
	 }
	 */
	
	 private String encryptingUserCode(){
		 char [] original = new char [150];
		 for(int i = 0; i < 140; i ++){
			 original[i] = (char)i; 
			 //System.out.println(i + "    " +  (char)i);
		 }
		 
		 String encryptedUserCode = "";
		 for(int i = 0; i < userCode.length(); i ++){
			 char temp = userCode.charAt(i);
			 if((int)temp > 64 && (int)temp < 91 ){ //caps
				 int value = 90-(int)temp;
				 //System.out.println(value);
				 encryptedUserCode = encryptedUserCode + (char) (97 + value);
				// System.out.println((char)temp + "  to   " +  (char)(97 + value));
			 }
			 else if((int)temp > 96 && (int)temp < 122 ){ //not caps
				 int value = 122 -(int)temp;
				 //System.out.println(value);
				 encryptedUserCode = encryptedUserCode + (char) (65 + value);
				// System.out.println((char)temp + "  to   " +  (char)(65 + value));
			 }
			else encryptedUserCode = encryptedUserCode + (char)temp;
		 }
		// System.out.println(original);
		 return encryptedUserCode;
	 }
	 
	 private String encryptingClientCode(){ //integer
		 String encryptedClientCode = "";
		 for(int i = 0; i < clientCode.length(); i ++){
			 String test = "" + clientCode.charAt(i);
			 encryptedClientCode = encryptedClientCode + mappingClientCode[Integer.parseInt(test)];
		 }
		 return encryptedClientCode;
	 }
	 
	 public String decryptingClientCode(String encryptedClientCode){ //integer
		 String decryptedClientCode = "";
		 for(int i = 0; i < encryptedClientCode.length(); i ++){
			 String test = "" + encryptedClientCode.charAt(i);
			 for(int m = 0; m < mappingClientCode.length; m++ ){
				 if(test.equals(mappingClientCode[m])){
					 decryptedClientCode = decryptedClientCode + m;
				 }
			 }
		 }
		 return decryptedClientCode;
	 }
	 
	 public String decryptingNoOfUsers(String encryptedUsers){ //integer
		 String decryptedUsers = "";
		 for(int i = 0; i < encryptedUsers.length(); i ++){
			 String test = "" + encryptedUsers.charAt(i);
			 for(int m = 0; m < mappingClientCode.length; m++ ){
				 if(test.equals(mappingClientCode[m])){
					 decryptedUsers = decryptedUsers + m;
				 }
			 }
		 }
		 return decryptedUsers;
	 }
	 
	 private String encryptingPeriod(){
		 String encryptedPeriods = mappingPeriods[Integer.parseInt(noOfMonths)-1];
		 return encryptedPeriods;
	 }
	 
	 private String encryptingSpecialKey(){
		 
		 String encryptedClientCode = "";
		 for(int i = 0; i < noOfUsers.length(); i ++){
			 String test = "" + noOfUsers.charAt(i);
			 encryptedClientCode = encryptedClientCode + mappingClientCode[Integer.parseInt(test)];
		 }
		 return encryptedClientCode;
	 }
		 
	 public String encryptedActivationKey1(EncryptingLevel1 encrypting){
		 String year = encrypting.encryptingYY();
		 String month = encryptingMM();
		 String day = encryptingDD();
		 //String hour = encryptingHH();
		 //String minute = encryptingMIN();
		 //String second = encryptingSEC();
		 String clientCode = encryptingClientCode(); //integer
		 String userCode = encryptingUserCode(); 
		 String period = encryptingPeriod();
		 String specialKey = encryptingSpecialKey();
		 
		 this.mappingKey = period + clientCode + day +  year + userCode + month + specialKey ;
		 
		 if(Constants.devMode) System.out.println("period: " + period + " , clientcode: " + clientCode + ", day: " + day +  ", year:" + year + ", usercode: "+ userCode + ", months: " + month + ", no of user: " + specialKey);
		 if(Constants.devMode) System.out.println("encrytedKey - first level: \"" + this.mappingKey + "\"" + " and Size: " + this.mappingKey.length());
		 return this.mappingKey;
	 }
	 
	 public KeyBean decodingActivationKey1(String encryptedKey){
		 
		 KeyBean bean = new KeyBean();
		 
		 int pp = 1;
		 int yy = 2; 
		 int mm = 2; 
		 int dd = 2; 
		 int cc = 4; 
		 int uc = 2;
		 int nu = 2;
		 
		//period + clientCode + day +  year + userCode + month + specialKey
		 	
		 decodedYear = decodingYYDDHHMINSEC(encryptedKey.substring(pp+cc+dd, pp + cc+dd+yy), mappingYY);
		 decodedMonth = decodingMM(encryptedKey.substring(pp+cc+dd+yy+uc, pp+cc+dd+yy+uc+mm));
		 decodedDay = decodingYYDDHHMINSEC(encryptedKey.substring(pp+cc, pp +cc+dd), mappingDD);
		 
		 decodedPeriod = encryptedKey.substring(0, 1);
		 
		 String clientCode = encryptedKey.substring(1, pp+cc);
		 String userCode = encryptedKey.substring(pp+cc+dd+yy, pp+cc+dd+yy+uc);
		 String no_of_users = encryptedKey.substring(pp+cc+dd+yy+uc+mm, pp+cc+dd+yy+uc+mm+nu);
		 
		 String users = decryptingNoOfUsers(no_of_users);
		 String store_id = decryptingClientCode(clientCode);
		 
		 String fromDate = "20" + decodedYear + "-" +  decodedMonth + "-" + decodedDay;
		 
		 bean.setFrom_date(fromDate);
		 
		 bean.setStore_id(store_id);
		 bean.setNo_of_users(users);
		 bean.setUser_id(userCode);
		 
		 if(Constants.devMode) System.out.println("Decoded From Date - 20" + decodedYear + ":" +  decodedMonth + ":" + decodedDay + ", Periods - " + decodedPeriod );
		 if(Constants.devMode) System.out.println("store_id: " + store_id + ",  userCode:" + userCode + ", no_of_users: " + users );
		 
		 int months = 0;
		 for(int i = 0; i < 24; i++){
			 if(decodedPeriod.equals(EncryptingLevel1.mappingPeriods[i])){
				 months = i+1;
				 break;
			 }
		  }
		 bean.setNo_of_month("" + months);
		 return bean;
	 }
	
	 public String decodingYYDDHHMINSEC(String yy, String[] mapping){
		 //System.out.print(yy);
		 int first = 0;
		 for(int i = 0; i < 10; i++){
			 if(mapping[i].charAt(0) ==  yy.charAt(0))first = i;
		 }
		 int second = 0;
		 for(int i = 0; i < 10; i++){
			 if(mapping[i].charAt(0) ==  yy.charAt(1))second = i;
		 }
		// System.out.println("  " + first + second);
		 return "" + first + second;
	 }
	 
	 public String decodingMM(String mm){
		 String months = "";
		 for(int i = 0; i < 12; i++){
			 if(mm.equals(mappingMM[i]))months = Integer.toString(i+1);
		 }
		 return months;
	 }
	 
	
	 
}
