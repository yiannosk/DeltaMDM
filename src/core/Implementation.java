package core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


public class Implementation implements IFace {
	int deliveryMethod=0; 
	//boolean performingAdaptation=false;
	public static final int CLOUD=1;
	//the amount of people who have voted
	public static final int CLOUD_CDN=2;
	//the amount of people who are currently voting
	public static final int CLOUD_CDN_P2P = 3;
    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public String concat(String s1, String s2) {
        return s1+s2;
    }

   
    @Override
    public FakeClass getFake(long l, String s, char c, OtherFake o) {
        FakeClass f = new FakeClass();
        f.property1 = l;
        f.stringProperty = s;
        f.charProperty = c;
        f.other = o;
        return f;
    }

    @Override
    public int[] doubleArray(int[] arr) {
        for(int i = 0 ; i < arr.length ; i++) {
            arr[i] = arr[i] * 2;
        }
        return arr;
    }

    @Override
    public List<String> asString(int[] arr) {
        List<String> ret = new ArrayList<>();
        for(int i : arr) {
            ret.add(String.valueOf(i));
        }
        return ret;
    }

    @Override
    public Map<String,Integer> wordHistogram(String text) {
        Map<String,Integer> hist = new HashMap<>();
        for(String w : text.split(" ")) {
            Integer c = hist.get(w);
            if(c == null) {
                c = 0;
            }
            c++;
            hist.put(w,c);
        }
        return hist;
    }
    
    public int echo(String string)
    {
    	System.out.println("ECHO "+string);
    	return 4;
    }
    
    public void getMeasurements()
    {
    	
    }
    public ArrayList<Float> getPrediction(String session, int futuresteps)
    {
  //  System.out.println("Predicting "+ futuresteps+ " of "+ session);
    RPEngine rpe=RPEngine.getInstance();
   // System.out.println("Predicting "+ futuresteps+ " of "+ session);
    ArrayList<Float> data;
   // System.out.println("Predicting "+ futuresteps+ " of "+ session);
	MeasuredEntity mv=HistoryData.getMAServerMeasuredEntity(session);
//	System.out.println("Predicting "+ futuresteps+ " of "+ session);
	data=mv.sortValuesForVideoQoE();
//	System.out.println("Predicting "+ futuresteps+ " of "+ session);
	return rpe.predict(data,futuresteps);
    }

    public ArrayList<Integer> getBWServerPrediction(String session, int futuresteps)
    {
  //  System.out.println("Predicting "+ futuresteps+ " of "+ session);
    RPEngine rpe=RPEngine.getInstance();
   // System.out.println("Predicting "+ futuresteps+ " of "+ session);
    ArrayList<Integer> data;
   // System.out.println("Predicting "+ futuresteps+ " of "+ session);
	MeasuredEntity mv=HistoryData.getMAServerMeasuredEntity(session);
//	System.out.println("Predicting "+ futuresteps+ " of "+ session);
	data=mv.sortValuesForServerBW();
//	System.out.println("Predicting "+ futuresteps+ " of "+ session);
	return rpe.predictInt(data,futuresteps);
    }

/*    public ArrayList<Integer> getBWClientPrediction(String session, int futuresteps)
    {
  //  System.out.println("Predicting "+ futuresteps+ " of "+ session);
    RPEngine rpe=RPEngine.getInstance();
   // System.out.println("Predicting "+ futuresteps+ " of "+ session);
    ArrayList<Integer> data;
   // System.out.println("Predicting "+ futuresteps+ " of "+ session);
	MeasuredEntity mv=HistoryData.getMeasuredEntity(session);
//	System.out.println("Predicting "+ futuresteps+ " of "+ session);
	data=mv.sortValuesForClientBW();
//	System.out.println("Predicting "+ futuresteps+ " of "+ session);
	return rpe.predictInt(data,futuresteps);
    }
        */
	@Override
	public void setMeasure(String id, Long timestamp, int sessionBW, float retransmissionRate, float videoQoE, int representationRate, int jitter, int serverBW) {
		System.out.println("Session :"+ id+" timestamp: "+ timestamp+" clientBW: "+ sessionBW+ " serverBW: "+ serverBW);
		String Server="";
		String Client="";
		Measurement m=new Measurement();
		m.packetLoss=retransmissionRate;
		m.videoQoE=videoQoE;
		m.sessionBW=sessionBW;
		m.serverBW=serverBW;
		m.representationRate=representationRate;
		m.jitter=jitter;
		if (id.length()>24)
		{
		for(int i=0;i<12;i+=3)
		{
			Server+=id.subSequence(i, i+3);
			if (i!=9) Server+=".";
		}
		for(int j=13;j<25;j+=3)
		{
			Client+=id.subSequence(j, j+3);
			if (j!=22) Client+=".";
		}
		System.out.println(Server+ " "+ Client);
		///// Got IPs of Server and Client
		if (Server.contentEquals("000.000.000.000"))
			{
			System.out.println("Client measurement");
			MeasuredEntity me=HistoryData.getClientsMeasuredEntity(Client);
			me.setTypos(1);
			me.addValue(timestamp, m);
			}
		if (Client.contentEquals("000.000.000.000")) 
			{
			if (Server.contentEquals("091.184.204.017") || Server.contentEquals("091.184.204.027"))
			{
				System.out.println("MAS Server measurement");
				MeasuredEntity me3=HistoryData.getMAServerMeasuredEntity(Server);
				me3.addValue(timestamp, m);
			//	me3.
				try {
					HistoryData.MAS_Servers.put(InetAddress.getByName(Server), m.serverBW);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("EHG measurement");
				MeasuredEntity me2=HistoryData.getEHGServerMeasuredEntity(Server);
				me2.setTypos(3);
				me2.addValue(timestamp, m);
				try {
					HistoryData.EHG_Servers.put(InetAddress.getByName(Server), m.serverBW);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			}
		}		
			
		
		
		
	}
    
	public void Algorithm1(float lowlimit,float highlimit, float currentValue,float predictedValue)
	{
		float value= Math.max(currentValue,predictedValue);
		if (value<=lowlimit)
		{
			setDeliveryMethod(1);
			System.out.println("Only Cloud");
		}
		if (value>lowlimit && value<=highlimit)
		{
			setDeliveryMethod(2);
			System.out.println("Cloud + CDN");
		}
		if (value>highlimit)
		{
			setDeliveryMethod(3);
			System.out.println("Cloud + CDN + P2P");
		}
		}
	
	public void Algorithm2(String session, int limit, int predictedClientBW, int currentClientBW, int predictedServerBW, int currentServerBW )
	{
		int clientBW= Math.min(currentClientBW,predictedClientBW);
		int serverBW= Math.min(currentServerBW,predictedServerBW);
		if (serverBW<limit && clientBW<limit)
		{
			System.out.println("Low Server and Client bandwidth ... Reducing Representation ... not activated");
		//	HistoryData.pastData.get(session).setQualityAlert(false);
		//	switchRepresentation(HistoryData.pastData.get(session).getClient().toString(),"5000000");
		}
		if (serverBW<limit && clientBW>=limit)
		{
			System.out.println("Problem on Server --- Low Server Bandwidth --- Switching Server");
			HistoryData.MAServersData.get(session).setQualityAlert(false);
			HistoryData.addServer(HistoryData.MAServersData.get(session).getServer(), serverBW);
			try {
				switchServer(HistoryData.MAServersData.get(session).getClient().toString(),session);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (clientBW<limit && serverBW>=limit)
		{
			System.out.println("Problem on Client --- Low Client Bandwidth --- Changing Representation ... not activated");
		//	HistoryData.pastData.get(session).setQualityAlert(false);
			//switchRepresentation(HistoryData.pastData.get(session).getClient().toString(),"5000000");
		}
	}
	
	public void Algorithm4(String session, int limit, int predictedServerBW, int currentServerBW )
	{
		int serverBW= Math.max(currentServerBW,predictedServerBW);
		if (serverBW>limit)
		{
			System.out.println("Problem on Server --- Server Bandwidth: " + serverBW);
			HistoryData.MAServersData.get(session).setQualityAlert(false);
			HistoryData.addServer(HistoryData.MAServersData.get(session).getServer(), serverBW);
			try {
				//switchServer(HistoryData.MAServersData.get(session).getClient().toString());
				switchServer("91.184.204.37",session);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public void switchServer(String UserIP, String session) throws UnknownHostException {
		System.out.println("Switching Server");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 
		HttpPost request = new HttpPost("http://91.184.204.25:8502");
		//InetAddress UserIP=InetAddress.getByName("1.2.3.4");
		//List<InetAddress> listOf_MAS_IPs=getPeers(UserIP, HistoryData.MAS_Servers);
		//Iterator<InetAddress> ite = listOf_MAS_IPs.iterator(); 
	    Set<InetAddress> MAS_list= HistoryData.sortServers();
	    String servers="";
		servers+=MAS_list;
	    servers=servers.replace('/','"');
	    servers=servers.replace(",","\",");
	    servers=servers.replace("]","\"]");
	    System.out.println(MAS_list);
	    //String MAS_list="[\"91.184.204.17\", \"91.184.204.27\"]";
		//System.out.println(MAS_autolist);
		//System.out.println(MAS_list);
		//String UserIP="1.2.3.4";
	    //while (ite.hasNext()){
		//	MAS_list+=ite.toString();
		//}
		String bitrate="5000000";
		StringEntity params;
		try {
			params = new StringEntity("{\"jsonrpc\":\"2.0\",\"params\" : {\"UserIP\": \""+ UserIP+ "\", \"listOf_MAS_IPs\" : "+ servers +", \"bitrate\": "+ bitrate+ "}, \"method\": \"RunAdaptation\", \"id\": 0}");
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			System.out.println("Re-enabling adaptation");
			HistoryData.MAServersData.get(session).setQualityAlert(true);
		}
		
		
	}

	private void switchRepresentation(String UserIP, String bitrate) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 
		HttpPost request = new HttpPost("http://91.184.204.25:8502");
		String MAS_list="\"\"";
		StringEntity params;
		try {
			params = new StringEntity("{\"params\" : {\"UserIP\": \""+ UserIP+ "\", \"listOf_MAS_IPs\" : "+ MAS_list +", \"bitrate\": "+ bitrate+ "}, \"method\": \"RunAdaptation\", \"id\": 0}");
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*public void qualityAlert()
	{
		System.out.println("QualityCheck1");
		ArrayList<Float> predictionArray;
		Set<String> measuredEnt=HistoryData.getServerMeasuredEntities();
		Iterator<String> iterator = measuredEnt.iterator(); 
	     while (iterator.hasNext()){
	    	String session=iterator.next();
	//    	System.out.println("Value: "+session + " "); 
	    	predictionArray=this.getPrediction(session, 2);
	    	MeasuredEntity me=HistoryData.getMAServerMeasuredEntity(session);
	    	float lowL=me.lowLimit;
	    	float highLimit=me.highLimit;
	// 		System.out.println("OK"+lowL+" "+highLimit);
	 		float predictedValue=predictionArray.get(1);
	 		float currentValue=predictionArray.get(0);
//	 		System.out.println("OK");
	 		this.Algorithm1(lowL, highLimit,currentValue, predictedValue);	    	 
		   } 
	 //    System.out.println("QualityAlertDone");
	}
	
	
	
	
	public void qualityAlert2()
	{
		System.out.println("QualityCheck2");
		ArrayList<Integer> predictionServerArray,predictionClientArray;
		Set<String> measuredEnt=HistoryData.getMeasuredEntities();
		Iterator<String> iterator = measuredEnt.iterator(); 
	     while (iterator.hasNext()){
	    	String session=iterator.next();
	    	if (HistoryData.pastData.get(session).getQualityAlert()==true)
	    	{
	//    	System.out.println("Value: "+session + " "); 
	    	predictionServerArray=this.getBWServerPrediction(session, 2);
	    	predictionClientArray=this.getBWClientPrediction(session, 2);
	    	
	// 		System.out.println("OK"+lowL+" "+highLimit);
	 		int predictedServerValue=predictionServerArray.get(1);
	 		int currentServerValue=predictionServerArray.get(0);
	 		
	 		int predictedClientValue=predictionClientArray.get(1);
	 		int currentClientValue=predictionClientArray.get(0);
//	 		System.out.println("OK");
	 		System.out.println("predictedClientValue " + predictedClientValue + " currentClientValue " + currentClientValue+ " predictedServerValue " + predictedServerValue + " currentServerValue "+ currentServerValue);
	 		this.Algorithm2(session, 1000000, predictedClientValue, currentClientValue, predictedServerValue, currentServerValue);	    	 
		   }
	     }
	 //    System.out.println("QualityAlertDone");
	}*/
	
/*	public void qualityAlert3()
	{
		System.out.println("QualityCheck3");
		ArrayList<Float> predictionArray;
		Set<String> measuredEnt=HistoryData.getMAServerMeasuredEntities();
		Iterator<String> iterator = measuredEnt.iterator(); 
	     while (iterator.hasNext()){
	    	String session=iterator.next();
	//    	System.out.println("Value: "+session + " "); 
	    	predictionArray=this.getPrediction(session, 2);
	    	MeasuredEntity me=HistoryData.getMAServerMeasuredEntity(session);
	    	float lowL=me.lowLimit;
	    	float highLimit=me.highLimit;
	// 		System.out.println("OK"+lowL+" "+highLimit);
	 		float predictedValue=predictionArray.get(1);
	 		float currentValue=predictionArray.get(0);
//	 		System.out.println("OK");
	 		this.Algorithm1(lowL, highLimit,currentValue, predictedValue);	    	 
		   } 
	 //    System.out.println("QualityAlertDone");
	}*/
	
	public void qualityAlert4()
	{
		System.out.println("QualityCheck4");
		ArrayList<Integer> predictionServerArray,predictionClientArray;
		Set<String> measuredEnt=HistoryData.getMAServerMeasuredEntities();
		Iterator<String> iterator = measuredEnt.iterator(); 
	     while (iterator.hasNext()){
	    	String session=iterator.next();
	    	if (HistoryData.MAServersData.get(session).getQualityAlert()==true)
	    	{
	//    	System.out.println("Value: "+session + " "); 
	    	predictionServerArray=this.getBWServerPrediction(session, 2);
	    	//predictionClientArray=this.getBWClientPrediction(session, 2);
	    	
	// 		System.out.println("OK"+lowL+" "+highLimit);
	 		int predictedServerValue=predictionServerArray.get(1);
	 		int currentServerValue=predictionServerArray.get(0);
	 		
	 		System.out.println("PredictedServerValue " + predictedServerValue + " currentServerValue "+ currentServerValue);
	 		this.Algorithm4(session, 4000000, predictedServerValue, currentServerValue);	    	 
		   }
	     }
	 //    System.out.println("QualityAlertDone");
	}
	
	
	
	private void setDeliveryMethod(int deliveryM) {
		deliveryMethod=deliveryM;				
	}
	
	public int getDeliveryMethod() {
		return deliveryMethod;				
	}

	
	@Override
	public List<InetAddress> getPeers(InetAddress IP, List<InetAddress> peers) {
		List<InetAddress> lista=peers;
		System.out.println("getPeers.... returning peers");
		if (lista.isEmpty()) return lista;
		Set<InetAddress> set;
		set=HistoryData.sortEHGServers();
		List<InetAddress> sortedlist = new ArrayList<InetAddress>();
		Iterator<InetAddress> itere=set.iterator();
		while (itere.hasNext())
		{
			InetAddress thenext=itere.next();
			if (peers.contains(thenext))
				sortedlist.add(thenext);
		}
		if (sortedlist.isEmpty())
		{
			System.out.println("No measurements -> random placement");
			long seed = System.nanoTime();
	        Collections.shuffle(lista, new Random(seed));
	        return lista;
	    }
		return sortedlist;
	}

	@Override
	public List<InetAddress> getAccessPoints(InetAddress IP, List<InetAddress> peers, String RequiredResources) {
		System.out.println("getAccessPoints.......");
		Set<InetAddress> set;
		set=HistoryData.sortServers();
		List<InetAddress> sortedlist = new ArrayList<InetAddress>(set);
		return sortedlist;

	}

	
}