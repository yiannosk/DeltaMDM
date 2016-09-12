package core;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class MeasuredEntity {

	//public enum typos {MAS_SERVER=1, EHG_SERVER=2, CLIENT=3};
	public String sessionID;
	public HashMap<Long,Measurement> measuredValues;
	public float lowLimit;
	public float highLimit;
	private boolean qualityAlert;
	private InetAddress ipClient;
	private InetAddress ipServer;
	int typos=0;
	
	public void setServer(InetAddress ip)
	{
		ipServer=ip;
	}
	
	public void setTypos(int typos)
	{
		this.typos=typos;
	}
	
	public void setClient(InetAddress ip)
	{
		ipClient=ip;
	}
	
	public InetAddress getClient ()
	{
		return ipClient;
	}
	
	public InetAddress getServer ()
	{
		return ipServer;
	}
	
	public int getTypos ()
	{
		return this.typos;
	}
	
	public MeasuredEntity(String session)
	{
		sessionID=session;
		measuredValues=new HashMap<Long,Measurement>();
	}
	
	public void addValue(Long timestamp, Measurement m)
	{
		measuredValues.put(timestamp,m);
	}
	
	public boolean getQualityAlert()
	{
		return qualityAlert;
	}
	
	public void setQualityAlert(boolean alert)
	{
		this.qualityAlert=alert;
	}
	
	public void sortandprint()
	{
		Set<Long> set = measuredValues.keySet();
		ArrayList<Long> list = new ArrayList<Long>();
		list.addAll(set);
		Collections.sort(list);
		for (Long key : list) {
		  //  System.out.println(key + ": " + measuredValues.get(key).videoQoE);
		}
	}
	
	/*
	public void removeOldValuesOfArray()
	{
		HashMap<Long,Measurement> measuredValuesNew= new HashMap<Long,Measurement>();
		if (measuredValues!=null)
		{
			if (measuredValues.size()>100)
			{
				System.out.println("Removing some of the past measurements for session: "+ this.sessionID);
				Set<Long> set = measuredValues.keySet();
				ArrayList<Long> list = new ArrayList<Long>();
				list.addAll(set);
				Collections.sort(list);
				Iterator it= list.iterator();
				for (Long key : list) {
					measuredValuesNew.put(key, measuredValues.get(key));
				    }
				measuredValues.clear();
				for(int i=0;i<50;i++)
				{
					measuredValues.put(arg0, arg1)
				}
			}
			
		}
	}
	*/
	public ArrayList<Float> sortValuesForVideoQoE()
	{
		ArrayList<Float> videoQoE= new ArrayList<Float>();
		if (measuredValues==null) return videoQoE;
		Set<Long> set = measuredValues.keySet();
		ArrayList<Long> list = new ArrayList<Long>();
		list.addAll(set);
		Collections.sort(list);
		
		for (Long key : list) {
			videoQoE.add(measuredValues.get(key).videoQoE);
		 //   System.out.println(key + ": " + measuredValues.get(key).videoQoE);
		}
		return videoQoE;
	}
	
	public ArrayList<Integer> sortValuesForServerBW()
	{
		ArrayList<Integer> serverBW= new ArrayList<Integer>();
		if (measuredValues==null) return serverBW;
		Set<Long> set = measuredValues.keySet();
		ArrayList<Long> list = new ArrayList<Long>();
		list.addAll(set);
		Collections.sort(list);
		
		for (Long key : list) {
			serverBW.add(measuredValues.get(key).serverBW);
		   // System.out.println(key + ": " + measuredValues.get(key).serverBW);
		}
		return serverBW;
	}
	
	public ArrayList<Integer> sortValuesForClientBW()
	{
		ArrayList<Integer> clientBW= new ArrayList<Integer>();
		if (measuredValues==null) return clientBW;
		Set<Long> set = measuredValues.keySet();
		ArrayList<Long> list = new ArrayList<Long>();
		list.addAll(set);
		Collections.sort(list);
		
		for (Long key : list) {
			clientBW.add(measuredValues.get(key).sessionBW);
		 //   System.out.println(key + ": " + measuredValues.get(key).sessionBW);
		}
		return clientBW;
	}
	
}
