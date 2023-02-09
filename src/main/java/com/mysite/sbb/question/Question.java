package com.mysite.sbb.question;
	
import java.time.LocalDateTime;	//자신의 시스템이 위치한 장소(국가)의 시간 설정 
import java.util.List;

import com.mysite.sbb.answer.Answer;

//persistence : JPA에 사용된 어노테이션 
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;	//JPA에서 적용된 어노테이션
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity	/* 자바 클래스를 DataBase에 테이블과 매핑된 클래스 . 
		테이블명 : question. 컬럼명 id, subject, content, createDate */
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		//시퀀스 할당(초기값, 증가값)
	private Integer id;	// Primary Key, 시퀀스 (1,1) 
	
	@Column(length = 200) // 글자수 200자까지
	private String subject;
	
	@Column(columnDefinition ="TEXT")
	private String content;
	
	private LocalDateTime createDate;	//create_date : 
	// 변수이름 작성 : 첫자 소문자 + 다음단어 대문자 (camelCase) => DB 컬럼명 : 소문자단어_대문자단어
	
	/*
	@Column(length = 300)
	private String addr; */
	
	// Question 테이블에서 Answer 테이블을 참조하는 컬럼을 생성 @OnetoMany
	@OneToMany (mappedBy = "question", cascade = CascadeType.REMOVE)// cascadeType.Remove : 부모 컬럼 삭제 시, 참조컬럼도 다 삭제 
	private List<Answer> answerList;
	
		//question.getAnswerList();
	
	
	
	
	

}
