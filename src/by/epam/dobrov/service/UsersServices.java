package by.epam.dobrov.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.dobrov.dao.impl.UserDAOImpl;
import by.epam.dobrov.dao.impl.generator.HashGenerator;
import by.epam.dobrov.entity.Users;

public class UsersServices {
	private final static Logger LOGGER = LoggerFactory.getLogger(UsersServices.class);

	private UserDAOImpl userDAOImpl;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public UsersServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		userDAOImpl = new UserDAOImpl();
	}

	public void listUser() throws ServletException, IOException {

		LOGGER.info("Got list of users from DB");

		List<Users> listUsers = userDAOImpl.listAll();
		request.setAttribute("listUsers", listUsers);
		// РЅРµ СЃРѕРІСЃРµРј РїРѕРЅСЏС‚РЅРѕ, РЅРѕ РґР°Р»СЊС€Рµ РЅР° jsp СЃС‚СЂР°РЅРёС†Рµ РјС‹ РёР·РІР»РµРєР°РµРј
		// РґР°РЅРЅС‹Рµ РёР· Р»РёСЃС‚Р®Р·РµСЂСЃ, СЃСЋРґР°

		String listpage = "user_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);// РїСЂРёСЃРІР°РёРІР°РµРј РѕР±СЉРєС‚Сѓ
		/*
		 * СЂРµРєРІРµСЃС‚Р”РёСЃРїСЂРµС‡РµСЂ РЅР°С€ Р·Р°РїСЂРѕСЃ, Рё РІ РјРµС‚РѕРґ Р“РµС‚Р РёРєРІРµСЃС‚Р”РёСЃРїСЂ РїРµСЂРµРґР°РµРј РЅР°С€ Р°РґСЂРµСЃ РЅР°
		 * РєРѕС‚РѕСЂС‹Р№ РјС‹ С…РѕС‚РёРј РїРµСЂРµРІРµСЃС‚Рё РїРѕР»СЊР·РѕРІР°С‚РµР»СЏ СЂРёРµРєРІРµСЃС‚ Рё СЂРµСЃРїРѕРЅСЃ С‚СѓС‚ РґР»СЏ С‚РѕРіРѕ С‡С‚РѕР±С‹
		 * РјС‹ РїРѕРЅРёРјР°Р»Рё С‡С‚Рѕ РЅР°С€ Р·Р°РїСЂРѕСЃ РЅРёРєСѓРґР° РЅРµ РґРµРІР°РµС‚СЃСЏ, Р° РїСЂРѕСЃС‚Рѕ РїРµСЂРµРЅР°РїСЂР°РІР»СЏРµС‚СЃСЏ РЅР°
		 * РґСЂСѓРіРѕР№ РІРЅСѓС‚СЂ Р°РґСЂРµСЃ
		 */
		requestDispatcher.forward(request, response);
		/*
		 * С„РѕСЂРІР°СЂРґ РїРµСЂРµРЅР°РїСЂР°РІСЏР»РµС‚ СЃР°Рј, Р±РµР· РІРµРґРѕРјР° РєР»РёРµРЅС‚Р°, Р° СЂРµРґРёСЂРµРєС‚ СЃРєР°Р¶РµС‚ Рѕ С‚РѕРј С‡С‚Рѕ
		 * РїРµСЂРµРЅР°РїСЂР°РІР»СЏРµС‚, СЂР°Р·РЅРёС†Р° РјРµР¶РґСѓ РЅРёРјРё, СЂРµРґРёРµРєС‚ РїСЂРѕРёСЃС…РѕРґРёС‚ РЅР° РєР»РёРµРЅС‚Рµ (Р±СЂР°СѓР·РµСЂ
		 * РґРµР»Р°РµС‚ РЅРѕРІС‹Р№ Р·Р°РїСЂРѕСЃ) С„РѕСЂРІР°СЂРґ РїСЂРѕРёСЃС…РѕРґРёС‚ РЅР° СЃРµСЂРІРµСЂРµ . РєР»РёРµРЅС‚ РЅРµ Р·РЅР°РµС‚ РѕР±
		 * СЌС‚РѕРј,РїСЂРё 1 РЈР Р› РјРµРЅСЏРµС‚СЃСЏ, РїСЂРё 2 РЅРµ РјРµРЅСЏРµС‚СЃСЏ, СЂРµРґРёСЂРµРєС‚ РјРµРґР»РµРЅРЅРµРµ С‚.Рє. Р±РѕР»СЊС€Рµ
		 * РѕРїРµСЂР°С†РёР№ Р’РђР–РќР«Р™ РњРћРњР•РќРў С„РѕСЂРІР°СЂРґ СЂР°Р±РѕС‚Р°РµС‚ С‚РѕР»СЊРєРѕ РїРµСЂРµРЅР°РїСЂР°РІР»РµРЅРёРµРј РЅР° РІРЅСѓС‚СЂРµРЅРЅРёРµ
		 * Р°РґСЂРµСЃР° , С‚.Рµ. РІ РЅР°С€РµРј СЃР»СѓС‡Р°Рµ РЅР° РЅР°С€Рё jsp С„Р°Р№Р»С‹ , РЅР° РІРЅРµС€РЅРёРµ С‚РёРїР° РіСѓРіР» РЅРµР»СЊР·СЏ
		 */
	}

	public void createUser() throws ServletException, IOException {

		LOGGER.info("Try to create a new Users");

		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		// РІРѕР·РјРѕР¶РЅРѕ РЅСѓР¶РµРЅ Р±СѓРґРµС‚ СЃС‚Р°С‚СѓСЃ

		Users existUser = userDAOImpl.findByEmail(email);

		if (existUser != null) { // РїСЂРѕРІРµСЂСЏРµРј С‡С‚РѕР±С‹ РёРјРµР№Р» РЅРµ Р±С‹Р» РїРѕРІС‚РѕСЏСЋС‰РёРјСЃСЏ
			String message = " User  with this  email " + email + " is already exist!!!";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);

			LOGGER.warn("Get warning, User  with this  email {} is already exist!!!", email);

		} else {

			Users newUser = new Users();
			
			newUser.setEmail(email);
			newUser.setFullName(fullName);
			
			if (password != null & !password.isEmpty()) {
				String encryptedPassword = HashGenerator.generateSHA256(password);
				newUser.setPassword(encryptedPassword);
			}
			
			userDAOImpl.create(newUser);
			listUser(); // РѕР±РЅРѕРІР»СЏРµРј РёРЅС„РѕСЂРјР°С†Рё Рѕ РїРѕР»СЊР·РѕРІР°С‚РµР»СЏС…

			LOGGER.info("Created a new User and add information: {}; {}; {};  ,in DB", email, fullName, password);
		}
	}

	public void editUser() throws ServletException, IOException {// РјРµС‚РѕРґ РІС‹С‚СЏРіРёРІР°РµС‚ РЅРѕРјРµСЂ Р�Р” РєРѕС‚РѕСЂС‹Р№ РёРґРµС‚ РїСЂРё РЅР°РІРµРґРµРЅРёРё
																	// РїРѕСЃР»Рµ ?id=... Рё РІС‹Р·С‹РІР°РµС‚
		LOGGER.info("Edit User by ID");
		// РјРµС‚РѕРґ РіРµС‚ РёР· Р®Р·РµСЂР”Р°РѕР�РњРџР›
		int userId = Integer.parseInt(request.getParameter("id"));
		Users user = userDAOImpl.get(userId);

		/*
		 * РџРµСЂРµРґР°С‡Р° РґР°РЅРЅС‹С… РѕСЃСѓС‰РµСЃС‚РІР»СЏРµС‚СЃСЏ СЃ РїРѕРјРѕС‰СЊСЋ РјРµС‚РѕРґР° setAttribute(name, value),
		 * РіРґРµ name - СЃС‚СЂРѕРєРѕРІРѕРµ РЅР°Р·РІР°РЅРёРµ РґР°РЅРЅС‹С…, Р° value - СЃР°РјРё РґР°РЅРЅС‹Рµ, РєРѕС‚РѕСЂС‹Рµ РјРѕРіСѓС‚
		 * РїСЂРµРґСЃС‚Р°РІР»СЏС‚СЊ СЂР°Р·Р»РёС‡РЅС‹Рµ РґР°РЅРЅС‹Рµ.
		 */
		String destPage = "user_form.jsp";

		if (user == null) {
			destPage = "message.jsp";
			String errorMessage = "Could not find user with ID " + userId;
			request.setAttribute("message", errorMessage);

			LOGGER.warn("Could not find user with ID {}", userId);

		} else {
			/*
			 * СѓСЃС‚Р°РЅР°РІР»РёРІР°РµРј РїР°СЂРѕР»СЊ СЂР°РІРЅС‹Рј РЅСѓР»СЋ, С‡С‚РѕР±С‹ РїР°СЂРѕР»СЊ РѕСЃС‚Р°РІР°Р»СЃСЏ РїСѓСЃС‚С‹Рј РїРѕ СѓРјРѕР»С‡Р°РЅРёСЋ
			 * РµСЃР»Рё РѕСЃС‚Р°РІРёС‚СЊ РїСѓСЃС‚С‹Рј, РїР°СЂРѕР»СЊ РїРѕР»СЊР·РѕРІР°С‚РµР»СЏ РЅРµ Р±СѓРґРµС‚ РѕР±РЅРѕРІР»РµРЅ СЌС‚Рѕ РґР»СЏ СЂР°Р±РѕС‚С‹ СЃ
			 * С„СѓРЅРєС†РёРµР№ Р·Р°С€РёС„СЂРѕРІР°РЅРЅС‹Рј РїР°СЂРѕР»РµРј
			 */
			user.setPassword(null); //РѕР±РЅСѓР»СЏРµРј РїР°СЂРѕР»СЊ С‡С‚РѕР±С‹ РµРіРѕ РЅРµ Р±С‹Р»Рѕ РІ РєРѕР»РѕРЅРєРµ РїР°СЃСЃРІРѕСЂРґ РїСЂРё Р°РїРґРµР№С‚Рµ
			request.setAttribute("user", user);

		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
		requestDispatcher.forward(request, response);

		/**
		 * РІ СЌС‚РѕРј РјРµС‚РѕРґРµ РјС‹ РїРѕР»СѓС‡Р°РµРј СЃ РїРѕРјРѕС‰СЊСЋ Р�Рґ , СЋР·РµСЂР° СЃРѕ РІСЃРµРјРё РµРіРѕ РґР°РЅРЅС‹РјРё, РµСЃР»Рё
		 * СЋР·РµСЂ =РЅСѓР» С‚Рѕ РѕС€РёР±РєР°, РµСЃР»Рё РЅРµС‚ , С‚Рѕ РѕР±РЅСѓР»СЏРµРј РµРіРѕ РїР°СЂРѕР»СЊ РґР»СЏ СЂР°Р±РѕС‚С‹ СЃ
		 * Р·Р°С€РёС„СЂРѕРІР°РЅРЅС‹Рј РїР°СЂРѕР»РµРј Рё РѕС‚РїСЂР°РІР»СЏРµРј Р·Р°РїСЂРѕСЃ РЅР° СЌС‚РѕРіРѕ СЋР·РµСЂР°
		 */
	}

	public void updateUser() throws ServletException, IOException { //

		LOGGER.info("Update some information about User");

		int userId = Integer.parseInt(request.getParameter("userId"));
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");

		Users userById = userDAOImpl.get(userId);

		Users userByEmail = userDAOImpl.findByEmail(email);
		/*
		 * РёРґРµС‚ РїСЂРѕРІРµСЂРєР° СЋР·РµСЂР° РЅР° РёРґ РёР»Рё РёРјРµР№Р», РЅР° РєРѕС‚РѕСЂС‹Рµ С…РѕС‚РёРј Р·Р°РјРµРЅРёС‚СЊ , РµСЃР»Рё С‚Р°РєРѕРІС‹Рµ
		 * РѕР±РЅР°СЂСѓР¶РёР»РёСЃСЊ С‚Рѕ РѕС€РёР±РєР°, РµСЃР»Рё РЅРµС‚ С‚Рѕ РїРµСЂРµР·Р°РїРёСЃС‹РІР°РµРј Р° С‚Р°Рє Р¶Рµ РїСЂРѕРІРµСЂРєР° РїР°СЂРѕР»СЏ
		 * РµСЃС‚СЊ РїР°СЂРѕР»СЊ РЅРµ СЂР°РІРµРЅ РЅСѓР»Р» Р»Рё РЅРµ РїСѓСЃС‚РѕР№
		 */
		if (userByEmail != null && userByEmail.getUsersId() != userById.getUsersId()) {
			String message = "Could not update user. User with email " + email + " already exists.";
			request.setAttribute("message", message);

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);

			LOGGER.warn("Couldn't update user.User with this email is already exist!");

		} else {
			userById.setEmail(email);
			userById.setFullName(fullName);
	

			if (password != null & !password.isEmpty()) {
				String encryptedPassword = HashGenerator.generateSHA256(password);
				userById.setPassword(encryptedPassword);
			}

			userDAOImpl.update(userById);

			listUser();

			LOGGER.info("User has been updated successfully");
		}

		/*
		 * Рё РµСЃР»Рё РѕРЅ РЅРµ РїСЂРёРЅР°РґР»РµР¶РёС‚ С‚РµРєСѓС‰РµРјСѓ СЂРµРґР°РєС‚РёСЂСѓСЋС‰РµРјСѓ РїРѕР»СЊР·РѕРІР°С‚РµР»СЋ, РјС‹ РґРѕР»Р¶РЅС‹
		 * РїРѕРєР°Р·Р°С‚СЊ СЃРѕРѕР±С‰РµРЅРёРµ РѕР± РѕС€РёР±Рєt РїСЂР°РІРёР»СЊРЅРѕ? Рё РµСЃР»Рё СЌР»РµРєС‚СЂРѕРЅРЅР°СЏ РїРѕС‡С‚Р° РїСЂРёРЅР°РґР»РµР¶РёС‚
		 * С‚РµРєСѓС‰РµРјСѓ РїРѕР»СЊР·РѕРІР°С‚РµР»СЋ СЂРµРґР°РєС‚РёСЂРѕРІР°РЅРёСЏ - СЌС‚Рѕ РѕР·РЅР°С‡Р°РµС‚, С‡С‚Рѕ РјС‹ РїСЂРѕСЃС‚Рѕ РѕР±РЅРѕРІР»СЏРµРј
		 * РґСЂСѓРіРёРµ РґР°РЅРЅС‹Рµ РїРѕР»СЊР·РѕРІР°С‚РµР»СЏ
		 */

	}

// РµСЃР»Рё РёРґ РЅРµ РЅР°Р№РґРµРЅ С‚Рѕ СЃРѕРѕР±С‰РµРЅРёРµ Рѕ С‚РѕРј С‡С‚Рѕ РёРґ СѓР¶Рµ РЅРµ СЃСѓС‰РµСЃС‚РІСѓРµС‚ 
	public void deleteUser() throws ServletException, IOException { // СѓРґР°Р»РµРЅРёРµ РїРѕР»СЊР·РѕРІР°С‚РµР»СЏ

		LOGGER.info("Delete User from user list and from DB");

		int userId = Integer.parseInt(request.getParameter("id"));

		Users userById = userDAOImpl.get(userId);
		String message;

		if (userId == 1) {
			message = "The default admin user account cannot be deleted.";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);

			LOGGER.warn("The default admin user account cannot be deleted.");
			return;
		}
		if (userById == null) {
			message = "Couldn't find user with ID " + userId + " or this user deleted by another admin";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
			LOGGER.warn("Couldn't find user with ID {} or this user deleted by another admin", userId);
		} else {
			userDAOImpl.delete(userId);
			listUser();

			LOGGER.info("User has been deleted");
		}

	}

	public void login() throws ServletException, IOException { // РїРѕРЅСЏС‚СЊ РєР°Рє СЂР°Р±РѕС‚Р°РµС‚ РјРµС‚РѕРґ Р›РѕРіРёРЅ 17-56

		LOGGER.info("User is  trying to get the login access ");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		boolean loginResult = userDAOImpl.checkLogin(email, password);

		/*
		 * РњС‹ РґРѕР»Р¶РЅС‹ С…СЂР°РЅРёС‚СЊ СЃРѕСЃС‚РѕСЏРЅРёРµ РёР·РµСЂР° РЅР° СЃРµСЂРІРµСЂРµ, РґР»СЏ СЌС‚РѕРіРѕ РёСЃРї РЎРµСЃСЃРёРё,СЃРµСЃСЃРёРё
		 * РјРѕРіСѓС‚ С…СЂР°РЅРёС‚СЊ Р»СЋР±С‹Рµ Р·РЅР°С‡РµРЅРёСЏ РІ РїР°РјСЏС‚Рё СЃРµСЂРІРµСЂР°, Рё РѕРЅРё РіРѕСЂР°Р·РґРѕ Р±С‹СЃС‚СЂРµРµ С‡РµРј РµСЃР»Рё
		 * РёСЃРї Р”Р‘, РјС‹ РјРѕР¶РµРј СѓРґР°Р»РёС‚СЊ СЃРѕСЃС‚РѕСЏРЅРёРµСЋРµР·СЂР° РєР°Рє С‚РѕР»СЊРєРѕ РѕРЅ РІС‹Р№РґРµС‚ РёР· СЃРёСЃС‚РµРјС‹, РґР»СЏ
		 * СЂРµР°Р»РёР·Р°С†РёРё Р»РѕРіР�Рќ/РђРЈРў РјС‹ РґРѕР»Р¶РЅС‹ РёСЃРї СЃРµСЃСЃРёРё РЁР°РіРё - РїРѕР»СѓС‡РµРЅРёРµ СЃРµСЃСЃРёРё, Р·Р°РґР°РµРј Рё
		 * С…СЂР°РЅРёРј Р°С‚СЂРёР±СѓС‚СЃ СЃРµСЃСЃРёРё СЃ РёРјРµРЅРµРј Рё Р·РЅР°С‡РµРЅРёРµРј,С‡РёС‚Р°РµРј Р°С‚СЂРёР±СѓС‚ СЃРµСЃСЃРёРё Рё СѓРґР°Р»СЏРµРј
		 * 
		 */
		if (loginResult) {

			request.getSession().setAttribute("useremail", email);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");// РїРµСЂРµРЅР°РїСЂР°РІР»СЏРµРј РЅР° СЃС‚СЂР°РЅРёС†Сѓ Р°РґРјРёРЅР°
			dispatcher.forward(request, response);

			LOGGER.debug("User is authenticated");
		} else {
			/*
			 * СЂР°Р·РЅРёС†Р° РјРµР¶РґСѓ request.getSession().setAttribute Рё РїСЂРѕСЃС‚Рѕ СЂРёРєРІРµСЃС‚ СЃРµС‚ Р°СЂС‚РёР±СѓС‚,
			 * РІ С‚РѕРј С‡С‚Рѕ РїСЂРѕСЃС‚Рѕ СЂРёРєРІРµСЃС‚ РїРѕР»СѓС‡РµРЅРЅРѕРµ Р·РЅР°С‡РµРЅРёРµ Р¶РёРІРµС‚ С‚РѕР»СЊРєРѕ С‚Рѕ РІСЂРµРјСЏ РїРѕРєР° СЌС‚РѕС‚
			 * СЂРёРєРІРµСЃС‚ Р°РєС‚РёРІРµРЅ Рё РїРѕС‚РѕРј СѓРґР°Р»СЏРµС‚СЃСЏ , Р° Р·РЅР°С‡РµРЅРёРµ РєРѕС‚РѕСЂРѕРµ С…СЂР°РЅРёС‚СЊСЃСЏ РІ СЃРµСЃСЃРёРё,
			 * РѕСЃС‚Р°РµС‚СЃСЏ РЅР° СЃРµСЂРІРµСЂРµ ,СЃС‚РѕР»СЊРєРѕ Р¶Рµ СЃРєРѕР»СЊРєРѕ Рё СЃРµСЃСЃРёСЏ
			 */
			String message = "Login access failed! ";
			request.setAttribute("message", message);

			/*
			 * РїРµСЂРµРЅР°РїСЂР°РІР»СЏРµРј РЅР° СЃС‚СЂР°РЅРёС†Сѓ Р›РћРіРёРЅ Рё РґРѕР±Р°РІР»СЏРµРј С‚Р°Рј РїСЂРµРґР»РѕР¶РµРЅРёРµ СЃ РѕС€РёР±РєРѕР№
			 * РґРѕСЃС‚СѓРїР°
			 */
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");

			dispatcher.forward(request, response);

			LOGGER.warn("Login access failed");
		}
	}

}
