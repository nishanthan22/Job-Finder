package com.n22.jfn.services;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.n22.jfn.dto.JobDetails;

@Service
public class JobService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobService.class);

	public List<JobDetails> fetchLinkedInJobs(String url) throws Exception {
    	
        try {
            // Introduce delay between requests to avoid 429 error
            Thread.sleep(1000); // Adjust the delay based on the API rate limits
         
            // Fetch the document from the URL
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Connection", "keep-alive")
                    .get();
            // Select all job listings
            Elements jobListings = doc.select(".jobs-search__results-list li");

            // Initialize a list to hold job details
            List<JobDetails> jobDetailsList = new ArrayList<>();

            // Iterate over each job listing
            for (Element job : jobListings) {
                // Extract the job URL
                String jobUrl = job.select("a.base-card__full-link").attr("href");

                // Extract the job name
                String jobName = job.select(".base-search-card__title").text();

                // Extract the company name
                String companyName = job.select(".base-search-card__subtitle a").text();

                // Extract the job location
                String location = job.select(".job-search-card__location").text();

                // Extract the number of applicants (if available)
                String applicants = job.select(".job-posting-benefits__text").text(); // This selector might need adjustment based on actual HTML

                // Create a new JobDetails object and add to the list
                jobDetailsList.add(new JobDetails(jobUrl, jobName, companyName, location, applicants));
            }
            
            LOGGER.info("Totally, [{}] Job links are fetched.\n", jobDetailsList.size());
   

            return jobDetailsList;
        } catch (Exception exc) {
            LOGGER.error("ERROR IN FETCHING JOB LINKS: {}", exc.getMessage());
            throw exc;
        }
    }

	
}
