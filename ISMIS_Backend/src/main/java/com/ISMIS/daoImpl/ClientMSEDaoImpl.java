package com.ISMIS.daoImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ISMIS.dao.ClientMSEDao;
import com.ISMIS.dto.ClientMseDto;
import com.ISMIS.model.ClientMasterBean;
import com.ISMIS.model.ClientMseBean;
import com.ISMIS.model.ClientMseTemplateBean;

@Repository
public class ClientMSEDaoImpl extends CommonDaoImpl implements ClientMSEDao {

	static Logger logger = LogManager.getLogger(ClientMSEDaoImpl.class);

	@Override
	public Map<Integer, ArrayList<ClientMseTemplateBean>> getMseFormLabelsTemplate() {
		Connection conn = null;
		String sql = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		HashMap<Integer, ArrayList<ClientMseTemplateBean>> allItemListCaptionWise = new HashMap<Integer, ArrayList<ClientMseTemplateBean>>();
		int caption_level = 0;
		ArrayList<ClientMseTemplateBean> templateList = null;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			cs = conn.prepareCall("{CALL recur_mis_mse_format_data()}");
			rs = cs.executeQuery();

			while (rs.next()) {

				ClientMseTemplateBean mseTemplateBean = new ClientMseTemplateBean();
				mseTemplateBean.setMental_status_lbl_id(rs.getInt("mental_status_lbl_id"));
				mseTemplateBean.setLbl_name(rs.getString("lbl_name"));
				mseTemplateBean.setMental_status_lbl_parent_id(rs.getInt("mental_status_lbl_parent_id"));
				mseTemplateBean.setOption_type(rs.getString("option_type"));
				mseTemplateBean.setHas_child(rs.getString("has_child"));
				caption_level = rs.getInt("caption_level");
				mseTemplateBean.setCaption_level(caption_level);

				if (allItemListCaptionWise.get(caption_level) != null) {
					templateList = allItemListCaptionWise.get(caption_level);
					templateList.add(mseTemplateBean);
				} else {
					templateList = new ArrayList<ClientMseTemplateBean>();
					templateList.add(mseTemplateBean);
					allItemListCaptionWise.put(caption_level, templateList);
				}
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				allItemListCaptionWise = null;
				logger.info("Problem in Class - ClientMSEDaoImpl ~~ method- getMseFormLabelsTemplate() - "
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
		return allItemListCaptionWise;
	}

	@Override
	public Integer getMaxMseCaptionLevel() {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer max_val = 0;
		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			sql = "SELECT MAX(caption_level) AS v_max FROM mis_mse_format";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				max_val = rs.getInt("v_max");
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				max_val = null;
				logger.info(
						"Problem in Class - ClientMSEDaoImpl ~~ method- getMaxMseCaptionLevel() - " + ex.getMessage());
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
		return max_val;
	}

	@Override
	public String saveUpdateMseResult(ClientMseDto clientMseDto) {
		Connection conn = null;
		String sql = null;
		String insert_sql = null;
		PreparedStatement ps = null;
		PreparedStatement insert_ps = null;
		ResultSet rs = null;
		String status = null;
		List<ClientMseBean> clientMseBean = clientMseDto.getClientMseBean();
		int count = 0;

		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			/****** Query to check MSE exits against a client ******/

			sql = "SELECT COUNT(1) as cnt FROM  mis_mse_result";
			sql += " WHERE client_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientMseDto.getClientId());
			rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt("cnt");
			}

			ps.clearBatch();
			ps.clearParameters();

			if (count > 0) {
				sql = "DELETE FROM  mis_mse_result";
				sql += " WHERE client_id = ?";

				ps = conn.prepareStatement(sql);
				ps.setInt(1, clientMseDto.getClientId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();
			}

			/************* Query to Insert MSE against a client ************/

			insert_sql = "INSERT INTO mis_mse_result (";
			insert_sql += " client_id, mental_status_lbl_Parent_id,";
			insert_sql += " mental_status_lbl_id, lbl_status,";
			insert_sql += " mse_text_value) VALUES (?, ?, ?, ?, ?)";

			insert_ps = conn.prepareStatement(insert_sql);

			for (ClientMseBean mseVal : clientMseBean) {
				// insert new mse against client
				insert_ps.setInt(1, mseVal.getClientId());
				insert_ps.setInt(2, mseVal.getParentId());
				insert_ps.setInt(3, mseVal.getCurrent_lbl_Id());
				insert_ps.setString(4, mseVal.getStatus());

				if (mseVal.getTxtValue() == null) {
					insert_ps.setNull(5, Types.VARCHAR);
				} else {
					insert_ps.setString(5, mseVal.getTxtValue());
				}

				insert_ps.executeUpdate();
				insert_ps.clearBatch();
				insert_ps.clearParameters();

			}

			if (clientMseDto.getSummeryTxt() != null) {
				this.saveUpdateMseSummery(conn, clientMseDto.getClientId(), clientMseDto.getSummeryTxt());
			}

			if (clientMseDto.getNewEntry()) {

				sql = "UPDATE mis_client_master";
				sql += " SET IS_MSE = ?";
				sql += " WHERE client_id = ?";

				ps = conn.prepareStatement(sql);
				ps.setString(1, "Y");
				ps.setInt(2, clientMseDto.getClientId());

				ps.executeUpdate();
				ps.clearBatch();
				ps.clearParameters();

			}

			conn.commit();
		} catch (Exception ex) {
			try {
				status = ex.getMessage();
				logger.info(
						"Problem in Class - ClientMSEDaoImpl ~~ method- saveUpdateMseResult() - " + ex.getMessage());
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
		return status;
	}

	private void saveUpdateMseSummery(Connection conn, Integer clinetId, String summeryTxt) throws Exception {

		int count = 0;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		sql = "SELECT COUNT(1) AS cnt FROM mis_mse_summary";
		sql += " WHERE client_id = ?";

		ps = conn.prepareStatement(sql);

		ps.setInt(1, clinetId);
		rs = ps.executeQuery();

		if (rs.next()) {
			count = rs.getInt("cnt");
		}

		ps.clearBatch();
		ps.clearParameters();

		if (count > 0) {
			// update summary txt
			sql = "UPDATE mis_mse_summary";
			sql += " SET summary_text = ?";
			sql += " WHERE client_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, summeryTxt);
			ps.setInt(2, clinetId);
			ps.executeUpdate();
		} else {
			// insert summary txt
			sql = "INSERT INTO mis_mse_summary (";
			sql += " client_id, summary_text) VALUES (?, ?)";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clinetId);
			ps.setString(2, summeryTxt);
			ps.executeUpdate();
		}
	}

	@Override
	public Map<Integer, ArrayList<ClientMseTemplateBean>> getClientMseResult(Integer clientId) {

		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<Integer, ArrayList<ClientMseTemplateBean>> allItemListCaptionWise = new HashMap<Integer, ArrayList<ClientMseTemplateBean>>();
		int caption_level = 0;
		ArrayList<ClientMseTemplateBean> templateList = null;

		try {

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = "SELECT mse_format.mental_status_lbl_id,";
			sql += " mse_format.mental_status_lbl_parent_id,";
			sql += " mse_format.lbl_name, mse_format.option_type,";
			sql += " mse_format.has_child, mse_format.caption_level,";
			sql += " mse_result.lbl_status, mse_result.mse_text_value,";
			sql += " mse_result.client_id";
			sql += " FROM mis_mse_format mse_format LEFT OUTER JOIN";
			sql += " mis_mse_result mse_result ON mse_format.mental_status_lbl_id = mse_result.mental_status_lbl_id";
			sql += " AND mse_result.client_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientId);
			rs = ps.executeQuery();

			while (rs.next()) {

				ClientMseTemplateBean mseTemplateBean = new ClientMseTemplateBean();
				mseTemplateBean.setMental_status_lbl_id(rs.getInt("mental_status_lbl_id"));
				mseTemplateBean.setLbl_name(rs.getString("lbl_name"));
				mseTemplateBean.setMental_status_lbl_parent_id(rs.getInt("mental_status_lbl_parent_id"));
				mseTemplateBean.setOption_type(rs.getString("option_type"));
				mseTemplateBean.setHas_child(rs.getString("has_child"));
				mseTemplateBean.setLbl_status(rs.getString("lbl_status"));
				mseTemplateBean.setMse_text_value(rs.getString("mse_text_value"));
				caption_level = rs.getInt("caption_level");
				mseTemplateBean.setCaption_level(caption_level);

				if (allItemListCaptionWise.get(caption_level) != null) {
					templateList = allItemListCaptionWise.get(caption_level);
					templateList.add(mseTemplateBean);
				} else {
					templateList = new ArrayList<ClientMseTemplateBean>();
					templateList.add(mseTemplateBean);
					allItemListCaptionWise.put(caption_level, templateList);
				}
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				allItemListCaptionWise = null;
				logger.info("Problem in Class - ClientMSEDaoImpl ~~ method- getClientMseResult() - " + ex.getMessage());
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
		return allItemListCaptionWise;
	}

	@Override
	public String getMseSummary(Integer clientId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String summary = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = "SELECT summary_text FROM mis_mse_summary WHERE client_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientId);
			rs = ps.executeQuery();

			if (rs.next()) {
				summary = rs.getString("summary_text");
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				summary = null;
				logger.info("Problem in Class - ClientMSEDaoImpl ~~ method- getMseSummary() - " + ex.getMessage());
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
		return summary;
	}

	@Override
	public List<ClientMseBean> getClientMseResultList(Integer clientId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ClientMseBean> mseBeansList = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = "SELECT client_id, mental_status_lbl_Parent_id,";
			sql += " mental_status_lbl_id, lbl_status, mse_text_value";
			sql += " FROM mis_mse_result WHERE client_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, clientId);
			rs = ps.executeQuery();

			mseBeansList = new ArrayList<ClientMseBean>();

			while (rs.next()) {
				ClientMseBean mseBean = new ClientMseBean();
				mseBean.setClientId(rs.getInt("client_id"));
				mseBean.setParentId(rs.getInt("mental_status_lbl_Parent_id"));
				mseBean.setCurrent_lbl_Id(rs.getInt("mental_status_lbl_id"));
				mseBean.setStatus(rs.getString("lbl_status"));
				mseBean.setTxtValue(rs.getString("mse_text_value"));
				mseBeansList.add(mseBean);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				mseBeansList = null;
				logger.info(
						"Problem in Class - ClientMSEDaoImpl ~~ method- getClientMseResultList() - " + ex.getMessage());
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
		return mseBeansList;
	}

	@Override
	public List<ClientMasterBean> getClientMsePendinglist(Integer homeId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ClientMasterBean> clientPendingList = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			clientPendingList = new ArrayList<ClientMasterBean>();

			sql = "SELECT CLIENT_NAME, CLIENT_ID, HOME_ID, CLIENT_UID, DATE_OF_ENTRY, exit_date, COHORT_YEAR, SEX, AGE, POLICE_STATION FROM mis_client_master";
			sql += " WHERE HOME_ID = ? AND IS_DELETED = 'N' AND IS_MSE = 'N'";
			sql += " ORDER BY CLIENT_ID DESC";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, homeId);
			rs = ps.executeQuery();

			while (rs.next()) {
				ClientMasterBean masterBean = new ClientMasterBean();
				masterBean.setClientName(rs.getString("CLIENT_NAME"));
				masterBean.setClientId(rs.getInt("CLIENT_ID"));
				masterBean.setHomeId(rs.getInt("HOME_ID"));
				masterBean.setClientUid(rs.getString("CLIENT_UID"));
				masterBean.setDateOfEntry(rs.getDate("DATE_OF_ENTRY"));
				masterBean.setExitDate(rs.getDate("exit_date"));
				masterBean.setCohortYear(rs.getString("COHORT_YEAR"));
				masterBean.setSex(rs.getInt("SEX"));
				masterBean.setAge(rs.getInt("AGE"));
				masterBean.setPoliceStation(rs.getString("POLICE_STATION"));
				clientPendingList.add(masterBean);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				clientPendingList = null;
				logger.info(
						"Problem in Class - ClientDaoImpl ~~ method- getClientMsePendinglist() - " + ex.getMessage());
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
		return clientPendingList;
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
			sql += " WHERE HOME_ID = ? AND IS_DELETED = 'N' AND  IS_MSE = 'Y'";
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
				logger.info("Problem in Class - ClientMSEDaoImpl ~~ method- getClientUidList() - " + ex.getMessage());
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
			sql += " WHERE HOME_ID = ? AND IS_DELETED = 'N' AND  IS_MSE = 'Y'";
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
				logger.info("Problem in Class - ClientMSEDaoImpl ~~ method- getClientNameList() - " + ex.getMessage());
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

			sql = " SELECT CLIENT_ID, CLIENT_NAME, HOME_ID, CLIENT_UID, SEX, AGE, DATE_OF_ENTRY, COHORT_YEAR, POLICE_STATION,";
			sql += " (SELECT attribute_desc FROM mis_category_attributes WHERE category_attribute_id = cm.SEX) sexStr";
			sql += " FROM mis_client_master cm";
			sql += " WHERE HOME_ID = ? AND IS_DELETED = 'N' AND  IS_MSE = 'Y'";

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
				masterBean.setSex(rs.getInt("SEX"));
				masterBean.setDateOfEntry(rs.getDate("DATE_OF_ENTRY"));
				masterBean.setPoliceStation(rs.getString("POLICE_STATION"));
				masterBean.setSexStr(rs.getString("sexStr"));
				masterBean.setAge(rs.getInt("AGE"));
				clientUidList.add(masterBean);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				clientUidList = null;
				logger.info(
						"Problem in Class - ClientMSEDaoImpl ~~ method- getClientBasicInfoList() - " + ex.getMessage());
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
}
