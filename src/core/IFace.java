package core;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mmacias on 08/02/14.
 */
public interface IFace {
    int add(int a, int b);
    String concat(String s1, String s2);
    FakeClass getFake(long l, String s, char c, OtherFake o);
    int[] doubleArray(int[] arr);
    List<String> asString(int[] arr);
    Map<String,Integer> wordHistogram(String text);
    int echo(String sting);
    ArrayList<Float> getPrediction(String sessionID, int FutureValues);
    void setMeasure(String id, Long timestamp, int sessionBW, float retransmissionRate, float videoQoE, int representationRate, int Jitter, int ServerBW);
    List<InetAddress> getPeers(InetAddress IP, List<InetAddress> peers);
    List<InetAddress> getAccessPoints(InetAddress IP, List<InetAddress> peers, String requiredResources);
    
    //Ordered list of DomainAccessPoint_ID}=getAccessPoints(MSM_ID, User_IPAddress, RequiredResources)
    // {Ordered list of ContentPeer_ID}=getPeers(DestinationPeer; {List of ContentPeer_ID}; RequiredResources)
   }
