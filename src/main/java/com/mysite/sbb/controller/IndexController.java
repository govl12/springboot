package com.mysite.sbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

	@GetMapping("/") 		//http://localgost:9696/ 
	public String index() {
		
		return "index";
		
	}
}
