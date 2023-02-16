package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;



//사용자 정보를 폼에서 넘겨받아서 인증을 처리함.

@RequiredArgsConstructor
@Service
//스프링 시큐리티 설정에 등록할 서비스 
//UserDetailService : 스프링시큐리티에서 제공하는 인터페이스, 
//loadByUsername()를 구현하도록 강제하는 인터페이스.
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);
		
		if(_siteUser.isEmpty()) {
			
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		
		} 
		
		//폼에서 넘어오는 username을 DB에서 쿼리해서  siteUser객체에 담은 Optiona에서 뽑아옴
		//siteUser = DB에서 Select 한 레코드 
		SiteUser siteUser =  _siteUser.get();
		
		//Authentication : 인증 : Identity(ID) + PassWord를 확인하는 것
		//Authorization :  허가 : 인증된 사용자에게 사이트의 서비스를 쓸 수 있도록 권한을 부여하는 것.
			//계정이 admin이라면 ADMIN ROLE 을 적용
			// 계정이 admin이 아니라면 User Role을 적용 
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		//사용자 명이 admin인 경우, admin권한 부여 
		if ("admin".equals(username)) {
			
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
			
		}else {
			
			//그 외의 경우 
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
			
		}
		
		// 사용자명, 비밀번호, 권한을 USER객체로 생성하여 리턴. 
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
		
		
	}

}
