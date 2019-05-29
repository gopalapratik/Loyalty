package com.FTO.requests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

public class UserRequest 
{
	public CloseableHttpClient httpclient;
	public CloseableHttpResponse response;
	HttpGet getRequest;
	HttpPost postRequest;
	public CloseableHttpResponse getRequest(String apiUrl) throws HttpHostConnectException
	{
		
		getRequest=new HttpGet(apiUrl);
		
		try {
			response=httpclient.execute(getRequest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public CloseableHttpResponse getRequestWithHeaders(String apiurl, HashMap<String, String> requestHeaders)
	{
		getRequest=new HttpGet(apiurl);
		for(Map.Entry<String, String> entry:requestHeaders.entrySet())
		{
			getRequest.addHeader(entry.getKey(), entry.getValue());
		}
		
		try {
			response=httpclient.execute(getRequest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	public CloseableHttpResponse postRequestWithOnlyPayload(String apiurl, String payload) //currently dont require passing headers HashMap<String, String> requestHeaders
	{
		postRequest=new HttpPost(apiurl);
		/*for(Map.Entry<String, String> entry:requestHeaders.entrySet())
		{
			postRequest.addHeader(entry.getKey(), entry.getValue());
		}*/
		try {
			postRequest.setEntity(new StringEntity(payload));
			response=httpclient.execute(postRequest);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	public CloseableHttpResponse postRequestWithHeadersPayload(String apiurl, String payload,HashMap<String, String> requestHeaders) //currently dont require passing headers HashMap<String, String> requestHeaders
	{
		postRequest=new HttpPost(apiurl);
		for(Map.Entry<String, String> entry:requestHeaders.entrySet())
		{
			postRequest.addHeader(entry.getKey(), entry.getValue());
		}
		try {
			postRequest.setEntity(new StringEntity(payload));
			response=httpclient.execute(postRequest);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
