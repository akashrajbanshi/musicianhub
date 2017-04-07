package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.SearchHistory;


/**
 * Dao interface for Search History
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface SearchHistoryDao {

	/**
	 * Adds the search history
	 * 
	 * @param searchHistory
	 */
	public void addSearchHistory(SearchHistory searchHistory);

	/**
	 * Adds the search history
	 * 
	 * @param session
	 * @param searchHistory
	 */
	public void addSearchHistory(Session session, SearchHistory searchHistory);

	/**
	 * Gets the search history by the user's id
	 * 
	 * @param user_id
	 * @return search history list
	 */
	public List<SearchHistory> getSearchHistoryByUser(int user_id);

	/**
	 * Deletes the search history from the database of a particular user
	 * 
	 * @param user_id
	 */
	public void clearSearchHistory(int user_id);
}
