package com.laky.edu;

import com.laky.edu.WebSocket.IMWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LakyeduApplication {


	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(LakyeduApplication.class);
		ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
		//解决WebSocket不能注入的问题
		IMWebSocket.setApplicationContext(configurableApplicationContext);

//		SpringApplication.run(LakyeduApplication.class, args);
	}
}
