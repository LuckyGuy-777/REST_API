package com.in28minutes.rest.webservices.restful_web_services.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.in28minutes.rest.webservices.restful_web_services.user.UserNotFoundException;

// 오류 응답요소 커스터마이징 클래스.(모든 컨트롤러, 테스트 컨트롤러 대상.)
// ExceptionHandler을 선언한 클래스를 위한것
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	

	// 아래 어노테이션은, 어떤 예외를 여기서 처리할건지 정의함. (발생한 모든예외를 대상임)
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
				ex.getMessage(), request.getDescription(false));
	
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	// 찾는 유저가 없는경우에 에러반환. 500 -> 404 bad request로 응답변환하는 코드
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
				ex.getMessage(), request.getDescription(false));
	
		// NOT_FOUND로 바꿔야, 500 -> 404로 응답이 바뀜
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
		
	}
	
	// ResponseEntityExceptionHandler의, handleMethodArgumentNotValid을,
	// 오버라이드 해서 사용함.
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
				"Total Errors:" + ex.getErrorCount() +" First Error: "+ex.getFieldError().getDefaultMessage(), request.getDescription(false)); // 에러개수 반환 ex.getErrorCount(), 첫번쨰 에러만 반환됨. ex.getFieldError().getDefaultMessage()
		
		// 에러메세지 모두 띄우는 코드
		// ex.getFieldError() 메시지문자열에 메세지 추가.(반복문사용)
		
		// BAD_REQUEST, 400
		return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
