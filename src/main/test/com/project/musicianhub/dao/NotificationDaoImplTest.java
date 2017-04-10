package com.project.musicianhub.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Notification;
import com.project.musicianhub.model.NotificationFollow;
import com.project.musicianhub.model.NotificationToken;
import com.project.musicianhub.model.User;

public class NotificationDaoImplTest {

	NotificationDaoImpl notificationDaoImpl;

	@Test
	public void testAddUserNotificationTokenNotificationToken() {
		notificationDaoImpl = new NotificationDaoImpl();
		Notification notification1 = new Notification(1, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(1);
		notification1.getToUser().setId(2);
		notification1.getMusic().setId(1);
		NotificationToken notificationToken = new NotificationToken(1, notification1.getFromUser(), "token");
		notificationDaoImpl.addUserNotificationToken(notificationToken);
	}

	@Test
	public void testAddUserNotificationTokenSessionNotificationToken() {
		notificationDaoImpl = new NotificationDaoImpl();
		Session session = SessionUtil.getSession();
		Notification notification1 = new Notification(1, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(1);
		notification1.getToUser().setId(2);
		notification1.getMusic().setId(1);
		NotificationToken notificationToken = new NotificationToken(1, notification1.getFromUser(), "token");
		notificationDaoImpl.addUserNotificationToken(session, notificationToken);
	}

	@Test
	public void testAddNotificationNotification() {
		notificationDaoImpl = new NotificationDaoImpl();
		Notification notification1 = new Notification(1, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(1);
		notification1.getToUser().setId(2);
		notification1.getMusic().setId(1);
		notificationDaoImpl.addNotification(notification1);
	}

	@Test
	public void testAddNotificationSessionNotification() {
		notificationDaoImpl = new NotificationDaoImpl();
		Session session = SessionUtil.getSession();
		Notification notification1 = new Notification(1, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(1);
		notification1.getToUser().setId(2);
		notification1.getMusic().setId(1);
		notificationDaoImpl.addNotification(session, notification1);
	}

	@Test
	public void testAddNotificationFollowNotificationFollow() {
		notificationDaoImpl = new NotificationDaoImpl();
		NotificationFollow notification1 = new NotificationFollow(1, new User(), new User(), true);
		notification1.getFromUser().setId(1);
		notification1.getToUser().setId(2);
		notificationDaoImpl.addNotificationFollow(notification1);
	}

	@Test
	public void testAddNotificationFollowSessionNotificationFollow() {
		notificationDaoImpl = new NotificationDaoImpl();
		Session session = SessionUtil.getSession();
		NotificationFollow notification1 = new NotificationFollow(1, new User(), new User(), true);
		notification1.getFromUser().setId(1);
		notification1.getToUser().setId(2);
		notificationDaoImpl.addNotificationFollow(session, notification1);
	}

	@Test
	public void testGetTokenByUserId() {
		notificationDaoImpl = new NotificationDaoImpl();
		Notification notification1 = new Notification(1, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(1);
		NotificationToken notificationToken = new NotificationToken(1, notification1.getFromUser(), "token");
		NotificationToken notificationTokenObj = notificationDaoImpl.getTokenByUserId(1);
		assertEquals(notificationToken.getId(), notificationTokenObj.getId());
	}

	@Test
	public void testUpdateUserNotificationToken() {
		notificationDaoImpl = new NotificationDaoImpl();
		Notification notification1 = new Notification(1, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(1);
		NotificationToken notificationToken = new NotificationToken(1, notification1.getFromUser(), "token");
		notificationDaoImpl.updateUserNotificationToken(notificationToken);
	}

	@Test
	public void testGetAllNotification() {
		notificationDaoImpl = new NotificationDaoImpl();
		List<Notification> notifications = notificationDaoImpl.getAllNotification(1);
		assertEquals(1, notifications.size());
	}

	@Test
	public void testGetAllNotificationFollow() {
		notificationDaoImpl = new NotificationDaoImpl();
		List<NotificationFollow> notificationFollow = notificationDaoImpl.getAllNotificationFollow(1);
		assertEquals(1, notificationFollow.size());
	}

	@Test
	public void testGetAllNotificationByFromAndToUserIntIntIntString() {
		notificationDaoImpl = new NotificationDaoImpl();
		Notification notification = notificationDaoImpl.getAllNotificationByFromAndToUser(1, 1, 2, "comment");
		assertEquals(notification.getId(), 1);
	}

	@Test
	public void testGetAllNotificationByFromAndToUserIntInt() {
		notificationDaoImpl = new NotificationDaoImpl();
		NotificationFollow notificationFollow = notificationDaoImpl.getAllNotificationByFromAndToUser(1, 2);
		assertEquals(notificationFollow.getId(), 1);
	}

}
