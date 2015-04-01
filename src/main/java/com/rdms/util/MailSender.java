package com.rdms.util;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailSender {
	
	private MailAuthenticator authenticator;
	private Properties props = System.getProperties();
	private Session session;
	
	public MailSender(String userName, String password) {
		this.authenticator = new MailAuthenticator(userName, password);
		this.props.put("mail.smtp.auth", true);
		String smtpHostName = "smtp." + userName.split("@")[1];
		this.props.put("mail.smtp.host", smtpHostName);
		this.session = Session.getInstance(props, authenticator);
		//this.session.setDebug(true);
	}
	
	public static void main(String[] args) {
		String userName = "rdms_admin@163.com";
		String password = "woshixin";
		String recipient = "18825111236@163.com";
		String mailSubject = "test";
		String mailContent = "test";
		MailSender mailSender = new MailSender(userName, password);
		try {
			mailSender.sendMail(recipient, mailSubject, mailContent);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 单发邮件
	 * @param recipient 收件人
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendMail(String recipient, String subject, Object content) throws AddressException, MessagingException {
		MimeMessage mimeMsg = new MimeMessage(session);
		// 设置发件人
		mimeMsg.setFrom(new InternetAddress(authenticator.getUserName()));
		// 设置收件人
		mimeMsg.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		// 设置邮件主题
		mimeMsg.setSubject(subject);
		// 设置邮件内容
		mimeMsg.setContent(content, "text/html;charset=utf-8");
		// 发送邮件
		Transport.send(mimeMsg);
	}
	
	/**
	 * 群发邮件
	 * @param recipients 收件人数组
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public void sendMail(String[] recipients, String subject, Object content) throws AddressException, MessagingException {
		MimeMessage mimeMsg = new MimeMessage(session);
		// 设置发件人
		mimeMsg.setFrom(new InternetAddress(authenticator.getUserName()));
		// 设置收件人
		int size = recipients.length;
		InternetAddress[] addresses = new InternetAddress[size];
		for(int i = 0; i < size; i++) {
			String recipient = recipients[i];
			addresses[i] = new InternetAddress(recipient);
		}
		mimeMsg.setRecipients(RecipientType.TO, addresses);
		// 设置邮件主题
		mimeMsg.setSubject(subject);
		// 设置邮件内容
		mimeMsg.setContent(content, "text/html;charset=utf8");
		// 发送邮件
		Transport.send(mimeMsg);
	}
	
}
