package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity	//JPA에서 자바객체를 DB의 테이블에 매핑 
public class Answer { //테이블이름 . DTO 
	
	@Id	// primary key 옵션. 중복X 
	@GeneratedValue(strategy=GenerationType.IDENTITY) // identity 옵션 = 자동증가 
	private Integer id;		// Primary Key, 자동증가( 초기값 1, 증가값 1)
	
	@Column(columnDefinition = "TEXT")
	private String content;
	

	private LocalDateTime createDate; //DB저장명 : create_date
	
	
	@ManyToOne // Foreign key 설정
	// Foreign key : 부모테이블의 PK, UK컬럼의 값을 참조해서 값을 할당
	// question = one, answer = many;
	private Question question; //Foreign Key, Question 테이블(부모테이블)의 id컬럼(Primary key)를 참조 
			//question_id
	
	//게시글의 작성자(질문글의 작성자)
	//Foreign Key, SiteUser의 값을 참조 .. 
	@ManyToOne //여러개의 질문을 한사람이 작성할 수 있으므로 
	private SiteUser author;
	
	//글 수정 일시 저장
	private LocalDateTime modifyDate;
	
	//추천인 
	
	@ManyToMany
	Set<SiteUser> voter;
	
}
