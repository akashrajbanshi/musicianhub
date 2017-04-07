package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.Like;


/**
 * Dao interface for Like
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface LikeDao {

	/**
	 * Adds the like to the database
	 * 
	 * @param like
	 */
	public void addLike(Like like);

	/**
	 * Adds the like to the database
	 * 
	 * @param session
	 * @param like
	 */

	public void addLike(Session session, Like like);

	/**
	 * Gets all the likes available for the music
	 * 
	 * @param music_id
	 * @return music's like list
	 */

	public List<Like> getLikebyMusic(int music_id);

	/**
	 * Updates the existing like from the database of a particular like object
	 * by id
	 * 
	 * @param id
	 * @param like
	 * @return
	 */
	public int updateLike(int id, Like like);

}
