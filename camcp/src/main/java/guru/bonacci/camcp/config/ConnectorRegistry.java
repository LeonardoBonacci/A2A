package guru.bonacci.camcp.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConnectorRegistry {

	// maps connector name -> enabled/disabled
	private final Map<String, Boolean> enabledConnectors = new ConcurrentHashMap<>();

	private final FlowConfigLoader flowConfigLoader;

	/**
	 * Initialize the registry at startup: - Registers all connectors from
	 * flows.json - Optionally, disable connectors
	 */
	@PostConstruct
	public void init() {
		// Register connectors from flows.json
		Map<String, Connector> connectorsFromJson = flowConfigLoader.getFlowsConfig().connectors();
		connectorsFromJson.keySet().forEach(name -> enabledConnectors.putIfAbsent(name, true));

		// Override connectors (these values come from a different data store)
//		disableConnector("dont-use-me");
	}

	/**
	 * Programmatically add a connector with initial enabled/disabled state
	 */
//	public void disableConnector(String connectorName) {
//		enabledConnectors.put(connectorName, false);
//	}

//	public boolean isEnabled(String connectorName) {
//		return enabledConnectors.getOrDefault(connectorName, false);
//	}

	public ToolConfig getToolConfig(String toolName) {
		return flowConfigLoader.getFlowsConfig().tools().get(toolName);
	}

	public Map<String, Connector> getConnectors() {
		return flowConfigLoader.getFlowsConfig().connectors().entrySet().stream()
//        .filter(entry -> isEnabled(entry.getKey()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}
