package com.ISMIS.daoImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ISMIS.dao.ClientHealthDao;

import com.ISMIS.dto.PsychometryTestDto;

import com.ISMIS.dto.DiagnosisDto;
import com.ISMIS.dto.PathologicalDto;
import com.ISMIS.model.ClientBMIBean;
import com.ISMIS.model.ClientDiabetesSignificanceBean;
import com.ISMIS.model.ClientHemoglobinSignificanceBean;
import com.ISMIS.model.ClientHivTestBean;
import com.ISMIS.model.ClientMasterBean;
import com.ISMIS.model.ClientPsychoGafBean;
import com.ISMIS.model.ClientPsychoIdeasBean;
import com.ISMIS.model.ClientPsychoLspBean;
import com.ISMIS.model.ClientPsychoPanssBean;
import com.ISMIS.model.ClientThyroidSignificanceBean;
import com.ISMIS.model.ClientMedication;
import com.ISMIS.model.ClientMedicationDetails;
import com.ISMIS.model.ClientOtherTestsBean;

@Repository
public class ClientHealthDaoImpl extends CommonDaoImpl implements ClientHealthDao {

	static Logger logger = LogManager.getLogger(ClientHealthDaoImpl.class);

	@Override
	public List<ClientBMIBean> getBmiList(Integer clientId) {

		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ClientBMIBean> clientBmiList = null;

		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			clientBmiList = new ArrayList<ClientBMIBean>();

			sql = "SELECT bmi_id, weight, weight_unit,";
			sql += " height, height_unit, bmi, bmi_class, bp, date_of_entry, cohort_year, qtr_of_year,";
			sql += " (SELECT bmi_class FROM mis_client_bmi_class WHERE bmi_class_id = clt_bmi.bmi_class) AS bmi_class_str";
			sql += " FROM mis_client_bmi clt_bmi";
			sql += " WHERE CLIENT_ID = ? AND entry_flag = ? AND IS_DELETED = ? ORDER BY bmi_id DESC";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientId);
			ps.setString(2, "R");
			ps.setString(3, "N");
			rs = ps.executeQuery();

			while (rs.next()) {

				ClientBMIBean clientBMIBean = new ClientBMIBean();

				clientBMIBean.setBmiId(rs.getInt("bmi_id"));
				clientBMIBean.setClientId(clientId);
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

				clientBMIBean.setDateOfEntry(rs.getDate("date_of_entry"));
				clientBMIBean.setCohortYear(rs.getString("cohort_year"));
				clientBMIBean.setQtrOfYear(rs.getInt("qtr_of_year"));
				clientBMIBean.setBmiClassStr(rs.getString("bmi_class_str"));

				clientBmiList.add(clientBMIBean);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				clientBmiList = null;
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- getBmiList() - " + ex.getMessage());
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
		return clientBmiList;
	}

	@Override
	public String saveClientBmi(ClientBMIBean bmiBean) {

		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String status = null;

		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = "INSERT INTO mis_client_bmi (";
			sql += " CLIENT_ID, weight, weight_unit, height,";
			sql += " height_unit, bmi, bmi_class, bp, date_of_entry,";
			sql += " cohort_year, qtr_of_year, entry_flag, CREATED_BY, MODIFIED_BY, CREATED_ON";
			sql += " ) VALUES";
			sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, bmiBean.getClientId());

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
			}

			ps.setString(10, bmiBean.getCohortYear());
			ps.setInt(11, bmiBean.getQtrOfYear());
			ps.setString(12, "R");
			ps.setInt(13, bmiBean.getCreatedBy());
			ps.setInt(14, bmiBean.getCreatedBy());

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				bmiBean.setBmiId(rs.getInt(1));
			}

			conn.commit();

		} catch (Exception ex) {
			try {
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- saveClientBmi() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				status = sStackTrace;
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
		return status;
	}

	@Override
	public String updateClientBmi(ClientBMIBean bmiBean) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		// ResultSet rs = null;
		String status = null;

		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

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

			}

			ps.setString(9, bmiBean.getCohortYear());
			ps.setInt(10, bmiBean.getQtrOfYear());
			ps.setInt(11, bmiBean.getModifiedBy());
			ps.setInt(12, bmiBean.getBmiId());

			ps.executeUpdate();
			ps.clearBatch();
			ps.clearParameters();

			conn.commit();

		} catch (Exception ex) {
			try {
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- updateClientBmi() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				status = sStackTrace;
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
		return status;
	}

	@Override
	public List<PsychometryTestDto> getClientPsychometryTestList(Integer clientId) {

		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PsychometryTestDto> clientPsychometryTestList = null;

		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			clientPsychometryTestList = new ArrayList<PsychometryTestDto>();

			/*
			 * sql = "SELECT psycho_seq_gen.seq_id,"; sql +=
			 * " mis_client_psycho_gaf.psycho_gaf_id, mis_client_psycho_gaf.gaf_class, mis_client_psycho_gaf.gaf_score,"
			 * ; sql += " mis_client_psycho_gaf.client_id, mis_client_psycho_gaf.seq_id,";
			 * sql +=
			 * " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = mis_client_psycho_gaf.gaf_score) gafScoreStr,"
			 * ; sql +=
			 * " mis_client_psycho_gaf.date_of_entry, mis_client_psycho_gaf.cohort_year, mis_client_psycho_gaf.qtr_of_year,"
			 * ; sql +=
			 * " psycho_ids.psycho_ideas_id, psycho_ids.sc, psycho_ids.ia, psycho_ids.client_id,"
			 * ; sql +=
			 * " psycho_ids.c_and_u, psycho_ids.wrk, psycho_ids.doi, psycho_ids.gds, psycho_ids.gis, psycho_ids.ideas_status,"
			 * ; sql +=
			 * " psycho_ids.date_of_entry, psycho_ids.cohort_year, psycho_ids.qtr_of_year, psycho_ids.seq_id,"
			 * ; sql +=
			 * " psycho_lsp.psycho_lsp_id, psycho_lsp.lsp_score, psycho_lsp.lsp_class, psycho_lsp.client_id,"
			 * ; sql +=
			 * " psycho_lsp.date_of_entry, psycho_lsp.cohort_year, psycho_lsp.qtr_of_year, psycho_lsp.seq_id,"
			 * ; sql +=
			 * " psycho_panss.psycho_panss_id, psycho_panss.ps, psycho_panss.ns, psycho_panss.gp, psycho_panss.total,"
			 * ; sql +=
			 * " psycho_panss.date_of_entry, psycho_panss.cohort_year, psycho_panss.qtr_of_year, psycho_panss.seq_id, psycho_panss.client_id"
			 * ; sql += " FROM"; sql +=
			 * " (SELECT * FROM mis_client_psycho_seq_gen WHERE client_id = ? ORDER BY seq_id DESC) psycho_seq_gen LEFT OUTER JOIN"
			 * ; sql +=
			 * " (SELECT * FROM mis_client_psycho_gaf WHERE entry_flag='R') mis_client_psycho_gaf"
			 * ; sql += " ON psycho_seq_gen.seq_id = mis_client_psycho_gaf.seq_id"; sql +=
			 * " LEFT OUTER JOIN"; sql +=
			 * " (SELECT * FROM mis_client_psycho_ideas WHERE entry_flag='R') psycho_ids";
			 * sql += " ON psycho_seq_gen.seq_id = psycho_ids.seq_id"; sql +=
			 * " LEFT OUTER JOIN"; sql +=
			 * " (SELECT * FROM mis_client_psycho_lsp WHERE entry_flag='R')  psycho_lsp";
			 * sql += " ON psycho_seq_gen.seq_id = psycho_lsp.seq_id"; sql +=
			 * " LEFT OUTER JOIN"; sql +=
			 * " (SELECT * FROM mis_client_psycho_panss WHERE entry_flag='R') psycho_panss";
			 * sql += " ON psycho_seq_gen.seq_id = psycho_panss.seq_id";
			 */
			sql = "SELECT";
			sql += " psycho_seq_gen.seq_id,";
			sql += " mis_client_psycho_gaf.psycho_gaf_id, mis_client_psycho_gaf.gaf_score, mis_client_psycho_gaf.gaf_class,";
			sql += " mis_client_psycho_gaf.client_id, mis_client_psycho_gaf.seq_id,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = mis_client_psycho_gaf.gaf_score) gafScoreStr,";
			sql += " mis_client_psycho_gaf.date_of_entry, mis_client_psycho_gaf.cohort_year, mis_client_psycho_gaf.qtr_of_year,";
			sql += " psycho_ids.psycho_ideas_id, psycho_ids.sc, psycho_ids.ia, psycho_ids.client_id,";
			sql += " psycho_ids.c_and_u, psycho_ids.wrk, psycho_ids.doi, psycho_ids.gds, psycho_ids.gis, psycho_ids.ideas_status,";
			sql += " psycho_ids.date_of_entry, psycho_ids.cohort_year, psycho_ids.qtr_of_year, psycho_ids.seq_id,";
			sql += " psycho_lsp.psycho_lsp_id, psycho_lsp.lsp_score, psycho_lsp.lsp_class, psycho_lsp.client_id,";
			sql += " psycho_lsp.date_of_entry, psycho_lsp.cohort_year, psycho_lsp.qtr_of_year, psycho_lsp.seq_id,";
			sql += " psycho_panss.psycho_panss_id, psycho_panss.ps, psycho_panss.ns, psycho_panss.gp, psycho_panss.total, psycho_panss.client_id,";
			sql += " psycho_panss.date_of_entry, psycho_panss.cohort_year, psycho_panss.qtr_of_year, psycho_panss.seq_id";
			sql += " FROM";
			sql += " (SELECT * FROM mis_client_psycho_seq_gen WHERE client_id = ?) psycho_seq_gen LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_psycho_gaf WHERE entry_flag='R' AND IS_DELETED = 'N') mis_client_psycho_gaf";
			sql += " ON psycho_seq_gen.seq_id = mis_client_psycho_gaf.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_psycho_ideas WHERE entry_flag='R' AND IS_DELETED = 'N') psycho_ids";
			sql += " ON psycho_seq_gen.seq_id = psycho_ids.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_psycho_lsp WHERE entry_flag='R' AND IS_DELETED = 'N') psycho_lsp";
			sql += " ON psycho_seq_gen.seq_id = psycho_lsp.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_psycho_panss WHERE entry_flag='R' AND IS_DELETED = 'N') psycho_panss";
			sql += " ON psycho_seq_gen.seq_id = psycho_panss.seq_id ORDER BY psycho_seq_gen.seq_id DESC";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientId);
			rs = ps.executeQuery();

			while (rs.next()) {

				ClientPsychoGafBean clientPsychoGafBean = new ClientPsychoGafBean();

				clientPsychoGafBean.setPsychoGafId(rs.getInt("mis_client_psycho_gaf.psycho_gaf_id"));
				clientPsychoGafBean.setClientId(rs.getInt("mis_client_psycho_gaf.client_id"));
				clientPsychoGafBean.setGafScore(rs.getString("mis_client_psycho_gaf.gaf_score") != null
						? Integer.parseInt(rs.getString("mis_client_psycho_gaf.gaf_score"))
						: null);
				clientPsychoGafBean.setGafClass(rs.getString("mis_client_psycho_gaf.gaf_class") != null
						? Integer.parseInt(rs.getString("mis_client_psycho_gaf.gaf_class"))
						: null);
				clientPsychoGafBean.setGafScoreStr(rs.getString("gafScoreStr"));
				clientPsychoGafBean.setDateOfEntry(rs.getDate("mis_client_psycho_gaf.date_of_entry"));
				clientPsychoGafBean.setCohortYear(rs.getString("mis_client_psycho_gaf.cohort_year"));
				clientPsychoGafBean.setQtrOfYear(rs.getInt("mis_client_psycho_gaf.qtr_of_year"));
				clientPsychoGafBean.setSeqId(rs.getInt("mis_client_psycho_gaf.seq_id"));

				/****************************************************************************/

				ClientPsychoIdeasBean clientPsychoIdeasBean = new ClientPsychoIdeasBean();

				clientPsychoIdeasBean.setPsychoIdeasId(rs.getInt("psycho_ids.psycho_ideas_id"));
				clientPsychoIdeasBean.setClientId(rs.getInt("psycho_ids.client_id"));
				clientPsychoIdeasBean.setSc(
						rs.getString("psycho_ids.sc") != null ? Integer.parseInt(rs.getString("psycho_ids.sc")) : null);
				clientPsychoIdeasBean.setIa(
						rs.getString("psycho_ids.ia") != null ? Integer.parseInt(rs.getString("psycho_ids.ia")) : null);
				clientPsychoIdeasBean.setC_and_u(rs.getString("psycho_ids.c_and_u") != null
						? Integer.parseInt(rs.getString("psycho_ids.c_and_u"))
						: null);
				clientPsychoIdeasBean.setWrk(
						rs.getString("psycho_ids.wrk") != null ? Integer.parseInt(rs.getString("psycho_ids.wrk"))
								: null);
				clientPsychoIdeasBean.setDoi(
						rs.getString("psycho_ids.doi") != null ? Integer.parseInt(rs.getString("psycho_ids.doi"))
								: null);

				clientPsychoIdeasBean.setGis(
						rs.getString("psycho_ids.gis") != null ? Integer.parseInt(rs.getString("psycho_ids.gis"))
								: null);
				clientPsychoIdeasBean.setIdeasStatus(rs.getString("psycho_ids.ideas_status"));
				clientPsychoIdeasBean.setDateOfEntry(rs.getDate("psycho_ids.date_of_entry"));
				clientPsychoIdeasBean.setCohortYear(rs.getString("psycho_ids.cohort_year"));
				clientPsychoIdeasBean.setQtrOfYear(rs.getInt("psycho_ids.qtr_of_year"));
				clientPsychoIdeasBean.setSeqId(rs.getInt("psycho_ids.seq_id"));

				/************************************************************************/
				ClientPsychoLspBean clientPsychoLspBean = new ClientPsychoLspBean();

				clientPsychoLspBean.setPsychoLspId(rs.getInt("psycho_lsp.psycho_lsp_id"));
				clientPsychoLspBean.setClientId(rs.getInt("psycho_lsp.client_id"));
				clientPsychoLspBean.setLspScore(rs.getString("psycho_lsp.lsp_score") != null
						? Integer.parseInt(rs.getString("psycho_lsp.lsp_score"))
						: null);
				clientPsychoLspBean.setLspClass(rs.getString("psycho_lsp.lsp_class") != null
						? Integer.parseInt(rs.getString("psycho_lsp.lsp_class"))
						: null);
				clientPsychoLspBean.setDateOfEntry(rs.getDate("psycho_lsp.date_of_entry"));
				clientPsychoLspBean.setCohortYear(rs.getString("psycho_lsp.cohort_year"));
				clientPsychoLspBean.setQtrOfYear(rs.getInt("psycho_lsp.qtr_of_year"));
				clientPsychoLspBean.setSeqId(rs.getInt("psycho_lsp.seq_id"));

				/************************************************************************/

				ClientPsychoPanssBean clientPsychoPanssBean = new ClientPsychoPanssBean();

				clientPsychoPanssBean.setPsychoPanssId(rs.getInt("psycho_panss.psycho_panss_id"));
				clientPsychoPanssBean.setClientId(rs.getInt("psycho_panss.client_id"));
				clientPsychoPanssBean.setPs(
						rs.getString("psycho_panss.ps") != null ? Integer.parseInt(rs.getString("psycho_panss.ps"))
								: null);
				clientPsychoPanssBean.setNs(
						rs.getString("psycho_panss.ns") != null ? Integer.parseInt(rs.getString("psycho_panss.ns"))
								: null);
				clientPsychoPanssBean.setGp(
						rs.getString("psycho_panss.gp") != null ? Integer.parseInt(rs.getString("psycho_panss.gp"))
								: null);
				clientPsychoPanssBean.setTotal(rs.getString("psycho_panss.total") != null
						? Integer.parseInt(rs.getString("psycho_panss.total"))
						: null);
				clientPsychoPanssBean.setDateOfEntry(rs.getDate("psycho_panss.date_of_entry"));
				clientPsychoPanssBean.setCohortYear(rs.getString("psycho_panss.cohort_year"));
				clientPsychoPanssBean.setQtrOfYear(rs.getInt("psycho_panss.qtr_of_year"));
				clientPsychoPanssBean.setSeqId(rs.getInt("psycho_panss.seq_id"));

				PsychometryTestDto psychometryTestDto = new PsychometryTestDto();

				psychometryTestDto.setClientPsychoGafBean(clientPsychoGafBean);
				psychometryTestDto.setClientPsychoIdeasBean(clientPsychoIdeasBean);
				psychometryTestDto.setClientPsychoLspBean(clientPsychoLspBean);
				psychometryTestDto.setClientPsychoPanss(clientPsychoPanssBean);
				psychometryTestDto.setClientId(clientId);
				psychometryTestDto.setSequenceNo(rs.getInt("psycho_seq_gen.seq_id"));

				clientPsychometryTestList.add(psychometryTestDto);
			}

			conn.commit();
		} catch (Exception ex) {
			try {
				clientPsychometryTestList = null;
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- getPsychometryTestList() - "
						+ ex.getMessage());
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
		return clientPsychometryTestList;
	}

	@Override
	public String saveUpdateClientPsychometryTest(PsychometryTestDto psychometryTestDto) {

		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String status = null;

		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			if (psychometryTestDto.getSequenceNo() == null) {

				sql = "INSERT INTO mis_client_psycho_seq_gen (";
				sql += " CLIENT_ID,";
				sql += " CREATED_BY, CREATED_ON, MODIFIED_BY";
				sql += " ) VALUES";
				sql += " (?, ?, CURRENT_TIMESTAMP, ?)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, psychometryTestDto.getClientId());
				ps.setInt(2, psychometryTestDto.getUserId());
				ps.setInt(3, psychometryTestDto.getUserId());

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					psychometryTestDto.setSequenceNo(rs.getInt(1));
				}

				ps.clearBatch();
				ps.clearParameters();
			}

			/***********************************
			 * ClientPsychoGaf
			 ****************************************/

			if (psychometryTestDto.getClientPsychoGafBean() != null) {

				ClientPsychoGafBean clientPsychoGafBean = psychometryTestDto.getClientPsychoGafBean();

				if (psychometryTestDto.getClientPsychoGafBean().getPsychoGafId() == null) {

					/******************* INSERT CLIENT Psycho Gaf TABLE ****************/

					sql = "INSERT INTO mis_client_psycho_gaf (";
					sql += " CLIENT_ID, gaf_score, gaf_class, date_of_entry,";
					sql += " cohort_year, qtr_of_year, entry_flag, seq_id,";
					sql += " CREATED_BY, CREATED_ON, MODIFIED_BY";
					sql += " ) VALUES";
					sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, clientPsychoGafBean.getClientId());

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

					ps.setString(5, clientPsychoGafBean.getCohortYear());
					ps.setInt(6, clientPsychoGafBean.getQtrOfYear());
					ps.setString(7, "R");
					ps.setInt(8, psychometryTestDto.getSequenceNo());
					ps.setInt(9, psychometryTestDto.getUserId());
					ps.setInt(10, psychometryTestDto.getUserId());

					ps.executeUpdate();
					rs = ps.getGeneratedKeys();

					if (rs.next()) {
						clientPsychoGafBean.setPsychoGafId(rs.getInt(1));
						clientPsychoGafBean.setSeqId(psychometryTestDto.getSequenceNo());
					}

					ps.clearBatch();
					ps.clearParameters();

				} else if (clientPsychoGafBean.getIsDirty()) {

					/******************* UPDATE CLIENT Psycho Gaf TABLE ****************/

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

					ps.setString(4, clientPsychoGafBean.getCohortYear());
					ps.setInt(5, clientPsychoGafBean.getQtrOfYear());
					ps.setInt(6, psychometryTestDto.getUserId());
					ps.setInt(7, clientPsychoGafBean.getPsychoGafId());

					ps.executeUpdate();
					ps.clearBatch();
					ps.clearParameters();
				}

			}

			/***********************************
			 * ClientPsychoIdeas
			 ****************************************/

			if (psychometryTestDto.getClientPsychoIdeasBean() != null) {

				ClientPsychoIdeasBean clientPsychoIdeasBean = psychometryTestDto.getClientPsychoIdeasBean();

				if (psychometryTestDto.getClientPsychoIdeasBean().getPsychoIdeasId() == null) {

					/******************* INSERT CLIENT Psycho IDEAS TABLE ****************/

					sql = "INSERT INTO mis_client_psycho_ideas (";
					sql += " CLIENT_ID, sc, ia, c_and_u,";
					sql += " wrk, doi, gds, gis,";
					sql += " ideas_status, date_of_entry,";
					sql += " cohort_year, qtr_of_year, entry_flag, seq_id,";
					sql += " CREATED_BY, CREATED_ON, MODIFIED_BY";
					sql += " ) VALUES";
					sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, clientPsychoIdeasBean.getClientId());

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

					ps.setString(11, clientPsychoIdeasBean.getCohortYear());
					ps.setInt(12, clientPsychoIdeasBean.getQtrOfYear());
					ps.setString(13, "R");
					ps.setInt(14, psychometryTestDto.getSequenceNo());
					ps.setInt(15, psychometryTestDto.getUserId());
					ps.setInt(16, psychometryTestDto.getUserId());

					ps.executeUpdate();
					rs = ps.getGeneratedKeys();

					if (rs.next()) {
						clientPsychoIdeasBean.setPsychoIdeasId(rs.getInt(1));
						clientPsychoIdeasBean.setSeqId(psychometryTestDto.getSequenceNo());
					}

					ps.clearBatch();
					ps.clearParameters();

				} else if (clientPsychoIdeasBean.getIsDirty()) {

					/******************* UPDATE CLIENT Psycho IDEAS TABLE ****************/

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

					ps.setString(10, clientPsychoIdeasBean.getCohortYear());
					ps.setInt(11, clientPsychoIdeasBean.getQtrOfYear());
					ps.setInt(12, psychometryTestDto.getUserId());
					ps.setInt(13, clientPsychoIdeasBean.getPsychoIdeasId());

					ps.executeUpdate();
					ps.clearBatch();
					ps.clearParameters();
				}
			}

			/***********************
			 * ClientPsychoLsp
			 ****************************************/

			if (psychometryTestDto.getClientPsychoLspBean() != null) {

				ClientPsychoLspBean clientPsychoLspBean = psychometryTestDto.getClientPsychoLspBean();

				if (psychometryTestDto.getClientPsychoLspBean().getPsychoLspId() == null) {

					/******************* INSERT CLIENT Psycho LSP TABLE ****************/

					sql = "INSERT INTO mis_client_psycho_lsp (";
					sql += " CLIENT_ID, lsp_score, lsp_class, date_of_entry,";
					sql += " cohort_year, qtr_of_year, entry_flag, seq_id,";
					sql += " CREATED_BY, CREATED_ON, MODIFIED_BY";
					sql += " ) VALUES";
					sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, clientPsychoLspBean.getClientId());

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

					ps.setString(5, clientPsychoLspBean.getCohortYear());
					ps.setInt(6, clientPsychoLspBean.getQtrOfYear());
					ps.setString(7, "R");
					ps.setInt(8, psychometryTestDto.getSequenceNo());
					ps.setInt(9, psychometryTestDto.getUserId());
					ps.setInt(10, psychometryTestDto.getUserId());

					ps.executeUpdate();
					rs = ps.getGeneratedKeys();

					if (rs.next()) {
						clientPsychoLspBean.setPsychoLspId(rs.getInt(1));
						clientPsychoLspBean.setSeqId(psychometryTestDto.getSequenceNo());
					}

					ps.clearBatch();
					ps.clearParameters();

				} else if (clientPsychoLspBean.getIsDirty()) {

					/******************* UPDATE CLIENT Psycho LSP TABLE ****************/

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

					ps.setString(4, clientPsychoLspBean.getCohortYear());
					ps.setInt(5, clientPsychoLspBean.getQtrOfYear());
					ps.setInt(6, psychometryTestDto.getUserId());
					ps.setInt(7, clientPsychoLspBean.getPsychoLspId());

					ps.executeUpdate();
					ps.clearBatch();
					ps.clearParameters();
				}
			}

			/***********************************
			 * ClientPsychoPanss
			 ****************************************/

			if (psychometryTestDto.getClientPsychoPanss() != null) {

				ClientPsychoPanssBean clientPsychoPanssBean = psychometryTestDto.getClientPsychoPanss();

				if (psychometryTestDto.getClientPsychoPanss().getPsychoPanssId() == null) {

					/******************* INSERT CLIENT Psycho PANSS TABLE ****************/

					sql = "INSERT INTO mis_client_psycho_panss (";
					sql += " CLIENT_ID, ps, ns, gp,";
					sql += " total, date_of_entry,";
					sql += " cohort_year, qtr_of_year, entry_flag, seq_id,";
					sql += " CREATED_BY, CREATED_ON, MODIFIED_BY";
					sql += " ) VALUES";
					sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, clientPsychoPanssBean.getClientId());

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

					ps.setString(7, clientPsychoPanssBean.getCohortYear());
					ps.setInt(8, clientPsychoPanssBean.getQtrOfYear());
					ps.setString(9, "R");
					ps.setInt(10, psychometryTestDto.getSequenceNo());
					ps.setInt(11, psychometryTestDto.getUserId());
					ps.setInt(12, psychometryTestDto.getUserId());

					ps.executeUpdate();
					rs = ps.getGeneratedKeys();

					if (rs.next()) {
						clientPsychoPanssBean.setPsychoPanssId(rs.getInt(1));
						clientPsychoPanssBean.setSeqId(psychometryTestDto.getSequenceNo());
					}

					ps.clearBatch();
					ps.clearParameters();

				} else if (clientPsychoPanssBean.getIsDirty()) {

					/******************* UPDATE CLIENT Psycho PANSS TABLE ****************/

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

					ps.setString(6, clientPsychoPanssBean.getCohortYear());
					ps.setInt(7, clientPsychoPanssBean.getQtrOfYear());
					ps.setInt(8, psychometryTestDto.getUserId());
					ps.setInt(9, clientPsychoPanssBean.getPsychoPanssId());

					ps.executeUpdate();
					ps.clearBatch();
					ps.clearParameters();
				}
			}

			conn.commit();

		} catch (Exception ex) {
			try {
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- saveUpdateClinetPsychometryTest() - "
						+ ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				status = sStackTrace;
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
		return status;
	}

	@Override
	public List<ClientMedication> getMedicationList(Integer clientId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ClientMedication> clientMedicationList = null;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			clientMedicationList = new ArrayList<ClientMedication>();

			sql = "SELECT medication_id, diagnosis, doctor_name, date_of_prescription, cohort_year,qtr_of_year,IS_DELETED "
					+ "FROM mis_client_medication " + "WHERE client_id=? " + "AND IS_DELETED=? "
					+ "ORDER BY medication_id DESC";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientId);
			ps.setString(2, "N");
			rs = ps.executeQuery();

			while (rs.next()) {
				ClientMedication clientMedication = new ClientMedication();
				clientMedication.setMedicationId(rs.getInt("medication_id"));
				clientMedication.setClientId(clientId);
				clientMedication.setDiagnosis(rs.getString("diagnosis"));
				clientMedication.setDoctorName(rs.getString("doctor_name"));
				clientMedication.setDateOfPrescription(rs.getDate("date_of_prescription"));
				clientMedication.setCohortYear(rs.getString("cohort_year"));
				clientMedication.setQtrOfYear(rs.getInt("qtr_of_year"));
				clientMedication.setIsDeleted(rs.getString("IS_DELETED"));
				List<ClientMedicationDetails> clientMedicationDetailsList = getClientMedicationDetailsList(
						clientMedication.getMedicationId(), conn);
				clientMedication.setClientMedicationDetailsList(clientMedicationDetailsList);
				clientMedicationList.add(clientMedication);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				clientMedicationList = null;
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- getBmiList() - " + ex.getMessage());
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
		return clientMedicationList;
	}

	private List<ClientMedicationDetails> getClientMedicationDetailsList(Integer medicationId, Connection conn)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ClientMedicationDetails> clientMedicationDetailsList = new ArrayList<ClientMedicationDetails>();
		String sql = "SELECT medication_det_id, medicine_name, medicine_doses, medicine_type,"
				+ "(SELECT attribute_desc FROM mis_category_attributes "
				+ "WHERE category_attribute_id = cmd.medicine_type) AS medicine_type_str, IS_DELETED "
				+ "FROM mis_client_medication_det cmd " + "WHERE medication_id=? " + "AND IS_DELETED=? "
				+ "ORDER BY medication_det_id DESC";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, medicationId);
		ps.setString(2, "N");
		rs = ps.executeQuery();

		while (rs.next()) {
			ClientMedicationDetails clientMedicationDetails = new ClientMedicationDetails();
			clientMedicationDetails.setMedicationDetailId(rs.getInt("medication_det_id"));
			clientMedicationDetails.setMedicationId(medicationId);
			clientMedicationDetails.setMedicineName(rs.getString("medicine_name"));
			clientMedicationDetails.setMedicineDoses(rs.getString("medicine_doses"));
			clientMedicationDetails.setMedicineType(rs.getInt("medicine_type"));
			clientMedicationDetails.setMedicineTypeStr(rs.getString("medicine_type_str"));
			clientMedicationDetails.setIsDeleted(rs.getString("IS_DELETED"));
			clientMedicationDetailsList.add(clientMedicationDetails);
		}
		return clientMedicationDetailsList;
	}

	@Override
	public String saveClientMedication(ClientMedication clientMedication) {
		Connection conn = null;
		String sql = null;
		String sqlChild = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String status = null;

		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = "INSERT INTO mis_client_medication (";
			sql += " client_id, diagnosis, doctor_name, date_of_prescription,";
			sql += " cohort_year, qtr_of_year, CREATED_BY, MODIFIED_BY, CREATED_ON";
			sql += " ) VALUES";
			sql += " (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, clientMedication.getClientId());

			if (clientMedication.getDiagnosis() == null) {
				ps.setNull(2, Types.VARCHAR);
			} else {
				ps.setString(2, clientMedication.getDiagnosis());
			}

			if (clientMedication.getDoctorName() == null) {
				ps.setNull(3, Types.VARCHAR);
			} else {
				ps.setString(3, clientMedication.getDoctorName());
			}

			if (clientMedication.getDateOfPrescription() == null) {
				ps.setNull(4, Types.DATE);
			} else {
				ps.setDate(4, new java.sql.Date(clientMedication.getDateOfPrescription().getTime()));
			}

			ps.setString(5, clientMedication.getCohortYear());
			ps.setInt(6, clientMedication.getQtrOfYear());
			ps.setInt(7, clientMedication.getCreatedBy());
			ps.setInt(8, clientMedication.getCreatedBy());

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				clientMedication.setMedicationId(rs.getInt(1));
			}

			ps.clearParameters();

			sqlChild = "INSERT into mis_client_medication_det(medication_id, client_id, medicine_name, medicine_doses, medicine_type, CREATED_BY, MODIFIED_BY, CREATED_ON) ";
			sqlChild += "VALUES(?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
			for (ClientMedicationDetails details : clientMedication.getClientMedicationDetailsList()) {
				if (details.getIsDeleted().equalsIgnoreCase("Y")) {
					continue;
				}

				ps = conn.prepareStatement(sqlChild);
				ps.setInt(1, clientMedication.getMedicationId());
				ps.setInt(2, clientMedication.getClientId());

				if (details.getMedicineName() == null) {
					ps.setNull(3, Types.VARCHAR);
				} else {
					ps.setString(3, details.getMedicineName());
				}
				if (details.getMedicineDoses() == null) {
					ps.setNull(4, Types.VARCHAR);
				} else {
					ps.setString(4, details.getMedicineDoses());
				}
				if (details.getMedicineType() == null) {
					ps.setNull(5, Types.INTEGER);
				} else {
					ps.setInt(5, details.getMedicineType());
				}
				ps.setInt(6, details.getCreatedBy());
				ps.setInt(7, details.getCreatedBy());

				ps.executeUpdate();
			}
			conn.commit();

		} catch (Exception ex) {
			try {
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- saveClientMedication() - "
						+ ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				status = sStackTrace;
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
		return status;
	}

	@Override
	public String updateClientMedication(ClientMedication clientMedication) {
		Connection conn = null;
		String sql = null;
		String sqlChild = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String status = null;

		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = "UPDATE mis_client_medication SET " + "diagnosis = ?, " + "doctor_name = ?,  "
					+ "date_of_prescription = ?, " + "cohort_year = ?, " + "qtr_of_year = ?, "
					+ "modified_by = ?, MODIFIED_ON = CURRENT_TIMESTAMP " + "WHERE client_id = ? "
					+ "AND medication_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientMedication.getClientId());

			if (clientMedication.getDiagnosis() == null) {
				ps.setNull(1, Types.VARCHAR);
			} else {
				ps.setString(1, clientMedication.getDiagnosis());
			}

			if (clientMedication.getDoctorName() == null) {
				ps.setNull(2, Types.VARCHAR);
			} else {
				ps.setString(2, clientMedication.getDoctorName());
			}

			if (clientMedication.getDateOfPrescription() == null) {
				ps.setNull(3, Types.DATE);
			} else {
				ps.setDate(3, new java.sql.Date(clientMedication.getDateOfPrescription().getTime()));
			}

			ps.setString(4, clientMedication.getCohortYear());
			ps.setInt(5, clientMedication.getQtrOfYear());
			ps.setInt(6, clientMedication.getModifiedBy());
			ps.setInt(7, clientMedication.getClientId());
			ps.setInt(8, clientMedication.getMedicationId());

			ps.executeUpdate();
			ps.clearParameters();

			for (ClientMedicationDetails details : clientMedication.getClientMedicationDetailsList()) {
				if (details.getMedicationDetailId() == null) {
					// insert if new medicine added during update
					sqlChild = "INSERT into mis_client_medication_det(medication_id, client_id, medicine_name, medicine_doses, medicine_type, CREATED_BY, MODIFIED_BY, CREATED_ON) ";
					sqlChild += "VALUES(?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
					if (details.getIsDeleted().equalsIgnoreCase("Y")) {
						continue;
					}
					ps = conn.prepareStatement(sqlChild);
					ps.setInt(1, clientMedication.getMedicationId());
					ps.setInt(2, clientMedication.getClientId());

					if (details.getMedicineName() == null) {
						ps.setNull(3, Types.VARCHAR);
					} else {
						ps.setString(3, details.getMedicineName());
					}
					if (details.getMedicineDoses() == null) {
						ps.setNull(4, Types.VARCHAR);
					} else {
						ps.setString(4, details.getMedicineDoses());
					}
					if (details.getMedicineType() == null) {
						ps.setNull(5, Types.INTEGER);
					} else {
						ps.setInt(5, details.getMedicineType());
					}
					ps.setInt(6, details.getCreatedBy());
					ps.setInt(7, details.getCreatedBy());
				} else {
					// update existing medicine
					sqlChild = "UPDATE mis_client_medication_det SET " + "medicine_name = ?, " + "medicine_doses = ?,  "
							+ "medicine_type = ?, " + "IS_DELETED = ?, "
							+ "modified_by = ?, MODIFIED_ON = CURRENT_TIMESTAMP " + "WHERE medication_id = ? "
							+ "AND medication_det_id = ?";

					ps = conn.prepareStatement(sqlChild);

					if (details.getMedicineName() == null) {
						ps.setNull(1, Types.VARCHAR);
					} else {
						ps.setString(1, details.getMedicineName());
					}
					if (details.getMedicineDoses() == null) {
						ps.setNull(2, Types.VARCHAR);
					} else {
						ps.setString(2, details.getMedicineDoses());
					}
					if (details.getMedicineType() == null) {
						ps.setNull(3, Types.INTEGER);
					} else {
						ps.setInt(3, details.getMedicineType());
					}
					if (details.getIsDeleted() == null) {
						ps.setNull(4, Types.VARCHAR);
					} else {
						ps.setString(4, details.getIsDeleted());
					}
					ps.setInt(5, details.getModifiedBy());
					ps.setInt(6, details.getMedicationId());
					ps.setInt(7, details.getMedicationDetailId());
				}
				ps.executeUpdate();
			}
			conn.commit();

		} catch (Exception ex) {
			try {
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- updateClientMedication() - "
						+ ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				status = sStackTrace;
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
		return status;
	}

	@Override
	public DiagnosisDto getLastDiagnosisAndDoctor(Integer clientId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DiagnosisDto diagnosisDto = null;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = "SELECT diagnosis, doctor_name FROM mis_client_medication WHERE CREATED_ON = "
					+ "(SELECT MAX(CREATED_ON) FROM mis_client_medication WHERE client_id = ? AND IS_DELETED='N')";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientId);
			rs = ps.executeQuery();

			if (rs.next()) {
				diagnosisDto = new DiagnosisDto();

				diagnosisDto.setDiagnosis(rs.getString("diagnosis"));
				diagnosisDto.setDoctorName(rs.getString("doctor_name"));
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				diagnosisDto = null;
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- getLastDiagnosisAndDoctor() - "
						+ ex.getMessage());
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
		return diagnosisDto;
	}

	@Override
	public String saveUpdatePathologicalTest(PathologicalDto pathologicalDto) {

		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String status = null;

		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			if (pathologicalDto.getSequenceNo() == null) {

				sql = "INSERT INTO mis_client_pathology_seq_gen (";
				sql += " CLIENT_ID,";
				sql += " CREATED_BY, CREATED_ON, MODIFIED_BY";
				sql += " ) VALUES";
				sql += " (?, ?, CURRENT_TIMESTAMP, ?)";

				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, pathologicalDto.getClientId());
				ps.setInt(2, pathologicalDto.getUserId());
				ps.setInt(3, pathologicalDto.getUserId());

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					pathologicalDto.setSequenceNo(rs.getInt(1));
				}

				ps.clearBatch();
				ps.clearParameters();
			}

			/******************* CLIENT THYROID SIGNIFICANTS ****************/

			if (pathologicalDto.getClientThyroidSignificanceBean() != null) {

				ClientThyroidSignificanceBean clientThyroidSignificanceBean = pathologicalDto
						.getClientThyroidSignificanceBean();

				if (clientThyroidSignificanceBean.getThyroidSignfId() == null) {

					/******************* INSERT CLIENT THYROID SIGNIFICANTS TABLE ****************/

					sql = "INSERT INTO mis_client_thyroid_significance (";
					sql += " CLIENT_ID, thyro_tsh, thyro_T3, thyro_T4,";
					sql += " date_of_entry,";
					sql += " cohort_year, qtr_of_year, entry_flag, seq_id,";
					sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
					sql += " ) VALUES";
					sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, clientThyroidSignificanceBean.getClientId());

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

					ps.setString(6, clientThyroidSignificanceBean.getCohortYear());
					ps.setInt(7, clientThyroidSignificanceBean.getQtrOfYear());
					ps.setString(8, "R");
					ps.setInt(9, pathologicalDto.getSequenceNo());
					ps.setInt(10, pathologicalDto.getUserId());
					ps.setInt(11, pathologicalDto.getUserId());

					ps.executeUpdate();
					rs = ps.getGeneratedKeys();

					if (rs.next()) {
						clientThyroidSignificanceBean.setThyroidSignfId(rs.getInt(1));
						clientThyroidSignificanceBean.setSeqId(pathologicalDto.getSequenceNo());
					}

					ps.clearBatch();
					ps.clearParameters();

				} else if (clientThyroidSignificanceBean.getIsDirty()) {

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

					ps.setString(5, clientThyroidSignificanceBean.getCohortYear());
					ps.setInt(6, clientThyroidSignificanceBean.getQtrOfYear());
					ps.setInt(7, pathologicalDto.getUserId());
					ps.setInt(8, clientThyroidSignificanceBean.getThyroidSignfId());

					ps.executeUpdate();
					ps.clearBatch();
					ps.clearParameters();
				}
			}

			/******************* CLIENT DIABETES SIGNIFICANTS ****************/

			if (pathologicalDto.getClientDiabetesSignificanceBean() != null) {

				ClientDiabetesSignificanceBean clientDiabetesSignificanceBean = pathologicalDto
						.getClientDiabetesSignificanceBean();

				if (clientDiabetesSignificanceBean.getDiabetesSignfId() == null) {

					/******************* INSERT CLIENT DIABETES SIGNIFICANTS TABLE ****************/

					sql = "INSERT INTO mis_client_diabetes_significance (";
					sql += " CLIENT_ID, diabetes_rbs, diabetes_fbs, diabetes_ppbs,";
					sql += " date_of_entry,";
					sql += " cohort_year, qtr_of_year, entry_flag, seq_id,";
					sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
					sql += " ) VALUES";
					sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, clientDiabetesSignificanceBean.getClientId());

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

					ps.setString(6, clientDiabetesSignificanceBean.getCohortYear());
					ps.setInt(7, clientDiabetesSignificanceBean.getQtrOfYear());
					ps.setString(8, "R");
					ps.setInt(9, pathologicalDto.getSequenceNo());
					ps.setInt(10, pathologicalDto.getUserId());
					ps.setInt(11, pathologicalDto.getUserId());

					ps.executeUpdate();
					rs = ps.getGeneratedKeys();

					if (rs.next()) {
						clientDiabetesSignificanceBean.setDiabetesSignfId(rs.getInt(1));
						clientDiabetesSignificanceBean.setSeqId(pathologicalDto.getSequenceNo());
					}

					ps.clearBatch();
					ps.clearParameters();

				} else if (clientDiabetesSignificanceBean.getIsDirty()) {

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

					ps.setString(5, clientDiabetesSignificanceBean.getCohortYear());
					ps.setInt(6, clientDiabetesSignificanceBean.getQtrOfYear());
					ps.setInt(7, pathologicalDto.getSequenceNo());
					ps.setInt(8, clientDiabetesSignificanceBean.getDiabetesSignfId());

					ps.executeUpdate();
					ps.clearBatch();
					ps.clearParameters();
				}

			}

			/************ CLIENT HEMOGLOBIN SIGNIFICANCE ********/

			if (pathologicalDto.getClientHemoglobinSignificanceBean() != null) {

				ClientHemoglobinSignificanceBean clientHemoglobinSignificanceBean = pathologicalDto
						.getClientHemoglobinSignificanceBean();

				if (clientHemoglobinSignificanceBean.getHemoglobinSignfId() == null) {

					/************ INSERT CLIENT HEMOGLOBIN SIGNIFICANCE TABLE ********/

					sql = "INSERT INTO mis_client_hemoglobin_significance (";
					sql += " CLIENT_ID, hb_percent, esr, hemoglobin_level,";
					sql += " oth_lab_test, oth_date_of_entry,";
					sql += " cohort_year, qtr_of_year,";
					sql += " date_of_entry, entry_flag, seq_id,";
					sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
					sql += " ) VALUES";
					sql += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, clientHemoglobinSignificanceBean.getClientId());

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
						ps.setDate(6,
								new java.sql.Date(clientHemoglobinSignificanceBean.getOthDateOfEntry().getTime()));
					}

					ps.setString(7, clientHemoglobinSignificanceBean.getCohortYear());
					ps.setInt(8, clientHemoglobinSignificanceBean.getQtrOfYear());

					if (clientHemoglobinSignificanceBean.getDateOfEntry() == null) {
						ps.setNull(9, Types.DATE);
					} else {
						ps.setDate(9, new java.sql.Date(clientHemoglobinSignificanceBean.getDateOfEntry().getTime()));
					}
					ps.setString(10, "R");
					ps.setInt(11, pathologicalDto.getSequenceNo());
					ps.setInt(12, pathologicalDto.getUserId());
					ps.setInt(13, pathologicalDto.getUserId());

					ps.executeUpdate();
					rs = ps.getGeneratedKeys();

					if (rs.next()) {
						clientHemoglobinSignificanceBean.setHemoglobinSignfId(rs.getInt(1));
						clientHemoglobinSignificanceBean.setSeqId(pathologicalDto.getSequenceNo());
					}

					ps.clearBatch();
					ps.clearParameters();

				} else if (clientHemoglobinSignificanceBean.getIsDirty()) {

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
						ps.setDate(5,
								new java.sql.Date(clientHemoglobinSignificanceBean.getOthDateOfEntry().getTime()));
					}

					ps.setString(6, clientHemoglobinSignificanceBean.getCohortYear());
					ps.setInt(7, clientHemoglobinSignificanceBean.getQtrOfYear());

					if (clientHemoglobinSignificanceBean.getDateOfEntry() == null) {
						ps.setNull(8, Types.DATE);
					} else {
						ps.setDate(8, new java.sql.Date(clientHemoglobinSignificanceBean.getDateOfEntry().getTime()));
					}

					ps.setInt(9, pathologicalDto.getSequenceNo());
					ps.setInt(10, clientHemoglobinSignificanceBean.getHemoglobinSignfId());

					ps.executeUpdate();
					ps.clearBatch();
					ps.clearParameters();
				}
			}

			/************ CLIENT HIV TEST ********/

			if (pathologicalDto.getClientHivTestBean() != null) {

				ClientHivTestBean clientHivTestBean = pathologicalDto.getClientHivTestBean();

				if (clientHivTestBean.getHivId() == null) {

					/************ INSERT CLIENT HIV TEST ********/

					sql = "INSERT INTO mis_client_hiv_test (";
					sql += " CLIENT_ID, hiv_data, date_of_admin, seq_id,";
					sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
					sql += " ) VALUES";
					sql += " (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

					ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					ps.setInt(1, clientHivTestBean.getClientId());

					if (clientHivTestBean.getHivData() == null) {
						ps.setNull(2, Types.VARCHAR);
					} else {
						ps.setString(2, clientHivTestBean.getHivData());
					}

					if (clientHivTestBean.getDateOfAdmin() == null) {
						ps.setNull(3, Types.DATE);
					} else {
						ps.setDate(3, new java.sql.Date(clientHivTestBean.getDateOfAdmin().getTime()));
					}

					ps.setInt(4, pathologicalDto.getSequenceNo());
					ps.setInt(5, pathologicalDto.getUserId());
					ps.setInt(6, pathologicalDto.getUserId());

					ps.executeUpdate();
					rs = ps.getGeneratedKeys();

					if (rs.next()) {
						clientHivTestBean.setHivId(rs.getInt(1));
						clientHivTestBean.setSeqId(pathologicalDto.getSequenceNo());
					}

					ps.clearBatch();
					ps.clearParameters();

				} else if (clientHivTestBean.getIsDirty()) {

					sql = "UPDATE mis_client_hiv_test";
					sql += " SET hiv_data = ?,";
					sql += " date_of_admin = ?,";
					sql += " modified_by = ?,";
					sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
					sql += " WHERE hiv_id = ?";

					ps = conn.prepareStatement(sql);

					if (clientHivTestBean.getHivData() == null) {
						ps.setNull(1, Types.VARCHAR);
					} else {
						ps.setString(1, clientHivTestBean.getHivData());
					}

					if (clientHivTestBean.getDateOfAdmin() == null) {
						ps.setNull(2, Types.DATE);
					} else {
						ps.setDate(2, new java.sql.Date(clientHivTestBean.getDateOfAdmin().getTime()));
					}

					ps.setInt(3, pathologicalDto.getUserId());
					ps.setInt(4, clientHivTestBean.getHivId());

					ps.executeUpdate();
					ps.clearBatch();
					ps.clearParameters();

				}
			}

			/************ CLIENT Other Test ********/

			if (pathologicalDto.getListClientOthTest() != null) {

				List<ClientOtherTestsBean> listClientOthTest = pathologicalDto.getListClientOthTest();

				if (!listClientOthTest.isEmpty()) {

					for (ClientOtherTestsBean clientOtherTestsBean : listClientOthTest) {

						if (clientOtherTestsBean.getOtherTestId() == null) {

							sql = "INSERT INTO mis_client_other_tests (";
							sql += " CLIENT_ID, oth_test_name, oth_test_data, date_of_admin, seq_id,";
							sql += " CREATED_BY, MODIFIED_BY, CREATED_ON";
							sql += " ) VALUES";
							sql += " (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

							ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

							ps.setInt(1, clientOtherTestsBean.getClientId());

							if (clientOtherTestsBean.getOthTestName() == null) {
								ps.setNull(2, Types.VARCHAR);
							} else {
								ps.setString(2, clientOtherTestsBean.getOthTestName());
							}

							if (clientOtherTestsBean.getOthTestData() == null) {
								ps.setNull(3, Types.VARCHAR);
							} else {
								ps.setString(3, clientOtherTestsBean.getOthTestData());
							}

							if (clientOtherTestsBean.getDateOfAdmin() == null) {
								ps.setNull(4, Types.DATE);
							} else {
								ps.setDate(4, new java.sql.Date(clientOtherTestsBean.getDateOfAdmin().getTime()));
							}

							ps.setInt(5, pathologicalDto.getSequenceNo());
							ps.setInt(6, pathologicalDto.getUserId());
							ps.setInt(7, pathologicalDto.getUserId());

							ps.executeUpdate();
							rs = ps.getGeneratedKeys();

							if (rs.next()) {
								clientOtherTestsBean.setOtherTestId(rs.getInt(1));
								clientOtherTestsBean.setSeqId(pathologicalDto.getSequenceNo());
							}

							ps.clearBatch();
							ps.clearParameters();

						} else if (clientOtherTestsBean.getIsDirty()) {

							// update

							sql = "UPDATE mis_client_other_tests";
							sql += " SET oth_test_name = ?,";
							sql += " oth_test_data = ?,";
							sql += " date_of_admin = ?,";
							sql += " IS_DELETED = ?,";
							sql += " modified_by = ?,";
							sql += " MODIFIED_ON = CURRENT_TIMESTAMP";
							sql += " WHERE other_test_id = ?";

							ps = conn.prepareStatement(sql);

							if (clientOtherTestsBean.getOthTestName() == null) {
								ps.setNull(1, Types.VARCHAR);
							} else {
								ps.setString(1, clientOtherTestsBean.getOthTestName());
							}

							if (clientOtherTestsBean.getOthTestData() == null) {
								ps.setNull(2, Types.VARCHAR);
							} else {
								ps.setString(2, clientOtherTestsBean.getOthTestData());
							}

							if (clientOtherTestsBean.getDateOfAdmin() == null) {
								ps.setNull(3, Types.DATE);
							} else {
								ps.setDate(3, new java.sql.Date(clientOtherTestsBean.getDateOfAdmin().getTime()));
							}

							ps.setString(4, clientOtherTestsBean.getIsDeleted());
							ps.setInt(5, pathologicalDto.getUserId());
							ps.setInt(6, clientOtherTestsBean.getOtherTestId());

							ps.executeUpdate();
							ps.clearBatch();
							ps.clearParameters();
						}
					}
				}
			}

			conn.commit();

		} catch (Exception ex) {
			try {
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- saveUpdatePathologicalTest() - "
						+ ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				status = sStackTrace;
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
		return status;
	}

	@Override
	public List<PathologicalDto> getPathologicalTestList(Integer clientId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PathologicalDto> pathologicalDtos = null;
		List<ClientOtherTestsBean> clientOtherTestsList = null;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			pathologicalDtos = new ArrayList<PathologicalDto>();

			sql = "SELECT";
			sql += " pathology_seq_gen.seq_id,";
			sql += " thy_signifi.thyroid_signf_id, thy_signifi.CLIENT_ID, thy_signifi.thyro_tsh, thy_signifi.thyro_T3, thy_signifi.thyro_T4,";
			sql += " thy_signifi.date_of_entry, thy_signifi.cohort_year, thy_signifi.qtr_of_year, thy_signifi.seq_id,";
			sql += " diabets_signifi.diabetes_signf_id, diabets_signifi.CLIENT_ID, diabets_signifi.diabetes_rbs, diabets_signifi.diabetes_fbs, diabets_signifi.diabetes_ppbs,";
			sql += " diabets_signifi.date_of_entry, diabets_signifi.cohort_year, diabets_signifi.qtr_of_year, diabets_signifi.seq_id,";
			sql += " hemogl_signifi.hemoglobin_signf_id, hemogl_signifi.CLIENT_ID, hemogl_signifi.hb_percent, hemogl_signifi.esr, hemogl_signifi.hemoglobin_level,";
			sql += " hemogl_signifi.date_of_entry, hemogl_signifi.oth_lab_test, hemogl_signifi.oth_date_of_entry,";
			sql += " hemogl_signifi.cohort_year, hemogl_signifi.qtr_of_year, hemogl_signifi.seq_id,";
			sql += " hiv_test.hiv_id, hiv_test.client_id, hiv_test.hiv_data, hiv_test.date_of_admin, hiv_test.seq_id";
			sql += " FROM";
			sql += " (SELECT * FROM mis_client_pathology_seq_gen WHERE client_id = ?) pathology_seq_gen";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_thyroid_significance WHERE entry_flag = 'R' AND IS_DELETED = 'N') thy_signifi";
			sql += " ON pathology_seq_gen.seq_id = thy_signifi.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_diabetes_significance WHERE entry_flag = 'R' AND IS_DELETED = 'N') diabets_signifi";
			sql += " ON pathology_seq_gen.seq_id = diabets_signifi.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_hemoglobin_significance WHERE entry_flag = 'R' AND IS_DELETED = 'N') hemogl_signifi";
			sql += " ON pathology_seq_gen.seq_id = hemogl_signifi.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_hiv_test WHERE IS_DELETED = 'N') hiv_test";
			sql += " ON pathology_seq_gen.seq_id = hiv_test.seq_id";
			sql += " ORDER BY pathology_seq_gen.seq_id DESC";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientId);
			rs = ps.executeQuery();

			while (rs.next()) {

				PathologicalDto pathologicalDto = new PathologicalDto();

				ClientThyroidSignificanceBean clientThyroidSignificanceBean = new ClientThyroidSignificanceBean();

				clientThyroidSignificanceBean.setThyroidSignfId(rs.getInt("thyroid_signf_id"));
				clientThyroidSignificanceBean.setClientId(rs.getInt("thy_signifi.CLIENT_ID"));
				clientThyroidSignificanceBean.setThyroTsh(rs.getString("thyro_tsh"));
				clientThyroidSignificanceBean.setThyroT3(rs.getString("thyro_T3"));
				clientThyroidSignificanceBean.setThyroT4(rs.getString("thyro_T4"));
				clientThyroidSignificanceBean.setDateOfEntry(rs.getDate("thy_signifi.date_of_entry"));
				clientThyroidSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				clientThyroidSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));
				clientThyroidSignificanceBean.setSeqId(rs.getInt("thy_signifi.seq_id"));

				ClientDiabetesSignificanceBean clientDiabetesSignificanceBean = new ClientDiabetesSignificanceBean();

				clientDiabetesSignificanceBean.setDiabetesSignfId(rs.getInt("diabetes_signf_id"));
				clientDiabetesSignificanceBean.setClientId(rs.getInt("diabets_signifi.CLIENT_ID"));
				clientDiabetesSignificanceBean.setDiabetesRBS(rs.getString("diabetes_rbs"));
				clientDiabetesSignificanceBean.setDiabetesFBS(rs.getString("diabetes_fbs"));
				clientDiabetesSignificanceBean.setDiabetesPPBS(rs.getString("diabetes_ppbs"));
				clientDiabetesSignificanceBean.setDateOfEntry(rs.getDate("diabets_signifi.date_of_entry"));
				clientDiabetesSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				clientDiabetesSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));
				clientDiabetesSignificanceBean.setSeqId(rs.getInt("diabets_signifi.seq_id"));

				ClientHemoglobinSignificanceBean clientHemoglobinSignificanceBean = new ClientHemoglobinSignificanceBean();

				clientHemoglobinSignificanceBean.setHemoglobinSignfId(rs.getInt("hemoglobin_signf_id"));
				clientHemoglobinSignificanceBean.setClientId(rs.getInt("hemogl_signifi.CLIENT_ID"));
				clientHemoglobinSignificanceBean.setHbPercent(rs.getString("hb_percent"));
				clientHemoglobinSignificanceBean.setEsr(rs.getString("esr"));
				clientHemoglobinSignificanceBean.setHemoglobinLevel(rs.getString("hemoglobin_level"));
				clientHemoglobinSignificanceBean.setDateOfEntry(rs.getDate("hemogl_signifi.date_of_entry"));
				clientHemoglobinSignificanceBean.setOthLabTest(rs.getString("oth_lab_test"));
				clientHemoglobinSignificanceBean.setOthDateOfEntry(rs.getDate("oth_date_of_entry"));
				clientHemoglobinSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				clientHemoglobinSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));
				clientHemoglobinSignificanceBean.setSeqId(rs.getInt("hemogl_signifi.seq_id"));

				ClientHivTestBean clientHivTestBean = new ClientHivTestBean();

				clientHivTestBean.setHivId(rs.getInt("hiv_id"));
				clientHivTestBean.setClientId(rs.getInt("hiv_test.client_id"));
				clientHivTestBean.setHivData(rs.getString("hiv_data"));
				clientHivTestBean.setDateOfAdmin(rs.getDate("hiv_test.date_of_admin"));
				clientHivTestBean.setSeqId(rs.getInt("hiv_test.seq_id"));

				pathologicalDto.setSequenceNo(rs.getInt("pathology_seq_gen.seq_id"));
				pathologicalDto.setClientId(clientId);
				pathologicalDto.setClientThyroidSignificanceBean(clientThyroidSignificanceBean);
				pathologicalDto.setClientDiabetesSignificanceBean(clientDiabetesSignificanceBean);
				pathologicalDto.setClientHemoglobinSignificanceBean(clientHemoglobinSignificanceBean);
				pathologicalDto.setClientHivTestBean(clientHivTestBean);
				pathologicalDtos.add(pathologicalDto);
			}

			ps.clearBatch();
			ps.clearParameters();

			if (!pathologicalDtos.isEmpty()) {

				for (PathologicalDto pathological : pathologicalDtos) {

					sql = "SELECT other_test_id, client_id, oth_test_name, oth_test_data, date_of_admin, seq_id";
					sql += " FROM mis_client_other_tests WHERE seq_id = ? ORDER BY other_test_id DESC";

					ps = conn.prepareStatement(sql);
					ps.setInt(1, pathological.getSequenceNo());
					rs = ps.executeQuery();

					clientOtherTestsList = new ArrayList<ClientOtherTestsBean>();

					while (rs.next()) {

						ClientOtherTestsBean otherTestsBean = new ClientOtherTestsBean();
						otherTestsBean.setOtherTestId(rs.getInt("other_test_id"));
						otherTestsBean.setClientId(rs.getInt("client_id"));
						otherTestsBean.setOthTestName(rs.getString("oth_test_name"));
						otherTestsBean.setOthTestData(rs.getString("oth_test_data"));
						otherTestsBean.setDateOfAdmin(rs.getDate("date_of_admin"));
						otherTestsBean.setSeqId(rs.getInt("seq_id"));

						clientOtherTestsList.add(otherTestsBean);
					}

					if (!clientOtherTestsList.isEmpty()) {
						pathological.setListClientOthTest(clientOtherTestsList);
					}
				}
			}

			ps.clearBatch();
			ps.clearParameters();

			conn.commit();
		} catch (Exception ex) {
			try {
				pathologicalDtos = null;
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- getPathologicalTestList() - "
						+ ex.getMessage());
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
		return pathologicalDtos;
	}

	@Override
	public LinkedHashMap<Integer, ArrayList<PathologicalDto>> getAllPathologicalTestListDateWise(Integer homeId,
			Date fromDate, Date toDate) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PathologicalDto> pathologicalDtos = null;
		LinkedHashMap<Integer, ArrayList<PathologicalDto>> clientWisePathList = null;
		Integer _clientId = 0;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = " SELECT";
			sql += " (SELECT CLIENT_NAME FROM mis_client_master WHERE CLIENT_ID = pathology_seq_gen.cid) client_name,";
			sql += " (SELECT CLIENT_UID FROM mis_client_master WHERE CLIENT_ID = pathology_seq_gen.cid) CLIENT_UID,";
			sql += " (SELECT DATE_OF_ENTRY FROM mis_client_master WHERE CLIENT_ID = pathology_seq_gen.cid) clint_date_of_entry,";
			sql += " pathology_seq_gen.seq_id, pathology_seq_gen.cid,";
			sql += " thy_signifi.thyroid_signf_id, thy_signifi.thyro_tsh, thy_signifi.thyro_T3, thy_signifi.thyro_T4,";
			sql += " thy_signifi.date_of_entry AS thy_date_of_entry,";
			sql += " diabets_signifi.diabetes_signf_id, diabets_signifi.diabetes_rbs, diabets_signifi.diabetes_fbs, diabets_signifi.diabetes_ppbs,";
			sql += " diabets_signifi.date_of_entry AS diabets_signifi_date_of_entry,";
			sql += " hemogl_signifi.hemoglobin_signf_id, hemogl_signifi.hb_percent, hemogl_signifi.esr, hemogl_signifi.hemoglobin_level,";
			sql += " hemogl_signifi.date_of_entry AS hemogl_signifi_date_of_entry, hemogl_signifi.oth_lab_test, hemogl_signifi.oth_date_of_entry,";
			sql += " hiv_test.hiv_id, hiv_test.hiv_data, hiv_test.date_of_admin";
			sql += " FROM";
			sql += " (SELECT seq_gen.seq_id, cm.CLIENT_ID AS cid FROM mis_client_pathology_seq_gen seq_gen,";
			sql += " mis_client_master cm WHERE seq_gen.client_id = cm.CLIENT_ID AND HOME_ID = ?) pathology_seq_gen ";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_thyroid_significance WHERE entry_flag = 'R' AND IS_DELETED = 'N' AND date_of_entry BETWEEN  ? AND ?) thy_signifi";
			sql += " ON pathology_seq_gen.seq_id = thy_signifi.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_diabetes_significance WHERE entry_flag = 'R' AND IS_DELETED = 'N' AND date_of_entry BETWEEN  ? AND ?) diabets_signifi";
			sql += " ON pathology_seq_gen.seq_id = diabets_signifi.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_hemoglobin_significance WHERE entry_flag = 'R' AND IS_DELETED = 'N' AND date_of_entry BETWEEN  ? AND ?) hemogl_signifi";
			sql += " ON pathology_seq_gen.seq_id = hemogl_signifi.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_hiv_test WHERE IS_DELETED = 'N' AND date_of_admin BETWEEN  ? AND ? ) hiv_test";
			sql += " ON pathology_seq_gen.seq_id = hiv_test.seq_id";
			sql += " WHERE NOT (ISNULL(thyroid_signf_id) AND ISNULL(diabetes_signf_id) AND ISNULL(hemoglobin_signf_id) AND ISNULL(hiv_id))";
			sql += " ORDER BY pathology_seq_gen.cid DESC, pathology_seq_gen.seq_id DESC";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, homeId);
			ps.setDate(2, new java.sql.Date(fromDate.getTime()));
			ps.setDate(3, new java.sql.Date(toDate.getTime()));
			ps.setDate(4, new java.sql.Date(fromDate.getTime()));
			ps.setDate(5, new java.sql.Date(toDate.getTime()));
			ps.setDate(6, new java.sql.Date(fromDate.getTime()));
			ps.setDate(7, new java.sql.Date(toDate.getTime()));
			ps.setDate(8, new java.sql.Date(fromDate.getTime()));
			ps.setDate(9, new java.sql.Date(toDate.getTime()));

			rs = ps.executeQuery();

			clientWisePathList = new LinkedHashMap<Integer, ArrayList<PathologicalDto>>();

			while (rs.next()) {

				_clientId = rs.getInt("cid");

				PathologicalDto pathologicalDto = new PathologicalDto();

				ClientThyroidSignificanceBean clientThyroidSignificanceBean = new ClientThyroidSignificanceBean();

				clientThyroidSignificanceBean.setThyroidSignfId(rs.getInt("thyroid_signf_id"));
				// clientThyroidSignificanceBean.setClientId(rs.getInt("thy_signifi.CLIENT_ID"));
				clientThyroidSignificanceBean.setThyroTsh(rs.getString("thyro_tsh"));
				clientThyroidSignificanceBean.setThyroT3(rs.getString("thyro_T3"));
				clientThyroidSignificanceBean.setThyroT4(rs.getString("thyro_T4"));
				clientThyroidSignificanceBean.setDateOfEntry(rs.getDate("thy_date_of_entry"));
				// clientThyroidSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				// clientThyroidSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));
				// clientThyroidSignificanceBean.setSeqId(rs.getInt("thy_signifi.seq_id"));

				ClientDiabetesSignificanceBean clientDiabetesSignificanceBean = new ClientDiabetesSignificanceBean();

				clientDiabetesSignificanceBean.setDiabetesSignfId(rs.getInt("diabetes_signf_id"));
				// clientDiabetesSignificanceBean.setClientId(rs.getInt("diabets_signifi.CLIENT_ID"));
				clientDiabetesSignificanceBean.setDiabetesRBS(rs.getString("diabetes_rbs"));
				clientDiabetesSignificanceBean.setDiabetesFBS(rs.getString("diabetes_fbs"));
				clientDiabetesSignificanceBean.setDiabetesPPBS(rs.getString("diabetes_ppbs"));
				clientDiabetesSignificanceBean.setDateOfEntry(rs.getDate("diabets_signifi_date_of_entry"));
				// clientDiabetesSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				// clientDiabetesSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));
				// clientDiabetesSignificanceBean.setSeqId(rs.getInt("diabets_signifi.seq_id"));

				ClientHemoglobinSignificanceBean clientHemoglobinSignificanceBean = new ClientHemoglobinSignificanceBean();

				clientHemoglobinSignificanceBean.setHemoglobinSignfId(rs.getInt("hemoglobin_signf_id"));
				// clientHemoglobinSignificanceBean.setClientId(rs.getInt("hemogl_signifi.CLIENT_ID"));
				clientHemoglobinSignificanceBean.setHbPercent(rs.getString("hb_percent"));
				clientHemoglobinSignificanceBean.setEsr(rs.getString("esr"));
				clientHemoglobinSignificanceBean.setHemoglobinLevel(rs.getString("hemoglobin_level"));
				clientHemoglobinSignificanceBean.setDateOfEntry(rs.getDate("hemogl_signifi_date_of_entry"));
				clientHemoglobinSignificanceBean.setOthLabTest(rs.getString("oth_lab_test"));
				clientHemoglobinSignificanceBean.setOthDateOfEntry(rs.getDate("oth_date_of_entry"));
				// clientHemoglobinSignificanceBean.setCohortYear(rs.getString("cohort_year"));
				// clientHemoglobinSignificanceBean.setQtrOfYear(rs.getInt("qtr_of_year"));
				// clientHemoglobinSignificanceBean.setSeqId(rs.getInt("hemogl_signifi.seq_id"));

				ClientHivTestBean clientHivTestBean = new ClientHivTestBean();

				clientHivTestBean.setHivId(rs.getInt("hiv_id"));
				// clientHivTestBean.setClientId(rs.getInt("hiv_test.client_id"));
				clientHivTestBean.setHivData(rs.getString("hiv_data"));
				clientHivTestBean.setDateOfAdmin(rs.getDate("date_of_admin"));
				// clientHivTestBean.setSeqId(rs.getInt("hiv_test.seq_id"));

				pathologicalDto.setSequenceNo(rs.getInt("pathology_seq_gen.seq_id"));
				pathologicalDto.setClientThyroidSignificanceBean(clientThyroidSignificanceBean);
				pathologicalDto.setClientDiabetesSignificanceBean(clientDiabetesSignificanceBean);
				pathologicalDto.setClientHemoglobinSignificanceBean(clientHemoglobinSignificanceBean);
				pathologicalDto.setClientHivTestBean(clientHivTestBean);

				if (clientWisePathList.get(_clientId) == null) {

					pathologicalDtos = new ArrayList<PathologicalDto>();
					ClientMasterBean clientMasterBean = new ClientMasterBean();

					clientMasterBean.setClientName(rs.getString("client_name"));
					clientMasterBean.setClientUid(rs.getString("CLIENT_UID"));
					clientMasterBean.setDateOfEntry(rs.getDate("clint_date_of_entry"));
					pathologicalDto.setClientMasterBean(clientMasterBean);
					pathologicalDtos.add(pathologicalDto);
					clientWisePathList.put(_clientId, pathologicalDtos);

				} else {
					pathologicalDtos = clientWisePathList.get(_clientId);
					pathologicalDtos.add(pathologicalDto);
				}

			}

			ps.clearBatch();
			ps.clearParameters();

			conn.commit();
		} catch (Exception ex) {
			try {
				clientWisePathList = null;
				logger.info("Problem in Class - ClientHealthDaoImpl ~~ method- getAllPathologicalTestListDateWise() - "
						+ ex.getMessage());
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
		return clientWisePathList;
	}

	@Override
	public LinkedHashMap<Integer, ArrayList<PsychometryTestDto>> getAllClientPsychometryTestListDateWise(Integer homeId,
			Date fromDate, Date toDate) {

		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PsychometryTestDto> psychometryTestDtos = null;
		LinkedHashMap<Integer, ArrayList<PsychometryTestDto>> clientPsychometryTestList = null;
		Integer _clientId = 0;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = " SELECT";
			sql += " psycho_seq_gen.seq_id, psycho_seq_gen.cid,";
			sql += " (SELECT CLIENT_NAME FROM mis_client_master WHERE CLIENT_ID = psycho_seq_gen.cid) client_name,";
			sql += " (SELECT CLIENT_UID FROM mis_client_master WHERE CLIENT_ID = psycho_seq_gen.cid) CLIENT_UID,";
			sql += " (SELECT DATE_OF_ENTRY FROM mis_client_master WHERE CLIENT_ID = psycho_seq_gen.cid) clint_date_of_entry,";
			sql += " mis_client_psycho_gaf.psycho_gaf_id, mis_client_psycho_gaf.gaf_score,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = mis_client_psycho_gaf.gaf_score) gafScoreStr,";
			sql += " mis_client_psycho_gaf.date_of_entry,";
			sql += " psycho_ids.psycho_ideas_id, psycho_ids.sc, psycho_ids.ia,";
			sql += " psycho_ids.c_and_u, psycho_ids.wrk, psycho_ids.doi, psycho_ids.gds, psycho_ids.gis, psycho_ids.ideas_status,";
			sql += " psycho_ids.date_of_entry,";
			sql += " psycho_lsp.psycho_lsp_id, psycho_lsp.lsp_score, psycho_lsp.lsp_class,";
			sql += " psycho_lsp.date_of_entry,";
			sql += " psycho_panss.psycho_panss_id, psycho_panss.ps, psycho_panss.ns, psycho_panss.gp, psycho_panss.total,";
			sql += " psycho_panss.date_of_entry";
			sql += " FROM";
			sql += " (SELECT seq_gen.seq_id, cm.CLIENT_ID AS cid FROM  mis_client_psycho_seq_gen seq_gen,";
			sql += "  mis_client_master cm WHERE seq_gen.client_id = cm.CLIENT_ID AND HOME_ID = ?) psycho_seq_gen";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_psycho_gaf WHERE entry_flag='R' AND IS_DELETED = 'N' AND date_of_entry BETWEEN  ? AND ?) mis_client_psycho_gaf";
			sql += " ON psycho_seq_gen.seq_id = mis_client_psycho_gaf.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_psycho_ideas WHERE entry_flag='R' AND IS_DELETED = 'N' AND date_of_entry BETWEEN  ? AND ?) psycho_ids";
			sql += " ON psycho_seq_gen.seq_id = psycho_ids.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_psycho_lsp WHERE entry_flag='R' AND IS_DELETED = 'N' AND date_of_entry BETWEEN  ? AND ?) psycho_lsp";
			sql += " ON psycho_seq_gen.seq_id = psycho_lsp.seq_id";
			sql += " LEFT OUTER JOIN";
			sql += " (SELECT * FROM mis_client_psycho_panss WHERE entry_flag='R' AND IS_DELETED = 'N' AND date_of_entry BETWEEN  ? AND ?) psycho_panss";
			sql += " ON psycho_seq_gen.seq_id = psycho_panss.seq_id";
			sql += " WHERE NOT (ISNULL(psycho_gaf_id) AND ISNULL(psycho_ideas_id) AND ISNULL(psycho_lsp_id) AND ISNULL(psycho_panss_id))";
			sql += " ORDER BY psycho_seq_gen.cid DESC, psycho_seq_gen.seq_id DESC";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, homeId);
			ps.setDate(2, new java.sql.Date(fromDate.getTime()));
			ps.setDate(3, new java.sql.Date(toDate.getTime()));
			ps.setDate(4, new java.sql.Date(fromDate.getTime()));
			ps.setDate(5, new java.sql.Date(toDate.getTime()));
			ps.setDate(6, new java.sql.Date(fromDate.getTime()));
			ps.setDate(7, new java.sql.Date(toDate.getTime()));
			ps.setDate(8, new java.sql.Date(fromDate.getTime()));
			ps.setDate(9, new java.sql.Date(toDate.getTime()));

			rs = ps.executeQuery();

			clientPsychometryTestList = new LinkedHashMap<Integer, ArrayList<PsychometryTestDto>>();

			while (rs.next()) {

				_clientId = rs.getInt("cid");

				ClientPsychoGafBean clientPsychoGafBean = new ClientPsychoGafBean();

				clientPsychoGafBean.setPsychoGafId(rs.getInt("mis_client_psycho_gaf.psycho_gaf_id"));
				// clientPsychoGafBean.setClientId(rs.getInt("mis_client_psycho_gaf.client_id"));
				clientPsychoGafBean.setGafScore(rs.getString("mis_client_psycho_gaf.gaf_score") != null
						? Integer.parseInt(rs.getString("mis_client_psycho_gaf.gaf_score"))
						: null);
				/*
				 * clientPsychoGafBean.setGafClass(rs.getString(
				 * "mis_client_psycho_gaf.gaf_class") != null ?
				 * Integer.parseInt(rs.getString("mis_client_psycho_gaf.gaf_class")) : null);
				 */
				clientPsychoGafBean.setGafScoreStr(rs.getString("gafScoreStr"));
				clientPsychoGafBean.setDateOfEntry(rs.getDate("mis_client_psycho_gaf.date_of_entry"));
				// clientPsychoGafBean.setCohortYear(rs.getString("mis_client_psycho_gaf.cohort_year"));
				// clientPsychoGafBean.setQtrOfYear(rs.getInt("mis_client_psycho_gaf.qtr_of_year"));
				// clientPsychoGafBean.setSeqId(rs.getInt("mis_client_psycho_gaf.seq_id"));

				/****************************************************************************/

				ClientPsychoIdeasBean clientPsychoIdeasBean = new ClientPsychoIdeasBean();

				clientPsychoIdeasBean.setPsychoIdeasId(rs.getInt("psycho_ids.psycho_ideas_id"));
				// clientPsychoIdeasBean.setClientId(rs.getInt("psycho_ids.client_id"));
				clientPsychoIdeasBean.setSc(
						rs.getString("psycho_ids.sc") != null ? Integer.parseInt(rs.getString("psycho_ids.sc")) : null);
				clientPsychoIdeasBean.setIa(
						rs.getString("psycho_ids.ia") != null ? Integer.parseInt(rs.getString("psycho_ids.ia")) : null);
				clientPsychoIdeasBean.setC_and_u(rs.getString("psycho_ids.c_and_u") != null
						? Integer.parseInt(rs.getString("psycho_ids.c_and_u"))
						: null);
				clientPsychoIdeasBean.setWrk(
						rs.getString("psycho_ids.wrk") != null ? Integer.parseInt(rs.getString("psycho_ids.wrk"))
								: null);
				clientPsychoIdeasBean.setDoi(
						rs.getString("psycho_ids.doi") != null ? Integer.parseInt(rs.getString("psycho_ids.doi"))
								: null);

				clientPsychoIdeasBean.setGis(
						rs.getString("psycho_ids.gis") != null ? Integer.parseInt(rs.getString("psycho_ids.gis"))
								: null);
				clientPsychoIdeasBean.setIdeasStatus(rs.getString("psycho_ids.ideas_status"));
				clientPsychoIdeasBean.setDateOfEntry(rs.getDate("psycho_ids.date_of_entry"));
				// clientPsychoIdeasBean.setCohortYear(rs.getString("psycho_ids.cohort_year"));
				// clientPsychoIdeasBean.setQtrOfYear(rs.getInt("psycho_ids.qtr_of_year"));
				// clientPsychoIdeasBean.setSeqId(rs.getInt("psycho_ids.seq_id"));

				/************************************************************************/
				ClientPsychoLspBean clientPsychoLspBean = new ClientPsychoLspBean();

				clientPsychoLspBean.setPsychoLspId(rs.getInt("psycho_lsp.psycho_lsp_id"));
				// clientPsychoLspBean.setClientId(rs.getInt("psycho_lsp.client_id"));
				clientPsychoLspBean.setLspScore(rs.getString("psycho_lsp.lsp_score") != null
						? Integer.parseInt(rs.getString("psycho_lsp.lsp_score"))
						: null);
				clientPsychoLspBean.setLspClass(rs.getString("psycho_lsp.lsp_class") != null
						? Integer.parseInt(rs.getString("psycho_lsp.lsp_class"))
						: null);
				clientPsychoLspBean.setDateOfEntry(rs.getDate("psycho_lsp.date_of_entry"));
				// clientPsychoLspBean.setCohortYear(rs.getString("psycho_lsp.cohort_year"));
				// clientPsychoLspBean.setQtrOfYear(rs.getInt("psycho_lsp.qtr_of_year"));
				// clientPsychoLspBean.setSeqId(rs.getInt("psycho_lsp.seq_id"));

				/************************************************************************/

				ClientPsychoPanssBean clientPsychoPanssBean = new ClientPsychoPanssBean();

				clientPsychoPanssBean.setPsychoPanssId(rs.getInt("psycho_panss.psycho_panss_id"));
				// clientPsychoPanssBean.setClientId(rs.getInt("psycho_panss.client_id"));
				clientPsychoPanssBean.setPs(
						rs.getString("psycho_panss.ps") != null ? Integer.parseInt(rs.getString("psycho_panss.ps"))
								: null);
				clientPsychoPanssBean.setNs(
						rs.getString("psycho_panss.ns") != null ? Integer.parseInt(rs.getString("psycho_panss.ns"))
								: null);
				clientPsychoPanssBean.setGp(
						rs.getString("psycho_panss.gp") != null ? Integer.parseInt(rs.getString("psycho_panss.gp"))
								: null);
				clientPsychoPanssBean.setTotal(rs.getString("psycho_panss.total") != null
						? Integer.parseInt(rs.getString("psycho_panss.total"))
						: null);
				clientPsychoPanssBean.setDateOfEntry(rs.getDate("psycho_panss.date_of_entry"));
				// clientPsychoPanssBean.setCohortYear(rs.getString("psycho_panss.cohort_year"));
				// clientPsychoPanssBean.setQtrOfYear(rs.getInt("psycho_panss.qtr_of_year"));
				// clientPsychoPanssBean.setSeqId(rs.getInt("psycho_panss.seq_id"));

				PsychometryTestDto psychometryTestDto = new PsychometryTestDto();

				psychometryTestDto.setClientPsychoGafBean(clientPsychoGafBean);
				psychometryTestDto.setClientPsychoIdeasBean(clientPsychoIdeasBean);
				psychometryTestDto.setClientPsychoLspBean(clientPsychoLspBean);
				psychometryTestDto.setClientPsychoPanss(clientPsychoPanssBean);
				// psychometryTestDto.setClientId(clientId);
				psychometryTestDto.setSequenceNo(rs.getInt("psycho_seq_gen.seq_id"));

				if (clientPsychometryTestList.get(_clientId) == null) {
					psychometryTestDtos = new ArrayList<PsychometryTestDto>();
					ClientMasterBean clientMasterBean = new ClientMasterBean();

					clientMasterBean.setClientName(rs.getString("client_name"));
					clientMasterBean.setClientUid(rs.getString("CLIENT_UID"));
					clientMasterBean.setDateOfEntry(rs.getDate("clint_date_of_entry"));
					psychometryTestDto.setClientMasterBean(clientMasterBean);
					psychometryTestDtos.add(psychometryTestDto);
					clientPsychometryTestList.put(_clientId, psychometryTestDtos);

				} else {
					psychometryTestDtos = clientPsychometryTestList.get(_clientId);
					psychometryTestDtos.add(psychometryTestDto);
				}
			}

			conn.commit();
		} catch (Exception ex) {
			try {
				clientPsychometryTestList = null;
				logger.info(
						"Problem in Class - ClientHealthDaoImpl ~~ method- getAllClientPsychometryTestListDateWise() - "
								+ ex.getMessage());
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
		return clientPsychometryTestList;
	}

}
