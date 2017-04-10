package com.project.musicianhub.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.SearchHistory;
import com.project.musicianhub.model.User;

public class SearchHistoryServiceImplTest {

	SearchHistoryServiceImpl searchHistoryServiceImpl;

	@Test
	public void testAddSearchHistory() {
		searchHistoryServiceImpl = new SearchHistoryServiceImpl();
		SearchHistory searchHistory1 = new SearchHistory(1, new Music(), new User());
		searchHistory1.getMusic().setId(1);
		searchHistory1.getUser().setId(1);
		searchHistoryServiceImpl.addSearchHistory(searchHistory1);
	}

	@Test
	public void testGetSearchHistoryByUser() {
		searchHistoryServiceImpl = new SearchHistoryServiceImpl();
		List<Music> musics = searchHistoryServiceImpl.getSearchHistoryByUser(1, null);
		Music music = musics.get(0);
		assertEquals(1, music.getId());
		assertEquals("Timilai", music.getTitle());
		assertEquals("pop", music.getGenre());
		assertEquals(new Date(), new Date());
		assertEquals("img/1.jpg", music.getAlbum_art_path());
		assertEquals("music/1.mp3", music.getMusic_path());
		assertEquals(false, music.isDeleted());
	}

	@Test
	public void testClearSearchHistory() {
		searchHistoryServiceImpl = new SearchHistoryServiceImpl();
		searchHistoryServiceImpl.clearSearchHistory(1);
	}

}
