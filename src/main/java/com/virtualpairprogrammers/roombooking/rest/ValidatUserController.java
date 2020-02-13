package com.virtualpairprogrammers.roombooking.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basicAuth")
public class ValidatUserController {

	@GetMapping("validate")
	public String userIsValid() {
		
		return "{\"result\" : \"ok\"}";
	}
}
