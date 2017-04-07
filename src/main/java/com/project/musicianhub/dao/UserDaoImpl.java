package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.musicianhub.model.User;

/**
 * Dao implementation for User
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class UserDaoImpl implements UserDao {

	/**
	 * Adds user to the database
	 * 
	 * @param user
	 *            user object
	 * @return user object
	 */
	@Override
	public User addUser(User user) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addUser(session, user);

		tx.commit();

		session.close();
		return user;
	}

	/**
	 * Adds user to the database
	 * 
	 * @param session
	 *            session object
	 * @param user
	 *            user object
	 */
	@Override
	public void addUser(Session session, User user) {
		session.save(user);
	}

	/**
	 * Gets all the user
	 * 
	 * @return user list
	 */
	@Override
	public List<User> getUsers() {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User");
		List<User> users = query.list();
		tx.commit();
		session.close();
		return users;
	}

	/**
	 * Deletes the user by id
	 * 
	 * @param id
	 *            user id
	 * @return delete success/failure as int
	 */
	@Override
	public int deleteUsers(int id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "delete from User where id = :id";
		Query query = session.createQuery(hql);
		query.setInteger("id", id);
		int rowCount = query.executeUpdate();
		System.out.println("Rows affected: " + rowCount);

		tx.commit();

		session.close();

		return rowCount;
	}

	/**
	 * Updates the user
	 * 
	 * @param user
	 *            user object
	 * @return update success/failure as int
	 */
	@Override
	public int updateUsers(User user) {
		if (user.getId() <= 0)
			return 0;
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "update User set name = :name, username = :username, gender=:gender, user_img_path= :user_img_path, email = :email, phone_no = :phone_no where id = :id";
		Query query = session.createQuery(hql);
		query.setInteger("id", user.getId());
		query.setString("name", user.getName());
		query.setString("gender", user.getGender());
		query.setString("username", user.getUsername());
		query.setString("user_img_path", user.getUser_img_path());
		query.setString("email", user.getEmail());
		query.setLong("phone_no", user.getPhone_no());
		int rowCount = query.executeUpdate();
		System.out.println("Rows affected: " + rowCount);

		tx.commit();

		session.close();
		return rowCount;
	}

	/**
	 * Gets the User info by its id
	 * 
	 * @param user_id
	 *            user id
	 * @return user object
	 */
	@Override
	public User getUserById(int user_id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User where id = :id");
		query.setInteger("id", user_id);
		List<User> users = query.list();
		if (users.size() == 0) {
			User user = new User();
			user = null;
			users.add(user);
		}
		User user = users.get(0);

		tx.commit();
		session.close();
		return user;
	}

	/**
	 * Gets the last inserted user id
	 * 
	 * @return user id
	 */
	@Override
	public int getLastInsertedUserId() {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User order by id DESC");
		query.setMaxResults(1);
		int lastId = 0;
		User user = (User) query.uniqueResult();
		if (user != null)
			lastId = user.getId();
		tx.commit();
		session.close();
		return lastId;
	}

	/**
	 * Gets user by its username
	 * 
	 * @param user
	 *            user object
	 * @return user object
	 */
	@Override
	public User getUserByUserName(User user) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User where username = :username");
		query.setString("username", user.getUsername());
		List<User> users = query.list();
		if (users.size() == 0) {
			User userObj = new User();
			userObj = null;
			users.add(userObj);
		}
		User userObj = users.get(0);
		tx.commit();

		session.close();
		return userObj;
	}

	/**
	 * Checks if table is empty
	 * 
	 * @return true: if table is empty else false
	 */
	@Override
	public boolean checkIfTableEmpty() {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User");

		List<User> users = query.list();
		tx.commit();
		session.close();
		if (users.size() == 0)
			return true;
		else
			return false;

	}

}
