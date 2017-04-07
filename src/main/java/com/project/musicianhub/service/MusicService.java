package com.project.musicianhub.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.media.multipart.MultiPart;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Response;

/**
 * Service interface for the Music
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface MusicService {

	/**
	 * Creates the music
	 * 
	 * @param music
	 * @return custom response
	 */
	public Response addMusic(Music music);

	/**
	 * Gets all the music available
	 * 
	 * @return
	 */
	public List<Music> getMusic();

	/**
	 * Gets the music according to the user's id
	 * 
	 * @param user_id
	 * @param request
	 * @param firstResult
	 *            start fetching data from this point
	 * @param isMaximumResultSet
	 *            check for setting the maximum result
	 * @return music list
	 */
	public List<Music> getMusicbyUser(int user_id, HttpServletRequest request, int firstResult,
			boolean isMaximumResultSet);

	/**
	 * Deletes the music by music's id
	 * 
	 * @param id
	 *            music id
	 * @return custom response
	 */
	public Response deleteMusic(int id);

	/**
	 * Updates the music by music object
	 * 
	 * @param music
	 * @return custom response
	 */
	public Response updateMusic(Music music);

	/**
	 * Get Music by music id
	 * 
	 * @param music_id
	 * @param request
	 *            http request
	 * @return music object
	 */
	public Music getMusicbyId(int music_id, HttpServletRequest request);

	/**
	 * Uploads the album art image for the music
	 * 
	 * @param multiPart
	 *            image's part from the client
	 * @param context
	 *            servlet context
	 * @return upload image path
	 */
	public String uploadAlbumImage(MultiPart multiPart, ServletContext context);

	/**
	 * Uploads the music to the server
	 * 
	 * @param multiPart
	 *            music part from the client
	 * @param context
	 *            servlet context
	 * @return upload music path
	 */
	public String uploadUserMusic(MultiPart multiPart, ServletContext context);

	/**
	 * Gets the music list according to the search text from the client
	 * 
	 * @param searchText
	 *            user's text input from the client
	 * @param request
	 *            http request
	 * @return music list
	 */
	public List<Music> searchMusic(String searchText, HttpServletRequest request);

	/**
	 * Gets the music according to all the followed user of the current user
	 * 
	 * @param user_id
	 *            current user's id
	 * @param request
	 *            http request
	 * @param firstResult
	 *            start fetching data from this point
	 * @param music
	 *            music object
	 * @return music object
	 */
	public Music getMusicByFollowingUser(int user_id, HttpServletRequest request, int firstResult, Music music);

}
