package guru.bonacci.camcp.config;

import java.util.Map;

public record Connector(Integer priority, String name, String endpoint, Map<String, Object> params) {
}