package com.rdms.bug.action;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rdms.base.vo.AppVO;
import com.rdms.base.vo.ErrorCode;
import com.rdms.bug.action.model.EmailModel;
import com.rdms.util.MailSender;

@Controller("emailAction")
@Scope("prototype")
public class EmailAction extends ActionSupport implements ModelDriven<EmailModel> {
	
	private static final long serialVersionUID = 1L;
	@Resource(name="appVO")
	private AppVO appVO;
	@Resource(name="emailModel")
	private EmailModel emailModel;

	public String sendEmail() {
		EmailModel model = this.getModel();
		
		// 设置系统默认邮箱账号及密码
		model.setUserName("rdms_admin@163.com");
		model.setPassword("woshixin");
		
		String userName = model.getUserName();
		String password = model.getPassword();
		String recipient = model.getRecipient();
		String subject = model.getSubject();
		String content = model.getContent();
		MailSender mailSender = new MailSender(userName, password);
		AppVO appVO = this.getAppVO();
		try {
			mailSender.sendMail(recipient, subject, content);
			appVO.setSuccess(true);
			appVO.setMsg("发送邮件成功");
		} catch (AddressException e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,邮件发送失败");
			appVO.setErrorCode(ErrorCode.SYS_ERROR);
			return ERROR;
		} catch (MessagingException e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,邮件发送失败");
			appVO.setErrorCode(ErrorCode.SYS_ERROR);
			return ERROR;
		}
		return SUCCESS;
	}

	public AppVO getAppVO() {
		return appVO;
	}

	public void setAppVO(AppVO appVO) {
		this.appVO = appVO;
	}

	@Override
	public EmailModel getModel() {
		return this.emailModel;
	}

}
