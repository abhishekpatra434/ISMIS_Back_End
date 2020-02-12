package com.ISMIS.serviceImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISMIS.dao.UserDao;
import com.ISMIS.dto.Response;
import com.ISMIS.model.MenuBean;
import com.ISMIS.model.UserBean;
import com.ISMIS.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public Response checkLogin(UserBean userBean) {

		Response response = null;
		try {
			String status = userDao.checkLogin(userBean);
			response = new Response();

			if (status != null) {
				response.setKey(2);
				response.setMessage(status);
			} else {
				if (userBean.getUserId() == null) {
					response.setKey(1);
					response.setMessage("Invalid Login");
				} else {
					response.setKey(0);
					response.setValue(userBean);
				}
			}
		} catch (Exception ex) {
			logger.info("Problem in Class - UserServiceImpl ~~ method- checkLogin() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}

	@Override
	public Response menuList(UserBean userBean) {
		Response response = new Response();
		List<MenuBean> menuList = null;
		Object object = null;
		try {
			
			object = userDao.getMenuList(userBean);
			
			if(object == null) {
				response.setKey(2);
				response.setMessage("Some internal error ha taken place");
			}else if(object instanceof String) {
				response.setKey(1);
				response.setMessage(object.toString());
			}else {
				menuList = (List<MenuBean>) object;
				response.setKey(0);
				response.setValue(menuList);
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - UserServiceImpl ~~ method- checkLogin() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

}
