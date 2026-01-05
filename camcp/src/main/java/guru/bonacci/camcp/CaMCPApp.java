package guru.bonacci.camcp;

import java.time.Instant;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class CaMCPApp implements ApplicationRunner {

	private final WeatherService tester;
	
	public static void main(String[] args) {
		SpringApplication.run(CaMCPApp.class, args);
	}
	
  @Bean
  ToolCallbackProvider weatherTools(WeatherService weatherService) {
      return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
  }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		tester.sendWeatherEvent("Berlin " + Instant.now());
	}
}
