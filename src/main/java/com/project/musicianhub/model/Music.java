package com.project.musicianhub.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Model class for Music
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
@Entity
@Table(name = "music")
public class Music {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String title;
	@Column
	private String genre;
	@Column
	private Date published_date;
	@Column
	private String album_art_path;
	@Column
	private String music_path;
	@Column
	private boolean deleted;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user = new User();

	@Transient
	List<Like> likes = new ArrayList<>();
	@Transient
	private List<Comment> comments = new ArrayList<>();
	@Transient
	private List<Music> musics = new ArrayList<>();

	public Music() {
		super();

	}

	

	public Music(int id, String title, String genre, Date published_date, String album_art_path, String music_path,
			boolean deleted) {
		super();
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.published_date = published_date;
		this.album_art_path = album_art_path;
		this.music_path = music_path;
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Date getPublished_date() {
		return published_date;
	}

	public void setPublished_date(Date published_date) {
		this.published_date = published_date;
	}

	public String getAlbum_art_path() {
		return album_art_path;
	}

	public void setAlbum_art_path(String album_art_path) {
		this.album_art_path = album_art_path;
	}

	public String getMusic_path() {
		return music_path;
	}

	public void setMusic_path(String music_path) {
		this.music_path = music_path;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Music> getMusics() {
		return musics;
	}

	public void setMusics(List<Music> musics) {
		this.musics = musics;
	}

}
