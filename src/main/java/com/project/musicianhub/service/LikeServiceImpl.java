package com.project.musicianhub.service;

import java.util.List;

import com.project.musicianhub.dao.LikeDaoImpl;
import com.project.musicianhub.model.Like;
import com.project.musicianhub.model.Response;

/**
 * Service implementation for the Like
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class LikeServiceImpl implements LikeService {

	LikeDaoImpl likeDaoImpl = new LikeDaoImpl();

	/**
	 * Creates the like for the music post
	 * 
	 * @param like
	 *            like object
	 * @return custom response
	 */
	@Override
	public Response addLike(Like like) {
		Response response = new Response();
		// gets all the like by music id and user id
		Like likeobj = likeDaoImpl.getLikebyMusicAndUser(like.getMusic().getId(), like.getUser().getId());
		// if music like is present for current criteria
		if (likeobj != null) {
			// set if the current music is liked or not
			likeobj.setLiked(like.isLiked());
			// then, update the like for the current music
			likeDaoImpl.updateLike(likeobj.getMusic().getId(), likeobj);
			response.setMessage("Like Updated!");
		} else {
			// create a new like if the like has not been created by the same
			// user before
			likeDaoImpl.addLike(like);
			response.setMessage("Like Added!");
		}
		// gets all the like for the current music
		List<Like> likeList = likeDaoImpl.getLikebyMusic(like.getMusic().getId());
		// return likes and response
		response.setLikes(likeList);
		return response;
	}

	/**
	 * Gets all the likes using the music id
	 * 
	 * @param music_id
	 * @return
	 */
	@Override
	public List<Like> getLikeByMusic(int music_id) {
		return likeDaoImpl.getLikebyMusic(music_id);
	}

	/**
	 * Updates the like using the music id and like object
	 * 
	 * @param id
	 *            like id
	 * @param like
	 *            like object
	 * @return
	 */
	@Override
	public int updateLike(int id, Like like) {
		return likeDaoImpl.updateLike(id, like);

	}

	/**
	 * Get like by the user and music id
	 * 
	 * @param music_id
	 *            music id
	 * @param user_id
	 *            user id
	 * @return like object
	 */
	@Override
	public Like getLikeByUser(int music_id, int user_id) {
		return likeDaoImpl.getLikebyMusicAndUser(music_id, user_id);
	}

}
