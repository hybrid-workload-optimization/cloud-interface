package kr.co.strato.cloud.aks.model;

import java.util.List;

import lombok.Data;

@Data
public class CloudResponseDto {
	
	@Data
	public static class GetList {
		
		//클러스터 이름
		private String clusterName;
		
		//Kubernetes 버전
		private String kubernetesVersion;
		
		//클러스터 생성 지역
		private String region;
		
		private List<NodePools> nopePools;
		
		@Data
		public static class NodePools {
			private String nodePoolName;
			private String vmType;
			private Integer nodeCount;
			
		}

	}
}
