package com.rdms.sys.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Page implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	private String name;
	private String url;
	private String createUser;
	private Date createTime;
	
	private Menu menu;

	public Page() {}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Menu getMenu() {
		return menu;
	}

	@JSON(serialize=false)
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
