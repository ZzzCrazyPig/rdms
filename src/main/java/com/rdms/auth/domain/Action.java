package com.rdms.auth.domain;

import java.io.Serializable;

public class Action implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String url;
	private String detail;
	
	public Action() {}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
