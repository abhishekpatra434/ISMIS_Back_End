package com.ISMIS.model;

import java.util.Date;

public class ClientPsychoIdeasBean extends CommonModel {

	private Integer psychoIdeasId;
	private Integer clientId;
	private Integer sc;
	private Integer ia;
	private Integer c_and_u;
	private Integer wrk;
	private Integer doi;
	// private Integer gds;
	private Integer gis;
	private String ideasStatus;
	private Date dateOfEntry;
	private String cohortYear;
	private Integer qtrOfYear;
	private String isDeleted;
	private Boolean isDirty;
	private Integer seqId;

	public Integer getPsychoIdeasId() {
		return psychoIdeasId;
	}

	public void setPsychoIdeasId(Integer psychoIdeasId) {
		this.psychoIdeasId = psychoIdeasId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getSc() {
		return sc;
	}

	public void setSc(Integer sc) {
		this.sc = sc;
	}

	public Integer getIa() {
		return ia;
	}

	public void setIa(Integer ia) {
		this.ia = ia;
	}

	public Integer getC_and_u() {
		return c_and_u;
	}

	public void setC_and_u(Integer c_and_u) {
		this.c_and_u = c_and_u;
	}

	public Integer getWrk() {
		return wrk;
	}

	public void setWrk(Integer wrk) {
		this.wrk = wrk;
	}

	public Integer getDoi() {
		return doi;
	}

	public void setDoi(Integer doi) {
		this.doi = doi;
	}

	/*
	 * public Integer getGds() { return gds; }
	 * 
	 * public void setGds(Integer gds) { this.gds = gds; }
	 */

	public Integer getGis() {
		return gis;
	}

	public void setGis(Integer gis) {
		this.gis = gis;
	}

	public String getIdeasStatus() {
		return ideasStatus;
	}

	public void setIdeasStatus(String ideasStatus) {
		this.ideasStatus = ideasStatus;
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
