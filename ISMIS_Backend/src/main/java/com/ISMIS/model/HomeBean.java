package com.ISMIS.model;

public class HomeBean extends CommonModel{

	private Integer home_id;
	private String home_name;
	private String home_address;
	private String contact_no;

	public Integer getHome_id() {
		return home_id;
	}

	public void setHome_id(Integer home_id) {
		this.home_id = home_id;
	}

	public String getHome_name() {
		return home_name;
	}

	public void setHome_name(String home_name) {
		this.home_name = home_name;
	}

	public String getHome_address() {
		return home_address;
	}

	public void setHome_address(String home_address) {
		this.home_address = home_address;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

}
