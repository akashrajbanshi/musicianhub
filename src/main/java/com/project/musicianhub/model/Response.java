package com.project.musicianhub.model;

import java.util.List;

/**
 * Model class for Response
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class Response {
	private int id;
	private String code;
	private String message;
	private Music music;
	private User user;
	private List<Like> likes;
	private String userImagePath;

	public Response() {
		super();
	}

	public Response(int id, String code, String message) {
		super();
		this.id = id;
		this.code = code;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserImagePath() {
		return userImagePath;
	}

	public void setUserImagePath(String userImagePath) {
		this.userImagePath = userImagePath;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

}
