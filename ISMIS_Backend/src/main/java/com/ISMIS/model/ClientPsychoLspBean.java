package com.ISMIS.model;

import java.util.Date;

public class ClientPsychoLspBean extends CommonModel {

	private Integer psychoLspId;
	private Integer clientId;
	private Integer lspScore;
	private Integer lspClass;
	private Date dateOfEntry;
	private String cohortYear;
	private Integer qtrOfYear;
	private String isDeleted;
	private Boolean isDirty;
	private Integer seqId;

	public Integer getPsychoLspId() {
		return psychoLspId;
	}

	public void setPsychoLspId(Integer psychoLspId) {
		this.psychoLspId = psychoLspId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getLspScore() {
		return lspScore;
	}

	public void setLspScore(Integer lspScore) {
		this.lspScore = lspScore;
	}

	public Integer getLspClass() {
		return lspClass;
	}

	public void setLspClass(Integer lspClass) {
		this.lspClass = lspClass;
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
