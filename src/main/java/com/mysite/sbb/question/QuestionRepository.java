package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


//JpaRepository를 사용하기 위해서 만든 Interface
				// 테이블이름Repository 					
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	//JPA에서 Question테이블을 Select, Insert, update, delete 
	// JPA의 CRUD메소드 :
		//save () : Insert, Update
		//delete() : delete
		//findAll()
		//findById() : 사용자 정의 필요
		//정의해서 사용 : findBySubject()...
	
	
	//Question 테이블을 SQL쿼리를 사용하지 않고 JPA메소드를 사용해서 CRUD 하는 Repository
		//JpaRepository<Question, Integer>
				//Question : JPA 메소드에서 쿼리할 엔티티 클래스의 이름.
				//Integer : 엔티티 클래스의 PK키가 들어있는 컬럼의 데이터 타입.
	
	// 하나의 컬럼을 조건으로 처리된 값 가져오기 
	//findById()  : Question 테이블의 primary Key 컬럼이므로 기본 제공
	// 나머지는 다 선언하고 사용해야함..
	//findBy = SQL 쿼리에서 where 과 같은 의미 
	//Question findBy~ : 검색 결과가 1개일때 처리 
	//select * from question where subject = ? 
	Question findBySubject(String subject);
	//select * from question where content =? 
	Question findByContent(String content);
		
	
	//select * from question where subject like '%sbb%'

	List<Question> findBySubjectLike(String subject);
	
	//content 컬럼을 조건으로 검색
	//select * from question where content like '%내용%'
	List<Question> findByContentLike(String content);
	
	//select * from question where subject like '%sbb%' and content like '%내용%'
	List<Question> findBySubjectLikeOrContentLike(String subject, String content);
	//필드 이름 = 소문자
	
	//모든 레코드를 정렬해서 출력(조건없이)
	//select * from question order by createDate asc
	List<Question> findAllByOrderByCreateDateAsc();
	//select * from question order by createDate desc
	List<Question> findAllByOrderByCreateDateDesc();
	
	//조건을 사용해서 검색 후 정렬해서 출력 
	//select * from question where subject =? order by createDate asc(오름차순 정렬)	
	List<Question> findBySubjectLikeOrderByCreateDateAsc(String subject);
	//select * from question where subject =? order by createDate desc(내림차순 정렬)
	List<Question> findBySubjectLikeOrderByCreateDateDesc(String subject);
	
	
	//insert, update, delete 는 정의(선언) 해 줄 필요가 없음. 
	//Update, insert : save()
	
	//delete : delete()
	
	//페이징을 처리하기 위한 메소드 생성
	//select * from question 
	//pageable 변수에 page, 레코드 수를 넣어주면 자동으로 처리 됨~( 보통 10개)
	//JPA에서 페이지 번호는 0번부터 시작
	//검색하여 출력할 레코드 수를 JPA에 알려주면 JPA가 전체 레코드(1000)/ 설정한 레코드수(10) = 100페이지 나옴.
	Page<Question> findAll(Pageable pageable);
	
	
	//0217 검색 쿼리 추가 
	
	Question findBySubjectAndContent(String subject, String content);
	
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	//쿼리 작성 시에는 테이블 기준이 아닌 엔티티 기준으로 작성해야함. Site_user : 테이블명명.SiteUser :엔티티명
//	q.author_id = u1.id 컬럼명, q.author = u1 : 엔티티명
	@Query("select "
			 + "distinct q "
			 + "from Question q "
			 + "left outer join SiteUser u1 on q.author=u1 "
			 + "left outer join Answer a on a.question=q "
			 + "left outer join SiteUser u2 on a.author=u2 "
			 + "where "
			 + " q.subject like %:kw% "
			 + " or q.content like %:kw% "
			 + " or u1.username like %:kw% "
			 + " or a.content like %:kw% "
			 + " or u2.username like %:kw% ")
			 Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
			}
		
