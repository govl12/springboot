package com.mysite.sbb;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {
	
	@Autowired	//객체 자동 주입. JPA의 CRUD 할 수 있는 메소드가 적용되어 있음. QuestionRepository 타입. 
	private QuestionRepository questionRepository;

	/* 조건에 맞는 레코드(1개)만 가져오기* - findById() */
	
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
		
		
	}
	
	
	
	/* Select List JUnit Test  - 전체레코드의 값을 가져오는 findAll()
	@Test
	public void jpaTest() { 
		List<Question> all = this.questionRepository.findAll();
		//findAll() : questionRepository에 저장되어 있는 모든 레코드를 가지고 옴. 
		
		//assertEquals(4, all.size());		// assertEquals(기대값,실제값) .
		//기대값 = 실제값 (두 값이 일치) : JUnit 성공
		
		Question q = all.get(3); 
		// all = list. List all이라는 변수. list의 0번방의 객체(첫번째 레코드)를 끄집어 낸다.
		//assertEquals("sbb가 무엇인가요?",q.getSubject()); // 성공
		
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getContent());
		
	} */
	
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

	}*/
}
