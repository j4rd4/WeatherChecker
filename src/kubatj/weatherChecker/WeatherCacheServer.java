package kubatj.weatherChecker;

import javax.xml.ws.Endpoint;

public class WeatherCacheServer {

    protected WeatherCacheServer() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new WeatherCacheImpl();
        String address = "http://localhost:8080/WeatherChecker/services/WeatherCacheImplPort";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws Exception { 
        new WeatherCacheServer();
        System.out.println("Server ready..."); 
        
        Thread.sleep(50 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
