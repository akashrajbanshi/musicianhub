package com.project.musicianhub.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.model.Comment;

public interface CommentService {
	
	public Comment addComment(Comment comment);
	
	public List<Comment> getCommentByMusic(int music_id, HttpServletRequest request);

}
