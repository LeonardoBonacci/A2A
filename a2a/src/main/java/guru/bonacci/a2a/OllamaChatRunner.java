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
             .defaultSystem("Please prioritise context information for answering questions")
             .defaultToolCallbacks(tools)
             .build();
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Ollama 3.2 chat POC...");

	  CallResponseSpec resp = chatClient.prompt("Generate a concise weather forecast for Paris today and send it via the MCP tool `sendWeatherEvent`.").call();

    // Print the model responses
    System.out.println(resp.content());
	}  
}
