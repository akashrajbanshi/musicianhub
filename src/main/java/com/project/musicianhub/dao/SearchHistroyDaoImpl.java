package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.musicianhub.model.SearchHistory;

public class SearchHistroyDaoImpl implements SearchHistoryDao {

	@Override
	public void addSearchHistory(SearchHistory searchHistory) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addSearchHistory(session, searchHistory);

		tx.commit();

		session.close();

	}

	@Override
	public void addSearchHistory(Session session, SearchHistory searchHistory) {

		session.save(searchHistory);

	}

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
