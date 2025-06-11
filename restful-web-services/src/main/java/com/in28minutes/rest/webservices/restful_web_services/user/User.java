package com.in28minutes.rest.webservices.restful_web_services.user;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class User {
	private Integer id;
	
	//이름은 최소 2글자. @Size
	@Size(min=2, message = "이름은 적어도 2글자 이어야만 합니다.")
	@JsonProperty("user_name") // 필드명 커스터마이징
	private String name;
	
	// 생일은 항상 과거날짜로. @Past 
	@Past(message = "생일 날짜는, 반드시 과거의 날짜이어야만 합니다.")
	@JsonProperty("birth_date")
	private LocalDate birthdate;
	
	public User(Integer id, String name, LocalDate birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthdate=" + birthdate + "]";
	}
	
	

}
