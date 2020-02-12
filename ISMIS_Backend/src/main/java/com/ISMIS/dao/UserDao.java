package com.ISMIS.dao;

import com.ISMIS.model.UserBean;

public interface UserDao {

	String checkLogin(UserBean userBean);

	Object getMenuList(UserBean userBean);

}
