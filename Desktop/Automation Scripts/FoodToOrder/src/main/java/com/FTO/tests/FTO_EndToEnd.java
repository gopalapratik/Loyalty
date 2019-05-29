package com.FTO.tests;

import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FTO.Util.TestUtil;
import com.FTO.requests.UserRequest;


//check initialization, object creation, data pass through constructor and all prerequisites
public class FTO_EndToEnd 
{
	UserRequest ur;
	TestUtil tu;
	String finalGetURL="",getRequestResponseJSON,finalPostURL;
	HashMap<String, String> getRequestHeaders, postRequestHeaders;
	int responsecode;
	JSONObject jsobject1;
	String proxyHost="bcproxy";
	int proxyPort= 8080;
	String proxyUser = "EXTMASGG";
	String proxyPassword = "Jan@2019" ;
	
	public FTO_EndToEnd()
	{
		ur=new UserRequest();
		tu=new TestUtil();
	}
	
	@BeforeTest
	public void setUp()
	{
		getRequestHeaders=new HashMap<String,String>();
		postRequestHeaders=new HashMap<String, String>();
		tu.readPropertiesFile();
	}
	
	/*@DataProvider(name="OrderNumbers")
	public Object[][] getOrderNumbers()
	{
		return new Object[][]
		{
			{""},
			{""},
			{""},
			{""},
			{""}
		};
	}*/
	
	@Test//(dataProvider="OrderNumbers")
	public void testFTOServices()
	{
		
		HttpHost proxy = new HttpHost("wmmproxyservice", 8080, "http");
		ur.httpclient = HttpClientBuilder.create().setProxy(proxy).build();
		finalGetURL=tu.prop.getProperty("getapiurl")+"CO-0075079";
		System.out.println(finalGetURL);
		getRequestHeaders.put("Content-Type", "application/json");
		getRequestHeaders.put("ews-apiKey", "08ff-81b8-4be3-aa1cfefcf149:7a90-d580-48cb-eaff984dd4d2");
		getRequestHeaders.put("ews-App-Id", "5c45e8e7efb73d718fc8ab72");
		ur.response=ur.getRequestWithHeaders(finalGetURL, getRequestHeaders);
		System.out.println(ur.response);
		responsecode=ur.response.getStatusLine().getStatusCode();
		System.out.println(responsecode);
		Assert.assertEquals(responsecode, 200, "Response Code Not as Expected");//Expected check current working of prop file otherwise use direct code
		getRequestResponseJSON=tu.getResponseJSONAsString(ur.response);
		//Perform Field Validation------Before this I guess can get JSON object and then validate
		/*finalPostURL=tu.prop.getProperty("postapiurl")+tu.prop.getProperty("postapiendpoint");
		postRequestHeaders.put("", "");//check how many headers need to add
		ur.response=ur.postRequestWithHeadersPayload(finalPostURL, getRequestResponseJSON, postRequestHeaders);
		responsecode=ur.response.getStatusLine().getStatusCode();
		/*in get also using same response variable and response code variables and many common variables
		will it create any problem -------------need to check*/
	//	Assert.assertEquals(responsecode, tu.prop.getProperty("postRequestResponseCode"), "Response Code Not as Expected");//Expected check current working of prop file otherwise use direct code
		
	}
	/*public static void main(String[] args)
	{
		FTO_EndToEnd fEE=new FTO_EndToEnd();
		fEE.testFTOServices();
	}*/
}
