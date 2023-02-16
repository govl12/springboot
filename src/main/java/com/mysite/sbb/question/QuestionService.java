package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

//로직을 서비스에서 다 적고, 컨트롤러에서는 서비스에서 작성한 메소드 호출.
@RequiredArgsConstructor	// DI(생성자에 객체 주입)
@Service
public class QuestionService {
	//JPA 메소드를 사용하기 위해(생성자를 이용한 객체 자동 주입) 
	private final QuestionRepository questionRepository;
	
	//메소드 : Question 테이블의 list 정보를 가지고 오는 메소드 (2월 14일 수정 - 페이징 처리.)
//	public List<Question> getList(){
//		return this.questionRepository.findAll();
//	}
	
	//controller 에서 getList메소드로 호출 시 출력할 page번호를 매개변수로 받음 : 0,1,2,3
	public Page<Question> getList(int page){
		
		//sort를 사용해서 최신글을 먼저 출력하도록 설정
		List<Sort.Order> sorts = new ArrayList();
		sorts.add(Sort.Order.desc("createDate"));
		
		
		//pageable 객체에 2개의 값을 담아서 매개 변수로 던짐. 10: 출력할 레코드 수 
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		
		return this.questionRepository.findAll(pageable);
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
	public void create(String subject, String content, SiteUser user) {
		//Question 객체를 생성 후 setter주입
		Question q = new Question();
				q.setSubject(subject);
				q.setContent(content);
				q.setCreateDate(LocalDateTime.now());
				q.setAuthor(user);// 0216 질문 작성자 저장 
				//Repository save ()ㅁ소드에 Question 객체 저장
				this.questionRepository.save(q);	//DB에 INSERT 더
				
	}
	
	//질문 수정 서비스
	public void modify(Question question, String subject, String content) {
		
		question.setSubject(subject);
		
		question.setContent(content);
		
		question.setModifyDate(LocalDateTime.now());
		
		this.questionRepository.save(question);
		
	}
	
	//질문 삭제 서비스
	
	public void delete(Question question) {
		
		this.questionRepository.delete(question);
		
	}
}
