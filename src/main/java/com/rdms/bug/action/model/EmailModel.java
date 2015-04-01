package com.rdms.bug.action.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("emailModel")
@Scope("prototype")
public class EmailModel {
	
	// 收件人邮箱账号
	private String recipient;
	// 邮件标题
	private String subject;
	// 邮件内容
	private String content;
	// 发件人邮箱账号
	private String userName;
	// 系统邮箱密码
	private String password;
	
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
