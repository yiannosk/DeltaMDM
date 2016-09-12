package test;

import easyrpc.client.ClientFactory;
import easyrpc.client.protocol.http.HttpClient;
import easyrpc.client.serialization.jsonrpc.JSONCaller;
import easyrpc.server.RpcServer;
import easyrpc.server.serialization.jsonrpc.JSONCallee;
import easyrpc.server.protocol.http.HttpService;
import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

import org.junit.Ignore;

import core.FakeClass;
import core.IFace;
import core.Implementation;
import core.OtherFake;


public class Test extends TestCase {
    RpcServer server;
    IFace obj;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        System.out.println("Setting up");
        server = new RpcServer(new HttpService(8080,"/rpc"),new JSONCallee());
        System.out.println("Setting up:OK");
        System.out.println("Adding endpoin");
        server.addEndpoint(new Implementation());
        System.out.println("Adding endpoint:OK");

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
            	System.out.println("Starting Server");
            	server.start();
            	System.out.println("Starting Server:OK");
            }
        });
        th.start();
        Thread.sleep(200000);
        HttpClient httpclient=new HttpClient("localhost", 8080, "/rpc");
        JSONCaller caller=new JSONCaller();
        ClientFactory theFactory= new ClientFactory(httpclient,caller);
        obj= (IFace) theFactory.instantiate(IFace.class);
        //obj = new Implementation();
        IFace obj2 = (IFace) new ClientFactory(new HttpClient("localhost", 8080, "/rpc"), new JSONCaller()).instantiate(IFace.class);

    }
    

    @org.junit.Test
    public void testBasicHttpCalls() throws Exception {
    	
    	// HttpClient httpclient=new HttpClient("localhost", 8080, "/rpc");
        // JSONCaller caller=new JSONCaller();
       //  ClientFactory theFactory= new ClientFactory(httpclient,caller);
    	IFace faceobj=new Implementation();
         System.out.println("LLamando a concat: " + faceobj.concat("left", "right"));
        assertEquals(faceobj.add(2, 3), 5);
//       faceobj.doSomeStupidStuff("Hola Mundo!");
//       faceobj.doSomething();
   }

    
    @org.junit.Test
    public void testPOJOCalls() throws Exception {
        OtherFake of = new OtherFake();
        of.int1 = 1;
        of.int2 = 2;
        FakeClass ret = obj.getFake(1L,"aeiou",'z',of);
        assertEquals((long)ret.property1, 1L);
        assertEquals(ret.charProperty, 'z');
        assertEquals(ret.stringProperty, "aeiou");
        assertEquals((int)of.int1, 1);
        assertEquals((int)of.int2, 2);
        System.out.println("Mola");

    }
   
    @org.junit.Test
    public void testArrays() {
        int[] in = { 1, 2, 3, 4, 5, 6, -2 };
        int[] out = obj.doubleArray(in);
        for(int i = 0 ; i < in.length ; i++) {
            assertEquals(in[i], out[i] / 2);
        }
    }
    @org.junit.Test
    public void testList() {
        int[] in = new int[] { 1,2,3,4,5,6,10,222 };
        List<String> out = obj.asString(in);
        for(int i = 0 ; i < out.size() ; i++) {
            assertEquals((int)Integer.valueOf(out.get(i)), in[i]);
        }
    }
    @org.junit.Test
    public void testMaps() {
        Map<String,Integer> wc = obj.wordHistogram("el perro de san roque no tiene rabo perro perro rabo");
        assertNull(wc.get("tralariero"));
        assertEquals((int) wc.get("el"), 1);
        assertEquals((int)wc.get("perro"),3);
        assertEquals((int)wc.get("de"),1);
        assertEquals((int)wc.get("san"),1);
        assertEquals((int)wc.get("roque"),1);
        assertEquals((int)wc.get("no"),1);
        assertEquals((int)wc.get("tiene"),1);
        assertEquals((int)wc.get("rabo"),2);
    }

    public void fakeMethod(int a, String b, FakeClass o) {}
    @org.junit.Test
    public void testAmqpJson() throws Throwable {

        FakeClass fc = new FakeClass();
        fc.property1 = 1L; fc.stringProperty = "hola"; fc.charProperty='c';
        fc.other = new OtherFake();
        fc.other.int1 = 125;
        fc.other.int2 = null;

        JSONCaller caller = new JSONCaller();
        byte[] json = caller.serializeCall((Object)null,this.getClass().getMethod("fakeMethod", int.class, String.class, FakeClass.class),
                new Object[] { 666, "777", fc });

        assertTrue(new String(json).startsWith("{\"jsonrpc\":\"2.0\",\"method\":\"fakeMethod\",\"params\":[666,\"777\",{\"property1\":1,\"stringProperty\":\"hola\",\"charProperty\":\"c\",\"other\":{\"int1\":125,\"int2\":null}}],\"id\":"));
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        System.out.println("Stoping Server");
        server.stop();
        System.out.println("Stoping Server:OK");
    }

}