package com.ISMIS.model;

public class ClientMseBean {

	private Integer clientId;
	private Integer parentId;
	private Integer current_lbl_Id;
	private String status;
	private String txtValue;

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getCurrent_lbl_Id() {
		return current_lbl_Id;
	}

	public void setCurrent_lbl_Id(Integer current_lbl_Id) {
		this.current_lbl_Id = current_lbl_Id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTxtValue() {
		return txtValue;
	}

	public void setTxtValue(String txtValue) {
		this.txtValue = txtValue;
	}

}
