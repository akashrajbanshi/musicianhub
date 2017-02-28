package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.musicianhub.model.Comment;

public class CommentDaoImpl implements CommentDao {

	@Override
	public Comment addComment(Comment comment) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addComment(session, comment);

		tx.commit();

		session.close();
		return comment;
	}

	@Override
	public void addComment(Session session, Comment comment) {

		session.save(comment);
	}

	@Override
	public List<Comment> getCommentByMusic(int music_id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Comment where music_id = :music_id");
		query.setInteger("music_id", music_id);
		List<Comment> comment = query.list();
		tx.commit();
		session.close();
		return comment;

	}

}
