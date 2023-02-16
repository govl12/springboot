package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity(prePostEnabled = true)//2월 16일 수정
	// Question컨트롤러, answer 컨트롤러에서 로그인 여부를 판별하기 위해 사용한
	//@PreAuthorize("isAuthenticated()") 사용하기 위해 반드시 필요
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests().requestMatchers(
				
				new AntPathRequestMatcher("/**")).permitAll()
		
		//h2 database는 http로 통신하는 database이므로 csrf 적용되지 않도록 설정
		.and().
			
			csrf().ignoringRequestMatchers(
				//csrf키가 없더라도 접근이 가능하도록 설정.
					new AntPathRequestMatcher("/h2-console/**"))
			.and()
				
				.headers()
				
				.addHeaderWriter(new XFrameOptionsHeaderWriter(
								
						XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
				
				//Spring Security 로그인 처리 부분
				.and()
				
					.formLogin()
					
					//로그인 페이지 url : /user/login
					.loginPage("/user/login")
					
					//로그인 성공시 세션(서버메모리)에 로그인 정보를 담고 루트페이지로 이동 
					.defaultSuccessUrl("/")
					
					.and()
					
						//로그아웃 설정
						.logout()
						
						.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
						
						.logoutSuccessUrl("/")
						
						//로그아웃 시 모든 로그인 정보 삭제 
						.invalidateHttpSession(true)
					
					
			;
		
		return http.build();
	
	}
	
		//Userservice 에서 사용하기 위해 bean 등록 - bcrypt ~encoder 객체를 직접 생성보다는, bean으로 등록해서 사용하는 것이 수정면에서 좋다.
		@Bean 
		PasswordEncoder passwordEncoder() {
			
			return new BCryptPasswordEncoder();
		
		}
		
		//스프링 시큐리티 설정 - 로그인 인증 빈 생성, 
		//빈 생성시 스프링에있는 내부 동작으로 인해 usersecurityservice와 passwordencoder 실행됨. 
		@Bean
		AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
				throws Exception{
			
			return authenticationConfiguration.getAuthenticationManager();
			
		}
}
