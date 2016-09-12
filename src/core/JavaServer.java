/*package core;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
 
class JavaServer {
    public static void main(String args[]) throws Exception {
        String fromClient;
        String toClient;
        HashMap<String, HashMap<Date, Float>> point=new HashMap<String, HashMap<Date, Float>>();
        ServerSocket server = new ServerSocket(8080);
        System.out.println("wait for connection on port 8080");
 
        DateFormat format = new SimpleDateFormat("MMddyyHHmmss");
        boolean run = true;
        while(run) {
            Socket client = server.accept();
            System.out.println("got connection on port 8080");
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
 
            fromClient = in.readLine();
            System.out.println("received: " + fromClient);
 
            if(fromClient.startsWith("GetAccessPoints")){
                String[] array=fromClient.split("\\:");
            	System.out.println(array[1]+array[2]+array[3]);
                toClient = "olleH";
                System.out.println("send olleH");
                out.println(toClient);
                System.out.println("received: " + fromClient);
 
                if(fromClient.equals("Bye")) {
                    toClient = "eyB";
                    System.out.println("send eyB");
                    out.println(toClient);
                    client.close();
                    run = false;
                    System.out.println("socket closed");
                }
            }
            if(fromClient.startsWith("GetPeers")){
                
            	toClient = "olleH";
                System.out.println("send olleH");
                out.println(toClient);
                fromClient = in.readLine();
                System.out.println("received: " + fromClient);
 
                if(fromClient.equals("Bye")) {
                    toClient = "eyB";
                    System.out.println("send eyB");
                    out.println(toClient);
                    client.close();
                    run = false;
                    System.out.println("socket closed");
                }
            }
            if(fromClient.startsWith("AccessPoints")){
            	String[] points=fromClient.split("\\|");
            	System.out.println(points.length);
            	point.clear();
            	for(int i=1;i<points.length;i++) //Ta access points
            	{
            		String[] thepoint=points[i].split(",");
            		HashMap<Date,Float> values=new HashMap<Date,Float>(); 
            		for(int j=1;j<thepoint.length;j++) //oi metriseis gia kathe access point
            		{
            		String[] timest=thepoint[j].split(";");
            		values.put(Timestamp.valueOf(timest[0]), Float.valueOf(timest[1]));
            		System.out.println("Access Point: "+thepoint[0] +" Timestamp="+timest[0]+" value="+timest[1]);
            		}
            		point.put(thepoint[0],values);
            	}
            	System.out.println(point.toString());
                if(fromClient.equals("Bye")) {
                    toClient = "eyB";
                    System.out.println("send eyB");
                    out.println(toClient);
                    client.close();
                    run = false;
                    System.out.println("socket closed");
                }
                RPEngine rpe=RPEngine.getInstance();
        		System.out.println("11111.............................");
        		Float[] data=new Float[7];
        		data[0]=(float) 0.5674682;
    		    data[1]=(float) 0.6773514;
    		    data[2]=(float) 0.7316666;
    		    data[3]=(float) 0.8291016;
    		    data[4]=(float) 0.9166814;
    		    data[5]=(float) 1.0106846;
    		    data[6]=(float) 1.1956498;
        	//	rpe.predict(data);
            }
        }
        System.exit(0);
    }
}
*/