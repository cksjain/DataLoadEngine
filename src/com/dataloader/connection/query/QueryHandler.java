package com.dataloader.connection.query;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.dataloader.connection.Connector;
import com.dataloader.handlers.HttpRequest;

public class QueryHandler {

	private static String QUERY = "/query?q=";
	private static String QUERY_DELIMETER = "+";

	public static Map<String, String> exportData(String baseURL, String version, String sessionId, String query) {
		String batchURL = baseURL + Connector.SERVICE_DATA_URL + "v" + version + QUERY;
		batchURL += String.join(QUERY_DELIMETER, query.split(" "));
		System.out.println(batchURL);
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", Connector.BEARER + sessionId);
		Map<String, String> response = null;
		try {
			response = HttpRequest.createGETRequest(batchURL, headerMap);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static Map<String, String> queryMore(String queryURL,String sessionId) {
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", Connector.BEARER + sessionId);
		Map<String, String> response = null;
		try {
			response = HttpRequest.createGETRequest(queryURL, headerMap);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return response;
	}
}