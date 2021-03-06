package com.project.musicianhub.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Model class for Search History
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
@Entity
@Table(name = "search_history")
public class SearchHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "music_id")
	private Music music = new Music();

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user = new User();

	public SearchHistory() {
		super();
	}

	public SearchHistory(int id, Music music, User user) {
		super();
		this.id = id;
		this.music = music;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
