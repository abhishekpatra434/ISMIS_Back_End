package com.ISMIS.dto;

import java.util.List;

import com.ISMIS.model.ClientDiabetesSignificanceBean;
import com.ISMIS.model.ClientHemoglobinSignificanceBean;
import com.ISMIS.model.ClientHivTestBean;
import com.ISMIS.model.ClientMasterBean;
import com.ISMIS.model.ClientOtherTestsBean;
import com.ISMIS.model.ClientThyroidSignificanceBean;

public class PathologicalDto {

	private ClientThyroidSignificanceBean clientThyroidSignificanceBean;
	private ClientDiabetesSignificanceBean clientDiabetesSignificanceBean;
	private ClientHemoglobinSignificanceBean clientHemoglobinSignificanceBean;

	private ClientHivTestBean clientHivTestBean;
	private List<ClientOtherTestsBean> listClientOthTest;

	private Integer sequenceNo;
	private Integer userId;
	private Integer clientId;

	private ClientMasterBean clientMasterBean;

	public ClientThyroidSignificanceBean getClientThyroidSignificanceBean() {
		return clientThyroidSignificanceBean;
	}

	public void setClientThyroidSignificanceBean(ClientThyroidSignificanceBean clientThyroidSignificanceBean) {
		this.clientThyroidSignificanceBean = clientThyroidSignificanceBean;
	}

	public ClientDiabetesSignificanceBean getClientDiabetesSignificanceBean() {
		return clientDiabetesSignificanceBean;
	}

	public void setClientDiabetesSignificanceBean(ClientDiabetesSignificanceBean clientDiabetesSignificanceBean) {
		this.clientDiabetesSignificanceBean = clientDiabetesSignificanceBean;
	}

	public ClientHemoglobinSignificanceBean getClientHemoglobinSignificanceBean() {
		return clientHemoglobinSignificanceBean;
	}

	public void setClientHemoglobinSignificanceBean(ClientHemoglobinSignificanceBean clientHemoglobinSignificanceBean) {
		this.clientHemoglobinSignificanceBean = clientHemoglobinSignificanceBean;
	}

	public ClientHivTestBean getClientHivTestBean() {
		return clientHivTestBean;
	}

	public void setClientHivTestBean(ClientHivTestBean clientHivTestBean) {
		this.clientHivTestBean = clientHivTestBean;
	}

	public List<ClientOtherTestsBean> getListClientOthTest() {
		return listClientOthTest;
	}

	public void setListClientOthTest(List<ClientOtherTestsBean> listClientOthTest) {
		this.listClientOthTest = listClientOthTest;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public ClientMasterBean getClientMasterBean() {
		return clientMasterBean;
	}

	public void setClientMasterBean(ClientMasterBean clientMasterBean) {
		this.clientMasterBean = clientMasterBean;
	}

}
