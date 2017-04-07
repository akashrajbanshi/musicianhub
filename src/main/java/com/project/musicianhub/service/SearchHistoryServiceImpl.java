package com.project.musicianhub.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.dao.SearchHistroyDaoImpl;
import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.SearchHistory;

/**
 * Service implementation for the Search History
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class SearchHistoryServiceImpl implements SearchHistoryService {

	SearchHistroyDaoImpl searchHistroyDaoImpl = new SearchHistroyDaoImpl();

	/**
	 * Creates the Search History for the user
	 * 
	 * @param searchHistory
	 *            searchHistory object
	 */
	@Override
	public void addSearchHistory(SearchHistory searchHistory) {
		// gets the search history list from the user id
		List<SearchHistory> searchHistories = searchHistroyDaoImpl
				.getSearchHistoryByUser(searchHistory.getUser().getId());

		int searchrecord = 0;
		for (SearchHistory searchHistoryObj : searchHistories) {
			// check if the search history already exist in the database
			if (searchHistoryObj.getMusic().getId() == searchHistory.getMusic().getId()
					&& searchHistoryObj.getUser().getId() == searchHistory.getUser().getId()) {
				// if exist increment the search record
				searchrecord++;
			}
		}

		// if search record is not present in the database
		if (searchrecord == 0) {
			// create the search history
			searchHistroyDaoImpl.addSearchHistory(searchHistory);
		}

	}

	/**
	 * Gets all the search history by user's id
	 * 
	 * @param user_id
	 *            user id
	 * @param request
	 *            http servlet request
	 * @return music list
	 */
	@Override
	public List<Music> getSearchHistoryByUser(int user_id, HttpServletRequest request) {
		// get all the search history for the user
		List<SearchHistory> searchHistory = searchHistroyDaoImpl.getSearchHistoryByUser(user_id);

		List<Music> musics = new ArrayList<>();
		// get the server path
		String serverPath = request.getServerName().concat(":") + request.getServerPort();
		for (SearchHistory searchHistoryObj : searchHistory) {
			musics.add(searchHistoryObj.getMusic());
		}

		List<Music> finalMusicList = new ArrayList<>();
		for (Music music : musics) {
			// set the image path
			String imagePath = serverPath.concat(File.separator).concat(music.getAlbum_art_path());
			music.setAlbum_art_path(imagePath);

			// if the music is not deleted
			if (!music.isDeleted())
				// only then add the music object to the list
				finalMusicList.add(music);
		}
		return finalMusicList;
	}

	/**
	 * Clears the search history according to the user
	 * 
	 * @param user_id
	 *            user id
	 */
	@Override
	public void clearSearchHistory(int user_id) {
		searchHistroyDaoImpl.clearSearchHistory(user_id);

	}

}
