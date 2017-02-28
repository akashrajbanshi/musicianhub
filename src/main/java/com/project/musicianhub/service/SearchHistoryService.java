package com.project.musicianhub.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.SearchHistory;

public interface SearchHistoryService {

	public void addSearchHistory(SearchHistory searchHistory);

	public List<Music> getSearchHistoryByUser(int user_id, HttpServletRequest request);

	public void clearSearchHistory(int user_id);

}
