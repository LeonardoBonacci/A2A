package guru.bonacci.camcp.config;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class EndpointResolver {

	private final FlowsConfig flowsConfig;

	public EndpointResolver(FlowConfigLoader loader) {
		this.flowsConfig = loader.getFlowsConfig();
	}

	public List<String> resolveEndpointsForTool(String toolName) {
		ToolConfig tool = flowsConfig.tools().get(toolName);

		if (tool == null || tool.connectors().isEmpty()) {
			throw new IllegalStateException("No connectors defined for tool: " + toolName);
		}

		return tool.connectors().stream().map(flowsConfig.connectors()::get).map(this::buildUri).toList();
	}

	private String buildUri(Connector connector) {
		StringBuilder uri = new StringBuilder(connector.endpoint());
		if (connector.params() != null && !connector.params().isEmpty()) {
			uri.append("?");
			connector.params().forEach((k, v) -> uri.append(k).append("=").append(v).append("&"));
			uri.setLength(uri.length() - 1);
		}
		return uri.toString();
	}
}