package com.ISMIS.daoImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.ISMIS.dao.ClientDao;
import com.ISMIS.dto.ClientDto;
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
import com.ISMIS.service.UploadService;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@Repository
public class ClientDaoImpl extends CommonDaoImpl implements ClientDao {

	static Logger logger = LogManager.getLogger(ClientDaoImpl.class);

	@Autowired
	private UploadService uploadservice;

	@Value("${upload-path}")
	String uploadPath;

	@Override
	public String saveClientIntakeDetails(ClientDto clientDto, MultipartFile file) {

		Connection conn = null;
		String status = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int cnt = 0;
		int clientId = 0;
		int createdBy = 0;
		int last_seq_no = 0;
		String prefix = null;
		int home_id = clientDto.getClientMasterBean().getHomeId();
		String uid_year = null;
		String message = null;
		String realPath = null;
		String clientUid = null;

		try {

			uid_year = StringUtils.substringBefore(clientDto.getClientMasterBean().getCohortYear(), "-");

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			/******************* INSERT CLIENT MASTER ****************/

			ClientMasterBean clientMasterBean = clientDto.getClientMasterBean();
			// clientMasterBean.setCreatedBy(1);
			createdBy = clientMasterBean.getCreatedBy();

			sql = "INSERT INTO mis_client_master (";
			sql += " HOME_ID, CLIENT_UID, CLIENT_NAME, AGE, SEX,";
			sql += " DATE_OF_ENTRY, TYPE_OF_ENTRY, COHORT_YEAR,";
			sql += " FOUND_IN_AREA, LANDMARK, POLICE_STATION,";
			sql += " WARD_NO, REFERRED_BY, REFERRED_DESC,";
			sql += " DATE_OF_IDENTIFICATION, IDENTIFICATION_MARK,";
			sql += " RELIGION, MOTHER_TONGUE, OTH_LANG_KNOWN,";
			sql += " EDUCATION_LEVEL, OTH_INFORMATION,";
			sql += " exit_date, exit_status, duration_of_stay, REFERENCE_NO, REFERRED_BY_IF_OTH,";
			sql += " RELIGION_IF_OTH, EXIT_STATUS_IF_OTH, EXIT_REMARKS,";
			sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
			sql += " ) VALUES";
			sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, clientMasterBean.getHomeId());

			if (clientMasterBean.getClientUid() == null) {
				ps.setNull(2, Types.VARCHAR);
			} else {
				ps.setString(2, clientMasterBean.getClientUid());
			}
			ps.setString(3, clientMasterBean.getClientName());

			if (clientMasterBean.getAge() == null) {
				ps.setNull(4, Types.INTEGER);
			} else {
				ps.setInt(4, clientMasterBean.getAge());
			}

			ps.setInt(5, clientMasterBean.getSex());
			ps.setDate(6, new java.sql.Date(clientMasterBean.getDateOfEntry().getTime()));
			ps.setInt(7, clientMasterBean.getTypeOfEntry());
			ps.setString(8, clientMasterBean.getCohortYear());

			if (clientMasterBean.getFoundInArea() == null) {
				ps.setNull(9, Types.VARCHAR);
			} else {
				ps.setString(9, clientMasterBean.getFoundInArea());
			}

			if (clientMasterBean.getLandmark() == null) {
				ps.setNull(10, Types.VARCHAR);
			} else {
				ps.setString(10, clientMasterBean.getLandmark());
			}

			if (clientMasterBean.getPoliceStation() == null) {
				ps.setNull(11, Types.VARCHAR);
			} else {
				ps.setString(11, clientMasterBean.getPoliceStation());
			}

			if (clientMasterBean.getWardNo() == null) {
				ps.setNull(12, Types.VARCHAR);
			} else {
				ps.setString(12, clientMasterBean.getWardNo());
			}

			if (clientMasterBean.getReferredBy() == null) {
				ps.setNull(13, Types.INTEGER);
			} else {
				ps.setInt(13, clientMasterBean.getReferredBy());
			}

			if (clientMasterBean.getReferredDesc() == null) {
				ps.setNull(14, Types.VARCHAR);
			} else {
				ps.setString(14, clientMasterBean.getReferredDesc());
			}

			if (clientMasterBean.getDateOfIdentification() == null) {
				ps.setNull(15, Types.DATE);
			} else {
				ps.setDate(15, new java.sql.Date(clientMasterBean.getDateOfIdentification().getTime()));
			}

			if (clientMasterBean.getIdentificationMark() == null) {
				ps.setNull(16, Types.VARCHAR);
			} else {
				ps.setString(16, clientMasterBean.getIdentificationMark());
			}

			if (clientMasterBean.getReligionId() == null) {
				ps.setNull(17, Types.INTEGER);
			} else {
				ps.setInt(17, clientMasterBean.getReligionId());
			}

			if (clientMasterBean.getMotherTongue() == null) {
				ps.setNull(18, Types.VARCHAR);
			} else {
				ps.setString(18, clientMasterBean.getMotherTongue());
			}

			if (clientMasterBean.getOthLangKnown() == null) {
				ps.setNull(19, Types.VARCHAR);
			} else {
				ps.setString(19, clientMasterBean.getOthLangKnown());
			}

			if (clientMasterBean.getEducationLevel() == null) {
				ps.setNull(20, Types.INTEGER);
			} else {
				ps.setInt(20, clientMasterBean.getEducationLevel());
			}

			if (clientMasterBean.getOthInformation() == null) {
				ps.setNull(21, Types.VARCHAR);
			} else {
				ps.setString(21, clientMasterBean.getOthInformation());
			}

			if (clientMasterBean.getExitDate() == null) {
				ps.setNull(22, Types.DATE);
			} else {
				ps.setDate(22, new java.sql.Date(clientMasterBean.getExitDate().getTime()));
			}

			if (clientMasterBean.getExitStatus() == null) {
				ps.setNull(23, Types.INTEGER);
			} else {
				ps.setInt(23, clientMasterBean.getExitStatus());
			}

			if (clientMasterBean.getDurationOfStay() == null) {
				ps.setNull(24, Types.INTEGER);
			} else {
				ps.setInt(24, clientMasterBean.getDurationOfStay());
			}

			if (clientMasterBean.getReferenceNo() == null) {
				ps.setNull(25, Types.VARCHAR);
			} else {
				ps.setString(25, clientMasterBean.getReferenceNo());
			}

			if (clientMasterBean.getReferredByIfOth() == null) {
				ps.setNull(26, Types.VARCHAR);
			} else {
				ps.setString(26, clientMasterBean.getReferredByIfOth());
			}

			if (clientMasterBean.getReligionIfOth() == null) {
				ps.setNull(27, Types.VARCHAR);
			} else {
				ps.setString(27, clientMasterBean.getReligionIfOth());
			}

			if (clientMasterBean.getExitStatusIfOth() == null) {
				ps.setNull(28, Types.VARCHAR);
			} else {
				ps.setString(28, clientMasterBean.getExitStatusIfOth());
			}

			if (clientMasterBean.getExitRemarks() == null) {
				ps.setNull(29, Types.VARCHAR);
			} else {
				ps.setString(29, clientMasterBean.getExitRemarks());
			}

			ps.setInt(30, createdBy);
			ps.setInt(31, createdBy);

			cnt = ps.executeUpdate();
			if (cnt > 0) {

				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					clientId = (rs.getInt(1));
					clientMasterBean.setClientId(clientId);
				}

				ps.clearParameters();

				/******************* INSERT CLIENT ADDRESS TABLE ****************/

				ClientAddressBean addressBean = clientDto.getClientAddressBean();

				sql = "INSERT INTO mis_client_address (";
				sql += " CLIENT_ID, gram_panchayat_mouza, para_village, police_station,";
				sql += " post_office, pin_no, district,";
				sql += " state, country, CREATED_BY, MODIFIED_BY, CREATED_ON";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (addressBean.getGramPanchayatMouza() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, addressBean.getGramPanchayatMouza());
				}

				if (addressBean.getParaVillage() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, addressBean.getParaVillage());
				}

				if (addressBean.getPoliceStation() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, addressBean.getPoliceStation());
				}

				if (addressBean.getPostOffice() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, addressBean.getPostOffice());
				}

				if (addressBean.getPinNo() == null) {
					ps.setNull(6, Types.VARCHAR);
				} else {
					ps.setString(6, addressBean.getPinNo());
				}

				if (addressBean.getDistrict() == null) {
					ps.setNull(7, Types.VARCHAR);
				} else {
					ps.setString(7, addressBean.getDistrict());
				}

				if (addressBean.getState() == null) {
					ps.setNull(8, Types.VARCHAR);
				} else {
					ps.setString(8, addressBean.getState());
				}

				if (addressBean.getCountry() == null) {
					ps.setNull(9, Types.VARCHAR);
				} else {
					ps.setString(9, addressBean.getCountry());
				}

				ps.setInt(10, createdBy);
				ps.setInt(11, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					addressBean.setAddressId(rs.getInt(1));
					addressBean.setClientId(clientId);
				}

				ps.clearParameters();

				/******************* INSERT CLIENT FAMILY TABLE ****************/

				ClientFamilyBean clientFamilyBean = clientDto.getClientFamilyBean();

				sql = "INSERT INTO mis_client_family (";
				sql += " CLIENT_ID, guardian_name, contact_address, contact1_no, contact2_no,";
				sql += " relation_with_guardian, family_size, occupation_of_family,";
				sql += " monthly_income_of_family, relation_if_oth, CREATED_BY, MODIFIED_BY, CREATED_ON";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (clientFamilyBean.getGuardianName() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, clientFamilyBean.getGuardianName());
				}

				if (clientFamilyBean.getContactAddress() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, clientFamilyBean.getContactAddress());
				}

				if (clientFamilyBean.getContact1No() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, clientFamilyBean.getContact1No());
				}

				if (clientFamilyBean.getContact2No() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, clientFamilyBean.getContact2No());
				}

				if (clientFamilyBean.getRelationWithGuardian() == null) {
					ps.setNull(6, Types.VARCHAR);
				} else {
					ps.setInt(6, clientFamilyBean.getRelationWithGuardian());
				}

				if (clientFamilyBean.getFamilySize() == null) {
					ps.setNull(7, Types.VARCHAR);
				} else {
					ps.setInt(7, clientFamilyBean.getFamilySize());
				}

				if (clientFamilyBean.getOccupationOfFamily() == null) {
					ps.setNull(8, Types.VARCHAR);
				} else {
					ps.setString(8, clientFamilyBean.getOccupationOfFamily());
				}

				if (clientFamilyBean.getMonthlyIncomeOfFamily() == null) {
					ps.setNull(9, Types.DECIMAL);
				} else {
					ps.setDouble(9, clientFamilyBean.getMonthlyIncomeOfFamily());
				}

				if (clientFamilyBean.getRelationIfOth() == null) {
					ps.setNull(10, Types.VARCHAR);
				} else {
					ps.setString(10, clientFamilyBean.getRelationIfOth());
				}

				ps.setInt(11, createdBy);
				ps.setInt(12, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					clientFamilyBean.setFamilyId(rs.getInt(1));
					clientFamilyBean.setClientId(clientId);
				}

				ps.clearParameters();

				/******************* INSERT CLIENT INIT MEDICAL DET TABLE ****************/

				ClientInitMedicalDetBean clientInitMedicalDetBean = clientDto.getClientInitMedicalDetBean();

				sql = "INSERT INTO mis_client_init_medical_det (";
				sql += " CLIENT_ID, provisional_diagnosis, diagnosis_group, comorbidity,";
				sql += " oth_medical_condition_found, diagnosis_group_if_oth, CREATED_BY, MODIFIED_BY, CREATED_ON";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (clientInitMedicalDetBean.getProvisionalDiagnosis() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, clientInitMedicalDetBean.getProvisionalDiagnosis());
				}

				if (clientInitMedicalDetBean.getDiagnosisGroup() == null) {
					ps.setNull(3, Types.INTEGER);
				} else {
					ps.setInt(3, clientInitMedicalDetBean.getDiagnosisGroup());
				}

				if (clientInitMedicalDetBean.getComorbidity() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, clientInitMedicalDetBean.getComorbidity());
				}

				if (clientInitMedicalDetBean.getOthMedicalConditionFound() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, clientInitMedicalDetBean.getOthMedicalConditionFound());
				}

				if (clientInitMedicalDetBean.getDiagnosisGroupIfOth() == null) {
					ps.setNull(6, Types.VARCHAR);
				} else {
					ps.setString(6, clientInitMedicalDetBean.getDiagnosisGroupIfOth());
				}

				ps.setInt(7, createdBy);
				ps.setInt(8, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					clientInitMedicalDetBean.setInitMedicalDetId(rs.getInt(1));
					clientInitMedicalDetBean.setClientId(clientId);
				}

				ps.clearParameters();

				/******************* INSERT CLIENT BMI TABLE ****************/

				ClientBMIBean bmiBean = clientDto.getClientBMIBean();

				sql = "INSERT INTO mis_client_bmi (";
				sql += " CLIENT_ID, weight, weight_unit, height,";
				sql += " height_unit, bmi, bmi_class, bp, date_of_entry,";
				sql += " cohort_year, qtr_of_year, entry_flag, CREATED_BY, MODIFIED_BY, CREATED_ON";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (bmiBean.getWeight() == null) {
					ps.setNull(2, Types.DOUBLE);
				} else {
					ps.setDouble(2, bmiBean.getWeight());
				}

				if (bmiBean.getWeightUnit() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, bmiBean.getWeightUnit());
				}

				if (bmiBean.getHeight() == null) {
					ps.setNull(4, Types.DOUBLE);
				} else {
					ps.setDouble(4, bmiBean.getHeight());
				}

				if (bmiBean.getHeightUnit() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, bmiBean.getHeightUnit());
				}

				if (bmiBean.getBmi() == null) {
					ps.setNull(6, Types.DOUBLE);
				} else {
					ps.setDouble(6, bmiBean.getBmi());
				}

				if (bmiBean.getBmiClass() == null) {
					ps.setNull(7, Types.INTEGER);
				} else {
					ps.setInt(7, bmiBean.getBmiClass());
				}

				if (bmiBean.getBp() == null) {
					ps.setNull(8, Types.VARCHAR);
				} else {
					ps.setString(8, bmiBean.getBp());
				}

				if (bmiBean.getDateOfEntry() == null) {
					ps.setNull(9, Types.DATE);
				} else {
					ps.setDate(9, new java.sql.Date(bmiBean.getDateOfEntry().getTime()));
					// java.sql.Date date = java.sql.Date.valueOf(bmiBean.getDateOfEntry());
					// ps.setDate(8, date);
				}

				if (bmiBean.getCohortYear() == null) {
					ps.setNull(10, Types.VARCHAR);
				} else {
					ps.setString(10, bmiBean.getCohortYear());
				}

				if (bmiBean.getQtrOfYear() == null) {
					ps.setNull(11, Types.INTEGER);
				} else {
					ps.setInt(11, bmiBean.getQtrOfYear());
				}

				ps.setString(12, "I");
				ps.setInt(13, createdBy);
				ps.setInt(14, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					bmiBean.setBmiId(rs.getInt(1));
					bmiBean.setClientId(clientId);
				}

				ps.clearParameters();

				/******************* INSERT CLIENT Psycho Gaf TABLE ****************/

				ClientPsychoGafBean clientPsychoGafBean = clientDto.getClientPsychoGafBean();

				sql = "INSERT INTO mis_client_psycho_gaf (";
				sql += " CLIENT_ID, gaf_score, gaf_class, date_of_entry,";
				sql += " cohort_year, qtr_of_year, entry_flag,";
				sql += " CREATED_BY, MODIFIED_BY, CREATED_ON ";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (clientPsychoGafBean.getGafScore() == null) {
					ps.setNull(2, Types.INTEGER);
				} else {
					ps.setInt(2, clientPsychoGafBean.getGafScore());
				}

				if (clientPsychoGafBean.getGafClass() == null) {
					ps.setNull(3, Types.INTEGER);
				} else {
					ps.setInt(3, clientPsychoGafBean.getGafClass());
				}

				if (clientPsychoGafBean.getDateOfEntry() == null) {
					ps.setNull(4, Types.DATE);
				} else {
					ps.setDate(4, new java.sql.Date(clientPsychoGafBean.getDateOfEntry().getTime()));
				}

				// ps.setString(5, clientPsychoGafBean.getCohortYear());
				// ps.setInt(6, clientPsychoGafBean.getQtrOfYear());

				if (clientPsychoGafBean.getCohortYear() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, clientPsychoGafBean.getCohortYear());
				}

				if (clientPsychoGafBean.getQtrOfYear() == null) {
					ps.setNull(6, Types.INTEGER);
				} else {
					ps.setInt(6, clientPsychoGafBean.getQtrOfYear());
				}

				ps.setString(7, "I");
				ps.setInt(8, createdBy);
				ps.setInt(9, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					clientPsychoGafBean.setPsychoGafId(rs.getInt(1));
					clientPsychoGafBean.setClientId(clientId);
				}

				ps.clearParameters();

				/******************* INSERT CLIENT Psycho IDEAS TABLE ****************/

				ClientPsychoIdeasBean clientPsychoIdeasBean = clientDto.getClientPsychoIdeasBean();

				sql = "INSERT INTO mis_client_psycho_ideas (";
				sql += " CLIENT_ID, sc, ia, c_and_u,";
				sql += " wrk, doi, gds, gis,";
				sql += " ideas_status, date_of_entry,";
				sql += " cohort_year, qtr_of_year, entry_flag,";
				sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (clientPsychoIdeasBean.getSc() == null) {
					ps.setNull(2, Types.INTEGER);
				} else {
					ps.setInt(2, clientPsychoIdeasBean.getSc());
				}

				if (clientPsychoIdeasBean.getIa() == null) {
					ps.setNull(3, Types.INTEGER);
				} else {
					ps.setInt(3, clientPsychoIdeasBean.getIa());
				}

				if (clientPsychoIdeasBean.getC_and_u() == null) {
					ps.setNull(4, Types.INTEGER);
				} else {
					ps.setInt(4, clientPsychoIdeasBean.getC_and_u());
				}

				if (clientPsychoIdeasBean.getWrk() == null) {
					ps.setNull(5, Types.INTEGER);
				} else {
					ps.setInt(5, clientPsychoIdeasBean.getWrk());
				}

				if (clientPsychoIdeasBean.getDoi() == null) {
					ps.setNull(6, Types.INTEGER);
				} else {
					ps.setInt(6, clientPsychoIdeasBean.getDoi());
				}

				if (clientPsychoIdeasBean.getGis() == null) {
					ps.setNull(7, Types.INTEGER);
				} else {
					ps.setInt(7, clientPsychoIdeasBean.getGis());
				}

				if (clientPsychoIdeasBean.getGis() == null) {
					ps.setNull(8, Types.INTEGER);
				} else {
					ps.setInt(8, clientPsychoIdeasBean.getGis());
				}

				if (clientPsychoIdeasBean.getIdeasStatus() == null) {
					ps.setNull(9, Types.VARCHAR);
				} else {
					ps.setString(9, clientPsychoIdeasBean.getIdeasStatus());
				}

				if (clientPsychoIdeasBean.getDateOfEntry() == null) {
					ps.setNull(10, Types.DATE);
				} else {
					ps.setDate(10, new java.sql.Date(clientPsychoIdeasBean.getDateOfEntry().getTime()));
				}

				// ps.setString(11, clientPsychoIdeasBean.getCohortYear());
				// ps.setInt(12, clientPsychoIdeasBean.getQtrOfYear());

				if (clientPsychoIdeasBean.getCohortYear() == null) {
					ps.setNull(11, Types.VARCHAR);
				} else {
					ps.setString(11, clientPsychoIdeasBean.getCohortYear());
				}

				if (clientPsychoIdeasBean.getQtrOfYear() == null) {
					ps.setNull(12, Types.INTEGER);
				} else {
					ps.setInt(12, clientPsychoIdeasBean.getQtrOfYear());
				}

				ps.setString(13, "I");
				ps.setInt(14, createdBy);
				ps.setInt(15, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					clientPsychoIdeasBean.setPsychoIdeasId(rs.getInt(1));
					clientPsychoIdeasBean.setClientId(clientId);
				}

				ps.clearParameters();

				/******************* INSERT CLIENT Psycho LSP TABLE ****************/

				ClientPsychoLspBean clientPsychoLspBean = clientDto.getClientPsychoLspBean();

				sql = "INSERT INTO mis_client_psycho_lsp (";
				sql += " CLIENT_ID, lsp_score, lsp_class, date_of_entry,";
				sql += " cohort_year, qtr_of_year, entry_flag,";
				sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (clientPsychoLspBean.getLspScore() == null) {
					ps.setNull(2, Types.INTEGER);
				} else {
					ps.setInt(2, clientPsychoLspBean.getLspScore());
				}

				if (clientPsychoLspBean.getLspClass() == null) {
					ps.setNull(3, Types.INTEGER);
				} else {
					ps.setInt(3, clientPsychoLspBean.getLspClass());
				}

				if (clientPsychoLspBean.getDateOfEntry() == null) {
					ps.setNull(4, Types.DATE);
				} else {
					ps.setDate(4, new java.sql.Date(clientPsychoLspBean.getDateOfEntry().getTime()));
				}

				// ps.setString(5, clientPsychoLspBean.getCohortYear());
				// ps.setInt(6, clientPsychoLspBean.getQtrOfYear());

				if (clientPsychoLspBean.getCohortYear() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, clientPsychoLspBean.getCohortYear());
				}

				if (clientPsychoLspBean.getQtrOfYear() == null) {
					ps.setNull(6, Types.INTEGER);
				} else {
					ps.setInt(6, clientPsychoLspBean.getQtrOfYear());
				}

				ps.setString(7, "I");
				ps.setInt(8, createdBy);
				ps.setInt(9, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					clientPsychoLspBean.setPsychoLspId(rs.getInt(1));
					clientPsychoLspBean.setClientId(clientId);
				}

				ps.clearParameters();

				/******************* INSERT CLIENT Psycho PANSS TABLE ****************/

				ClientPsychoPanssBean clientPsychoPanssBean = clientDto.getClientPsychoPanss();

				sql = "INSERT INTO mis_client_psycho_panss (";
				sql += " CLIENT_ID, ps, ns, gp,";
				sql += " total, date_of_entry,";
				sql += " cohort_year, qtr_of_year, entry_flag,";
				sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (clientPsychoPanssBean.getPs() == null) {
					ps.setNull(2, Types.INTEGER);
				} else {
					ps.setInt(2, clientPsychoPanssBean.getPs());
				}

				if (clientPsychoPanssBean.getNs() == null) {
					ps.setNull(3, Types.INTEGER);
				} else {
					ps.setInt(3, clientPsychoPanssBean.getNs());
				}

				if (clientPsychoPanssBean.getGp() == null) {
					ps.setNull(4, Types.INTEGER);
				} else {
					ps.setInt(4, clientPsychoPanssBean.getGp());
				}

				if (clientPsychoPanssBean.getTotal() == null) {
					ps.setNull(5, Types.INTEGER);
				} else {
					ps.setInt(5, clientPsychoPanssBean.getTotal());
				}

				if (clientPsychoPanssBean.getDateOfEntry() == null) {
					ps.setNull(6, Types.DATE);
				} else {
					ps.setDate(6, new java.sql.Date(clientPsychoPanssBean.getDateOfEntry().getTime()));
				}

				// ps.setString(7, clientPsychoPanssBean.getCohortYear());
				// ps.setInt(8, clientPsychoPanssBean.getQtrOfYear());

				if (clientPsychoPanssBean.getCohortYear() == null) {
					ps.setNull(7, Types.VARCHAR);
				} else {
					ps.setString(7, clientPsychoPanssBean.getCohortYear());
				}

				if (clientPsychoPanssBean.getQtrOfYear() == null) {
					ps.setNull(8, Types.INTEGER);
				} else {
					ps.setInt(8, clientPsychoPanssBean.getQtrOfYear());
				}

				ps.setString(9, "I");
				ps.setInt(10, createdBy);
				ps.setInt(11, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					clientPsychoPanssBean.setPsychoPanssId(rs.getInt(1));
					clientPsychoPanssBean.setClientId(clientId);
				}

				ps.clearParameters();

				/******************* INSERT CLIENT THYROID SIGNIFICANTS TABLE ****************/

				ClientThyroidSignificanceBean clientThyroidSignificanceBean = clientDto
						.getClientThyroidSignificanceBean();

				sql = "INSERT INTO mis_client_thyroid_significance (";
				sql += " CLIENT_ID, thyro_tsh, thyro_T3, thyro_T4,";
				sql += " date_of_entry,";
				sql += " cohort_year, qtr_of_year, entry_flag,";
				sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (clientThyroidSignificanceBean.getThyroTsh() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, clientThyroidSignificanceBean.getThyroTsh());
				}

				if (clientThyroidSignificanceBean.getThyroT3() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, clientThyroidSignificanceBean.getThyroT3());
				}

				if (clientThyroidSignificanceBean.getThyroT4() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, clientThyroidSignificanceBean.getThyroT4());
				}

				if (clientThyroidSignificanceBean.getDateOfEntry() == null) {
					ps.setNull(5, Types.DATE);
				} else {
					ps.setDate(5, new java.sql.Date(clientThyroidSignificanceBean.getDateOfEntry().getTime()));
				}

				// ps.setString(6, clientThyroidSignificanceBean.getCohortYear());
				// ps.setInt(7, clientThyroidSignificanceBean.getQtrOfYear());

				if (clientThyroidSignificanceBean.getCohortYear() == null) {
					ps.setNull(6, Types.VARCHAR);
				} else {
					ps.setString(6, clientThyroidSignificanceBean.getCohortYear());
				}

				if (clientThyroidSignificanceBean.getQtrOfYear() == null) {
					ps.setNull(7, Types.INTEGER);
				} else {
					ps.setInt(7, clientThyroidSignificanceBean.getQtrOfYear());
				}

				ps.setString(8, "I");
				ps.setInt(9, createdBy);
				ps.setInt(10, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					clientThyroidSignificanceBean.setThyroidSignfId(rs.getInt(1));
					clientThyroidSignificanceBean.setClientId(clientId);
				}

				ps.clearParameters();

				/******************* INSERT CLIENT DIABETES SIGNIFICANTS TABLE ****************/

				ClientDiabetesSignificanceBean clientDiabetesSignificanceBean = clientDto
						.getClientDiabetesSignificanceBean();

				sql = "INSERT INTO mis_client_diabetes_significance (";
				sql += " CLIENT_ID, diabetes_rbs, diabetes_fbs, diabetes_ppbs,";
				sql += " date_of_entry,";
				sql += " cohort_year, qtr_of_year, entry_flag,";
				sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (clientDiabetesSignificanceBean.getDiabetesRBS() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, clientDiabetesSignificanceBean.getDiabetesRBS());
				}

				if (clientDiabetesSignificanceBean.getDiabetesFBS() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, clientDiabetesSignificanceBean.getDiabetesFBS());
				}

				if (clientDiabetesSignificanceBean.getDiabetesPPBS() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, clientDiabetesSignificanceBean.getDiabetesPPBS());
				}

				if (clientDiabetesSignificanceBean.getDateOfEntry() == null) {
					ps.setNull(5, Types.DATE);
				} else {
					ps.setDate(5, new java.sql.Date(clientDiabetesSignificanceBean.getDateOfEntry().getTime()));
				}

				// ps.setString(6, clientDiabetesSignificanceBean.getCohortYear());
				// ps.setInt(7, clientDiabetesSignificanceBean.getQtrOfYear());

				if (clientDiabetesSignificanceBean.getCohortYear() == null) {
					ps.setNull(6, Types.VARCHAR);
				} else {
					ps.setString(6, clientDiabetesSignificanceBean.getCohortYear());
				}

				if (clientDiabetesSignificanceBean.getQtrOfYear() == null) {
					ps.setNull(7, Types.INTEGER);
				} else {
					ps.setInt(7, clientDiabetesSignificanceBean.getQtrOfYear());
				}

				ps.setString(8, "I");
				ps.setInt(9, createdBy);
				ps.setInt(10, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					clientDiabetesSignificanceBean.setDiabetesSignfId(rs.getInt(1));
					clientDiabetesSignificanceBean.setClientId(clientId);
				}

				ps.clearParameters();

				/*****************
				 * INSERT CLIENT HEMOGLOBIN SIGNIFICANCE TABLE
				 ****************/

				ClientHemoglobinSignificanceBean clientHemoglobinSignificanceBean = clientDto
						.getClientHemoglobinSignificanceBean();

				sql = "INSERT INTO mis_client_hemoglobin_significance (";
				sql += " CLIENT_ID, hb_percent, esr, hemoglobin_level,";
				sql += " oth_lab_test, oth_date_of_entry,";
				sql += " cohort_year, qtr_of_year,";
				sql += " date_of_entry, entry_flag,";
				sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (clientHemoglobinSignificanceBean.getHbPercent() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, clientHemoglobinSignificanceBean.getHbPercent());
				}

				if (clientHemoglobinSignificanceBean.getEsr() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, clientHemoglobinSignificanceBean.getEsr());
				}

				if (clientHemoglobinSignificanceBean.getHemoglobinLevel() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, clientHemoglobinSignificanceBean.getHemoglobinLevel());
				}

				if (clientHemoglobinSignificanceBean.getOthLabTest() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, clientHemoglobinSignificanceBean.getOthLabTest());
				}

				if (clientHemoglobinSignificanceBean.getOthDateOfEntry() == null) {
					ps.setNull(6, Types.DATE);
				} else {
					ps.setDate(6, new java.sql.Date(clientHemoglobinSignificanceBean.getOthDateOfEntry().getTime()));
				}

				// ps.setString(7, clientHemoglobinSignificanceBean.getCohortYear());
				// ps.setInt(8, clientHemoglobinSignificanceBean.getQtrOfYear());

				if (clientHemoglobinSignificanceBean.getCohortYear() == null) {
					ps.setNull(7, Types.VARCHAR);
				} else {
					ps.setString(7, clientHemoglobinSignificanceBean.getCohortYear());
				}

				if (clientHemoglobinSignificanceBean.getQtrOfYear() == null) {
					ps.setNull(8, Types.INTEGER);
				} else {
					ps.setInt(8, clientHemoglobinSignificanceBean.getQtrOfYear());
				}

				if (clientHemoglobinSignificanceBean.getDateOfEntry() == null) {
					ps.setNull(9, Types.DATE);
				} else {
					ps.setDate(9, new java.sql.Date(clientHemoglobinSignificanceBean.getDateOfEntry().getTime()));
				}
				ps.setString(10, "I");
				ps.setInt(11, createdBy);
				ps.setInt(12, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					clientHemoglobinSignificanceBean.setHemoglobinSignfId(rs.getInt(1));
					clientHemoglobinSignificanceBean.setClientId(clientId);
				}

				ps.clearParameters();

				/******************* INSERT CLIENT INTAKE ADMINISTRATION TABLE ****************/

				ClientIntakeAdministrationBean clientIntakeAdministrationBean = clientDto
						.getClientIntakeAdministrationBean();

				sql = "INSERT INTO mis_client_intake_administration (";
				sql += " CLIENT_ID, caregiver_name, doctor_signature,";
				sql += " doctor_signature_date,";
				sql += " counsellor_sw_name, counsellor_sign_date,";
				sql += " coordinator_name, coordinator_sign_date,";
				sql += " remarks, CREATED_BY, MODIFIED_BY, CREATED_ON";
				sql += " ) VALUES";
				sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, clientId);

				if (clientIntakeAdministrationBean.getCaregiverName() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, clientIntakeAdministrationBean.getCaregiverName());
				}

				if (clientIntakeAdministrationBean.getDoctorSignature() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, clientIntakeAdministrationBean.getDoctorSignature());
				}

				if (clientIntakeAdministrationBean.getDoctorSignatureDate() == null) {
					ps.setNull(4, Types.DATE);
				} else {
					ps.setDate(4, new java.sql.Date(clientIntakeAdministrationBean.getDoctorSignatureDate().getTime()));
				}

				if (clientIntakeAdministrationBean.getCounsellorSwName() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, clientIntakeAdministrationBean.getCounsellorSwName());
				}

				if (clientIntakeAdministrationBean.getCounsellorSignDate() == null) {
					ps.setNull(6, Types.DATE);
				} else {
					ps.setDate(6, new java.sql.Date(clientIntakeAdministrationBean.getCounsellorSignDate().getTime()));
				}

				if (clientIntakeAdministrationBean.getCoordinatorName() == null) {
					ps.setNull(7, Types.VARCHAR);
				} else {
					ps.setString(7, clientIntakeAdministrationBean.getCoordinatorName());
				}

				if (clientIntakeAdministrationBean.getCoordinatorSignDate() == null) {
					ps.setNull(8, Types.DATE);
				} else {
					ps.setDate(8, new java.sql.Date(clientIntakeAdministrationBean.getCoordinatorSignDate().getTime()));
				}

				if (clientIntakeAdministrationBean.getRemarks() == null) {
					ps.setNull(9, Types.VARCHAR);
				} else {
					ps.setString(9, clientIntakeAdministrationBean.getRemarks());
				}

				ps.setInt(10, createdBy);
				ps.setInt(11, createdBy);

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					clientIntakeAdministrationBean.setIntakeAdminId(rs.getInt(1));
					clientIntakeAdministrationBean.setClientId(clientId);
				}

				ps.clearParameters();

				/********** Image Upload ************/

				if (file != null) {

					realPath = uploadPath + "Clients/" + clientId + "/Profile_Picture";
					message = uploadservice.uploadFile(realPath, file);

					if (!message.equalsIgnoreCase("error")) {

						// ClientDocuments clientDocuments = clientDto.getClientDocuments();

						sql = "INSERT INTO mis_documents (";
						sql += " CLIENT_ID, sub_entity_id, document_type,";
						sql += " upload_path,";
						sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
						sql += " ) VALUES";
						sql += " (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

						ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

						ps.setInt(1, clientId);
						ps.setNull(2, Types.VARCHAR);
						ps.setString(3, "Profile_Picture");
						ps.setString(4, message);
						ps.setInt(5, createdBy);
						ps.setInt(6, createdBy);

						ps.executeUpdate();
						ps.clearParameters();

					} else {
						// revert
						throw new Exception("File Upload Problem.");
					}
				}

				synchronized (ClientDaoImpl.class) {

					ps.clearParameters();

					sql = "SELECT prefix, last_seq_no FROM  mis_client_uid_generator WHERE home_id = ?";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, home_id);
					rs = ps.executeQuery();

					if (rs.next()) {
						last_seq_no = rs.getInt("last_seq_no");
						prefix = rs.getString("prefix");
					}

					ps.clearParameters();

					last_seq_no = last_seq_no + 1;
					clientUid = uid_year + "/" + prefix + "/" + last_seq_no;

					sql = "UPDATE mis_client_master";
					sql += " SET CLIENT_UID = ?";
					sql += " WHERE CLIENT_ID = ?";

					ps = conn.prepareStatement(sql);

					ps.setString(1, clientUid);
					ps.setInt(2, clientId);

					ps.executeUpdate();
					ps.clearParameters();

					clientDto.getClientMasterBean().setClientUid(clientUid);

					sql = "UPDATE mis_client_uid_generator";
					sql += " SET  last_seq_no = ?";
					sql += " WHERE home_id = ?";

					ps = conn.prepareStatement(sql);

					ps.setInt(1, last_seq_no);
					ps.setInt(2, home_id);

					ps.executeUpdate();
					ps.clearParameters();
				}
			}

			conn.commit();
		} catch (Exception ex) {
			try {
				conn.rollback();
				logger.info(
						"Problem in Class - ClientDaoImpl ~~ method- saveClientIntakeDetails() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				status = ex.getMessage();
				if (realPath != null) {
					uploadservice.deleteFolder(uploadPath + "Clients/" + clientId);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	@Override
	public String updateClientIntakeDetails(ClientDto clientDto, MultipartFile file) {
		Connection conn = null;
		String status = null;
		String pic_delete_status = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int modifiedBy = 0;
		int clientId = 0;
		String message = null;
		String realPath = null;
		int document_id = 0;

		try {
			modifiedBy = clientDto.getClientMasterBean().getModifiedBy();
			clientId = clientDto.getClientMasterBean().getClientId();

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			/******************* UPDATE CLIENT MASTER ****************/

			if (clientDto.getClientMasterBean().getIsDirty()) {

				ClientMasterBean clientMasterBean = clientDto.getClientMasterBean();

				sql = "UPDATE mis_client_master";
				sql += " SET CLIENT_NAME = ?,";
				sql += " AGE = ?,";
				sql += " SEX = ?,";
				sql += " DATE_OF_ENTRY = ?,";
				sql += " TYPE_OF_ENTRY = ?,";
				sql += " COHORT_YEAR = ?,";
				sql += " FOUND_IN_AREA = ?,";
				sql += " LANDMARK = ?,";
				sql += " POLICE_STATION = ?,";
				sql += " WARD_NO = ?,";
				sql += " REFERRED_BY = ?,";
				sql += " REFERRED_DESC = ?,";
				sql += " DATE_OF_IDENTIFICATION = ?,";
				sql += " IDENTIFICATION_MARK = ?,";
				sql += " RELIGION = ?,";
				sql += " MOTHER_TONGUE = ?,";
				sql += " OTH_LANG_KNOWN = ?,";
				sql += " EDUCATION_LEVEL = ?,";
				sql += " OTH_INFORMATION = ?,";
				sql += " exit_date = ?,";
				sql += " exit_status = ?,";
				sql += " duration_of_stay = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP,";
				sql += " REFERENCE_NO = ?,";
				sql += " REFERRED_BY_IF_OTH = ?,";
				sql += " RELIGION_IF_OTH = ?,";
				sql += " EXIT_STATUS_IF_OTH = ?,";
				sql += " EXIT_REMARKS = ?";
				sql += " WHERE CLIENT_ID = ?";
				sql += " AND HOME_ID = ?";

				ps = conn.prepareStatement(sql);

				ps.setString(1, clientMasterBean.getClientName());

				if (clientMasterBean.getAge() == null) {
					ps.setNull(2, Types.INTEGER);
				} else {
					ps.setInt(2, clientMasterBean.getAge());
				}

				ps.setInt(3, clientMasterBean.getSex());
				ps.setDate(4, new java.sql.Date(clientMasterBean.getDateOfEntry().getTime()));
				ps.setInt(5, clientMasterBean.getTypeOfEntry());
				ps.setString(6, clientMasterBean.getCohortYear());

				if (clientMasterBean.getFoundInArea() == null) {
					ps.setNull(7, Types.VARCHAR);
				} else {
					ps.setString(7, clientMasterBean.getFoundInArea());
				}

				if (clientMasterBean.getLandmark() == null) {
					ps.setNull(8, Types.VARCHAR);
				} else {
					ps.setString(8, clientMasterBean.getLandmark());
				}

				if (clientMasterBean.getPoliceStation() == null) {
					ps.setNull(9, Types.VARCHAR);
				} else {
					ps.setString(9, clientMasterBean.getPoliceStation());
				}

				if (clientMasterBean.getWardNo() == null) {
					ps.setNull(10, Types.VARCHAR);
				} else {
					ps.setString(10, clientMasterBean.getWardNo());
				}

				if (clientMasterBean.getReferredBy() == null) {
					ps.setNull(11, Types.INTEGER);
				} else {
					ps.setInt(11, clientMasterBean.getReferredBy());
				}

				if (clientMasterBean.getReferredDesc() == null) {
					ps.setNull(12, Types.VARCHAR);
				} else {
					ps.setString(12, clientMasterBean.getReferredDesc());
				}

				if (clientMasterBean.getDateOfIdentification() == null) {
					ps.setNull(13, Types.DATE);
				} else {
					ps.setDate(13, new java.sql.Date(clientMasterBean.getDateOfIdentification().getTime()));
				}

				if (clientMasterBean.getIdentificationMark() == null) {
					ps.setNull(14, Types.VARCHAR);
				} else {
					ps.setString(14, clientMasterBean.getIdentificationMark());
				}

				if (clientMasterBean.getReligionId() == null) {
					ps.setNull(15, Types.INTEGER);
				} else {
					ps.setInt(15, clientMasterBean.getReligionId());
				}

				if (clientMasterBean.getMotherTongue() == null) {
					ps.setNull(16, Types.VARCHAR);
				} else {
					ps.setString(16, clientMasterBean.getMotherTongue());
				}

				if (clientMasterBean.getOthLangKnown() == null) {
					ps.setNull(17, Types.VARCHAR);
				} else {
					ps.setString(17, clientMasterBean.getOthLangKnown());
				}

				if (clientMasterBean.getEducationLevel() == null) {
					ps.setNull(18, Types.INTEGER);
				} else {
					ps.setInt(18, clientMasterBean.getEducationLevel());
				}

				if (clientMasterBean.getOthInformation() == null) {
					ps.setNull(19, Types.VARCHAR);
				} else {
					ps.setString(19, clientMasterBean.getOthInformation());
				}

				if (clientMasterBean.getExitDate() == null) {
					ps.setNull(20, Types.DATE);
				} else {
					ps.setDate(20, new java.sql.Date(clientMasterBean.getExitDate().getTime()));
				}

				if (clientMasterBean.getExitStatus() == null) {
					ps.setNull(21, Types.INTEGER);
				} else {
					ps.setInt(21, clientMasterBean.getExitStatus());
				}

				if (clientMasterBean.getDurationOfStay() == null) {
					ps.setNull(22, Types.INTEGER);
				} else {
					ps.setInt(22, clientMasterBean.getDurationOfStay());
				}

				ps.setInt(23, modifiedBy);

				if (clientMasterBean.getReferenceNo() == null) {
					ps.setNull(24, Types.VARCHAR);
				} else {
					ps.setString(24, clientMasterBean.getReferenceNo());
				}

				if (clientMasterBean.getReferredByIfOth() == null) {
					ps.setNull(25, Types.VARCHAR);
				} else {
					ps.setString(25, clientMasterBean.getReferredByIfOth());
				}

				if (clientMasterBean.getReligionIfOth() == null) {
					ps.setNull(26, Types.VARCHAR);
				} else {
					ps.setString(26, clientMasterBean.getReligionIfOth());
				}

				if (clientMasterBean.getExitStatusIfOth() == null) {
					ps.setNull(27, Types.VARCHAR);
				} else {
					ps.setString(27, clientMasterBean.getExitStatusIfOth());
				}

				if (clientMasterBean.getExitRemarks() == null) {
					ps.setNull(28, Types.VARCHAR);
				} else {
					ps.setString(28, clientMasterBean.getExitRemarks());
				}

				ps.setInt(29, clientMasterBean.getClientId());
				ps.setInt(30, clientMasterBean.getHomeId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();

			}

			/******************* UPDATE CLIENT ADDRESS TABLE ****************/

			if (clientDto.getClientAddressBean().getIsDirty()) {

				ClientAddressBean addressBean = clientDto.getClientAddressBean();

				sql = "UPDATE mis_client_address";
				sql += " SET gram_panchayat_mouza = ?,";
				sql += " para_village = ?,";
				sql += " police_station = ?,";
				sql += " post_office = ?,";
				sql += " pin_no = ?,";
				sql += " district = ?,";
				sql += " state = ?,";
				sql += " country = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
				sql += " WHERE address_id = ?";

				ps = conn.prepareStatement(sql);

				if (addressBean.getGramPanchayatMouza() == null) {
					ps.setNull(1, Types.VARCHAR);
				} else {
					ps.setString(1, addressBean.getGramPanchayatMouza());
				}

				if (addressBean.getParaVillage() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, addressBean.getParaVillage());
				}

				if (addressBean.getPoliceStation() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, addressBean.getPoliceStation());
				}

				if (addressBean.getPostOffice() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, addressBean.getPostOffice());
				}

				if (addressBean.getPinNo() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, addressBean.getPinNo());
				}

				if (addressBean.getDistrict() == null) {
					ps.setNull(6, Types.VARCHAR);
				} else {
					ps.setString(6, addressBean.getDistrict());
				}

				if (addressBean.getState() == null) {
					ps.setNull(7, Types.VARCHAR);
				} else {
					ps.setString(7, addressBean.getState());
				}

				if (addressBean.getCountry() == null) {
					ps.setNull(8, Types.VARCHAR);
				} else {
					ps.setString(8, addressBean.getCountry());
				}

				ps.setInt(9, modifiedBy);
				// ps.setInt(10, addressBean.getClientId());
				ps.setInt(10, addressBean.getAddressId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();

			}

			/******************* UPDATE CLIENT FAMILY TABLE ****************/

			if (clientDto.getClientFamilyBean().getIsDirty()) {

				ClientFamilyBean clientFamilyBean = clientDto.getClientFamilyBean();

				sql = "UPDATE mis_client_family";
				sql += " SET guardian_name = ?,";
				sql += " contact_address = ?,";
				sql += " contact1_no = ?,";
				sql += " contact2_no = ?,";
				sql += " relation_with_guardian = ?,";
				sql += " family_size = ?,";
				sql += " occupation_of_family = ?,";
				sql += " monthly_income_of_family = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP,";
				sql += " relation_if_oth = ?";
				sql += " WHERE family_id = ?";

				ps = conn.prepareStatement(sql);

				if (clientFamilyBean.getGuardianName() == null) {
					ps.setNull(1, Types.VARCHAR);
				} else {
					ps.setString(1, clientFamilyBean.getGuardianName());
				}

				if (clientFamilyBean.getContactAddress() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, clientFamilyBean.getContactAddress());
				}

				if (clientFamilyBean.getContact1No() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, clientFamilyBean.getContact1No());
				}

				if (clientFamilyBean.getContact2No() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, clientFamilyBean.getContact2No());
				}

				if (clientFamilyBean.getRelationWithGuardian() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setInt(5, clientFamilyBean.getRelationWithGuardian());
				}

				if (clientFamilyBean.getFamilySize() == null) {
					ps.setNull(6, Types.VARCHAR);
				} else {
					ps.setInt(6, clientFamilyBean.getFamilySize());
				}

				if (clientFamilyBean.getOccupationOfFamily() == null) {
					ps.setNull(7, Types.VARCHAR);
				} else {
					ps.setString(7, clientFamilyBean.getOccupationOfFamily());
				}

				if (clientFamilyBean.getMonthlyIncomeOfFamily() == null) {
					ps.setNull(8, Types.DECIMAL);
				} else {
					ps.setDouble(8, clientFamilyBean.getMonthlyIncomeOfFamily());
				}

				ps.setInt(9, modifiedBy);

				if (clientFamilyBean.getRelationIfOth() == null) {
					ps.setNull(10, Types.VARCHAR);
				} else {
					ps.setString(10, clientFamilyBean.getRelationIfOth());
				}
				// ps.setInt(11, clientFamilyBean.getClientId());
				ps.setInt(11, clientFamilyBean.getFamilyId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();

			}

			/******************* UPDATE CLIENT INIT MEDICAL DET TABLE ****************/

			if (clientDto.getClientInitMedicalDetBean().getIsDirty()) {

				ClientInitMedicalDetBean clientInitMedicalDetBean = clientDto.getClientInitMedicalDetBean();

				sql = "UPDATE mis_client_init_medical_det";
				sql += " SET provisional_diagnosis = ?,";
				sql += " diagnosis_group = ?,";
				sql += " comorbidity = ?,";
				sql += " oth_medical_condition_found = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP,";			
				sql += " diagnosis_group_if_oth = ?";
				sql += " WHERE init_medical_det_id = ?";

				ps = conn.prepareStatement(sql);

				if (clientInitMedicalDetBean.getProvisionalDiagnosis() == null) {
					ps.setNull(1, Types.VARCHAR);
				} else {
					ps.setString(1, clientInitMedicalDetBean.getProvisionalDiagnosis());
				}

				if (clientInitMedicalDetBean.getDiagnosisGroup() == null) {
					ps.setNull(2, Types.INTEGER);
				} else {
					ps.setInt(2, clientInitMedicalDetBean.getDiagnosisGroup());
				}

				if (clientInitMedicalDetBean.getComorbidity() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, clientInitMedicalDetBean.getComorbidity());
				}

				if (clientInitMedicalDetBean.getOthMedicalConditionFound() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, clientInitMedicalDetBean.getOthMedicalConditionFound());
				}

				ps.setInt(5, modifiedBy);

				if (clientInitMedicalDetBean.getDiagnosisGroupIfOth() == null) {
					ps.setNull(6, Types.VARCHAR);
				} else {
					ps.setString(6, clientInitMedicalDetBean.getDiagnosisGroupIfOth());
				}

				// ps.setInt(7, clientInitMedicalDetBean.getClientId());
				ps.setInt(7, clientInitMedicalDetBean.getInitMedicalDetId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();

			}

			/******************* UPDATE CLIENT BMI TABLE ****************/

			if (clientDto.getClientBMIBean().getIsDirty()) {

				ClientBMIBean bmiBean = clientDto.getClientBMIBean();

				sql = "UPDATE mis_client_bmi";
				sql += " SET weight = ?,";
				sql += " weight_unit = ?,";
				sql += " height = ?,";
				sql += " height_unit = ?,";
				sql += " bmi = ?,";
				sql += " bmi_class = ?,";
				sql += " bp = ?,";
				sql += " date_of_entry = ?,";
				sql += " cohort_year = ?,";
				sql += " qtr_of_year = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
				sql += " WHERE bmi_id = ?";

				ps = conn.prepareStatement(sql);

				if (bmiBean.getWeight() == null) {
					ps.setNull(1, Types.DOUBLE);
				} else {
					ps.setDouble(1, bmiBean.getWeight());
				}

				if (bmiBean.getWeightUnit() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, bmiBean.getWeightUnit());
				}

				if (bmiBean.getHeight() == null) {
					ps.setNull(3, Types.DOUBLE);
				} else {
					ps.setDouble(3, bmiBean.getHeight());
				}

				if (bmiBean.getHeightUnit() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, bmiBean.getHeightUnit());
				}

				if (bmiBean.getBmi() == null) {
					ps.setNull(5, Types.DOUBLE);
				} else {
					ps.setDouble(5, bmiBean.getBmi());
				}

				if (bmiBean.getBmiClass() == null) {
					ps.setNull(6, Types.INTEGER);
				} else {
					ps.setInt(6, bmiBean.getBmiClass());
				}

				if (bmiBean.getBp() == null) {
					ps.setNull(7, Types.VARCHAR);
				} else {
					ps.setString(7, bmiBean.getBp());
				}

				if (bmiBean.getDateOfEntry() == null) {
					ps.setNull(8, Types.DATE);
				} else {
					ps.setDate(8, new java.sql.Date(bmiBean.getDateOfEntry().getTime()));
					// java.sql.Date date = java.sql.Date.valueOf(bmiBean.getDateOfEntry());
					// ps.setDate(8, date);
				}

				// ps.setString(9, bmiBean.getCohortYear());
				// ps.setInt(10, bmiBean.getQtrOfYear());

				if (bmiBean.getCohortYear() == null) {
					ps.setNull(9, Types.VARCHAR);
				} else {
					ps.setString(9, bmiBean.getCohortYear());
				}

				if (bmiBean.getQtrOfYear() == null) {
					ps.setNull(10, Types.INTEGER);
				} else {
					ps.setInt(10, bmiBean.getQtrOfYear());
				}
				ps.setInt(11, modifiedBy);
				ps.setInt(12, bmiBean.getBmiId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();
			}

			/******************* UPDATE CLIENT Psycho Gaf TABLE ****************/

			if (clientDto.getClientPsychoGafBean().getIsDirty()) {

				ClientPsychoGafBean clientPsychoGafBean = clientDto.getClientPsychoGafBean();

				sql = "UPDATE mis_client_psycho_gaf";
				sql += " SET gaf_score = ?,";
				sql += " gaf_class = ?,";
				sql += " date_of_entry = ?,";
				sql += " cohort_year = ?,";
				sql += " qtr_of_year = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
				sql += " WHERE psycho_gaf_id = ?";

				ps = conn.prepareStatement(sql);

				if (clientPsychoGafBean.getGafScore() == null) {
					ps.setNull(1, Types.INTEGER);
				} else {
					ps.setInt(1, clientPsychoGafBean.getGafScore());
				}

				if (clientPsychoGafBean.getGafClass() == null) {
					ps.setNull(2, Types.INTEGER);
				} else {
					ps.setInt(2, clientPsychoGafBean.getGafClass());
				}

				if (clientPsychoGafBean.getDateOfEntry() == null) {
					ps.setNull(3, Types.DATE);
				} else {
					ps.setDate(3, new java.sql.Date(clientPsychoGafBean.getDateOfEntry().getTime()));
				}

				// ps.setString(4, clientPsychoGafBean.getCohortYear());
				// ps.setInt(5, clientPsychoGafBean.getQtrOfYear());

				if (clientPsychoGafBean.getCohortYear() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, clientPsychoGafBean.getCohortYear());
				}

				if (clientPsychoGafBean.getQtrOfYear() == null) {
					ps.setNull(5, Types.INTEGER);
				} else {
					ps.setInt(5, clientPsychoGafBean.getQtrOfYear());
				}
				ps.setInt(6, modifiedBy);
				ps.setInt(7, clientPsychoGafBean.getPsychoGafId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();
			}

			/******************* UPDATE CLIENT Psycho IDEAS TABLE ****************/

			if (clientDto.getClientPsychoIdeasBean().getIsDirty()) {

				ClientPsychoIdeasBean clientPsychoIdeasBean = clientDto.getClientPsychoIdeasBean();

				sql = "UPDATE mis_client_psycho_ideas";
				sql += " SET sc = ?,";
				sql += " ia = ?,";
				sql += " c_and_u = ?,";
				sql += " wrk = ?,";
				sql += " doi = ?,";
				sql += " gds = ?,";
				sql += " gis = ?,";
				sql += " ideas_status = ?,";
				sql += " date_of_entry = ?,";
				sql += " cohort_year = ?,";
				sql += " qtr_of_year = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
				sql += " WHERE psycho_ideas_id = ?";

				ps = conn.prepareStatement(sql);

				if (clientPsychoIdeasBean.getSc() == null) {
					ps.setNull(1, Types.INTEGER);
				} else {
					ps.setInt(1, clientPsychoIdeasBean.getSc());
				}

				if (clientPsychoIdeasBean.getIa() == null) {
					ps.setNull(2, Types.INTEGER);
				} else {
					ps.setInt(2, clientPsychoIdeasBean.getIa());
				}

				if (clientPsychoIdeasBean.getC_and_u() == null) {
					ps.setNull(3, Types.INTEGER);
				} else {
					ps.setInt(3, clientPsychoIdeasBean.getC_and_u());
				}

				if (clientPsychoIdeasBean.getWrk() == null) {
					ps.setNull(4, Types.INTEGER);
				} else {
					ps.setInt(4, clientPsychoIdeasBean.getWrk());
				}

				if (clientPsychoIdeasBean.getDoi() == null) {
					ps.setNull(5, Types.INTEGER);
				} else {
					ps.setInt(5, clientPsychoIdeasBean.getDoi());
				}

				if (clientPsychoIdeasBean.getGis() == null) {
					ps.setNull(6, Types.INTEGER);
				} else {
					ps.setInt(6, clientPsychoIdeasBean.getGis());
				}

				if (clientPsychoIdeasBean.getGis() == null) {
					ps.setNull(7, Types.INTEGER);
				} else {
					ps.setInt(7, clientPsychoIdeasBean.getGis());
				}

				if (clientPsychoIdeasBean.getIdeasStatus() == null) {
					ps.setNull(8, Types.VARCHAR);
				} else {
					ps.setString(8, clientPsychoIdeasBean.getIdeasStatus());
				}

				if (clientPsychoIdeasBean.getDateOfEntry() == null) {
					ps.setNull(9, Types.DATE);
				} else {
					ps.setDate(9, new java.sql.Date(clientPsychoIdeasBean.getDateOfEntry().getTime()));
				}

				// ps.setString(10, clientPsychoIdeasBean.getCohortYear());
				// ps.setInt(11, clientPsychoIdeasBean.getQtrOfYear());

				if (clientPsychoIdeasBean.getCohortYear() == null) {
					ps.setNull(10, Types.VARCHAR);
				} else {
					ps.setString(10, clientPsychoIdeasBean.getCohortYear());
				}

				if (clientPsychoIdeasBean.getQtrOfYear() == null) {
					ps.setNull(11, Types.INTEGER);
				} else {
					ps.setInt(11, clientPsychoIdeasBean.getQtrOfYear());
				}
				ps.setInt(12, modifiedBy);
				ps.setInt(13, clientPsychoIdeasBean.getPsychoIdeasId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();
			}

			/******************* UPDATE CLIENT Psycho LSP TABLE ****************/

			if (clientDto.getClientPsychoLspBean().getIsDirty()) {

				ClientPsychoLspBean clientPsychoLspBean = clientDto.getClientPsychoLspBean();

				sql = "UPDATE mis_client_psycho_lsp";
				sql += " SET lsp_score = ?,";
				sql += " lsp_class = ?,";
				sql += " date_of_entry = ?,";
				sql += " cohort_year = ?,";
				sql += " qtr_of_year = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
				sql += " WHERE psycho_lsp_id = ?";

				ps = conn.prepareStatement(sql);

				if (clientPsychoLspBean.getLspScore() == null) {
					ps.setNull(1, Types.INTEGER);
				} else {
					ps.setInt(1, clientPsychoLspBean.getLspScore());
				}

				if (clientPsychoLspBean.getLspClass() == null) {
					ps.setNull(2, Types.INTEGER);
				} else {
					ps.setInt(2, clientPsychoLspBean.getLspClass());
				}

				if (clientPsychoLspBean.getDateOfEntry() == null) {
					ps.setNull(3, Types.DATE);
				} else {
					ps.setDate(3, new java.sql.Date(clientPsychoLspBean.getDateOfEntry().getTime()));
				}

				// ps.setString(4, clientPsychoLspBean.getCohortYear());
				// ps.setInt(5, clientPsychoLspBean.getQtrOfYear());

				if (clientPsychoLspBean.getCohortYear() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, clientPsychoLspBean.getCohortYear());
				}

				if (clientPsychoLspBean.getQtrOfYear() == null) {
					ps.setNull(5, Types.INTEGER);
				} else {
					ps.setInt(5, clientPsychoLspBean.getQtrOfYear());
				}

				ps.setInt(6, modifiedBy);
				ps.setInt(7, clientPsychoLspBean.getPsychoLspId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();
			}

			/******************* UPDATE CLIENT Psycho PANSS TABLE ****************/

			ClientPsychoPanssBean clientPsychoPanssBean = clientDto.getClientPsychoPanss();

			if (clientDto.getClientPsychoPanss().getIsDirty()) {

				sql = "UPDATE mis_client_psycho_panss";
				sql += " SET ps = ?,";
				sql += " ns = ?,";
				sql += " gp = ?,";
				sql += " total = ?,";
				sql += " date_of_entry = ?,";
				sql += " cohort_year = ?,";
				sql += " qtr_of_year = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
				sql += " WHERE psycho_panss_id = ?";

				ps = conn.prepareStatement(sql);

				if (clientPsychoPanssBean.getPs() == null) {
					ps.setNull(1, Types.INTEGER);
				} else {
					ps.setInt(1, clientPsychoPanssBean.getPs());
				}

				if (clientPsychoPanssBean.getNs() == null) {
					ps.setNull(2, Types.INTEGER);
				} else {
					ps.setInt(2, clientPsychoPanssBean.getNs());
				}

				if (clientPsychoPanssBean.getGp() == null) {
					ps.setNull(3, Types.INTEGER);
				} else {
					ps.setInt(3, clientPsychoPanssBean.getGp());
				}

				if (clientPsychoPanssBean.getTotal() == null) {
					ps.setNull(4, Types.INTEGER);
				} else {
					ps.setInt(4, clientPsychoPanssBean.getTotal());
				}

				if (clientPsychoPanssBean.getDateOfEntry() == null) {
					ps.setNull(5, Types.DATE);
				} else {
					ps.setDate(5, new java.sql.Date(clientPsychoPanssBean.getDateOfEntry().getTime()));
				}

				// ps.setString(6, clientPsychoPanssBean.getCohortYear());
				// ps.setInt(7, clientPsychoPanssBean.getQtrOfYear());

				if (clientPsychoPanssBean.getCohortYear() == null) {
					ps.setNull(6, Types.VARCHAR);
				} else {
					ps.setString(6, clientPsychoPanssBean.getCohortYear());
				}

				if (clientPsychoPanssBean.getQtrOfYear() == null) {
					ps.setNull(7, Types.INTEGER);
				} else {
					ps.setInt(7, clientPsychoPanssBean.getQtrOfYear());
				}

				ps.setInt(8, modifiedBy);
				ps.setInt(9, clientPsychoPanssBean.getPsychoPanssId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();
			}

			/******************* UPDATE CLIENT THYROID SIGNIFICANTS TABLE ****************/

			if (clientDto.getClientThyroidSignificanceBean().getIsDirty()) {

				ClientThyroidSignificanceBean clientThyroidSignificanceBean = clientDto
						.getClientThyroidSignificanceBean();

				sql = "UPDATE mis_client_thyroid_significance";
				sql += " SET thyro_tsh = ?,";
				sql += " thyro_T3 = ?,";
				sql += " thyro_T4 = ?,";
				sql += " date_of_entry = ?,";
				sql += " cohort_year = ?,";
				sql += " qtr_of_year = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
				sql += " WHERE thyroid_signf_id = ?";

				ps = conn.prepareStatement(sql);

				if (clientThyroidSignificanceBean.getThyroTsh() == null) {
					ps.setNull(1, Types.VARCHAR);
				} else {
					ps.setString(1, clientThyroidSignificanceBean.getThyroTsh());
				}

				if (clientThyroidSignificanceBean.getThyroT3() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, clientThyroidSignificanceBean.getThyroT3());
				}

				if (clientThyroidSignificanceBean.getThyroT4() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, clientThyroidSignificanceBean.getThyroT4());
				}

				if (clientThyroidSignificanceBean.getDateOfEntry() == null) {
					ps.setNull(4, Types.DATE);
				} else {
					ps.setDate(4, new java.sql.Date(clientThyroidSignificanceBean.getDateOfEntry().getTime()));
				}

				// ps.setString(5, clientThyroidSignificanceBean.getCohortYear());
				// ps.setInt(6, clientThyroidSignificanceBean.getQtrOfYear());

				if (clientThyroidSignificanceBean.getCohortYear() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, clientThyroidSignificanceBean.getCohortYear());
				}

				if (clientThyroidSignificanceBean.getQtrOfYear() == null) {
					ps.setNull(6, Types.INTEGER);
				} else {
					ps.setInt(6, clientThyroidSignificanceBean.getQtrOfYear());
				}

				ps.setInt(7, modifiedBy);
				ps.setInt(8, clientThyroidSignificanceBean.getThyroidSignfId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();
			}

			/******************* UPDATE CLIENT DIABETES SIGNIFICANTS TABLE ****************/

			if (clientDto.getClientDiabetesSignificanceBean().getIsDirty()) {

				ClientDiabetesSignificanceBean clientDiabetesSignificanceBean = clientDto
						.getClientDiabetesSignificanceBean();

				sql = "UPDATE mis_client_diabetes_significance";
				sql += " SET diabetes_rbs = ?,";
				sql += " diabetes_fbs = ?,";
				sql += " diabetes_ppbs = ?,";
				sql += " date_of_entry = ?,";
				sql += " cohort_year = ?,";
				sql += " qtr_of_year = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
				sql += " WHERE diabetes_signf_id = ?";

				ps = conn.prepareStatement(sql);

				if (clientDiabetesSignificanceBean.getDiabetesRBS() == null) {
					ps.setNull(1, Types.VARCHAR);
				} else {
					ps.setString(1, clientDiabetesSignificanceBean.getDiabetesRBS());
				}

				if (clientDiabetesSignificanceBean.getDiabetesFBS() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, clientDiabetesSignificanceBean.getDiabetesFBS());
				}

				if (clientDiabetesSignificanceBean.getDiabetesPPBS() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, clientDiabetesSignificanceBean.getDiabetesPPBS());
				}

				if (clientDiabetesSignificanceBean.getDateOfEntry() == null) {
					ps.setNull(4, Types.DATE);
				} else {
					ps.setDate(4, new java.sql.Date(clientDiabetesSignificanceBean.getDateOfEntry().getTime()));
				}

				// ps.setString(5, clientDiabetesSignificanceBean.getCohortYear());
				// ps.setInt(6, clientDiabetesSignificanceBean.getQtrOfYear());

				if (clientDiabetesSignificanceBean.getCohortYear() == null) {
					ps.setNull(5, Types.VARCHAR);
				} else {
					ps.setString(5, clientDiabetesSignificanceBean.getCohortYear());
				}

				if (clientDiabetesSignificanceBean.getQtrOfYear() == null) {
					ps.setNull(6, Types.INTEGER);
				} else {
					ps.setInt(6, clientDiabetesSignificanceBean.getQtrOfYear());
				}

				ps.setInt(7, modifiedBy);
				ps.setInt(8, clientDiabetesSignificanceBean.getDiabetesSignfId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();
			}

			/************* UPDATE CLIENT HEMOGLOBIN SIGNIFICANCE TABLE ****************/

			if (clientDto.getClientHemoglobinSignificanceBean().getIsDirty()) {

				ClientHemoglobinSignificanceBean clientHemoglobinSignificanceBean = clientDto
						.getClientHemoglobinSignificanceBean();

				sql = "UPDATE mis_client_hemoglobin_significance";
				sql += " SET hb_percent = ?,";
				sql += " esr = ?,";
				sql += " hemoglobin_level = ?,";
				sql += " oth_lab_test = ?,";
				sql += " oth_date_of_entry = ?,";
				sql += " cohort_year = ?,";
				sql += " qtr_of_year = ?,";
				sql += " date_of_entry = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
				sql += " WHERE hemoglobin_signf_id = ?";

				ps = conn.prepareStatement(sql);

				if (clientHemoglobinSignificanceBean.getHbPercent() == null) {
					ps.setNull(1, Types.VARCHAR);
				} else {
					ps.setString(1, clientHemoglobinSignificanceBean.getHbPercent());
				}

				if (clientHemoglobinSignificanceBean.getEsr() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, clientHemoglobinSignificanceBean.getEsr());
				}

				if (clientHemoglobinSignificanceBean.getHemoglobinLevel() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, clientHemoglobinSignificanceBean.getHemoglobinLevel());
				}

				if (clientHemoglobinSignificanceBean.getOthLabTest() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, clientHemoglobinSignificanceBean.getOthLabTest());
				}

				if (clientHemoglobinSignificanceBean.getOthDateOfEntry() == null) {
					ps.setNull(5, Types.DATE);
				} else {
					ps.setDate(5, new java.sql.Date(clientHemoglobinSignificanceBean.getOthDateOfEntry().getTime()));
				}

				// ps.setString(6, clientHemoglobinSignificanceBean.getCohortYear());
				// ps.setInt(7, clientHemoglobinSignificanceBean.getQtrOfYear());

				if (clientHemoglobinSignificanceBean.getCohortYear() == null) {
					ps.setNull(6, Types.VARCHAR);
				} else {
					ps.setString(6, clientHemoglobinSignificanceBean.getCohortYear());
				}

				if (clientHemoglobinSignificanceBean.getQtrOfYear() == null) {
					ps.setNull(7, Types.INTEGER);
				} else {
					ps.setInt(7, clientHemoglobinSignificanceBean.getQtrOfYear());
				}

				if (clientHemoglobinSignificanceBean.getDateOfEntry() == null) {
					ps.setNull(8, Types.DATE);
				} else {
					ps.setDate(8, new java.sql.Date(clientHemoglobinSignificanceBean.getDateOfEntry().getTime()));
				}

				ps.setInt(9, modifiedBy);				
				ps.setInt(10, clientHemoglobinSignificanceBean.getHemoglobinSignfId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();
			}

			/******************* UPDATE CLIENT INTAKE ADMINISTRATION TABLE ****************/

			if (clientDto.getClientIntakeAdministrationBean().getIsDirty()) {

				ClientIntakeAdministrationBean clientIntakeAdministrationBean = clientDto
						.getClientIntakeAdministrationBean();

				sql = "UPDATE mis_client_intake_administration";
				sql += " SET caregiver_name = ?,";
				sql += " doctor_signature = ?,";
				sql += " doctor_signature_date = ?,";
				sql += " counsellor_sw_name = ?,";
				sql += " counsellor_sign_date = ?,";
				sql += " coordinator_name = ?,";
				sql += " coordinator_sign_date = ?,";
				sql += " remarks = ?,";
				sql += " modified_by = ?,";
				sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
				sql += " WHERE intake_admin_id = ?";

				ps = conn.prepareStatement(sql);

				if (clientIntakeAdministrationBean.getCaregiverName() == null) {
					ps.setNull(1, Types.VARCHAR);
				} else {
					ps.setString(1, clientIntakeAdministrationBean.getCaregiverName());
				}

				if (clientIntakeAdministrationBean.getDoctorSignature() == null) {
					ps.setNull(2, Types.VARCHAR);
				} else {
					ps.setString(2, clientIntakeAdministrationBean.getDoctorSignature());
				}

				if (clientIntakeAdministrationBean.getDoctorSignatureDate() == null) {
					ps.setNull(3, Types.DATE);
				} else {
					ps.setDate(3, new java.sql.Date(clientIntakeAdministrationBean.getDoctorSignatureDate().getTime()));
				}

				if (clientIntakeAdministrationBean.getCounsellorSwName() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, clientIntakeAdministrationBean.getCounsellorSwName());
				}

				if (clientIntakeAdministrationBean.getCounsellorSignDate() == null) {
					ps.setNull(5, Types.DATE);
				} else {
					ps.setDate(5, new java.sql.Date(clientIntakeAdministrationBean.getCounsellorSignDate().getTime()));
				}

				if (clientIntakeAdministrationBean.getCoordinatorName() == null) {
					ps.setNull(6, Types.VARCHAR);
				} else {
					ps.setString(6, clientIntakeAdministrationBean.getCoordinatorName());
				}

				if (clientIntakeAdministrationBean.getCoordinatorSignDate() == null) {
					ps.setNull(7, Types.DATE);
				} else {
					ps.setDate(7, new java.sql.Date(clientIntakeAdministrationBean.getCoordinatorSignDate().getTime()));
				}

				if (clientIntakeAdministrationBean.getRemarks() == null) {
					ps.setNull(8, Types.VARCHAR);
				} else {
					ps.setString(8, clientIntakeAdministrationBean.getRemarks());
				}

				ps.setInt(9, modifiedBy);
				// ps.setInt(10, clientIntakeAdministrationBean.getClientId());
				ps.setInt(10, clientIntakeAdministrationBean.getIntakeAdminId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();
			}

			/************************************
			 * Image Upload
			 *******************************************/

			if (file != null) {

				if (clientDto.getClientDocuments() != null) {

					// Change Profile_Picture.

					ClientDocuments clientDocuments = clientDto.getClientDocuments();

					if (clientDocuments.getIsDirty()) {

						sql = "SELECT document_id, upload_path FROM mis_documents";
						sql += " WHERE client_id = ? AND document_type = ?";

						ps = conn.prepareStatement(sql);
						ps.setInt(1, clientId);
						ps.setString(2, "Profile_Picture");
						rs = ps.executeQuery();

						if (rs.next()) {
							document_id = rs.getInt("document_id");
							realPath = rs.getString("upload_path");
						}

						ps.clearBatch();
						ps.clearParameters();

						realPath = StringUtils.substringBeforeLast(realPath.trim(), "/");

						pic_delete_status = uploadservice.deleteFiles(realPath, "Profile_Picture");

						if (!pic_delete_status.equals("error")) {

							message = uploadservice.uploadFile(realPath, file);

							if (message.equals("error")) {
								// revert
								throw new Exception("File Upload Problem.");
							} else {
								// update clientDocument
								sql = "UPDATE mis_documents";
								sql += " SET upload_path = ?,";
								sql += " is_updated = ?,";
								sql += " MODIFIED_BY = ?,";
								sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
								sql += " WHERE";
								sql += " document_id = ?";

								ps = conn.prepareStatement(sql);

								ps.setString(1, message);
								ps.setString(2, "Y");
								ps.setInt(3, modifiedBy);
								// ps.setInt(4, clientId);
								ps.setInt(4, document_id);

								ps.executeUpdate();
								ps.clearBatch();
								ps.clearParameters();
							}
						} else {
							// revert
							throw new Exception("File Delete Problem.");
						}
					}

				} else {
					// Upload Profile_Picture first time.
					realPath = uploadPath + "Clients/" + clientId + "/Profile_Picture";
					message = uploadservice.uploadFile(realPath, file);

					if (!message.equalsIgnoreCase("error")) {

						sql = "INSERT INTO mis_documents (";
						sql += " CLIENT_ID, sub_entity_id, document_type,";
						sql += " upload_path,";
						sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
						sql += " ) VALUES";
						sql += " (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

						ps = conn.prepareStatement(sql);

						ps.setInt(1, clientId);
						ps.setNull(2, Types.VARCHAR);
						ps.setString(3, "Profile_Picture");
						ps.setString(4, message);
						ps.setInt(5, modifiedBy);
						ps.setInt(6, modifiedBy);

						ps.executeUpdate();
						ps.clearBatch();
						ps.clearParameters();

					} else {
						// revert
						throw new Exception("File Upload Problem.");
					}
				}
			}

			conn.commit();
		} catch (Exception ex) {
			try {
				logger.info(
						"Problem in Class - ClientDaoImpl ~~ method- updateClientIntakeDetails() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				status = ex.getMessage();
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	@Override
	public List<ClientMasterBean> getClientUidList(String clientUid, String homeId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ClientMasterBean> clientUidList = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			clientUidList = new ArrayList<ClientMasterBean>();

			sql = "SELECT CLIENT_NAME, CLIENT_ID, HOME_ID, CLIENT_UID, DATE_OF_ENTRY, exit_date FROM mis_client_master";
			sql += " WHERE HOME_ID = ? AND IS_DELETED = 'N'";
			if (clientUid != null && !clientUid.equalsIgnoreCase("")) {
				sql += " AND CLIENT_UID LIKE ?";
			}
			sql += " ORDER BY CLIENT_ID DESC";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(homeId));

			if (clientUid != null && !clientUid.equalsIgnoreCase("")) {
				ps.setString(2, clientUid + '%');
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				ClientMasterBean masterBean = new ClientMasterBean();
				masterBean.setClientName(rs.getString("CLIENT_NAME"));
				masterBean.setClientId(rs.getInt("CLIENT_ID"));
				masterBean.setHomeId(rs.getInt("HOME_ID"));
				masterBean.setClientUid(rs.getString("CLIENT_UID"));
				masterBean.setDateOfEntry(rs.getDate("DATE_OF_ENTRY"));
				masterBean.setExitDate(rs.getDate("exit_date"));
				clientUidList.add(masterBean);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				clientUidList = null;
				logger.info("Problem in Class - ClientDaoImpl ~~ method- getClientUidList() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return clientUidList;
	}

	@Override
	public List<ClientMasterBean> getClientBasicInfoList(String clientId, String cohortYr, String homeId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ClientMasterBean> clientUidList = null;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			clientUidList = new ArrayList<ClientMasterBean>();

			sql = " SELECT CLIENT_ID, CLIENT_NAME, HOME_ID, CLIENT_UID, AGE, SEX, DATE_OF_ENTRY, COHORT_YEAR, POLICE_STATION, IS_MSE,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = cm.SEX) sexStr";
			sql += " FROM mis_client_master cm";
			sql += " WHERE HOME_ID = ? AND IS_DELETED = 'N'";

			if (clientId != null && !clientId.equalsIgnoreCase("")) {
				sql += " AND CLIENT_ID = ?";
			}

			if (cohortYr != null && !cohortYr.equalsIgnoreCase("")) {
				sql += " AND COHORT_YEAR = ?";
			}

			sql += " ORDER BY CLIENT_ID DESC";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(homeId));

			if (clientId != null && !clientId.equalsIgnoreCase("")) {
				ps.setString(2, clientId);
			}

			if (cohortYr != null && !cohortYr.equalsIgnoreCase("")) {
				if (clientId != null && !clientId.equalsIgnoreCase("")) {
					ps.setString(3, cohortYr);
				} else {
					ps.setString(2, cohortYr);
				}
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				ClientMasterBean masterBean = new ClientMasterBean();
				masterBean.setClientName(rs.getString("CLIENT_NAME"));
				masterBean.setClientId(rs.getInt("CLIENT_ID"));
				masterBean.setHomeId(rs.getInt("HOME_ID"));
				masterBean.setClientUid(rs.getString("CLIENT_UID"));
				masterBean.setCohortYear(rs.getString("COHORT_YEAR"));
				masterBean.setAge(rs.getInt("AGE"));
				masterBean.setSex(rs.getInt("SEX"));
				masterBean.setDateOfEntry(rs.getDate("DATE_OF_ENTRY"));
				masterBean.setPoliceStation(rs.getString("POLICE_STATION"));
				masterBean.setSexStr(rs.getString("sexStr"));
				masterBean.setIsMse(rs.getString("IS_MSE"));

				clientUidList.add(masterBean);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				clientUidList = null;
				logger.info(
						"Problem in Class - ClientDaoImpl ~~ method- getClientBasicInfoList() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return clientUidList;
	}

	@Override
	public List<String> getCohortYrList(String homeId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> cohortYrList = null;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			cohortYrList = new ArrayList<String>();

			sql = " SELECT DISTINCT COHORT_YEAR FROM mis_client_master";
			sql += " WHERE HOME_ID = ? ORDER BY COHORT_YEAR";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(homeId));
			rs = ps.executeQuery();
			while (rs.next()) {
				cohortYrList.add(rs.getString("COHORT_YEAR"));
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				cohortYrList = null;
				logger.info("Problem in Class - ClientDaoImpl ~~ method- getCohortYrList() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cohortYrList;
	}

	@Override
	public ClientDto getClientIntakeDetails(String clientId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer _clientId = Integer.parseInt(clientId);
		ClientDto clientDto = null;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			clientDto = new ClientDto();

			sql = "SELECT cm.CLIENT_ID, cm.HOME_ID, cm.CLIENT_UID, cm.CLIENT_NAME, cm.AGE,";
			sql += " cm.SEX, cm.DATE_OF_ENTRY, cm.TYPE_OF_ENTRY, cm.COHORT_YEAR, cm.FOUND_IN_AREA,";
			sql += " cm.LANDMARK, cm.POLICE_STATION, cm.WARD_NO, cm.REFERRED_BY,";
			sql += " cm.REFERRED_DESC, cm.REFERENCE_NO, cm.DATE_OF_IDENTIFICATION, cm.IDENTIFICATION_MARK,";
			sql += " cm.RELIGION, cm.MOTHER_TONGUE, cm.OTH_LANG_KNOWN, cm.EDUCATION_LEVEL,";
			sql += " cm.OTH_INFORMATION, cm.exit_date, cm.exit_status, cm.duration_of_stay,";
			sql += " cm.REFERRED_BY_IF_OTH, cm.RELIGION_IF_OTH, cm.EXIT_STATUS_IF_OTH, cm.EXIT_REMARKS,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = cm.SEX) sexStr,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = cm.TYPE_OF_ENTRY) typeOfEntryStr,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = cm.REFERRED_BY) refferedByStr,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = cm.RELIGION) religionStr,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = cm.EDUCATION_LEVEL) educationLvlStr,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = cm.exit_status) exitStatusStr,";
			sql += " clt_addrs.address_id, clt_addrs.gram_panchayat_mouza, clt_addrs.para_village,";
			sql += " clt_addrs.police_station as adds_police_station, clt_addrs.post_office, clt_addrs.pin_no,";
			sql += " clt_addrs.district, clt_addrs.state, clt_addrs.country,";
			sql += " clt_fmly.family_id, clt_fmly.guardian_name, clt_fmly.contact_address,";
			sql += " clt_fmly.contact1_no, clt_fmly.contact2_no, clt_fmly.relation_with_guardian, clt_fmly.family_size,";
			sql += " clt_fmly.occupation_of_family, clt_fmly.monthly_income_of_family, clt_fmly.relation_if_oth,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = clt_fmly.relation_with_guardian) relationWithGuardianStr";
			sql += " FROM mis_client_master cm, mis_client_address clt_addrs,";
			sql += " mis_client_family clt_fmly";
			sql += " WHERE cm.CLIENT_ID = clt_addrs.CLIENT_ID";
			sql += " AND cm.CLIENT_ID = clt_fmly.CLIENT_ID";
			sql += " AND cm.CLIENT_ID = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, _clientId);
			rs = ps.executeQuery();

			if (rs.next()) {

				ClientMasterBean clientMasterBean = new ClientMasterBean();
				clientMasterBean.setClientId(rs.getInt("CLIENT_ID"));
				clientMasterBean.setHomeId(rs.getInt("HOME_ID"));
				clientMasterBean.setClientUid(rs.getString("CLIENT_UID"));
				clientMasterBean.setClientName(rs.getString("CLIENT_NAME"));
				clientMasterBean.setAge(rs.getString("AGE") != null ? Integer.parseInt(rs.getString("AGE")) : null);
				clientMasterBean.setSex(rs.getInt("SEX"));
				clientMasterBean.setSexStr(rs.getString("sexStr"));
				clientMasterBean.setDateOfEntry(rs.getDate("DATE_OF_ENTRY"));
				clientMasterBean.setTypeOfEntry(rs.getInt("TYPE_OF_ENTRY"));
				clientMasterBean.setTypeOfEntryStr(rs.getString("typeOfEntryStr"));
				clientMasterBean.setCohortYear(rs.getString("COHORT_YEAR"));
				clientMasterBean.setFoundInArea(rs.getString("FOUND_IN_AREA"));
				clientMasterBean.setLandmark(rs.getString("LANDMARK"));
				clientMasterBean.setPoliceStation(rs.getString("POLICE_STATION"));
				clientMasterBean.setWardNo(rs.getString("WARD_NO"));
				clientMasterBean.setReferredBy(rs.getInt("REFERRED_BY"));
				clientMasterBean.setReferredByStr(rs.getString("refferedByStr"));
				clientMasterBean.setReferredDesc(rs.getString("REFERRED_DESC"));
				clientMasterBean.setReferenceNo(rs.getString("REFERENCE_NO"));
				clientMasterBean.setDateOfIdentification(rs.getDate("DATE_OF_IDENTIFICATION"));
				clientMasterBean.setIdentificationMark(rs.getString("IDENTIFICATION_MARK"));
				clientMasterBean.setReligionId(rs.getInt("RELIGION"));
				clientMasterBean.setReligionIdStr(rs.getString("religionStr"));
				clientMasterBean.setMotherTongue(rs.getString("MOTHER_TONGUE"));
				clientMasterBean.setOthLangKnown(rs.getString("OTH_LANG_KNOWN"));
				clientMasterBean.setEducationLevel(rs.getInt("EDUCATION_LEVEL"));
				clientMasterBean.setEducationLevelStr(rs.getString("educationLvlStr"));
				clientMasterBean.setOthInformation(rs.getString("OTH_INFORMATION"));
				clientMasterBean.setExitDate(rs.getDate("exit_date"));
				clientMasterBean.setExitStatus(rs.getInt("exit_status"));
				clientMasterBean.setExitStatusStr(rs.getString("exitStatusStr"));
				clientMasterBean.setDurationOfStay(
						rs.getString("duration_of_stay") != null ? Integer.parseInt(rs.getString("duration_of_stay"))
								: null);
				clientMasterBean.setReferredByIfOth(rs.getString("REFERRED_BY_IF_OTH"));
				clientMasterBean.setReligionIfOth(rs.getString("RELIGION_IF_OTH"));
				clientMasterBean.setExitStatusIfOth(rs.getString("EXIT_STATUS_IF_OTH"));
				clientMasterBean.setExitRemarks(rs.getString("EXIT_REMARKS"));

				ClientAddressBean addressBean = new ClientAddressBean();

				addressBean.setAddressId(rs.getInt("address_id"));
				addressBean.setClientId(_clientId);
				addressBean.setGramPanchayatMouza(rs.getString("gram_panchayat_mouza"));
				addressBean.setParaVillage(rs.getString("para_village"));
				addressBean.setPoliceStation(rs.getString("adds_police_station"));
				addressBean.setPostOffice(rs.getString("post_office"));
				addressBean.setPinNo(rs.getString("pin_no"));
				addressBean.setDistrict(rs.getString("district"));
				addressBean.setState(rs.getString("state"));
				addressBean.setCountry(rs.getString("country"));

				ClientFamilyBean clientFamilyBean = new ClientFamilyBean();

				clientFamilyBean.setFamilyId(rs.getInt("family_id"));
				clientFamilyBean.setClientId(_clientId);
				clientFamilyBean.setGuardianName(rs.getString("guardian_name"));
				clientFamilyBean.setContactAddress(rs.getString("contact_address"));
				clientFamilyBean.setContact1No(rs.getString("contact1_no"));
				clientFamilyBean.setContact2No(rs.getString("contact2_no"));
				clientFamilyBean.setRelationWithGuardian(rs.getInt("relation_with_guardian"));
				clientFamilyBean.setRelationIfOth(rs.getString("relation_if_oth"));
				clientFamilyBean.setRelationWithGuardianStr(rs.getString("relationWithGuardianStr"));
				clientFamilyBean.setFamilySize(
						rs.getString("family_size") != null ? Integer.parseInt(rs.getString("family_size")) : null);
				clientFamilyBean.setOccupationOfFamily(rs.getString("occupation_of_family"));
				clientFamilyBean.setMonthlyIncomeOfFamily(rs.getString("monthly_income_of_family") != null
						? Double.parseDouble(rs.getString("monthly_income_of_family"))
						: null);

				clientDto.setClientMasterBean(clientMasterBean);
				clientDto.setClientAddressBean(addressBean);
				clientDto.setClientFamilyBean(clientFamilyBean);
			}

			/************************************************************************/

			ps.clearParameters();

			sql = "SELECT init_medical_det_id, provisional_diagnosis,";
			sql += " diagnosis_group, comorbidity, oth_medical_condition_found, diagnosis_group_if_oth,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = clt_det.diagnosis_group) diagnosisGrpStr";
			sql += " FROM mis_client_init_medical_det clt_det";
			sql += " WHERE CLIENT_ID = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, _clientId);
			rs = ps.executeQuery();

			if (rs.next()) {

				ClientInitMedicalDetBean clientInitMedicalDetBean = new ClientInitMedicalDetBean();

				clientInitMedicalDetBean.setInitMedicalDetId(rs.getInt("init_medical_det_id"));
				clientInitMedicalDetBean.setClientId(_clientId);
				clientInitMedicalDetBean.setProvisionalDiagnosis(rs.getString("provisional_diagnosis"));
				clientInitMedicalDetBean.setDiagnosisGroup(rs.getInt("diagnosis_group"));
				clientInitMedicalDetBean.setDiagnosisGroupStr(rs.getString("diagnosisGrpStr"));
				clientInitMedicalDetBean.setComorbidity(rs.getString("comorbidity"));
				clientInitMedicalDetBean.setOthMedicalConditionFound(rs.getString("oth_medical_condition_found"));
				clientInitMedicalDetBean.setDiagnosisGroupIfOth(rs.getString("diagnosis_group_if_oth"));

				clientDto.setClientInitMedicalDetBean(clientInitMedicalDetBean);
			}

			/************************************************************************/

			ps.clearParameters();

			sql = "SELECT bmi_id, weight, weight_unit,";
			sql += " height, height_unit, bmi, bmi_class, bp, date_of_entry, cohort_year, qtr_of_year,";
			sql += " (SELECT bmi_class FROM mis_client_bmi_class WHERE bmi_class_id = clt_bmi.bmi_class) AS bmi_class_str";
			sql += " FROM mis_client_bmi clt_bmi";
			sql += " WHERE CLIENT_ID = ? AND entry_flag = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, _clientId);
			ps.setString(2, "I");
			rs = ps.executeQuery();

			if (rs.next()) {
				ClientBMIBean clientBMIBean = new ClientBMIBean();

				clientBMIBean.setBmiId(rs.getInt("bmi_id"));
				clientBMIBean.setClientId(_clientId);
				clientBMIBean
						.setWeight(rs.getString("weight") != null ? Double.parseDouble(rs.getString("weight")) : null);
				clientBMIBean.setWeightUnit(rs.getString("weight_unit"));
				clientBMIBean
						.setHeight(rs.getString("height") != null ? Double.parseDouble(rs.getString("height")) : null);
				clientBMIBean.setHeightUnit(rs.getString("height_unit"));
				clientBMIBean.setBmi(rs.getString("bmi") != null ? Double.parseDouble(rs.getString("bmi")) : null);
				clientBMIBean.setBmiClass(
						rs.getString("bmi_class") != null ? Integer.parseInt(rs.getString("bmi_class")) : null);
				clientBMIBean.setBp(rs.getString("bp"));
				// clientBMIBean.setDateOfEntry(rs.getDate("date_of_entry").toLocalDate());
				clientBMIBean.setDateOfEntry(rs.getDate("date_of_entry"));
				clientBMIBean.setCohortYear(rs.getString("cohort_year"));
				clientBMIBean.setQtrOfYear(rs.getInt("qtr_of_year"));
				clientBMIBean.setBmiClassStr(rs.getString("bmi_class_str"));

				clientDto.setClientBMIBean(clientBMIBean);
			}

			/************************************************************************/

			ps.clearParameters();

			sql = "SELECT psycho_gaf_id, gaf_score, gaf_class,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = gaf.gaf_score) gafScoreStr,";
			sql += " date_of_entry, cohort_year, qtr_of_year";
			sql += " FROM mis_client_psycho_gaf gaf";
			sql += " WHERE CLIENT_ID = ? AND entry_flag = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, _clientId);
			ps.setString(2, "I");
			rs = ps.executeQuery();

			if (rs.next()) {

				ClientPsychoGafBean clientPsychoGafBean = new ClientPsychoGafBean();

				clientPsychoGafBean.setPsychoGafId(rs.getInt("psycho_gaf_id"));
				clientPsychoGafBean.setClientId(_clientId);
				clientPsychoGafBean.setGafScore(
						rs.getString("gaf_score") != null ? Integer.parseInt(rs.getString("gaf_score")) : null);
				clientPsychoGafBean.setGafClass(
						rs.getString("gaf_class") != null ? Integer.parseInt(rs.getString("gaf_class")) : null);
				clientPsychoGafBean.setGafScoreStr(rs.getString("gafScoreStr"));
				clientPsychoGafBean.setDateOfEntry(rs.getDate("date_of_entry"));
				clientPsychoGafBean.setCohortYear(rs.getString("cohort_year"));
				clientPsychoGafBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				// System.out.println(DateUtil.SqlDateToUtilDate(rs.getDate("date_of_entry")));
				clientDto.setClientPsychoGafBean(clientPsychoGafBean);
			}

			/************************************************************************/

			ps.clearParameters();

			sql = "SELECT psycho_ideas_id, sc, ia,";
			sql += " c_and_u, wrk, doi, gds, gis, ideas_status,";
			sql += " date_of_entry, cohort_year, qtr_of_year";
			sql += " FROM mis_client_psycho_ideas";
			sql += " WHERE CLIENT_ID = ? AND entry_flag = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, _clientId);
			ps.setString(2, "I");
			rs = ps.executeQuery();

			if (rs.next()) {

				ClientPsychoIdeasBean clientPsychoIdeasBean = new ClientPsychoIdeasBean();

				clientPsychoIdeasBean.setPsychoIdeasId(rs.getInt("psycho_ideas_id"));
				clientPsychoIdeasBean.setClientId(_clientId);
				clientPsychoIdeasBean.setSc(rs.getString("sc") != null ? Integer.parseInt(rs.getString("sc")) : null);
				clientPsychoIdeasBean.setIa(rs.getString("ia") != null ? Integer.parseInt(rs.getString("ia")) : null);
				clientPsychoIdeasBean
						.setC_and_u(rs.getString("c_and_u") != null ? Integer.parseInt(rs.getString("c_and_u")) : null);
				clientPsychoIdeasBean
						.setWrk(rs.getString("wrk") != null ? Integer.parseInt(rs.getString("wrk")) : null);
				clientPsychoIdeasBean
						.setDoi(rs.getString("doi") != null ? Integer.parseInt(rs.getString("doi")) : null);
				/*
				 * clientPsychoIdeasBean .setGds(rs.getString("gds") != null ?
				 * Integer.parseInt(rs.getString("gds")) : null);
				 */
				clientPsychoIdeasBean
						.setGis(rs.getString("gis") != null ? Integer.parseInt(rs.getString("gis")) : null);
				clientPsychoIdeasBean.setIdeasStatus(rs.getString("ideas_status"));
				clientPsychoIdeasBean.setDateOfEntry(rs.getDate("date_of_entry"));
				clientPsychoIdeasBean.setCohortYear(rs.getString("cohort_year"));
				clientPsychoIdeasBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientPsychoIdeasBean(clientPsychoIdeasBean);
			}

			/************************************************************************/

			ps.clearParameters();

			sql = "SELECT psycho_lsp_id, lsp_score, lsp_class,";
			sql += " date_of_entry, cohort_year, qtr_of_year";
			sql += " FROM mis_client_psycho_lsp";
			sql += " WHERE CLIENT_ID = ? AND entry_flag = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, _clientId);
			ps.setString(2, "I");
			rs = ps.executeQuery();

			if (rs.next()) {

				ClientPsychoLspBean clientPsychoLspBean = new ClientPsychoLspBean();

				clientPsychoLspBean.setPsychoLspId(rs.getInt("psycho_lsp_id"));
				clientPsychoLspBean.setClientId(_clientId);
				clientPsychoLspBean.setLspScore(
						rs.getString("lsp_score") != null ? Integer.parseInt(rs.getString("lsp_score")) : null);
				clientPsychoLspBean.setLspClass(
						rs.getString("lsp_class") != null ? Integer.parseInt(rs.getString("lsp_class")) : null);
				clientPsychoLspBean.setDateOfEntry(rs.getDate("date_of_entry"));
				clientPsychoLspBean.setCohortYear(rs.getString("cohort_year"));
				clientPsychoLspBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientPsychoLspBean(clientPsychoLspBean);
			}

			/************************************************************************/

			ps.clearParameters();

			sql = "SELECT psycho_panss_id, ps, ns, gp, total,";
			sql += " date_of_entry, cohort_year, qtr_of_year";
			sql += " FROM mis_client_psycho_panss";
			sql += " WHERE CLIENT_ID = ? AND entry_flag = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, _clientId);
			ps.setString(2, "I");
			rs = ps.executeQuery();

			if (rs.next()) {

				ClientPsychoPanssBean clientPsychoPanssBean = new ClientPsychoPanssBean();

				clientPsychoPanssBean.setPsychoPanssId(rs.getInt("psycho_panss_id"));
				clientPsychoPanssBean.setClientId(_clientId);
				clientPsychoPanssBean.setPs(rs.getString("ps") != null ? Integer.parseInt(rs.getString("ps")) : null);
				clientPsychoPanssBean.setNs(rs.getString("ns") != null ? Integer.parseInt(rs.getString("ns")) : null);
				clientPsychoPanssBean.setGp(rs.getString("gp") != null ? Integer.parseInt(rs.getString("gp")) : null);
				clientPsychoPanssBean
						.setTotal(rs.getString("total") != null ? Integer.parseInt(rs.getString("total")) : null);
				clientPsychoPanssBean.setDateOfEntry(rs.getDate("date_of_entry"));
				clientPsychoPanssBean.setCohortYear(rs.getString("cohort_year"));
				clientPsychoPanssBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientPsychoPanss(clientPsychoPanssBean);
			}

			/************************************************************************/

			ps.clearParameters();

			sql = "SELECT thyroid_signf_id, thyro_tsh, thyro_T3, thyro_T4,";
			sql += " date_of_entry, cohort_year, qtr_of_year";
			sql += " FROM mis_client_thyroid_significance";
			sql += " WHERE CLIENT_ID = ? AND entry_flag = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, _clientId);
			ps.setString(2, "I");
			rs = ps.executeQuery();

			if (rs.next()) {

				ClientThyroidSignificanceBean clientThyroidSignificanceBean = new ClientThyroidSignificanceBean();

				clientThyroidSignificanceBean.setThyroidSignfId(rs.getInt("thyroid_signf_id"));
				clientThyroidSignificanceBean.setClientId(_clientId);
				clientThyroidSignificanceBean.setThyroTsh(rs.getString("thyro_tsh"));
				clientThyroidSignificanceBean.setThyroT3(rs.getString("thyro_T3"));
				clientThyroidSignificanceBean.setThyroT4(rs.getString("thyro_T4"));
				clientThyroidSignificanceBean.setDateOfEntry(rs.getDate("date_of_entry"));
				clientThyroidSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				clientThyroidSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientThyroidSignificanceBean(clientThyroidSignificanceBean);
			}

			/************************************************************************/

			ps.clearParameters();

			sql = "SELECT diabetes_signf_id, diabetes_rbs, diabetes_fbs, diabetes_ppbs,";
			sql += " date_of_entry, cohort_year, qtr_of_year";
			sql += " FROM mis_client_diabetes_significance";
			sql += " WHERE CLIENT_ID = ? AND entry_flag = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, _clientId);
			ps.setString(2, "I");
			rs = ps.executeQuery();

			if (rs.next()) {

				ClientDiabetesSignificanceBean clientDiabetesSignificanceBean = new ClientDiabetesSignificanceBean();

				clientDiabetesSignificanceBean.setDiabetesSignfId(rs.getInt("diabetes_signf_id"));
				clientDiabetesSignificanceBean.setClientId(_clientId);
				clientDiabetesSignificanceBean.setDiabetesRBS(rs.getString("diabetes_rbs"));
				clientDiabetesSignificanceBean.setDiabetesFBS(rs.getString("diabetes_fbs"));
				clientDiabetesSignificanceBean.setDiabetesPPBS(rs.getString("diabetes_ppbs"));
				clientDiabetesSignificanceBean.setDateOfEntry(rs.getDate("date_of_entry"));
				clientDiabetesSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				clientDiabetesSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientDiabetesSignificanceBean(clientDiabetesSignificanceBean);
			}

			/************************************************************************/

			ps.clearParameters();

			sql = "SELECT hemoglobin_signf_id, hb_percent, esr, hemoglobin_level,";
			sql += " date_of_entry, oth_lab_test, oth_date_of_entry,";
			sql += " cohort_year, qtr_of_year";
			sql += " FROM mis_client_hemoglobin_significance";
			sql += " WHERE CLIENT_ID = ? AND entry_flag = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, _clientId);
			ps.setString(2, "I");
			rs = ps.executeQuery();

			if (rs.next()) {

				ClientHemoglobinSignificanceBean clientHemoglobinSignificanceBean = new ClientHemoglobinSignificanceBean();

				clientHemoglobinSignificanceBean.setHemoglobinSignfId(rs.getInt("hemoglobin_signf_id"));
				clientHemoglobinSignificanceBean.setClientId(_clientId);
				clientHemoglobinSignificanceBean.setHbPercent(rs.getString("hb_percent"));
				clientHemoglobinSignificanceBean.setEsr(rs.getString("esr"));
				clientHemoglobinSignificanceBean.setHemoglobinLevel(rs.getString("hemoglobin_level"));
				clientHemoglobinSignificanceBean.setDateOfEntry(rs.getDate("date_of_entry"));
				clientHemoglobinSignificanceBean.setOthLabTest(rs.getString("oth_lab_test"));
				clientHemoglobinSignificanceBean.setOthDateOfEntry(rs.getDate("oth_date_of_entry"));
				clientHemoglobinSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				clientHemoglobinSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientHemoglobinSignificanceBean(clientHemoglobinSignificanceBean);
			}

			/************************************************************************/

			ps.clearParameters();

			sql = "SELECT intake_admin_id, caregiver_name, doctor_signature, doctor_signature_date,";
			sql += " counsellor_sw_name, counsellor_sign_date, coordinator_name,";
			sql += " coordinator_sign_date, remarks";
			sql += " FROM mis_client_intake_administration";
			sql += " WHERE CLIENT_ID = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, _clientId);
			rs = ps.executeQuery();

			if (rs.next()) {

				ClientIntakeAdministrationBean clientIntakeAdministrationBean = new ClientIntakeAdministrationBean();

				clientIntakeAdministrationBean.setIntakeAdminId(rs.getInt("intake_admin_id"));
				clientIntakeAdministrationBean.setClientId(_clientId);
				clientIntakeAdministrationBean.setCaregiverName(rs.getString("caregiver_name"));
				clientIntakeAdministrationBean.setDoctorSignature(rs.getString("doctor_signature"));
				clientIntakeAdministrationBean.setDoctorSignatureDate(rs.getDate("doctor_signature_date"));
				clientIntakeAdministrationBean.setCounsellorSwName(rs.getString("counsellor_sw_name"));
				clientIntakeAdministrationBean.setCounsellorSignDate(rs.getDate("counsellor_sign_date"));
				clientIntakeAdministrationBean.setCoordinatorName(rs.getString("coordinator_name"));
				clientIntakeAdministrationBean.setCoordinatorSignDate(rs.getDate("coordinator_sign_date"));
				clientIntakeAdministrationBean.setRemarks(rs.getString("remarks"));

				clientDto.setClientIntakeAdministrationBean(clientIntakeAdministrationBean);

			}

			conn.commit();
		} catch (Exception ex) {
			try {
				clientDto = null;
				logger.info(
						"Problem in Class - ClientDaoImpl ~~ method- getClientIntakeDetails() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return clientDto;
	}

	@Override
	public List<ClientMasterBean> getClientNameList(String clientName, String homeId) {

		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ClientMasterBean> clientList = null;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			clientList = new ArrayList<ClientMasterBean>();

			sql = "SELECT CLIENT_NAME, CLIENT_ID, HOME_ID, CLIENT_UID, DATE_OF_ENTRY, exit_date FROM mis_client_master";
			sql += " WHERE HOME_ID = ? AND IS_DELETED = 'N'";
			if (clientName != null && !clientName.equalsIgnoreCase("")) {
				sql += " AND CLIENT_NAME LIKE ?";
			}
			sql += " ORDER BY CLIENT_ID DESC";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(homeId));

			if (clientName != null && !clientName.equalsIgnoreCase("")) {
				ps.setString(2, clientName + '%');
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				ClientMasterBean masterBean = new ClientMasterBean();
				masterBean.setClientName(rs.getString("CLIENT_NAME"));
				masterBean.setClientId(rs.getInt("CLIENT_ID"));
				masterBean.setHomeId(rs.getInt("HOME_ID"));
				masterBean.setClientUid(rs.getString("CLIENT_UID"));
				masterBean.setDateOfEntry(rs.getDate("DATE_OF_ENTRY"));
				masterBean.setExitDate(rs.getDate("exit_date"));
				clientList.add(masterBean);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				clientList = null;
				logger.info("Problem in Class - ClientDaoImpl ~~ method- getClientNameList() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return clientList;
	}

	@Override
	public String getUploadedFileInfo(Integer clientId, String documetType) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String path = null;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = "SELECT upload_path FROM mis_documents";
			sql += " WHERE client_id = ? AND document_type = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientId);
			ps.setString(2, documetType);

			rs = ps.executeQuery();

			if (rs.next()) {
				path = rs.getString("upload_path");
			}

			conn.commit();
		} catch (Exception ex) {
			try {
				path = null;
				logger.info("Problem in Class - ClientDaoImpl ~~ method- getUploadedFileInfo() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	@Override
	public List<ClientDto> getClientIntakeList(String homeId, String clientId, String cohortYr) {
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ArrayList<ClientDto> clientList = new ArrayList<ClientDto>();

		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			cs = conn.prepareCall("{CALL mis_get_client_intake_list2(?, ?, ?)}");
			cs.setInt(1, Integer.parseInt(homeId));

			if (clientId == null || clientId.equalsIgnoreCase("")) {
				cs.setNull(2, Types.INTEGER);
			} else {
				cs.setInt(2, Integer.parseInt(clientId));
			}
			if (cohortYr == null || cohortYr.equalsIgnoreCase("")) {
				cs.setNull(3, Types.VARCHAR);
			} else {
				cs.setString(3, cohortYr);
			}

			rs = cs.executeQuery();

			while (rs.next()) {

				ClientDto clientDto = new ClientDto();

				ClientMasterBean clientMasterBean = new ClientMasterBean();
				clientMasterBean.setClientId(rs.getInt("CLIENT_ID"));
				clientMasterBean.setHomeId(rs.getInt("HOME_ID"));
				clientMasterBean.setClientUid(rs.getString("CLIENT_UID"));
				clientMasterBean.setClientName(rs.getString("CLIENT_NAME"));
				clientMasterBean.setAge(rs.getString("AGE") != null ? Integer.parseInt(rs.getString("AGE")) : null);
				clientMasterBean.setAgeClassStr(rs.getString("ageCls"));
				clientMasterBean.setSex(rs.getInt("SEX"));
				clientMasterBean.setSexStr(rs.getString("sexStr"));
				clientMasterBean.setDateOfEntry(rs.getDate("DATE_OF_ENTRY"));
				clientMasterBean.setTypeOfEntry(rs.getInt("TYPE_OF_ENTRY"));
				clientMasterBean.setTypeOfEntryStr(rs.getString("typeOfEntryStr"));
				clientMasterBean.setCohortYear(rs.getString("COHORT_YEAR"));
				clientMasterBean.setFoundInArea(rs.getString("FOUND_IN_AREA"));
				clientMasterBean.setLandmark(rs.getString("LANDMARK"));
				clientMasterBean.setPoliceStation(rs.getString("POLICE_STATION"));
				clientMasterBean.setWardNo(rs.getString("WARD_NO"));
				clientMasterBean.setReferredBy(rs.getInt("REFERRED_BY"));
				clientMasterBean.setReferredByStr(rs.getString("refferedByStr"));
				clientMasterBean.setReferredDesc(rs.getString("REFERRED_DESC"));
				clientMasterBean.setReferenceNo(rs.getString("REFERENCE_NO"));
				clientMasterBean.setDateOfIdentification(rs.getDate("DATE_OF_IDENTIFICATION"));
				clientMasterBean.setIdentificationMark(rs.getString("IDENTIFICATION_MARK"));
				clientMasterBean.setReligionId(rs.getInt("RELIGION"));
				clientMasterBean.setReligionIdStr(rs.getString("religionStr"));
				clientMasterBean.setMotherTongue(rs.getString("MOTHER_TONGUE"));
				clientMasterBean.setOthLangKnown(rs.getString("OTH_LANG_KNOWN"));
				clientMasterBean.setEducationLevel(rs.getInt("EDUCATION_LEVEL"));
				clientMasterBean.setEducationLevelStr(rs.getString("educationLvlStr"));
				clientMasterBean.setOthInformation(rs.getString("OTH_INFORMATION"));
				clientMasterBean.setExitDate(rs.getDate("exit_date"));
				clientMasterBean.setExitStatus(rs.getInt("exit_status"));
				clientMasterBean.setExitStatusStr(rs.getString("exitStatusStr"));
				clientMasterBean.setDurationOfStay(
						rs.getString("duration_of_stay") != null ? Integer.parseInt(rs.getString("duration_of_stay"))
								: null);
				clientMasterBean.setReferredByIfOth(rs.getString("REFERRED_BY_IF_OTH"));
				clientMasterBean.setReligionIfOth(rs.getString("RELIGION_IF_OTH"));
				clientMasterBean.setExitStatusIfOth(rs.getString("EXIT_STATUS_IF_OTH"));
				clientMasterBean.setExitRemarks(rs.getString("EXIT_REMARKS"));

				ClientAddressBean addressBean = new ClientAddressBean();

				addressBean.setAddressId(rs.getInt("address_id"));
				// addressBean.setClientId(rs.getInt("CLIENT_ID"));
				addressBean.setGramPanchayatMouza(rs.getString("gram_panchayat_mouza"));
				addressBean.setParaVillage(rs.getString("para_village"));
				addressBean.setPoliceStation(rs.getString("adds_police_station"));
				addressBean.setPostOffice(rs.getString("post_office"));
				addressBean.setPinNo(rs.getString("pin_no"));
				addressBean.setDistrict(rs.getString("district"));
				addressBean.setState(rs.getString("state"));
				addressBean.setCountry(rs.getString("country"));

				ClientFamilyBean clientFamilyBean = new ClientFamilyBean();

				clientFamilyBean.setFamilyId(rs.getInt("family_id"));
				// clientFamilyBean.setClientId(rs.getInt("CLIENT_ID"));
				clientFamilyBean.setGuardianName(rs.getString("guardian_name"));
				clientFamilyBean.setContactAddress(rs.getString("contact_address"));
				clientFamilyBean.setContact1No(rs.getString("contact1_no"));
				clientFamilyBean.setContact2No(rs.getString("contact2_no"));
				clientFamilyBean.setRelationWithGuardian(rs.getInt("relation_with_guardian"));
				clientFamilyBean.setRelationIfOth(rs.getString("relation_if_oth"));
				clientFamilyBean.setRelationWithGuardianStr(rs.getString("relationWithGuardianStr"));
				clientFamilyBean.setFamilySize(
						rs.getString("family_size") != null ? Integer.parseInt(rs.getString("family_size")) : null);
				clientFamilyBean.setOccupationOfFamily(rs.getString("occupation_of_family"));
				clientFamilyBean.setMonthlyIncomeOfFamily(rs.getString("monthly_income_of_family") != null
						? Double.parseDouble(rs.getString("monthly_income_of_family"))
						: null);

				clientDto.setClientMasterBean(clientMasterBean);
				clientDto.setClientAddressBean(addressBean);
				clientDto.setClientFamilyBean(clientFamilyBean);

				/***********************************************************************************************/

				ClientInitMedicalDetBean clientInitMedicalDetBean = new ClientInitMedicalDetBean();

				clientInitMedicalDetBean.setInitMedicalDetId(rs.getInt("init_medical_det_id"));
				// clientInitMedicalDetBean.setClientId(rs.getInt("CLIENT_ID"));
				clientInitMedicalDetBean.setProvisionalDiagnosis(rs.getString("provisional_diagnosis"));
				clientInitMedicalDetBean.setDiagnosisGroup(rs.getInt("diagnosis_group"));
				clientInitMedicalDetBean.setDiagnosisGroupStr(rs.getString("diagnosisGrpStr"));
				clientInitMedicalDetBean.setComorbidity(rs.getString("comorbidity"));
				clientInitMedicalDetBean.setOthMedicalConditionFound(rs.getString("oth_medical_condition_found"));
				clientInitMedicalDetBean.setDiagnosisGroupIfOth(rs.getString("diagnosis_group_if_oth"));

				clientDto.setClientInitMedicalDetBean(clientInitMedicalDetBean);

				/***********************************************************************************************/

				ClientBMIBean clientBMIBean = new ClientBMIBean();

				clientBMIBean.setBmiId(rs.getInt("bmi_id"));
				// clientBMIBean.setClientId(rs.getInt("CLIENT_ID"));
				clientBMIBean
						.setWeight(rs.getString("weight") != null ? Double.parseDouble(rs.getString("weight")) : null);
				clientBMIBean.setWeightUnit(rs.getString("weight_unit"));
				clientBMIBean
						.setHeight(rs.getString("height") != null ? Double.parseDouble(rs.getString("height")) : null);
				clientBMIBean.setHeightUnit(rs.getString("height_unit"));
				clientBMIBean.setBmi(rs.getString("bmi") != null ? Double.parseDouble(rs.getString("bmi")) : null);
				clientBMIBean.setBmiClass(
						rs.getString("bmi_class") != null ? Integer.parseInt(rs.getString("bmi_class")) : null);
				clientBMIBean.setBp(rs.getString("bp"));
				// clientBMIBean.setDateOfEntry(rs.getDate("date_of_entry").toLocalDate());
				clientBMIBean.setDateOfEntry(rs.getDate("clt_bmi.date_of_entry"));
				clientBMIBean.setCohortYear(rs.getString("cohort_year"));
				clientBMIBean.setQtrOfYear(rs.getInt("qtr_of_year"));
				clientBMIBean.setBmiClassStr(rs.getString("bmi_class_str"));

				clientDto.setClientBMIBean(clientBMIBean);

				/***********************************************************************************************/

				ClientPsychoGafBean clientPsychoGafBean = new ClientPsychoGafBean();

				clientPsychoGafBean.setPsychoGafId(rs.getInt("psycho_gaf_id"));
				// clientPsychoGafBean.setClientId(rs.getInt("CLIENT_ID"));
				clientPsychoGafBean.setGafScore(
						rs.getString("gaf_score") != null ? Integer.parseInt(rs.getString("gaf_score")) : null);
				clientPsychoGafBean.setGafClass(
						rs.getString("gaf_class") != null ? Integer.parseInt(rs.getString("gaf_class")) : null);
				clientPsychoGafBean.setGafScoreStr(rs.getString("gafScoreStr"));
				clientPsychoGafBean.setDateOfEntry(rs.getDate("mis_client_psycho_gaf.date_of_entry"));
				clientPsychoGafBean.setCohortYear(rs.getString("cohort_year"));
				clientPsychoGafBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				// System.out.println(DateUtil.SqlDateToUtilDate(rs.getDate("date_of_entry")));
				clientDto.setClientPsychoGafBean(clientPsychoGafBean);

				/***********************************************************************************************/

				ClientPsychoIdeasBean clientPsychoIdeasBean = new ClientPsychoIdeasBean();

				clientPsychoIdeasBean.setPsychoIdeasId(rs.getInt("psycho_ideas_id"));
				// clientPsychoIdeasBean.setClientId(rs.getInt("CLIENT_ID"));
				clientPsychoIdeasBean.setSc(rs.getString("sc") != null ? Integer.parseInt(rs.getString("sc")) : null);
				clientPsychoIdeasBean.setIa(rs.getString("ia") != null ? Integer.parseInt(rs.getString("ia")) : null);
				clientPsychoIdeasBean
						.setC_and_u(rs.getString("c_and_u") != null ? Integer.parseInt(rs.getString("c_and_u")) : null);
				clientPsychoIdeasBean
						.setWrk(rs.getString("wrk") != null ? Integer.parseInt(rs.getString("wrk")) : null);
				clientPsychoIdeasBean
						.setDoi(rs.getString("doi") != null ? Integer.parseInt(rs.getString("doi")) : null);
				/*
				 * clientPsychoIdeasBean .setGds(rs.getString("gds") != null ?
				 * Integer.parseInt(rs.getString("gds")) : null);
				 */
				clientPsychoIdeasBean
						.setGis(rs.getString("gis") != null ? Integer.parseInt(rs.getString("gis")) : null);
				clientPsychoIdeasBean.setIdeasStatus(rs.getString("ideas_status"));
				clientPsychoIdeasBean.setDateOfEntry(rs.getDate("psycho_ids.date_of_entry"));
				clientPsychoIdeasBean.setCohortYear(rs.getString("cohort_year"));
				clientPsychoIdeasBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientPsychoIdeasBean(clientPsychoIdeasBean);

				/***********************************************************************************************/

				ClientPsychoLspBean clientPsychoLspBean = new ClientPsychoLspBean();

				clientPsychoLspBean.setPsychoLspId(rs.getInt("psycho_lsp_id"));
				// clientPsychoLspBean.setClientId(rs.getInt("CLIENT_ID"));
				clientPsychoLspBean.setLspScore(
						rs.getString("lsp_score") != null ? Integer.parseInt(rs.getString("lsp_score")) : null);
				clientPsychoLspBean.setLspClass(
						rs.getString("lsp_class") != null ? Integer.parseInt(rs.getString("lsp_class")) : null);
				clientPsychoLspBean.setDateOfEntry(rs.getDate("psycho_lsp.date_of_entry"));
				clientPsychoLspBean.setCohortYear(rs.getString("cohort_year"));
				clientPsychoLspBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientPsychoLspBean(clientPsychoLspBean);

				/***********************************************************************************************/

				ClientPsychoPanssBean clientPsychoPanssBean = new ClientPsychoPanssBean();

				clientPsychoPanssBean.setPsychoPanssId(rs.getInt("psycho_panss_id"));
				// clientPsychoPanssBean.setClientId(rs.getInt("CLIENT_ID"));
				clientPsychoPanssBean.setPs(rs.getString("ps") != null ? Integer.parseInt(rs.getString("ps")) : null);
				clientPsychoPanssBean.setNs(rs.getString("ns") != null ? Integer.parseInt(rs.getString("ns")) : null);
				clientPsychoPanssBean.setGp(rs.getString("gp") != null ? Integer.parseInt(rs.getString("gp")) : null);
				clientPsychoPanssBean
						.setTotal(rs.getString("total") != null ? Integer.parseInt(rs.getString("total")) : null);
				clientPsychoPanssBean.setDateOfEntry(rs.getDate("psycho_panss.date_of_entry"));
				clientPsychoPanssBean.setCohortYear(rs.getString("cohort_year"));
				clientPsychoPanssBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientPsychoPanss(clientPsychoPanssBean);

				/***********************************************************************************************/

				ClientThyroidSignificanceBean clientThyroidSignificanceBean = new ClientThyroidSignificanceBean();

				clientThyroidSignificanceBean.setThyroidSignfId(rs.getInt("thyroid_signf_id"));
				// clientThyroidSignificanceBean.setClientId(rs.getInt("CLIENT_ID"));
				clientThyroidSignificanceBean.setThyroTsh(rs.getString("thyro_tsh"));
				clientThyroidSignificanceBean.setThyroT3(rs.getString("thyro_T3"));
				clientThyroidSignificanceBean.setThyroT4(rs.getString("thyro_T4"));
				clientThyroidSignificanceBean.setDateOfEntry(rs.getDate("thy_signifi.date_of_entry"));
				clientThyroidSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				clientThyroidSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientThyroidSignificanceBean(clientThyroidSignificanceBean);

				/***********************************************************************************************/

				ClientDiabetesSignificanceBean clientDiabetesSignificanceBean = new ClientDiabetesSignificanceBean();

				clientDiabetesSignificanceBean.setDiabetesSignfId(rs.getInt("diabetes_signf_id"));
				// clientDiabetesSignificanceBean.setClientId(rs.getInt("CLIENT_ID"));
				clientDiabetesSignificanceBean.setDiabetesRBS(rs.getString("diabetes_rbs"));
				clientDiabetesSignificanceBean.setDiabetesFBS(rs.getString("diabetes_fbs"));
				clientDiabetesSignificanceBean.setDiabetesPPBS(rs.getString("diabetes_ppbs"));
				clientDiabetesSignificanceBean.setDateOfEntry(rs.getDate("diabets_signifi.date_of_entry"));
				clientDiabetesSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				clientDiabetesSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientDiabetesSignificanceBean(clientDiabetesSignificanceBean);

				/***********************************************************************************************/

				ClientHemoglobinSignificanceBean clientHemoglobinSignificanceBean = new ClientHemoglobinSignificanceBean();

				clientHemoglobinSignificanceBean.setHemoglobinSignfId(rs.getInt("hemoglobin_signf_id"));
				// clientHemoglobinSignificanceBean.setClientId(rs.getInt("CLIENT_ID"));
				clientHemoglobinSignificanceBean.setHbPercent(rs.getString("hb_percent"));
				clientHemoglobinSignificanceBean.setEsr(rs.getString("esr"));
				clientHemoglobinSignificanceBean.setHemoglobinLevel(rs.getString("hemoglobin_level"));
				clientHemoglobinSignificanceBean.setDateOfEntry(rs.getDate("hemogl_signifi.date_of_entry"));
				clientHemoglobinSignificanceBean.setOthLabTest(rs.getString("oth_lab_test"));
				clientHemoglobinSignificanceBean.setOthDateOfEntry(rs.getDate("oth_date_of_entry"));
				clientHemoglobinSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				clientHemoglobinSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));

				clientDto.setClientHemoglobinSignificanceBean(clientHemoglobinSignificanceBean);

				/***********************************************************************************************/

				ClientIntakeAdministrationBean clientIntakeAdministrationBean = new ClientIntakeAdministrationBean();

				clientIntakeAdministrationBean.setIntakeAdminId(rs.getInt("intake_admin_id"));
				// clientIntakeAdministrationBean.setClientId(rs.getInt("CLIENT_ID"));
				clientIntakeAdministrationBean.setCaregiverName(rs.getString("caregiver_name"));
				clientIntakeAdministrationBean.setDoctorSignature(rs.getString("doctor_signature"));
				clientIntakeAdministrationBean.setDoctorSignatureDate(rs.getDate("doctor_signature_date"));
				clientIntakeAdministrationBean.setCounsellorSwName(rs.getString("counsellor_sw_name"));
				clientIntakeAdministrationBean.setCounsellorSignDate(rs.getDate("counsellor_sign_date"));
				clientIntakeAdministrationBean.setCoordinatorName(rs.getString("coordinator_name"));
				clientIntakeAdministrationBean.setCoordinatorSignDate(rs.getDate("coordinator_sign_date"));
				clientIntakeAdministrationBean.setRemarks(rs.getString("remarks"));

				clientDto.setClientIntakeAdministrationBean(clientIntakeAdministrationBean);

				/***********************************************************************************************/

				clientList.add(clientDto);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				clientList = null;
				logger.info("Problem in Class - ClientDaoImpl ~~ method- getClientIntakeList() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return clientList;
	}

}
