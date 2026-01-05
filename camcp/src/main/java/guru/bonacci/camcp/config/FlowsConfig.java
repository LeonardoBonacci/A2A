package guru.bonacci.camcp.config;

import java.util.List;
import java.util.Map;

public record FlowsConfig(Map<String, ToolConfig> tools, Map<String, Connector> connectors) {
}

// tool config for a single tool
record ToolConfig(List<String> connectors) {
}

// connector definition
record Connector(String endpoint, Map<String, Object> params) {
}