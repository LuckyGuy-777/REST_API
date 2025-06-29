package com.in28minutes.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity(name = "user_details")
public class User {
	
	protected User() {
		
	}
	
	@Id
	@GeneratedValue
	private Integer id;
	
	// 생일은 항상 과거날짜로. @Past 
	@Past(message = "생일 날짜는, 반드시 과거의 날짜이어야만 합니다.")
	//@JsonProperty("birth_date")
	private LocalDate birthdate;
	
	//이름은 최소 2글자. @Size
	@Size(min=2, message = "이름은 적어도 2글자 이어야만 합니다.")
	//@JsonProperty("user_name") // 필드명 커스터마이징
	private String name;
	
	//유저에게 게시물을 매핑하는 기능. 유저와 게시물은 1대다 관계
	//이 필드는 Post의, user와 관계를 가짐 mappedBy
	// user 빈에 대해, 게시물을, JSON 응답에 포함시키려는게 아님. @JsonIgnore
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Post> posts;

	
	public User(Integer id, String name, LocalDate birthdate) {
		super();
		this.id = id;
		this.birthdate = birthdate;
		this.name = name;
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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthdate=" + birthdate + "]";
	}
	
	

}
