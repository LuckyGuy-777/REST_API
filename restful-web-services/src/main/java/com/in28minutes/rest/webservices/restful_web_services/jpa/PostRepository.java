package com.in28minutes.rest.webservices.restful_web_services.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservices.restful_web_services.user.Post;

// Post 클래스를 관리하는 레포지터리
// 게시물 저장소
public interface PostRepository extends JpaRepository<Post, Integer>{

}
