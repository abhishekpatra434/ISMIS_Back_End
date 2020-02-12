package com.ISMIS.serviceImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ISMIS.dao.ClientDao;
import com.ISMIS.dto.ClientDto;
import com.ISMIS.dto.Response;
import com.ISMIS.model.ClientMasterBean;
import com.ISMIS.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	static Logger logger = LogManager.getLogger(ClientServiceImpl.class);

	@Autowired
	private ClientDao clientdao;

	@Override
	public Response saveClientIntakeDetails(ClientDto clientDto, MultipartFile file) {

		Response response = null;
		String status = null;

		try {
			response = new Response();

			if (clientDto.getClientMasterBean().getClientId() == null) {
				// Insert Block
				status = clientdao.saveClientIntakeDetails(clientDto, file);

				if (status != null) {
					response.setKey(2);
					response.setMessage(status);
				} else {
					if (clientDto.getClientMasterBean().getClientId() == null) {
						response.setKey(1);
						response.setMessage("Some error has taken place.");
					} else {
						response.setKey(0);
						response.setValue(clientDto);
					}
				}
			} else {
				// update block
				status = clientdao.updateClientIntakeDetails(clientDto, file);

				if (status != null) {
					response.setKey(2);
					response.setMessage(status);
				} else {
					if (clientDto.getClientMasterBean().getClientId() == null) {
						response.setKey(1);
						response.setMessage("Some error has taken place.");
					} else {
						response.setKey(0);
						response.setValue(clientDto);
					}
				}
			}

		} catch (Exception ex) {
			logger.info(
					"Problem in Class - ClientServiceImpl ~~ method- saveClientIntakeDetails() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}

	@Override
	public Response getClientUidList(String clientUid, String homeId) {
		Response response = new Response();
		List<ClientMasterBean> uidList = null;
		try {

			uidList = clientdao.getClientUidList(clientUid, homeId);

			if (uidList != null) {
				response.setKey(0);
				response.setValue(uidList);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - ClientServiceImpl ~~ method- getClientUidList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response getClientBasicInfoList(String clientId, String cohortYr, String homeId) {
		Response response = new Response();
		List<ClientMasterBean> clientList = null;
		try {

			clientList = clientdao.getClientBasicInfoList(clientId, cohortYr, homeId);

			if (clientList != null) {
				response.setKey(0);
				response.setValue(clientList);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info(
					"Problem in Class - ClientServiceImpl ~~ method- getClientBasicInfoList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}

	@Override
	public Response getCohortYrList(String homeId) {
		Response response = new Response();
		List<String> cohortList = null;
		try {

			cohortList = clientdao.getCohortYrList(homeId);

			if (cohortList != null) {
				response.setKey(0);
				response.setValue(cohortList);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - ClientServiceImpl ~~ method- getCohortYrList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response getClientIntakeDetails(String clientId) {

		Response response = new Response();
		ClientDto clientDto = null;
		try {

			clientDto = clientdao.getClientIntakeDetails(clientId);

			if (clientDto != null) {
				response.setKey(0);
				response.setValue(clientDto);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info(
					"Problem in Class - ClientServiceImpl ~~ method- getClientIntakeDetails() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response getClientNameList(String clientName, String homeId) {

		Response response = new Response();
		List<ClientMasterBean> clientList = null;
		try {

			clientList = clientdao.getClientNameList(clientName, homeId);

			if (clientList != null) {
				response.setKey(0);
				response.setValue(clientList);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - ClientServiceImpl ~~ method- getClientNameList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public String getUploadedFileInfo(Integer clientId, String documetType) {
		return clientdao.getUploadedFileInfo(clientId, documetType);
	}

}
