package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.musicianhub.model.SearchHistory;

/**
 * Dao implementation for Search History
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class SearchHistroyDaoImpl implements SearchHistoryDao {
	/**
	 * Adds the search history
	 * 
	 * @param searchHistory
	 *            searchHistory object
	 */

	@Override
	public void addSearchHistory(SearchHistory searchHistory) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addSearchHistory(session, searchHistory);

		tx.commit();

		session.close();

	}

	/**
	 * Adds the search history
	 * 
	 * @param session
	 *            session object
	 * @param searchHistory
	 *            searchHistory object
	 */
	@Override
	public void addSearchHistory(Session session, SearchHistory searchHistory) {

		session.save(searchHistory);

	}

	/**
	 * Gets the search history by the user's id
	 * 
	 * @param user_id
	 *            user id
	 * @return search history list
	 */
	@Override
	public List<SearchHistory> getSearchHistoryByUser(int user_id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from SearchHistory where user_id = :user_id");
		query.setInteger("user_id", user_id);
		List<SearchHistory> searchHistories = query.list();
		tx.commit();
		session.close();
		return searchHistories;

	}

	/**
	 * Deletes the search history from the database of a particular user
	 * 
	 * @param user_id
	 *            user id
	 */
	@Override
	public void clearSearchHistory(int user_id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "delete from SearchHistory where user_id = :user_id";
			Query query = session.createQuery(hql);
			query.setInteger("user_id", user_id);
			query.executeUpdate();
			tx.commit();
		} catch (Throwable t) {
			tx.rollback();
			throw t;
		}
		session.close();

	}

}
