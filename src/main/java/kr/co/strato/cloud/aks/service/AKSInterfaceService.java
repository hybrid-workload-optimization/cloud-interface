package kr.co.strato.cloud.aks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.core.exception.AzureException;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.Context;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.containerservice.fluent.models.ManagedClusterInner;
import com.azure.resourcemanager.containerservice.models.CredentialResult;
import com.google.gson.Gson;

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
	
	@Value("${azure.auth.subscriptionId}")
	private String subscriptionId;
	
	@Value("${azure.auth.clientId}")
	private String clientId;
	
	@Value("${azure.auth.clientSecret}")
	private String clientSecret;
	
	@Value("${azure.auth.tenantId}")
	private String tenantId;
	
	@Value("${azure.auth.rgName}")
	private String rgName;
	
	public String provisioningCluster(CreateArg arg) {
		
		log.debug("[provisioningCluster] start");
		
		AzureResourceManager azureResourceManager = azureCredential.getAzureAuth(clientId, clientSecret, tenantId, subscriptionId);
		
		log.debug("[provisioningCluster] >>> request cluster create");

		String clusterName = arg.getClusterName();
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
		
		List<CredentialResult> listKubeConfig = azureResourceManager
														.kubernetesClusters()
														.listAdminKubeConfigContent(rgName, clusterName);

		String kubeconfig = listKubeConfig.get(0).toString();
		
		return kubeconfig;
	}
	
	public String getListCluster() {
		
		log.debug("[getListCluster] start");
		
		AzureResourceManager azureResourceManager = azureCredential.getAzureAuth(clientId, clientSecret, tenantId, subscriptionId);

		PagedIterable<ManagedClusterInner> getList = null;
		
		try {		
			getList = azureResourceManager
					        .kubernetesClusters()
					        .manager()
					        .serviceClient()
					        .getManagedClusters()
					        .listByResourceGroup(rgName, Context.NONE);
		} catch(Exception e) {
			log.error("[getListCluster] >>> Faild get cluster list", e);
			throw new BusinessException(ErrorCode.CLUSTER_LIST_GET_FAILED);
		}
		
		Gson gson = new Gson();
		
		String list = gson.toJson(getList);
		
		return list;
	}
	
	public boolean scaleCluster(CloudParamDto.ScaleArg arg) {
		
		log.debug("[scaleCluster] start");
		
		AzureResourceManager azureResourceManager = azureCredential.getAzureAuth(clientId, clientSecret, tenantId, subscriptionId);
		
		
		log.debug("[scaleCluster] >>> request cluster create");


		String clusterName = arg.getClusterName();
		ManagedClusterInner clusterInner = new ManagedClusterInner();
		
		try {
			azureResourceManager
						.kubernetesClusters()
						.manager()
						.serviceClient()
						.getManagedClusters()
						.update(rgName, clusterName, clusterInner, Context.NONE);
			
		} catch (AzureException e) {
			log.error("[scaleCluster] >>> Faild cluster create", e);
			throw new BusinessException(ErrorCode.CLUSTER_CREATE_FAILED);
		}
		
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
