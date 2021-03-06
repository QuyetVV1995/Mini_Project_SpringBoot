package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

@SpringBootApplication
public class DemoSpringEmailApplication {

	@Autowired
	private SendEmailService sendEmailService;

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringEmailApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void triggerWhenStart(){
//		sendEmailService.sendEmail("ducvan95ubqn@gmail.com","test spring boot mail", "email");
//
//		try {
//			sendEmailService.sendEmailAttachImage();
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			sendEmailService.sendHtmlEmail();
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}

		try {
			sendEmailService.sendTemplateEmail();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
