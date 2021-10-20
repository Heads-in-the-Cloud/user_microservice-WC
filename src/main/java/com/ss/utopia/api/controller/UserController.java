package com.ss.utopia.api.controller;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;

import com.ss.utopia.api.pojo.User;
import com.ss.utopia.api.pojo.UserRole;
import com.ss.utopia.api.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	/* Login information: */
	/*
	 * username: admin1 password: pass
	 * 
	 * username: agent1 password: pass
	 * 
	 * username: user1 password: pass
	 *
	 * Comment out JWT filter in com.ss.utopia.api.config.SecurityConfiguration for
	 * form based login
	 */

	@Autowired
	UserService user_service;

	@GetMapping(path = "/admin/read")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok().body(user_service.findAllUsers());
	}

	@GetMapping(path = "/read/user={username}")
	public ResponseEntity<User> getUserByName(@PathVariable String username) {
		return ResponseEntity.ok().body(user_service.findByUsername(username));
	}

	@GetMapping(path = "/admin/read/id={user_id}")
	public ResponseEntity<User> getUserById(@PathVariable Integer user_id) {
		return ResponseEntity.ok().body(user_service.findById(user_id));
	}

	@GetMapping(path = "/admin/read/user_roles")
	public ResponseEntity<List<UserRole>> getAllUserRoles() {
		return ResponseEntity.ok().body(user_service.findAllUserRoles());
	}

	@GetMapping(path = "/admin/read/user_role/id={user_role_id}")
	public ResponseEntity<UserRole> getUserRoleById(@PathVariable Integer user_role_id) {
		
		
		return ResponseEntity.ok().body(user_service.findUserRoleById(user_role_id));
	}

	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User user){

		User new_user = user_service.save(user);
		URI uri = URI.create(
				ServletUriComponentsBuilder.fromCurrentContextPath().path("/read/id=" + user.getId()).toUriString());

		return ResponseEntity.created(uri).body(new_user);

	}

	@PostMapping("/admin/add/role")
	public ResponseEntity<UserRole> addUserRole(@RequestBody UserRole user_role) throws SQLException{

		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/read/user_role/id=" + user_role.getId()).toUriString());
		return ResponseEntity.created(uri).body(user_service.save(user_role));

	}

	@PutMapping("/traveler/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {

		User updated_user = user_service.update(user);
		return ResponseEntity.ok().body(updated_user);

	}

	@PutMapping("/admin/update/role")
	public ResponseEntity<UserRole> updateUserRole(@RequestBody UserRole user_role) {

		UserRole updated_user_role = user_service.update(user_role);
		return ResponseEntity.ok().body(updated_user_role);

	}

	@Transactional
	@DeleteMapping(path = "/traveler/delete/id={user_id}")
	public void deleteUser(@PathVariable Integer user_id) {
		user_service.deleteById(user_id);

	}

	@Transactional
	@DeleteMapping(path = "/admin/delete/user_role/id={user_role_id}")
	public void deleteUserRole(@PathVariable Integer user_role_id) {
		user_service.deleteUserRole(user_role_id);

	}

}
