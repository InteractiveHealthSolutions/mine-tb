/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package com.ihsinformatics.minetbdashboard.server.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.dv.util.Base64;

/**
 * OpenMRS's security class deals with the hashing of passwords.
 */
public class Security {

	public static final String ENCRYPTION_KEY_SPEC = "AES";
	public static final String ENCRYPTION_CIPHER_CONFIGURATION = "AES/CBC/PKCS5Padding";
	public static final String ENCRYPTION_VECTOR_RUNTIME_PROPERTY = "encryption.vector";
	public static final String ENCRYPTION_VECTOR_DEFAULT = "9wyBUNglFCRVSUhMfsTa3Q==";
	public static final String ENCRYPTION_KEY_RUNTIME_PROPERTY = "encryption.key";
	public static final String ENCRYPTION_KEY_DEFAULT = "dTfyELRrAICGDwzjHDjuhw==";

	/**
	 * Defined encoding to avoid using default platform charset
	 */
	private static final String encoding = "UTF-8";

	/**
	 * encryption settings
	 */
	public static Log log = LogFactory.getLog(Security.class);

	/**
	 * Compare the given hash and the given string-to-hash to see if they are
	 * equal. The string-to-hash is usually of the form password + salt. <br/>
	 * <br/>
	 * This should be used so that this class can compare against the new
	 * correct hashing algorithm and the old incorrect hashin algorithm.
	 * 
	 * @param hashedPassword
	 *            a stored password that has been hashed previously
	 * @param passwordToHash
	 *            a string to encode/hash and compare to hashedPassword
	 * @return true/false whether the two are equal
	 * @throws Exception
	 * @since 1.5
	 * @should match strings hashed with incorrect sha1 algorithm
	 * @should match strings hashed with sha1 algorithm
	 * @should match strings hashed with sha512 algorithm and 128 characters
	 *         salt
	 */
	public static boolean hashMatches(String hashedPassword,
			String passwordToHash) throws Exception {
		if (hashedPassword == null || passwordToHash == null)
			throw new Exception(
					"Neither the hashed password or the password to hash cannot be null");

		return hashedPassword.equals(encodeString(passwordToHash))
				|| hashedPassword.equals(encodeStringSHA1(passwordToHash))
				|| hashedPassword
						.equals(incorrectlyEncodeString(passwordToHash));
	}

	/**
	 * This method will hash <code>strToEncode</code> using the preferred
	 * algorithm. Currently, OpenMRS's preferred algorithm is hard coded to be
	 * SHA-512.
	 * 
	 * @param strToEncode
	 *            string to encode
	 * @return the SHA-512 encryption of a given string
	 * @should encode strings to 128 characters
	 */
	public static String encodeString(String strToEncode) throws Exception {
		String algorithm = "SHA-512";
		MessageDigest md;
		byte[] input;
		try {
			md = MessageDigest.getInstance(algorithm);
			input = strToEncode.getBytes(encoding);
		} catch (NoSuchAlgorithmException e) {
			// Yikes! Can't encode password...what to do?
			log.error("Can't encode password because the given algorithm: "
					+ algorithm + "was not found! (fail)", e);
			throw new Exception(
					"System cannot find password encryption algorithm", e);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("System cannot find " + encoding + " encoding",
					e);
		}
		return hexString(md.digest(input));
	}

	/**
	 * This method will hash <code>strToEncode</code> using the old SHA-1
	 * algorithm.
	 * 
	 * @param strToEncode
	 *            string to encode
	 * @return the SHA-1 encryption of a given string
	 */
	private static String encodeStringSHA1(String strToEncode) throws Exception {
		String algorithm = "SHA1";
		MessageDigest md;
		byte[] input;
		try {
			md = MessageDigest.getInstance(algorithm);
			input = strToEncode.getBytes(encoding);
		} catch (NoSuchAlgorithmException e) {
			// Yikes! Can't encode password...what to do?
			log.error("Can't encode password because the given algorithm: "
					+ algorithm + "was not found! (fail)", e);
			throw new Exception("System cannot find SHA1 encryption algorithm",
					e);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("System cannot find " + encoding + " encoding",
					e);
		}
		return hexString(md.digest(input));
	}

	/**
	 * Convenience method to convert a byte array to a string
	 * 
	 * @param b
	 *            Byte array to convert to HexString
	 * @return Hexidecimal based string
	 */
	private static String hexString(byte[] block) {
		StringBuffer buf = new StringBuffer();
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		int len = block.length;
		int high = 0;
		int low = 0;
		for (int i = 0; i < len; i++) {
			high = ((block[i] & 0xf0) >> 4);
			low = (block[i] & 0x0f);
			buf.append(hexChars[high]);
			buf.append(hexChars[low]);
		}

		return buf.toString();
	}

	/**
	 * This method will hash <code>strToEncode</code> using SHA-1 and the
	 * incorrect hashing method that sometimes dropped out leading zeros.
	 * 
	 * @param strToEncode
	 *            string to encode
	 * @return the SHA-1 encryption of a given string
	 */
	private static String incorrectlyEncodeString(String strToEncode)
			throws Exception {
		String algorithm = "SHA1";
		MessageDigest md;
		byte[] input;
		try {
			md = MessageDigest.getInstance(algorithm);
			input = strToEncode.getBytes(encoding);
		} catch (NoSuchAlgorithmException e) {
			// Yikes! Can't encode password...what to do?
			log.error("Can't encode password because the given algorithm: "
					+ algorithm + "was not found! (fail)", e);
			throw new Exception("System cannot find SHA1 encryption algorithm",
					e);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("System cannot find " + encoding + " encoding",
					e);
		}
		return incorrectHexString(md.digest(input));
	}

	/**
	 * This method used to be the simple hexString method, however, as pointed
	 * out in ticket http://dev.openmrs.org/ticket/1178, it was not working
	 * correctly. Authenticated still needs to occur against both this method
	 * and the correct hex string, so this wrong implementation will remain
	 * until we either force users to change their passwords, or we just decide
	 * to invalidate them.
	 * 
	 * @param b
	 * @return the old possibly less than 40 characters hashed string
	 */
	private static String incorrectHexString(byte[] b) {
		if (b == null || b.length < 1)
			return "";
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			s.append(Integer.toHexString(b[i] & 0xFF));
		}
		return new String(s);
	}

	/**
	 * This method will generate a random string
	 * 
	 * @return a secure random token.
	 */
	public static String getRandomToken() throws Exception {
		Random rng = new Random();
		return encodeString(Long.toString(System.currentTimeMillis())
				+ Long.toString(rng.nextLong()));
	}

	/**
	 * encrypt text to a string with specific initVector and secretKey; rarely
	 * used except in testing and where specifically necessary
	 * 
	 * @see #encrypt(String)
	 * 
	 * @param text
	 *            string to be encrypted
	 * @param initVector
	 *            custom init vector byte array
	 * @param secretKey
	 *            custom secret key byte array
	 * @return encrypted text
	 * @throws Exception
	 * @since 1.9
	 */
	public static String encrypt(String text, byte[] initVector,
			byte[] secretKey) throws Exception {
		IvParameterSpec initVectorSpec = new IvParameterSpec(initVector);
		SecretKeySpec secret = new SecretKeySpec(secretKey, ENCRYPTION_KEY_SPEC);
		byte[] encrypted;

		try {
			Cipher cipher = Cipher.getInstance(ENCRYPTION_CIPHER_CONFIGURATION);
			cipher.init(Cipher.ENCRYPT_MODE, secret, initVectorSpec);
			encrypted = cipher.doFinal(text.getBytes(encoding));
		} catch (GeneralSecurityException e) {
			throw new Exception("could not encrypt text", e);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("System cannot find " + encoding + " encoding",
					e);
		}

		return Base64.encode(encrypted);
	}

	/**
	 * decrypt text to a string with specific initVector and secretKey; rarely
	 * used except in testing and where specifically necessary
	 * 
	 * @see #decrypt(String)
	 * 
	 * @param text
	 *            text to be decrypted
	 * @param initVector
	 *            custom init vector byte array
	 * @param secretKey
	 *            custom secret key byte array
	 * @return decrypted text
	 * @throws Exception
	 * @since 1.9
	 */
	public static String decrypt(String text, byte[] initVector,
			byte[] secretKey) throws Exception {
		IvParameterSpec initVectorSpec = new IvParameterSpec(initVector);
		SecretKeySpec secret = new SecretKeySpec(secretKey, ENCRYPTION_KEY_SPEC);
		String decrypted = null;

		try {
			Cipher cipher = Cipher.getInstance(ENCRYPTION_CIPHER_CONFIGURATION);
			cipher.init(Cipher.DECRYPT_MODE, secret, initVectorSpec);
			byte[] original = cipher.doFinal(Base64.decode(text));
			decrypted = new String(original, encoding);
		} catch (GeneralSecurityException e) {
			throw new Exception("could not decrypt text", e);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("System cannot find " + encoding + " encoding",
					e);
		}

		return decrypted;
	}

	/**
	 * generate a new cipher initialization vector; should only be called once
	 * in order to not invalidate all encrypted data
	 * 
	 * @return a random array of 16 bytes
	 * @since 1.9
	 */
	public static byte[] generateNewInitVector() {
		// initialize the init vector with 16 random bytes
		byte[] initVector = new byte[16];
		new SecureRandom().nextBytes(initVector);

		// TODO get the following (better) method working
		// Cipher cipher = Cipher.getInstance(CIPHER_CONFIGURATION);
		// AlgorithmParameters params = cipher.getParameters();
		// byte[] initVector =
		// params.getParameterSpec(IvParameterSpec.class).getIV();

		return initVector;
	}

	/**
	 * generate a new secret key; should only be called once in order to not
	 * invalidate all encrypted data
	 * 
	 * @return generated secret key byte array
	 * @throws Exception
	 * @since 1.9
	 */
	public static byte[] generateNewSecretKey() throws Exception {
		// Get the KeyGenerator
		KeyGenerator kgen = null;
		try {
			kgen = KeyGenerator.getInstance(ENCRYPTION_KEY_SPEC);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("Could not generate cipher key", e);
		}
		kgen.init(128); // 192 and 256 bits may not be available

		// Generate the secret key specs.
		SecretKey skey = kgen.generateKey();

		return skey.getEncoded();
	}

}
