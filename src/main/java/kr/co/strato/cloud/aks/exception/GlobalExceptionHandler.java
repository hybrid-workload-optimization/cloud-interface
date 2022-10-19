package kr.co.strato.cloud.aks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

// exception 발생시 전역으로 처리할 exception handler 작성
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 로직에 대한 예외 처리
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        final ErrorCode errorCode = e.getCode();
        final ErrorResponse response = new ErrorResponse(errorCode);
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }
	
    /**
     * 그 밖에 발생하는 모든 예외 처리
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleEntityNotFoundException", e);
        ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
        
    private ErrorResponse makeErrorResponse(BindingResult bindingResult){
        String code = "";
        String message = "";
        String detail = "";

        //에러가 있다면
        if(bindingResult.hasErrors()){
            //DTO에 설정한 meaasge값을 가져온다
            detail = bindingResult.getFieldError().getDefaultMessage();

            //DTO에 유효성체크를 걸어놓은 어노테이션명을 가져온다.
            String bindResultCode = bindingResult.getFieldError().getCode();

            switch (bindResultCode){
                case "NotNull":
                    code = ErrorCode.NOT_NULL.getErrorCode();
                    message = ErrorCode.NOT_NULL.getMessage();
                    break;
                case "NotEmpty":
                    code = ErrorCode.NOT_EMPTY.getErrorCode();
                    message = ErrorCode.NOT_EMPTY.getMessage();
                    break;
                case "Min":
                    code = ErrorCode.MIN_VALUE.getErrorCode();
                    message = ErrorCode.MIN_VALUE.getMessage();
                    break;
            }
        }

        return new ErrorResponse(code, message, detail);
    }
    
}