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
 * Model class for Comment
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
@Entity
@Table(name = "comment")
public class Comment {

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
	private String comment;

	public Comment() {
		super();
	}

	public Comment(int id, Music music, User user, String comment) {
		super();
		this.id = id;
		this.music = music;
		this.user = user;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
