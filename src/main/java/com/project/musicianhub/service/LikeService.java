package com.project.musicianhub.service;

import java.util.List;

import com.project.musicianhub.model.Like;
import com.project.musicianhub.model.Response;

public interface LikeService {

	public Response addLike(Like like);

	public List<Like> getLikeByMusic(int music_id);

	public int updateLike(int id, Like like);

	public Like getLikeByUser(int music_id, int user_id);

}
