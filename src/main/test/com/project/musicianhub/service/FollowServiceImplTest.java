package com.project.musicianhub.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.project.musicianhub.model.Follow;
import com.project.musicianhub.model.User;

public class FollowServiceImplTest {
	FollowServiceImpl followServiceImpl;

	@Test
	public void testAddFollow() {
		followServiceImpl = new FollowServiceImpl();
		Follow follow1 = new Follow(1, new User(), new User(), true);
		follow1.getFollowerUser().setId(1);
		follow1.getFollowingUser().setId(2);
		followServiceImpl.addFollow(follow1);
	}

	@Test
	public void testGetFollowByFollowingAndFollowerId() {
		followServiceImpl = new FollowServiceImpl();
		Follow follow1 = new Follow(1, new User(), new User(), true);
		follow1.getFollowerUser().setId(1);
		follow1.getFollowingUser().setId(2);
		Follow follow = followServiceImpl.getFollowByFollowingAndFollowerId(follow1);
		assertEquals(1, follow.getId());
		assertEquals(1, follow.getFollowerUser().getId());
		assertEquals(2, follow.getFollowingUser().getId());
	}

	@Test
	public void testGetFollowersByUser() {
		followServiceImpl = new FollowServiceImpl();
		List<Follow> followers = followServiceImpl.getFollowersByUser(1, null);
		assertEquals(2, followers.size());
		Follow follower = followers.get(0);
		assertEquals(1, follower.getId());
	}

	@Test
	public void testGetFollowingByUser() {
		followServiceImpl = new FollowServiceImpl();
		List<Follow> followings = followServiceImpl.getFollowingByUser(1, null);
		assertEquals(2, followings.size());
		Follow following = followings.get(0);
		assertEquals(1, following.getId());
	}

}
