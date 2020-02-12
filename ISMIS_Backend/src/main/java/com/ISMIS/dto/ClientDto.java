package com.ISMIS.dto;

import com.ISMIS.model.ClientAddressBean;
import com.ISMIS.model.ClientBMIBean;
import com.ISMIS.model.ClientDiabetesSignificanceBean;
import com.ISMIS.model.ClientDocuments;
import com.ISMIS.model.ClientFamilyBean;
import com.ISMIS.model.ClientHemoglobinSignificanceBean;
import com.ISMIS.model.ClientInitMedicalDetBean;
import com.ISMIS.model.ClientIntakeAdministrationBean;
import com.ISMIS.model.ClientMasterBean;
import com.ISMIS.model.ClientPsychoGafBean;
import com.ISMIS.model.ClientPsychoIdeasBean;
import com.ISMIS.model.ClientPsychoLspBean;
import com.ISMIS.model.ClientPsychoPanssBean;
import com.ISMIS.model.ClientThyroidSignificanceBean;

public class ClientDto {

	private ClientMasterBean clientMasterBean;
	private ClientAddressBean clientAddressBean;
	private ClientFamilyBean clientFamilyBean;

	private ClientInitMedicalDetBean clientInitMedicalDetBean;
	private ClientBMIBean clientBMIBean;

	private ClientPsychoGafBean clientPsychoGafBean;
	private ClientPsychoIdeasBean clientPsychoIdeasBean;
	private ClientPsychoLspBean clientPsychoLspBean;
	private ClientPsychoPanssBean clientPsychoPanss;

	private ClientThyroidSignificanceBean clientThyroidSignificanceBean;
	private ClientDiabetesSignificanceBean clientDiabetesSignificanceBean;
	private ClientHemoglobinSignificanceBean clientHemoglobinSignificanceBean;
	private ClientIntakeAdministrationBean clientIntakeAdministrationBean;

	private ClientDocuments clientDocuments;

	public ClientMasterBean getClientMasterBean() {
		return clientMasterBean;
	}

	public void setClientMasterBean(ClientMasterBean clientMasterBean) {
		this.clientMasterBean = clientMasterBean;
	}

	public ClientAddressBean getClientAddressBean() {
		return clientAddressBean;
	}

	public void setClientAddressBean(ClientAddressBean clientAddressBean) {
		this.clientAddressBean = clientAddressBean;
	}

	public ClientFamilyBean getClientFamilyBean() {
		return clientFamilyBean;
	}

	public void setClientFamilyBean(ClientFamilyBean clientFamilyBean) {
		this.clientFamilyBean = clientFamilyBean;
	}

	public ClientInitMedicalDetBean getClientInitMedicalDetBean() {
		return clientInitMedicalDetBean;
	}

	public void setClientInitMedicalDetBean(ClientInitMedicalDetBean clientInitMedicalDetBean) {
		this.clientInitMedicalDetBean = clientInitMedicalDetBean;
	}

	public ClientBMIBean getClientBMIBean() {
		return clientBMIBean;
	}

	public void setClientBMIBean(ClientBMIBean clientBMIBean) {
		this.clientBMIBean = clientBMIBean;
	}

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

	public ClientIntakeAdministrationBean getClientIntakeAdministrationBean() {
		return clientIntakeAdministrationBean;
	}

	public void setClientIntakeAdministrationBean(ClientIntakeAdministrationBean clientIntakeAdministrationBean) {
		this.clientIntakeAdministrationBean = clientIntakeAdministrationBean;
	}

	public ClientDocuments getClientDocuments() {
		return clientDocuments;
	}

	public void setClientDocuments(ClientDocuments clientDocuments) {
		this.clientDocuments = clientDocuments;
	}

}
