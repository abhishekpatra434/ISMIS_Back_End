package com.ISMIS.model;

import java.util.Date;

public class ClientPsychoGafBean extends CommonModel {

	private Integer psychoGafId;
	private Integer clientId;
	private Integer gafScore;
	private String gafScoreStr;

	private Integer gafClass;
	private Date dateOfEntry;
	private String cohortYear;
	private Integer qtrOfYear;
	private String isDeleted;
	private Boolean isDirty;
	private Integer seqId;

	public Integer getPsychoGafId() {
		return psychoGafId;
	}

	public void setPsychoGafId(Integer psychoGafId) {
		this.psychoGafId = psychoGafId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getGafScore() {
		return gafScore;
	}

	public void setGafScore(Integer gafScore) {
		this.gafScore = gafScore;
	}

	public Integer getGafClass() {
		return gafClass;
	}

	public void setGafClass(Integer gafClass) {
		this.gafClass = gafClass;
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

	public String getGafScoreStr() {
		return gafScoreStr;
	}

	public void setGafScoreStr(String gafScoreStr) {
		this.gafScoreStr = gafScoreStr;
	}

	public Integer getSeqId() {
		return seqId;
	}

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

}
