package com.ss.utopia.api;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit4.SpringRunner;

import com.ss.utopia.api.controller.UserController;
import com.ss.utopia.api.dao.UserRepository;
import com.ss.utopia.api.dao.UserRoleRepository;

import com.ss.utopia.api.pojo.User;
import com.ss.utopia.api.pojo.UserRole;
import com.ss.utopia.api.service.UserService;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

	@Autowired
	UserService user_service;

	@Autowired
	UserRoleRepository user_role_repository;

	@Autowired
	UserRepository user_repository;


	@Nested
	class testUser {

		String good_id = "uniqueUsername159";
		Integer id;
		String given_name = "firstname";
		String family_name = "lastname";
		String email = "email@domain.com";
		String phone = "(555) 555 5555";
		UserRole user_role = new UserRole();

		String password = "$2a$10$av0tEudFZckwPrnld9/r9.qww/qq4oC0uZpKOoBAloW9PI9Tubr7S";
		User user = new User();

		@Transactional
		private void init() {


			this.phone = phone;
			this.email = email;
			this.given_name = given_name;
			this.family_name = family_name;
			this.user_role = user_role_repository.getById(1);
			this.password = password;

		
			user.setPhone(phone);
			user.setUser_role(user_role);
			user.setUsername(good_id);
			user.setFamily_name(family_name);
			user.setGiven_name(given_name);
			user.setEmail(email);
			user.setPassword(password);
			this.user = user_service.save(user).get();
			this.id = user.getId();
			System.out.println(user);



		}

		@Transactional
		private void tearDown() {
			try {
				
				
				user_service.deleteUser(id);
				
	

			} catch (Exception e) {
				System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
				e.printStackTrace();
			}

		}

		@Transactional
		@Test
		public void testUsername() {
			init();
			assertEquals(user.getUsername(), good_id);
			tearDown();
		}

		@Transactional
		@Test
		public void testIsDeleted() {
			init();
			
			tearDown();
			System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLl");
			System.out.println(id);
			assertEquals(user_repository.findById(id), Optional.empty());

			
		}

		@Transactional
		@Test
		public void testUserRole() {
			init();
			assertEquals(user_service.findByUsername(good_id).getUser_role().getName(), "ROLE_ADMIN");
			tearDown();
		}

	}
	
	@Nested
	class testUserRole{
		
		String first = "lllllllllllll";
		String last = "llllllllllllll";
		
		
		public void testRole() {
			assertEquals(user_role_repository.findById(1).get().getName(), "ROLE_ADMIN");
			assertEquals(user_role_repository.findById(2).get().getName(), "ROLE_AGENT");
			assertEquals(user_role_repository.findById(3).get().getName(), "ROLE_TRAVELER");
		}
		
		
		
		
		public void testUpdateRole() {
			User user = user_repository.findAll().get(0);

			user.getUser_role().setName("ROLE_TRAVELER");
			
			User updated_user = user_service.update(user).get();
			assertEquals(updated_user.getUser_role().getId(), 3);
			user_service.update(user);
			
		}
		public void testUpdateUsername() {
			User user = user_repository.findAll().get(0);

			user.setGiven_name(first);
			user.setFamily_name(last);
			User updated_user = user_service.update(user).get();
			
			assertEquals(updated_user.getGiven_name(), first);
			assertEquals(updated_user.getFamily_name(), last);

			user_service.update(user);

		}
		
		@Test
		public void testUpdateExistingUsername() {
			User user = user_repository.findAll().get(0);
			User existing_user = user_repository.findAll().get(1);


			user.setUsername(existing_user.getUsername());
			assertEquals(Optional.empty(),  user_service.update(user));
			
			
		}
		@Test
		public void testUpdateExistingEmail() {
			User user = user_repository.findAll().get(0);
			User existing_user = user_repository.findAll().get(1);
			
			user.setEmail(existing_user.getEmail());
			assertEquals(Optional.empty(),  user_service.update(user));

		}
		
		@Test
		public void testUpdateExistingPhone() {
			User user = user_repository.findAll().get(0);
			User existing_user = user_repository.findAll().get(1);
			
			user.setPhone(existing_user.getPhone());
			assertEquals(Optional.empty(),  user_service.update(user));

		}
		
		@Test
		public void testNonExistingUser() {
			User user = user_repository.findAll().get(0);
			user.setId(9999);
			
			assertEquals(Optional.empty(),  user_service.update(user));
			
			
		}

		
		
		
		
		
	}

	

}
