package com.project.musicianhub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Model class for Follow
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
@Entity
@Table(name = "follow")
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "following_user_id")
	User followingUser = new User();

	@ManyToOne
	@JoinColumn(name = "follower_user_id")
	User followerUser = new User();

	@Column
	private boolean follow_status;
	
	

	public Follow() {
		super();
	}

	public Follow(int id, User followingUser, User followerUser, boolean follow_status) {
		super();
		this.id = id;
		this.followingUser = followingUser;
		this.followerUser = followerUser;
		this.follow_status = follow_status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isFollow_status() {
		return follow_status;
	}

	public void setFollow_status(boolean follow_status) {
		this.follow_status = follow_status;
	}

	public User getFollowingUser() {
		return followingUser;
	}

	public void setFollowingUser(User followingUser) {
		this.followingUser = followingUser;
	}

	public User getFollowerUser() {
		return followerUser;
	}

	public void setFollowerUser(User followerUser) {
		this.followerUser = followerUser;
	}

}
