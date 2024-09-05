package com.n22.jfn.apphelper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.n22.jfn.constants.AppConstants;
import com.n22.jfn.constants.UrlConstants;
import com.n22.jfn.dto.JobDetails;

@Component
public class Utilities {

	public String getURLWithFilterParams(String portal) {

		String final_url = "";
		if (portal.equalsIgnoreCase(AppConstants.LINKEDIN)) {
			final_url = AppConstants.LINKEDIN_JOBS_URL.concat(UrlConstants.SEARCH_QUERY)
					.concat(UrlConstants.LEVEL_FILTER).concat("2").concat(UrlConstants.COMMA).concat("3")
					.concat(UrlConstants.AMPERSAND).concat(UrlConstants.WORK_TYPE_FILTER).concat(AppConstants.TRUE)
					.concat(UrlConstants.AMPERSAND).concat(UrlConstants.TIME_POSTED_FILTER).concat("r86400")
					.concat(UrlConstants.AMPERSAND).concat(UrlConstants.KEYWORDS).concat(AppConstants.INTERESTED_ROLES)
					.concat(UrlConstants.AMPERSAND).concat(UrlConstants.REFRESH).concat(AppConstants.TRUE)
					.concat(UrlConstants.AMPERSAND).concat(UrlConstants.SORT_BY);

		}

		return final_url;
	}

	public JsonObject covertListToJsonObject(List<JobDetails> jobDetails) {
		// Convert List<JobDetails> to JsonObject
      
        
        // Create JsonArray from List<JobDetails>
        JsonArray jsonArray = new JsonArray();
        for (JobDetails j : jobDetails) {
            JsonObject jobJson = new JsonObject();
            
            jobJson.addProperty("job_name", j.getJobName());
            jobJson.addProperty("company", j.getCompanyName());
            jobJson.addProperty("location", j.getLocation());
            jobJson.addProperty("link", j.getJobUrl());
            jobJson.addProperty("benefits", j.getBenefits());
            
            jsonArray.add(jobJson);
        }
        
        // Create JsonObject and add JsonArray to it
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("total_jobs", jobDetails.size());
        jsonObject.add("jobs", jsonArray);
        
		return jsonObject;
	}

}
