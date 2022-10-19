package kr.co.strato.cloud.aks.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import kr.co.strato.cloud.aks.model.CloudParamDto;
import kr.co.strato.cloud.aks.model.CloudResponseDto.GetList;
import kr.co.strato.cloud.aks.model.CreateArg;
import kr.co.strato.cloud.aks.service.AKSInterfaceService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AKSInterfaceController {
	
	@Autowired
	private AKSInterfaceService interfaceService;
	
	/**
	 * 클러스터 프로비저닝
	 * @param arg
	 * 		: 클러스터 생성 정보 
	 * @return
	 * 		: Admin용 KubeConfig
	 */
	@PostMapping("/api/v1/cloud/aks/provisioning")
	public String provisioningCluster(@RequestBody @Valid CreateArg arg) {
		
		log.debug("[provisioningCluster] param = {}", arg.toString());
		
		String kubeConfig = interfaceService.provisioningCluster(arg);
	
		log.debug("[provisioningCluster] return = {}", kubeConfig);
		
		return kubeConfig;
	}
	
	@GetMapping("/api/v1/cloud/aks/list")
	public List<GetList> getListCluster() {
	
		log.debug("[getListCluster] param is null");
		
		List<GetList> result = interfaceService.getListCluster();
	
		log.debug("[getListCluster] return = {}", result.toString());
		
		return result;
		
	}
	
	/**
	 * 클러스터 삭제.
	 * @param clusterName
	 * 		: 삭제하려는 클러스터 이름
	 * @return
	 * 		: 삭제 성공 여부.
	 */
	@DeleteMapping("/api/v1/cloud/aks/delete")
	public boolean deleteCluster(@RequestBody @Valid CloudParamDto.DeleteArg arg) {
		
		log.debug("[deleteCluster] param = {}", arg.toString());
		
		boolean result = interfaceService.deleteCluster(arg);

		log.debug("[deleteCluster] return = {}", result);
		
		return result;
	}
	
	
	/**
	 * 노드 스케일 조정
	 * @param arg
	 * @return
	 * 		: 스케일 조정 성공 여부
	 */
	@PostMapping("/api/v1/cloud/aks/scale")
	public boolean scaleCluster(@RequestHeader HttpHeaders headers, @RequestBody @Valid CloudParamDto.ScaleArg arg) {
		
		log.debug("[scaleCluster] param = {}", arg.toString());
		
		boolean result = interfaceService.scaleCluster(arg);
		
		log.debug("[scaleCluster] return = {}", result);
		
		return result;
	}
	
	/**
	 * 노드 풀 수정(VM 상품 교체 및 노드 수 조정)
	 * @param arg
	 * @return
	 * 		: 수정 성공 여부.
	 */
	@PostMapping("/api/v1/cloud/aks/modify")
	public boolean modifyCluster(@RequestBody CloudParamDto.ModifyArg arg) {
		
		log.debug("[modifyCluster] param = {}", arg.toString());
		
		boolean result = interfaceService.modifyCluster(arg);
		
		log.debug("[modifyCluster] return = {}", result);
		
		return result;
	}
	
	
	
	/**
	 * 클러스터 중복 체크
	 * @param clusterName
	 * 		: 중복체크하려는 클러스터 이름
	 * @return
	 * 		: 중복 여부.
	 */
	@PostMapping("/api/v1/cloud/aks/duplicateCheck")
	public boolean duplicateCheckCluster(@RequestHeader HttpHeaders headers, @RequestBody CloudParamDto.DuplicateArg arg) {
		
		log.debug("[duplicateCheckCluster] param = {}", arg.toString());
		
		boolean result = interfaceService.duplicateCheckCluster(arg);
		
		log.debug("[duplicateCheckCluster] return = {}", result);
		
		return result;
	}
	

	
	
}
