package com.dataloader.connection;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.dataloader.enums.OrgType;
import com.dataloader.handlers.HttpRequest;

public class Connector {

	public static final String SERVICE_DATA_URL = "/services/data/";
	public static final String BEARER = "Bearer ";
	public static final String URL_SAPERATOR = "/";
	public static final String SALESFORCE_LOGIN_TEMPLATE = "<se:Envelope xmlns:se=\"http://schemas.xmlsoap.org/soap/envelope/\"><se:Header/><se:Body><login xmlns=\"urn:partner.soap.sforce.com\"><username><username_value></username><password><password_value></password></login></se:Body></se:Envelope>";
	public static final String[] SFDC_SUCCESS_RESPONSE_VALUES = { "metadataServerUrl", "passwordExpired", "serverUrl",
			"sessionId", "userId", "currencySymbol", "organizationId", "organizationName", "userEmail", "userFullName",
			"userTimeZone", "userName" };
	public static final String[] SFDC_ERROR_RESPONSE_VALUE = { "faultcode", "faultstring" };

	public static Map<String, String> loginToSalesforce(String username, String password, String loginVersion,
			String orgType) {
		OrgType org = OrgType.valueOf(orgType);
		String requestBody = SALESFORCE_LOGIN_TEMPLATE.replace("<username_value>", username).replace("<password_value>",
				password);
		String requestURL = org.getLoginURL() + loginVersion + "/";
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("SOAPAction", "\"\"");
		headerMap.put("Content-Type", "text/xml");

		Map<String, String> response = null;
		try {
			response = HttpRequest.createPOSTRequest(requestURL, requestBody, headerMap);
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
		return getLoginResponseMap(response.get("body"), Integer.valueOf(response.get("code")));
	}

	private static Map<String, String> getLoginResponseMap(String responseBody, int code) {
		Map<String, String> loginResponseMap = new HashMap<>();
		if (code == 200) {
			for (String responseValue : Connector.SFDC_SUCCESS_RESPONSE_VALUES) {
				loginResponseMap.put(responseValue,
						responseBody.substring(
								(responseBody.indexOf("<" + responseValue + ">") + responseValue.length() + 2),
								responseBody.indexOf("</" + responseValue + ">")));
			}
		} else {
			for (String responseValue : Connector.SFDC_ERROR_RESPONSE_VALUE) {
				loginResponseMap.put(responseValue,
						responseBody.substring(
								(responseBody.indexOf("<" + responseValue + ">") + responseValue.length() + 2),
								responseBody.indexOf("</" + responseValue + ">")));
			}
		}
		loginResponseMap.put("code", String.valueOf(code));
		return loginResponseMap;
	}

}