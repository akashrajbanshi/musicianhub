package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.SearchHistory;

public interface SearchHistoryDao {

	public void addSearchHistory(SearchHistory searchHistory);

	public void addSearchHistory(Session session, SearchHistory searchHistory);

	public List<SearchHistory> getSearchHistoryByUser(int user_id);

	public void clearSearchHistory(int user_id);
}
