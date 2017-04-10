package com.project.musicianhub.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.project.musicianhub.model.Comment;
import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.User;

public class CommentDaoImplTest {

	CommentDaoImpl commentDaoImpl;

	@Test
	public void testAddCommentComment() {
		commentDaoImpl = new CommentDaoImpl();
		Comment comment1 = new Comment(1, new Music(), new User(), "Yes this is a comment!");
		comment1.getMusic().setId(1);
		comment1.getUser().setId(1);
		Comment comment = commentDaoImpl.addComment(comment1);
		assertEquals(comment, comment1);
	}

	@Test
	public void testAddCommentSessionComment() {
		commentDaoImpl = new CommentDaoImpl();
		Session session = SessionUtil.getSession();
		Comment comment1 = new Comment(1, new Music(), new User(), "Yes this is a comment!");
		comment1.getMusic().setId(1);
		comment1.getUser().setId(1);
		Comment comment = commentDaoImpl.addComment(comment1);
		commentDaoImpl.addComment(session, comment);
		assertEquals(comment, comment1);
	}

	@Test
	public void testGetCommentByMusic() {
		commentDaoImpl = new CommentDaoImpl();
		List<Comment> comments = commentDaoImpl.getCommentByMusic(1);
		assertEquals(1, comments.size());
	}

}
