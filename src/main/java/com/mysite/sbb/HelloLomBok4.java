package com.mysite.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor // 생성자 생성 시 필드이름 앞에 final이 들어있느 필드만 아규먼트로 생성  

public class HelloLomBok4 {

	private final String hello;	//필드 : private
	private int lombok;		//필드
	
	
	/* @RequiredArgsConstructor : 필드 이름 앞에 FINAL 키가 할당된 필드만 아규먼트로 등록
	  	public HelloLomBok4(String hello){
	  	this.hello = hello;
	 */	
	
	
	public static void main(String[] args) {
		//requiredArgsConstrcuor 테스트
		HelloLomBok4 lombok4 = new HelloLomBok4("안녕");
				
		//toSTring()메소드 호출
		System.out.println(lombok4);

	}

}
