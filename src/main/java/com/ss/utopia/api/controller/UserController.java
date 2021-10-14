package com.ss.utopia.api.controller;

import java.net.URI;


import java.util.List;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
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

	
	@Autowired
	UserService user_service;

	@GetMapping(path = "/read")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok().body(user_service.findAllUsers());
	}
	
	
	@GetMapping(path = "/read/user={username}")
	public ResponseEntity<User> getUserByName(@PathVariable String username) {
		return ResponseEntity.ok().body(user_service.findByUsername(username));
	}
	
	
	@GetMapping(path = "/read/id={user_id}")
	public ResponseEntity<User> getUserById(@PathVariable Integer user_id) {
		return ResponseEntity.ok().body(user_service.findById(user_id));
	}
	
	
	@GetMapping(path = "/read/user_roles")
	public ResponseEntity<List<UserRole>> getAllUserRoles() {
		return ResponseEntity.ok().body(user_service.findAllUserRoles());
	}
	
	
	@GetMapping(path = "/read/user_role/id={user_role_id}")
	public ResponseEntity<UserRole> getUserRoleById(@PathVariable Integer user_role_id) {
		return ResponseEntity.ok().body(user_service.findUserRoleById(user_role_id));
	}
	
	
	
	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User user){
	
		Optional<User> new_user = user_service.save(user);
		if(new_user.isEmpty()) {
			ResponseEntity.badRequest().build();
		}
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/read/id=" + user.getId())
				.toUriString());
		return ResponseEntity.created(uri).body(new_user.get());

	}
	
	@PostMapping("/add/role")
	public ResponseEntity<UserRole> addUserRole(@RequestBody UserRole user_role){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/read/user_role/id=" + user_role.getId())
				.toUriString());
		return ResponseEntity.created(uri).body(user_service.save(user_role));
	
	}
	
	@PutMapping("/update/role")
	public ResponseEntity<UserRole> updateUserRole(@RequestBody UserRole user_role){
		Optional<UserRole> updated_user_role = user_service.update(user_role);
		if(updated_user_role.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(updated_user_role.get());
	}
	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody User user){
		Optional<User> updated_user = user_service.update(user);
		if(updated_user.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(updated_user.get());
	}
	
	
		

	@Transactional
	@DeleteMapping(path = "/delete/id={user_id}")
	public void deleteUser(@PathVariable Integer user_id) {
		user_service.deleteUser(user_id);
	}
	@Transactional
	@DeleteMapping(path = "/delete/user_role/id={user_role_id}")
	public void deleteUserRole(@PathVariable Integer user_role_id) {
		user_service.deleteUserRole(user_role_id);
	}
	

}
