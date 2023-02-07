package com.mysite.sbb;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity		//JPA에서 자바객체를 DB의 테이블에 매핑 
public class Answer { //테이블이름 . DTO 
	
	@Id	// primary key 옵션. 중복X 
	@GeneratedValue(strategy=GenerationType.IDENTITY) // identity 옵션 = 자동증가 
	private Integer id;		// Primary Key, 자동증가( 초기값 1, 증가값 1)
	
	@Column(columnDefinition = "TEXT")
	private String content;
	

	private LocalDateTime createDate;
	
	
	@ManyToOne  // Foreign key : 부모테이블의 PK, UK컬럼의 값을 참조해서 값을 할당
	// question = one, answer = many;
	private Question question; //Foreign Key, Question 테이블(부모테이블)의 id컬럼(Primary key)를 참조 
	
	
	
}
