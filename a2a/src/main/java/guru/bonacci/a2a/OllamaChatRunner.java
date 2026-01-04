package guru.bonacci.a2a;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OllamaChatRunner implements CommandLineRunner {

	private final ChatClient.Builder chatClientBuilder;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Ollama 3.2 chat POC...");

		 // Build a ChatClient from the auto‑configured builder
    ChatClient chatClient = chatClientBuilder.build();
    
    // Send messages to the model
    CallResponseSpec resp = chatClient.prompt("Hello Ollama 3.2! Please respond with a short friendly greeting.").call();

    // Print the model responses
    System.out.println(resp.content());
	}  
}
