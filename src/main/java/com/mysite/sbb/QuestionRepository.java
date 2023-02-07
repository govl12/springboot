package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;


//JpaRepository를 사용하기 위해서 만든 Interface
				// 테이블이름Repository 					
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	//JPA에서 Question테이블을 Select, Insert, update, delete 
	//Question 테이블을 SQL쿼리를 사용하지 않고 JPA메소드를 사용해서 CRUD 하는 Repository
		//JpaRepository<Question, Integer>
				//Question : JPA 메소드에서 쿼리할 엔티티 클래스의 이름.
				//Integer : 엔티티 클래스의 PK키가 들어있는 컬럼의 데이터 타입.
	
	
	
	
}
