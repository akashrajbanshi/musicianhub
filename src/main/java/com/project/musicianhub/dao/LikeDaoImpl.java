package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.musicianhub.model.Like;
import com.project.musicianhub.model.Music;

public class LikeDaoImpl implements LikeDao {

	@Override
	public void addLike(Like like) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addLike(session, like);

		tx.commit();

		session.close();

	}

	@Override
	public void addLike(Session session, Like like) {
		session.save(like);
	}

	@Override
	public List<Like> getLikebyMusic(int music_id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Like where music_id = :music_id and liked = true");
		query.setInteger("music_id", music_id);
		List<Like> likes = query.list();
		tx.commit();
		session.close();
		return likes;
	}

	@Override
	public int updateLike(int id, Like like) {
		if (id <= 0)
			return 0;
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "update Like set liked = :liked where user_id = :user_id and music_id = :music_id";
		Query query = session.createQuery(hql);
		query.setInteger("music_id", id);
		query.setInteger("user_id", like.getUser().getId());
		query.setBoolean("liked", like.isLiked());
		int rowCount = query.executeUpdate();
		System.out.println("Rows affected: " + rowCount);

		tx.commit();

		session.close();
		return rowCount;
	}

	public Like getLikebyMusicAndUser(int music_id, int user_id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Like where music_id = :music_id and user_id = :user_id");
		query.setInteger("music_id", music_id);
		query.setInteger("user_id", user_id);
		List<Like> like = query.list();
		tx.commit();
		session.close();
		if (like.size() == 0) {
			return null;
		}
		return like.get(0);
	}

}
