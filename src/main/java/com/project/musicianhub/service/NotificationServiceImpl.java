package com.project.musicianhub.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonObject;
import com.project.musicianhub.dao.NotificationDaoImpl;
import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Notification;
import com.project.musicianhub.model.NotificationFollow;
import com.project.musicianhub.model.NotificationResponse;
import com.project.musicianhub.model.NotificationToken;
import com.project.musicianhub.model.User;

/**
 * Service implementation for the Notification
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class NotificationServiceImpl implements NotificationService {

	NotificationDaoImpl notificationDaoImpl = new NotificationDaoImpl();
	UserServiceImpl userServiceImpl = new UserServiceImpl();
	MusicServiceImpl musicServiceImpl = new MusicServiceImpl();

	/**
	 * Creates the notification for likes and comment from the notification
	 * object and notification sender user object
	 * 
	 * @param notification
	 * @param notificationSenderUser
	 */
	@Override
	public void addNotification(Notification notification, User notificationSenderUser) {
		// sets notification user id from sender user object
		notification.getFromUser().setId(notificationSenderUser.getId());
		// gets all the notification from and to users id, music id and
		// notification type
		Notification notificationObj = notificationDaoImpl.getAllNotificationByFromAndToUser(
				notification.getMusic().getId(), notificationSenderUser.getId(), notification.getToUser().getId(),
				notification.getType());
		// if notification does not exist
		if (notificationObj == null)
			// creates the notification
			notificationDaoImpl.addNotification(notification);
	}

	/**
	 * Creates the notification for follow from notification and notification
	 * sender user object
	 * 
	 * @param notification
	 * @param notificationSenderUser
	 */
	@Override
	public void addNotificationFollow(Notification notification, User notificationSenderUser) {
		NotificationFollow notificationFollow = new NotificationFollow();
		// sets the from user id
		notificationFollow.getFromUser().setId(notificationSenderUser.getId());
		// sets the to user id
		notificationFollow.getToUser().setId(notification.getToUser().getId());

		// gets the notification according to from and to user id
		NotificationFollow notificationFollowObj = notificationDaoImpl
				.getAllNotificationByFromAndToUser(notificationSenderUser.getId(), notification.getToUser().getId());

		// if follow notification does not exists
		if (notificationFollowObj == null)
			// creates follow notification
			notificationDaoImpl.addNotificationFollow(notificationFollow);
	}

	/**
	 * Gets all the notification from the user id
	 * 
	 * @param id
	 *            user's id
	 * @return notification response
	 */
	@Override
	public NotificationResponse getAllNotificationForUser(int id) {
		NotificationResponse notificationResponse = new NotificationResponse();
		// get all notification according to user id
		List<Notification> notifications = notificationDaoImpl.getAllNotification(id);
		// get all the follow notification according to user id
		List<NotificationFollow> notificationFollows = notificationDaoImpl.getAllNotificationFollow(id);
		// add the notifications as notification response
		notificationResponse.getNotifications().addAll(notifications);
		notificationResponse.getNotificationFollows().addAll(notificationFollows);
		return notificationResponse;

	}

	/**
	 * Creates the user notification token for the current user
	 * 
	 * @param notificationToken
	 */
	@Override
	public void addUserNotificationToken(NotificationToken notificationToken) {

		// gets the notification token according to user's id
		NotificationToken noToken = notificationDaoImpl.getTokenByUserId(notificationToken.getUser().getId());

		// if token is present
		if (noToken != null) {
			// if token is same
			if (noToken.getToken().equalsIgnoreCase(notificationToken.getToken())) {
				// do nothing
				return;
			}
		}

		// if token is present but different
		if (noToken != null && !noToken.getToken().equalsIgnoreCase(notificationToken.getToken())) {
			// sets the new token id
			notificationToken.setId(noToken.getId());
			// update token
			notificationDaoImpl.updateUserNotificationToken(notificationToken);
		} else {
			// if token is not present for the user create new notification
			// token
			notificationDaoImpl.addUserNotificationToken(notificationToken);
		}
	}

	/**
	 * sends notification to the user
	 * 
	 * @param notification
	 * @param request
	 * @throws IOException
	 */
	@Override
	public void sendNotification(Notification notification, HttpServletRequest request) throws IOException {

		NotificationToken noToken = new NotificationToken();
		// if notification receiver is present
		if (notification.getToUser().getId() != 0) {
			// gets the token number for the user
			noToken = notificationDaoImpl.getTokenByUserId(notification.getToUser().getId());
		} else {
			// get the music from music id
			Music music = musicServiceImpl.getMusicbyId(notification.getMusic().getId(), null);
			// get the token number
			noToken = notificationDaoImpl.getTokenByUserId(music.getUser().getId());
			// set music to notification
			notification.setMusic(music);
			// set receiver user id
			notification.getToUser().setId(music.getUser().getId());
		}

		// get the user responsible for sending notification
		User notificationSenderUser = userServiceImpl.getUserById(notification.getFromUser().getId());

		// sets up connection for sending notification
		HttpURLConnection conn = setupFCMConnection();

		// sets notification information
		JsonObject json = setNotificationInformation(notification, noToken, notificationSenderUser, request);
		// send notification
		sendNotification(conn, json);

	}

	/**
	 * Sets the notification information
	 * 
	 * @param notification
	 * @param noToken
	 *            token number for the user
	 * @param notificationSenderUser
	 * @param request
	 * @return json object
	 */
	private JsonObject setNotificationInformation(Notification notification, NotificationToken noToken,
			User notificationSenderUser, HttpServletRequest request) {
		JsonObject json = new JsonObject();
		// add to json property
		json.addProperty("to", noToken.getToken().trim());
		JsonObject info = new JsonObject();
		// set server path
		String serverPath = request.getServerName().concat(":") + request.getServerPort();

		String notificationMessage = "";

		// if the notification type is follow
		if (notification.getType().equalsIgnoreCase("follow")) {
			// creates notification follow
			this.addNotificationFollow(notification, notificationSenderUser);
			// add properties for follow notification
			info.addProperty("type", notification.getType());
			info.addProperty("title", "New Follower!");
			// sets the notification message
			notificationMessage = notificationSenderUser.getName().concat(" is now following you!");
			// add properties for follow notification
			info.addProperty("message", notificationMessage);
			info.addProperty("senderUserName", notificationSenderUser.getName());
			info.addProperty("userId", notificationSenderUser.getId());
			info.addProperty("userImagePath",
					serverPath.concat(File.separator).concat(notificationSenderUser.getUser_img_path()));
		} else if (notification.getType().equalsIgnoreCase("like")) {
			// creates notification
			this.addNotification(notification, notificationSenderUser);
			// add properties for like notification
			info.addProperty("title", "New Like!");
			// sets the notification message
			notificationMessage = notificationSenderUser.getName().concat(" likes your music ")
					.concat(notification.getMusic().getTitle()).concat("!");
			// add properties for like notification
			info.addProperty("type", notification.getType());
			info.addProperty("message", notificationMessage);
			info.addProperty("senderUserName", notificationSenderUser.getName());
			info.addProperty("musicTitle", notification.getMusic().getTitle());
			info.addProperty("musicId", notification.getMusic().getId());
			info.addProperty("userImagePath",
					serverPath.concat(File.separator).concat(notificationSenderUser.getUser_img_path()));
		} else {
			// creates notification
			this.addNotification(notification, notificationSenderUser);
			// add properties for comment notification
			info.addProperty("title", "New Comment!");
			// sets the notification message
			notificationMessage = notificationSenderUser.getName().concat(" commented on your music ")
					.concat(notification.getMusic().getTitle()).concat("!");
			// add properties for comment notification
			info.addProperty("type", notification.getType());
			info.addProperty("message", notificationMessage);
			info.addProperty("senderUserName", notificationSenderUser.getName());
			info.addProperty("musicTitle", notification.getMusic().getTitle());
			info.addProperty("musicId", notification.getMusic().getId());
			info.addProperty("userImagePath",
					serverPath.concat(File.separator).concat(notificationSenderUser.getUser_img_path()));
		}
		json.add("data", info);
		return json;
	}

	/**
	 * Sets up the Firebase Cloud Messaging Connection through the URL
	 * 
	 * @return http url connection
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private HttpURLConnection setupFCMConnection() throws MalformedURLException, IOException, ProtocolException {
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key="
				+ "AAAA543FBA8:APA91bHG1gULEG62kVKfVQYZICgDVS2X1b5np-vVE6GkqtVPl1FF8s0HneHyoQ5ClZcnFBAOiWoi6HqJbAeE4FJheg3UnhOQTs5lrEP4Y34f4aThXcgmvncsJeYmOUcca8d9voWc3q40");
		conn.setRequestProperty("Content-Type", "application/json");
		return conn;
	}

	/**
	 * Sends the notification
	 * 
	 * @param conn
	 *            http URL connection
	 * @param json
	 *            json object
	 */
	private void sendNotification(HttpURLConnection conn, JsonObject json) {
		try {
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
