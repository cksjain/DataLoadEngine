package com.dataloader.enums;

public enum BulkJobOperation {
	INSERT("insert"), DELETE("delete"), HARD_DELETE("hardDelete");

	private String operation;

	public String getOperation() {
		return this.operation;
	}

	private BulkJobOperation(String operation) {
		this.operation = operation;
	}
}
