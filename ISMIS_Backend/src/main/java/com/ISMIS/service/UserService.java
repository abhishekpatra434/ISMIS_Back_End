package com.ISMIS.service;

import com.ISMIS.dto.Response;
import com.ISMIS.model.UserBean;

public interface UserService {

	Response checkLogin(UserBean userBean);
	
	Response menuList(UserBean userBean);

}
