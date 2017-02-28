package com.project.musicianhub.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.dao.SearchHistroyDaoImpl;
import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.SearchHistory;

public class SearchHistoryServiceImpl implements SearchHistoryService {

	SearchHistroyDaoImpl searchHistroyDaoImpl = new SearchHistroyDaoImpl();

	@Override
	public void addSearchHistory(SearchHistory searchHistory) {

		List<SearchHistory> searchHistories = searchHistroyDaoImpl
				.getSearchHistoryByUser(searchHistory.getUser().getId());

		int searchrecord = 0;
		for (SearchHistory searchHistoryObj : searchHistories) {
			if (searchHistoryObj.getMusic().getId() == searchHistory.getMusic().getId()
					&& searchHistoryObj.getUser().getId() == searchHistory.getUser().getId()) {
				searchrecord++;
			}
		}

		if (searchrecord == 0) {
			searchHistroyDaoImpl.addSearchHistory(searchHistory);
		}

	}

	@Override
	public List<Music> getSearchHistoryByUser(int user_id, HttpServletRequest request) {
		List<SearchHistory> searchHistory = searchHistroyDaoImpl.getSearchHistoryByUser(user_id);

		List<Music> musics = new ArrayList<>();
		String serverPath = request.getServerName().concat(":") + request.getServerPort();
		for (SearchHistory searchHistoryObj : searchHistory) {
			musics.add(searchHistoryObj.getMusic());
		}

		List<Music> finalMusicList = new ArrayList<>();
		for (Music music : musics) {
			String imagePath = serverPath.concat(File.separator).concat(music.getAlbum_art_path());

			music.setAlbum_art_path(imagePath);

			if (!music.isDeleted())
				finalMusicList.add(music);
		}
		return finalMusicList;
	}

	@Override
	public void clearSearchHistory(int user_id) {
		searchHistroyDaoImpl.clearSearchHistory(user_id);

	}

}
