package com.n22.jfn.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;


@RestController
public class MainController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@GetMapping("/welcome")
	public String welcomeTest() {
		return "Hello, It's running";
	}
	
	
	@GetMapping("/getlinkedinjobs")
	public JsonObject fetchLinkedinJobs(@RequestHeader HashMap<String, String> headers,
			@RequestParam HashMap<String, String> params) {
		JsonObject response = new JsonObject();
		LOGGER.info("REQUEST_PARAMS: {}, REQUEST_HEADERS: {}", params, headers);
		
		response.addProperty("sample", "sample");
		
		return response;
	}
}
