package com.project.musicianhub.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Notification;
import com.project.musicianhub.model.NotificationResponse;
import com.project.musicianhub.model.NotificationToken;
import com.project.musicianhub.model.User;

public class NotificationServiceImplTest {

	NotificationServiceImpl notificationServiceImpl;

	@Test
	public void testAddNotification() {
		notificationServiceImpl = new NotificationServiceImpl();
		Notification notification1 = new Notification(1, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(1);
		notification1.getToUser().setId(2);
		notification1.getMusic().setId(1);
		NotificationToken notificationToken = new NotificationToken(1, notification1.getFromUser(), "token");
		notificationServiceImpl.addUserNotificationToken(notificationToken);
	}

	@Test
	public void testAddNotificationFollow() {
		notificationServiceImpl = new NotificationServiceImpl();
		Notification notification1 = new Notification(1, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(1);
		notification1.getToUser().setId(2);
		notification1.getMusic().setId(1);
		notificationServiceImpl.addNotificationFollow(notification1, notification1.getFromUser());
	}

	@Test
	public void testGetAllNotificationForUser() {
		notificationServiceImpl = new NotificationServiceImpl();
		NotificationResponse notificationResponse = notificationServiceImpl.getAllNotificationForUser(1);
		List<Notification> notifications = notificationResponse.getNotifications();
		Notification notification = notifications.get(0);
		assertEquals(1, notification.getId());
		assertEquals("comment", notification.getType());
		assertEquals(2, notification.getFromUser().getId());
		assertEquals(1, notification.getToUser().getId());
		assertEquals(1, notification.getMusic().getId());
	}

	@Test
	public void testAddUserNotificationToken() {
		notificationServiceImpl = new NotificationServiceImpl();
		Notification notification1 = new Notification(1, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(1);
		notification1.getToUser().setId(2);
		notification1.getMusic().setId(1);
		NotificationToken notificationToken = new NotificationToken(1, notification1.getFromUser(), "token");
		notificationServiceImpl.addUserNotificationToken(notificationToken);
	}

	@Test
	public void testSendNotification() throws IOException {
		notificationServiceImpl = new NotificationServiceImpl();
		Notification notification1 = new Notification(1, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(1);
		notification1.getToUser().setId(2);
		notification1.getMusic().setId(1);
		notificationServiceImpl.sendNotification(notification1, null);
	}

}
