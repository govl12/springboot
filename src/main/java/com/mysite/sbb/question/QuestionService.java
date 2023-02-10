package com.mysite.sbb.question;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;



import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor	// DI(생성자에 객체 주입)
@Service
public class QuestionService {
	//JPA 메소드를 사용하기 위해(생성자를 이용한 객체 자동 주입) 
	private final QuestionRepository questionRepository;
	
	//메소드 : Question 테이블의 list 정보를 가지고 오는 메소드 
	public List<Question> getList(){
		return this.questionRepository.findAll();
	}
	
	//상세페이지를 처리하는 메소드 : id를 받아서 Question 테이블을 select(findById(1))
		//select한 레코드를 Question 객체에 담아서 객체 자체를 return해줌.
	
	
	public Question getQuestion(Integer id) {
		
		//select * from question where id =?
		Optional<Question> op = this.questionRepository.findById(id);
		
		if(op.isPresent()) {
			
			return op.get();
			
		} else {
			//사용자 정의 예외
				//throw : 예외 강제로 생성.
				//throws : 예외를 요청한 곳에서 처리하도록 미루는 것 
			throw new DataNotFoundException("요청한 레코드를 찾지 못했습니다.");
			
		}


	}
	
	
}
