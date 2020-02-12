package com.ISMIS.model;

public class ClientBmiClassBean extends CommonModel {

	private Integer bmiClassId;
	private Integer lowerBmi;
	private Integer upperBmi;
	private String bmiClass;

	public Integer getBmiClassId() {
		return bmiClassId;
	}

	public void setBmiClassId(Integer bmiClassId) {
		this.bmiClassId = bmiClassId;
	}

	public Integer getLowerBmi() {
		return lowerBmi;
	}

	public void setLowerBmi(Integer lowerBmi) {
		this.lowerBmi = lowerBmi;
	}

	public Integer getUpperBmi() {
		return upperBmi;
	}

	public void setUpperBmi(Integer upperBmi) {
		this.upperBmi = upperBmi;
	}

	public String getBmiClass() {
		return bmiClass;
	}

	public void setBmiClass(String bmiClass) {
		this.bmiClass = bmiClass;
	}

}
