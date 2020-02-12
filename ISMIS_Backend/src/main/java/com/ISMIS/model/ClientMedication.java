package com.ISMIS.model;

import java.util.Date;
import java.util.List;

public class ClientMedication extends CommonModel  {
	private Integer medicationId;
	private Integer clientId;
	private String diagnosis;
	private String doctorName;
	private Date dateOfPrescription;
	private String cohortYear;
	private Integer qtrOfYear;
	private String isDeleted;
	private Boolean isDirty;
	private List<ClientMedicationDetails> clientMedicationDetailsList;
	
	public Integer getMedicationId() {
		return medicationId;
	}
	public void setMedicationId(Integer medicationId) {
		this.medicationId = medicationId;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public Date getDateOfPrescription() {
		return dateOfPrescription;
	}
	public void setDateOfPrescription(Date dateOfPrescription) {
		this.dateOfPrescription = dateOfPrescription;
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
	public List<ClientMedicationDetails> getClientMedicationDetailsList() {
		return clientMedicationDetailsList;
	}
	public void setClientMedicationDetailsList(List<ClientMedicationDetails> clientMedicationDetailsList) {
		this.clientMedicationDetailsList = clientMedicationDetailsList;
	}
		
}
