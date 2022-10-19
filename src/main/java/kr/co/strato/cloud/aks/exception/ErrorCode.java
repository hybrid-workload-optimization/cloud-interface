package kr.co.strato.cloud.aks.exception;

import lombok.Getter;

// 에러코드 정리 enum 클래스
public enum ErrorCode {

     NOT_NULL(400, "ERROR_CODE_0001","필수값이 누락되었습니다")
     , NOT_EMPTY (400, "ERROR_CODE_0002","필수값이 누락되었습니다")
     , MIN_VALUE(400, "ERROR_CODE_0003", "최소값보다 커야 합니다.")

     , CLUSTER_NOTFOUND(400,"ERROR_CODE_0004","클러스터가 존재하지 않습니다.")
     , CLUSTER_DUPLICATED(400,"ERROR_CODE_0005", "동일한 클러스터가 이미 존재합니다.")
     , NODEPOOL_DUPLICATED(400,"ERROR_CODE_0006","동일한 노드풀이 이미 존재합니다.")
     
     , AZURE_UNAUTHORIZED(400,"ERROR_CODE_0010", "Azure 클라우드 인증에 실패하였습니다.")
     , AZURE_NOPERMISSION(400,"ERROR_CODE_0011", "Azure 클라우드 권한이 없습니다.")
     , AUTH_INFO(400,"ERROR_CODE_0012","인증정보 파일 생성에 실패하였습니다.")
     
     , CLUSTER_CREATE_FAILED(400,"ERROR_CODE_0030","클러스터 생성에 실패하였습니다.")
     , KUBE_CONFIG(400,"ERROR_CODE_0031","Kube_config 생성에 실패하였습니다.")
     , CLUSTER_DELETE_FAILED(400,"ERROR_CODE_0032","클러스터 삭제에 실패하였습니다.")
     , CLUSTER_LIST_GET_FAILED(400,"ERROR_CODE_0033","클러스터 리스트를 불러오는데 실패하였습니다.")
     , CLUSTER_SCALE_FAILED(400,"ERROR_CODE_0034","클러스터 노드풀 스케일 조정에 실패하였습니다.")
     , CLUSTER_MODIFY_DELETE_FAILED(400,"ERROR_CODE_0035","클러스터 기존 노드풀 삭제에 실패하였습니다.")
     , CLUSTER_MODIFY_CREATE_FAILED(400,"ERROR_CODE_0036","클러스터 노드풀 수정에 실패하였습니다.")
     
     , NODEPOOL_FIND_FAILED(400,"ERROR_CODE_0037","클러스터의 노드풀이 존재하지 않거나 여러개이므로 설정 대상 노드풀 입력이 필요합니다.")
     , NODEPOOL_NOTFOUND(400,"ERROR_CODE_0038","클러스터의 설정대상 노드풀을 찾을 수 없습니다.")
     
     , INTERNAL_SERVER_ERROR(500, "ERROR_CODE_0100","Internal Server Error")
     ;

	@Getter
	private int status;
    @Getter
    private String errorCode;
    @Getter
    private String message;

    ErrorCode(int status, String errorCode, String message) {
    	this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
    
}