package com.in28minutes.rest.webservices.restful_web_services.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// 클래스에 지정하는 어노테이션.
// 프로퍼티명을 주어서, 보이지 않게할 필드 지정
// 여러개 추가 가능
//@JsonIgnoreProperties({"field1","field2"})

//필터컨트롤러의, 심플빈프로퍼티필터 정의후, JsonFilter를 정의해야함. 
// 필터프로바이더의, 첫번째 매개변수 문자열에 들어갈것임.
// 그래야 필터적용이 됨.
@JsonFilter("SomeBeanFilter")
public class somebean {
	private String field1;
	
	// 어떤 필드를 표시하고싶지 않을떄 ex: 비밀번호 필드.. 등 -> @JsonIgnore
	//@JsonIgnore
	private String field2;
	private String field3;
	public somebean(String field1, String field2, String field3) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}
	public String getField1() {
		return field1;
	}
	public String getField2() {
		return field2;
	}
	public String getField3() {
		return field3;
	}
	@Override
	public String toString() {
		return "somebean [field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + "]";
	}
	
}
