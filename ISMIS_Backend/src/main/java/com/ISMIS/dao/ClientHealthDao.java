package com.ISMIS.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import com.ISMIS.dto.PsychometryTestDto;
import com.ISMIS.dto.DiagnosisDto;
import com.ISMIS.dto.PathologicalDto;
import com.ISMIS.model.ClientBMIBean;
import com.ISMIS.model.ClientMedication;

public interface ClientHealthDao {

	List<ClientBMIBean> getBmiList(Integer clientId);

	String saveClientBmi(ClientBMIBean bmiBean);

	String updateClientBmi(ClientBMIBean bmiBean);

	List<PsychometryTestDto> getClientPsychometryTestList(Integer clientId);

	String saveUpdateClientPsychometryTest(PsychometryTestDto psychometryTestDto);

	List<ClientMedication> getMedicationList(Integer clientId);

	String saveClientMedication(ClientMedication clientMedication);

	String updateClientMedication(ClientMedication clientMedication);

	DiagnosisDto getLastDiagnosisAndDoctor(Integer clientId);

	String saveUpdatePathologicalTest(PathologicalDto pathologicalDto);

	List<PathologicalDto> getPathologicalTestList(Integer clientId);

	LinkedHashMap<Integer, ArrayList<PathologicalDto>> getAllPathologicalTestListDateWise(Integer homeId, Date fromDate,
			Date toDate);

	LinkedHashMap<Integer, ArrayList<PsychometryTestDto>> getAllClientPsychometryTestListDateWise(Integer homeId,
			Date fromDate, Date toDate);

}
