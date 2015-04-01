package com.rdms.comm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.rdms.auth.domain.Role;
import com.rdms.comm.action.model.EmployeeModel;

public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String account;
	private String pwd;
	private String name;
	private String gender;
	private Date birthDate;
	private Date entryDate;
	private String email;
	private String phone;
	private String dept;
	private String position;
	private String stats;
	private String pic;
	
	private Set<Role> roles = new HashSet<Role>();
	
	public Employee() {}
	
	// 重载equals 用于判断两个emp对象是否相等
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof Employee) {
			Employee emp = (Employee) obj;
			String id = emp.getId();
			if(id != null && id.equals(this.getId())) {
				return true;
			}
		}
		return false;
	}


	public static EmployeeModel toModel(Employee entity) {
		Employee emp = entity;
		EmployeeModel model = new EmployeeModel();
		model.setId(emp.getId());
		model.setAccount(emp.getAccount());
		model.setName(emp.getName());
		model.setGender(emp.getGender());
		model.setBirthDate(emp.getBirthDate());
		model.setEntryDate(emp.getEntryDate());
		model.setEmail(emp.getEmail());
		model.setPhone(emp.getPhone());
		model.setDept(emp.getDept());
		model.setPosition(emp.getPosition());
		model.setStats(emp.getStats());
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
