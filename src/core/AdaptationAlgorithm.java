package core;

import java.util.TimerTask;
import java.util.Date;

// Create a class extends with TimerTask
public class AdaptationAlgorithm extends TimerTask {
	Implementation imp=new Implementation();
	Date now; // to display current time
 
	// Add your task here
	public void run() {
		now = new Date(); // initialize date
		System.out.println("Time is :" + now); // Display current time
		//imp.qualityAlert();
		for (int i=0;i<19;i++)
		{
			imp.qualityAlert4();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	/*		
		while(true) {
		    long millis = System.currentTimeMillis();
		    imp.qualityAlert2();
		    try {
				Thread.sleep(1000 - millis % 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
	}
}