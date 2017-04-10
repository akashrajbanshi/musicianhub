package com.project.musicianhub.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.project.musicianhub.model.Follow;
import com.project.musicianhub.model.User;

public class FollowDaoImplTest {
	FollowDaoImpl followDaoImpl;

	@Test
	public void testAddFollowFollow() {
		followDaoImpl = new FollowDaoImpl();
		Follow follow1 = new Follow(1, new User(), new User(), true);
		follow1.getFollowerUser().setId(1);
		follow1.getFollowingUser().setId(2);
		followDaoImpl.addFollow(follow1);
	}

	@Test
	public void testAddFollowSessionFollow() {
		followDaoImpl = new FollowDaoImpl();
		Session session = SessionUtil.getSession();
		Follow follow1 = new Follow(1, new User(), new User(), true);
		follow1.getFollowerUser().setId(1);
		follow1.getFollowingUser().setId(2);
		followDaoImpl.addFollow(session, follow1);
	}

	@Test
	public void testGetFollowersByUser() {
		followDaoImpl = new FollowDaoImpl();
		List<Follow> followers = followDaoImpl.getFollowersByUser(1);
		assertEquals(2, followers.size());
		Follow follower = followers.get(0);
		assertEquals(1, follower.getId());
	}

	@Test
	public void testGetFollowingByUser() {
		followDaoImpl = new FollowDaoImpl();
		List<Follow> followings = followDaoImpl.getFollowingByUser(1);
		assertEquals(2, followings.size());
		Follow following = followings.get(0);
		assertEquals(1, following.getId());
	}

	@Test
	public void testGetFollowByFollowingAndFollowerId() {
		followDaoImpl = new FollowDaoImpl();
		Follow follow1 = new Follow(1, new User(), new User(), true);
		follow1.getFollowerUser().setId(1);
		follow1.getFollowingUser().setId(2);
		Follow follow = followDaoImpl.getFollowByFollowingAndFollowerId(follow1);
		assertEquals(1, follow.getId());
		assertEquals(1, follow.getFollowerUser().getId());
		assertEquals(2, follow.getFollowingUser().getId());
	}

	@Test
	public void testUpdateFollow() {
		followDaoImpl = new FollowDaoImpl();
		Follow follow1 = new Follow(1, new User(), new User(), true);
		follow1.getFollowerUser().setId(1);
		follow1.getFollowingUser().setId(2);
		followDaoImpl.updateFollow(follow1);
	}

}
