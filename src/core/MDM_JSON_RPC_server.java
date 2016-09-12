package core;

import easyrpc.client.ClientFactory;
import easyrpc.client.protocol.http.HttpClient;
import easyrpc.client.serialization.jsonrpc.JSONCaller;
import easyrpc.server.RpcServer;
import easyrpc.server.serialization.jsonrpc.JSONCallee;
import easyrpc.server.protocol.http.HttpService;
import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public class MDM_JSON_RPC_server{
	
    static RpcServer server;
    IFace obj;
    public static void main(String args[]) throws InterruptedException, MalformedURLException, UnknownHostException
    {
    System.out.println("Setting up");
    HistoryData hd=new HistoryData();
    HttpService httpS= new HttpService(8080,"/rpc");
    JSONCallee jsonC= new JSONCallee();
    server = new RpcServer(httpS,jsonC);
    //server = new RpcServer(new HttpService(8080,"/rpc"),new JSONCallee());
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
            	System.out.println("Starting RPEngine");
            	RPEngine rpe=RPEngine.getInstance();
            	System.out.println("Starting RPEngine:OK");
            }
        });
        th.start();
        Thread.sleep(200000);
  }
    
    public void tearDown() throws Exception {
        this.tearDown();
        System.out.println("Stoping Server");
        server.stop();
        System.out.println("Stoping Server:OK");
    }

}