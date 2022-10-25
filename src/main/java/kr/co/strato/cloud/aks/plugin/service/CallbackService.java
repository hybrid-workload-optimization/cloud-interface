package kr.co.strato.cloud.aks.plugin.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import kr.co.strato.cloud.aks.plugin.model.CallbackData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CallbackService {
	
	private final long RETRY_SLEEP_MILLIS = 20000;	// 2 초
	private final long RETRY_COUNT = 3;	// 6번.
	
	@Autowired
	@Qualifier("restService")
	RestTemplate restService;
	
	
	
	private <T> ResponseEntity<String> sendCallbackPost(String url , T param) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<T> request = new HttpEntity<>(param, headers);		
		return  restService.postForEntity(url, request, String.class);
		
	}
	
	/**
	 * 콜백 전송.
	 * @param callBackUrl
	 * @param data
	 */
	public void sendCallback(String callBackUrl, CallbackData data) {
		try {
			
			String s = data.getStatus();
			int code = data.getCode();
			Object r = data.getResult();
			
			for(int cnt = 0; cnt < RETRY_COUNT; cnt++) {	
				try {
					log.info("[Send Callback] >>> URL: {}", callBackUrl);
					log.info("[Send Callback] >>> Status: {}: ", s);
					log.info("[Send Callback] >>> Code: {}", code);
					if(r != null) {
						log.info("[Send Callback] >>> ResultBody: "+ new Gson().toJson(r).toString());
					}
					
					
					URI url = new URI(callBackUrl);
					ResponseEntity<String> resp = sendCallbackPost(url.toString(), data);
					String status = resp.getStatusCode().toString(); 
		
					log.info("[Send Callback Result:] >>> Resp status: " + status );		
					break;
				} catch(Exception e){
					log.error("[Send Callback MLId :] >>> SEND CALLBACK FAIL : Exception " + e.getMessage()+" retry cnt = "+cnt);
					log.info("[retry -sleep({}msec) Send Callback MLId : {}, url : {}", RETRY_SLEEP_MILLIS, callBackUrl);
					Thread.sleep(RETRY_SLEEP_MILLIS);
				}
			}
		} catch(Exception ex) {
			log.error("retry all fail.. callback:{}", callBackUrl);
			log.error(ex.getMessage(), ex.fillInStackTrace());
		}
	}
}
