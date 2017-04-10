package com.project.musicianhub.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.project.musicianhub.model.Response;
import com.project.musicianhub.model.User;

public class UserServiceImplTest {

	UserServiceImpl userServiceImpl;

	@Test
	public void testAddUser() {
		userServiceImpl = new UserServiceImpl();
		User user1 = new User(1, "Akash Rajbanshi", "akash", "akashrajbanshi@hotmail.com", "abc", "abc", "male",
				234234234);
		Response response = userServiceImpl.addUser(user1);
		assertEquals(response.getMessage(), "User added Successfully!");
	}

	@Test
	public void testGetUsers() {
		userServiceImpl = new UserServiceImpl();
		List<User> users = userServiceImpl.getUsers();
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
		userServiceImpl = new UserServiceImpl();
		int deleted = userServiceImpl.deleteUsers(1);
		assertEquals(1, deleted);
	}

	@Test
	public void testUpdateUsers() {
		userServiceImpl = new UserServiceImpl();
		User user = new User(1, "Akash Rajbanshi", "akash", "akashrajbanshi@hotmail.com", "abc", "abc", "male",
				234234234);
		Response response = userServiceImpl.updateUsers(user, null);
		assertEquals("User updated Successfully", response.getMessage());
	}

	@Test
	public void testGetUserById() {
		userServiceImpl = new UserServiceImpl();
		User user = userServiceImpl.getUserById(1);
		assertEquals(1, user.getId());
		assertEquals("Akash Rajbanshi", user.getName());
		assertEquals("akash", user.getUsername());
		assertEquals("akashrajbanshi@hotmail.com", user.getEmail());
		assertEquals("abc", user.getUser_img_path());
		assertEquals("abc", user.getPassword());
		assertEquals(234234234, user.getPhone_no());
	}

	@Test
	public void testUploadUserImage() {
		userServiceImpl = new UserServiceImpl();
		User user = new User(1, "Akash Rajbanshi", "akash", "akashrajbanshi@hotmail.com", "abc", "abc", "male",
				234234234);
		String userImage = userServiceImpl.uploadUserImage(null, null);
		assertEquals(user.getUser_img_path(), userImage);
	}

	@Test
	public void testGetLastInsertedUserId() {
		userServiceImpl = new UserServiceImpl();
		int id = userServiceImpl.getLastInsertedUserId();
		assertEquals(1, id);
	}

	@Test
	public void testGetUserCredentials() {
		userServiceImpl = new UserServiceImpl();
		User user = new User(1, "Akash Rajbanshi", "akash", "akashrajbanshi@hotmail.com", "abc", "abc", "male",
				234234234);
		Response response = userServiceImpl.getUserCredentials(user, null);
		assertEquals("User not found", response.getMessage());
	}

}
