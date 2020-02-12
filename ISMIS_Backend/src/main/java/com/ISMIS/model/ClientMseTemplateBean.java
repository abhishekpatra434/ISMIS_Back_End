package com.ISMIS.model;

import java.util.ArrayList;
import java.util.List;

public class ClientMseTemplateBean {

	private int mental_status_lbl_id;
	private String lbl_name;
	private int mental_status_lbl_parent_id;
	private String option_type;
	private String has_child;
	private int caption_level;
	private ArrayList<ClientMseTemplateBean> childNode;
	private String lbl_status;
	private String mse_text_value;

	public int getMental_status_lbl_id() {
		return mental_status_lbl_id;
	}

	public void setMental_status_lbl_id(int mental_status_lbl_id) {
		this.mental_status_lbl_id = mental_status_lbl_id;
	}

	public String getLbl_name() {
		return lbl_name;
	}

	public void setLbl_name(String lbl_name) {
		this.lbl_name = lbl_name;
	}

	public int getMental_status_lbl_parent_id() {
		return mental_status_lbl_parent_id;
	}

	public void setMental_status_lbl_parent_id(int mental_status_lbl_parent_id) {
		this.mental_status_lbl_parent_id = mental_status_lbl_parent_id;
	}

	public String getOption_type() {
		return option_type;
	}

	public void setOption_type(String option_type) {
		this.option_type = option_type;
	}

	public String getHas_child() {
		return has_child;
	}

	public void setHas_child(String has_child) {
		this.has_child = has_child;
	}

	public int getCaption_level() {
		return caption_level;
	}

	public void setCaption_level(int caption_level) {
		this.caption_level = caption_level;
	}

	public ArrayList<ClientMseTemplateBean> getChildNode() {
		return childNode;
	}

	public void setChildNode(ArrayList<ClientMseTemplateBean> childNode) {
		this.childNode = childNode;
	}

	public String getLbl_status() {
		return lbl_status;
	}

	public void setLbl_status(String lbl_status) {
		this.lbl_status = lbl_status;
	}

	public String getMse_text_value() {
		return mse_text_value;
	}

	public void setMse_text_value(String mse_text_value) {
		this.mse_text_value = mse_text_value;
	}

}
