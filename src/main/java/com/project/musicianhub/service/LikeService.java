package com.project.musicianhub.service;

import java.util.List;

import com.project.musicianhub.model.Like;
import com.project.musicianhub.model.Response;

/**
 * Service interface for the Like
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface LikeService {

	/**
	 * Creates the like for the music post
	 * 
	 * @param like
	 * @return custom response
	 */
	public Response addLike(Like like);

	/**
	 * Gets all the likes using the music id
	 * 
	 * @param music_id
	 * @return
	 */
	public List<Like> getLikeByMusic(int music_id);

	/**
	 * Updates the like using the music id and like object
	 * 
	 * @param id
	 * @param like
	 * @return
	 */
	public int updateLike(int id, Like like);

	/**
	 * Get like by the user and music id
	 * 
	 * @param music_id
	 * @param user_id
	 * @return like object
	 */
	public Like getLikeByUser(int music_id, int user_id);

}
