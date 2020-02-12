package com.ISMIS.commonUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static java.sql.Date StringToDate(String strDate) throws Exception {
		java.sql.Date dtReturn = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			dtReturn = new java.sql.Date(simpleDateFormat.parse(strDate).getTime());
		} catch (ParseException e) {
			// e.printStackTrace();
			throw e;
		}
		return dtReturn;
	}

	public static String dateToString(Date dt) {
		String stringDate = "";
		SimpleDateFormat simpleDateFormat = null;
		try {
			simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			if (dt != null && !dt.equals("")) {
				stringDate = simpleDateFormat.format(dt);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return stringDate;
	}

	public static String SqlDateToString(Date date) {
		String dateStr = "";
		try {
			if (date != null && !date.toString().isEmpty()) {
				dateStr = new SimpleDateFormat("dd/MM/yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(date.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	public static Date SqlDateToUtilDate(Date date) {
		Date dateStr = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String stringDate = null;
		try {
			if (date != null && !date.toString().isEmpty()) {
				stringDate = simpleDateFormat.format(date);
				dateStr = new SimpleDateFormat("dd-MM-yyyy").parse(stringDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}

}
