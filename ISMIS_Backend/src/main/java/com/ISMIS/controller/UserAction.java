package com.ISMIS.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ISMIS.commonUtility.GetUserIp;
import com.ISMIS.commonUtility.PersonalUtility;
import com.ISMIS.commonUtility.URIConstant;
import com.ISMIS.dto.Response;
import com.ISMIS.model.UserBean;
import com.ISMIS.model.UserNotificationDetailsBean;
import com.ISMIS.service.UserApplicationDetails;
//import com.ISMIS.model.UserNotificationDetailsBean;
//import com.ISMIS.service.UserApplicationDetails;
import com.ISMIS.service.UserService;

@RestController
//@CrossOrigin(origins = "http://172.16.1.24:4400")
//@CrossOrigin(origins = "*")
public class UserAction {

	static Logger logger = LogManager.getLogger(UserAction.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String homePage(HttpServletRequest req, HttpServletResponse res) throws SQLException {
		System.out.println("hloo    ");
		return "Welcome to IS-MIS Home o ss";
	}
	

	//Testing
	/*
	 * @RequestMapping(value = URIConstant.USER_SIGNUP, method = RequestMethod.POST)
	 * public @ResponseBody Response login(@RequestBody UserBean userBean) {
	 * 
	 * Response response = null; response = userService.checkLogin(userBean);
	 * 
	 * return response; }
	 */
	
	//Production
	@RequestMapping(value = URIConstant.USER_SIGNUP, method = RequestMethod.POST)
	public @ResponseBody Response login(@RequestBody UserBean userBean, HttpServletRequest req,
			HttpServletResponse res) {

		Response response = null;
		String usessionId = null;
		String tokenId = null;
		UserNotificationDetailsBean userNotificationDetailsBean = null;
		boolean isActive = false;
		try {

			response = userService.checkLogin(userBean);

			if (response.getKey() == 0) {

				// Assuming the user already logged in from any device..
				boolean isUserAlreadyLogedIn = true;

				// Fatch User information from map ...
				for (Map.Entry<String, UserNotificationDetailsBean> entity : UserApplicationDetails.APPDET.entrySet()) {

					UserNotificationDetailsBean _userNotificationDetailsBean = entity.getValue();

					if (userBean.getUserId().equals(_userNotificationDetailsBean.getUserBean().getUserId())
							&& userBean.getHomeId().equals(_userNotificationDetailsBean.getUserBean().getHomeId())) {
						usessionId = entity.getKey();
						userNotificationDetailsBean = _userNotificationDetailsBean;
						break;
					}
				}

				// Checking user already login or not
				if (userNotificationDetailsBean == null) {
					isUserAlreadyLogedIn = false;
				} else {
					// Is user is already logged in, and user does a abnormal termination of browser
					// or tab or mechine
					Date prevDate = userNotificationDetailsBean.getUserNotificationsTime();
					Date currentDate = new Date();
					long seconds = (currentDate.getTime() - prevDate.getTime()) / 1000;
					if (seconds > 60) {
						// If UserApplicationDetails map already updated by this user over 60 seconds
						UserApplicationDetails.APPDET.remove(usessionId);
						isUserAlreadyLogedIn = false;
					}
				}

				if (!isUserAlreadyLogedIn) {

					// Creating new session for new user and inserting data into map. ..
					userNotificationDetailsBean = new UserNotificationDetailsBean();
					userNotificationDetailsBean.setUserNotificationsTime(new Date());
					userNotificationDetailsBean.setUserBean((UserBean) response.getValue());
					userNotificationDetailsBean.setUserSessionIp(GetUserIp.getClientIpAddress(req));

					synchronized (UserAction.class) {

						// Fatch User information from map ...
						for (Map.Entry<String, UserNotificationDetailsBean> entity : UserApplicationDetails.APPDET
								.entrySet()) {

							UserNotificationDetailsBean _userNotificationDetailsBean = entity.getValue();

							if (userBean.getUserId().equals(_userNotificationDetailsBean.getUserBean().getUserId())
									&& userBean.getHomeId()
											.equals(_userNotificationDetailsBean.getUserBean().getHomeId())) {
								// if user is already logged in by any thread
								isActive = true;
								break;
							}
						}

						if (!isActive) {
							// Insert At Map at Login time
							do {
								tokenId = PersonalUtility.SessionTokenGenerater();
							} while (UserApplicationDetails.APPDET.get(tokenId) != null);

							UserApplicationDetails.APPDET.put(tokenId, userNotificationDetailsBean);
							response.setMessage(tokenId);
							logger.info(tokenId);
						} else {
							response = new Response();
							response.setKey(3);
							response.setMessage("You have already logged in from another device.");
						}

					} // end of synchronized block for double check locking

				} else {
					response = new Response();
					response.setKey(3);
					response.setMessage("You have already logged in from another device.");
				}
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - UserAction ~~ method- login() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
			response = new Response();
			response.setKey(2);
			response.setMessage("Some error has taken place.");
		}

		return response;
	}
		

	@RequestMapping(value = URIConstant.GET_MENU_LIST, method = RequestMethod.POST)
	public @ResponseBody Response getMenuList(@RequestBody UserBean userBean) {
		Response response = null;
		response = userService.menuList(userBean);

		return response;
	}

	@RequestMapping(value = "/invalidSession")
	public @ResponseBody Response invalidSession() {
		Response response = new Response();
		try {
			response.setKey(2);
			response.setMessage("Invalid Session");
		} catch (Exception ex) {
			logger.info("Problem in Class - UserAction ~~ method- invalidSession() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@RequestMapping(value = "/logOut")
	public @ResponseBody Response logOut(@RequestBody UserBean userBean, HttpServletRequest req,
			HttpServletResponse res) {

		Response response = new Response();

		try {
			String usesionId = req.getHeader("usessionId");

			if (UserApplicationDetails.APPDET.get(usesionId) != null) {

				UserNotificationDetailsBean getUserNotificationDetailsBean = UserApplicationDetails.APPDET
						.get(usesionId);

				if (userBean.getUserId().equals(getUserNotificationDetailsBean.getUserBean().getUserId())
						&& userBean.getHomeId().equals(getUserNotificationDetailsBean.getUserBean().getHomeId())) {
					UserApplicationDetails.APPDET.remove(usesionId);
					response.setKey(0);
					response.setMessage("Successfully LoggedOut");
				} else {
					response.setKey(1);
					response.setMessage("UESR ID OR HOME ID DID NOT MATCHED");
				}
			} else {
				response.setKey(2);
				response.setMessage("Invalid Session");
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - UserAction ~~ method- logOut() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
			response.setKey(2);
			response.setMessage(sStackTrace);
		}
		return response;
	}

	@RequestMapping(value = "/getNotifications")
	public @ResponseBody void getPendingNotifications(HttpServletRequest request, HttpServletResponse response) {

		try {
			String usesionId = request.getHeader("usessionId");
			UserNotificationDetailsBean getUserNotificationDetailsBean = UserApplicationDetails.APPDET.get(usesionId);

			// Added for updating session list...
			UserNotificationDetailsBean userNotificationDetailsBean = new UserNotificationDetailsBean();
			userNotificationDetailsBean.setUserNotificationsTime(new Date());
			userNotificationDetailsBean.setUserSessionIp(GetUserIp.getClientIpAddress(request));
			userNotificationDetailsBean.setUserBean(getUserNotificationDetailsBean.getUserBean());
			UserApplicationDetails.APPDET.put(usesionId, userNotificationDetailsBean);

		} catch (Exception ex) {
			logger.info("Problem in Class - UserAction ~~ method- getPendingNotifications() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
	}

}
