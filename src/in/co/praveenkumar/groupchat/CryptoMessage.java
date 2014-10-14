package in.co.praveenkumar.groupchat;

import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.codec.binary.Hex.encodeHex;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

public class CryptoMessage{
    private static final int KEYSZ = 56;
    private static Cipher ecipher;
    private static Cipher dcipher;
    private static SecretKey key;
    
    public CryptoMessage(){
    	try {
    		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(KEYSZ); 
            key = keyGenerator.generateKey();
			ecipher = Cipher.getInstance("DES");
	        dcipher = Cipher.getInstance("DES");
	        ecipher.init(Cipher.ENCRYPT_MODE, key);
	        dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (NoSuchPaddingException e) {
            return;
        }
        catch (InvalidKeyException e) {
            return;
        }
    }
    
    public static String encrypt(String str) {
 	   try { 
 		   byte[] utf8 = str.getBytes("UTF8"); 
 		   byte[] enc = ecipher.doFinal(utf8);
 		   enc = BASE64EncoderStream.encode(enc);
 		   return new String(enc);
 		   }
 	   catch (Exception e) {
 		   e.printStackTrace();
 	   }
 	   return null;
    }
    
    public static String saveKey(SecretKey key) throws IOException{
        byte[] encoded = key.getEncoded();
        char[] hex = encodeHex(encoded);
        String data = String.valueOf(hex);
        return data;
    }
    
    public static SecretKey loadKey(String file) throws IOException{
        String data = file;
        char[] hex = data.toCharArray();
        byte[] encoded;
        try{
            encoded = decodeHex(hex);
        }catch (DecoderException e){
            return null;
        }
        SecretKey key = new SecretKeySpec(encoded, "DES");
        return key;
    }
    
    public static String decrypt(String str) {
    	try {
    		byte[] dec = BASE64DecoderStream.decode(str.getBytes());
    		byte[] utf8 = dcipher.doFinal(dec);
    		return new String(utf8, "UTF8");
    	}
    	catch (Exception e) {	  
    		e.printStackTrace();
    	}
    	return null;
    } 
    
    public static SecretKey getKey(){
    	return key;
    }
}