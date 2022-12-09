package kr.co.strato.cloud.aks.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.core.exception.AzureException;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.Context;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.containerservice.fluent.models.AgentPoolInner;
import com.azure.resourcemanager.containerservice.fluent.models.ManagedClusterInner;
import com.azure.resourcemanager.containerservice.models.AgentPoolMode;
import com.azure.resourcemanager.containerservice.models.CredentialResult;
import com.azure.resourcemanager.containerservice.models.ManagedClusterAgentPoolProfile;
import com.google.gson.Gson;

import kr.co.strato.cloud.aks.exception.BusinessException;
import kr.co.strato.cloud.aks.exception.ErrorCode;
import kr.co.strato.cloud.aks.model.CloudParamDto;
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
		String version = arg.getKubernetesVersion(); 
		String region = arg.getRegion();
		String nodePoolName = arg.getNodePoolName();
		String vmType = arg.getVmType();
		Integer nodeCount = arg.getNodeCount();
		
		ManagedClusterInner clusterInner = new ManagedClusterInner();
		ManagedClusterAgentPoolProfile agentPoolProfile = new ManagedClusterAgentPoolProfile();
		clusterInner.withKubernetesVersion(version);
		agentPoolProfile.withCount(nodeCount);
		agentPoolProfile.withName(nodePoolName);
		agentPoolProfile.withVmSize(vmType);
		List<ManagedClusterAgentPoolProfile> agentPoolProfiles = new ArrayList<>();
		agentPoolProfiles.add(agentPoolProfile);
		clusterInner.withAgentPoolProfiles(agentPoolProfiles);
		
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
		String nodePoolName = arg.getNodePoolName();
		Integer nodeCount = arg.getNodeCount();
		
		try {
			azureResourceManager
						.kubernetesClusters()
				        .manager()
				        .serviceClient()
				        .getAgentPools()
				        .createOrUpdate(
				            rgName
				            , clusterName
				            , nodePoolName
				            , new AgentPoolInner()
				                .withCount(nodeCount)
				                .withMode(AgentPoolMode.SYSTEM)
				            , Context.NONE);
			
			return true;
		} catch(Exception e) {
			log.error("[scaleCluster] >>> Faild cluster scale", e);
			throw new BusinessException(ErrorCode.CLUSTER_SCALE_FAILED);
		}
		
	}
	
	public boolean modifyCluster(CloudParamDto.ModifyArg arg) {
		
		log.debug("[modifyCluster] start");
		
		AzureResourceManager azureResourceManager = azureCredential.getAzureAuth(clientId, clientSecret, tenantId, subscriptionId);
		
		log.debug("[modifyCluster] >>> request cluster modify");


		String clusterName = arg.getClusterName();
		String nodePoolName = arg.getNodePoolName();
		Integer nodeCount = arg.getNodeCount();
		
//		try {
//			azureResourceManager
//						.kubernetesClusters()
//				        .manager()
//				        .serviceClient()
//				        .getAgentPools()
//				        .createOrUpdate(
//				            rgName
//				            , clusterName
//				            , nodePoolName
//				            , new AgentPoolInner()
//				                .withCount(nodeCount)
//				                .withMode(AgentPoolMode.SYSTEM)
//				            , Context.NONE);
//			
//			return true;
//		} catch(Exception e) {
//			log.error("[modifyCluster] >>> Faild cluster modify", e);
//			throw new BusinessException(ErrorCode.CLUSTER_SCALE_FAILED);
//		}
		
	}
	
	
	public boolean deleteCluster(CloudParamDto.DeleteArg arg) {
	
		return false;
	}
	
	public boolean duplicateCheckCluster(CloudParamDto.DuplicateArg arg) {
		
		return false;
	}

}
