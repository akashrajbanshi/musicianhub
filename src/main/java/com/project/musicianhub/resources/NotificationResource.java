package com.project.musicianhub.resources;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import com.project.musicianhub.model.Notification;
import com.project.musicianhub.model.NotificationResponse;
import com.project.musicianhub.model.NotificationToken;
import com.project.musicianhub.service.NotificationServiceImpl;

/**
 * Resource class for notification
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */

@Path("/")
public class NotificationResource {

	NotificationServiceImpl notificationServiceImpl = new NotificationServiceImpl();

	/**
	 * Creates the notification token for the user
	 * 
	 * @param user_id
	 *            user id
	 * @param notificationToken
	 *            notificationToken object
	 */
	@POST
	@Path("/registerUserToken")
	public void addUserNotificationToken(@PathParam("user_id") int user_id, NotificationToken notificationToken) {

		notificationToken.getUser().setId(user_id);

		notificationServiceImpl.addUserNotificationToken(notificationToken);

	}

	/**
	 * Sends the notification to the user based on the sending and receiving
	 * user's id
	 * 
	 * @param user_id
	 *            sending user id
	 * @param to_user_id
	 *            receiving user id
	 * @param notification
	 *            notification object
	 * @param request
	 *            http request
	 * @throws IOException
	 *             input out exception for sending notification
	 */
	@POST
	@Path("sendNotification/{to_user_id}")
	public void sendNotification(@PathParam("user_id") int user_id, @PathParam("to_user_id") int to_user_id,
			Notification notification, @Context HttpServletRequest request) throws IOException {
		notification.getToUser().setId(to_user_id);
		notification.getFromUser().setId(user_id);

		notificationServiceImpl.sendNotification(notification, request);

	}

	/**
	 * Gets all the notification for the user by its id
	 * 
	 * @param user_id
	 * @return notification response
	 */
	@GET
	@Path("getAllNotification")
	public NotificationResponse getAllNotification(@PathParam("user_id") int user_id) {
		return notificationServiceImpl.getAllNotificationForUser(user_id);
	}

}
