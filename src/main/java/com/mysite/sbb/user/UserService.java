package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	//user 레포지터리를 사용함.
	private final UserRepository userRepository;
	
	//Bean으로 bcrypt 해싱함수 사용
	private final PasswordEncoder passwordEncoder;
	
	//SiteUser에 데이터를 저장하는 create 메소드 
	public SiteUser create(String username, String email, String password) {
		
		SiteUser user = new SiteUser();
		
		user.setUsername(username);
		
		user.setEmail(email);
	
		//비밀번호 암호화 저장 - BCrypt 함수 사용(객체 직접 생성)
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
		//객체 직접 생성하지 않고 빈으로 등록한 passwordEncoder 객체 주입받아 사용 
		user.setPassword(passwordEncoder.encode(password));
		
		this.userRepository.save(user);
		
		return user;
	}
	
	//siteUser를 조회할 수 있는 getUser() 추가. 
	public SiteUser getUser(String username) {
		
		Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
		
		if(siteUser.isPresent()) {
				
			return siteUser.get();
			
		} else {
			
			throw new DataNotFoundException("siteuser is not found");
		}
	}
}
