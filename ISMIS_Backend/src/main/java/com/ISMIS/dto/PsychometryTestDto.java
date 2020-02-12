package com.ISMIS.dto;

import com.ISMIS.model.ClientMasterBean;
import com.ISMIS.model.ClientPsychoGafBean;
import com.ISMIS.model.ClientPsychoIdeasBean;
import com.ISMIS.model.ClientPsychoLspBean;
import com.ISMIS.model.ClientPsychoPanssBean;

public class PsychometryTestDto {

	private ClientPsychoGafBean clientPsychoGafBean;
	private ClientPsychoIdeasBean clientPsychoIdeasBean;
	private ClientPsychoLspBean clientPsychoLspBean;
	private ClientPsychoPanssBean clientPsychoPanss;
	private Integer sequenceNo;
	private Integer userId;
	private Integer clientId;
	private ClientMasterBean clientMasterBean;

	public ClientPsychoGafBean getClientPsychoGafBean() {
		return clientPsychoGafBean;
	}

	public void setClientPsychoGafBean(ClientPsychoGafBean clientPsychoGafBean) {
		this.clientPsychoGafBean = clientPsychoGafBean;
	}

	public ClientPsychoIdeasBean getClientPsychoIdeasBean() {
		return clientPsychoIdeasBean;
	}

	public void setClientPsychoIdeasBean(ClientPsychoIdeasBean clientPsychoIdeasBean) {
		this.clientPsychoIdeasBean = clientPsychoIdeasBean;
	}

	public ClientPsychoLspBean getClientPsychoLspBean() {
		return clientPsychoLspBean;
	}

	public void setClientPsychoLspBean(ClientPsychoLspBean clientPsychoLspBean) {
		this.clientPsychoLspBean = clientPsychoLspBean;
	}

	public ClientPsychoPanssBean getClientPsychoPanss() {
		return clientPsychoPanss;
	}

	public void setClientPsychoPanss(ClientPsychoPanssBean clientPsychoPanss) {
		this.clientPsychoPanss = clientPsychoPanss;
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
