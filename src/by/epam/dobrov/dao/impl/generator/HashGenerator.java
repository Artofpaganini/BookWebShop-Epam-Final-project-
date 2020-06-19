package by.epam.dobrov.dao.impl.generator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.dobrov.service.BookServices;

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
public class HashGenerator {

	private final static Logger LOGGER = LoggerFactory.getLogger(HashGenerator.class);

	private HashGenerator() {

	}

	public static String generateSHA256(String message) throws HashGenerationException {
		return hashString(message, "SHA-256");
	}

	private static String hashString(String message, String algorithm) throws HashGenerationException {
		LOGGER.info("Get string value  for encryption in SHA-256");
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));

			return convertByteArrayToHexString(hashedBytes);

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {

			LOGGER.error("Get an exception. Could not generate hash from String");

			throw new HashGenerationException("Could not generate hash from String", ex);

		}

	}

	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < arrayBytes.length; i++) {

			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}
}
