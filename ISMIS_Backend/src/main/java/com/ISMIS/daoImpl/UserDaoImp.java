package com.ISMIS.daoImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ISMIS.dao.UserDao;
import com.ISMIS.model.MenuBean;
import com.ISMIS.model.UserBean;

@Repository
public class UserDaoImp extends CommonDaoImpl implements UserDao {

	static Logger logger = LogManager.getLogger(UserDaoImp.class);

	@Override
	public String checkLogin(UserBean userBean) {

		Connection conn = null;
		String status = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			String sql = "SELECT um.USER_ID, um.USER_NAME,";
			sql += " um.USER_CODE, um.EMAIL_ID,";
			sql += " um.CONTACT_NO, um.IS_ACTIVE";
			sql += " FROM  mis_user_master um, mis_user_authentication uauth,";
			sql += " mis_home_user_mapping homeUser";
			sql += " WHERE um.USER_ID = uauth.USER_ID";
			sql += " AND um.USER_ID = homeUser.USER_ID";
			sql += " AND um.USER_CODE = ?";
			sql += " AND uauth.USER_PWD = ?";
			sql += " AND homeUser.HOME_ID = ?";
			sql += " AND um.IS_ACTIVE = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userBean.getUserCode());
			ps.setString(2, userBean.getUserPassword());
			ps.setInt(3, userBean.getHomeId());
			ps.setString(4, "Y");
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				userBean.setUserId(rs.getInt("USER_ID"));
				userBean.setUserName(rs.getString("USER_NAME"));
				userBean.setIsActive(rs.getString("is_active"));
				userBean.setEmailId(rs.getString("EMAIL_ID"));
				userBean.setContactNo(rs.getString("CONTACT_NO"));

			}
			conn.commit();
		} catch (Exception ex) {
			try {
				logger.info("Problem in Class - UserDaoImp ~~ method- checkLogin() - " + ex.getMessage());
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
	public Object getMenuList(UserBean userBean) {

		Connection conn = null;
		ArrayList<MenuBean> menuList = null;
		ArrayList<MenuBean> tmp_menuList = null;
		String sql = null;
		int v_max = 0;
		int max_role_id = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object return_val = null;
		HashMap<Integer, ArrayList<MenuBean>> menu = null;
		HashMap<Integer, ArrayList<MenuBean>> sub_menu = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = "SELECT DISTINCT role_id as max_role FROM mis_role_user_mapping WHERE user_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, userBean.getUserId());
			rs = ps.executeQuery();

			if (rs.next()) {
				max_role_id = rs.getInt("max_role");
			}

			ps.clearParameters();

			if (max_role_id > 0) {

				sql = "select max(menu_level) as v_max from mis_menu_master";

				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();

				if (rs.next()) {
					v_max = rs.getInt("v_max");
				}

				ps.clearParameters();

				for (int i = v_max; i >= 0; i--) {

					// System.out.println(i);

					sql = "SELECT DISTINCT";
					sql += " menu.menu_id,";
					sql += " menu.parent_menu_id,";
					sql += " menu.menu_name,";
					sql += " menu.MENU_ICON_URL,";
					sql += " (SELECT action_url FROM mis_page_master WHERE menu.menu_id = menu_id) AS action_url,";
					sql += " menu_level, " + v_max + " as max_level";
					sql += " FROM mis_menu_master menu, mis_menu_role_mapping menu_rol, mis_role_user_mapping rol_usr";
					sql += " WHERE rol_usr.role_id = menu_rol.role_id";
					sql += " AND menu.menu_id = menu_rol.menu_id ";
					sql += " AND menu.menu_LEVEL = ? ";
					sql += " AND rol_usr.user_id = ?";
					sql += " ORDER BY menu.seq_no";

					ps = conn.prepareStatement(sql);
					ps.setInt(1, i);
					ps.setInt(2, userBean.getUserId());

					rs = ps.executeQuery();

					menu = new HashMap<Integer, ArrayList<MenuBean>>();

					while (rs.next()) {

						MenuBean menuBean = new MenuBean();
						menuBean.setMenuId(rs.getInt("menu_id"));
						menuBean.setParentMenuId(rs.getInt("parent_menu_id"));
						menuBean.setMenuName(rs.getString("menu_name"));
						menuBean.setAction(rs.getString("action_url"));
						menuBean.setLevel(rs.getInt("menu_level"));
						menuBean.setMaxLevel(rs.getInt("max_level"));
						menuBean.setMenuIconUrl(rs.getString("MENU_ICON_URL"));

						if (menu.get(menuBean.getParentMenuId()) != null) {

							menuList = menu.get(menuBean.getParentMenuId());
							menuList.add(menuBean);

						} else {
							menuList = new ArrayList<MenuBean>();
							menuList.add(menuBean);
							menu.put(menuBean.getParentMenuId(), menuList);
						}
					}

					if (i != v_max && !sub_menu.isEmpty()) {

						for (Map.Entry<Integer, ArrayList<MenuBean>> entry : menu.entrySet()) {
							tmp_menuList = entry.getValue();

							for (MenuBean menuBean : tmp_menuList) {
								if (sub_menu.get(menuBean.getMenuId()) != null) {
									menuBean.setMenuLists(sub_menu.get(menuBean.getMenuId()));
								}
							}
						}
					}
					sub_menu = menu;
					ps.clearParameters();
				}

				return_val = sub_menu.get(0);

			} else {
				return_val = new String("No role assinged for the user.");
			}

			conn.commit();
		} catch (Exception ex) {
			try {
				logger.info("Problem in Class - UserDaoImp ~~ method- getMenuList() - " + ex.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				String sStackTrace = sw.toString();
				logger.info(sStackTrace);
				return_val = null;
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

		return return_val;
	}

	/*
	 * @Override public Object getMenuList(UserBean userBean) throws Exception {
	 * 
	 * Connection conn = null; List<MenuBean> menuList = null; String sql = null;
	 * int v_max = 0; int max_role_id = 0; PreparedStatement ps = null; ResultSet rs
	 * = null; Object return_val = null;
	 * 
	 * try { conn = dataSource.getConnection(); conn.setAutoCommit(false);
	 * 
	 * sql =
	 * "SELECT DISTINCT role_id as max_role FROM mis_role_user_mapping WHERE user_id = ?"
	 * ;
	 * 
	 * ps = conn.prepareStatement(sql); ps.setInt(1, userBean.getUserId()); rs =
	 * ps.executeQuery();
	 * 
	 * if (rs.next()) { max_role_id = rs.getInt("max_role"); }
	 * 
	 * ps.clearParameters();
	 * 
	 * if (max_role_id > 0) {
	 * 
	 * sql = "select max(menu_level) as v_max from mis_menu_master";
	 * 
	 * ps = conn.prepareStatement(sql); rs = ps.executeQuery();
	 * 
	 * if (rs.next()) { v_max = rs.getInt("v_max"); }
	 * 
	 * ps.clearParameters();
	 * 
	 * sql = "SELECT DISTINCT"; sql += " menu.menu_id,"; sql +=
	 * " menu.parent_menu_id,"; sql += " menu.menu_name,"; sql +=
	 * " (SELECT action_url FROM mis_page_master WHERE menu.menu_id = menu_id) AS action_url,"
	 * ; sql += " menu_level, " + v_max + " as max_level"; sql +=
	 * " FROM mis_menu_master menu, mis_menu_role_mapping menu_rol, mis_role_user_mapping rol_usr"
	 * ; sql += " WHERE rol_usr.role_id = menu_rol.role_id"; sql +=
	 * " AND menu.menu_id = menu_rol.menu_id "; sql += " AND rol_usr.user_id = ?";
	 * 
	 * ps = conn.prepareStatement(sql); ps.setInt(1, userBean.getUserId());
	 * 
	 * rs = ps.executeQuery();
	 * 
	 * menuList = new ArrayList<MenuBean>();
	 * 
	 * while(rs.next()){ MenuBean menuBean = new MenuBean();
	 * menuBean.setMenuId(String.valueOf(rs.getInt("menu_id")));
	 * menuBean.setParentMenuId(String.valueOf(rs.getInt("parent_menu_id")));
	 * menuBean.setMenuName(rs.getString("menu_name"));
	 * menuBean.setAction(rs.getString("action_url"));
	 * menuBean.setLevel(String.valueOf(rs.getInt("menu_level")));
	 * menuBean.setMaxLevel(String.valueOf(rs.getInt("max_level")));
	 * menuList.add(menuBean); }
	 * 
	 * ps.clearParameters(); return_val = menuList;
	 * 
	 * } else { return_val = new String("No role assinged for the user."); }
	 * 
	 * conn.commit(); } catch (Exception ex) { conn.rollback();
	 * logger.info("Problem in Class - UserDaoImp ~~ method- getMenuList() - " +
	 * ex.getMessage()); StringWriter sw = new StringWriter(); PrintWriter pw = new
	 * PrintWriter(sw); ex.printStackTrace(pw); String sStackTrace = sw.toString();
	 * logger.info(sStackTrace); return_val = null; } finally { try { if (conn !=
	 * null) { conn.close(); } } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * return return_val; }
	 */

}
