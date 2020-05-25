package com.capstore.app.signup_login;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service("emailSenderService")
@Data
public class EmailSenderService {
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public EmailSenderService(JavaMailSender javaMailSender) {
		this.javaMailSender=javaMailSender;
	}
	
	@Async
	public void sendEmail(MimeMessage email) {
		javaMailSender.send(email);
	}

	public MimeMessage createMessage() {
		
		return javaMailSender.createMimeMessage();
	}

	@Async
	public void sendEmail(SimpleMailMessage email) {
		javaMailSender.send(email);
	}
}
