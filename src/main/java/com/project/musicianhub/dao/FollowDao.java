package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.Follow;



/**
 * Dao interface for follow
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface FollowDao {

	/**
	 * Adds follow to the database
	 * 
	 * @param follow
	 */
	public void addFollow(Follow follow);

	/**
	 * Adds follow to the database
	 * 
	 * @param session
	 * @param follow
	 */
	public void addFollow(Session session, Follow follow);

	/**
	 * Gets follower list by user id
	 * 
	 * @param user_id
	 * @return followers list
	 */
	public List<Follow> getFollowersByUser(int user_id);

	/**
	 * Gets following list by user id
	 * 
	 * @param user_id
	 * @return following list
	 */
	public List<Follow> getFollowingByUser(int user_id);

	/**
	 * Gets the follow data by the follower and following user id
	 * 
	 * @param follow
	 * @return follow object
	 */
	public Follow getFollowByFollowingAndFollowerId(Follow follow);

	/**
	 * Updates the follow
	 * 
	 * @param follow
	 */
	public void updateFollow(Follow follow);

}
