package com.project.musicianhub.model;

import java.util.List;

/**
 * Model class for Notification Response
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class NotificationResponse {

	List<Notification> notifications;
	List<NotificationFollow> notificationFollows;

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<NotificationFollow> getNotificationFollows() {
		return notificationFollows;
	}

	public void setNotificationFollows(List<NotificationFollow> notificationFollows) {
		this.notificationFollows = notificationFollows;
	}

}
