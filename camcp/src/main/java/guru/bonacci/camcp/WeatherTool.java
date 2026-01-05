package guru.bonacci.camcp;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import guru.bonacci.camcp.config.ConnectorInfo;
import guru.bonacci.camcp.config.ConnectorRegistry;
import guru.bonacci.camcp.config.EndpointResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherTool {

	private final ProducerTemplate template;
	private final EndpointResolver endpoints;
	private final ConnectorRegistry registry;
	

	@Tool(name = "get-connectors", description = "Returns the connectors available for a given tool")
	public List<ConnectorInfo> getConnectors(String toolName) {
		return List.of(new ConnectorInfo("kafka-weather", 100), new ConnectorInfo("kafka-weather2", 50));
	}
	
	private static final String SEND_RANDOM_EVENTS_TOOLNAME = "send-random-events";
	
	@Tool(name = SEND_RANDOM_EVENTS_TOOLNAME, description = "Send a random joke")
	public String sendJokes(String joke) {
		
		log.info("joking {}", joke);

		for (String endpoint : endpoints.resolveEndpointsForTool("send-random-events")) {
			log.info("sending to {}", endpoint);
			template.sendBody(endpoint, joke);
		}

		return "ok";
	}

	private static final String SEND_WEATHER_EVENTS_TOOLNAME = "send-weather-events";
	
	@Tool(name = SEND_WEATHER_EVENTS_TOOLNAME, description = "Send weather forecast for a specific city")
	public String sendWeatherEvent(String weather, String connectorName) {
		
		log.info("Weather request '{}' using connector '{}'", weather, connectorName);
		
//		for (String endpoint : endpoints.resolveEndpointsForTool(SEND_WEATHER_EVENTS_TOOLNAME)) {
//			log.info("sending to {}", endpoint);
//			template.sendBody(endpoint, weather);
//		}

		String endpoint = endpoints.resolveEndpointsForConnector(SEND_WEATHER_EVENTS_TOOLNAME, connectorName);
		log.info("sending to {}", endpoint);
		template.sendBody(endpoint, weather);

		return "ok";
	}
}