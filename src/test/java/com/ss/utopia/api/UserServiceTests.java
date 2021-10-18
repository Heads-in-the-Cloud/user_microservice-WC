package com.ss.utopia.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.test.context.junit4.SpringRunner;

import com.ss.utopia.api.dao.UserRepository;
import com.ss.utopia.api.dao.UserRoleRepository;

import com.ss.utopia.api.pojo.User;
import com.ss.utopia.api.pojo.UserRole;
import com.ss.utopia.api.service.UserService;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

	@Autowired
	UserService user_service;

	@Autowired
	UserRoleRepository user_role_repository;

	@Autowired
	UserRepository user_repository;

	@Autowired
	SessionFactory sessionFactory;

	@Nested
	class testUser {

		String good_id = "uniqueUsername159";
		String non_existing_username = "qwertyuiopasdfghjklasldkfj";
		Integer id;
		String given_name = "firstname";
		String family_name = "lastname";
		String email = "email@domain.com";
		String phone = "(555) 555 5555";
		UserRole user_role = new UserRole();

		String password = "$2a$10$av0tEudFZckwPrnld9/r9.qww/qq4oC0uZpKOoBAloW9PI9Tubr7S";
		User user = new User();

		public void init() {

			user = new User();
			user.setPhone(phone);
			user.setUser_role(user_role);
			user.setUsername(good_id);
			user.setFamily_name(family_name);
			user.setGiven_name(given_name);
			user.setEmail(email);
			user.setPassword(password);

		}

		public void save() {
			this.user = user_service.save(user);
		}

		private void tearDown() {

			user_service.deleteById(user.getId());

		}
		@Test
		public void testUsername() {
			init();
			user.getUser_role().setId(1);
			save();

			assertEquals(user.getUsername(), good_id);
			tearDown();
		}

		@Test
		public void testIsDeleted() {
			init();
			user.getUser_role().setId(1);
			save();
			
			assertEquals(user_repository.existsById(user.getId()), true);
			
			tearDown();
			assertEquals(user_repository.existsById(user.getId()), false);

		}

		@Test
		public void testUserRoleAdmin() {
			init();
			user.getUser_role().setId(1);
			save();

			assertEquals(user_service.findById(user.getId()).getUser_role().getName(), "ROLE_ADMIN");
			tearDown();
		}

		@Test
		public void testUserRoleAgent() {
			init();
			user.getUser_role().setId(2);
			save();

			assertEquals(user_service.findById(user.getId()).getUser_role().getName(), "ROLE_AGENT");
			tearDown();
		}

	@Test
		public void testUserRoleTraveler() {
			init();
			user.getUser_role().setId(3);
			save();

			assertEquals(user_service.findById(user.getId()).getUser_role().getName(), "ROLE_TRAVELER");
			tearDown();
		}

		@Test
	public void testNonExistingUser() {
			User user = user_repository.findAll().get(0);
			user.setId(9999);
			user.setUsername(non_existing_username);

			Assertions.assertThrows(NoSuchElementException.class, () -> {
				assertEquals(Optional.empty(), user_service.update(user));
			});
		}

	}

	@Nested
	class testUserRole {

		String first = "lllllllllllll";
		String last = "llllllllllllll";

		@Test
		public void testRole() {
			assertEquals(user_role_repository.findById(1).get().getName(), "ROLE_ADMIN");
			assertEquals(user_role_repository.findById(2).get().getName(), "ROLE_AGENT");
			assertEquals(user_role_repository.findById(3).get().getName(), "ROLE_TRAVELER");
		}

		@Test
		public void testUpdateRole() {
			User user = user_repository.findAll().get(0);

			user.getUser_role().setId(2);

			User updated_user = user_service.update(user);
			assertEquals(updated_user.getUser_role().getName(), "ROLE_AGENT");
			user_service.update(user);

		}
		
		@Test
		public void testUpdateUsername() {
			User user = user_repository.findAll().get(0);

			user.setGiven_name(first);
			user.setFamily_name(last);
			User updated_user = user_service.update(user);

			assertEquals(updated_user.getGiven_name(), first);
			assertEquals(updated_user.getFamily_name(), last);

			user_service.update(user);

		}

		@Test
		public void testUpdateExistingUsername() {
			User user = user_repository.findAll().get(0);
			User existing_user = user_repository.findAll().get(1);

			user.setUsername(existing_user.getUsername());
			Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
				user_service.update(user);
			});

		}

		@Test
		public void testUpdateExistingEmail() {
			User user = user_repository.findAll().get(0);
			User existing_user = user_repository.findAll().get(1);

			user.setEmail(existing_user.getEmail());

			Assertions.assertThrows(DataIntegrityViolationException.class, () -> {

				user_service.update(user);
			});
		}

		@Test
		public void testUpdateExistingPhone() {
			User user = user_repository.findAll().get(0);
			User existing_user = user_repository.findAll().get(1);

			user.setPhone(existing_user.getPhone());

			Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
				user_service.update(user);
			});
		}

	}

}
