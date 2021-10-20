package com.ss.utopia.api.service;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.apache.logging.log4j.message.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	public User save(User user){

		//prevent unintentional update on existing user with save
		user.setId(null);
		
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

		return user_repository.save(user);

	}

	public UserRole save(UserRole user_role) throws SQLException {
		
		if(user_role.getUsers() != null) {
			user_role.getUsers().clear();		
		}

		if(user_role_repository.existsById(user_role.getId())) {
			throw new SQLException("Entity with Id already exists");
		}
		
		return user_role_repository.save(user_role);
	}

	public UserRole update(UserRole user_role) {

		if(user_role.getUsers() != null) {
			user_role.getUsers().clear();		
		}
		return user_role_repository.save(user_role);

	}

	public List<User> findAllUsers() {
		return user_repository.findAll();
	}

	public List<UserRole> findAllUserRoles() {
		return user_role_repository.findAll();
	}

	public User findByUsername(String username) {
		return user_repository.findByUsername(username).get();
	}

	public User findById(Integer user_id) {
		return user_repository.findById(user_id).get();
	}

	public UserRole findUserRoleById(Integer user_role_id) {
		return user_role_repository.findById(user_role_id).get();
	}

	public User update(User user) {

		User user_to_update = user_repository.findById(user.getId()).get();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		if (user.getUsername() != null) {
			user_to_update.setUsername(user.getUsername());
		}
		if (user.getGiven_name() != null) {
			user_to_update.setGiven_name(user.getGiven_name());
		}
		if (user.getFamily_name() != null) {
			user_to_update.setFamily_name(user.getFamily_name());
		}
		if (user.getPassword() != null) {
			user_to_update.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		}
		if (user.getPhone() != null) {
			user_to_update.setPhone(user.getPhone());
		}
		if (user.getEmail() != null) {
			user_to_update.setEmail(user.getEmail());
		}
		if (user.getUser_role() != null) {
			user_to_update.setUser_role(user.getUser_role());
		}

		user_repository.save(user_to_update);

		tx.commit();

		return user_to_update;

	}

	public void deleteByUsername(String username) {
		user_repository.deleteByUsername(username);

	}

	public void deleteById(Integer id) {

		user_repository.deleteById(id);

	}

	public void deleteUserRole(Integer user_role_id) {
		user_role_repository.deleteById(user_role_id);
	}

}
