package com.virtualpairprogrammers.roombooking.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.virtualpairprogrammers.roombooking.services.JwtService;

@RestController
@RequestMapping("/api/basicAuth")
public class ValidatUserController {

	@Autowired
	JwtService jwtService;
	
	@GetMapping("validate")
	public Map<String,String> userIsValid(HttpServletResponse response) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		
		String userName = currentUser.getUsername();
		String role = currentUser.getAuthorities().toArray()[0].toString().substring(5);
		
		String token = jwtService.generateToken(userName, role);
				
		Map<String,String> results = new HashMap<>();
		results.put("result", "Bearer " + token);
		return results; //"{\"result\" : \"ok\"}";
	}

	@PostMapping("validate/decryptToken")
	public String decryptToken(@RequestBody String token) {

		System.out.println(token);
		JsonParser jsonParser = new BasicJsonParser();
		Map<String, Object> jsonMap = jsonParser.parseMap(token);
		return  jwtService.validateToken(((String) jsonMap.get("result")).substring(7)); //"{\"decoded\": " + "\"" + jwtService.validateToken((String) jsonMap.get("result")) + "\"}";
	}


}
