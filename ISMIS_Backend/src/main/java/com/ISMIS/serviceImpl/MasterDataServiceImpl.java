package com.ISMIS.serviceImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISMIS.dao.MasterDataDao;
import com.ISMIS.dto.Response;
import com.ISMIS.model.AgeClassBean;
import com.ISMIS.model.AttributeBean;
import com.ISMIS.model.ClientBmiClassBean;
import com.ISMIS.model.HomeBean;
import com.ISMIS.service.MasterDataService;

@Service
public class MasterDataServiceImpl implements MasterDataService {

	static Logger logger = LogManager.getLogger(MasterDataServiceImpl.class);

	@Autowired
	private MasterDataDao masterDataDao;

	@Override
	public Response getHomeList() {

		Response response = null;
		List<HomeBean> homeList = null;

		try {
			homeList = masterDataDao.getHomeList();
			response = new Response();

			if (homeList == null) {
				response.setKey(2);
				response.setMessage("Some error has taken place.");
			} else {
				if (homeList.isEmpty()) {
					response.setKey(1);
					response.setMessage("No data found.");
				} else {
					response.setKey(0);
					response.setValue(homeList);
				}
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - MasterDataServiceImpl ~~ method- getHomeList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}

	@Override
	public Response getCategoryWiseAttrList(int categoryId) {
		Response response = null;
		List<AttributeBean> categoryList = null;

		try {
			categoryList = masterDataDao.getCategoryWiseAttrList(categoryId);
			response = new Response();

			if (categoryList == null) {
				response.setKey(2);
				response.setMessage("Some error has taken place.");
			} else {
				if (categoryList.isEmpty()) {
					response.setKey(1);
					response.setMessage("No data found.");
				} else {
					response.setKey(0);
					response.setValue(categoryList);
				}
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - MasterDataServiceImpl ~~ method- getHomeList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}

	@Override
	public Response getAgeClassList() {
		Response response = null;
		List<AgeClassBean> getAgeClassList = null;

		try {
			response = new Response();
			getAgeClassList = masterDataDao.getAgeClassList();

			if (getAgeClassList == null) {
				response.setKey(2);
				response.setMessage("Some error has taken place.");
			} else {
				if (getAgeClassList.isEmpty()) {
					response.setKey(1);
					response.setMessage("No data found.");
				} else {
					response.setKey(0);
					response.setValue(getAgeClassList);
				}
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - MasterDataServiceImpl ~~ method- getAgeClassList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}

	@Override
	public Response getBmiClassList() {
		Response response = null;
		List<ClientBmiClassBean> getClinetBmiClassList = null;

		try {
			response = new Response();
			getClinetBmiClassList = masterDataDao.getBmiClassList();

			if (getClinetBmiClassList == null) {
				response.setKey(2);
				response.setMessage("Some error has taken place.");
			} else {
				if (getClinetBmiClassList.isEmpty()) {
					response.setKey(1);
					response.setMessage("No data found.");
				} else {
					response.setKey(0);
					response.setValue(getClinetBmiClassList);
				}
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - MasterDataServiceImpl ~~ method- getAgeClassList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}

}
