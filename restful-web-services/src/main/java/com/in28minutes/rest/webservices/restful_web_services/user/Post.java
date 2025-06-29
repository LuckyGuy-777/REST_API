package com.in28minutes.rest.webservices.restful_web_services.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Post {
	@Id
	@GeneratedValue
	private Integer id;
	
//	글자는 최소 10글자
	@Size(min = 10)
	private String description;
	
	// 일대 다 관계를 설정할때 @ManyToOne. fetch= 관계가 지연로딩되는지 즉시로딩되는지 결정
	// 유효성 검증에 사용되는 어노테이션들임 (@Valid)
	@ManyToOne( fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;
	

	public Integer getId() {
		return id;
	} 

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + "]";
	}
	
}
