package com.mysite.sbb;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class SbbApplicationTests {
	
	@Autowired	//객체 자동 주입. JPA의 CRUD 할 수 있는 메소드가 적용되어 있음. QuestionRepository(인터페이스)타입. 
	private QuestionRepository questionRepository;
	//인터페이스는 객체 안만들어져서 원래는 Autowired가 안되지만 Spring 내부적으로 걍 처리됨..
	
	@Autowired	//객체 자동 주입(DI), JPA의 메소드를 사용 , findAll(), findById(), save(), delete()
	private  AnswerRepository answerRepository; 
	
	/*하나의 질문에 여러개의 답변 찾기 */
	
	@Transactional // 아래의 메소드가 하나의 트랜젝션으로 작동되도록 설정(Test에서만 사용)
	@Test
	public void testjpa8() {
		//1. Question 테이블에서 질문의 레코드를 얻어온다(끄집어 낸다)
			Optional<Question> op =
			this.questionRepository.findById(1);
			
			Question q = null;
			if (op.isPresent()) {
				q = op.get();
				
			}
			
			System.out.println(q.getId());
			System.out.println(q.getSubject());
			
		//2. 끄집어낸 객체의 q.getAnswerList(); <== 끄집어낸 객체의 답변글을 얻어온다.
		//Question 객체의 answerList컬럼은 List<answer>를 저장 하고 있음.
			List<Answer> all = q.getAnswerList();	
		//3. 출력 구문에서 출력한다.
			
			for (int i = 0; i < all.size(); i++) {
				Answer a = all.get(3);
				System.out.println(a.getId());
				System.out.println(a.getContent());
				System.out.println(a.getCreateDate());
				System.out.println("================");
			}
		
		
	}
	
	
	
	
	
	/* Answer 테이블 중 레코드 하나 가져오기
@Test
public void testjpa7() {
	Optional<Answer> op =
	this.answerRepository.findById(2);
	
	if(op.isPresent()) {	// isPresent() : null(false), null이 아닐 때 (true)
		Answer a = op.get();
		System.out.println(a.getId());
		System.out.println(a.getContent());
		System.out.println(a.getCreateDate());
	//	System.out.println(a.getQuestion());
		
		 
	}
	
}*/
	
	
	
	
	
	/*Answer 테이블에 값 insert 처리 
	@Test
	public void testAnswerjpa() {
		// 1. Question(부모)테이블의 답변을 처리할 레코드를 먼저 select 한다 => findById(1)
		Optional<Question> op = 
		this.questionRepository.findById(3); // 부모테이블에서 나온 1번 레코드..
		Question q = op.get();
		
		// 2. Answer 엔티티 테이블에 객체 생성을 하고, setter를 사용해서 값을 넣는다.
			// setQuestion(q) => q : 부모테이블에서 가져온 객체 
		Answer a = new Answer(); 
		a.setQuestion(q);
		a.setContent("3 - sbb답변 3입니다.");
		a.setCreateDate(LocalDateTime.now());
		
		// 3. save()를 통해서 값을 저장.
		this.answerRepository.save(a);
		
	}*/
	
	
	
	/* 데이터 삭제 : delete() 
	@Test
	public void testjpa6() {
		//1. 테이블에서 삭제할 레코드를 가지고 옴
		Optional<Question> op = 	//Optional : jpa검색해서 1개를 가지고 오는것? 
				this.questionRepository.findById(2); //2번 레코드를 검색저장.
		//2. Optional 에 저장된 객체를 끄집어 낸다.
			Question q = op.get();
		//3. delete(q)
		this.questionRepository.delete(q);

	}*/
	
	/* 데이터 수정 : save() 
	@Test
	public void testjpa5() {
		// 1. 수정할 객체를 findById(= select) 메소드를 사용해서 가지고 온다.
		Optional<Question> op =  //ID 1번을 저장.
		this.questionRepository.findById(1);
		
		// 2. 가지고 온 객체를 끄집어내서 setter를 사용해서 수정
		Question q = op.get();
		q.setSubject("수정된 제목");
		q.setContent("수정된 내용");
		
		// 3. 수정된 객체를 save(q)
		this.questionRepository.save(q);	
		
	}*/
	
	/* 테이블의 모든 레코드 정렬 : desc, asc
	@Test
	public void testjpa4() {
		List<Question> all =
				this.questionRepository.findAllByOrderByCreateDateAsc();
		List<Question> all2 =
				this.questionRepository.findAllByOrderByCreateDateDesc();
		
		System.out.println("총 리스트에 들어간 객체 수 : " + all.size());
		System.out.println("=========ASC 정렬 후 출력==============");
	
		for(int i = 0; i < all.size() ; i++) {
			Question q = all.get(i);
			
			System.out.println(q.getId());
			System.out.println(q.getSubject());
			System.out.println(q.getContent());
			System.out.println(q.getCreateDate());
			System.out.println("====================");	
		}
		
		System.out.println("========DESC 정렬 후 출력 ===============");
		for(int i = 0; i < all2.size() ; i++) {
			Question q = all2.get(i);
			
			System.out.println(q.getId());
			System.out.println(q.getSubject());
			System.out.println(q.getContent());
			System.out.println(q.getCreateDate());
			System.out.println("====================");	
		}
	
	} */
	
	
	/*특정 조건을 사용해서 검색 후 정렬하여 출력 
	@Test
	public void testjpa3() {
		List<Question> or = 
		this.questionRepository.findBySubjectLikeOrderByCreateDateAsc("%sbb%");
	
		List<Question> or2 = 
				this.questionRepository.findBySubjectLikeOrderByCreateDateDesc("%sbb%");

		
		
		System.out.println("검색된 레코드 수 : " + or.size());
		
		System.out.println("===== ASC : 오름차순 정렬 ======");
		for (int i = 0; i< or.size(); i++) { //i = 지역변수. 메소드 끝나면 사라짐.
		Question q = or.get(i);
		
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getCreateDate());
		System.out.println("==========================");
		
		}
		
		System.out.println("======DESC : 내림차순 정렬========");
		
		for (int i = 0; i< or2.size(); i++) {
			Question q = or2.get(i);
			
			System.out.println(q.getId());
			System.out.println(q.getSubject());
			System.out.println(q.getCreateDate());
			System.out.println("==========================");
			
			}
		/*
		for (Question q : or) {
			System.out.println(q);
		}
	} */
	
	
	/* 두 컬럼을 or 연산으로 검색 : subject, content  
	
	@Test
	public void testjpa2() {
		List<Question> sq = 
		this.questionRepository.findBySubjectLikeOrContentLike("%sbb%", "%생성%");
		
		Question q4 = sq.get(0);
		System.out.println("총 검색된 갯수 : " + sq.size());
		System.out.println(q4.getId());
		System.out.println(q4.getSubject());
		System.out.println(q4.getContent());
	} */
	
	/* 사용자 정의 Select 문 - Like 사용 - 
	 * subject 컬럼, content 컬럼 검색 : SubjectLike, ContentLike 
	@Test
	public void testjpa() {
		
		List<Question> all = this.questionRepository.findBySubjectLike("%sbb%");
		
		Question q = all.get(0);
		System.out.println("리스트에 검색된 레코드 수 : " + all.size());
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getContent());
	
		System.out.println("=====================");
		
		List<Question> all2 = this.questionRepository.findByContentLike("%생성%");
		
		Question q3 = all2.get(0);
		
		System.out.println("리스트에 검색된 레코드 수 : " + all2.size());
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getContent());
	
	} */
	
	
	
	
	
	/* 조건에 맞는 레코드(1개)만 가져오기 : PK컬럼은 findById(1) 
	 * Question 테이블의 Primary Key 컬럼은 기본적으로 제공됨 : findById()
	 *
	@Test
	public void jpaTestget() {
		Optional<Question> oq = this.questionRepository.findById(2);
							//ID컬럼의 값이 1인 것만 가져오기 
		if(oq.isPresent()) {
			Question q = oq.get();
			System.out.println(q.getId());
			System.out.println(q.getSubject());
			System.out.println(q.getContent());
			System.out.println(q.getCreateDate());
			
		}
		
		
	} */
	
	
	
	/* Select List JUnit Test  - 전체레코드의 값을 가져오는 findAll()  
	@Test
	public void jpaTest() { 
		List<Question> all = this.questionRepository.findAll();
		//findAll() : questionRepository에 저장되어 있는 모든 레코드를 가지고 옴. 
		
		//assertEquals(4, all.size());		// assertEquals(기대값,실제값) .
		//기대값 = 실제값 (두 값이 일치) : JUnit 성공
		
		Question q = all.get(0); 
		// all = list. List all이라는 변수. list의 0번방의 객체(첫번째 레코드)를 끄집어 낸다.
		//assertEquals("sbb가 무엇인가요?",q.getSubject()); // 성공
		
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getContent());
		
	}  */
	
	/* Insert JUnit Test, JPA 인터페이스에 정의된 - DB에 값을 저장하는 save()
	@Test
	void contextLoads() {
		Question q1 = new Question();
		
		//객체.set컬럼 : setter로 컬럼에 값 주입.
		q1.setSubject("sbb가 무엇인가요?"); 
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());	//현재 시간을 setter에 저장
		this.questionRepository.save(q1);	//setter로 객체 주입한 q1..
		
		
		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다");
		q2.setContent("id는 자동으로 생성되나요");
		q2.setCreateDate(LocalDateTime.now());	//현재 시간을 setter에 저장
		this.questionRepository.save(q2); //두번째 질문 저장

	} */
}

