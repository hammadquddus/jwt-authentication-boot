package com.virtualpairprogrammers.roombooking;

import java.util.List;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.virtualpairprogrammers.roombooking.data.UserRepository;
import com.virtualpairprogrammers.roombooking.model.entities.User;

@SpringBootApplication
public class RoombookingApplication {

	public static final Logger logger = LoggerFactory.getLogger(RoombookingApplication.class);

	
//	Added the code below to Application.java and for now it works, default on port 8082, starts with spring app. It doesn`t hit the spot but for dev purposes it is all ok.
	@Bean
	org.h2.tools.Server h2Server() {
	    Server server = new Server();
	    try {
	        server.runTool("-tcp");
	        server.runTool("-tcpAllowOthers");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return server;

	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(RoombookingApplication.class, args);
	}

	
}
