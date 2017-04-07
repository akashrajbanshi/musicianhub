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
	 *            user object
	 * @return user object
	 */
	public User addUser(User user);

	/**
	 * Adds user to the database
	 * 
	 * @param session
	 *            session object
	 * @param user
	 *            user object
	 */
	public void addUser(Session session, User user);

	/**
	 * Gets all the user
	 * 
	 * @return user list
	 */
	public List<User> getUsers();

	/**
	 * Deletes the user by id
	 * 
	 * @param id
	 *            user id
	 * @return delete success/failure as int
	 */
	public int deleteUsers(int id);

	/**
	 * Updates the user
	 * 
	 * @param user
	 *            user object
	 * @return update success/failure as int
	 */
	public int updateUsers(User user);

	/**
	 * Gets the User info by its id
	 * 
	 * @param user_id
	 *            user id
	 * @return user object
	 */
	public User getUserById(int user_id);

	/**
	 * Gets the last inserted user id
	 * 
	 * @return user id
	 */
	public int getLastInsertedUserId();

	/**
	 * Gets user by its username
	 * 
	 * @param user
	 *            user object
	 * @return user object
	 */
	public User getUserByUserName(User user);

	/**
	 * Checks if table is empty
	 * 
	 * @return true: if table is empty else false
	 */
	boolean checkIfTableEmpty();

}
