package guru.bonacci.camcp.config;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EndpointResolver {

	private final ConnectorRegistry connectorRegistry;


	public List<String> resolveEndpointsForTool(String toolName) {
		ToolConfig tool = connectorRegistry.getToolConfig(toolName);
		return tool.connectors().stream().map(connectorRegistry.getConnectors()::get).map(this::buildUri).filter(Objects::nonNull).toList();
	}

	public String resolveEndpointsForConnector(String toolName, String connectorName) {
		ToolConfig tool = connectorRegistry.getToolConfig(toolName);
		return tool.connectors().stream().map(connectorRegistry.getConnectors()::get)
				.filter(connector -> connector.name().equals(connectorName)).map(this::buildUri).findFirst().get();
	}

	private String buildUri(Connector connector) {
		if (connector == null) {
			return null;
		}
		
		StringBuilder uri = new StringBuilder(connector.endpoint());
		if (connector.params() != null && !connector.params().isEmpty()) {
			uri.append("?");
			connector.params().forEach((k, v) -> uri.append(k).append("=").append(v).append("&"));
			uri.setLength(uri.length() - 1);
		}
		return uri.toString();
	}
}