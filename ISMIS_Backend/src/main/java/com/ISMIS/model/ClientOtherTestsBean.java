package com.ISMIS.model;

import java.util.Date;

public class ClientOtherTestsBean extends CommonModel {

	private Integer otherTestId;
	private Integer clientId;
	private String othTestName;
	private String othTestData;
	private Date dateOfAdmin;
	private Integer seqId;
	private String isDeleted;
	private Boolean isDirty;

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

	public Integer getOtherTestId() {
		return otherTestId;
	}

	public void setOtherTestId(Integer otherTestId) {
		this.otherTestId = otherTestId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getOthTestName() {
		return othTestName;
	}

	public void setOthTestName(String othTestName) {
		this.othTestName = othTestName;
	}

	public String getOthTestData() {
		return othTestData;
	}

	public void setOthTestData(String othTestData) {
		this.othTestData = othTestData;
	}

	public Date getDateOfAdmin() {
		return dateOfAdmin;
	}

	public void setDateOfAdmin(Date dateOfAdmin) {
		this.dateOfAdmin = dateOfAdmin;
	}

	public Integer getSeqId() {
		return seqId;
	}

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

}
