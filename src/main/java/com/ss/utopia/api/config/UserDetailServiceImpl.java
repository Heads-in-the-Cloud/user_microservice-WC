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
//import org.springframework.security.core.userdetails.User;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("load user by name");

		// TODO Auto-generated method stub
//		return new User("foo", "$2a$10$1GvDNviK2wYho51TrXXrQe4AwVaRtlDxunXCE7xfem6gNRsfFv9HS",
//                new ArrayList<>());
//		
//		
//		System.out.println("inside UserDetailService");
		System.out.println("username" + username);
		System.out.println(username);
		Optional<User> user= userRepository.findByUsername(username);
		System.out.println(user);
		user.orElseThrow(() -> new UsernameNotFoundException("not found" + username));
		return new UserDetailsImpl(user.get());
	}

}
