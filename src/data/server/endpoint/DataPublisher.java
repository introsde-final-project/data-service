package data.server.endpoint;

import data.server.ws.DataImplementation;

import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;

/**
 * Created by bishruti on 1/2/16.
 */
public class DataPublisher {
    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException {
        String PROTOCOL = "http://";
        String HOSTNAME = InetAddress.getLocalHost().getHostAddress();
        if (HOSTNAME.equals("127.0.0.1"))
        {
            HOSTNAME = "localhost";
        }
        String PORT = "8005";
        String BASE_URL = "/data";

        if (String.valueOf(System.getenv("PORT")) != "null"){
            PORT=String.valueOf(System.getenv("PORT"));
        }

        String endpointUrl = PROTOCOL+HOSTNAME+":"+PORT+BASE_URL;
        System.out.println("Starting Data Service...");
        System.out.println("--> Published. Check out "+endpointUrl+"?wsdl");
        Endpoint.publish(endpointUrl, new DataImplementation());
    }
}
