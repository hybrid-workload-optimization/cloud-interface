package kr.co.strato.cloud.aks.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/** 오류 코드 */
	private ErrorCode code;
	
	public ErrorCode getCode() {
		return code;
	}
	public void setCode(ErrorCode code) {
		this.code = code;
	}
	
	/**
	 * 생성자
	 * @param code 오류코드 
	 * @param message  오류메시지 
	 */
	public BusinessException(ErrorCode code) {
		this.code = code;
	}
	
	/**
	 * 생성자
	 * @param code 오류코드 
	 * @param message  오류메시지 
	 */
	public BusinessException(ErrorCode code, String message) {
		super(message);
		this.code = code;
	}
	
}
