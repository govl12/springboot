package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class TestController {


	@GetMapping("/testweb")
	@ResponseBody
	public String test() {

		return "Test코드 블락 입니다. - MyWebSite - 추가된 내용입니다 - 추가 항목입니다.";
		
	}
	
	
}
