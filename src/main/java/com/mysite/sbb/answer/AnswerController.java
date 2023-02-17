package com.mysite.sbb.answer;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

	//private final AnswerRepository answerRepository;
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;//0216추ㅡ가..
	
	//http://localhost:9696/answer/create/1 요청에 대한 답변글 등록 처리
	
	
	
	/* 2월 16일 수정 - 답변 작성자 저장하는걸로 변경 
	@PostMapping("/create/{id}")
	public String createAnswer(

			//<Validation 전 구성>
			//Model model, @PathVariable("id") Integer id,@RequestParam String content
			
			//Answer-content의 유효성 검사
			Model model, @PathVariable("id")Integer id,
			@Valid AnswerForm answerForm, BindingResult bindingResult
			) {
			
		
		Question question = this.questionService.getQuestion(id);
		//답변 내용을 저장하는 메소드 호출(Service에서 호출)
		
		//Content의 값이 비어 있을 때
		if(bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		}

		this.answerService.create(question, answerForm.getContent());
		
		return String.format("redirect:/question/detail/%s", id);
	}*/
	
	
	
//답변에 작성자 저장하기
	//securityConfig.java에서 @EnableMethodSecurity(prePostEnabled =true )클래스 위에 부여되어 있을때 작동 됨. 
	@PreAuthorize("isAuthenticated()")//로그인 상태에서만 접근 가능.
	@PostMapping("/create/{id}")
	//principal 객체 : 스프링 시큐리티가 제공. 로그인 사용자에 대한 정보 확인 가능 .
	public String createAnswer(Model model, @PathVariable("id") Integer id,
			@Valid AnswerForm answerForm, BindingResult bindingResult, 
			Principal principal) {
		
		Question question = this.questionService.getQuestion(id);
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		if(bindingResult.hasErrors()){
			
			model.addAttribute("question", question);
			
			return "question_detail";
		}

			Answer answer = 
			
			this.answerService.create(question, answerForm.getContent(), siteUser);
			
			return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
	}
	//답변 글 수정 
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
		
		Answer answer = this.answerService.getAnswer(id);
		
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		
		}

		answerForm.setContent(answer.getContent());
		
		return"answer_form";
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
			
			@PathVariable("id") Integer id, Principal principal) {
		
			if(bindingResult.hasErrors()) {
				
				return "answer_form";
			}
			
			Answer answer = this.answerService.getAnswer(id);
			
			if(!answer.getAuthor().getUsername().equals(principal.getName())) {
				
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");

			}
			
			this.answerService.modify(answer, answerForm.getContent());
		
			return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(),answer.getId());
	}
	
	//답변 글 삭제 
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
		
		Answer answer = this.answerService.getAnswer(id);
		
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
			
		}
		

		
		this.answerService.delete(answer);

		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}
	
	//답변 글 추천
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Integer id) {
		
		Answer answer = this.answerService.getAnswer(id);
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		
		
		this.answerService.vote(answer, siteUser);
		
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
		
	}
	
}
