package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
	/*0217 GET List수정 
	public Page<Question> getList(int page, String kw){
		
		//sort를 사용해서 최신글을 먼저 출력하도록 설정
		List<Sort.Order> sorts = new ArrayList();
		sorts.add(Sort.Order.desc("createDate"));
		
		
		//pageable 객체에 2개의 값을 담아서 매개 변수로 던짐. 10: 출력할 레코드 수 
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		
		Specification<Question> spec = search(kw);
		
		return this.questionRepository.findAll(spec, pageable);
	}*/
	
	/*
	public Page<Question> getList(int page){
		
		//sort를 사용해서 최신글을 먼저 출력하도록 설정
		List<Sort.Order> sorts = new ArrayList();
		sorts.add(Sort.Order.desc("createDate"));
		
		
		//pageable 객체에 2개의 값을 담아서 매개 변수로 던짐. 10: 출력할 레코드 수 
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		
		return this.questionRepository.findAll(pageable);
	}
	*/
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
	
	//추천인 저장 
	
	public void vote(Question question, SiteUser siteUser) {
		
		question.getVoter().add(siteUser);
		
		this.questionRepository.save(question);
	
	}
	
	//검색기능 (0217) - JPA에서 제공하는 Specification
	
	private Specification<Question> search(String kw){
		
		return new Specification<>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query,
					//q : 기준을 의미하는 Question 엔티티의 객체(질문의 제목과 내용을 검색) 
					CriteriaBuilder cb) {
				
				query.distinct(true);//중복을 제거
				
				Join<Question,SiteUser> u1 = q.join("author", JoinType.LEFT);
				//Question엔티티와 SiteUser 엔티티를 아우터 조인(JoinType.LEFT)하여 만든 SiteUser 엔티티 객체.
				//Question 엔티티-SiteUser 은 author 속성으로 연결되어 있음. - 질문작성자 검색 하기 위해 필요.
				
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				//Question 엔티티와 Answer 엔티티를 아우터 조인하여 만든 Answer 엔티티 객체. 
				//AnswerList속성으로 연결되어 있음 - 답변 내ㅑ용 검색을 위해 필요.
				
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				// a객체와 SiteUser 엔티티와 아우터 조인하여 만든 SiteUser 엔티티의 객체 - 답변 작성자 검색 위해 필요
				
				return cb.or(cb.like(q.get("subject"), "%"+kw+"%"),	// 제목
						
						cb.like(q.get("content"), "%" + kw + "%"),	//내용
						
						cb.like(u1.get("username"),  "%" + kw + "%"), 	//작성자
						
						cb.like(a.get("content"),  "%" + kw + "%"), // 답변내용"
							
						cb.like(u2.get("username"), "%" + kw + "%")); //답변 작성자 
			
				// search() : 
				// 검색어 kw 를 입력받아 쿼리의 조인문과 where문을 생성하여 리턴. 
			}
		};
	}
	// 02017 겟리스트 수정 
	public Page<Question> getList(int page, String kw) {
		
		List<Sort.Order> sorts = new ArrayList<>();
		
		sorts.add(Sort.Order.desc("createDate"));
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		
		return this.questionRepository.findAllByKeyword(kw, pageable);
	}
	
}
