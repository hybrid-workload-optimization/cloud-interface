package kr.co.strato.cloud.aks.plugin.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumerService {

	@Autowired
	private ClusterJobService clusterService;
	
	@KafkaListener(topics = "${plugin.kafka.request}", groupId = "${plugin.kafka.group}", containerFactory = "kafkaListenerContainerFactory")
    public void consumerMessage(String message) throws IOException {
    	
		log.info("Listen Consumer >>> {}", message);
		
        clusterService.consumerMessage(message);
	}
	
}
