package com.ISMIS.model;

import java.util.Date;

public class ClientMedicationDetails extends CommonModel  {
	private Integer medicationDetailId;
	private Integer clientId;
	private Integer medicationId;
	private String medicineName;
	private String medicineDoses;
	private Integer medicineType;
	private String medicineTypeStr;
	private String isDeleted;
	private Boolean isDirty;
	public Integer getMedicationDetailId() {
		return medicationDetailId;
	}
	public void setMedicationDetailId(Integer medicationDetailId) {
		this.medicationDetailId = medicationDetailId;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getMedicationId() {
		return medicationId;
	}
	public void setMedicationId(Integer medicationId) {
		this.medicationId = medicationId;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	public String getMedicineDoses() {
		return medicineDoses;
	}
	public void setMedicineDoses(String medicineDoses) {
		this.medicineDoses = medicineDoses;
	}
	public Integer getMedicineType() {
		return medicineType;
	}
	public void setMedicineType(Integer medicineType) {
		this.medicineType = medicineType;
	}
	public String getMedicineTypeStr() {
		return medicineTypeStr;
	}
	public void setMedicineTypeStr(String medicineTypeStr) {
		this.medicineTypeStr = medicineTypeStr;
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
