package kr.co.strato.cloud.aks.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
// exception 발생시 응답하는 에러 정보 클래스
public class ErrorResponse {

	private int status;
	
    private String code;

    private String message;

    private String detail;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }
    
    public ErrorResponse(ErrorCode errorCode) {
    	this.status = errorCode.getStatus();
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
    }
}