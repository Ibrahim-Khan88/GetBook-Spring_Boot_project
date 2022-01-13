package com.security.userdetailservice;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.entity.UserInfo;
import com.security.repository.UserRepositoty;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserRepositoty userRepositoty;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserInfo userInfo = userRepositoty.findByGmail(username);
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		User user = new User(userInfo.getUsername(), userInfo.getPassword(), Arrays.asList(authority));

		// System.out.println(user.getUsername() + " " + user.getAuthorities());
		UserDetails userdetail = user;

		return userdetail;
	}

}
