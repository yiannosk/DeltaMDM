package core;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mmacias on 08/02/14.
 */
public interface IFace2 {
	
	//void RunAdaptation(InetAddress IP,List<InetAddress> peers,String bitrate);

	String[] RunAdaptation(String UserIP, String[] peers2, String bitrate);
	String RunAdaptation(String UserIP, String peers2, String bitrate);
	}
