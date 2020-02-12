package com.ISMIS.model;

import java.util.Date;

public class ClientMasterBean extends CommonModel {

	private Integer clientId;
	private Integer homeId;
	private String clientUid;
	private String clientName;
	private Integer age;
	private Integer sex;
	private String sexStr;
	private Date dateOfEntry;
	private Integer typeOfEntry;
	private String typeOfEntryStr;
	private String cohortYear;
	private String foundInArea;
	private String landmark;
	private String policeStation;
	private String wardNo;
	private Integer referredBy;
	private String referredByStr;
	private String referredDesc;
	private String referredByIfOth;
	private Date dateOfIdentification;
	private String identificationMark;
	private Integer religionId;
	private String religionIfOth;
	private String religionIdStr;
	private String motherTongue;
	private String othLangKnown;
	private Integer educationLevel;
	private String educationLevelStr;
	private String othInformation;
	private Date exitDate;
	private Integer exitStatus;
	private String exitStatusIfOth;
	private String exitStatusStr;
	private Integer durationOfStay;
	private String isDeleted;
	private Boolean isDirty;
	private Integer ageClass;
	private String ageClassStr;
	private String referenceNo;
	private String exitRemarks;
	private String isMse;

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getHomeId() {
		return homeId;
	}

	public void setHomeId(Integer homeId) {
		this.homeId = homeId;
	}

	public String getClientUid() {
		return clientUid;
	}

	public void setClientUid(String clientUid) {
		this.clientUid = clientUid;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

	public Integer getTypeOfEntry() {
		return typeOfEntry;
	}

	public void setTypeOfEntry(Integer typeOfEntry) {
		this.typeOfEntry = typeOfEntry;
	}

	public String getCohortYear() {
		return cohortYear;
	}

	public void setCohortYear(String cohortYear) {
		this.cohortYear = cohortYear;
	}

	public String getFoundInArea() {
		return foundInArea;
	}

	public void setFoundInArea(String foundInArea) {
		this.foundInArea = foundInArea;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getPoliceStation() {
		return policeStation;
	}

	public void setPoliceStation(String policeStation) {
		this.policeStation = policeStation;
	}

	public String getWardNo() {
		return wardNo;
	}

	public void setWardNo(String wardNo) {
		this.wardNo = wardNo;
	}

	public Integer getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(Integer referredBy) {
		this.referredBy = referredBy;
	}

	public String getReferredDesc() {
		return referredDesc;
	}

	public void setReferredDesc(String referredDesc) {
		this.referredDesc = referredDesc;
	}

	public Date getDateOfIdentification() {
		return dateOfIdentification;
	}

	public void setDateOfIdentification(Date dateOfIdentification) {
		this.dateOfIdentification = dateOfIdentification;
	}

	public String getIdentificationMark() {
		return identificationMark;
	}

	public void setIdentificationMark(String identificationMark) {
		this.identificationMark = identificationMark;
	}

	public Integer getReligionId() {
		return religionId;
	}

	public void setReligionId(Integer religionId) {
		this.religionId = religionId;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	public String getOthLangKnown() {
		return othLangKnown;
	}

	public void setOthLangKnown(String othLangKnown) {
		this.othLangKnown = othLangKnown;
	}

	public Integer getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(Integer educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getOthInformation() {
		return othInformation;
	}

	public void setOthInformation(String othInformation) {
		this.othInformation = othInformation;
	}

	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	public Integer getExitStatus() {
		return exitStatus;
	}

	public void setExitStatus(Integer exitStatus) {
		this.exitStatus = exitStatus;
	}

	public Integer getDurationOfStay() {
		return durationOfStay;
	}

	public void setDurationOfStay(Integer durationOfStay) {
		this.durationOfStay = durationOfStay;
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

	public Integer getAgeClass() {
		return ageClass;
	}

	public void setAgeClass(Integer ageClass) {
		this.ageClass = ageClass;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getExitRemarks() {
		return exitRemarks;
	}

	public void setExitRemarks(String exitRemarks) {
		this.exitRemarks = exitRemarks;
	}

	public String getSexStr() {
		return sexStr;
	}

	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}

	public String getTypeOfEntryStr() {
		return typeOfEntryStr;
	}

	public void setTypeOfEntryStr(String typeOfEntryStr) {
		this.typeOfEntryStr = typeOfEntryStr;
	}

	public String getReferredByStr() {
		return referredByStr;
	}

	public void setReferredByStr(String referredByStr) {
		this.referredByStr = referredByStr;
	}

	public String getReligionIdStr() {
		return religionIdStr;
	}

	public void setReligionIdStr(String religionIdStr) {
		this.religionIdStr = religionIdStr;
	}

	public String getEducationLevelStr() {
		return educationLevelStr;
	}

	public void setEducationLevelStr(String educationLevelStr) {
		this.educationLevelStr = educationLevelStr;
	}

	public String getExitStatusStr() {
		return exitStatusStr;
	}

	public void setExitStatusStr(String exitStatusStr) {
		this.exitStatusStr = exitStatusStr;
	}

	public String getIsMse() {
		return isMse;
	}

	public void setIsMse(String isMse) {
		this.isMse = isMse;
	}

	public String getReferredByIfOth() {
		return referredByIfOth;
	}

	public void setReferredByIfOth(String referredByIfOth) {
		this.referredByIfOth = referredByIfOth;
	}

	public String getReligionIfOth() {
		return religionIfOth;
	}

	public void setReligionIfOth(String religionIfOth) {
		this.religionIfOth = religionIfOth;
	}

	public String getExitStatusIfOth() {
		return exitStatusIfOth;
	}

	public void setExitStatusIfOth(String exitStatusIfOth) {
		this.exitStatusIfOth = exitStatusIfOth;
	}

	public String getAgeClassStr() {
		return ageClassStr;
	}

	public void setAgeClassStr(String ageClassStr) {
		this.ageClassStr = ageClassStr;
	}

}
