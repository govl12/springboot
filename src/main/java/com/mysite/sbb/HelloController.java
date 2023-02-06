package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller	//SpringBoot Framework에 HelloController라는 빈(Bean)을 등록하게 됨(빈 이름 : helloController)
public class HelloController { //객체생성=> 메소드 호출 가능 

	@GetMapping("/hello") //메소드 위에 붙임. 
	@ResponseBody //메소드 위에 붙임
	public String hello() { //String : 리턴값이 String(문자열)
		
		return "Helloworld - My Web site";
		
	}

	
	
}
