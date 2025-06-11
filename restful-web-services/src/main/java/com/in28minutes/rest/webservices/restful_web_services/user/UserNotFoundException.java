package com.in28minutes.rest.webservices.restful_web_services.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 찾지 못했으니까, NOT_FOUND 응답 반환.(404)
@ResponseStatus(code = HttpStatus.NOT_FOUND) // 반환하고싶은 응답상태를 파라미터로 기입
public class UserNotFoundException extends RuntimeException {

	// 메세지를 받아서, 부모클래스(런타임엑셉션)에 전달
	public UserNotFoundException(String message) {
		super(message);
	}
	
}
