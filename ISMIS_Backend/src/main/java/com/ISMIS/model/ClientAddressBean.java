package com.ISMIS.model;

public class ClientAddressBean extends CommonModel {

	private Integer addressId;
	private Integer clientId;
	private String gramPanchayatMouza;
	private String paraVillage;
	private String policeStation;
	private String postOffice;
	private String pinNo;
	private String district;
	private String state;
	private String country;
	private Boolean isDirty;

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getGramPanchayatMouza() {
		return gramPanchayatMouza;
	}

	public void setGramPanchayatMouza(String gramPanchayatMouza) {
		this.gramPanchayatMouza = gramPanchayatMouza;
	}

	public String getParaVillage() {
		return paraVillage;
	}

	public void setParaVillage(String paraVillage) {
		this.paraVillage = paraVillage;
	}

	public String getPoliceStation() {
		return policeStation;
	}

	public void setPoliceStation(String policeStation) {
		this.policeStation = policeStation;
	}

	public String getPostOffice() {
		return postOffice;
	}

	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	public String getPinNo() {
		return pinNo;
	}

	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getIsDirty() {
		return isDirty;
	}

	public void setIsDirty(Boolean isDirty) {
		this.isDirty = isDirty;
	}

}
