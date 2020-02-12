package com.ISMIS.dto;

import java.util.List;

import com.ISMIS.model.ClientMseBean;
import com.ISMIS.model.ClientMseTemplateBean;

public class ClientMseDto {

	private List<ClientMseBean> clientMseBean;
	private String summeryTxt;
	private Integer clientId;
	private ClientMseTemplateBean clientMseResultTempBeans;
	private Boolean newEntry;

	public List<ClientMseBean> getClientMseBean() {
		return clientMseBean;
	}

	public void setClientMseBean(List<ClientMseBean> clientMseBean) {
		this.clientMseBean = clientMseBean;
	}

	public String getSummeryTxt() {
		return summeryTxt;
	}

	public void setSummeryTxt(String summeryTxt) {
		this.summeryTxt = summeryTxt;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public ClientMseTemplateBean getClientMseResultTempBeans() {
		return clientMseResultTempBeans;
	}

	public void setClientMseResultTempBeans(ClientMseTemplateBean clientMseResultTempBeans) {
		this.clientMseResultTempBeans = clientMseResultTempBeans;
	}

	public Boolean getNewEntry() {
		return newEntry;
	}

	public void setNewEntry(Boolean newEntry) {
		this.newEntry = newEntry;
	}

}
