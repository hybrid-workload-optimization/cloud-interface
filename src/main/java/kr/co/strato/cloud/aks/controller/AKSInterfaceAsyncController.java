package kr.co.strato.cloud.aks.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.strato.cloud.aks.plugin.model.CreateMessageData;
import kr.co.strato.cloud.aks.plugin.model.DeleteMessageData;
import kr.co.strato.cloud.aks.plugin.model.ModifyMessageData;
import kr.co.strato.cloud.aks.plugin.model.ScaleMessageData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AKSInterfaceAsyncController {

	@Autowired
	private AKSInterfaceAsyncService aksInterfaceAsyncService;
	
	@PostMapping("/api/v1/cloud/aks/async/provisioning")
	public void asyncProvisioningCluster(@RequestBody @Valid CreateMessageData messageData) {
		
		log.debug("[asyncProvisioningCluster] param = {}", messageData.toString());
		
		aksInterfaceAsyncService.asyncProvisioningCluster(messageData);
		
	}
	
	@DeleteMapping("/api/v1/cloud/aks/async/delete")
	public void asyncDeleteCluster(@RequestBody @Valid DeleteMessageData messageData) {
		
		log.debug("[asyncDeleteCluster] param = {}", messageData.toString());
		
		aksInterfaceAsyncService.asyncDeleteCluster(messageData);
		
	}
	
	@PostMapping("/api/v1/cloud/aks/async/scale")
	public void asyncScaleCluster(@RequestBody @Valid ScaleMessageData messageData) {

		log.debug("[asyncScaleCluster] param = {}", messageData.toString());
		
		aksInterfaceAsyncService.asyncScaleCluster(messageData);
		
	}
	
	@PostMapping("/api/v1/cloud/aks/async/modify")
	public void asyncModifyCluster(@RequestBody @Valid ModifyMessageData messageData) {
		
		log.debug("[asyncModifyCluster] param = {}", messageData.toString());

		aksInterfaceAsyncService.asyncModifyCluster(messageData);
		
	}
	
}
