package com.project.musicianhub.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.dao.CommentDaoImpl;
import com.project.musicianhub.model.Comment;

/**
 * Service implementation for Comment
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class CommentServiceImpl implements CommentService {

	CommentDaoImpl commentDaoImpl = new CommentDaoImpl();

	/**
	 * Creates the comment using the comment object
	 * 
	 * @param comment
	 * @return
	 */
	@Override
	public Comment addComment(Comment comment) {
		return commentDaoImpl.addComment(comment);

	}

	/**
	 * Gets the comment list by the music id
	 * 
	 * @param music_id
	 * @param request
	 *            http request
	 * @return comment list
	 */
	@Override
	public List<Comment> getCommentByMusic(int music_id, HttpServletRequest request) {
		// gets the comment list by music id
		List<Comment> comments = commentDaoImpl.getCommentByMusic(music_id);
		// server path for the project
		String serverPath = request.getServerName().concat(":") + request.getServerPort();
		List<Comment> commentList = new ArrayList<>();

		for (Comment comment : comments) {
			// sets the user image path for the all the comments
			comment.getUser().setCommentUserImagePath(serverPath);
			commentList.add(comment);
		}
		return commentList;
	}

}
