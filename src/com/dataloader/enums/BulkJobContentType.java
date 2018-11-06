package com.dataloader.enums;

public enum BulkJobContentType {
	QUERY("JSON"), FILE("CSV");

	private String jobType;

	public String getJobType() {
		return this.jobType;
	}

	private BulkJobContentType(String jobType) {
		this.jobType = jobType;
	}
}
