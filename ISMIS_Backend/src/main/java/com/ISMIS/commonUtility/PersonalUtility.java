package com.ISMIS.commonUtility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ISMIS.model.ClientMseTemplateBean;

public class PersonalUtility {

	static Logger logger = LogManager.getLogger(PersonalUtility.class);

	public static LinkedHashMap<Integer, ClientMseTemplateBean> convetListToMap(
			ArrayList<ClientMseTemplateBean> rootNodeList) {

		LinkedHashMap<Integer, ClientMseTemplateBean> rootList = null;

		try {
			rootList = new LinkedHashMap<Integer, ClientMseTemplateBean>();

			for (ClientMseTemplateBean templateBean : rootNodeList) {
				rootList.put(templateBean.getMental_status_lbl_id(), templateBean);
			}

		} catch (Exception ex) {
			rootList = null;
			logger.info("Problem in Class - PersonalUtility ~~ method- convetListToMap() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return rootList;
	}

	public static String SessionTokenGenerater() {

		String token = null;
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(25);
		try {

			for (int i = 0; i < 25; i++) {
				int index = (int) (AlphaNumericString.length() * Math.random());
				sb.append(AlphaNumericString.charAt(index));
			}

			token = sb.toString();

		} catch (Exception ex) {
			logger.info("Problem in Class - PersonalUtility ~~ method- SessionTokenGenerater() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return token;
	}

}
