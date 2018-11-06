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
		String jobURL = baseURL + Connector.SERVICE_DATA_URL + version + JOBS_INJEST;
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", Connector.BEARER + sessionId);
		Map<String, String> response = null;
		try {
			response = HttpRequest.createPOSTRequest(jobURL, getJobRequestBody(object, operation, contentType),
					headerMap);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.get("body");
	}

	public static void uploadDataBatch(String baseURL, String version, String object, String operation,
			String contentType, String sessionId, String jobId) {
		String batchURL = baseURL + Connector.SERVICE_DATA_URL + version + JOBS_INJEST + Connector.URL_SAPERATOR + jobId
				+ BATCHES;
	}

	public static String getJobRequestBody(String object, String operation, String contentType) {
		String jsonValue = "{" + "  \"operation\" : \"" + BulkJobOperation.valueOf(operation).getOperation() + "\","
				+ "  \"object\" : \"" + object + "\"," + "  \"contentType\" : \""
				+ BulkJobContentType.valueOf(contentType).getJobType() + "\"}";
		return jsonValue;
	}

}
