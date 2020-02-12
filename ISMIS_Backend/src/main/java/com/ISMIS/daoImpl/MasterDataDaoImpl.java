package com.ISMIS.daoImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ISMIS.dao.MasterDataDao;
import com.ISMIS.model.AgeClassBean;
import com.ISMIS.model.AttributeBean;
import com.ISMIS.model.ClientBmiClassBean;
import com.ISMIS.model.HomeBean;

@Repository
public class MasterDataDaoImpl extends CommonDaoImpl implements MasterDataDao {

	static Logger logger = LogManager.getLogger(MasterDataDaoImpl.class);

	@Override
	public List<HomeBean> getHomeList() {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<HomeBean> homeList = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			homeList = new ArrayList<HomeBean>();
			sql = "SELECT home_id, home_name, home_address, contact_no FROM mis_home_master";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				HomeBean hbean = new HomeBean();
				hbean.setHome_id(rs.getInt("home_id"));
				hbean.setHome_name(rs.getString("home_name"));
				hbean.setHome_address(rs.getString("home_address"));
				hbean.setContact_no(rs.getString("contact_no"));
				homeList.add(hbean);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				homeList = null;
				logger.info("Problem in Class - MasterDataDaoImpl ~~ method- getHomeList() - " + ex.getMessage());
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
		return homeList;
	}

	@Override
	public List<AttributeBean> getCategoryWiseAttrList(int categoryId) {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AttributeBean> categoryList = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			categoryList = new ArrayList<AttributeBean>();

			sql = "SELECT cat_att.category_attribute_id, cat_att.attribute_desc";
			sql += " FROM mis_category_master cm, mis_category_attributes cat_att";
			sql += " WHERE cm.category_id = cat_att.category_id ";
			sql += " AND cm.category_id = ?";
			sql += " AND cat_att.IS_DELETED = ?";
			sql += " ORDER BY attribute_id";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, categoryId);
			ps.setString(2, "N");
			rs = ps.executeQuery();

			while (rs.next()) {
				AttributeBean attributeBean = new AttributeBean();
				attributeBean.setCategoryAttributeId(rs.getInt("category_attribute_id"));
				attributeBean.setAttrDesc(rs.getString("attribute_desc"));
				categoryList.add(attributeBean);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				categoryList = null;
				logger.info("Problem in Class - MasterDataDaoImpl ~~ method- getHomeList() - " + ex.getMessage());
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
		return categoryList;
	}

	@Override
	public List<AgeClassBean> getAgeClassList() {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AgeClassBean> ageClassList = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			ageClassList = new ArrayList<AgeClassBean>();

			sql = "SELECT age_class_id, lower_age, upper_age, age_class";
			sql += " FROM mis_age_class WHERE IS_DELETED = 'N'";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				AgeClassBean ageClassBean = new AgeClassBean();
				ageClassBean.setAgeClassId(rs.getInt("age_class_id"));
				ageClassBean.setLowerAge(rs.getInt("lower_age"));
				ageClassBean.setUpperAge(rs.getInt("upper_age"));
				ageClassBean.setAgeClass(rs.getString("age_class"));

				ageClassList.add(ageClassBean);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				ageClassList = null;
				logger.info("Problem in Class - MasterDataDaoImpl ~~ method- getAgeClassList() - " + ex.getMessage());
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
		return ageClassList;
	}

	@Override
	public List<ClientBmiClassBean> getBmiClassList() {
		Connection conn = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ClientBmiClassBean> bmiClassList = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			bmiClassList = new ArrayList<ClientBmiClassBean>();

			sql = "SELECT bmi_class_id, lower_bmi, upper_bmi, bmi_class";
			sql += " FROM mis_client_bmi_class WHERE IS_DELETED = 'N'";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				ClientBmiClassBean bmiClassBean = new ClientBmiClassBean();
				bmiClassBean.setBmiClassId(rs.getInt("bmi_class_id"));
				bmiClassBean.setLowerBmi(rs.getInt("lower_bmi"));
				bmiClassBean.setUpperBmi(rs.getInt("upper_bmi"));
				bmiClassBean.setBmiClass(rs.getString("bmi_class"));

				bmiClassList.add(bmiClassBean);
			}
			conn.commit();
		} catch (Exception ex) {
			try {
				bmiClassList = null;
				logger.info("Problem in Class - MasterDataDaoImpl ~~ method- getBmiClassList() - " + ex.getMessage());
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
		return bmiClassList;
	}

}
