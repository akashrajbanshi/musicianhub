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
 * Model class for Like
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
@Entity
@Table(name = "post_like")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "music_id")
	private Music music = new Music();

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user = new User();

	@Column
	private boolean liked;

	public Like() {
		super();
	}

	public Like(int id, Music music, User user, boolean liked) {
		super();
		this.id = id;
		this.music = music;
		this.user = user;
		this.liked = liked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
