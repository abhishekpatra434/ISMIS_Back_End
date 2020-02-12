package com.ISMIS.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ISMIS.dao.ClientDao;
import com.ISMIS.dao.ClientHealthDao;
import com.ISMIS.daoImpl.ClientHealthDaoImpl;
import com.ISMIS.dto.ClientDto;
import com.ISMIS.dto.PathologicalDto;
import com.ISMIS.dto.PsychometryTestDto;
import com.ISMIS.dto.Response;
import com.ISMIS.model.ClientMasterBean;
import com.ISMIS.service.ExportService;

@Service
public class ExportServiceImpl implements ExportService {

	static Logger logger = LogManager.getLogger(ExportServiceImpl.class);

	@Autowired
	ClientDao clientDao;

	@Autowired
	ClientHealthDao clientHealthDao;

	private File filetodownload = null;

	@Override
	public ResponseEntity<byte[]> exportToExcel(String homeId, String clientId, String cohortYr, String exportFor) {
		byte[] bytes = null;
		HttpHeaders httpHeaders = null;
		if (exportFor.equalsIgnoreCase("INTAKE")) {
			try {

				List<ClientDto> clientList = clientDao.getClientIntakeList(homeId, clientId, cohortYr);

				// List<ClientMasterBean> clientList = clientDao.getClientBasicInfoList(null,
				// null, homeId);

				String excelFileName = "AllClientList.xlsx";

				String sheetName = "Client List";

				if (clientList.size() > 0) {

					XSSFWorkbook wb = new XSSFWorkbook();
					FileOutputStream outXsl = new FileOutputStream(new File(excelFileName));
					wb.write(outXsl);
					XSSFSheet sheet = wb.createSheet(sheetName);
					XSSFRow firstRow = sheet.createRow(0);

					firstRow.createCell(0).setCellValue("Client UID");
					firstRow.createCell(1).setCellValue("Client Name");
					firstRow.createCell(2).setCellValue("Age");
					firstRow.createCell(3).setCellValue("Age Class");
					firstRow.createCell(4).setCellValue("Sex");
					firstRow.createCell(5).setCellValue("Type of Entry");
					firstRow.createCell(6).setCellValue("Date of Entry");
					firstRow.createCell(7).setCellValue("Cohort Year");
					firstRow.createCell(8).setCellValue("Found in Area");
					firstRow.createCell(9).setCellValue("Landmark");
					firstRow.createCell(10).setCellValue("Police Station");
					firstRow.createCell(11).setCellValue("Ward No");
					firstRow.createCell(12).setCellValue("Referred By ");
					firstRow.createCell(13).setCellValue("Referred By (If Others)");
					firstRow.createCell(14).setCellValue("Referred Description");
					firstRow.createCell(15).setCellValue("Reference No");
					firstRow.createCell(16).setCellValue("Indentification Marks");
					firstRow.createCell(17).setCellValue("Indentification Date");
					firstRow.createCell(18).setCellValue("Country");
					firstRow.createCell(19).setCellValue("State");
					firstRow.createCell(20).setCellValue("District");
					firstRow.createCell(21).setCellValue("Post Office");
					firstRow.createCell(22).setCellValue("Police Station");
					firstRow.createCell(23).setCellValue("Gram Panchayat/Mouza");
					firstRow.createCell(24).setCellValue("Para/Village");
					firstRow.createCell(25).setCellValue("Pincode");
					firstRow.createCell(26).setCellValue("Name of Guardian");
					firstRow.createCell(27).setCellValue("Address of Guardian");
					firstRow.createCell(28).setCellValue("Contact 1 of Guardian");
					firstRow.createCell(29).setCellValue("Contact 2 of Guardian");
					firstRow.createCell(30).setCellValue("Relationship with Guardian");
					firstRow.createCell(31).setCellValue("Relationship (If Others)");
					firstRow.createCell(32).setCellValue("Family Size");
					firstRow.createCell(33).setCellValue("Occupation of Family");
					firstRow.createCell(34).setCellValue("Monthly Income of Family");
					firstRow.createCell(35).setCellValue("Religion");
					firstRow.createCell(36).setCellValue("Religion (If Others)");
					firstRow.createCell(37).setCellValue("Mother Tongue");
					firstRow.createCell(38).setCellValue("Other Languages Known");
					firstRow.createCell(39).setCellValue("Education Level");
					firstRow.createCell(40).setCellValue("Any other information");
					firstRow.createCell(41).setCellValue("Provisional Diagnosis");
					firstRow.createCell(42).setCellValue("Co-morbidity");
					firstRow.createCell(43).setCellValue("Diagnosis Grouping");
					firstRow.createCell(44).setCellValue("Diagnosis Grouping (If Others)");
					firstRow.createCell(45).setCellValue("Any other medical conditions found");
					firstRow.createCell(46).setCellValue("Date of Entry");
					firstRow.createCell(47).setCellValue("Weight");
					firstRow.createCell(48).setCellValue("Height");
					firstRow.createCell(49).setCellValue("Blood Pressure");
					firstRow.createCell(50).setCellValue("BMI Score");
					firstRow.createCell(51).setCellValue("BMI Class");
					firstRow.createCell(52).setCellValue("Date of Administration");
					firstRow.createCell(53).setCellValue("SC");
					firstRow.createCell(54).setCellValue("IA");
					firstRow.createCell(55).setCellValue("C&U");
					firstRow.createCell(56).setCellValue("W");
					firstRow.createCell(57).setCellValue("DOI");
					firstRow.createCell(58).setCellValue("GIS");
					firstRow.createCell(59).setCellValue("Date of Administration");
					firstRow.createCell(60).setCellValue("PS");
					firstRow.createCell(61).setCellValue("NS");
					firstRow.createCell(62).setCellValue("GP");
					firstRow.createCell(63).setCellValue("Date of Administration");
					firstRow.createCell(64).setCellValue("GAF Score");
					firstRow.createCell(65).setCellValue("Date of Administration");
					firstRow.createCell(66).setCellValue("LSP Score");
					firstRow.createCell(67).setCellValue("Date of Administration");
					firstRow.createCell(68).setCellValue("TSH (in uIU/ml)");
					firstRow.createCell(69).setCellValue("T3 (in pg/ml)");
					firstRow.createCell(70).setCellValue("T4 (in mg/dl)");
					firstRow.createCell(71).setCellValue("Date of Administration");
					firstRow.createCell(72).setCellValue("RBS");
					firstRow.createCell(73).setCellValue("FBS (in mg/dl)");
					firstRow.createCell(74).setCellValue("PPBS");
					firstRow.createCell(75).setCellValue("Date of Administration");
					firstRow.createCell(76).setCellValue("Hemoglobin (in gm/dl)");
					firstRow.createCell(77).setCellValue("ESR");
					firstRow.createCell(78).setCellValue("Any other lab tests");
					firstRow.createCell(79).setCellValue("Date of Administration");
					firstRow.createCell(80).setCellValue("Caregiver Name");
					firstRow.createCell(81).setCellValue("Name of Doctor");
					firstRow.createCell(82).setCellValue("Date");
					firstRow.createCell(83).setCellValue("Name of Counsellor/Social Worker");
					firstRow.createCell(84).setCellValue("Date");
					firstRow.createCell(85).setCellValue("Name of Co-ordinator");
					firstRow.createCell(86).setCellValue("Date");
					firstRow.createCell(87).setCellValue("Remarks(if any)");

					for (int i = 0; i < clientList.size(); i++) {

						int j = i + 1;

						XSSFRow row = sheet.createRow(j);

						ClientDto ClientDto = clientList.get(i);

						for (int c = 0; c < 88; c++) {

							XSSFCell cell = row.createCell(c);

							switch (c) {
							case 0:
								cell.setCellValue(ClientDto.getClientMasterBean().getClientUid());
								break;
							case 1:
								cell.setCellValue(ClientDto.getClientMasterBean().getClientName());
								break;
							case 2:
								cell.setCellValue(ClientDto.getClientMasterBean().getAge() == null ? ""
										: ClientDto.getClientMasterBean().getAge().toString());
								break;
							case 3:
								cell.setCellValue(ClientDto.getClientMasterBean().getAgeClassStr() == null ? ""
										: ClientDto.getClientMasterBean().getAgeClassStr().toString());
								break;
							case 4:
								cell.setCellValue(ClientDto.getClientMasterBean().getSexStr());
								break;
							case 5:
								cell.setCellValue(ClientDto.getClientMasterBean().getTypeOfEntryStr());
								break;
							case 6:
								cell.setCellValue(ClientDto.getClientMasterBean().getDateOfEntry().toString());
								break;
							case 7:
								cell.setCellValue(ClientDto.getClientMasterBean().getCohortYear());
								break;
							case 8:
								cell.setCellValue(ClientDto.getClientMasterBean().getFoundInArea());
								break;
							case 9:
								cell.setCellValue(ClientDto.getClientMasterBean().getLandmark() == null ? ""
										: ClientDto.getClientMasterBean().getLandmark());
								break;
							case 10:
								cell.setCellValue(ClientDto.getClientMasterBean().getPoliceStation() == null ? ""
										: ClientDto.getClientMasterBean().getPoliceStation());
								break;
							case 11:
								cell.setCellValue(ClientDto.getClientMasterBean().getWardNo() == null ? ""
										: ClientDto.getClientMasterBean().getWardNo());
								break;
							case 12:
								cell.setCellValue(ClientDto.getClientMasterBean().getReferredByStr() == null ? ""
										: ClientDto.getClientMasterBean().getReferredByStr());
								break;
							case 13:
								cell.setCellValue(ClientDto.getClientMasterBean().getReferredByIfOth() == null ? ""
										: ClientDto.getClientMasterBean().getReferredByIfOth());
								break;
							case 14:
								cell.setCellValue(ClientDto.getClientMasterBean().getReferredDesc() == null ? ""
										: ClientDto.getClientMasterBean().getReferredDesc());
								break;
							case 15:
								cell.setCellValue(ClientDto.getClientMasterBean().getReferenceNo() == null ? ""
										: ClientDto.getClientMasterBean().getReferenceNo());
								break;
							case 16:
								cell.setCellValue(ClientDto.getClientMasterBean().getIdentificationMark() == null ? ""
										: ClientDto.getClientMasterBean().getIdentificationMark());
								break;
							case 17:
								cell.setCellValue(ClientDto.getClientMasterBean().getDateOfIdentification() == null ? ""
										: ClientDto.getClientMasterBean().getDateOfIdentification().toString());
								break;
							case 18:
								cell.setCellValue(ClientDto.getClientAddressBean().getCountry() == null ? ""
										: ClientDto.getClientAddressBean().getCountry());
								break;
							case 19:
								cell.setCellValue(ClientDto.getClientAddressBean().getState() == null ? ""
										: ClientDto.getClientAddressBean().getState());
								break;
							case 20:
								cell.setCellValue(ClientDto.getClientAddressBean().getDistrict() == null ? ""
										: ClientDto.getClientAddressBean().getDistrict());
								break;
							case 21:
								cell.setCellValue(ClientDto.getClientAddressBean().getPostOffice() == null ? ""
										: ClientDto.getClientAddressBean().getPostOffice());
								break;
							case 22:
								cell.setCellValue(ClientDto.getClientAddressBean().getPoliceStation() == null ? ""
										: ClientDto.getClientAddressBean().getPoliceStation());
								break;
							case 23:
								cell.setCellValue(ClientDto.getClientAddressBean().getGramPanchayatMouza() == null ? ""
										: ClientDto.getClientAddressBean().getGramPanchayatMouza());
								break;
							case 24:
								cell.setCellValue(ClientDto.getClientAddressBean().getParaVillage() == null ? ""
										: ClientDto.getClientAddressBean().getParaVillage());
								break;
							case 25:
								cell.setCellValue(ClientDto.getClientAddressBean().getPinNo() == null ? ""
										: ClientDto.getClientAddressBean().getPinNo());
								break;
							case 26:
								cell.setCellValue(ClientDto.getClientFamilyBean().getGuardianName() == null ? ""
										: ClientDto.getClientFamilyBean().getGuardianName());
								break;
							case 27:
								cell.setCellValue(ClientDto.getClientFamilyBean().getContactAddress() == null ? ""
										: ClientDto.getClientFamilyBean().getContactAddress());
								break;
							case 28:
								cell.setCellValue(ClientDto.getClientFamilyBean().getContact1No() == null ? ""
										: ClientDto.getClientFamilyBean().getContact1No());
								break;
							case 29:
								cell.setCellValue(ClientDto.getClientFamilyBean().getContact2No() == null ? ""
										: ClientDto.getClientFamilyBean().getContact2No());
								break;
							case 30:
								cell.setCellValue(
										ClientDto.getClientFamilyBean().getRelationWithGuardianStr() == null ? ""
												: ClientDto.getClientFamilyBean().getRelationWithGuardianStr());
								break;
							case 31:
								cell.setCellValue(ClientDto.getClientFamilyBean().getRelationIfOth() == null ? ""
										: ClientDto.getClientFamilyBean().getRelationIfOth());
								break;
							case 32:
								cell.setCellValue(ClientDto.getClientFamilyBean().getFamilySize() == null ? ""
										: ClientDto.getClientFamilyBean().getFamilySize().toString());
								break;
							case 33:
								cell.setCellValue(ClientDto.getClientFamilyBean().getOccupationOfFamily() == null ? ""
										: ClientDto.getClientFamilyBean().getOccupationOfFamily());
								break;
							case 34:
								cell.setCellValue(ClientDto.getClientFamilyBean().getMonthlyIncomeOfFamily() == null
										? ""
										: ClientDto.getClientFamilyBean().getMonthlyIncomeOfFamily().toString());
								break;
							case 35:
								cell.setCellValue(ClientDto.getClientMasterBean().getReligionIdStr() == null ? ""
										: ClientDto.getClientMasterBean().getReligionIdStr());
								break;
							case 36:
								cell.setCellValue(ClientDto.getClientMasterBean().getReligionIfOth() == null ? ""
										: ClientDto.getClientMasterBean().getReligionIfOth());
								break;
							case 37:
								cell.setCellValue(ClientDto.getClientMasterBean().getMotherTongue() == null ? ""
										: ClientDto.getClientMasterBean().getMotherTongue());
								break;
							case 38:
								cell.setCellValue(ClientDto.getClientMasterBean().getOthLangKnown() == null ? ""
										: ClientDto.getClientMasterBean().getOthLangKnown());
								break;
							case 39:
								cell.setCellValue(ClientDto.getClientMasterBean().getEducationLevelStr() == null ? ""
										: ClientDto.getClientMasterBean().getEducationLevelStr());
								break;
							case 40:
								cell.setCellValue(ClientDto.getClientMasterBean().getOthInformation() == null ? ""
										: ClientDto.getClientMasterBean().getOthInformation());
								break;
							case 41:
								cell.setCellValue(
										ClientDto.getClientInitMedicalDetBean().getProvisionalDiagnosis() == null ? ""
												: ClientDto.getClientInitMedicalDetBean().getProvisionalDiagnosis());
								break;
							case 42:
								cell.setCellValue(ClientDto.getClientInitMedicalDetBean().getComorbidity() == null ? ""
										: ClientDto.getClientInitMedicalDetBean().getComorbidity());
								break;
							case 43:
								cell.setCellValue(
										ClientDto.getClientInitMedicalDetBean().getDiagnosisGroupStr() == null ? ""
												: ClientDto.getClientInitMedicalDetBean().getDiagnosisGroupStr());
								break;
							case 44:
								cell.setCellValue(
										ClientDto.getClientInitMedicalDetBean().getDiagnosisGroupIfOth() == null ? ""
												: ClientDto.getClientInitMedicalDetBean().getDiagnosisGroupIfOth());
								break;
							case 45:
								cell.setCellValue(
										ClientDto.getClientInitMedicalDetBean().getOthMedicalConditionFound() == null
												? ""
												: ClientDto.getClientInitMedicalDetBean()
														.getOthMedicalConditionFound());
								break;
							case 46:
								cell.setCellValue(ClientDto.getClientBMIBean().getDateOfEntry() == null ? ""
										: ClientDto.getClientBMIBean().getDateOfEntry().toString());
								break;
							case 47:
								cell.setCellValue(ClientDto.getClientBMIBean().getWeight() == null ? ""
										: ClientDto.getClientBMIBean().getWeight().toString());
								break;
							case 48:
								cell.setCellValue(ClientDto.getClientBMIBean().getHeight() == null ? ""
										: ClientDto.getClientBMIBean().getHeight().toString());
								break;
							case 49:
								cell.setCellValue(ClientDto.getClientBMIBean().getBp() == null ? ""
										: ClientDto.getClientBMIBean().getBp());
								break;
							case 50:
								cell.setCellValue(ClientDto.getClientBMIBean().getBmi() == null ? ""
										: ClientDto.getClientBMIBean().getBmi().toString());
								break;
							case 51:
								cell.setCellValue(ClientDto.getClientBMIBean().getBmiClassStr() == null ? ""
										: ClientDto.getClientBMIBean().getBmiClassStr());
								break;
							case 52:
								cell.setCellValue(ClientDto.getClientPsychoIdeasBean().getDateOfEntry() == null ? ""
										: ClientDto.getClientPsychoIdeasBean().getDateOfEntry().toString());
								break;
							case 53:
								cell.setCellValue(ClientDto.getClientPsychoIdeasBean().getSc() == null ? ""
										: ClientDto.getClientPsychoIdeasBean().getSc().toString());
								break;
							case 54:
								cell.setCellValue(ClientDto.getClientPsychoIdeasBean().getIa() == null ? ""
										: ClientDto.getClientPsychoIdeasBean().getIa().toString());
								break;
							case 55:
								cell.setCellValue(ClientDto.getClientPsychoIdeasBean().getC_and_u() == null ? ""
										: ClientDto.getClientPsychoIdeasBean().getC_and_u().toString());
								break;
							case 56:
								cell.setCellValue(ClientDto.getClientPsychoIdeasBean().getWrk() == null ? ""
										: ClientDto.getClientPsychoIdeasBean().getWrk().toString());
								break;
							case 57:
								cell.setCellValue(ClientDto.getClientPsychoIdeasBean().getDoi() == null ? ""
										: ClientDto.getClientPsychoIdeasBean().getDoi().toString());
								break;
							case 58:
								cell.setCellValue(ClientDto.getClientPsychoIdeasBean().getGis() == null ? ""
										: ClientDto.getClientPsychoIdeasBean().getGis().toString());
								break;
							case 59:
								cell.setCellValue(ClientDto.getClientPsychoPanss().getDateOfEntry() == null ? ""
										: ClientDto.getClientPsychoPanss().getDateOfEntry().toString());
								break;
							case 60:
								cell.setCellValue(ClientDto.getClientPsychoPanss().getPs() == null ? ""
										: ClientDto.getClientPsychoPanss().getPs().toString());
								break;
							case 61:
								cell.setCellValue(ClientDto.getClientPsychoPanss().getNs() == null ? ""
										: ClientDto.getClientPsychoPanss().getNs().toString());
								break;
							case 62:
								cell.setCellValue(ClientDto.getClientPsychoPanss().getGp() == null ? ""
										: ClientDto.getClientPsychoPanss().getGp().toString());
								break;
							case 63:
								cell.setCellValue(ClientDto.getClientPsychoGafBean().getDateOfEntry() == null ? ""
										: ClientDto.getClientPsychoGafBean().getDateOfEntry().toString());
								break;
							case 64:
								cell.setCellValue(ClientDto.getClientPsychoGafBean().getGafScoreStr() == null ? ""
										: ClientDto.getClientPsychoGafBean().getGafScoreStr());
								break;
							case 65:
								cell.setCellValue(ClientDto.getClientPsychoLspBean().getDateOfEntry() == null ? ""
										: ClientDto.getClientPsychoLspBean().getDateOfEntry().toString());
								break;
							case 66:
								cell.setCellValue(ClientDto.getClientPsychoLspBean().getLspScore() == null ? ""
										: ClientDto.getClientPsychoLspBean().getLspScore().toString());
								break;
							case 67:
								cell.setCellValue(ClientDto.getClientThyroidSignificanceBean().getDateOfEntry() == null
										? ""
										: ClientDto.getClientThyroidSignificanceBean().getDateOfEntry().toString());
								break;
							case 68:
								cell.setCellValue(
										ClientDto.getClientThyroidSignificanceBean().getThyroTsh() == null ? ""
												: ClientDto.getClientThyroidSignificanceBean().getThyroTsh());
								break;
							case 69:
								cell.setCellValue(ClientDto.getClientThyroidSignificanceBean().getThyroT3() == null ? ""
										: ClientDto.getClientThyroidSignificanceBean().getThyroT3().toString());
								break;
							case 70:
								cell.setCellValue(ClientDto.getClientThyroidSignificanceBean().getThyroT4() == null ? ""
										: ClientDto.getClientThyroidSignificanceBean().getThyroT4().toString());
								break;
							case 71:
								cell.setCellValue(ClientDto.getClientDiabetesSignificanceBean().getDateOfEntry() == null
										? ""
										: ClientDto.getClientDiabetesSignificanceBean().getDateOfEntry().toString());
								break;
							case 72:
								cell.setCellValue(
										ClientDto.getClientDiabetesSignificanceBean().getDiabetesRBS() == null ? ""
												: ClientDto.getClientDiabetesSignificanceBean().getDiabetesRBS());
								break;
							case 73:
								cell.setCellValue(
										ClientDto.getClientDiabetesSignificanceBean().getDiabetesFBS() == null ? ""
												: ClientDto.getClientDiabetesSignificanceBean().getDiabetesFBS());
								break;
							case 74:
								cell.setCellValue(
										ClientDto.getClientDiabetesSignificanceBean().getDiabetesPPBS() == null ? ""
												: ClientDto.getClientDiabetesSignificanceBean().getDiabetesPPBS());
								break;
							case 75:
								cell.setCellValue(
										ClientDto.getClientHemoglobinSignificanceBean().getDateOfEntry() == null ? ""
												: ClientDto.getClientHemoglobinSignificanceBean().getDateOfEntry()
														.toString());
								break;
							case 76:
								cell.setCellValue(
										ClientDto.getClientHemoglobinSignificanceBean().getHbPercent() == null ? ""
												: ClientDto.getClientHemoglobinSignificanceBean().getHbPercent());
								break;
							case 77:
								cell.setCellValue(ClientDto.getClientHemoglobinSignificanceBean().getEsr() == null ? ""
										: ClientDto.getClientHemoglobinSignificanceBean().getEsr());
								break;
							case 78:
								cell.setCellValue(
										ClientDto.getClientHemoglobinSignificanceBean().getOthLabTest() == null ? ""
												: ClientDto.getClientHemoglobinSignificanceBean().getOthLabTest());
								break;
							case 79:
								cell.setCellValue(
										ClientDto.getClientHemoglobinSignificanceBean().getOthDateOfEntry() == null ? ""
												: ClientDto.getClientHemoglobinSignificanceBean().getOthDateOfEntry()
														.toString());
								break;
							case 80:
								cell.setCellValue(
										ClientDto.getClientIntakeAdministrationBean().getCaregiverName() == null ? ""
												: ClientDto.getClientIntakeAdministrationBean().getCaregiverName());
								break;
							case 81:
								cell.setCellValue(
										ClientDto.getClientIntakeAdministrationBean().getDoctorSignature() == null ? ""
												: ClientDto.getClientIntakeAdministrationBean().getDoctorSignature());
								break;
							case 82:
								cell.setCellValue(
										ClientDto.getClientIntakeAdministrationBean().getDoctorSignatureDate() == null
												? ""
												: ClientDto.getClientIntakeAdministrationBean().getDoctorSignatureDate()
														.toString());
								break;
							case 83:
								cell.setCellValue(
										ClientDto.getClientIntakeAdministrationBean().getCounsellorSwName() == null ? ""
												: ClientDto.getClientIntakeAdministrationBean().getCounsellorSignDate()
														.toString());
								break;
							case 84:
								cell.setCellValue(
										ClientDto.getClientIntakeAdministrationBean().getCoordinatorName() == null ? ""
												: ClientDto.getClientIntakeAdministrationBean().getCoordinatorName());
								break;
							case 85:
								cell.setCellValue(
										ClientDto.getClientIntakeAdministrationBean().getCoordinatorName() == null ? ""
												: ClientDto.getClientIntakeAdministrationBean().getCoordinatorName());
								break;
							case 86:
								cell.setCellValue(
										ClientDto.getClientIntakeAdministrationBean().getCoordinatorSignDate() == null
												? ""
												: ClientDto.getClientIntakeAdministrationBean().getCoordinatorSignDate()
														.toString());
								break;
							case 87:
								cell.setCellValue(
										ClientDto.getClientIntakeAdministrationBean().getRemarks() == null ? ""
												: ClientDto.getClientIntakeAdministrationBean().getRemarks());
								break;

							default:
								break;
							}

						}

					}
					FileOutputStream fileOut = new FileOutputStream(excelFileName);
					wb.write(fileOut);
					fileOut.flush();
					fileOut.close();

					filetodownload = new File(excelFileName);
					String testname = "AllClientList.xlsx";
					if (filetodownload.exists()) {
						InputStream in = new FileInputStream(filetodownload);
						bytes = IOUtils.toByteArray(in);
						in.close();
						wb.close();
						httpHeaders = new HttpHeaders();
						httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
						httpHeaders.setContentLength(bytes.length);

					}
				}

			} catch (Exception ex) {
				logger.info("Problem in Class - ExportServiceImpl ~~ method- exportToExcel() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
			}
		}
		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<byte[]> pathologicalTestExport(Integer homeId, Date fromDate, Date toDate) {

		byte[] bytes = null;
		HttpHeaders httpHeaders = null;
		ArrayList<PathologicalDto> pathologicalDtos = null;
		int j = 0;
		try {

			LinkedHashMap<Integer, ArrayList<PathologicalDto>> clientList = clientHealthDao
					.getAllPathologicalTestListDateWise(homeId, fromDate, toDate);

			String excelFileName = "AllClientHealthPathologicalList.xlsx";

			String sheetName = "Client Pathological Test List";

			if (!clientList.isEmpty() && clientList.size() > 0) {

				XSSFWorkbook wb = new XSSFWorkbook();
				FileOutputStream outXsl = new FileOutputStream(new File(excelFileName));
				wb.write(outXsl);
				XSSFSheet sheet = wb.createSheet(sheetName);
				XSSFRow firstRow = sheet.createRow(0);

				firstRow.createCell(0).setCellValue("Client UID");
				firstRow.createCell(1).setCellValue("Client Name");
				firstRow.createCell(2).setCellValue("Intake Date_Of_Entry");
				firstRow.createCell(3).setCellValue("Thyroid Significance - Date of Administration");
				firstRow.createCell(4).setCellValue("TSH (in uIU/ml)");
				firstRow.createCell(5).setCellValue("T3 (in pg/ml)");
				firstRow.createCell(6).setCellValue("T4 (in mg/dl)");
				firstRow.createCell(7).setCellValue("Diabetes Significance - Date of Administration");
				firstRow.createCell(8).setCellValue("RBS (in mg/dl)");
				firstRow.createCell(9).setCellValue("FBS (in mg/dl)");
				firstRow.createCell(10).setCellValue("PPBS (in gm/dl)");
				firstRow.createCell(11).setCellValue("Other Blood Tests - Date of Administration");
				firstRow.createCell(12).setCellValue("Hemoglobin (in mg/dl)");
				firstRow.createCell(13).setCellValue("ESR (in mm/hr)");
				firstRow.createCell(14).setCellValue("HIV Significance - Date of Administration");
				firstRow.createCell(15).setCellValue("HIV Result");

				for (Map.Entry<Integer, ArrayList<PathologicalDto>> entry : clientList.entrySet()) {

					pathologicalDtos = entry.getValue();

					for (int i = 0; i < pathologicalDtos.size(); i++) {

						// j = i + 1;
						j = j + 1;

						XSSFRow row = sheet.createRow(j);

						PathologicalDto pathologicalDto = pathologicalDtos.get(i);

						for (int c = 0; c < 16; c++) {

							XSSFCell cell = row.createCell(c);

							switch (c) {
							case 0:
								if (pathologicalDto.getClientMasterBean() != null) {
									cell.setCellValue(pathologicalDto.getClientMasterBean().getClientUid());
								}
								break;
							case 1:
								if (pathologicalDto.getClientMasterBean() != null) {
									cell.setCellValue(pathologicalDto.getClientMasterBean().getClientName());
								}
								break;
							case 2:
								if (pathologicalDto.getClientMasterBean() != null) {
									cell.setCellValue(
											pathologicalDto.getClientMasterBean().getDateOfEntry().toString());
								}
								break;
							case 3:
								cell.setCellValue(
										pathologicalDto.getClientThyroidSignificanceBean().getDateOfEntry() == null ? ""
												: pathologicalDto.getClientThyroidSignificanceBean().getDateOfEntry()
														.toString());
								break;
							case 4:
								cell.setCellValue(
										pathologicalDto.getClientThyroidSignificanceBean().getThyroTsh() == null ? ""
												: pathologicalDto.getClientThyroidSignificanceBean().getThyroTsh());
								break;
							case 5:
								cell.setCellValue(
										pathologicalDto.getClientThyroidSignificanceBean().getThyroT3() == null ? ""
												: pathologicalDto.getClientThyroidSignificanceBean().getThyroT3()
														.toString());
								break;
							case 6:
								cell.setCellValue(
										pathologicalDto.getClientThyroidSignificanceBean().getThyroT4() == null ? ""
												: pathologicalDto.getClientThyroidSignificanceBean().getThyroT4()
														.toString());
								break;
							case 7:
								cell.setCellValue(
										pathologicalDto.getClientDiabetesSignificanceBean().getDateOfEntry() == null
												? ""
												: pathologicalDto.getClientDiabetesSignificanceBean().getDateOfEntry()
														.toString());
								break;
							case 8:
								cell.setCellValue(
										pathologicalDto.getClientDiabetesSignificanceBean().getDiabetesRBS() == null
												? ""
												: pathologicalDto.getClientDiabetesSignificanceBean().getDiabetesRBS());
								break;
							case 9:
								cell.setCellValue(
										pathologicalDto.getClientDiabetesSignificanceBean().getDiabetesFBS() == null
												? ""
												: pathologicalDto.getClientDiabetesSignificanceBean().getDiabetesFBS());
								break;
							case 10:
								cell.setCellValue(
										pathologicalDto.getClientDiabetesSignificanceBean().getDiabetesPPBS() == null
												? ""
												: pathologicalDto.getClientDiabetesSignificanceBean()
														.getDiabetesPPBS());
								break;
							case 11:
								cell.setCellValue(
										pathologicalDto.getClientHemoglobinSignificanceBean().getDateOfEntry() == null
												? ""
												: pathologicalDto.getClientHemoglobinSignificanceBean().getDateOfEntry()
														.toString());
								break;
							case 12:
								cell.setCellValue(
										pathologicalDto.getClientHemoglobinSignificanceBean().getHbPercent() == null
												? ""
												: pathologicalDto.getClientHemoglobinSignificanceBean().getHbPercent());
								break;
							case 13:
								cell.setCellValue(
										pathologicalDto.getClientHemoglobinSignificanceBean().getEsr() == null ? ""
												: pathologicalDto.getClientHemoglobinSignificanceBean().getEsr());
								break;
							case 14:
								cell.setCellValue(pathologicalDto.getClientHivTestBean().getDateOfAdmin() == null ? ""
										: pathologicalDto.getClientHivTestBean().getDateOfAdmin().toString());
								break;
							case 15:
								cell.setCellValue(pathologicalDto.getClientHivTestBean().getHivData() == null ? ""
										: pathologicalDto.getClientHivTestBean().getHivData());
								break;
							default:
								break;
							}
						}
					}
				}

				FileOutputStream fileOut = new FileOutputStream(excelFileName);
				wb.write(fileOut);
				fileOut.flush();
				fileOut.close();

				filetodownload = new File(excelFileName);
				// String testname = "AllClientList.xlsx";
				if (filetodownload.exists()) {
					InputStream in = new FileInputStream(filetodownload);
					bytes = IOUtils.toByteArray(in);
					in.close();
					wb.close();
					httpHeaders = new HttpHeaders();
					httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					httpHeaders.setContentLength(bytes.length);
				}
			}

		} catch (Exception ex) {
			logger.info(
					"Problem in Class - ExportServiceImpl ~~ method- pathologicalTestExport() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<byte[]> psychometryTestExport(Integer homeId, Date fromDate, Date toDate) {

		byte[] bytes = null;
		HttpHeaders httpHeaders = null;
		ArrayList<PsychometryTestDto> psychometryTestDtos = null;
		int j = 0;
		try {

			LinkedHashMap<Integer, ArrayList<PsychometryTestDto>> clientList = clientHealthDao
					.getAllClientPsychometryTestListDateWise(homeId, fromDate, toDate);

			String excelFileName = "AllClientHealthPsychometryTestList.xlsx";

			String sheetName = "Client Psychometry Test List";

			if (!clientList.isEmpty() && clientList.size() > 0) {

				XSSFWorkbook wb = new XSSFWorkbook();
				FileOutputStream outXsl = new FileOutputStream(new File(excelFileName));
				wb.write(outXsl);
				XSSFSheet sheet = wb.createSheet(sheetName);
				XSSFRow firstRow = sheet.createRow(0);

				firstRow.createCell(0).setCellValue("Client UID");
				firstRow.createCell(1).setCellValue("Client Name");
				firstRow.createCell(2).setCellValue("Intake Date_Of_Entry");
				firstRow.createCell(3).setCellValue("IDEAS - Date of Administration");
				firstRow.createCell(4).setCellValue("SC(Self Care)");
				firstRow.createCell(5).setCellValue("IA(Interpersonal Activities)");
				firstRow.createCell(6).setCellValue("C&U(Communication & Understanding)");
				firstRow.createCell(7).setCellValue("W(Work)");
				firstRow.createCell(8).setCellValue("DOI(Duration of Illness)");
				firstRow.createCell(9).setCellValue("GIS(Global Disability Score)");
				firstRow.createCell(10).setCellValue("PANSS - Date of Administration");
				firstRow.createCell(11).setCellValue("PS(Positive Scale)");
				firstRow.createCell(12).setCellValue("NS(Negative Scale)");
				firstRow.createCell(13).setCellValue("GP(General Psychopathology)");
				firstRow.createCell(14).setCellValue("GAF - Date of Administration");
				firstRow.createCell(15).setCellValue("GAF(Global Assessment of Functioning) Score");
				firstRow.createCell(16).setCellValue("LSP - Date of Administration");
				firstRow.createCell(17).setCellValue("LSP(Life Skills Profile) Score");

				for (Map.Entry<Integer, ArrayList<PsychometryTestDto>> entry : clientList.entrySet()) {

					psychometryTestDtos = entry.getValue();

					for (int i = 0; i < psychometryTestDtos.size(); i++) {

						j = j + 1;

						XSSFRow row = sheet.createRow(j);

						PsychometryTestDto psychometryTestDto = psychometryTestDtos.get(i);

						for (int c = 0; c < 18; c++) {

							XSSFCell cell = row.createCell(c);

							switch (c) {
							case 0:
								if (psychometryTestDto.getClientMasterBean() != null) {
									cell.setCellValue(psychometryTestDto.getClientMasterBean().getClientUid());
								}
								break;
							case 1:
								if (psychometryTestDto.getClientMasterBean() != null) {
									cell.setCellValue(psychometryTestDto.getClientMasterBean().getClientName());
								}
								break;
							case 2:
								if (psychometryTestDto.getClientMasterBean() != null) {
									cell.setCellValue(
											psychometryTestDto.getClientMasterBean().getDateOfEntry().toString());
								}
								break;
							case 3:
								cell.setCellValue(psychometryTestDto.getClientPsychoIdeasBean().getDateOfEntry() == null
										? ""
										: psychometryTestDto.getClientPsychoIdeasBean().getDateOfEntry().toString());
								break;
							case 4:
								cell.setCellValue(psychometryTestDto.getClientPsychoIdeasBean().getSc() == null ? ""
										: psychometryTestDto.getClientPsychoIdeasBean().getSc().toString());
								break;
							case 5:
								cell.setCellValue(psychometryTestDto.getClientPsychoIdeasBean().getIa() == null ? ""
										: psychometryTestDto.getClientPsychoIdeasBean().getIa().toString());
								break;
							case 6:
								cell.setCellValue(psychometryTestDto.getClientPsychoIdeasBean().getC_and_u() == null
										? ""
										: psychometryTestDto.getClientPsychoIdeasBean().getC_and_u().toString());
								break;
							case 7:
								cell.setCellValue(psychometryTestDto.getClientPsychoIdeasBean().getWrk() == null ? ""
										: psychometryTestDto.getClientPsychoIdeasBean().getWrk().toString());
								break;
							case 8:
								cell.setCellValue(psychometryTestDto.getClientPsychoIdeasBean().getDoi() == null ? ""
										: psychometryTestDto.getClientPsychoIdeasBean().getDoi().toString());
								break;
							case 9:
								cell.setCellValue(psychometryTestDto.getClientPsychoIdeasBean().getGis() == null ? ""
										: psychometryTestDto.getClientPsychoIdeasBean().getGis().toString());
								break;
							case 10:
								cell.setCellValue(psychometryTestDto.getClientPsychoPanss().getDateOfEntry() == null
										? ""
										: psychometryTestDto.getClientPsychoPanss().getDateOfEntry().toString());
								break;
							case 11:
								cell.setCellValue(psychometryTestDto.getClientPsychoPanss().getPs() == null ? ""
										: psychometryTestDto.getClientPsychoPanss().getPs().toString());
								break;
							case 12:
								cell.setCellValue(psychometryTestDto.getClientPsychoPanss().getNs() == null ? ""
										: psychometryTestDto.getClientPsychoPanss().getNs().toString());
								break;
							case 13:
								cell.setCellValue(psychometryTestDto.getClientPsychoPanss().getGp() == null ? ""
										: psychometryTestDto.getClientPsychoPanss().getGp().toString());
								break;
							case 14:
								cell.setCellValue(psychometryTestDto.getClientPsychoGafBean().getDateOfEntry() == null
										? ""
										: psychometryTestDto.getClientPsychoGafBean().getDateOfEntry().toString());
								break;
							case 15:
								cell.setCellValue(psychometryTestDto.getClientPsychoGafBean().getGafScoreStr() == null ? ""
										: psychometryTestDto.getClientPsychoGafBean().getGafScoreStr());
								break;
							case 16:
								cell.setCellValue(psychometryTestDto.getClientPsychoLspBean().getDateOfEntry() == null
										? ""
										: psychometryTestDto.getClientPsychoLspBean().getDateOfEntry().toString());
								break;
							case 17:
								cell.setCellValue(psychometryTestDto.getClientPsychoLspBean().getLspScore() == null ? ""
										: psychometryTestDto.getClientPsychoLspBean().getLspScore().toString());
								break;
							default:
								break;
							}
						}
					}
				}

				FileOutputStream fileOut = new FileOutputStream(excelFileName);
				wb.write(fileOut);
				fileOut.flush();
				fileOut.close();

				filetodownload = new File(excelFileName);
				if (filetodownload.exists()) {
					InputStream in = new FileInputStream(filetodownload);
					bytes = IOUtils.toByteArray(in);
					in.close();
					wb.close();
					httpHeaders = new HttpHeaders();
					httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					httpHeaders.setContentLength(bytes.length);
				}
			}

		} catch (

		Exception ex) {
			logger.info("Problem in Class - ExportServiceImpl ~~ method- psychometryTestExport() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	}

}
