package com.ISMIS.model;

import java.util.Date;

public class ClientHivTestBean extends CommonModel {

	private Integer hivId;
	private Integer clientId;
	private String hivData;
	private Date dateOfAdmin;
	private Integer seqId;
	private String isDeleted;
	private Boolean isDirty;

	public Integer getHivId() {
		return hivId;
	}

	public void setHivId(Integer hivId) {
		this.hivId = hivId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getHivData() {
		return hivData;
	}

	public void setHivData(String hivData) {
		this.hivData = hivData;
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

}
