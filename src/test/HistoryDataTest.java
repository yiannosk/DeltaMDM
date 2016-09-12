package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Ignore;
import org.junit.Test;

import core.HistoryData;
import core.Implementation;
import core.MeasuredEntity;
import core.Measurement;

public class HistoryDataTest {

	@Test
	public void testHistoryData() throws UnknownHostException {
		HistoryData hd= new HistoryData();
	}

	@Test
	public void testAddME() throws UnknownHostException {
		HistoryData hd= new HistoryData();
		MeasuredEntity me= HistoryData.getMAServerMeasuredEntity("091.184.204.017");
		System.out.println(me.sessionID);
	}
	

	@Test
	public void testAddValues() throws UnknownHostException {
		HistoryData hd= new HistoryData();
		MeasuredEntity me= HistoryData.getMAServerMeasuredEntity("000000000000-091184204017");
		System.out.println(me.sessionID);
		Measurement m= new Measurement();
		m.packetLoss=0;
		m.videoQoE=(float) 0.87;
		me.addValue((long) 1234, m);
		MeasuredEntity me2= HistoryData.getMAServerMeasuredEntity("000000000000-091184204017");
		System.out.println(me2.sessionID);
		Measurement m2= new Measurement();
		m2.packetLoss=1;
		m2.videoQoE=(float) 0.98;
		me.addValue((long) 14, m2);
		HistoryData.MAServersData.get("000000000000-091184204017").sortandprint();
		System.out.println("LEs:"+HistoryData.getMAServerMeasuredEntities().toString());
		System.out.println(HistoryData.MAServersData.get("000000000000-091184204017").getQualityAlert());
	}
	
	@Test
	public void testaddMore() throws UnknownHostException {
		HistoryData hd= new HistoryData();
		MeasuredEntity me= HistoryData.getMAServerMeasuredEntity("000000000000-091184204017");
		MeasuredEntity me2= HistoryData.getMAServerMeasuredEntity("000000000000-091184204027");
		MeasuredEntity me3= HistoryData.getMAServerMeasuredEntity("000000000000-091184204037");
		System.out.println("LEs:"+HistoryData.getMAServerMeasuredEntities().toString());
		HistoryData.MAServersData.get("000000000000-091184204027").setQualityAlert(false);
		System.out.println(HistoryData.MAServersData.get("000000000000-091184204017").getQualityAlert());
		System.out.println(HistoryData.MAServersData.get("000000000000-091184204027").getQualityAlert());
		System.out.println(HistoryData.MAServersData.get("000000000000-091184204037").getQualityAlert());
	}
	
/*	@Test
	public void testSortPrint() {
		HistoryData hd= new HistoryData();
		MeasuredEntity me1=new MeasuredEntity("session1");
		Measurement m1= new Measurement();
		m1.packetLoss=0;
		m1.videoQoE="0.87";
		me1.addValue((long) 1234, m1);
		Measurement m2= new Measurement();
		m2.packetLoss=0;
		m2.videoQoE="0.88";
		me1.addValue((long) 2344, m2);
		MeasuredEntity me2=new MeasuredEntity("session2");
		MeasuredEntity me3=new MeasuredEntity("session3");
		HistoryData.addMeasuredEntity("session1", me1);
		HistoryData.addMeasuredEntity("session2", me2);
		HistoryData.addMeasuredEntity("session3", me3);
		HistoryData.sortandprint();
		HistoryData.pastData.get("session1").sortandprint();
		
	}
	*/
	
	//@Ignore
	@Test
	public void testQualityAlert() throws UnknownHostException
	{
		HistoryData hd= new HistoryData();   
		Implementation imp=new Implementation();
		   imp.qualityAlert4();
		   System.out.println("OKquality");
	}
	
	@Test
	public void testIPs()
	{
		InetAddress ipClient = null,ipServer = null;
		String ipstr="091184204027-091184204037";
		String Server="";
		String Client="";
		char[] newipstr = null;
		for(int i=0;i<12;i+=3)
		{
			Server+=ipstr.subSequence(i, i+3);
			if (i!=9) Server+=".";
		}
		for(int j=13;j<25;j+=3)
		{
			Client+=ipstr.subSequence(j, j+3);
			if (j!=22) Client+=".";
		}
		System.out.println(Server+ " "+ Client);
		try {
			ipClient=InetAddress.getByName(Client);
			ipServer=InetAddress.getByName(Server);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ipClient);
		System.out.println(ipServer);
	}
	
	@Test
	public void testServersSort() throws UnknownHostException
	{
		HistoryData hd= new HistoryData();
	//	MeasuredEntity me= HistoryData.getMeasuredEntity("session6");
//		MeasuredEntity me4= HistoryData.getMeasuredEntity("session5");
//		MeasuredEntity me5= HistoryData.getMeasuredEntity("session4");
		System.out.println(HistoryData.sortServers());
		System.out.println("[\"91.184.204.17\", \"91.184.204.27\"]");
	}
	

	@Test
	public void testAddServers() throws UnknownHostException
	{
		HistoryData hd= new HistoryData();
	InetAddress newSer=InetAddress.getByName("91.184.204.27");
		HistoryData.addServer(newSer, 50000000);
		//	MeasuredEntity me= HistoryData.getMeasuredEntity("session6");
//		MeasuredEntity me4= HistoryData.getMeasuredEntity("session5");
//		MeasuredEntity me5= HistoryData.getMeasuredEntity("session4");
		System.out.println(HistoryData.sortServers());
	//	System.out.println("[\"91.184.204.17\", \"91.184.204.27\"]");
	}
		
		
}
