package by.epam.dobrov.dao.impl.generator;

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
public class HashGenerationException extends RuntimeException {

	public HashGenerationException(String message) {
		super(message);
	}

	public HashGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

}
