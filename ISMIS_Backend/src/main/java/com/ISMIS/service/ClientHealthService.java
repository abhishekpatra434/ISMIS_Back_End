package com.ISMIS.service;

import com.ISMIS.dto.PathologicalDto;
import com.ISMIS.dto.PsychometryTestDto;
import com.ISMIS.dto.Response;
import com.ISMIS.model.ClientBMIBean;
import com.ISMIS.model.ClientMedication;

public interface ClientHealthService {

	Response getBmiList(Integer clientId);

	Response saveUpdateClientBmi(ClientBMIBean bmiBean);

	Response getClientPsychometryTestList(Integer clientId);

	Response saveUpdateClientPsychometryTest(PsychometryTestDto psychometryTestDto);

	Response getMedicationList(Integer clientId);

	Response saveUpdateClientMedication(ClientMedication clientMedication);

	Response getLastDiagnosisAndDoctor(Integer clientId);
	
	Response saveUpdatePathologicalTest(PathologicalDto pathologicalDto);
	
	Response getPathologicalTestList(Integer clientId);

}
