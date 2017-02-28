package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.Comment;

public interface CommentDao {

	public Comment addComment(Comment comment);

	public void addComment(Session session, Comment comment);
	
	public List<Comment> getCommentByMusic(int music_id);

}
