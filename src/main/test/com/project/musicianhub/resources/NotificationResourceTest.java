package com.project.musicianhub.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Notification;
import com.project.musicianhub.model.NotificationResponse;
import com.project.musicianhub.model.NotificationToken;
import com.project.musicianhub.model.User;

public class NotificationResourceTest {

	private static NotificationResource mockedNotificationResource;

	private static Notification notification1;
	private static Notification notification2;
	private static NotificationResponse response;
	private static NotificationToken notificationToken;

	@BeforeClass
	public static void setUp() {
		mockedNotificationResource = mock(NotificationResource.class);
		notification1 = new Notification(1, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(1);
		notification1.getToUser().setId(2);
		notification1.getMusic().setId(1);
		notification1 = new Notification(2, "comment", new User(), new User(), new Music(), true);
		notification1.getFromUser().setId(2);
		notification1.getToUser().setId(1);
		notification1.getMusic().setId(1);
		response = new NotificationResponse();
		response.setNotifications(Arrays.asList(notification1, notification2));

		notificationToken = new NotificationToken(1, notification1.getFromUser(), "token");

		when(mockedNotificationResource.getAllNotification(notification1.getToUser().getId())).thenReturn(response);

	}

	@Test
	public void testAddUserNotificationToken() {
		mockedNotificationResource.addUserNotificationToken(1, notificationToken);
	}

	@Test
	public void testSendNotification() {
		try {
			mockedNotificationResource.sendNotification(1, notification1.getToUser().getId(), notification1, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllNotification() {
		NotificationResponse notificationResponse = mockedNotificationResource.getAllNotification(1);
		assertEquals(response, notificationResponse);
	}

}
