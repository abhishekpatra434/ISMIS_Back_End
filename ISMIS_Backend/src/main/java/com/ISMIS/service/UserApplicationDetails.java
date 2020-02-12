package com.ISMIS.service;

import java.util.concurrent.ConcurrentHashMap;

import com.ISMIS.model.UserNotificationDetailsBean;

public interface UserApplicationDetails {
	ConcurrentHashMap<String, UserNotificationDetailsBean> APPDET = new ConcurrentHashMap<String, UserNotificationDetailsBean>();
}
