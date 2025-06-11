package com.in28minutes.rest.webservices.restful_web_services.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	// 필터링방식도 구현될 예정
	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		
		somebean somebean = new somebean("v1","v2","v3");
		
		// 필터 설정목적으로 존재하는 객체인거같음, 데이터 뿐만하니라, 직렬화논리도 추가가능
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(somebean);
		// field1과 field3만 반환되게 하는 내용
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		//필터 프로바이더는, 여러 필터를 정의할수있게해줌
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
	
	// 리스트가 반환되더라도, somebean의 , field2가 @jsonignore으로
	// 표시되지 않기에, 역시, 리스트의 field2요소는 표시되지않음.. @jsonignore
	// field2, field3만 반환되게 하고싶다면?
	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		List<somebean> list = Arrays.asList(new somebean("v1","v2","v3"),
				new somebean("v4","v5","v6"));
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		//필터 프로바이더는, 여러 필터를 정의할수있게해줌
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
}
