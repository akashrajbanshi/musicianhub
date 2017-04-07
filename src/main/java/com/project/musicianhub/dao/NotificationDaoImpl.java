package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.musicianhub.model.Notification;
import com.project.musicianhub.model.NotificationFollow;
import com.project.musicianhub.model.NotificationToken;

/**
 * Dao implementation for Notification
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class NotificationDaoImpl implements NotificationDao {
	/**
	 * Adds the unique notification token to the database
	 * 
	 * @param notificationToken
	 *            notificationToken object
	 */
	@Override
	public void addUserNotificationToken(NotificationToken notificationToken) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addUserNotificationToken(session, notificationToken);

		tx.commit();

		session.close();

	}

	/**
	 * Adds the unique notification token to the database
	 * 
	 * @param session
	 *            session object
	 * @param notificationToken
	 *            notificationToken object
	 */
	@Override
	public void addUserNotificationToken(Session session, NotificationToken notificationToken) {
		session.save(notificationToken);
	}

	/**
	 * Adds notification to the database
	 * 
	 * @param notification
	 *            notification object
	 */
	@Override
	public void addNotification(Notification notification) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addNotification(session, notification);
		tx.commit();
		session.close();

	}

	/**
	 * Adds notification to the database
	 * 
	 * @param session
	 *            session object
	 * @param notification
	 *            notification object
	 */
	@Override
	public void addNotification(Session session, Notification notification) {
		session.save(notification);
	}

	/**
	 * Adds follow notification to the database
	 * 
	 * @param notificationFollow
	 *            notificationFollow object
	 */
	@Override
	public void addNotificationFollow(NotificationFollow notificationFollow) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addNotificationFollow(session, notificationFollow);
		tx.commit();
		session.close();

	}

	/**
	 * Adds follow notification to the database
	 * 
	 * @param session
	 *            session object
	 * @param notificationFollow
	 *            notificationFollow object
	 */
	@Override
	public void addNotificationFollow(Session session, NotificationFollow notificationFollow) {
		session.save(notificationFollow);
	}

	/**
	 * Gets the notification token by the user id
	 * 
	 * @param id
	 *            user's id
	 * @return notification token
	 */
	@Override
	public NotificationToken getTokenByUserId(int id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from NotificationToken where user_id = :user_id");
		query.setInteger("user_id", id);
		List<NotificationToken> notificationToken = query.list();
		tx.commit();
		session.close();
		if (notificationToken.size() == 0) {
			return null;
		}
		return notificationToken.get(0);
	}

	/**
	 * Updates the unique notification token
	 * 
	 * @param notificationToken
	 *            notificationToken object
	 */
	@Override
	public void updateUserNotificationToken(NotificationToken notificationToken) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "update NotificationToken set token = :token where id = :id";
		Query query = session.createQuery(hql);
		query.setInteger("id", notificationToken.getId());
		query.setString("token", notificationToken.getToken());

		int rowCount = query.executeUpdate();
		System.out.println("Rows affected: " + rowCount);

		tx.commit();

		session.close();

	}

	/**
	 * Gets all the notification by the user id
	 * 
	 * @param id
	 *            user's id
	 * @return notification list
	 */
	@Override
	public List<Notification> getAllNotification(int id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Notification where to_user_id = :user_id LIMIT 10");
		query.setInteger("user_id", id);
		List<Notification> notifications = query.list();
		tx.commit();
		session.close();
		return notifications;

	}

	/**
	 * Gets all the follow notification by the user id
	 * 
	 * @param id
	 *            user's id
	 * @return follow notification list
	 */
	@Override
	public List<NotificationFollow> getAllNotificationFollow(int id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from NotificationFollow where to_user_id = :user_id LIMIT 10");
		query.setInteger("user_id", id);
		List<NotificationFollow> notificationFollows = query.list();
		tx.commit();
		session.close();
		return notificationFollows;

	}

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
	@Override
	public Notification getAllNotificationByFromAndToUser(int musicId, int fromId, int toId, String type) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(
				"from Notification where from_user_id = :from_user_id and to_user_id = :to_user_id and music_id = :music_id and type = :type");
		query.setInteger("from_user_id", fromId);
		query.setInteger("to_user_id", toId);
		query.setInteger("music_id", musicId);
		query.setString("type", type);
		List<Notification> notification = query.list();
		tx.commit();
		session.close();
		if (notification.size() == 0) {
			return null;
		}
		return notification.get(0);

	}

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
	@Override
	public NotificationFollow getAllNotificationByFromAndToUser(int fromId, int toId) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("from NotificationFollow where from_user_id = :from_user_id and to_user_id = :to_user_id");
		query.setInteger("from_user_id", fromId);
		query.setInteger("to_user_id", toId);
		List<NotificationFollow> notificationFollow = query.list();
		tx.commit();
		session.close();
		if (notificationFollow.size() == 0) {
			return null;
		}
		return notificationFollow.get(0);

	}
}
