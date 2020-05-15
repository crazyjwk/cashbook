package com.gdu.cashbook;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication // == @configuration @enableautoconfiguration @componentscan
@PropertySource("classpath:google.properties") // google.properties 에서 값을 가져온다.
public class CashbookApplication {
	@Value("${google.username}")
	public String username;
	@Value("${google.password}")
	public String password;
	
	public static void main(String[] args) {
		SpringApplication.run(CashbookApplication.class, args);
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com"); //메일 서버 이름(구글)
		javaMailSender.setPort(587); // 구글 메일 포트
		javaMailSender.setUsername(username); // gmail 계정
		javaMailSender.setPassword(password); // password
		
		Properties prop = new Properties(); // Properties == HashMap<String ,String>
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		javaMailSender.setJavaMailProperties(prop);
		return javaMailSender; 
	}
}