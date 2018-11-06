package com.dataloader.enums;

public enum OrgType {
	PRODUCTION("https://login.salesforce.com/services/Soap/u/"), SANDBOX(
			"https://test.salesforce.com/services/Soap/u/");

	private String baseURL;

	public String getLoginURL() {
		return this.baseURL;
	}

	private OrgType(String baseURL) {
		this.baseURL = baseURL;
	}
}