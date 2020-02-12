package com.ISMIS.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ISMIS.dto.PathologicalDto;
import com.ISMIS.dto.PsychometryTestDto;
import com.ISMIS.dto.Response;
import com.ISMIS.model.ClientBMIBean;
import com.ISMIS.model.ClientMedication;
import com.ISMIS.service.ClientHealthService;

@RestController
public class ClientHealth {

	static Logger logger = LogManager.getLogger(ClientHealth.class);

	@Autowired
	private ClientHealthService clientHealthService;

	@RequestMapping(value = "/clientHealth/bmi/getBmiList", method = RequestMethod.GET)
	public Response getBmiList(@RequestParam(name = "clientId") Integer clientId) {
		Response response = clientHealthService.getBmiList(clientId);
		return response;
	}

	@RequestMapping(value = "/clientHealth/bmi/saveUpdateClientBmi", method = RequestMethod.POST)
	public Response saveUpdateClientBmi(@RequestBody ClientBMIBean bmiBean) {
		Response response = clientHealthService.saveUpdateClientBmi(bmiBean);
		return response;
	}

	@RequestMapping(value = "/clientHealth/psychometry/getClientPsychometryTestList", method = RequestMethod.GET)
	public Response getClinetPsychometryTestList(@RequestParam(name = "clientId") Integer clientId) {
		Response response = clientHealthService.getClientPsychometryTestList(clientId);
		return response;
	}

	@RequestMapping(value = "/clientHealth/psychometry/saveUpdateClientPsychometryTest", method = RequestMethod.POST)
	public Response saveUpdateClinetPsychometryTest(@RequestBody PsychometryTestDto psychometryTestDto) {
		Response response = clientHealthService.saveUpdateClientPsychometryTest(psychometryTestDto);
		return response;
	}

	@RequestMapping(value = "/clientHealth/medication/getMedicationList", method = RequestMethod.GET)
	public Response getMedicationList(@RequestParam(name = "clientId") Integer clientId) {
		Response response = clientHealthService.getMedicationList(clientId);
		return response;
	}

	@RequestMapping(value = "/clientHealth/medication/saveUpdateClientMedication", method = RequestMethod.POST)
	public Response saveUpdateClientMedication(@RequestBody ClientMedication clientMedication) {
		Response response = clientHealthService.saveUpdateClientMedication(clientMedication);
		return response;
	}

	@RequestMapping(value = "/clientHealth/medication/getLastDiagnosisAndDoctor", method = RequestMethod.GET)
	public Response getLastDiagnosisAndDoctor(@RequestParam(name = "clientId") Integer clientId) {
		Response response = clientHealthService.getLastDiagnosisAndDoctor(clientId);
		return response;
	}

	@RequestMapping(value = "/clientHealth/pathological/saveUpdatePathologicalTest", method = RequestMethod.POST)
	public Response saveUpdatePathologicalTest(@RequestBody PathologicalDto pathologicalDto) {
		Response response = clientHealthService.saveUpdatePathologicalTest(pathologicalDto);;
		return response;
	}
	
	@RequestMapping(value = "/clientHealth/medication/getPathologicalTestList", method = RequestMethod.GET)
	public Response getPathologicalTestList(@RequestParam(name = "clientId") Integer clientId) {
		Response response = clientHealthService.getPathologicalTestList(clientId);
		return response;
	}
}
