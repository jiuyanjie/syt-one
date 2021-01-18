package com.bookManagerment.utils;

import com.bookManagerment.config.BMSystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtils {

    @Autowired
    private  BMSystemProperties systemProperties;

    public void sendRemind(String email,String emailMsg) throws MessagingException {
		sendMail(email,emailMsg,systemProperties.getMailReaderRemindTitle());
	}

	public void sendRegisterVerifyCode(String email, String emailMsg) throws MessagingException {
		sendMail(email,emailMsg,systemProperties.getEmailReaderRegisterTitle());
	}

	private void sendMail(String email, String emailMsg,String title)
			throws AddressException, MessagingException {
		// 1.创建一个程序与邮件服务器会话对象 Session

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");
		props.setProperty("mail.smtp.auth", "true");//开启认证
		props.setProperty("mail.debug", "true");//启用调试
		props.setProperty("mail.smtp.timeout", "10000");//设置链接超时
		props.setProperty("mail.smtp.port", "465");//设置端口
		props.setProperty("mail.smtp.socketFactory.port", "465");//设置ssl端口
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.host", systemProperties.getMailHost());

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(systemProperties.getMailAccount(), systemProperties.getMailPassword());
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(systemProperties.getMailAccount())); // 设置发送者

		message.setRecipient(Message.RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者

		message.setSubject(title);	//邮箱标题

		message.setContent(emailMsg, systemProperties.getMailContent());	//格式

		// 3.创建 Transport用于将邮件发送

		Transport.send(message);

		System.out.println("邮箱发送成功:");
	}



}
