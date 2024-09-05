package com.n22.jfn.dto;

public class JobDetails {
	private String jobUrl;
	private String jobName;
	private String companyName;
	private String location;
	private String benefits;

	public JobDetails(String jobUrl, String jobName, String companyName, String location, String benefits) {
		this.jobUrl = jobUrl;
		this.jobName = jobName;
		this.companyName = companyName;
		this.location = location;
		this.benefits = benefits;
	}

	public String getJobUrl() {
		return jobUrl;
	}

	public String getJobName() {
		return jobName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getLocation() {
		return location;
	}

	public String getBenefits() {
		return benefits;
	}
}
