 package com.in28minutes.rest.webservices.restful_web_services.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// REST API
@RestController
public class HelloWorldController {
	
	// 애플리케이션 국가별 응답. messages.properties
	private MessageSource messageSource;
	
	// 생성자 주입방식
	// 메세지소스에 프로퍼티설정하면, 아래의 방식으로 가져올수 있음.
	private HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(path= "/hello-world")
	public String helloWorld() {
		return "hello world";
	}
	
	@GetMapping(path= "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("hello world");
	}
	
	// path parameters
	// /users/{id}/todos/{id} => /users/1/todos/101 . id값들을, 패스매개변수라함
	
	@GetMapping(path= "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(
				String.format("Hello World, %s", name));
	} 
	
	// 국제화된 메서드
	@GetMapping(path= "/hello-world-internationalized")
	public String helloWorldInternationalized() {
		
		// 현재스레드와 연관된 로케일 반환. 그렇지않으면 시스템 기본로케일.
		// 사용자의 해당 헤더에 관한 로케일을, 아래의 객체에 의해 반환함.
		Locale locale= LocaleContextHolder.getLocale();
		// 1. 코드, 2.메시지를 대신할 변수, 3. 디폴트메세지
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale); 
		
		 
		
		// 나라별, 응답메세지
		// 1 : good.morning.message
		
	}
	
}
