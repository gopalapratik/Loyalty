package com.FTO.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.formula.functions.T;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil 
{
	public Properties prop;
	File f;
	FileInputStream fis;
	Header[] resHeaders;
	List<Header> hlist;
	List<String> jsonResponseValues;
	Map<String, String> responseJSONalues;
	String headerValue,mimeTypeValue,payloadString,responseJsonBody,jsonValue,singleJSONKey;
	ContentType ct;
	Header headerVal;
	T result=null;
	ObjectMapper mapper;
	JSONObject jsonobject;
	JSONArray arrayInJSONResponse;
	Iterator<String> jsonKeysAll;
	public TestUtil()
	{
		mapper=new ObjectMapper();
	}
	public void readPropertiesFile()
	{
		prop=new Properties();
		f=new File("C:\\Gouri\\Automation\\FoodToOrder\\configuration\\config.properties");
		try {
			fis=new FileInputStream(f);
			prop.load(fis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getContentType(CloseableHttpResponse response)
	{
		ct=ContentType.getOrDefault(response.getEntity());//Q.what is entity ,getEntity() and getOrDefault
		mimeTypeValue=ct.getMimeType();
		return mimeTypeValue;
	}
	public String getResponseHeaders(CloseableHttpResponse response, String headerName)
	{
		resHeaders=response.getAllHeaders();
		hlist=Arrays.asList(resHeaders);
		for(Header h:hlist)
		{
			if(h.getName().equalsIgnoreCase(headerName))
			{
				headerValue=h.getValue();
				break;
			}
			
		}
		if(headerValue.isEmpty())
		{
			//try to write throw new RuntimeException
			System.out.println("Header Value is not available for header name="+headerName);
		}
		return headerValue;
	}
	/*Understand and try to write headers java 8 way
	 * public void getResponseHeaderValueJava8Way(CloseableHttpResponse response, String headerName)
	{
		hlist=Arrays.asList(response.getAllHeaders());
		headerVal=hlist.stream().filter(header->headerName.equalsIgnoreCase(header.getName())).findFirst().orElseThrow(new RuntimeErrorException(null, "Headr Value not found"));
	}*/
	
	public String javaObjectToJSON(Object object)
	{
		try {
			payloadString=mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payloadString;
	}
	public T jsonToJavaObject(String jsonString, Class<T> clsName)//what is T and exact explanation
	{
		try {
			result=mapper.readValue(jsonString, clsName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;	
	}
	public JSONObject getJSONObject(CloseableHttpResponse response)// is this method same as unmarshalling?
	{
		
			try {
				responseJsonBody=EntityUtils.toString(response.getEntity());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				jsonobject=new JSONObject(responseJsonBody);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//here we are converting this to get key value pair only right?
			return jsonobject;
			//in this code i want to write try-catch instead of throws but facing issue
	}
	public String getResponseJSONAsString(CloseableHttpResponse response)
	{
		try {
			responseJsonBody=EntityUtils.toString(response.getEntity());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseJsonBody;
	}
	public List<String> validateSimpleResponseJSON(CloseableHttpResponse response)
	{
//		jsonResponseValues=new ArrayList<String>();
		responseJSONalues=new HashMap<String,String>();
		try {
			jsonobject=getJSONObject(response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonKeysAll=jsonobject.keys();
		while(jsonKeysAll.hasNext())
		{
			singleJSONKey=jsonKeysAll.next();
			try {
				jsonValue=jsonobject.getString(singleJSONKey);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			responseJSONalues.put(singleJSONKey, jsonValue);

		}
		return jsonResponseValues;
	}
	public JSONArray validateNestedJSONArrayObject(CloseableHttpResponse response, String arrayName)
	{
		//validating nested json https://www.youtube.com/watch?v=h5VLKYOQOjM 
		try {
			/*responseJsonBody=EntityUtils.toString(response.getEntity());
			jsonobject=new JSONObject(responseJsonBody);*/
			
			//how can we get return type of each and every value in json response?
			//Before starting automation should we identify manually as structure will remain same?
			jsonobject=getJSONObject(response);
			arrayInJSONResponse=jsonobject.getJSONArray(arrayName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayInJSONResponse;
	}
	public String validateStringObjectNestedJSON(CloseableHttpResponse response, String keyName)
	{
		try
		{
			/*responseJsonBody=EntityUtils.toString(response.getEntity());
			jsonobject=new JSONObject(responseJsonBody);*/
			jsonobject=getJSONObject(response);
			jsonValue=jsonobject.getString(keyName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return jsonValue;
	}
	/*public void handleProxySettings()
	{
		final String authUser="SVC_XXCIUSER";
		final String authPass="Holly7time2soon";
		
		System.setProperty("http.ProxyHost", "wmmproxyservice");
		System.setProperty("http.ProxyPort", "8080");
		System.setProperty("http.ProxyUser", authUser);
		System.setProperty("http.ProxyPassword", authPass);
		
		Authenticator.setDefault(new Authenticator() 
		{
			public PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(authUser, authPass.toCharArray());
				
			}
		});
	}*/
}
