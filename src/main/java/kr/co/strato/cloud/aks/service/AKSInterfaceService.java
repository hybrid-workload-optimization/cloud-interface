package kr.co.strato.cloud.aks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.core.exception.AzureException;
import com.azure.core.util.Context;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.containerservice.fluent.models.ManagedClusterInner;

import kr.co.strato.cloud.aks.exception.BusinessException;
import kr.co.strato.cloud.aks.exception.ErrorCode;
import kr.co.strato.cloud.aks.model.CloudParamDto;
import kr.co.strato.cloud.aks.model.CloudResponseDto.GetList;
import kr.co.strato.cloud.aks.model.CreateArg;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AKSInterfaceService {

	@Autowired
	private AzureCredential azureCredential;
	
	public String provisioningCluster(CreateArg arg) {
		
		log.debug("[provisioningCluster] start");
		
		String clientId = "";
		String clientSecret = "";
		String tenantId = "";
		String subscriptionId = "";
		
		AzureResourceManager azureResourceManager = azureCredential.getAzureAuth(clientId, clientSecret, tenantId, subscriptionId);
		
		log.debug("[provisioningCluster] >>> request cluster create");


		String clusterName = arg.getClusterName();
		String rgName = "";
		ManagedClusterInner clusterInner = new ManagedClusterInner();
		
		try {
			azureResourceManager
						.kubernetesClusters()
						.manager()
						.serviceClient()
						.getManagedClusters()
						.createOrUpdate(rgName, clusterName, clusterInner, Context.NONE);
			
		} catch (AzureException e) {
			log.error("[provisioningCluster] >>> Faild cluster create", e);
			throw new BusinessException(ErrorCode.CLUSTER_CREATE_FAILED);
		}
		
		return null;
	}
	
	public List<GetList> getListCluster() {
		
		return null;
	}
	
	public boolean scaleCluster(CloudParamDto.ScaleArg arg) {
		
		return false;
	}
	
	public boolean modifyCluster(CloudParamDto.ModifyArg arg) {
		
		return false;
	}
	
	public boolean deleteCluster(CloudParamDto.DeleteArg arg) {
	
		return false;
	}
	
	public boolean duplicateCheckCluster(CloudParamDto.DuplicateArg arg) {
		
		return false;
	}

}
