package kr.co.strato.cloud.aks.plugin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
	
    private final KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${plugin.kafka.response}")
	private String TOPIC;

    public void sendMessage(String message) {
    	
    	try {
            kafkaTemplate.send(TOPIC, message);
            log.info("Message: " + message + " sent to topic: " + TOPIC);
    	} catch(Exception e) {
    		log.error("", e);
    	}
    }
	
}
