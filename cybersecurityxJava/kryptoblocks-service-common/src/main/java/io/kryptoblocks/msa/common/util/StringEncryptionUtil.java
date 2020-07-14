package io.kryptoblocks.msa.common.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import com.google.common.base.CharMatcher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class StringEncryptionUtil.
 */
public class StringEncryptionUtil {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(StringEncryptionUtil.class);
	
	/** The Constant ALNUM. */
	private static final CharMatcher ALNUM =
			  CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z'))
			  .precomputed();
	
	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Getter /**
  * Sets the key.
  *
  * @param key the new key
  */
 @Setter(AccessLevel.PUBLIC)
	private byte[] key;
	
	/**
	 * Gets the control length.
	 *
	 * @return the control length
	 */
	@Getter /**
  * Sets the control length.
  *
  * @param controlLength the new control length
  */
 @Setter(AccessLevel.PUBLIC)
	private int controlLength;

	/** The Constant ALGORITHM. */
	private static final String ALGORITHM = "AES";
	
	/**
	 * Instantiates a new string encryption util.
	 *
	 * @param controlL the control L
	 */
	public StringEncryptionUtil(int controlL) {
		setKey(randomString());
		setControlLength(controlL);
	}
	
	/**
	 * Instantiates a new string encryption util.
	 *
	 * @param key the key
	 * @param controlL the control L
	 */
	public StringEncryptionUtil(byte[] key, int controlL) {
		setKey(key);
		setControlLength(controlL);
	}

	/**
	 * Encrypts the given plain text.
	 *
	 * @param plainInput the plain input
	 * @return the string
	 * @throws InvalidKeyException the invalid key exception
	 * @throws NoSuchPaddingException the no such padding exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws BadPaddingException the bad padding exception
	 * @throws IllegalBlockSizeException the illegal block size exception
	 */
	public String encrypt(String plainInput) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException{
		byte[] inputByte = plainInput.getBytes();				
		String shuffledKey = shuffle(plainInput);
		LOGGER.debug("encryption key : {}", shuffledKey);
		byte[] shuffeldKeyByte = shuffledKey.getBytes(StandardCharsets.UTF_8);		
		SecretKeySpec secretKey = new SecretKeySpec(shuffeldKeyByte, ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		String encryptedValue = DatatypeConverter.printBase64Binary(cipher.doFinal(inputByte));
		 
		return shuffledKey + encryptedValue;
		 
	}

	/**
	 * De-crypt the given byte array.
	 *
	 * @param encryptedInput the encrypted input
	 * @return the string
	 * @throws InvalidKeyException the invalid key exception
	 * @throws NoSuchPaddingException the no such padding exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws BadPaddingException the bad padding exception
	 * @throws IllegalBlockSizeException the illegal block size exception
	 */
	public String decrypt(String encryptedInput) throws  InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
		
		String keyString = encryptedInput.substring(0, controlLength);		
		LOGGER.debug("dencryption key : {}", keyString);
		byte[] keyByte = keyString.getBytes(StandardCharsets.UTF_8);
		String data = encryptedInput.substring(controlLength, encryptedInput.length());
		byte[] dataByte = DatatypeConverter.parseBase64Binary(data);

				//data.getBytes(StandardCharsets.UTF_8);
		SecretKeySpec secretKey = new SecretKeySpec(keyByte, ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return new String(cipher.doFinal(dataByte));
	}
	
	/**
	 * Random string.
	 *
	 * @return the byte[]
	 */
	public byte[] randomString() {  	  
        int length = controlLength;
        boolean useLetters = true;
        boolean useNumbers = false;         
        return RandomStringUtils.random(length, useLetters, useNumbers).getBytes(StandardCharsets.UTF_8);
    }
	
	/**
	 * Shuffle.
	 *
	 * @param input the input
	 * @return the string
	 */
	public String shuffle(String input){
		String alphaAndDigitsInput = ALNUM.retainFrom(input);
		String alphaAndDigitsInputReverse = new StringBuilder(alphaAndDigitsInput).reverse().toString();
		int mid = alphaAndDigitsInput.length() / 2;
	    String fill = (alphaAndDigitsInput.length() % 2 != 0) ? (alphaAndDigitsInput.substring(0, mid) + alphaAndDigitsInput.substring(mid + 1, alphaAndDigitsInput.length())) : (alphaAndDigitsInput.substring(0, mid - 1) + alphaAndDigitsInput.substring(mid + 1, alphaAndDigitsInput.length()));
	    if(alphaAndDigitsInputReverse.length() >= controlLength) {
	    	return alphaAndDigitsInputReverse.substring(0, controlLength);	
	    }
	    return StringUtils.leftPad(alphaAndDigitsInputReverse, controlLength, fill);
	    
    }
}