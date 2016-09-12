package test;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import core.HistoryData;
import core.Implementation;
import core.MeasuredEntity;

public class ImplementationTest {

	@Ignore
	@Test
	public void testAlgorithm1() {
		Implementation imp=new Implementation();
		imp.Algorithm1((float)0.54,(float) 0.75, (float)0.76, (float)0.75);
		System.out.println(imp.getDeliveryMethod());
	}
	
	@Ignore
	@Test
	public void testAlgorithm2() {
		Implementation imp=new Implementation();
		imp.Algorithm2("sessionY", 8000, 9000,9000, 7000,6000);
		System.out.println(imp.getDeliveryMethod());
	}

	@Ignore
	@Test
	public void testAlgorithm2_2() throws UnknownHostException {
		HistoryData hd= new HistoryData();
		Implementation imp=new Implementation();
		imp.Algorithm2("session1",10000, 15000, 15000, 7000,6000);
		//System.out.println(imp());
	}
	
	@Test
	public void testSwitchServer() throws UnknownHostException {
		HistoryData hd= new HistoryData();
		Implementation imp=new Implementation();
		imp.switchServer("1.2.3.4","session"); 
		
		//System.out.println(imp());
	}
	
	@Test
	public void testgetPeers() throws UnknownHostException
	{
		HistoryData hd= new HistoryData();
		Implementation imp=new Implementation();
		InetAddress IP=InetAddress.getLoopbackAddress();
		List<InetAddress> peers = new ArrayList<InetAddress>();
		peers.add(InetAddress.getByName("1.2.3.4"));
		peers.add(InetAddress.getByName("1.2.3.5"));
		peers.add(InetAddress.getByName("1.2.3.6"));
		peers.add(InetAddress.getByName("1.2.3.7"));
		HistoryData.EHG_Servers.put(InetAddress.getByName("1.2.3.4"), 1);
		HistoryData.EHG_Servers.put(InetAddress.getByName("1.2.3.5"), 1000);
		HistoryData.EHG_Servers.put(InetAddress.getByName("1.2.3.6"), 10);
		HistoryData.EHG_Servers.put(InetAddress.getByName("1.2.3.8"), 10000);
		HistoryData.EHG_Servers.put(InetAddress.getByName("1.2.3.9"), 10000);
		List<InetAddress> resp= imp.getPeers(IP, peers);
		System.out.println(resp);
	}
	
	@Test
	public void testgetPeers2() throws UnknownHostException
	{
		HistoryData hd= new HistoryData();
		Implementation imp=new Implementation();
		InetAddress IP=InetAddress.getLoopbackAddress();
		List<InetAddress> peers = new ArrayList<InetAddress>();
		peers.add(InetAddress.getByName("1.2.3.4"));
		peers.add(InetAddress.getByName("1.2.3.5"));
		peers.add(InetAddress.getByName("1.2.3.6"));
		peers.add(InetAddress.getByName("1.2.3.7"));
		List<InetAddress> resp= imp.getPeers(IP, peers);
		System.out.println(resp);
	}
	
		
		
}
