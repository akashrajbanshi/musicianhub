package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.musicianhub.model.Comment;

/**
 * Dao implementation for the comments
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class CommentDaoImpl implements CommentDao {

	/**
	 * Adds comments to the database
	 * 
	 * @param comment
	 *            comment object
	 * @return comment object
	 */
	@Override
	public Comment addComment(Comment comment) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addComment(session, comment);

		tx.commit();

		session.close();
		return comment;
	}

	/**
	 * Adds comments to the database
	 * 
	 * @param session
	 *            session object
	 * @param comment
	 *            comment object
	 */
	@Override
	public void addComment(Session session, Comment comment) {

		session.save(comment);
	}

	/**
	 * Gets the music's comments from the music id
	 * 
	 * @param music_id
	 *            music id
	 * @return comment's list
	 */
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
