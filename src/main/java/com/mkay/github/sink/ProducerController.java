package com.mkay.github.sink;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ProducerController {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@PostMapping("/produce")
	public ResponseEntity<?> produceMessage() throws ExecutionException, InterruptedException {
		kafkaTemplate.send("demo-input-topic", "Some message sent at " + LocalDateTime.now()).get();
		return ResponseEntity.ok().build();
	}
}
