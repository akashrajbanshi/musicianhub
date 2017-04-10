package com.project.musicianhub.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.SearchHistory;
import com.project.musicianhub.model.User;

public class SearchHistroyDaoImplTest {

	SearchHistroyDaoImpl searchHistroyDaoImpl;

	@Test
	public void testAddSearchHistorySearchHistory() {
		searchHistroyDaoImpl = new SearchHistroyDaoImpl();
		SearchHistory searchHistory1 = new SearchHistory(1, new Music(), new User());
		searchHistory1.getMusic().setId(1);
		searchHistory1.getUser().setId(1);
		searchHistroyDaoImpl.addSearchHistory(searchHistory1);
	}

	@Test
	public void testAddSearchHistorySessionSearchHistory() {
		searchHistroyDaoImpl = new SearchHistroyDaoImpl();
		Session session = SessionUtil.getSession();
		SearchHistory searchHistory1 = new SearchHistory(1, new Music(), new User());
		searchHistory1.getMusic().setId(1);
		searchHistory1.getUser().setId(1);
		searchHistroyDaoImpl.addSearchHistory(session, searchHistory1);
	}

	@Test
	public void testGetSearchHistoryByUser() {
		searchHistroyDaoImpl = new SearchHistroyDaoImpl();
		List<SearchHistory> searchHistories = searchHistroyDaoImpl.getSearchHistoryByUser(1);
		SearchHistory searchHistory = searchHistories.get(0);
		assertEquals(1, searchHistory.getId());
	}

	@Test
	public void testClearSearchHistory() {
		searchHistroyDaoImpl = new SearchHistroyDaoImpl();
		searchHistroyDaoImpl.clearSearchHistory(1);
	}

}
