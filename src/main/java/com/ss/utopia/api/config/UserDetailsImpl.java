package com.ss.utopia.api.config;

import java.util.Collection;

import java.util.List;
import java.util.Arrays;

import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ss.utopia.api.pojo.User;




public class UserDetailsImpl implements UserDetails{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String userName;
		private String password;
		private boolean active;
		private List<GrantedAuthority> authorities;

		
		public UserDetailsImpl(User user) {
			this.userName=user.getUsername();
			this.password=user.getPassword();
			this.active=true;
			this.authorities= Arrays.asList(new SimpleGrantedAuthority(user.getUser_role().getName()));
		}
		public UserDetailsImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			return authorities;
		}

		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return password;
		}

		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return userName;
		}

		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return active;
		}

	}

