package com.project.musicianhub.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String name;
	@Column
	private String username;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private String user_img_path;
	@Column
	private String gender;
	@Column
	private long phone_no;

	@Transient
	private List<Link> links = new ArrayList<>();

	@Transient
	private String commentUserImagePath;

	public User() {
		super();
	}

	public User(int id, String name, String username, String email, String password, String user_img_path,
			String gender, long phone_no, List<Link> links, String commentUserImagePath) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.user_img_path = user_img_path;
		this.gender = gender;
		this.phone_no = phone_no;
		this.links = links;
		this.commentUserImagePath = commentUserImagePath;
	}

	public User(int id, String name, String username, String email, String password, String user_img_path,
			String gender, long phone_no) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.user_img_path = user_img_path;
		this.gender = gender;
		this.phone_no = phone_no;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser_img_path() {
		return user_img_path;
	}

	public void setUser_img_path(String user_img_path) {
		this.user_img_path = user_img_path;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(long phone_no) {
		this.phone_no = phone_no;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public String getCommentUserImagePath() {
		return commentUserImagePath;
	}

	public void setCommentUserImagePath(String commentUserImagePath) {
		this.commentUserImagePath = commentUserImagePath;
	}

	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}

}
