package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.Like;

public interface LikeDao {

	public void addLike(Like like);

	public void addLike(Session session, Like like);

	public List<Like> getLikebyMusic(int music_id);

	public int updateLike(int id, Like like);

}
