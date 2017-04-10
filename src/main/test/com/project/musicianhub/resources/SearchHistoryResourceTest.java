package com.project.musicianhub.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.Test;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.SearchHistory;
import com.project.musicianhub.model.User;

public class SearchHistoryResourceTest {

	private static SearchHistoryResource mockedSearchHistoryResource;

	private static SearchHistory searchHistory1;
	private static SearchHistory searchHistory2;
	private static Response response;

	@BeforeClass
	public static void setUp() {
		mockedSearchHistoryResource = mock(SearchHistoryResource.class);
		searchHistory1 = new SearchHistory(1, new Music(), new User());
		searchHistory1.getMusic().setId(1);
		searchHistory1.getUser().setId(1);
		searchHistory2 = new SearchHistory(2, new Music(), new User());
		searchHistory2.getMusic().setId(1);
		searchHistory2.getUser().setId(1);

		response = Response.ok().build();

		when(mockedSearchHistoryResource.getSearchHistoryByUser(1, null))
				.thenReturn(Arrays.asList(searchHistory1.getMusic(), searchHistory2.getMusic()));
		when(mockedSearchHistoryResource.addSearchHistory(1, searchHistory1, null)).thenReturn(response);
	}

	@Test
	public void testGetSearchHistoryByUser() {
		List<Music> musicList = mockedSearchHistoryResource.getSearchHistoryByUser(1, null);
		Music music = musicList.get(0);
		assertEquals(searchHistory1.getMusic().getId(), music.getId());
	}

	@Test
	public void testAddSearchHistory() {
		Response response = mockedSearchHistoryResource.addSearchHistory(1, searchHistory1, null);
		assertEquals(response, this.response);

	}

	@Test
	public void testClearSearchHistory() {
		Response response = mockedSearchHistoryResource.clearSearchHistory(1, null);
		assertEquals(response, this.response);
	}

}
