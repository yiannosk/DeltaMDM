package core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HistoryData {
	//make it more generic with all data!!!
	//private static HistoryData;
	//public String SessionID;
	
	//public static HashMap<String, MeasuredEntity> pastData;
	public static HashMap<String, MeasuredEntity> EHGServersData;
	public static HashMap<String, MeasuredEntity> ClientsData;
	public static HashMap<String, MeasuredEntity> MAServersData;
	
	public static HashMap<InetAddress, Integer> MAS_Servers;
	public static HashMap<InetAddress, Integer> EHG_Servers;
	public HistoryData() throws UnknownHostException
	{
		MAServersData=new HashMap<String, MeasuredEntity>();
		ClientsData=new HashMap<String, MeasuredEntity>();
		EHGServersData=new HashMap<String, MeasuredEntity>();
		
		MAS_Servers	= new HashMap<InetAddress, Integer>();
		MAS_Servers.put(InetAddress.getByName("91.184.204.17"), 0);
		MAS_Servers.put(InetAddress.getByName("91.184.204.27"), 1);
		EHG_Servers=new HashMap<InetAddress,Integer>();
	}
	
	public static void addServer(InetAddress server, int serverBW)
	{
		MAS_Servers.put(server,serverBW);
	}
	
	public void removeServer(InetAddress server)
	{
		MAS_Servers.remove(server);
	}
	
	public static void addEHGServer(InetAddress server, int serverBW)
	{
		EHG_Servers.put(server,serverBW);
	}
	
	public void removeEHGServer(InetAddress server)
	{
		EHG_Servers.remove(server);
	}
	

	public static Set<InetAddress> sortServers()
	{
		List<InetAddress> mapKeys = new ArrayList<InetAddress>(MAS_Servers.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(MAS_Servers.values());
		Collections.sort(mapValues);
	    LinkedHashMap<InetAddress,Integer> sortedMap = new LinkedHashMap<InetAddress,Integer>();
	    Iterator<Integer> valueIt = mapValues.iterator();
	    while (valueIt.hasNext()) {
	       int val = valueIt.next();
		   Iterator<InetAddress> keyIt = mapKeys.iterator();
	       while (keyIt.hasNext()) {
		           Object key = keyIt.next();
		           String comp1 = MAS_Servers.get(key).toString();
		           String comp2 = String.valueOf(val);
		           if (comp1.equals(comp2)){
		        	   MAS_Servers.remove(key);
		               mapKeys.remove(key);
		               sortedMap.put((InetAddress) key, val);
		               break;
		           }

		       }

		   }
	    Set<InetAddress> sortedServers;
	    sortedServers=sortedMap.keySet();
	   	    
	    /*
	    String servers="";
		Set<InetAddress> serv;
	    serv=sortedMap.keySet();
	    servers+=serv;
	    servers=servers.replace('/','"');
	    servers=servers.replace(",","\",");
	    servers=servers.replace("]","\"]");
	    */
		return sortedServers;
	}
	
	public String listOfIPToString(String listServer)
	{
		String servers=listServer;
		servers=servers.replace('/','"');
	    servers=servers.replace(",","\",");
	    servers=servers.replace("]","\"]");
	    return servers;
	}

	
	public static Set<InetAddress> sortEHGServers()
	{
		List<InetAddress> mapKeys = new ArrayList<InetAddress>(EHG_Servers.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(EHG_Servers.values());
		Collections.sort(mapValues);
	    LinkedHashMap<InetAddress,Integer> sortedMap = new LinkedHashMap<InetAddress,Integer>();
	    Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
	       int val = valueIt.next();
		   Iterator<InetAddress> keyIt = mapKeys.iterator();
	       while (keyIt.hasNext()) {
		           Object key = keyIt.next();
		           String comp1 = EHG_Servers.get(key).toString();
		           String comp2 = String.valueOf(val);
		           if (comp1.equals(comp2)){
		        	   EHG_Servers.remove(key);
		               mapKeys.remove(key);
		               sortedMap.put((InetAddress) key, val);
		               break;
		           }

		       }

		   }
	    Set<InetAddress> sortedServers;
	    sortedServers=sortedMap.keySet();
	   	    
	    /*
	    String servers="";
		Set<InetAddress> serv;
	    serv=sortedMap.keySet();
	    servers+=serv;
	    servers=servers.replace('/','"');
	    servers=servers.replace(",","\",");
	    servers=servers.replace("]","\"]");
	    */
		return sortedServers;
	}
	
		
	public static void addMASServerMeasuredEntity(String server)
	{
		MeasuredEntity m= new MeasuredEntity(server);
		m.lowLimit=(float)0.75;
		m.highLimit=(float)0.9;
		m.setQualityAlert(true);
		System.out.println("Creating Measured Entity:" +server);
		///Session string to the IPs
		InetAddress ipServer=null;
		try {
			ipServer = InetAddress.getByName("2.2.2.2");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ipServer=InetAddress.getByName(server);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.setServer(ipServer);
		MAServersData.put(server, m);
	}
	
	public static void addEHGServerMeasuredEntity(String server)
	{
		MeasuredEntity m= new MeasuredEntity(server);
		m.lowLimit=(float)0.75;
		m.highLimit=(float)0.9;
		m.setQualityAlert(true);
		System.out.println("Creating Measured Entity:" +server);
		///Session string to the IPs
		InetAddress ipServer=null;
		try {
			ipServer = InetAddress.getByName("2.2.2.2");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ipServer=InetAddress.getByName(server);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EHGServersData.put(server, m);
		
	}
	
		public static void addClientMeasuredEntity(String client)
		{
			MeasuredEntity m= new MeasuredEntity(client);
			m.lowLimit=(float)0.75;
			m.highLimit=(float)0.9;
			m.setQualityAlert(true);
			System.out.println("Creating Measured Entity:" +client);
			///Session string to the IPs
			InetAddress ipClient=null;
			try {
				ipClient = InetAddress.getByName("1.1.1.1");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ipClient=InetAddress.getByName(client);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//m.setServer(ipClient);
			ClientsData.put(client, m);
		}
		
		
		
	//public static void removeMeasuredEntity(String session)
	//{
	//	pastData.remove(session);
	//}
	
	public static MeasuredEntity getMAServerMeasuredEntity(String session)
	{
		if (MAServersData.containsKey(session)==false)
			{
			System.out.println("There is no session:" +session+" Creating");
			//return null;
			addMASServerMeasuredEntity(session);
			System.out.println("Session Created");
			}
		return MAServersData.get(session);
	}
	
	public static MeasuredEntity getClientsMeasuredEntity(String session)
	{
		if (ClientsData.containsKey(session)==false)
			{
			System.out.println("There is no session:" +session+" Creating");
			//return null;
			addClientMeasuredEntity(session);
			System.out.println("Session Created");
			}
		return ClientsData.get(session);
	}
	
	
	public static MeasuredEntity getEHGServerMeasuredEntity(String session)
	{
		if (EHGServersData.containsKey(session)==false)
			{
			System.out.println("There is no session:" +session+" Creating");
			//return null;
			addEHGServerMeasuredEntity(session);
			System.out.println("Session Created");
			}
		return EHGServersData.get(session);
	}
	
//	public static void sortandprint()
//	{
//		Set<String> set = pastData.keySet();
//		ArrayList<String> list = new ArrayList<String>();
//		list.addAll(set);
//		Collections.sort(list);
//		for (String key : list) {
///		    System.out.println(key + ": " + pastData.get(key).measuredValues);
//		}
//	}
	
	public static Set<String> getMAServerMeasuredEntities()
	{
		return MAServersData.keySet();
	}
	
	public static Set<String> getEHGServerMeasuredEntities()
	{
		return EHGServersData.keySet();
	}
	
	public static Set<String> getClientMeasuredEntities()
	{
		return ClientsData.keySet();
	}
	
}
