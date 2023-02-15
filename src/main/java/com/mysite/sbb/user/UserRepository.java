package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long>{

	//JPA는 SQL 쿼리를 사용하지 않고 메소드를 사용하여 SQL쿼리로 변환해서 처리. 
	//기본적으로 사용할 수 있는 메소드(JpaRepository에 있음)
	// findAll(), findById(), save() : Insert, Update, delete()
}
