package by.epam.dobrov.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import by.epam.dobrov.controller.admin.user.ListUsersServlet;
import by.epam.dobrov.dao.GenericDAO;
import by.epam.dobrov.dao.JpaDAO;
import by.epam.dobrov.dao.impl.generator.HashGenerator;
import by.epam.dobrov.entity.Users;

public class UserDAOImpl extends JpaDAO<Users> implements GenericDAO<Users> {

	public UserDAOImpl(EntityManager entityManager) {// тут вызываем ЕМ из JPADAO
		super(entityManager);

	}

	public Users create(Users user) {
		/*
		 * при создании мы передаем пасс generateSHA256, где пароль передается в
		 * hashstring и там конвертируется с помощью байтов в 16-ричную систему и
		 * возвращается и присваиется тут в encryptedPassword, после чего
		 * encryptedPassword устанавливается как пароль для юзера
		 */
		String encryptedPassword = HashGenerator.generateSHA256(user.getPassword());
		user.setPassword(encryptedPassword);
		return super.create(user);
	}

	@Override
	public Users update(Users user) {
		return super.update(user);
	}

	@Override
	public Users get(Object userId) {

		return super.find(Users.class, userId);
	}

	public Users findByEmail(String email) {

		/*
		 * поиск ищет конкретный имейл в бд , если находит то закидывает в этот
		 * лист(работает даже если в бд 2 одинаковых мыла), если не находит то лист
		 * будет null
		 */

		List<Users> usersList = super.findByNamedQuery("Users.findByEmail", "email", email);

		if (usersList != null && usersList.size() > 0) {
			return usersList.get(0);
		}
		return null;
	}

	public boolean checkLogin(String email, String password) {

		Map<String, Object> parameters = new HashMap<>();
		
		String encryptedPassword = HashGenerator.generateSHA256(password);
		parameters.put("email", email);
		parameters.put("password", encryptedPassword);

		List<Users> listUsers = super.findByNamedQuery("Users.checkLogin", parameters);

		if (listUsers.size() == 1) {
			return true;
		}

		return false;

	}

	@Override
	public void delete(Object userId) {

		super.delete(Users.class, userId);
	}

	@Override
	public Users block(Users user) {

		return super.block(user);
	}

	@Override
	public List<Users> listAll() {
		return super.findByNamedQuery("Users.findAll");
	}

	public List<Users> listAllBlockedUsers() {
		return super.findByNamedQuery("Users.findAllWhoBlocked");
	}

	@Override
	public long count() {
		long count = super.findCountByNamedQuery("Users.CountAll");

		return count;
	}

}
