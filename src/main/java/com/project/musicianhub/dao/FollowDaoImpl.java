package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.musicianhub.model.Follow;
import com.project.musicianhub.model.Music;

public class FollowDaoImpl implements FollowDao {

	@Override
	public void addFollow(Follow follow) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addFollow(session, follow);

		tx.commit();

		session.close();

	}

	@Override
	public void addFollow(Session session, Follow follow) {

		session.save(follow);
	}

	@Override
	public List<Follow> getFollowersByUser(int user_id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("from Follow where following_user_id = :user_id and follow_status = :follow_status");
		query.setInteger("user_id", user_id);
		query.setBoolean("follow_status", true);
		List<Follow> followList = query.list();
		tx.commit();
		session.close();
		return followList;
	}

	@Override
	public List<Follow> getFollowingByUser(int user_id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("from Follow where follower_user_id = :user_id and follow_status = :follow_status");
		query.setInteger("user_id", user_id);
		query.setBoolean("follow_status", true);
		List<Follow> followingList = query.list();
		tx.commit();
		session.close();
		return followingList;
	}

	public Follow getFollowByFollowingAndFollowerId(Follow follow) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(
				"from Follow where follower_user_id = :follower_user_id and following_user_id = :following_user_id");
		query.setInteger("follower_user_id", follow.getFollowerUser().getId());
		query.setInteger("following_user_id", follow.getFollowingUser().getId());
		List<Follow> followList = query.list();
		if (followList.size() == 0) {
			Follow followObj = new Follow();
			followObj = null;
			followList.add(followObj);

		}

		Follow followOb = followList.get(0);
		tx.commit();
		session.close();

		return followOb;
	}

	public void updateFollow(Follow follow) {

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "update Follow set follow_status = :follow_status where id = :id";
		Query query = session.createQuery(hql);
		query.setInteger("id", follow.getId());
		query.setBoolean("follow_status", follow.isFollow_status());
		int rowCount = query.executeUpdate();
		System.out.println("Rows affected: " + rowCount);

		tx.commit();

		session.close();

	}

}
