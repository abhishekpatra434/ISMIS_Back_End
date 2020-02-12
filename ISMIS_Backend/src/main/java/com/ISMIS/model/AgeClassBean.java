package com.ISMIS.model;

public class AgeClassBean extends CommonModel {

	private Integer ageClassId;
	private Integer lowerAge;
	private Integer upperAge;
	private String ageClass;

	public Integer getAgeClassId() {
		return ageClassId;
	}

	public void setAgeClassId(Integer ageClassId) {
		this.ageClassId = ageClassId;
	}

	public Integer getLowerAge() {
		return lowerAge;
	}

	public void setLowerAge(Integer lowerAge) {
		this.lowerAge = lowerAge;
	}

	public Integer getUpperAge() {
		return upperAge;
	}

	public void setUpperAge(Integer upperAge) {
		this.upperAge = upperAge;
	}

	public String getAgeClass() {
		return ageClass;
	}

	public void setAgeClass(String ageClass) {
		this.ageClass = ageClass;
	}

}
