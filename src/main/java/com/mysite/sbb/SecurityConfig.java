package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//접근을 허용하는 ...
@Configuration	//Security 의 설정을 적용하는 어노테이션 
@EnableWebSecurity	//http의 url 접근을 제어하는 어노테이션
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests().requestMatchers(
				
				new AntPathRequestMatcher("/**")).permitAll()
		
		.and().
			
			csrf().ignoringRequestMatchers(
				//csrf키가 없더라도 접근이 가능하도록 설정.
					new AntPathRequestMatcher("/h2-console/**"))
			.and()
				
				.headers()
				
				.addHeaderWriter(new XFrameOptionsHeaderWriter(
								
						XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
			;
		
		return http.build();
	
	}
	
		//Userservice 에서 사용하기 위해 bean 등록 - bcrypt ~encoder 객체를 직접 생성보다는, bean으로 등록해서 사용하는 것이 수정면에서 좋다.
		@Bean 
		PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		
	}
}
