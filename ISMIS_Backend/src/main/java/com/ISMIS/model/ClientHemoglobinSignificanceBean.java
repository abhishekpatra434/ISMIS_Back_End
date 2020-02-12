package com.ISMIS.model;

import java.util.Date;

public class ClientHemoglobinSignificanceBean extends CommonModel {

	private Integer hemoglobinSignfId;
	private Integer clientId;
	private String hbPercent;
	private String esr;
	private String hemoglobinLevel;
	private String othLabTest;
	private Date othDateOfEntry;
	private Date dateOfEntry;
	private String cohortYear;
	private Integer qtrOfYear;
	private String isDeleted;
	private Boolean isDirty;
	private Integer seqId;

	public Integer getHemoglobinSignfId() {
		return hemoglobinSignfId;
	}

	public void setHemoglobinSignfId(Integer hemoglobinSignfId) {
		this.hemoglobinSignfId = hemoglobinSignfId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getHbPercent() {
		return hbPercent;
	}

	public void setHbPercent(String hbPercent) {
		this.hbPercent = hbPercent;
	}

	public String getEsr() {
		return esr;
	}

	public void setEsr(String esr) {
		this.esr = esr;
	}

	public String getHemoglobinLevel() {
		return hemoglobinLevel;
	}

	public void setHemoglobinLevel(String hemoglobinLevel) {
		this.hemoglobinLevel = hemoglobinLevel;
	}

	public String getOthLabTest() {
		return othLabTest;
	}

	public void setOthLabTest(String othLabTest) {
		this.othLabTest = othLabTest;
	}

	public Date getOthDateOfEntry() {
		return othDateOfEntry;
	}

	public void setOthDateOfEntry(Date othDateOfEntry) {
		this.othDateOfEntry = othDateOfEntry;
	}

	public Date getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

	public String getCohortYear() {
		return cohortYear;
	}

	public void setCohortYear(String cohortYear) {
		this.cohortYear = cohortYear;
	}

	public Integer getQtrOfYear() {
		return qtrOfYear;
	}

	public void setQtrOfYear(Integer qtrOfYear) {
		this.qtrOfYear = qtrOfYear;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsDirty() {
		return isDirty;
	}

	public void setIsDirty(Boolean isDirty) {
		this.isDirty = isDirty;
	}

	public Integer getSeqId() {
		return seqId;
	}

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

}
