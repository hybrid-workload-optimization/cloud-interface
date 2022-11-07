package kr.co.strato.cloud.aks.service;

import java.util.ArrayList;
import java.util.List;

import com.azure.resourcemanager.containerservice.fluent.models.ManagedClusterInner;
import com.azure.resourcemanager.containerservice.models.ContainerServiceNetworkProfile;
import com.azure.resourcemanager.containerservice.models.ManagedClusterAgentPoolProfile;

import kr.co.strato.cloud.aks.model.CreateArg;
import kr.co.strato.cloud.aks.model.CreateArg.NodePools;

public class ProfileSetting {

	public List<ManagedClusterAgentPoolProfile> agentPoolProfileSet(CreateArg arg) {
		
		List<ManagedClusterAgentPoolProfile> agentPools = new ArrayList<>();
		
		
		return agentPools;
		
	}
	
	public ContainerServiceNetworkProfile networkProfileSet() {
		
		String dnsPrefix = "testDns";
		
		return null;
	}
	
	public ManagedClusterInner clusterInnerSet(CreateArg arg) {
	
		String clusterName = arg.getClusterName();
		String kubernetesVersion = arg.getKubernetesVersion();
		String region = arg.getRegion();
		List<NodePools> nodePools = arg.getNodePools();
		
		List<ManagedClusterAgentPoolProfile> agentPools = agentPoolProfileSet(arg);
		ContainerServiceNetworkProfile network = networkProfileSet();
		
		return null;
	}
}
