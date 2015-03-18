package com.rdms.sys.action.model;

import java.io.Serializable;
import java.util.Map;

import com.rdms.base.action.model.BaseModel;
import com.rdms.sys.domain.User;

public class UserModel extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String account;
	private String pwd;
	private String name;
	
	public UserModel() {}
	
	public static Map<String, Class<?>> getClassMap() {
		return null;
	}
	
	public static UserModel toModel(User entity) {
		User userEntity = entity;
		UserModel model = new UserModel();
		model.setId(userEntity.getId());
		model.setName(userEntity.getName());
		model.setAccount(userEntity.getAccount());
		return model;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
