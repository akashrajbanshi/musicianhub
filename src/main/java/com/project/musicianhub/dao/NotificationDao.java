package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.Notification;
import com.project.musicianhub.model.NotificationFollow;
import com.project.musicianhub.model.NotificationToken;

/**
 * Dao interface for Notification
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface NotificationDao {

	/**
	 * Adds the unique notification token to the database
	 * 
	 * @param notificationToken
	 *            notificationToken object
	 */
	public void addUserNotificationToken(NotificationToken notificationToken);

	/**
	 * Adds the unique notification token to the database
	 * 
	 * @param session
	 *            session object
	 * @param notificationToken
	 *            notificationToken object
	 */
	public void addUserNotificationToken(Session session, NotificationToken notificationToken);

	/**
	 * Adds notification to the database
	 * 
	 * @param notification
	 *            notification object
	 */
	public void addNotification(Notification notification);

	/**
	 * Adds notification to the database
	 * 
	 * @param session
	 *            session object
	 * @param notification
	 *            notification object
	 */
	public void addNotification(Session session, Notification notification);

	/**
	 * Adds follow notification to the database
	 * 
	 * @param notificationFollow
	 *            notificationFollow object
	 */
	public void addNotificationFollow(NotificationFollow notificationFollow);

	/**
	 * Adds follow notification to the database
	 * 
	 * @param session
	 *            session object
	 * @param notificationFollow
	 *            notificationFollow object
	 */
	public void addNotificationFollow(Session session, NotificationFollow notificationFollow);

	/**
	 * Gets the notification token by the user id
	 * 
	 * @param id
	 *            user's id
	 * @return notification token
	 */
	public NotificationToken getTokenByUserId(int id);

	/**
	 * Updates the unique notification token
	 * 
	 * @param notificationToken
	 *            notificationToken object
	 */
	public void updateUserNotificationToken(NotificationToken notificationToken);

	/**
	 * Gets all the notification by the user id
	 * 
	 * @param id
	 *            user's id
	 * @return notification list
	 */
	public List<Notification> getAllNotification(int id);

	/**
	 * Gets all the follow notification by the user id
	 * 
	 * @param id
	 *            user's id
	 * @return follow notification list
	 */
	public List<NotificationFollow> getAllNotificationFollow(int id);

	/**
	 * Gets the notification from the database using from and to user's id
	 * 
	 * @param musicId
	 *            music's id
	 * @param fromId
	 *            notification from user's id
	 * @param toId
	 *            notification to user's id
	 * @param type
	 *            type of the notification
	 * @return notification object
	 */
	public Notification getAllNotificationByFromAndToUser(int musicId, int fromId, int toId, String type);

	/**
	 * Gets the notification from the database using from and to user's id
	 * 
	 * @param musicId
	 *            music's id
	 * @param fromId
	 *            notification from user's id
	 * @param toId
	 *            notification to user's id
	 * @param type
	 *            type of the notification
	 * @return notification object
	 */
	public NotificationFollow getAllNotificationByFromAndToUser(int fromId, int toId);

}
