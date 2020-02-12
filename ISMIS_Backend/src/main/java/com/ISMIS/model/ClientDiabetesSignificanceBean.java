package com.ISMIS.model;

import java.util.Date;

public class ClientDiabetesSignificanceBean extends CommonModel {

	private Integer diabetesSignfId;
	private Integer clientId;
	private String diabetesRBS;
	private String diabetesFBS;
	private String diabetesPPBS;
	private Date dateOfEntry;
	private String cohortYear;
	private Integer qtrOfYear;
	private String isDeleted;
	private Boolean isDirty;
	private Integer seqId;

	public Integer getDiabetesSignfId() {
		return diabetesSignfId;
	}

	public void setDiabetesSignfId(Integer diabetesSignfId) {
		this.diabetesSignfId = diabetesSignfId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getDiabetesRBS() {
		return diabetesRBS;
	}

	public void setDiabetesRBS(String diabetesRBS) {
		this.diabetesRBS = diabetesRBS;
	}

	public String getDiabetesFBS() {
		return diabetesFBS;
	}

	public void setDiabetesFBS(String diabetesFBS) {
		this.diabetesFBS = diabetesFBS;
	}

	public String getDiabetesPPBS() {
		return diabetesPPBS;
	}

	public void setDiabetesPPBS(String diabetesPPBS) {
		this.diabetesPPBS = diabetesPPBS;
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
