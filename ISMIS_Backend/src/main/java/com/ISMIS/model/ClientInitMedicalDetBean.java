package com.ISMIS.model;

public class ClientInitMedicalDetBean extends CommonModel {

	private Integer initMedicalDetId;
	private Integer clientId;
	private String provisionalDiagnosis;
	private Integer diagnosisGroup;
	private String diagnosisGroupIfOth;
	private String diagnosisGroupStr;
	private String comorbidity;
	private String othMedicalConditionFound;
	private String isDeleted;
	private Boolean isDirty;

	public Integer getInitMedicalDetId() {
		return initMedicalDetId;
	}

	public void setInitMedicalDetId(Integer initMedicalDetId) {
		this.initMedicalDetId = initMedicalDetId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getProvisionalDiagnosis() {
		return provisionalDiagnosis;
	}

	public void setProvisionalDiagnosis(String provisionalDiagnosis) {
		this.provisionalDiagnosis = provisionalDiagnosis;
	}

	public Integer getDiagnosisGroup() {
		return diagnosisGroup;
	}

	public void setDiagnosisGroup(Integer diagnosisGroup) {
		this.diagnosisGroup = diagnosisGroup;
	}

	public String getComorbidity() {
		return comorbidity;
	}

	public void setComorbidity(String comorbidity) {
		this.comorbidity = comorbidity;
	}

	public String getOthMedicalConditionFound() {
		return othMedicalConditionFound;
	}

	public void setOthMedicalConditionFound(String othMedicalConditionFound) {
		this.othMedicalConditionFound = othMedicalConditionFound;
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

	public String getDiagnosisGroupStr() {
		return diagnosisGroupStr;
	}

	public void setDiagnosisGroupStr(String diagnosisGroupStr) {
		this.diagnosisGroupStr = diagnosisGroupStr;
	}

	public String getDiagnosisGroupIfOth() {
		return diagnosisGroupIfOth;
	}

	public void setDiagnosisGroupIfOth(String diagnosisGroupIfOth) {
		this.diagnosisGroupIfOth = diagnosisGroupIfOth;
	}

}
