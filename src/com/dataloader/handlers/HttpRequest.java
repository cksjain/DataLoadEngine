package com.dataloader.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpRequest {

	public static Map<String, String> createPOSTRequest(String url, String requestBody,
			Map<String, String> requestHeaders) throws ClientProtocolException, IOException, URISyntaxException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(new URI(url));
		for (String key : requestHeaders.keySet()) {
			post.setHeader(key, requestHeaders.get(key));
		}
		// System.out.println(url);
		// System.out.println(requestBody);
		HttpEntity entity = new ByteArrayEntity(requestBody.getBytes("UTF-8"));
		post.setEntity(entity);
		HttpResponse responseValue = client.execute(post);
		Map<String, String> response = new HashMap<>();

		BufferedReader rd = new BufferedReader(new InputStreamReader(responseValue.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		response.put("code", String.valueOf(responseValue.getStatusLine().getStatusCode()));
		response.put("body", String.valueOf(result));
		return response;
	}

	public static Map<String, String> createGETRequest(String url, Map<String, String> requestHeaders)
			throws ClientProtocolException, IOException, URISyntaxException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(new URI(url));
		for (String key : requestHeaders.keySet()) {
			get.setHeader(key, requestHeaders.get(key));
		}
		HttpResponse responseValue = client.execute(get);
		Map<String, String> response = new HashMap<>();

		BufferedReader rd = new BufferedReader(new InputStreamReader(responseValue.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		response.put("code", String.valueOf(responseValue.getStatusLine().getStatusCode()));
		response.put("body", String.valueOf(result));
		return response;
	}
	
	public static Map<String, String> createPUTRequest(String url, Map<String, String> requestHeaders, String requestBody)
			throws ClientProtocolException, IOException, URISyntaxException {
		HttpClient client = new DefaultHttpClient();
		HttpPut put = new HttpPut(new URI(url));
		for (String key : requestHeaders.keySet()) {
			put.setHeader(key, requestHeaders.get(key));
		}
		HttpEntity entity = new ByteArrayEntity(requestBody.getBytes("UTF-8"));
		put.setEntity(entity);
		HttpResponse responseValue = client.execute(put);
		Map<String, String> response = new HashMap<>();

		BufferedReader rd = new BufferedReader(new InputStreamReader(responseValue.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		response.put("code", String.valueOf(responseValue.getStatusLine().getStatusCode()));
		response.put("body", String.valueOf(result));
		return response;
	}

}