package com.ISMIS.dto;

import java.util.Date;

public class CommonDto {

	private String homeId;
	private String clientId;
	private String exportFor;
	private String cohortYr;
	private Date fromDate;
	private Date toDate;

	public String getHomeId() {
		return homeId;
	}

	public void setHomeId(String homeId) {
		this.homeId = homeId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getExportFor() {
		return exportFor;
	}

	public void setExportFor(String exportFor) {
		this.exportFor = exportFor;
	}

	public String getCohortYr() {
		return cohortYr;
	}

	public void setCohortYr(String cohortYr) {
		this.cohortYr = cohortYr;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
