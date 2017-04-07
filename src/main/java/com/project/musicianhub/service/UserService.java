package com.project.musicianhub.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.glassfish.jersey.media.multipart.MultiPart;

import com.project.musicianhub.model.Response;
import com.project.musicianhub.model.User;

/**
 * Service interface for the User
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface UserService {
	/**
	 * Create user
	 * 
	 * @param user
	 *            user object
	 * @return custom response
	 */
	public Response addUser(User user);

	/**
	 * Gets all the users data
	 * 
	 * @return user list
	 */
	public List<User> getUsers();

	/**
	 * Delete all the users
	 * 
	 * @param id
	 *            user id
	 * @return delete success/failure as int
	 */
	public int deleteUsers(int id);

	/**
	 * Updates the user information
	 * 
	 * @param user
	 *            user object
	 * @param request
	 *            http servlet request
	 * @return custom response
	 */
	public Response updateUsers(User user, HttpServletRequest request);

	/**
	 * Get user object from user id
	 * 
	 * @param user_id
	 *            user id
	 * @return user object
	 */
	public User getUserById(int user_id);

	/**
	 * Uploads the user image
	 * 
	 * @param multiPart
	 *            image part
	 * @param context
	 *            servlet context
	 * @return user image path
	 */
	public String uploadUserImage(MultiPart multiPart, ServletContext context);

	/**
	 * Gets the last inserted user id
	 * 
	 * @return user id
	 */
	public int getLastInsertedUserId();

	/**
	 * Gets the user credentials
	 * 
	 * @param user
	 *            user object
	 * @param request
	 *            http servlet request
	 * @return custom response
	 */
	public Response getUserCredentials(User user, HttpServletRequest request);
}
