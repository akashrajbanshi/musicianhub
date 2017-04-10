package com.project.musicianhub.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.project.musicianhub.model.Like;
import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.User;

public class LikeDaoImplTest {

	LikeDaoImpl likeDaoImpl;

	@Test
	public void testAddLikeLike() {
		likeDaoImpl = new LikeDaoImpl();
		Like like1 = new Like(1, new Music(), new User(), true);
		like1.getMusic().setId(1);
		like1.getUser().setId(1);
		likeDaoImpl.addLike(like1);
	}

	@Test
	public void testAddLikeSessionLike() {
		likeDaoImpl = new LikeDaoImpl();
		Session session = SessionUtil.getSession();
		Like like1 = new Like(1, new Music(), new User(), true);
		like1.getMusic().setId(1);
		like1.getUser().setId(1);
		likeDaoImpl.addLike(session, like1);
	}

	@Test
	public void testGetLikebyMusic() {
		likeDaoImpl = new LikeDaoImpl();
		List<Like> likes = likeDaoImpl.getLikebyMusic(1);
		assertEquals(1, likes.size());
		Like like = likes.get(0);
		assertEquals(1, like.getId());
		assertEquals(true, like.isLiked());
	}

	@Test
	public void testUpdateLike() {
		likeDaoImpl = new LikeDaoImpl();
		Like like1 = new Like(1, new Music(), new User(), true);
		like1.getMusic().setId(1);
		like1.getUser().setId(1);
		int liked = likeDaoImpl.updateLike(1, like1);
		assertEquals(1, liked);
	}

	@Test
	public void testGetLikebyMusicAndUser() {
		likeDaoImpl = new LikeDaoImpl();
		Like like1 = new Like(1, new Music(), new User(), true);
		like1.getMusic().setId(1);
		like1.getUser().setId(1);
		Like like = likeDaoImpl.getLikebyMusicAndUser(1, 1);
		assertEquals(like1.getMusic().getId(), like.getMusic().getId());
		assertEquals(like1.getUser().getId(), like.getUser().getId());
	}

}
