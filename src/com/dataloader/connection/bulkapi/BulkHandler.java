package com.dataloader.connection.bulkapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.dataloader.connection.Connector;
import com.dataloader.enums.BulkJobContentType;
import com.dataloader.enums.BulkJobOperation;
import com.dataloader.handlers.HttpRequest;

public class BulkHandler {

	public static final String JOBS_INJEST = "/jobs/ingest";
	public static final String BATCHES = "/batches";

	public static String createJob(String baseURL, String version, String object, String operation, String contentType,
			String sessionId) {
		String jobURL = baseURL + Connector.SERVICE_DATA_URL + "v" +version + JOBS_INJEST;
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", Connector.BEARER + sessionId);
		headerMap.put("Content-Type", "application/json");
		Map<String, String> response = null;
		System.out.println(jobURL);
		System.out.println(getJobRequestBody(object, operation, contentType));
		try {
			response = HttpRequest.createPOSTRequest(jobURL, getJobRequestBody(object, operation, contentType),
					headerMap);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return response.get("body");
	}

	public static Map<String, String> uploadDataBatch(String baseURL, String version, String sessionId, String jobId,
			String contentType, String requestBody) {
		String batchURL = baseURL + Connector.SERVICE_DATA_URL + "v" + version + JOBS_INJEST + Connector.URL_SAPERATOR + jobId
				+ BATCHES;
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", Connector.BEARER + sessionId);
		headerMap.put("Content-Type", BulkJobContentType.valueOf(contentType).getJobType());
		headerMap.put("Accept", "application/json");
		Map<String, String> response = null;
		try {
			System.out.println(requestBody);
			response = HttpRequest.createPUTRequest(batchURL, headerMap, requestBody);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static Map<String, String> getJobStatus(String baseURL, String version, String sessionId, String jobId) {
		String batchURL = baseURL + Connector.SERVICE_DATA_URL + "v" + version + JOBS_INJEST + Connector.URL_SAPERATOR
				+ jobId;
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

	public static String getJobRequestBody(String object, String operation, String contentType) {
		String jsonValue = "{" + "  \"operation\" : \"" + BulkJobOperation.valueOf(operation).getOperation() + "\","
				+ "  \"object\" : \"" + object + "\"," + "  \"contentType\" : \""
				+ BulkJobContentType.valueOf(contentType).getJobType() + "\"}";
		//" + ", \"lineEnding\" : \"CRLF\"" + "
		return jsonValue;
	}

}
