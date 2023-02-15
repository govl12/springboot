package com.mysite.sbb.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovy.lang.Binding;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	//user/signup이 get으로 요청되면-> signup 템플릿이 열리고, 
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		
		return "signup_form";
	}
	
	//post로 signup이 요청되면 회원가입이 진행됨. 
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, 
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}
		
		//비밀번호1과 비밀번호2가 동일한지 검증하는 로직. 
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			//rejectValue(필드명, 오류코드, 에러메시지) = 오류 발생하도록 만듬.
			bindingResult.rejectValue("password2", "passwordIncorrect", 
					"2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}
		
		
		try {
			//각각 하려면 어떻게 해야되는걸ㄲ ㅏ?
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(),
				
					userCreateForm.getPassword1());
		
		}catch(DataIntegrityViolationException e) {
			// 사용자가 동일하거나, 이메일이 동일하여 예외가 발생할 경우 오류메세지 화면 출력. 
			e.printStackTrace();
			
			//bindingResult.reejct("오류코드", "오류메세지) : 일반적인 오류 표시 
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			
			return "signup_form";
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
			bindingResult.reject("signupFailed", e.getMessage());
			
			return "signup_form";
			
		}
		
	//	userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(),
		//		userCreateForm.getPassword1());
		
		return "redirect:/";
		
	}

}
