package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.Music;

/**
 * Dao interface for Music
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface MusicDao {

	/**
	 * Adds music to the database
	 * 
	 * @param music
	 * @return music object
	 */
	public Music addMusic(Music music);

	/**
	 * Adds music to the database
	 * 
	 * @param session
	 * @param music
	 */
	public void addMusic(Session session, Music music);

	/**
	 * Gets all the music list from the database
	 * 
	 * @return music list
	 */
	public List<Music> getMusic();

	/**
	 * Gets music by user id
	 * 
	 * @param user_id
	 *            user's id
	 * @param firstResult
	 *            the value which defines from where the data is to extracted
	 * @param isMaximumResultSet
	 *            total number of data to be loaded
	 * @return music list
	 */
	public List<Music> getMusicbyUser(int user_id, int firstResult, boolean isMaximumResultSet);

	/**
	 * Deletes the music by id
	 * 
	 * @param id
	 * @return
	 */
	public int deleteMusic(int id);

	/**
	 * Updates the music by music object
	 * 
	 * @param music
	 * @return
	 */
	public int updateMusic(Music music);

	/**
	 * Gets the music by id
	 * 
	 * @param music_id
	 * @return music object
	 */
	public Music getMusicbyId(int music_id);

	/**
	 * Gets the Id of the last inserted music Id
	 * 
	 * @return music id
	 */
	public int getLastInsertedMusicId();

	/**
	 * Searches for the music by the search text provided
	 * 
	 * @param searchText
	 * @return list of music
	 */
	public List<Music> searchMusic(String searchText);

}
