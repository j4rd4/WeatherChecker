package kubatj.weatherChecker;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService(targetNamespace="http://webservices/kubatj")
@SOAPBinding(style=Style.RPC, use=Use.LITERAL, parameterStyle=ParameterStyle.WRAPPED)
public interface WeatherCache {
	@WebMethod(operationName="getWeatherForLocation")
	String getWeatherForLocation(String location);
	@WebMethod(operationName="getCitiesForLocation")
	String getCitiesForLocation(String location);
	@WebMethod(operationName="loadSelection")
	String loadSelection(String zip);
	@WebMethod(operationName="saveSelection")
	void saveSelection(String location, String zip);
}
