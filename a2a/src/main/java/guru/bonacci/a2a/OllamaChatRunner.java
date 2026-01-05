package guru.bonacci.a2a;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OllamaChatRunner implements CommandLineRunner {

	private final ChatClient chatClient;
	
	 public OllamaChatRunner(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools) {

     this.chatClient = chatClientBuilder
             .defaultToolCallbacks(tools)
             .build();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Ollama 3.2 chat POC...");

	  CallResponseSpec resp = chatClient.prompt(
	  		"""
	  			Discover use the highest priority connector for `sendWeatherEvent` via the MCP tool `getConnectors`"
	  			Only for the highest priority connector, generate a concise weather forecast for Paris today and send it via the MCP tool `sendWeatherEvent`. 
	  			Also, send a funny oneliner to MCP tool `sendJokes` without quotes.
	  			
	  			In the end, tell me what you did.
	  		""")
  		.call();

//	  CallResponseSpec resp = chatClient.prompt("Show me the available connectors for a the tool `send-weather-events` via the MCP tool `getConnectors`").call();
    // Print the model responses
    System.out.println(resp.content());
	}  
}
