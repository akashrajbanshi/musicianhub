package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.Follow;

public interface FollowDao {

	public void addFollow(Follow follow);

	public void addFollow(Session session, Follow follow);

	public List<Follow> getFollowersByUser(int user_id);

	public List<Follow> getFollowingByUser(int user_id);

}
