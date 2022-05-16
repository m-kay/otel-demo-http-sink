package com.mkay.github.sink;

import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BatchHttpSinkApplication {

	private static final Logger LOG = LoggerFactory.getLogger(BatchHttpSinkApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BatchHttpSinkApplication.class, args);
	}

	@Bean
	public Consumer<Message<List<String>>> batchSink(RestTemplateBuilder restTemplateBuilder) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		return message -> {
			LOG.info("Consuming batch of messages");
			restTemplate.postForEntity("https://httpbin.org/post", message.getPayload(), String.class);
			LOG.info("Sent batch to http api");
		};
	}

	@Bean
	public Consumer<Message<String>> singleSink(RestTemplateBuilder restTemplateBuilder) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		return message -> {
			LOG.info("Consuming single message");
			restTemplate.postForEntity("https://httpbin.org/post", message.getPayload(), String.class);
			LOG.info("Sent single message to http api");
		};
	}
}
