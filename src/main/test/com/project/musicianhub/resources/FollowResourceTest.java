package com.project.musicianhub.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.project.musicianhub.model.Follow;
import com.project.musicianhub.model.Response;
import com.project.musicianhub.model.User;

public class FollowResourceTest {

	private static FollowResource mockedFollowResource;

	private static Follow follow1;
	private static Follow follow2;
	private static Response response;

	@BeforeClass
	public static void setUp() {
		mockedFollowResource = mock(FollowResource.class);
		follow1 = new Follow(1, new User(), new User(), true);
		follow1.getFollowerUser().setId(1);
		follow1.getFollowingUser().setId(2);
		follow2 = new Follow(2, new User(), new User(), false);
		follow2.getFollowerUser().setId(1);
		follow2.getFollowingUser().setId(2);

		response = new Response();
		response.setMessage("follow");

		when(mockedFollowResource.getFollowersByUserId(follow1.getId(), null))
				.thenReturn(Arrays.asList(follow1, follow2));
		when(mockedFollowResource.addFollowing(follow1.getId(), follow1, null)).thenReturn(response);
	}

	@Test
	public void testAddFollowing() {
		Response response = mockedFollowResource.addFollowing(1, follow1, null);
		assertEquals("follow", response.getMessage());
	}

	@Test
	public void testGetFollowByFollowingAndFollowerId() {
		Follow follow = mockedFollowResource.getFollowByFollowingAndFollowerId(1, follow1);
		assertEquals(1, follow.getId());
		assertEquals(1, follow.getFollowerUser().getId());
		assertEquals(2, follow.getFollowingUser().getId());
	}

	@Test
	public void testGetFollowersByUserId() {
		List<Follow> followers = mockedFollowResource.getFollowersByUserId(1, null);
		assertEquals(2, followers.size());
		Follow follower = followers.get(0);
		assertEquals(1, follower.getId());
	}

	@Test
	public void testGetFollowingByUserId() {
		List<Follow> followings = mockedFollowResource.getFollowingByUserId(1, null);
		assertEquals(2, followings.size());
		Follow following = followings.get(0);
		assertEquals(1, following.getId());
	}

}
