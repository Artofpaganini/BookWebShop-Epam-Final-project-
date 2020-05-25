package by.epam.dobrov.dao.impl.generator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

	private HashGenerator() {

	}

	public static String generateSHA256(String message) throws HashGenerationException {
		return hashString(message, "SHA-256");
	}

	private static String hashString(String message, String algorithm) throws HashGenerationException {

		/*
		 * криптографическа хеш-функция, которая может вычислять дайджест сообщения из
		 * двоичных данных. Когда вы получаете набор зашифрованных данных, вы не можете
		 * быть уверены в том, что он не был изменен во время транспортировки. Дайджест
		 * сообщения помогает решить эту проблему.
		 */
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));

			return convertByteArrayToHexString(hashedBytes);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			throw new HashGenerationException("Could not generate hash from String", ex);
		}
	}

	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1)); // 16ричная система
																									// исчисления
		}
		return stringBuffer.toString();
	}
}
