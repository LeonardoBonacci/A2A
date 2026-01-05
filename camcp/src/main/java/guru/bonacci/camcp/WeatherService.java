package guru.bonacci.camcp;

import org.apache.camel.ProducerTemplate;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import guru.bonacci.camcp.config.EndpointResolver;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherService {

	private final ProducerTemplate template;
	private final EndpointResolver endpoints;

	
	@Tool(description = "Get weather forecast for a specific latitude/longitude")
	public String getWeatherByLocation(int latitude, int longitude) {

		if (latitude == 10 && longitude == 10) {
			return "The forecast for latitude 10 and longitude 10 is for rainy weather";
		}
		if (latitude == 20 && longitude == 20) {
			return "The forecast for latitude 20 and longitude 20 is for stormy weather";
		}
		return "The forecast is for sunny weather";
	}
	
	@Tool(name = "send-weather-events", description = "Send weather forecast for a specific city")
	public String sendWeatherEvent(String weather) {

    for (String endpoint : endpoints.resolveEndpointsForTool("send-weather-events")) {
  		System.out.println("sending to " + endpoint);
      template.sendBody(endpoint, weather);
    }
    
		return "ok";
	}

}