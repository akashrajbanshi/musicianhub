package com.project.musicianhub.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.dao.CommentDaoImpl;
import com.project.musicianhub.model.Comment;

public class CommentServiceImpl implements CommentService {

	CommentDaoImpl commentDaoImpl = new CommentDaoImpl();

	@Override
	public Comment addComment(Comment comment) {
		return commentDaoImpl.addComment(comment);

	}

	@Override
	public List<Comment> getCommentByMusic(int music_id, HttpServletRequest request) {
		List<Comment> comments = commentDaoImpl.getCommentByMusic(music_id);
		String serverPath = request.getServerName().concat(":") + request.getServerPort();
		List<Comment> commentList = new ArrayList<>();

		for (Comment comment : comments) {
	
			comment.getUser().setCommentUserImagePath(serverPath);
			commentList.add(comment);
		}
		return commentList;
	}

}
