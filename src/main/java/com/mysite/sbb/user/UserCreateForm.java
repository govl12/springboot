package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

	@Size(min=3, max=25, message="ID는 3자에서 25자 사이로 입력해주세요.")
	@NotEmpty(message="사용자 ID는 필수항목입니다.")
	private String username;
	
	@NotEmpty(message="비밀번호는 필수항목입니다.")
	private String password1;
	
	@NotEmpty(message="비밀번호 확인은  필수항목입니다.")
	private String password2;
	
	@Email
	@NotEmpty(message="이메일은 필수항목입니다.")
	private String email;
	
	
	
	
}
