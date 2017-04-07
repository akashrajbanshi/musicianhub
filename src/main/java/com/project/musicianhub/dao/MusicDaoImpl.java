package com.project.musicianhub.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.musicianhub.model.Music;

/**
 * Dao implementation for Music
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class MusicDaoImpl implements MusicDao {

	/**
	 * Adds music to the database
	 * 
	 * @param music
	 * @return music object
	 */
	@Override
	public Music addMusic(Music music) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addMusic(session, music);

		tx.commit();

		session.close();
		return music;
	}

	/**
	 * Adds music to the database
	 * 
	 * @param session
	 * @param music
	 */
	@Override
	public void addMusic(Session session, Music music) {

		music.setPublished_date(new Date());

		session.save(music);

	}

	/**
	 * Gets all the music list from the database
	 * 
	 * @return music list
	 */

	@Override
	public List<Music> getMusic() {
		Session session = SessionUtil.getSession();
		Query query = session.createQuery("from Music");
		List<Music> musicList = query.list();
		session.close();
		return musicList;
	}

	/**
	 * Gets music by user id
	 * 
	 * @param user_id
	 *            user's id
	 * @param firstResult
	 *            the value which defines from where the data is to extracted
	 * @param isMaximumResultSet
	 *            total number of data to be loaded
	 * @return music list
	 */
	@Override
	public List<Music> getMusicbyUser(int user_id, int firstResult, boolean isMaximumResultSet) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("from Music where user_id = :user_id and deleted = :deleted ORDER BY id DESC");
		query.setInteger("user_id", user_id);
		query.setBoolean("deleted", false);

		query.setFirstResult(firstResult);
		if (isMaximumResultSet) {
			query.setMaxResults(1);
		}
		List<Music> musicList = query.list();
		tx.commit();

		session.close();
		return musicList;
	}

	/**
	 * Gets the music by id
	 * 
	 * @param music_id
	 * @return music object
	 */
	@Override
	public Music getMusicbyId(int music_id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Music where id = :music_id");
		query.setInteger("music_id", music_id);
		List<Music> music = query.list();
		tx.commit();
		session.close();
		if (music.size() == 0) {
			return null;
		}
		return music.get(0);
	}

	/**
	 * Deletes the music by id
	 * 
	 * @param id
	 * @return
	 */

	@Override
	public int deleteMusic(int id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "update Music set deleted = :deleted where id = :id";
		Query query = session.createQuery(hql);
		query.setInteger("id", id);
		query.setBoolean("deleted", true);
		int rowCount = query.executeUpdate();
		System.out.println("Rows affected: " + rowCount);

		tx.commit();

		session.close();
		return rowCount;
	}

	/**
	 * Updates the music by music object
	 * 
	 * @param music
	 * @return
	 */

	@Override
	public int updateMusic(Music music) {
		if (music.getId() <= 0)
			return 0;
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "";
		if (!music.getAlbum_art_path().isEmpty()) {
			hql = "update Music set title = :title, genre=:genre, album_art_path = :album_art_path where id = :id";
		} else if (!music.getMusic_path().isEmpty()) {
			hql = "update Music set title = :title, genre=:genre, music_path = :music_path where id = :id";
		} else {

			hql = "update Music set title = :title, genre=:genre where id = :id";
		}

		if (!music.getMusic_path().isEmpty() && !music.getAlbum_art_path().isEmpty()) {
			hql = "update Music set title = :title, genre=:genre,album_art_path = :album_art_path, music_path = :music_path where id = :id";
		}
		Query query = session.createQuery(hql);
		query.setInteger("id", music.getId());
		query.setString("title", music.getTitle());
		query.setString("genre", music.getGenre());
		if (!music.getAlbum_art_path().isEmpty())
			query.setString("album_art_path", music.getAlbum_art_path());

		if (!music.getMusic_path().isEmpty())
			query.setString("music_path", music.getMusic_path());

		int rowCount = query.executeUpdate();
		System.out.println("Rows affected: " + rowCount);

		tx.commit();

		session.close();
		return rowCount;
	}

	/**
	 * Gets the Id of the last inserted music Id
	 * 
	 * @return music id
	 */

	@Override
	public int getLastInsertedMusicId() {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Music order by id DESC");
		query.setMaxResults(1);

		int lastId = 0;
		Music music = (Music) query.uniqueResult();
		if (music != null)
			lastId = music.getId();
		tx.commit();
		session.close();
		return lastId;
	}

	/**
	 * Searches for the music by the search text provided
	 * 
	 * @param searchText
	 * @return list of music
	 */

	@Override
	public List<Music> searchMusic(String searchText) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		Query query = session
				.createQuery("from Music where (title LIKE :title or genre LIKE :genre) and deleted = :deleted");
		query.setString("title", "%" + searchText + "%");
		query.setString("genre", "%" + searchText + "%");
		query.setBoolean("deleted", false);
		List<Music> musicList = query.list();
		tx.commit();
		session.close();
		return musicList;
	}

}
