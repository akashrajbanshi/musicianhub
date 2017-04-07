package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.Comment;

/**
 * Dao Interface for the comments
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface CommentDao {

	/**
	 * Adds comments to the database
	 * 
	 * @param comment
	 * @return comment object
	 */
	public Comment addComment(Comment comment);

	/**
	 * Adds comments to the database
	 * 
	 * @param session
	 *            params
	 * @param comment
	 *            parameter
	 */
	public void addComment(Session session, Comment comment);

	/**
	 * Gets the music's comments from the music id
	 * 
	 * @param music_id
	 * @return comment's list
	 */
	public List<Comment> getCommentByMusic(int music_id);

}
