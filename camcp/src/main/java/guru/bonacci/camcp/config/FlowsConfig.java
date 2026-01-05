package guru.bonacci.camcp.config;

import java.util.Map;

public record FlowsConfig(Map<String, ToolConfig> tools, Map<String, Connector> connectors) {
}