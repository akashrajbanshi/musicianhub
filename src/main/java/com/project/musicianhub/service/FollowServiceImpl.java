package com.project.musicianhub.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.dao.FollowDaoImpl;
import com.project.musicianhub.model.Follow;

public class FollowServiceImpl implements FollowService {

	FollowDaoImpl followDaoImpl = new FollowDaoImpl();

	@Override
	public void addFollow(Follow follow) {

		Follow followObj = followDaoImpl.getFollowByFollowingAndFollowerId(follow);
		if (followObj == null) {
			follow.setFollow_status(true);
			followDaoImpl.addFollow(follow);
		} else {
			if (followObj.isFollow_status()) {
				followObj.setFollow_status(false);
				followDaoImpl.updateFollow(followObj);
			} else {
				followObj.setFollow_status(true);
				followDaoImpl.updateFollow(followObj);
			}
		}

	}

	@Override
	public Follow getFollowByFollowingAndFollowerId(Follow follow) {
		return followDaoImpl.getFollowByFollowingAndFollowerId(follow);
	}

	@Override
	public List<Follow> getFollowersByUser(int user_id, HttpServletRequest request) {
		List<Follow> followers = followDaoImpl.getFollowersByUser(user_id);
		String serverPath = request.getServerName().concat(":") + request.getServerPort();
		List<Follow> followerList = new ArrayList<Follow>();
		for (Follow follow : followers) {
			follow.getFollowerUser().setCommentUserImagePath(serverPath);
			followerList.add(follow);
		}
		return followerList;
	}

	@Override
	public List<Follow> getFollowingByUser(int user_id, HttpServletRequest request) {
		List<Follow> followings = followDaoImpl.getFollowingByUser(user_id);
		String serverPath = request.getServerName().concat(":") + request.getServerPort();
		List<Follow> followingList = new ArrayList<Follow>();
		for (Follow follow : followings) {
			follow.getFollowingUser().setCommentUserImagePath(serverPath);
			followingList.add(follow);
		}
		return followingList;
	}

}
