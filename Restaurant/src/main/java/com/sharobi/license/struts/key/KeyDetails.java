package com.sharobi.license.struts.key;

import com.sharobi.license.mapEncryption1.EncryptingLevel1;
import com.sharobi.license.mapEncryption1.ExpiredDate;
import com.sharobi.license.struts.cipher.EncryptionDecryptionAES;

public class KeyDetails {
	
	
	public String key = "";
	
	public KeyBean getKeyDetails(String key){
		KeyBean bean = new KeyBean();
		
		EncryptionDecryptionAES aes2 = new EncryptionDecryptionAES();
		String mapKey = aes2.getMapKey(key); //map
		
		EncryptingLevel1 encryptingLevel1 = new EncryptingLevel1();
		bean = encryptingLevel1.decodingActivationKey1(mapKey);
		
		ExpiredDate date = new ExpiredDate(mapKey);
		String to_date = date.expiredDate();
		bean.setTo_date(to_date);
		
		return bean;
		
	}
	
	public static void main(String[] args){
		
		KeyDetails keyDetails = new KeyDetails();
		keyDetails.getKeyDetails("WGFhZERES2RtMDFKck5O");
		
	}

}
