package com.project.musicianhub.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.project.musicianhub.model.Like;
import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.User;

public class LikeServiceImplTest {

	LikeServiceImpl likeServiceImpl;

	@Test
	public void testAddLike() {
		likeServiceImpl = new LikeServiceImpl();
		Like like1 = new Like(1, new Music(), new User(), true);
		like1.getMusic().setId(1);
		like1.getUser().setId(1);
		likeServiceImpl.addLike(like1);
	}

	@Test
	public void testGetLikeByMusic() {
		likeServiceImpl = new LikeServiceImpl();
		List<Like> likes = likeServiceImpl.getLikeByMusic(1);
		assertEquals(1, likes.size());
		Like like = likes.get(0);
		assertEquals(1, like.getId());
		assertEquals(true, like.isLiked());
	}

	@Test
	public void testUpdateLike() {
		likeServiceImpl = new LikeServiceImpl();
		Like like1 = new Like(1, new Music(), new User(), true);
		like1.getMusic().setId(1);
		like1.getUser().setId(1);
		int liked = likeServiceImpl.updateLike(1, like1);
		assertEquals(1, liked);
	}

	@Test
	public void testGetLikeByUser() {
		likeServiceImpl = new LikeServiceImpl();
		Like like1 = new Like(1, new Music(), new User(), true);
		like1.getMusic().setId(1);
		like1.getUser().setId(1);
		Like like = likeServiceImpl.getLikeByUser(1, 1);
		assertEquals(like1.getMusic().getId(), like.getMusic().getId());
		assertEquals(like1.getUser().getId(), like.getUser().getId());
	}

}
