package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RList;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

public class RPEngine {
	private static RPEngine instance = null;
	Rengine re;
	String args[]={"--no-save"};
public RPEngine(){
	re=new Rengine(args, false, new TextConsole());
    System.out.println("Rengine created, waiting for R");
    System.out.println(System.getProperty("os.name"));
    if (System.getProperty("os.name").equals("Windows 7") || System.getProperty("os.name").equals("Windows 8.1"))
    {
    	System.out.println("It is Win7!");
    	System.out.println(re.eval("getwd()"));
    	System.out.println(re.eval(".libPaths('C:/Users/Yiannos/Documents/R/win-library/3.1') "));
    	System.out.println(re.eval("library(forecast)"));
    }
    else
    {
    System.out.println(re.eval("getwd()"));
	System.out.println(re.eval(".libPaths('/home/user/R/x86_64-pc-linux-gnu-library/3.1') "));
	System.out.println(re.eval("library(forecast)"));
    }
	// the engine creates R is a new thread, so we should wait until it's ready
    if (!re.waitForR()) {
        System.out.println("Cannot load R");
        return;
    }
    Timer time = new Timer(); // Instantiate Timer Object
	AdaptationAlgorithm st = new AdaptationAlgorithm(); // Instantiate SheduledTask class
	time.schedule(st, 10000, 20000); // Create Repetitively task for every 1 secs

	// Exists only to defeat instantiation.
}

public static RPEngine getInstance() {
    if(instance == null) {
       instance = new RPEngine();
    }
    return instance;
 }



	public ArrayList<Float> predict(ArrayList<Float> data,int steps)
	{
		ArrayList<Float> arr=new ArrayList<>();
		//		Rengine re=new Rengine(null, false, new TextConsole());
//        System.out.println("Rengine created, waiting for R");
		// the engine creates R is a new thread, so we should wait until it's ready
 //       if (!re.waitForR()) {
  //          System.out.println("Cannot load R");
   //         return;
   //     }
        
    	/* High-level API - do not use RNI methods unless there is no other way
		to accomplish what you want */
		try {
		REXP x;
		String thedata="mydata <- c(";
		if (data == null || data.isEmpty()) return arr;
		for(int i=0;i<(data.size()-1);i++)
		{
			thedata+=data.get(i);
			thedata+=", ";
		}
		thedata+=data.get(data.size()-1) + ") "; 
		//re.eval("mydata <- c(0.5674682, 0.6773514, 0.7316666, 0.8291016, 0.9166814, 1.0106846, 1.1956498) ");
		re.eval(thedata);
	    //System.out.println(re.eval("mydata"));
		//System.out.println(re.eval("fit <- ets(mydata)"));
		re.eval("mydata");
		re.eval("fit <- ets(mydata)");
		//System.out.println(re.eval("forecast(fit,h=4)"));
		x=re.eval("forecast(fit,h="+steps+")");
	//	System.out.println(x);
	//	System.out.println(x.asVector().size());
	//	System.out.println("The last element "+x.asVector().lastElement().toString());
		
		//System.out.println(x.asDouble());
		//double[] array=x.asDoubleArray();
		//System.out.println(Arrays.asList(array));
	
		
		//System.out.println("OKK");
	//	RList vl = x.asList();
	//	String[] k = vl.keys();
	//	if (k!=null) {
	//		System.out.println("and once again from the list:");
	//		int i=0; while (i<k.length) System.out.println(k[i++]);
	//	}	}
		
	//	System.out.println("This is the x.Double() "+ x.asDouble());
		//re.eval("q(\"yes\")");
		
		RVector vector= x.asVector();
	//	System.out.println(vector.elementAt(4));
		REXP resu= (REXP) vector.elementAt(4);
		double[] a=resu.asDoubleArray();
		for (int j=0;j<steps;j++)
		{
			arr.add((float) ((a[j]+a[steps+j])/2.0));
		}
	//	System.out.println("Input:"+data.toString());
//		System.out.println("Prediction:"+arr.toString());
		return arr;	
		
/*		for (int i=0;i<array.length;i++)
		{
			System.out.println(array[i]);
			arr.add((float) array[i]);
		}
		return arr;
		*/
		//re.eval("data(iris)",false);
		//System.out.println(x=re.eval("iris"));
		// generic vectors are RVector to accomodate names
		//RVector v = x.asVector();
		//if (v.getNames()!=null) {
		//	System.out.println("has names:");
		//	for (Enumeration e = v.getNames().elements() ; e.hasMoreElements() ;) {
		//		System.out.println(e.nextElement());
		//	}
		//}
		// for compatibility with Rserve we allow casting of vectors to lists
		//RList vl = x.asList();
		//String[] k = vl.keys();
		//if (k!=null) {
		//	System.out.println("and once again from the list:");
		//	int i=0; while (i<k.length) System.out.println(k[i++]);
		//}			

		// get boolean array
		//System.out.println(x=re.eval("iris[[1]]>mean(iris[[1]])"));
		// R knows about TRUE/FALSE/NA, so we cannot use boolean[] this way
		// instead, we use int[] which is more convenient (and what R uses internally anyway)
		//int[] bi = x.asIntArray();
		//{
		 //   int i = 0; while (i<bi.length) { System.out.print(bi[i]==0?"F ":(bi[i]==1?"T ":"NA ")); i++; }
		 //   System.out.println("");
		//}
		
		// push a boolean array
		//boolean by[] = { true, false, false };
		//re.assign("bool", by);
		//System.out.println(x=re.eval("bool"));
		// asBool returns the first element of the array as RBool
		// (mostly useful for boolean arrays of the length 1). is should return true
		//System.out.println("isTRUE? "+x.asBool().isTRUE());

		// now for a real dotted-pair list:
		//System.out.println(x=re.eval("pairlist(a=1,b='foo',c=1:5)"));
		//RList l = x.asList();
		//if (l!=null) {
		//	int i=0;
		//	String [] a = l.keys();
		//	System.out.println("Keys:");
		//	while (i<a.length) System.out.println(a[i++]);
		//	System.out.println("Contents:");
		//	i=0;
		//	while (i<a.length) System.out.println(l.at(i++));
		//}
		//System.out.println(re.eval("sqrt(36)"));
	} catch (Exception e) {
		System.out.println("EX:"+e);
		e.printStackTrace();
	}
        return null;
	}
	
	public ArrayList<Integer> predictInt(ArrayList<Integer> data,int steps)
	{
		ArrayList<Integer> arr=new ArrayList<>();

        
    	/* High-level API - do not use RNI methods unless there is no other way
		to accomplish what you want */
		try {
		REXP x;
		String thedata="mydata <- c(";
		if (data == null || data.isEmpty()) return arr;
		for(int i=0;i<(data.size()-1);i++)
		{
			thedata+=data.get(i);
			thedata+=", ";
		}
		thedata+=data.get(data.size()-1) + ") "; 
		//re.eval("mydata <- c(0.5674682, 0.6773514, 0.7316666, 0.8291016, 0.9166814, 1.0106846, 1.1956498) ");
		re.eval(thedata);
		re.eval("mydata");
		re.eval("fit <- ets(mydata)");
		
		x=re.eval("forecast(fit,h="+steps+")");
			
		RVector vector= x.asVector();
	//	System.out.println(vector.elementAt(4));
		REXP resu= (REXP) vector.elementAt(4);
		double[] a=resu.asDoubleArray();
		for (int j=0;j<steps;j++)
		{
			arr.add((int) ((a[j]+a[steps+j])/2));
		}
	//	System.out.println("Input:"+data.toString());
	//	System.out.println("Prediction:"+arr.toString());
		return arr;	
		

	} catch (Exception e) {
		System.out.println("EX:"+e);
		e.printStackTrace();
	}
        return null;
	}
	
}