package kr.co.strato.cloud.aks.model;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.Data;


@Data
public class CloudParamDto {

	@Data
	public static class ScaleArg {
		
		//클러스터 이름
		@NotEmpty(message = "클러스터 이름은 null 또는 공백을 허용하지 않습니다.")
		private String clusterName;
		
		//Node pool 이름(Optinal)
		private String nodePoolName;
				 
		//NodePool Size
		@Positive(message = "노드 수는 null 또는 0을 허용하지 않으며, 양수만 허용합니다.")
		private Integer nodeCount;

	}
	
	@Data
	public static class ModifyArg {
		
		//클러스터 이름
		@NotEmpty(message = "클러스터 이름은 null 또는 공백을 허용하지 않습니다.")
		private String clusterName;
		
		//vm 상품 타입
		@NotEmpty(message = "VM 상품유형은 null 또는 공백을 허용하지 않습니다.")
		private String vmType;
		
		//Node pool 이름(Optinal)
		private String nodePoolName;
				
		//NodePool Size
		@Positive(message = "노드 수는 null 또는 0을 허용하지 않으며, 양수만 허용합니다.")
		private Integer nodeCount;
		
	}
	
	@Data
	public static class DeleteArg {
		
		//클러스터 이름
		@NotEmpty(message = "클러스터 이름은 null 또는 공백을 허용하지 않습니다.")
		private String clusterName;
		
	}
	
	@Data
	public static class DuplicateArg {
		
		//클러스터 이름
		@NotEmpty(message = "클러스터 이름은 null 또는 공백을 허용하지 않습니다.")
		private String clusterName;

	}
	
}
