package com.project.musicianhub.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.project.musicianhub.model.User;

public class UserDaoImplTest {

	UserDaoImpl userDaoImpl;

	@Test
	public void testAddUserUser() {
		userDaoImpl = new UserDaoImpl();
		User user1 = new User(1, "Akash Rajbanshi", "akash", "akashrajbanshi@hotmail.com", "abc", "abc", "male",
				234234234);
		User user = userDaoImpl.addUser(user1);
		assertEquals(user.getEmail(), user1.getEmail());
	}

	@Test
	public void testAddUserSessionUser() {
		userDaoImpl = new UserDaoImpl();
		Session session = SessionUtil.getSession();
		User user1 = new User(1, "Akash Rajbanshi", "akash", "akashrajbanshi@hotmail.com", "abc", "abc", "male",
				234234234);
		userDaoImpl.addUser(session, user1);
	}

	@Test
	public void testGetUsers() {
		userDaoImpl = new UserDaoImpl();
		List<User> users = userDaoImpl.getUsers();
		assertEquals(2, users.size());

		User user = users.get(0);

		assertEquals(1, user.getId());
		assertEquals("Akash Rajbanshi", user.getName());
		assertEquals("akash", user.getUsername());
		assertEquals("akashrajbanshi@hotmail.com", user.getEmail());
		assertEquals("abc", user.getUser_img_path());
		assertEquals("abc", user.getPassword());
		assertEquals(234234234, user.getPhone_no());
	}

	@Test
	public void testDeleteUsers() {
		userDaoImpl = new UserDaoImpl();
		int deleted = userDaoImpl.deleteUsers(1);
		assertEquals(1, deleted);
	}

	@Test
	public void testUpdateUsers() {
		userDaoImpl = new UserDaoImpl();
		User user = new User(1, "Akash Rajbanshi", "akash", "akashrajbanshi@hotmail.com", "abc", "abc", "male",
				234234234);
		int updated = userDaoImpl.updateUsers(user);
		assertEquals(1, updated);
	}

	@Test
	public void testGetUserById() {
		userDaoImpl = new UserDaoImpl();
		User user = userDaoImpl.getUserById(1);
		assertEquals(1, user.getId());
		assertEquals("Akash Rajbanshi", user.getName());
		assertEquals("akash", user.getUsername());
		assertEquals("akashrajbanshi@hotmail.com", user.getEmail());
		assertEquals("abc", user.getUser_img_path());
		assertEquals("abc", user.getPassword());
		assertEquals(234234234, user.getPhone_no());
	}

	@Test
	public void testGetLastInsertedUserId() {
		userDaoImpl = new UserDaoImpl();
		int id = userDaoImpl.getLastInsertedUserId();
		assertEquals(1, id);
	}

	@Test
	public void testGetUserByUserName() {
		userDaoImpl = new UserDaoImpl();
		User userObj = new User(1, "Akash Rajbanshi", "akash", "akashrajbanshi@hotmail.com", "abc", "abc", "male",
				234234234);
		User user = userDaoImpl.getUserByUserName(userObj);
		assertEquals(1, user.getId());
		assertEquals("Akash Rajbanshi", user.getName());
		assertEquals("akash", user.getUsername());
		assertEquals("akashrajbanshi@hotmail.com", user.getEmail());
		assertEquals("abc", user.getUser_img_path());
		assertEquals("abc", user.getPassword());
		assertEquals(234234234, user.getPhone_no());
	}

	@Test
	public void testCheckIfTableEmpty() {
		userDaoImpl = new UserDaoImpl();
		boolean emptyTable = userDaoImpl.checkIfTableEmpty();
		assertTrue(emptyTable);
	}

}
