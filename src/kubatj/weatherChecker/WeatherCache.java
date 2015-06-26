package kubatj.weatherChecker;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService(targetNamespace="http://webservices/kubatj")
@SOAPBinding(style=Style.RPC, use=Use.LITERAL, parameterStyle=ParameterStyle.WRAPPED)
public interface WeatherCache {
	
	@WebMethod(operationName="getWeatherForLocation")
	String getWeatherForLocation(
			@WebParam(name="locationJson") String locationJson
		);
	
	@WebMethod(operationName="loadSelection")
	String loadSelection(
			@WebParam(name="countryCode") String countryCode,
			@WebParam(name="zip") String zip
		);
	
	@WebMethod(operationName="saveSelection")
	void saveSelection(
			@WebParam(name="locationJson") String locationJson,
			@WebParam(name="countryCode") String countryCode,
			@WebParam(name="zip") String zip
		);
}
