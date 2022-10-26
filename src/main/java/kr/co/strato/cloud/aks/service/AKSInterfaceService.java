package kr.co.strato.cloud.aks.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.strato.cloud.aks.model.CloudParamDto;
import kr.co.strato.cloud.aks.model.CloudResponseDto.GetList;
import kr.co.strato.cloud.aks.model.CreateArg;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AKSInterfaceService {

	public String provisioningCluster(CreateArg arg) {
		
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
