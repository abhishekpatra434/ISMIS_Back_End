package com.ISMIS.model;

import java.util.Date;

public class ClientBMIBean extends CommonModel {

	private Integer bmiId;
	private Integer clientId;
	private Double weight;
	private String weightUnit;
	private Double height;
	private String heightUnit;
	private Double bmi;
	private Integer bmiClass;
	private String bmiClassStr;
	private String bp;
	private Date dateOfEntry;
	private String cohortYear;
	private Integer qtrOfYear;
	private String isDeleted;
	private Boolean isDirty;

	public Integer getBmiId() {
		return bmiId;
	}

	public void setBmiId(Integer bmiId) {
		this.bmiId = bmiId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public String getHeightUnit() {
		return heightUnit;
	}

	public void setHeightUnit(String heightUnit) {
		this.heightUnit = heightUnit;
	}

	public Double getBmi() {
		return bmi;
	}

	public void setBmi(Double bmi) {
		this.bmi = bmi;
	}

	public Integer getBmiClass() {
		return bmiClass;
	}

	public void setBmiClass(Integer bmiClass) {
		this.bmiClass = bmiClass;
	}

	public String getBp() {
		return bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
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

	public String getBmiClassStr() {
		return bmiClassStr;
	}

	public void setBmiClassStr(String bmiClassStr) {
		this.bmiClassStr = bmiClassStr;
	}

}
