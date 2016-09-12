package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Ignore;
import org.junit.Test;

import core.IFace;
import core.IFace2;


import easyrpc.client.ClientFactory;
import easyrpc.client.protocol.amqp.AmqpClient;
import easyrpc.client.protocol.http.HttpClient;
import easyrpc.client.serialization.jsonrpc.JSONCaller;
import easyrpc.server.protocol.RpcClient;

public class testMSM {

	@Ignore
	@Test
	public void testServeradaptation() throws URISyntaxException, MalformedURLException {
		
	//	ClientFactory stubGenerator = new ClientFactory(
      //          new HttpClient(new URI("http://91.184.204.24:8080"), "rpc/core.IFace"),
       //         new JSONCaller());
		
	//	ClientFactory stubGenerator = new ClientFactory(new AmqpClient(new URI("amqp://91.184.204.24:8080"), "rpc/core.IFace"),new JSONCaller());
		
	ClientFactory stubGenerator = new ClientFactory( new HttpClient("91.184.204.24", 8080, "rpc"),new JSONCaller());
		IFace obj = (IFace) stubGenerator.instantiate(IFace.class);
		
		for (int i=0;i<90;i++)
	 	{
			 long millis = System.currentTimeMillis();
	 		try {
				Thread.sleep(1000 - millis % 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	//	if (i==15) {obj.getPrediction("sessionTEST3", 10);}
	 		obj.setMeasure("sessionTEST3",new Long(10000+i), 10000-100*i, 0, 0, 0, 0,10000);
	 	}
	}
	
	@Ignore
	@Test
	public void test2() throws URISyntaxException, MalformedURLException {
		
	//	ClientFactory stubGenerator = new ClientFactory(
      //          new HttpClient(new URI("http://91.184.204.24:8080"), "rpc/core.IFace"),
       //         new JSONCaller());
		
	//	ClientFactory stubGenerator = new ClientFactory(new AmqpClient(new URI("amqp://91.184.204.24:8080"), "rpc/core.IFace"),new JSONCaller());
		
	ClientFactory stubGenerator = new ClientFactory( new HttpClient("91.184.204.24", 8080, "rpc"),new JSONCaller());
		IFace obj = (IFace) stubGenerator.instantiate(IFace.class);
	 	
		obj.setMeasure("sessionX",new Long(10000), 0, 0, 0, 0, 0,0);
	}
	
	@Ignore
	@Test
	public void testMSM1() throws URISyntaxException, MalformedURLException {
		
	ClientFactory stubGenerator = new ClientFactory( new AmqpClient(new URI("amqp://91.184.204.25:8502"),"rpcQueue"),new JSONCaller());
		IFace2 obj = (IFace2) stubGenerator.instantiate(IFace2.class);
		ArrayList<String> peers = new ArrayList<String>();
	 	peers.add("1.2.3.5");
	 	peers.add("1.2.3.6");
	//	obj.RunAdaptation("1.2.3.4", peers, 7878787);
	}
	
	
	/*@Test
	public void testMSM2() throws URISyntaxException, MalformedURLException, InterruptedException {
		
//	ClientFactory stubGenerator = new ClientFactory( new HttpClient("91.184.204.25", 8502, "rpc"),new JSONCaller());

	ClientFactory stubGenerator = new ClientFactory( new RpcClient("91.184.204.25", 8502, "rpc"),new JSONCaller());

	new RpcClient();
	IFace2 obj = (IFace2) stubGenerator.instantiate(IFace2.class);
		//ArrayList<String> peers = new ArrayList<String>();
	 	//peers.add("1.2.3.5");
	 	//peers.add("1.2.3.6");
	 	String[] peers2={"3.2.2.2","1.2.3.4"};
		System.out.println(obj.RunAdaptation("46.251.105.181","", "7878787"));
	}*/
	
	@Ignore
	@Test
	public void testMSM3() throws URISyntaxException, MalformedURLException, InterruptedException {
		
	ClientFactory stubGenerator = new ClientFactory( new HttpClient("91.184.204.25", 8502, "rpc"),new JSONCaller());

	IFace2 obj = (IFace2) stubGenerator.instantiate(IFace2.class);
		//ArrayList<String> peers = new ArrayList<String>();
	 	//peers.add("1.2.3.5");
	 	//peers.add("1.2.3.6");
	 	String[] peers2={"3.2.2.2","1.2.3.4"};
		obj.RunAdaptation("46.251.105.181",peers2, "7878787");
	}
	
	@Test
	public void testMSM4() throws UnsupportedEncodingException, ClientProtocolException {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 
		HttpPost request = new HttpPost("http://91.184.204.25:8502");
		StringEntity params;
		try {
			params = new StringEntity("{\"params\" : {\"UserIP\": \"1.2.3.4\", \"listOf_MAS_IPs\" : \"\", \"bitrate\": 5000000}, \"method\": \"RunAdaptation\", \"id\": 0}");
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error");
			e.printStackTrace();
		}
		
	}





}
