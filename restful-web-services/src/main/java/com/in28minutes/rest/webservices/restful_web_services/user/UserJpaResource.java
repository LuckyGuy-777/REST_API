package com.in28minutes.rest.webservices.restful_web_services.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restful_web_services.jpa.PostRepository;
import com.in28minutes.rest.webservices.restful_web_services.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	
	private UserRepository userRepository;
	
	private PostRepository postRepository;
	
	public UserJpaResource(UserRepository userRepository,PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	//GEt / 모든유저정보 찾기
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	// http://localhost:8080/users
	// HATEOAS 의 개념.
	// EntityModel 도메인 객체를 래핑, 링크를 추가하는역할
	// WebMvcLinkBuilder
	
	
	//User 클래스를 래핑, EntityModel을 생성
	// 엔티티 모델 추가한다음, 링크를 추가해야함(링크를 반환하려면. WebMvcLinkBuilder)
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		//Optional에서 User 객체를 가져오려면, 해당객체.get
		EntityModel<User> entityModel = EntityModel.of(user.get());
		
		//WebMvcLinkBuilder.linkTo 메소드를 사용해, 컨트롤러 메소드를 가르키는 링크를 생성하고있음
		// 가르키고 있는 컨트롤러 메소드는, retrieveAllUsers 이고, 링크가 주어지면, entityModel에
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		// 추가할수있음. 리소스와 링크사이에, 링크를 지정해야함
		// 특정 리소스에 따라 원하는만큼 많은 링크를 추가가능함
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	// 아이디값으로, 유저 삭제
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRepository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForAUser(@PathVariable int id){
		// user가 비어있으면 사용자 찾을수없음 예외 반환
		
		// ID로 찾기를 이용해서, 사용자 세부정보를 가져오고,
		Optional<User> user = userRepository.findById(id);
				 
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		// 해당 사용자에 대한 게시물을 가져옴
		return user.get().getPosts();
	}

	// Create user
	// 201 응답, 생성된 리소스 location값을 응답값 헤더에 넣어 반환
	// @Valid , 검증(프로퍼티,메소드,반환타입의 유효성 확인). 바인딩이 수행될떄,
	// 객체에 정의된 유효성검증이 자동으로 수행된다
	@PostMapping("/jpa/users") // 정보를 보내려면, 리퀘스트 바디
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		//  /users/4 -> /users/{id}, user.getID
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}") //요청에 id를 추가하고,
				.buildAndExpand(savedUser.getId())// 생성된 사용자의 아이디로 바꾸려함
				.toUri(); // 그걸 url로 변환하고, 반환
		
		
		return ResponseEntity.created(location).build(); // 201, location을 인자로
		
	}
	
	// 사용자에 대한 게시물을 생성할수있는 REST API를 생성함
	// 특정 사용자 ID에 대해 게시물 생성
	// 게시물에대한 유효성 검증도 (Post 클래스의, @min, @max, @ManytoOne...등이 대상) @Valid 
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostsForAUser(@PathVariable int id, @Valid @RequestBody Post post){
		// user가 비어있으면 사용자 찾을수없음 예외 반환
		// 사용자를 찾는 코드
		Optional<User> user = userRepository.findById(id);
				 
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		// 게시물에 사용자를 설정함
		post.setUser(user.get());
		
		// 사용자가 존재하면, 게시물을 해당사용자에 매핑해서 저장
		// 게시물 저장소에 저장
		Post savePost = postRepository.save(post);
		
		// 생성된 게시물의 ID값을, {id} 에 매핑 시키는 코드
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}") //요청에 id를 추가하고,
				.buildAndExpand(savePost.getId())// 생성된 사용자의 아이디로 바꾸려함
				.toUri(); // 그걸 url로 변환하고, 반환
		
		
		return ResponseEntity.created(location).build(); // 201, location을 인자로
		
	}
	
	// 특정 게시물의 세부정보를 얻는 url을 만들어 보도록하자
	
}
