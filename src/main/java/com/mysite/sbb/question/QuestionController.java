package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor //final 필드의 생성자를 자동으로 만들어서 생성자를 통해서 의존성 주입.
@Controller
public class QuestionController {
	
/* 클래스를 객체로 생성해주는 어노테이션 
 * @Component : 일반적인 클래스를 객체화
 * @Controller : 클라이언트 요청을 받아서 처리, Controller
 * 	1. 클라이언트 요청을 받는다. 
 * 		@GetMapping, @PostMapping
 * 	2. 비즈니스 로직 처리, Service의 메소드 호출
 * 	3. View 페이지로 응당
 * @Service : DAO의 메소드를 인터페이스로 생성 후 인터페이스의 메소드를 구현한 클래스
 * 	- 유지보수를 쉽게하기 위해서 ( 약결합) 
 * @Repository : DAO 클래스를 BEAN 등록 해줌.
 * */
	
/* DI(의존성 주입) : 클라이언트의 생성에 대한 의존성을 클라이언트의 행위로부터 분리하는 것
 * 	1. @Autowired : Spring Framework에 생성된 빈을 주입
 * 		같은 타입의 객체가 존재할 경우 문제가 발생할 수 있다.
 * 	2. 생성자를 통한 의존성 주입 ( 권장 방식)
 * 	3. Setter를 통한 의존성 주입 
 * 
 * 
 * */
	
	
	//생성자를 통한 의존성 주입 <== 권장하는 방식
	private final QuestionRepository questionRepository ;
	
	
	@GetMapping("/question/list") //http://localhost:9292/question/list
	@PostMapping("/question/list")	//Form태그의 method=post action= "/question/list
//	@ResponseBody	//요청(return)을 브라우저에 출력하도록 해줌. 
	public String list(Model model) {
		//1. 클라이언트 요청 정보 : http://localhost:9696/question/list
		
		//2. 비즈니스 로직 처리 
		List<Question> questionList = 
				this.questionRepository.findAll();
		//3. 뷰(view) 페이지로 전환
			//Model : 뷰페이지로 서버의 데이터를 담아서 전송하는 객체(과거 - Session, Application)
		model.addAttribute("questionList", questionList);
		

		return "question_list";		//ResponseBody가 없을 경우, return 에 적힌 이름의 html(뷰페이지)가 출력됨. 
		
	}

	

}
