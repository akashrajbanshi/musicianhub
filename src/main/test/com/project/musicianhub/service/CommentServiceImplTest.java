package com.project.musicianhub.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.project.musicianhub.model.Comment;
import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.User;

public class CommentServiceImplTest {

	CommentServiceImpl commentServiceImpl;

	@Test
	public void testAddComment() {
		commentServiceImpl = new CommentServiceImpl();
		Comment comment1 = new Comment(1, new Music(), new User(), "Yes this is a comment!");
		comment1.getMusic().setId(1);
		comment1.getUser().setId(1);
		Comment comment = commentServiceImpl.addComment(comment1);
		assertEquals(comment, comment1);
	}

	@Test
	public void testGetCommentByMusic() {
		commentServiceImpl = new CommentServiceImpl();
		List<Comment> comments = commentServiceImpl.getCommentByMusic(1, null);
		assertEquals(1, comments.size());
	}

}
