package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long>{

	//JPA는 SQL 쿼리를 사용하지 않고 메소드를 사용하여 SQL쿼리로 변환해서 처리. 
	//기본적으로 사용할 수 있는 메소드(JpaRepository에 있음)
	// findAll(), findById(), save() : Insert, Update, delete()
	
	
	
	//로그인 처리하기 위해서 사용자 정보를 입력 받아서 DB에서 select 해서 siteUser객체에 저장됨.
	
	//스프링시큐리티 로그인설정을 위한 DB에 저장된 사용자 아이디 조회 
	Optional<SiteUser> findByUsername(String username);
}
