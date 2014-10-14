package in.co.praveenkumar.groupchat;

import static org.apache.commons.codec.binary.Hex.decodeHex;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;

import com.sun.mail.util.BASE64DecoderStream;


public class CryptoMessageOther {
	
	private static SecretKey key;
	private static Cipher ecipher;
    private static Cipher dcipher;
	
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
	
	public static SecretKey getKey(){
		return key;
	}
	
	 public static String decryptOther(String text, String key2){
	    	try {
				key = loadKey(key2);
				Cipher ecipher2;
			    Cipher dcipher2;
				ecipher2 = Cipher.getInstance("DES");
		        dcipher2 = Cipher.getInstance("DES");
		        ecipher2.init(Cipher.ENCRYPT_MODE, key);
		        dcipher2.init(Cipher.DECRYPT_MODE, key);
		        
		        byte[] dec = BASE64DecoderStream.decode(text.getBytes());
	    		byte[] utf8 = dcipher2.doFinal(dec);
	    		return new String(utf8, "UTF8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
	            return null;
	        } catch (InvalidKeyException e) {
	            return null;
	        } catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return null;
	    }

}

