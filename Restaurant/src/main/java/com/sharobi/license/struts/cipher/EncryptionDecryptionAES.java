package com.sharobi.license.struts.cipher;

import java.security.NoSuchAlgorithmException;
//import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

import com.sharobi.license.util.Constants;

public class EncryptionDecryptionAES {
	static Cipher cipher;
	
	
	public String  getJCAKey(String mapKey){
		
		String encryptedText = "";
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			SecretKey secretKey = keyGenerator.generateKey();
			cipher = Cipher.getInstance("AES");

			//String plainText = "LaagN*ydm01Jrad";
			if(Constants.devMode) System.out.println("mapKey: " + mapKey);

			encryptedText = encrypt(mapKey, secretKey);
			if(Constants.devMode) System.out.println("Level2 Encrypted: " + encryptedText);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encryptedText;
	}
	
	public String getMapKey(String jcaKey){
		
		String decryptedText = "";
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			SecretKey secretKey = keyGenerator.generateKey();
			decryptedText = decrypt(jcaKey, secretKey);
			if(Constants.devMode) System.out.println("Decrypted: " + decryptedText);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return decryptedText;
		
	}

	public static void main(String[] args) throws Exception {
		
		EncryptionDecryptionAES aes1 = new EncryptionDecryptionAES();
		String mapKey = "LaagN*ydm01Jra1d";
		String jcaKey = aes1.getJCAKey(mapKey);
		
		EncryptionDecryptionAES aes2 = new EncryptionDecryptionAES();
		String mapKey2 = aes2.getMapKey(jcaKey);
		
		if(mapKey.equals(mapKey2)){
			if(Constants.devMode) System.out.println("MATCHED!!!!!!!!!!!");
		}
		
	}

	public static String encrypt(String plainText, SecretKey secretKey)
			throws Exception {
		byte[] plainTextByte = plainText.getBytes();
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		//byte[] encryptedByte = cipher.doFinal(plainTextByte);
    cipher.doFinal(plainTextByte);
		
		//Base64 base64 = new Base64();
		return new String(Base64.encodeBase64(plainText.getBytes()));
	}

	public static String decrypt(String encryptedText, SecretKey secretKey)
			throws Exception {
		
		
		Base64 base64 = new Base64();
		byte[] encryptedTextByte = base64.decode(encryptedText);
		
		String mapKey = "";
		for(int i=0; i<encryptedTextByte.length; i++){
			mapKey = mapKey + (char)encryptedTextByte[i];
		}
		
		//System.out.println("Map Key: " + mapKey);
		return mapKey;
	}
}