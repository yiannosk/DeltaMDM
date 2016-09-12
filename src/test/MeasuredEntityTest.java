package test;

import java.util.ArrayList;

import org.junit.Test;

import core.MeasuredEntity;
import core.Measurement;

public class MeasuredEntityTest {

	@Test
	public void testMeasuredEntity() {
		MeasuredEntity me=new MeasuredEntity("session1");
	}

	@Test
	public void testAddValue() {
		MeasuredEntity me=new MeasuredEntity("session1");
		Measurement m=new Measurement();
		m.packetLoss=0;
		m.videoQoE=(float) 0.97;
		m.sessionBW=1600;
		me.addValue((long) 12345, m);
	}

	@Test
	public void testSortandprint() {
		MeasuredEntity me=new MeasuredEntity("session1");
		Measurement m=new Measurement();
		m.packetLoss=0;
		m.videoQoE=(float) 0.9;
		m.sessionBW=1600;
		Measurement m2=new Measurement();
		m2.packetLoss=0;
		m2.videoQoE=(float) 0.92;
		m2.sessionBW=1000;
		Measurement m3=new Measurement();
		m3.packetLoss=0;
		m3.videoQoE=(float) 0.93;
		m3.sessionBW=1300;
		me.addValue((long) 1900, m);
		me.addValue((long) 1700, m2);
		me.addValue((long) 1800, m3);
		me.sortandprint();
	}
	@Test
	public void testsortValuesForVideoQoE()
	{
		MeasuredEntity mer =new MeasuredEntity("session541");
		Measurement m1=new Measurement();
		m1.packetLoss=0;
		m1.videoQoE=(float) 0.9;
		m1.sessionBW=1600;
		Measurement m2=new Measurement();
		m2.packetLoss=0;
		m2.videoQoE=(float) 0.92;
		m2.sessionBW=1000;
		Measurement m3=new Measurement();
		m3.packetLoss=0;
		m3.videoQoE=(float) 0.93;
		m3.sessionBW=1300;
		mer.addValue((long) 2, m1);
		mer.addValue((long) 1, m2);
		mer.addValue((long) 3, m3);
		mer.sortValuesForVideoQoE();
	}
	
	@Test
	public void testsortValuesForClientBW()
	{
		MeasuredEntity mer =new MeasuredEntity("session41");
		Measurement m1=new Measurement();
		m1.packetLoss=0;
		m1.videoQoE=(float) 0.9;
		m1.sessionBW=1600;
		Measurement m2=new Measurement();
		m2.packetLoss=0;
		m2.videoQoE=(float) 0.92;
		m2.sessionBW=1000;
		Measurement m3=new Measurement();
		m3.packetLoss=0;
		m3.videoQoE=(float) 0.93;
		m3.sessionBW=1300;
		mer.addValue((long) 2, m1);
		mer.addValue((long) 1, m2);
		mer.addValue((long) 3, m3);
		mer.sortValuesForClientBW();
	}
	
	@Test
	public void testsaddServer()
	{
		
	}

}
