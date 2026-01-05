package guru.bonacci.camcp.config;

import java.io.InputStream;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class FlowConfigLoader {

	private FlowsConfig flowsConfig;

	/**
	 * Load flows.json from classpath on startup and validate bindings.
	 */
	@PostConstruct
	public void load() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			try (InputStream is = getClass().getClassLoader().getResourceAsStream("flows.json")) {
				if (is == null) {
					throw new IllegalStateException("flows.json not found on classpath");
				}
				flowsConfig = mapper.readValue(is, FlowsConfig.class);
			}

			validateBindings(flowsConfig);

		} catch (Exception e) {
			throw new RuntimeException("Failed to load flows.json", e);
		}
	}

	private void validateBindings(FlowsConfig config) {
		Map<String, Connector> connectors = config.connectors();

		for (Map.Entry<String, ToolConfig> toolEntry : config.tools().entrySet()) {
			String toolName = toolEntry.getKey();
			ToolConfig toolConfig = toolEntry.getValue();

			for (String connectorName : toolConfig.connectors()) {
				if (!connectors.containsKey(connectorName)) {
					throw new IllegalStateException(
							"Tool '" + toolName + "' references unknown connector '" + connectorName + "'");
				}
			}
		}
	}

	public FlowsConfig getFlowsConfig() {
		return flowsConfig;
	}
}
