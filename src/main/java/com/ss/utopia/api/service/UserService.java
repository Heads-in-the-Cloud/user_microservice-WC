package com.ss.utopia.api.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.utopia.api.dao.UserRepository;
import com.ss.utopia.api.dao.UserRoleRepository;

import com.ss.utopia.api.pojo.User;
import com.ss.utopia.api.pojo.UserRole;

@Service
public class UserService {

	
	@Autowired
	UserRepository user_repository;
	
	@Autowired
	UserRoleRepository user_role_repository;
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	public User findByUsername(String username) {
		return user_repository.findByUsername(username).get();
	}
	
	public User findById(Integer user_id) {
		return user_repository.findById(user_id).get();
	}
	
	public UserRole findUserRoleById(Integer user_role_id) {
		return user_role_repository.findById(user_role_id).get();
	}
	
	public List<User> findAllUsers(){
		return user_repository.findAll();
	}
	public List<UserRole> findAllUserRoles(){
		return user_role_repository.findAll();
	}
	
	
	public Optional<User> save(User user) {
		
		if(user_repository.findByUsername(user.getUsername()).isPresent()) {
			return Optional.empty();
		}
		if(user_repository.findByEmail(user.getEmail()).isPresent()) {
			return Optional.empty();

		}
		if(user_repository.findByPhone(user.getPhone()).isPresent()) {
			return Optional.empty();

		}
		
		

		return Optional.of(user_repository.save(user));
		
		
	}
	public UserRole save(UserRole user_role) {
		return user_role_repository.save(user_role);
	}
	
	public Optional<UserRole> update(UserRole user_role){
		if(user_role_repository.existsById(user_role.getId())) return Optional.empty();
		return Optional.of(user_role_repository.save(user_role));
		
	}
	

	public Optional<User> update(User user){
		if(!user_repository.existsById(user.getId())) return Optional.empty();
		
		User user_to_update = user_repository.findByUsername(user.getUsername()).get();

		try {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		if(user.getUsername() != null) {
			user_to_update.setUsername(user.getUsername());
		}
		if(user.getGiven_name() != null) {
			user_to_update.setGiven_name(user.getGiven_name());
		}
		if(user.getFamily_name() != null) {
			user_to_update.setFamily_name(user.getFamily_name());
		}
		if(user.getPassword() != null) {
			user_to_update.setPassword(user.getPassword());
		}
		if(user.getPhone() != null) {
			user_to_update.setPhone(user.getPhone());
		}
		if(user.getEmail() != null) {
			user_to_update.setEmail(user.getEmail());
		}
		if(user.getUser_role() != null) {
			user_to_update.setUser_role(user.getUser_role());
		}
		
		user_repository.save(user_to_update);
		tx.commit();
		}catch(Exception e) {
		
			return Optional.empty();
		}finally {
			//sessionFactory.close();
		}
		
		
		return Optional.of(user_to_update);
		
	}
	
	
	public void deleteUser(Integer user_id) {
		user_repository.deleteById(user_id);
	}
	public void deleteUserRole(Integer user_role_id) {
		user_role_repository.deleteById(user_role_id);
	}

	

	

	
	
	

	
		
	
	
	
	
}
