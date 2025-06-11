package com.in28minutes.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	// 사용자 검색, 상세정보 저장, 모든사용자 검색
	// JPA/Hibernate 로 DB와 통신
	// UserDaoService > Static List
	
	private static List<User> users = new ArrayList<>();
	
	private static int usersCount = 0;
	
	static {
		users.add(new User(++usersCount, "Adam", LocalDate.now().minusYears(30)));
		users.add(new User(++usersCount, "DAVID", LocalDate.now().minusYears(25)));
		users.add(new User(++usersCount, "ORION", LocalDate.now().minusYears(20)));

	}
	
	// 유저 전체 찾기
	public List<User> findAll(){
		return users;
	}
	
	// 유저 한명 찾기. orElse는, 값이 존재하면 리턴, 그렇지않으면 선언한 다른값 리턴
	public User findOne(int id) {
		Predicate<? super User> pridicate = user -> user.getId().equals(id);
		return users.stream().filter(pridicate).findFirst().orElse(null);
	}
	
	// 지정 유저 삭제
	public void deleteById(int id) {
		Predicate<? super User> pridicate = user -> user.getId().equals(id); // 매개변수 id와, user의 id를 비교.
		users.removeIf(pridicate); // 함수형 프로그래밍에는, 조건에 맞는 객체를 삭제하는 객체가 있음.(removeIf)
	}
	
	
	// 유저 생성
	public User save(User user) {
		user.setId(++usersCount);
		users.add(user);
		return user;
	}
	
	
}
