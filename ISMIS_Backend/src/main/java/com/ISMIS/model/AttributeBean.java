package com.ISMIS.model;

public class AttributeBean {

	private Integer categoryAttributeId;
	private String attrDesc;
	private String isDeleted;

	public Integer getCategoryAttributeId() {
		return categoryAttributeId;
	}

	public void setCategoryAttributeId(Integer categoryAttributeId) {
		this.categoryAttributeId = categoryAttributeId;
	}

	public String getAttrDesc() {
		return attrDesc;
	}

	public void setAttrDesc(String attrDesc) {
		this.attrDesc = attrDesc;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

}
