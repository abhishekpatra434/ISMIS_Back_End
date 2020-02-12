package com.ISMIS.model;

import java.util.ArrayList;

public class MenuBean extends CommonModel{

	private int menuId;
	private int parentMenuId;
	private String menuName;
	private String action;
	private int level;
	private int maxLevel;
	private ArrayList<MenuBean> menuLists;
	private String menuIconUrl;
	private boolean isActive;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(int parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public ArrayList<MenuBean> getMenuLists() {
		return menuLists;
	}

	public void setMenuLists(ArrayList<MenuBean> menuLists) {
		this.menuLists = menuLists;
	}

	public String getMenuIconUrl() {
		return menuIconUrl;
	}

	public void setMenuIconUrl(String menuIconUrl) {
		this.menuIconUrl = menuIconUrl;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
