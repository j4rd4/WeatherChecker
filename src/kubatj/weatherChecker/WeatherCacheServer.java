package kubatj.weatherChecker;

import javax.xml.ws.Endpoint;

public class WeatherCacheServer {

    protected WeatherCacheServer() throws Exception {
        System.out.println("Starting Server");
        Object cacheImplementor = new WeatherCacheImpl();
        String cacheAddress = "http://localhost:8080/WeatherChecker/services/WeatherCacheImplPort";
        Endpoint.publish(cacheAddress, cacheImplementor);
        
        Object checkerImplementor = new WeatherCheckerImpl();
        String checkerAddress = "http://localhost:8080/WeatherChecker/services/WeatherCheckerImplPort";
        Endpoint.publish(checkerAddress, checkerImplementor);
    }
    
    public static void main(String args[]) throws Exception { 
        new WeatherCacheServer();
        System.out.println("Server ready..."); 
        
        Thread.sleep(50 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
