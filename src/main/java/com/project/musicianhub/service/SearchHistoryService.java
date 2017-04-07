package com.project.musicianhub.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.SearchHistory;

/**
 * Service interface for the Search History
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface SearchHistoryService {

	/**
	 * Creates the Search History for the user
	 * 
	 * @param searchHistory
	 *            searchHistory object
	 */
	public void addSearchHistory(SearchHistory searchHistory);

	/**
	 * Gets all the search history by user's id
	 * 
	 * @param user_id
	 *            user id
	 * @param request
	 *            http servlet request
	 * @return music list
	 */
	public List<Music> getSearchHistoryByUser(int user_id, HttpServletRequest request);

	/**
	 * Clears the search history according to the user
	 * 
	 * @param user_id
	 *            user id
	 */

	public void clearSearchHistory(int user_id);

}
