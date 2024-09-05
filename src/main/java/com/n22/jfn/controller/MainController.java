package com.n22.jfn.controller;

import java.util.HashMap;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.n22.jfn.apphelper.Utilities;
import com.n22.jfn.constants.AppConstants;
import com.n22.jfn.dto.JobDetails;
import com.n22.jfn.services.JobService;

@RestController
public class MainController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	Utilities utils;

	@Autowired
	private JobService jobService;

	@GetMapping("/welcome")
	public String welcomeTest() {
		return "Hello, It's running";
	}

	@GetMapping("/getlinkedinjobs")
	public JsonObject fetchLinkedinJobs(@RequestHeader HashMap<String, String> headers,
			@RequestParam HashMap<String, String> params) {
		JsonObject response = new JsonObject();
		LOGGER.info("REQUEST_PARAMS: {}, REQUEST_HEADERS: {}", params, headers);

		String url = utils.getURLWithFilterParams(AppConstants.LINKEDIN);

		if (url.isBlank() || url == null)
			url = AppConstants.HARDCODED_LINKEDIN_URL;

		LOGGER.info("FORMATTED_FINAL_LINKEDIN_URL: {}", url);
		List<JobDetails> jobDetails;
		try {
			jobDetails = jobService.fetchLinkedInJobs(url);
			response.addProperty(AppConstants.STATUS_CODE, HttpStatus.OK.value());
			response.addProperty(AppConstants.STATUS, AppConstants.SUCCESS);
			if (jobDetails.isEmpty() || jobDetails == null)
				response.addProperty(AppConstants.RESPONSE, AppConstants.NO_JOBS_FOUND);
			else {
			JsonObject jobDetailsJson = utils.covertListToJsonObject(jobDetails);
			response.add(AppConstants.RESPONSE, jobDetailsJson);
			}

		} catch (Exception exc) {
			response.addProperty(AppConstants.STATUS, AppConstants.FAILURE);
			if (exc instanceof HttpStatusException) {
				HttpStatusException httpException = (HttpStatusException) exc;
				response.addProperty(AppConstants.STATUS_CODE, httpException.getStatusCode());
				response.addProperty(AppConstants.RESPONSE, httpException.getMessage());
			} else {
				response.addProperty(AppConstants.STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
				response.addProperty(AppConstants.RESPONSE, AppConstants.UNKNOWN_EXCEPTION);
			}

		}
		LOGGER.info("/getlinkedinjobs RESPONSE: {}", response);
		return response;
	}

}
