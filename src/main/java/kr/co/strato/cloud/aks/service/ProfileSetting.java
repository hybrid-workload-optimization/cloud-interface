package kr.co.strato.cloud.aks.service;

import java.util.ArrayList;
import java.util.List;

import com.azure.resourcemanager.containerservice.fluent.models.ManagedClusterInner;
import com.azure.resourcemanager.containerservice.models.ContainerServiceNetworkProfile;
import com.azure.resourcemanager.containerservice.models.ManagedClusterAgentPoolProfile;

import kr.co.strato.cloud.aks.model.CloudResponseDto.GetList.NodePools;
import kr.co.strato.cloud.aks.model.CreateArg;

public class ProfileSetting {

	public List<ManagedClusterAgentPoolProfile> agentPoolProfileSet(CreateArg arg) {
		
		List<ManagedClusterAgentPoolProfile> agentPools = new ArrayList<>();
		
		
		return agentPools;
		
	}
	
	public ContainerServiceNetworkProfile networkProfileSet() {
		
		String dnsPrefix = "testDns";
		
		ContainerServiceNetworkProfile networkProfile = new ContainerServiceNetworkProfile();
		
		return networkProfile;
	}
	
}
