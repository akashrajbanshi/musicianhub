package com.project.musicianhub.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.model.Follow;

/**
 * Service interface for the Follow
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface FollowService {

	/**
	 * Creates the follow for the user
	 * 
	 * @param follow
	 */
	public void addFollow(Follow follow);

	/**
	 * Gets the followers by user's id
	 * 
	 * @param user_id
	 * @param request
	 * @return followers list
	 */
	public List<Follow> getFollowersByUser(int user_id, HttpServletRequest request);

	/**
	 * Gets the following user by user's id
	 * 
	 * @param user_id
	 * @param request
	 * @return followings list
	 */
	public List<Follow> getFollowingByUser(int user_id, HttpServletRequest request);

	/**
	 * Gets the follow object by following user's id and follower's user id
	 * 
	 * @param follow
	 * @return follow object
	 */
	public Follow getFollowByFollowingAndFollowerId(Follow follow);

}
