package guru.bonacci.camcp;

import org.apache.camel.ProducerTemplate;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import guru.bonacci.camcp.config.EndpointResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

	private final ProducerTemplate template;
	private final EndpointResolver endpoints;

	@Tool(description = "Send a random joke")
	public String sendJokes(String joke) {
		
		log.info("joking {}", joke);

		for (String endpoint : endpoints.resolveEndpointsForTool("send-random-events")) {
			log.info("sending to {}", endpoint);
			template.sendBody(endpoint, joke);
		}

		return "ok";
	}

	
	@Tool(description = "Send weather forecast for a specific city")
	public String sendWeatherEvent(String weather) {
		
		log.info("weather request {}", weather);

		for (String endpoint : endpoints.resolveEndpointsForTool("send-weather-events")) {
			log.info("sending to {}", endpoint);
			template.sendBody(endpoint, weather);
		}

		return "ok";
	}
}