package com.project.musicianhub.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.project.musicianhub.model.Response;
import com.project.musicianhub.model.User;

public class UserResourceTest {

	private static UserResource mockedUserResource;

	private static User user1;
	private static User user2;
	private static Response response;

	@BeforeClass
	public static void setUp() {
		mockedUserResource = mock(UserResource.class);
		user1 = new User(1, "Akash Rajbanshi", "akash", "akashrajbanshi@hotmail.com", "abc", "abc", "male", 234234234);
		user2 = new User(2, "Girju Rajbanshi", "girju", "girju@hotmail.com", "abc", "abc", "female", 234234234);

		response = new Response();
		response.setMessage("User Updated Successfully!");

		when(mockedUserResource.getUsers()).thenReturn(Arrays.asList(user1, user2));
		when(mockedUserResource.getUserById(1, null)).thenReturn(user1);
		when(mockedUserResource.addUser(user1, null)).thenReturn(response);

		when(mockedUserResource.updateUser(user1, null)).thenReturn(response);

	}

	@Test
	public void testGetUsers() {

		List<User> allUsers = mockedUserResource.getUsers();

		assertEquals(2, allUsers.size());

		User user = allUsers.get(0);

		assertEquals(1, user.getId());
		assertEquals("Akash Rajbanshi", user.getName());
		assertEquals("akash", user.getUsername());
		assertEquals("akashrajbanshi@hotmail.com", user.getEmail());
		assertEquals("abc", user.getUser_img_path());
		assertEquals("abc", user.getPassword());
		assertEquals(234234234, user.getPhone_no());
	}

	@Test
	public void testGetUserById() {

		User user = mockedUserResource.getUserById(1, null);
		assertEquals(1, user.getId());
		assertEquals("Akash Rajbanshi", user.getName());
		assertEquals("akash", user.getUsername());
		assertEquals("akashrajbanshi@hotmail.com", user.getEmail());
		assertEquals("abc", user.getUser_img_path());
		assertEquals("abc", user.getPassword());
		assertEquals(234234234, user.getPhone_no());
	}

	@Test
	public void testAddUser() {
		Response response = mockedUserResource.addUser(user1, null);
		assertNotNull(response);
		assertEquals("User Added Successfully!", response.getMessage());
	}

	@Test
	public void testUpdateUser() {
		Response response = mockedUserResource.updateUser(user1, null);
		assertNotNull(response);
		assertEquals("User Updated Successfully!", response.getMessage());
	}

}
