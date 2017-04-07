package com.project.musicianhub.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.model.Comment;

/**
 * Service interface for Comment
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface CommentService {

	/**
	 * Creates the comment using the comment object
	 * 
	 * @param comment
	 *            comment object
	 * @return comment object
	 */
	public Comment addComment(Comment comment);

	/**
	 * Gets the comment list by the music id
	 * 
	 * @param music_id
	 *            music id
	 * @param request
	 *            http request
	 * @return comment list
	 */
	public List<Comment> getCommentByMusic(int music_id, HttpServletRequest request);

}
