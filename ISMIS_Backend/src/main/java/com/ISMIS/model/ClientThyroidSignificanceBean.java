package com.ISMIS.model;

import java.util.Date;

public class ClientThyroidSignificanceBean extends CommonModel {

	private Integer thyroidSignfId;
	private Integer clientId;
	private String thyroTsh;
	private String thyroT3;
	private String thyroT4;
	private Date dateOfEntry;
	private String cohortYear;
	private Integer qtrOfYear;
	private String isDeleted;
	private Boolean isDirty;
	private Integer seqId;

	public Integer getThyroidSignfId() {
		return thyroidSignfId;
	}

	public void setThyroidSignfId(Integer thyroidSignfId) {
		this.thyroidSignfId = thyroidSignfId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getThyroTsh() {
		return thyroTsh;
	}

	public void setThyroTsh(String thyroTsh) {
		this.thyroTsh = thyroTsh;
	}

	public String getThyroT3() {
		return thyroT3;
	}

	public void setThyroT3(String thyroT3) {
		this.thyroT3 = thyroT3;
	}

	public String getThyroT4() {
		return thyroT4;
	}

	public void setThyroT4(String thyroT4) {
		this.thyroT4 = thyroT4;
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
