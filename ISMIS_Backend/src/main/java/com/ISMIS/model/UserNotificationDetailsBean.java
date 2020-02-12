package com.ISMIS.model;

import java.util.Date;

import javax.servlet.http.HttpSession;

public class UserNotificationDetailsBean {

	private String userSessionIp;
	private Date userNotificationsTime;
	private UserBean userBean;

	public String getUserSessionIp() {
		return userSessionIp;
	}

	public void setUserSessionIp(String userSessionIp) {
		this.userSessionIp = userSessionIp;
	}

	public Date getUserNotificationsTime() {
		return userNotificationsTime;
	}

	public void setUserNotificationsTime(Date userNotificationsTime) {
		this.userNotificationsTime = userNotificationsTime;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}
