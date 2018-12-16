package com.dataloader.connection.objects;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.dataloader.connection.Connector;
import com.dataloader.handlers.HttpRequest;

public class ObjectsHandler {

	public static String ALL_OBJECT_STRING = "/sobjects";
	public static String DECRIBE_STRING = "/describe";
	public static String OAuth = "OAuth ";

	public static String describeAllObjects(String baseURL, String version, String sessionId) {
		String jobURL = baseURL + Connector.SERVICE_DATA_URL + "v" + version + ALL_OBJECT_STRING;
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", OAuth + sessionId);
		Map<String, String> response = null;
		System.out.println(jobURL);
		try {
			response = HttpRequest.createGETRequest(jobURL, headerMap);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return response.get("body");
	}

	public static String describeObject(String baseURL, String version, String sessionId, String objectName) {
		String jobURL = baseURL + Connector.SERVICE_DATA_URL + "v" + version + ALL_OBJECT_STRING
				+ Connector.URL_SAPERATOR + objectName + DECRIBE_STRING;
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", OAuth + sessionId);
		Map<String, String> response = null;
		System.out.println(jobURL);
		try {
			response = HttpRequest.createGETRequest(jobURL, headerMap);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return response.get("body");
	}

	public static String describeChildRecords(String baseURL, String version, String sessionId, String objectName,
			String recordIds, String relationShipName) {
		String jobURL = baseURL + Connector.SERVICE_DATA_URL + "v" + version + ALL_OBJECT_STRING
				+ Connector.URL_SAPERATOR + objectName + Connector.URL_SAPERATOR + recordIds + Connector.URL_SAPERATOR
				+ relationShipName;
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", Connector.BEARER + sessionId);
		Map<String, String> response = null;
		System.out.println(jobURL);
		try {
			response = HttpRequest.createGETRequest(jobURL, headerMap);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return response.get("body");
	}

}
