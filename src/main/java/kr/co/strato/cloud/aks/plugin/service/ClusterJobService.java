package kr.co.strato.cloud.aks.plugin.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import kr.co.strato.cloud.aks.plugin.model.CallbackData;
import kr.co.strato.cloud.aks.plugin.model.MessageData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClusterJobService implements Callbackable {
	
	private static Map<String, ClusterJobExecutor> clusterExecutorMap;
	
	@Autowired
	private CallbackService callbackService;

	@Autowired
	private KafkaProducerService kafkaProducerService;
	
	/**
	 * Kafka에 클러스터 작업 메세지를 입력한 경우 여기로 들어옴.
	 * 클러스터 프로비저닝, 삭제, 스케일 조정, 변경 요청 
	 * @param message
	 */
	public void consumerMessage(String message) {
		 
		/**
		 * 1. message -> 객체로 변환
		 * 2. 각 작업에 맞춰 ClusterJobCallable 상속 받아 작업 내용 작성한 클래스 생성
		 * 3. registryClusterJob 메소드 호출.
		 */

		log.debug("[consumerMessage] > message = {}", message);
		
		ClusterJobCallable<Object> callable = setJobCallable(message);
		
		registryClusterJob(callable);
		
	}

	/**
	 * 클러스터 작업을 등록한다.
	 * 클러스터는 한번에 한가지 작업을 수행하도록 Queue로 관리한다.
	 * @param clusterJob
	 * 			: 실제 클러스터 작업 내용을 가지고 있는 Callable
	 */
	public void registryClusterJob(ClusterJobCallable<Object> clusterJob) {

		log.debug("[registryClusterJob] > job registry cluster");
		
		String clusterName = clusterJob.getClusterName();
		
		//작업을 Queue에 등록시키고 Executor를 실행한다.
		ClusterJobExecutor executer = getClusterExecutorMap().get(clusterName);
		if(executer == null) {
			executer = new ClusterJobExecutor();
			getClusterExecutorMap().put(clusterName, executer);
		}
		
		//Job 실행 등록.
 		executer.putClusterJob(clusterJob);
		
		if(!executer.isRun()) {
			//Executor를 실행
			executer.start();
		}
	}
	
	@Override
	public void sendCallback(ClusterJobCallable callable, CallbackData data) {
		String url = callable.getCallbackUrl();
		String status = data.getStatus();
		
		// callback 전송
//		callbackService.sendCallback(url, data);
		
		Gson gson = new Gson();
		
		String sendMessage = gson.toJson(data);
		
		log.info("callback >>> {}", sendMessage);
		
		kafkaProducerService.sendMessage(sendMessage);
		
		//해당 클러스터에 작업 목록이 없는 경우 Executer 제거
		String clusterName = callable.getClusterName();		
		if(status.equals(CallbackData.STATUS_FINISH)) {
			ClusterJobExecutor executor = getClusterExecutorMap().get(clusterName);
			if(executor.getJobSize() == 0) {
				executor.stop();
				getClusterExecutorMap().remove(clusterName);
			}
		}
	}	
	
	public ClusterJobCallable<Object> setJobCallable(String message) {
		
		Gson gson = new Gson();
		
		MessageData messageData = gson.fromJson(message, MessageData.class);
		
		return null;
	}
	
	public static Map<String, ClusterJobExecutor> getClusterExecutorMap() {
		if(clusterExecutorMap == null) {
			clusterExecutorMap = Collections.synchronizedMap(new HashMap<String, ClusterJobExecutor>());
		}
		return clusterExecutorMap;
	}
	
}
