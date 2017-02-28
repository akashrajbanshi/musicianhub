package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.User;

public interface UserDao {

	public User addUser(User user);

	public void addUser(Session session, User user);

	public List<User> getUsers();

	public int deleteUsers(int id);

	public int updateUsers(User user);

	public User getUserById(int user_id);

	public int getLastInsertedUserId();

	User getUserByUserName(User user);

}
