package com.project.musicianhub.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.dao.FollowDaoImpl;
import com.project.musicianhub.model.Follow;

/**
 * Service implementation for the Follow
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class FollowServiceImpl implements FollowService {

	FollowDaoImpl followDaoImpl = new FollowDaoImpl();

	/**
	 * Creates the follow for the user
	 * 
	 * @param follow
	 */
	@Override
	public void addFollow(Follow follow) {
		// gets the follow object by following and followers id
		Follow followObj = followDaoImpl.getFollowByFollowingAndFollowerId(follow);
		// if the object is null
		if (followObj == null) {
			// sets the following to be true
			follow.setFollow_status(true);
			// then create a new database entry for the following
			followDaoImpl.addFollow(follow);

		} else {
			// if the user is already followed
			// check the following status
			if (followObj.isFollow_status()) {
				// if true, set the following status to false
				followObj.setFollow_status(false);
				// then, update the follow for the same object
				followDaoImpl.updateFollow(followObj);
			} else {
				// if false, set the following to be true
				followObj.setFollow_status(true);
				// then update the follow table
				followDaoImpl.updateFollow(followObj);
			}
		}

	}

	/**
	 * Gets the follow object by following user's id and follower's user id
	 * 
	 * @param follow
	 * @return follow object
	 */
	@Override
	public Follow getFollowByFollowingAndFollowerId(Follow follow) {
		return followDaoImpl.getFollowByFollowingAndFollowerId(follow);
	}

	/**
	 * Gets the follower user by user's id
	 * 
	 * @param user_id
	 * @param request
	 * @return followings list
	 */
	@Override
	public List<Follow> getFollowersByUser(int user_id, HttpServletRequest request) {
		// gets all the followers from the current logged in user id
		List<Follow> followers = followDaoImpl.getFollowersByUser(user_id);
		// sets the current server path
		String serverPath = request.getServerName().concat(":") + request.getServerPort();
		List<Follow> followerList = new ArrayList<Follow>();
		for (Follow follow : followers) {
			// sets the image for all the current followers for the user
			follow.getFollowerUser().setCommentUserImagePath(serverPath);
			followerList.add(follow);
		}
		return followerList;
	}

	/**
	 * Gets the following user by user's id
	 * 
	 * @param user_id
	 * @param request
	 * @return followings list
	 */
	@Override
	public List<Follow> getFollowingByUser(int user_id, HttpServletRequest request) {
		// gets all the following user list for the current user
		List<Follow> followings = followDaoImpl.getFollowingByUser(user_id);
		// gets the current server path
		String serverPath = request.getServerName().concat(":") + request.getServerPort();
		List<Follow> followingList = new ArrayList<Follow>();
		for (Follow follow : followings) {
			// sets the image path for all the following user
			follow.getFollowingUser().setCommentUserImagePath(serverPath);
			followingList.add(follow);
		}
		return followingList;
	}

}
