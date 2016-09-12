package test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import core.HistoryData;
import core.Implementation;
import core.MeasuredEntity;
import core.Measurement;
import core.RPEngine;

public class RPEngineTest {
	@Test
	public static void main(String[] args) throws UnknownHostException {
		//RPEngine rpe=RPEngine.getInstance();
		RPEngine rpe=RPEngine.getInstance();
		ArrayList<Float> data=new ArrayList<Float>();
		ArrayList<Float> res;
		data.add((float) 0.5674682);
		data.add((float) 0.6773514);
	    data.add((float) 0.7316666);
	    data.add((float) 0.8291016);
	    data.add((float) 0.9166814);
	    data.add((float) 1.0106846);
	    data.add((float) 1.1956498);
		//System.out.println("11111.............................");
		res=rpe.predict(data,10);
				
		for (Float key : res) {
			System.out.println(key);
		}
		
		System.out.println("22222.............................");
		/////Test
		Implementation imp=new Implementation();
		HistoryData hd= new HistoryData();
/*		Measurement m10= new Measurement();
		Measurement m11= new Measurement();
		Measurement m12= new Measurement();
		Measurement m13= new Measurement();
		m10.packetLoss=0;
		m10.videoQoE=(float) 0.84;
		m10.sessionBW=5000;
		m10.serverBW=10000;
		m11.packetLoss=0;
		m11.videoQoE=(float) 0.85;
		m11.sessionBW=5000;
		m11.serverBW=10000;
		m12.packetLoss=0;
		m12.videoQoE=(float) 0.86;
		m12.sessionBW=5000;
		m12.serverBW=10000;
		m13.packetLoss=0;
		m13.videoQoE=(float) 0.87;
		m13.sessionBW=5000;
		m13.serverBW=10000;
		MeasuredEntity me= HistoryData.getMeasuredEntity("001002003004-007006005004");
		System.out.println(me.sessionID);
		me.addValue((long) 1234, m10);
		me.addValue((long) 1235, m11);
		me.addValue((long) 1236, m12);
		me.addValue((long) 1237, m13);*/
		
		Measurement m20= new Measurement();
		Measurement m21= new Measurement();
		Measurement m22= new Measurement();
		Measurement m23= new Measurement();
		m20.packetLoss=0;
		m20.videoQoE=(float) 0.54;
		m21.packetLoss=0;
		m21.videoQoE=(float) 0.55;
		m22.packetLoss=0;
		m22.videoQoE=(float) 0.56;
		m23.packetLoss=0;
		m23.videoQoE=(float) 0.57;
		MeasuredEntity me2= HistoryData.getMAServerMeasuredEntity("007.006.005.002");
		System.out.println(me2.sessionID);
		me2.addValue((long) 124, m20);
		me2.addValue((long) 125, m21);
		me2.addValue((long) 126, m22);
		me2.addValue((long) 127, m23);
		System.out.println("LEs:"+HistoryData.getMAServerMeasuredEntities().toString());
		imp.qualityAlert4();
		ArrayList<Integer> zerolist= new ArrayList<Integer>();
	//	rpe.predictInt(zerolist,2);

	}
	
	
	@Test
	public static void main2(String[] args) throws UnknownHostException {
		//RPEngine rpe=RPEngine.getInstance();
		Implementation imp=new Implementation();
		HistoryData hd= new HistoryData();
		RPEngine rpe=RPEngine.getInstance();
		ArrayList<Integer> zerolist= new ArrayList<Integer>();
		rpe.predictInt(zerolist,2);
		zerolist.add(1);
		zerolist.add(2);
		zerolist.add(3);
		zerolist.add(4);
		rpe.predictInt(zerolist, 2);

	}
	

}
