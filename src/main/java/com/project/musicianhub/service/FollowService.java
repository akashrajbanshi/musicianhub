package com.project.musicianhub.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.model.Follow;

public interface FollowService {

	public void addFollow(Follow follow);

	public List<Follow> getFollowersByUser(int user_id, HttpServletRequest request);

	public List<Follow> getFollowingByUser(int user_id, HttpServletRequest request);

	Follow getFollowByFollowingAndFollowerId(Follow follow);

}
