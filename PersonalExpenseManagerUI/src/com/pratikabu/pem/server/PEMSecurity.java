/**
 * 
 */
package com.pratikabu.pem.server;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author pratsoni
 *
 */
public class PEMSecurity {
	private static final Logger logger = Logger.getLogger(PEMSecurity.class);
	
	private static final String CIPHER_DK = "pratsoni";
	private static Cipher encryptCipher, decryptCipher;

	/**
	 * This method will return with a hashed version of the plain text passed in it.
	 * It will hashed using 256 bit SHA technique.
	 * It cannot be decrypted.
	 * @param plainText
	 * @return
	 * @throws Exception When the SHA-256 encryption is not found
	 */
	public static String hashData(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(plainText.getBytes());
			byte[] byteData = md.digest();
			
	    	return convertToHex(byteData);
		} catch(Exception e) {
			logger.error("Error while hashing the data.", e);
			return plainText;
		}
	}
	
	/**
	 * This method will encrypt the plain text using DES algorithm.
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String plainText) {
		try {
			byte[] encrypted = getEncryptCipher().doFinal(plainText.getBytes());
			return new BASE64Encoder().encode(encrypted);
		} catch(Exception e) {
			logger.error("Error while encrypting.", e);
			return plainText;
		}
	}
	
	/**
	 * This method will decrypt the encrypted text using DES algo.
	 * @param encryptedDESText
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptedDESText) {
		try {
			byte[] encrypted = new BASE64Decoder().decodeBuffer(encryptedDESText);
			byte[] plainTextPwdBytes = (getDecryptCipher().doFinal(encrypted));
			return new String(plainTextPwdBytes);
		} catch (Exception e) {
			logger.error("Error while decrypting.", e);
			return encryptedDESText;
		}
	}

	private static String convertToHex(byte[] byteData) {
		// convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
		return hexString.toString();
	}

	private static Cipher getEncryptCipher() throws Exception {
		if(null == encryptCipher) {
			encryptCipher = initCipher(Cipher.ENCRYPT_MODE);
		}
		return encryptCipher;
	}

	private static Cipher getDecryptCipher() throws Exception {
		if(null == decryptCipher) {
			decryptCipher = initCipher(Cipher.DECRYPT_MODE);
		}
		return decryptCipher;
	}

	private static synchronized Cipher initCipher(int mode) throws Exception {
		DESKeySpec dks = new DESKeySpec(CIPHER_DK.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE
		cipher.init(mode, desKey);
		return cipher;
	}
}
