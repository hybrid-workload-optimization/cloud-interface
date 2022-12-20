package kr.co.strato.cloud.aks.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import kr.co.strato.cloud.aks.plugin.model.CreateMessageData;
import kr.co.strato.cloud.aks.plugin.model.DeleteMessageData;
import kr.co.strato.cloud.aks.plugin.model.ModifyMessageData;
import kr.co.strato.cloud.aks.plugin.model.ScaleMessageData;

@Service
public class AKSInterfaceAsyncService {

	public void asyncProvisioningCluster(@Valid CreateMessageData messageData) {
		// TODO Auto-generated method stub
		
	}

	public void asyncDeleteCluster(@Valid DeleteMessageData messageData) {
		// TODO Auto-generated method stub
		
	}

	public void asyncScaleCluster(@Valid ScaleMessageData messageData) {
		// TODO Auto-generated method stub
		
	}

	public void asyncModifyCluster(@Valid ModifyMessageData messageData) {
		// TODO Auto-generated method stub
		
	}
	
}
