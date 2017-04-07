package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.User;

/**
 * Dao interface for User
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface UserDao {

	/**
	 * Adds user to the database
	 * 
	 * @param user
	 * @return
	 */
	public User addUser(User user);

	/**
	 * Adds user to the database
	 * 
	 * @param session
	 * @param user
	 */
	public void addUser(Session session, User user);

	/**
	 * Gets all the user
	 * 
	 * @return
	 */
	public List<User> getUsers();

	/**
	 * Deletes the user by id
	 * 
	 * @param id
	 * @return
	 */
	public int deleteUsers(int id);

	/**
	 * Updates the user
	 * 
	 * @param user
	 * @return
	 */
	public int updateUsers(User user);

	/**
	 * Gets the User info by its id
	 * 
	 * @param user_id
	 * @return
	 */
	public User getUserById(int user_id);

	/**
	 * Gets the last inserted user id
	 * 
	 * @return
	 */
	public int getLastInsertedUserId();

	/**
	 * Gets user by its username
	 * 
	 * @param user
	 * @return
	 */
	public User getUserByUserName(User user);

}
