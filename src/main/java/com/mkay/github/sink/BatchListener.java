package com.mkay.github.sink;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BatchListener {

	private static final Logger LOG = LoggerFactory.getLogger(BatchListener.class);

	@Autowired
	private RestTemplate restTemplate;

	@KafkaListener(topics = "demo-input-topic", batch = "true", groupId = "annotated-batch-consumer")
	public void consumeBatch(List<String> messages) {
		LOG.info("Consuming batch of messages in annotated method");
		restTemplate.postForEntity("https://httpbin.org/post", messages, String.class);
		LOG.info("Sent batch to http api in annotated method");
	}

	@KafkaListener(topics = "demo-input-topic", batch = "false", groupId = "annotated-consumer")
	public void consumeSingle(String messages) {
		LOG.info("Consuming message in annotated method");
		restTemplate.postForEntity("https://httpbin.org/post", messages, String.class);
		LOG.info("Sent to http api in annotated method");
	}
}
