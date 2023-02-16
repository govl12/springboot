package com.mysite.sbb.user;

import lombok.Getter;

//enum : 열거 자료형 
@Getter
//상수 자료형은 setter을 안쓴다는게 무슨말인쥐..
public enum UserRole {

	ADMIN("ROLE_ADMIN"),
	
	USER("ROLE_USER");
	
	UserRole(String value) {
		
		this.value = value;
	}
	
	private String value;
	
}
