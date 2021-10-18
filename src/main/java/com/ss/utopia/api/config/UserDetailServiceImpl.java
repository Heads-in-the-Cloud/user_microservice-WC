package com.ss.utopia.api.config;

import java.util.ArrayList;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ss.utopia.api.dao.UserRepository;
import com.ss.utopia.api.pojo.User;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("load user by name");
		
		System.out.println("inside UserDetailService");
		Optional<User> user= userRepository.findByUsername(username);
		System.out.println(user);
		user.orElseThrow(() -> new UsernameNotFoundException("not found" + username));
		return new UserDetailsImpl(user.get());
		
		
		
	}

}
