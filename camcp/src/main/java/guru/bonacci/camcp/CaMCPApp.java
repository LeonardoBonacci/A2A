package guru.bonacci.camcp;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CaMCPApp {

	public static void main(String[] args) {
		SpringApplication.run(CaMCPApp.class, args);
	}
	
  @Bean
  ToolCallbackProvider weatherTools(WeatherService weatherService) {
      return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
  }
}
