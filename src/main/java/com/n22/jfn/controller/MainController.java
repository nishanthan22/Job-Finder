package com.n22.jfn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {
	
	
	@GetMapping("/welcome")
	public String welcomeTest() {
		return "Hello, It's running";
	}
}
