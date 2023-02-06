package com.mysite.sbb;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class HelloLombok2 {


	private int seq;
	private String title;
	private String writer;
	private String content;
	private Date regdate;
	private int cnt;
	
	
	
	
	
	public static void main(String[] args) {
		//객체 생성 후 테스트 
	
		HelloLombok2 lombok = new HelloLombok2();
		
		//setter 메소드가 자동 생성되었는지 확인
		lombok.setCnt(2);
		lombok.setContent("내용입니다.");
//		lombok.setRegdate("23/02/06");
		lombok.setTitle("제목입니다.");
		lombok.setWriter("글쓴이입니다.");
		lombok.setSeq(8);
		
		
		//getter를 사용해서 lombok2객체에 저장된 메모리 필드의 값을 출력
		
		System.out.println(lombok.getCnt());
		System.out.println(lombok.getContent());
		System.out.println(lombok.getSeq());
		System.out.println(lombok.getTitle());
		System.out.println(lombok.getWriter());
		System.out.println(lombok.getRegdate());
		
		//toString()메소드 호출 : 객체 자체를 print
		System.out.println(lombok);
	}

}
