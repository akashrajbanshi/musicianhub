package com.project.musicianhub.service;

import java.util.List;

import com.project.musicianhub.dao.LikeDaoImpl;
import com.project.musicianhub.model.Like;
import com.project.musicianhub.model.Response;

public class LikeServiceImpl implements LikeService {

	LikeDaoImpl likeDaoImpl = new LikeDaoImpl();

	@Override
	public Response addLike(Like like) {
		Response response = new Response();
		Like likeobj = likeDaoImpl.getLikebyMusicAndUser(like.getMusic().getId(), like.getUser().getId());

		if (likeobj != null) {
			likeobj.setLiked(like.isLiked());
			likeDaoImpl.updateLike(likeobj.getMusic().getId(), likeobj);
			response.setMessage("Like Updated!");
		} else {
			likeDaoImpl.addLike(like);
			response.setMessage("Like Added!");
		}
		List<Like> likeList = likeDaoImpl.getLikebyMusic(like.getMusic().getId());
		response.setLikes(likeList);
		return response;
	}

	@Override
	public List<Like> getLikeByMusic(int music_id) {
		return likeDaoImpl.getLikebyMusic(music_id);
	}

	@Override
	public int updateLike(int id, Like like) {
		return likeDaoImpl.updateLike(id, like);

	}

	@Override
	public Like getLikeByUser(int music_id, int user_id) {
		return likeDaoImpl.getLikebyMusicAndUser(music_id, user_id);
	}

}
