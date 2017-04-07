package com.project.musicianhub.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.project.musicianhub.model.Like;
import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Response;
import com.project.musicianhub.model.User;

public class LikeResourceTest {
	private static LikeResource mockedLikeResource;

	private static Like like1;
	private static Like like2;
	private static Response response;

	@BeforeClass
	public static void setUp() {
		mockedLikeResource = mock(LikeResource.class);
		like1 = new Like(1, new Music(), new User(), true);
		like1.getMusic().setId(1);
		like2.getUser().setId(1);

		like1 = new Like(2, new Music(), new User(), true);
		like1.getMusic().setId(2);
		like2.getUser().setId(1);

		response = new Response();
		response.setMessage("Liked!");

		when(mockedLikeResource.addLike(like1.getMusic().getId(), like1, null)).thenReturn(response);
		when(mockedLikeResource.getAllLikes(like1.getMusic().getId())).thenReturn(Arrays.asList(like1, like2));
		when(mockedLikeResource.getAllLikesByUser(like1.getMusic().getId(), like1.getUser().getId())).thenReturn(like1);
	}

	@Test
	public void testGetAllLikes() {

		List<Like> likes = mockedLikeResource.getAllLikes(1);
		assertEquals(1, likes.size());
		Like like = likes.get(0);
		assertEquals(1, like.getId());
		assertEquals(true, like.isLiked());
	}

	@Test
	public void testGetAllLikesByUser() {
		Like like = mockedLikeResource.getAllLikesByUser(1, 1);
		assertEquals(1, like.getId());
		assertEquals(true, like.isLiked());
	}

	@Test
	public void testAddLike() {
		Response response = mockedLikeResource.addLike(1, like1, null);
		assertNotNull(response);
		assertEquals("Liked!", response.getMessage());
	}

}
