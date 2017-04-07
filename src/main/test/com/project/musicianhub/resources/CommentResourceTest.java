package com.project.musicianhub.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.project.musicianhub.model.Comment;
import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Response;
import com.project.musicianhub.model.User;

public class CommentResourceTest {

	private static CommentResource mockedCommentResource;

	private static Comment comment1;
	private static Comment comment2;
	private static Response response;

	@BeforeClass
	public static void setUp() {
		mockedCommentResource = mock(CommentResource.class);
		comment1 = new Comment(1, new Music(), new User(), "Yes this is a comment!");
		comment1.getMusic().setId(1);
		comment1.getUser().setId(1);
		comment2 = new Comment(2, new Music(), new User(), "Yes this is another comment!");
		comment2.getMusic().setId(1);
		comment2.getUser().setId(1);

		response = new Response();
		response.setMessage("Music Updated Successfully!");

		when(mockedCommentResource.getAllComments(comment1.getMusic().getId(), null))
				.thenReturn(Arrays.asList(comment1, comment2));
		when(mockedCommentResource.addComment(comment1.getMusic().getId(), comment1, null)).thenReturn(comment1);
	}

	@Test
	public void testGetAllComments() {
		List<Comment> allComments = mockedCommentResource.getAllComments(1, null);
		assertEquals(2, allComments.size());
		Comment comment = allComments.get(0);
		assertEquals(1, comment.getId());
		assertEquals("Yes this is a comment!", comment.getComment());
	}

	@Test
	public void testAddComment() {
		Comment comment = mockedCommentResource.addComment(1, comment1, null);
		assertNotNull(comment);
		assertEquals(1, comment.getId());
		assertEquals("Yes this is a comment!", comment.getComment());
	}

}
