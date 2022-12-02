package kr.co.strato.cloud.aks.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class CreateArg {
	
	//클러스터 이름
	@NotEmpty(message = "클러스터 이름은 null 또는 공백을 허용하지 않습니다.")
	private String clusterName;
	
	//Kubernetes 버전
	@NotEmpty(message = "쿠버네티스 버전은 null 또는 공백을 허용하지 않습니다.")
	private String kubernetesVersion;
	
	//클러스터 생성 지역
	@NotEmpty(message = "지역은 null 또는 공백을 허용하지 않습니다.")
	private String region;
	
	//DNS 접두사 이름
	private String dnsPrefix;
	
	private List<NodePools> nodePools;
	
	@Data
	public static class NodePools {
		
		private String nodePoolName;
		
		//vm 상품 타입
		@NotEmpty(message = "VM 상품유형은 null 또는 공백을 허용하지 않습니다.")
		private String vmType;

		//NodePool Size
		@Positive(message = "노드 수는 null 또는 0을 허용하지 않으며, 양수만 허용합니다.")
		private Integer nodeCount;
		
	}
	
}
