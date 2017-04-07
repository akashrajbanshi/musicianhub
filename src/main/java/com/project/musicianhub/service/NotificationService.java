package com.project.musicianhub.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.project.musicianhub.model.Notification;
import com.project.musicianhub.model.NotificationResponse;
import com.project.musicianhub.model.NotificationToken;
import com.project.musicianhub.model.User;

/**
 * Service interface for the Notification
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public interface NotificationService {

	/**
	 * Creates the notification for likes and comment from the notification
	 * object and notification sender user object
	 * 
	 * @param notification
	 *            notification object
	 * @param notificationSenderUser
	 *            user object
	 */
	public void addNotification(Notification notification, User notificationSenderUser);

	/**
	 * Creates the notification for follow from notification and notification
	 * sender user object
	 * 
	 * @param notification
	 *            notification object
	 * @param notificationSenderUser
	 *            user object
	 */
	public void addNotificationFollow(Notification notification, User notificationSenderUser);

	/**
	 * Gets all the notification from the user id
	 * 
	 * @param id
	 *            user's id
	 * @return notification response
	 */
	public NotificationResponse getAllNotificationForUser(int id);

	/**
	 * Creates the user notification token for the current user
	 * 
	 * @param notificationToken
	 *            notificationToken object
	 */
	public void addUserNotificationToken(NotificationToken notificationToken);

	/**
	 * sends notification to the user
	 * 
	 * @param notification
	 *            notification object
	 * @param request
	 *            http servlet request
	 * @throws IOException
	 *             input output exception
	 */
	public void sendNotification(Notification notification, HttpServletRequest request) throws IOException;

}
