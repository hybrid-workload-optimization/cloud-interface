package kr.co.strato.cloud.aks.service;

import java.util.List;

import com.azure.resourcemanager.containerservice.fluent.models.ManagedClusterInner;
import com.azure.resourcemanager.containerservice.models.ContainerServiceNetworkProfile;
import com.azure.resourcemanager.containerservice.models.ManagedClusterAgentPoolProfile;

public class ProfileSetting {

	public List<ManagedClusterAgentPoolProfile> agentPoolProfileSet() {
		
		return null;
		
	}
	
	public ContainerServiceNetworkProfile networkProfileSet() {
		
		return null;
	}
	
	public ManagedClusterInner clusterInnerSet() {
	
		return null;
	}
}
