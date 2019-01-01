package com.dataloader.dataupload;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.dataloader.connection.Connector;
import com.dataloader.handlers.HttpRequest;

//curl https://yourInstance.salesforce.com/services/data/v34.0/composite/tree/Account/ -H "Authorization: Bearer token -H "Content-Type: application/json" -d "@newrecords.json"

public class RecordsCreation {

	static String COMPOSIT_TREE = "/composite/tree/";

	public static String createRecords(String baseURL, String version, String object, String sessionId,
			String dataBody) {
		String jobURL = baseURL + Connector.SERVICE_DATA_URL + "v" + version + COMPOSIT_TREE + object;
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", Connector.BEARER + sessionId);
		headerMap.put("Content-Type", "application/json");
		Map<String, String> response = null;
		try {
			response = HttpRequest.createPOSTRequest(jobURL, dataBody, headerMap);
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
