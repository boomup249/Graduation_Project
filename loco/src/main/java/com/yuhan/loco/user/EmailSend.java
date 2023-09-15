package com.yuhan.loco.user;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSend {
	   private final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
	   private int authNumber;
	   
	   private final String admin = "yuhanloco@gmail.com";
	   private final String password = "ceeueljkbnvsmcvc";
	   
	   Properties prop = new Properties();
	   
	   public EmailSend() {
       prop.put("mail.smtp.host", "smtp.gmail.com");
       prop.put("mail.smtp.port", 465);
       prop.put("mail.smtp.auth", "true");
       prop.put("mail.smtp.ssl.enable", "true");
       prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
       prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
       
	   }
	   
       Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(admin, password);
           }
       });
	   
	   public void makeRandomNumber() {
	       Random random = new Random();
	       int checkNum = random.nextInt(888888) + 111111;
//	       log.info("checkNum : " + checkNum);
	       authNumber = checkNum;
	   }

	   public String joinEmail(String email) {
	       makeRandomNumber();

	       String toMail = email;
	       String title = "회원가입을 위한 인증메일입니다.";
	       String message =
	                  "홈페이지를 방문해주셔서 감사합니다. \n\n" +
	                  "인증번호는 " + authNumber + " 입니다. \n\n" +
	                  "해당 인증번호를 인증번호 확인한에 기입하여 주시기바랍니다.";
	       mailSend(message,toMail, title);
	       return Integer.toString(authNumber);
	   }

	   public void mailSend(String message,String toMail, String title) {
	       MimeMessage mimeMessage = new MimeMessage(session);

	       try {
	    	   mimeMessage.setFrom(new InternetAddress(admin));
	    	   mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
	    	   mimeMessage.setSubject(title);
	    	   mimeMessage.setText(message);
	    	   
	    	   Transport.send(mimeMessage);
	       } catch (MessagingException e) {
	           throw new RuntimeException(e);
	       }
	   }
}
