package com.mysite.sbb.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AnswerForm {
	//content(내용)의 값 유효성 검사. @setter로 값 주입. -> 비어 있을 때 message 출력 

	@NotEmpty(message="답변을 입력해주세요.")
		private String content;
	
	}
	
	
	
