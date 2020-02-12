package com.ISMIS.model;

public class ClientFamilyBean extends CommonModel {

	private Integer familyId;
	private Integer clientId;
	private String guardianName;
	private String contactAddress;
	private String contact1No;
	private String contact2No;
	private Integer relationWithGuardian;
	private String relationIfOth;
	private String relationWithGuardianStr;
	private Integer familySize;
	private String occupationOfFamily;
	private Double monthlyIncomeOfFamily;
	private String isDeleted;
	private Boolean isDirty;

	public Integer getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Integer familyId) {
		this.familyId = familyId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContact1No() {
		return contact1No;
	}

	public void setContact1No(String contact1No) {
		this.contact1No = contact1No;
	}

	public String getContact2No() {
		return contact2No;
	}

	public void setContact2No(String contact2No) {
		this.contact2No = contact2No;
	}

	public Integer getRelationWithGuardian() {
		return relationWithGuardian;
	}

	public void setRelationWithGuardian(Integer relationWithGuardian) {
		this.relationWithGuardian = relationWithGuardian;
	}

	public Integer getFamilySize() {
		return familySize;
	}

	public void setFamilySize(Integer familySize) {
		this.familySize = familySize;
	}

	public String getOccupationOfFamily() {
		return occupationOfFamily;
	}

	public void setOccupationOfFamily(String occupationOfFamily) {
		this.occupationOfFamily = occupationOfFamily;
	}

	public Double getMonthlyIncomeOfFamily() {
		return monthlyIncomeOfFamily;
	}

	public void setMonthlyIncomeOfFamily(Double monthlyIncomeOfFamily) {
		this.monthlyIncomeOfFamily = monthlyIncomeOfFamily;
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

	public String getRelationWithGuardianStr() {
		return relationWithGuardianStr;
	}

	public void setRelationWithGuardianStr(String relationWithGuardianStr) {
		this.relationWithGuardianStr = relationWithGuardianStr;
	}

	public String getRelationIfOth() {
		return relationIfOth;
	}

	public void setRelationIfOth(String relationIfOth) {
		this.relationIfOth = relationIfOth;
	}

}
