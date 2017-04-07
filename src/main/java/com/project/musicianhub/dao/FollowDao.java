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
	 *            follow object
	 */
	public void addFollow(Follow follow);

	/**
	 * Adds follow to the database
	 * 
	 * @param session
	 *            session object
	 * @param follow
	 *            follow object
	 */
	public void addFollow(Session session, Follow follow);

	/**
	 * Gets follower list by user id
	 * 
	 * @param user_id
	 *            user id
	 * @return followers list
	 */
	public List<Follow> getFollowersByUser(int user_id);

	/**
	 * Gets following list by user id
	 * 
	 * @param user_id
	 *            user id
	 * @return following list
	 */
	public List<Follow> getFollowingByUser(int user_id);

	/**
	 * Gets the follow data by the follower and following user id
	 * 
	 * @param follow
	 *            follow object
	 * @return follow follow object
	 */
	public Follow getFollowByFollowingAndFollowerId(Follow follow);

	/**
	 * Updates the follow
	 * 
	 * @param follow
	 *            follow object
	 */
	public void updateFollow(Follow follow);

}
