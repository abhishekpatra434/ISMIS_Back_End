package com.ISMIS.serviceImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ISMIS.dao.ClientHealthDao;
import com.ISMIS.dto.PsychometryTestDto;
import com.ISMIS.dto.DiagnosisDto;
import com.ISMIS.dto.PathologicalDto;
import com.ISMIS.dto.Response;
import com.ISMIS.model.ClientBMIBean;
import com.ISMIS.model.ClientMedication;
import com.ISMIS.service.ClientHealthService;

@Service
public class ClientHealthServiceImpl implements ClientHealthService {

	static Logger logger = LogManager.getLogger(ClientHealthServiceImpl.class);

	@Autowired
	private ClientHealthDao clientHealthdao;

	@Override
	public Response getBmiList(Integer clientId) {
		Response response = new Response();
		List<ClientBMIBean> clientBmiList = null;
		try {

			clientBmiList = clientHealthdao.getBmiList(clientId);

			if (clientBmiList != null) {
				response.setKey(0);
				response.setValue(clientBmiList);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - ClientHealthServiceImpl ~~ method- getBmiList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response saveUpdateClientBmi(ClientBMIBean bmiBean) {

		Response response = null;
		String status = null;

		try {

			if (bmiBean.getBmiId() == null) {
				// insert
				status = clientHealthdao.saveClientBmi(bmiBean);
			} else {
				// update
				status = clientHealthdao.updateClientBmi(bmiBean);
			}

			response = new Response();

			if (status != null) {
				response.setKey(2);
				response.setMessage("Some error has taken place.");
			} else {
				response.setKey(0);
				response.setValue(bmiBean);
			}

		} catch (Exception ex) {
			logger.info(
					"Problem in Class - ClientHealthServiceImpl ~~ method- saveUpdateClientBmi() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response getClientPsychometryTestList(Integer clientId) {

		Response response = new Response();
		List<PsychometryTestDto> clientBmiList = null;

		try {

			clientBmiList = clientHealthdao.getClientPsychometryTestList(clientId);

			if (clientBmiList != null) {
				response.setKey(0);
				response.setValue(clientBmiList);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - ClientHealthServiceImpl ~~ method- getPsychometryTestList() - "
					+ ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response saveUpdateClientPsychometryTest(PsychometryTestDto psychometryTestDto) {
		Response response = null;
		String status = null;

		try {
			response = new Response();
			status = clientHealthdao.saveUpdateClientPsychometryTest(psychometryTestDto);

			if (status != null) {
				response.setKey(2);
				response.setMessage("Some error has taken place.");
			} else {
				response.setKey(0);
				response.setValue(psychometryTestDto);
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - ClientHealthServiceImpl ~~ method- saveUpdateClinetPsychometryTest() - "
					+ ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response getMedicationList(Integer clientId) {
		Response response = new Response();
		List<ClientMedication> clientMedicationList = null;
		try {

			clientMedicationList = clientHealthdao.getMedicationList(clientId);

			if (clientMedicationList != null) {
				response.setKey(0);
				response.setValue(clientMedicationList);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info(
					"Problem in Class - ClientHealthServiceImpl ~~ method- getMedicationList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response saveUpdateClientMedication(ClientMedication clientMedication) {
		Response response = null;
		String status = null;

		try {

			if (clientMedication.getMedicationId() == null) {
				// insert
				status = clientHealthdao.saveClientMedication(clientMedication);
			} else {
				// update
				status = clientHealthdao.updateClientMedication(clientMedication);
			}

			response = new Response();

			if (status != null) {
				response.setKey(2);
				response.setMessage("Some error has taken place.");
			} else {
				response.setKey(0);
				response.setValue(clientMedication);
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - saveUpdateClientBmi ~~ method- saveUpdateClientMedication() - "
					+ ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response getLastDiagnosisAndDoctor(Integer clientId) {
		Response response = new Response();
		DiagnosisDto diagnosisDto = null;
		try {

			diagnosisDto = clientHealthdao.getLastDiagnosisAndDoctor(clientId);

			if (diagnosisDto != null) {
				response.setKey(0);
				response.setValue(diagnosisDto);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - ClientHealthServiceImpl ~~ method- getLastDiagnosisAndDoctor() - "
					+ ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response saveUpdatePathologicalTest(PathologicalDto pathologicalDto) {
		Response response = null;
		String status = null;

		try {
			response = new Response();
			status = clientHealthdao.saveUpdatePathologicalTest(pathologicalDto);

			if (status != null) {
				response.setKey(2);
				response.setMessage("Some error has taken place.");
			} else {
				response.setKey(0);
				response.setValue(pathologicalDto);
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - ClientHealthServiceImpl ~~ method- saveUpdatePathologicalTest() - "
					+ ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response getPathologicalTestList(Integer clientId) {

		Response response = new Response();
		List<PathologicalDto> getPathologicalTestList = null;

		try {

			getPathologicalTestList = clientHealthdao.getPathologicalTestList(clientId);

			if (getPathologicalTestList != null) {
				response.setKey(0);
				response.setValue(getPathologicalTestList);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - ClientHealthServiceImpl ~~ method- getPathologicalTestList() - "
					+ ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

}
