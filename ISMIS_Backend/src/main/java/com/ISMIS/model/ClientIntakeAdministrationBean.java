package com.ISMIS.model;

import java.util.Date;

public class ClientIntakeAdministrationBean extends CommonModel {

	private Integer intakeAdminId;
	private Integer clientId;
	private String caregiverName;
	private String doctorSignature;
	private Date doctorSignatureDate;
	private String counsellorSwName;
	private Date counsellorSignDate;
	private String coordinatorName;
	private Date coordinatorSignDate;
	private String remarks;
	private String isDeleted;
	private Boolean isDirty;

	public Integer getIntakeAdminId() {
		return intakeAdminId;
	}

	public void setIntakeAdminId(Integer intakeAdminId) {
		this.intakeAdminId = intakeAdminId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getCaregiverName() {
		return caregiverName;
	}

	public void setCaregiverName(String caregiverName) {
		this.caregiverName = caregiverName;
	}

	public String getDoctorSignature() {
		return doctorSignature;
	}

	public void setDoctorSignature(String doctorSignature) {
		this.doctorSignature = doctorSignature;
	}

	public Date getDoctorSignatureDate() {
		return doctorSignatureDate;
	}

	public void setDoctorSignatureDate(Date doctorSignatureDate) {
		this.doctorSignatureDate = doctorSignatureDate;
	}

	public String getCounsellorSwName() {
		return counsellorSwName;
	}

	public void setCounsellorSwName(String counsellorSwName) {
		this.counsellorSwName = counsellorSwName;
	}

	public Date getCounsellorSignDate() {
		return counsellorSignDate;
	}

	public void setCounsellorSignDate(Date counsellorSignDate) {
		this.counsellorSignDate = counsellorSignDate;
	}

	public String getCoordinatorName() {
		return coordinatorName;
	}

	public void setCoordinatorName(String coordinatorName) {
		this.coordinatorName = coordinatorName;
	}

	public Date getCoordinatorSignDate() {
		return coordinatorSignDate;
	}

	public void setCoordinatorSignDate(Date coordinatorSignDate) {
		this.coordinatorSignDate = coordinatorSignDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
