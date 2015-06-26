package kubatj.weatherChecker;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.Holder;

@WebService(targetNamespace="http://webservices/kubatj")
@SOAPBinding(style=Style.RPC, use=Use.LITERAL, parameterStyle=ParameterStyle.WRAPPED)
public interface WeatherChecker {
	@WebMethod(operationName="getWeatherByCountryAndZIP")
	String getWeatherByCountryAndZIP(
			@WebParam(name="countryCode") String countryCode,
			@WebParam(name="zip") String zip,
			@WebParam(name="cached", mode = WebParam.Mode.OUT) Holder<Boolean> cached
		);
	
	@WebMethod(operationName="getStationsByCountryAndZIP")
	String getStationsByCountryAndZIP(
			@WebParam(name="countryCode") String countryCode,
			@WebParam(name="zip") String zip
		);

	@WebMethod(operationName="cacheLocationByCountryAndZIP")
	String cacheLocationByCountryAndZIP(
			@WebParam(name="countryCode") String countryCode,
			@WebParam(name="zip") String zip,
			@WebParam(name="locationJson") String locationJson
		);
}
