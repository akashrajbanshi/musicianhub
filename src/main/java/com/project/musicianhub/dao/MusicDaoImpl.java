package com.project.musicianhub.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.User;

public class MusicDaoImpl implements MusicDao {

	@Override
	public Music addMusic(Music music) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		addMusic(session, music);

		tx.commit();

		session.close();
		return music;
	}

	@Override
	public void addMusic(Session session, Music music) {

		music.setPublished_date(new Date());

		session.save(music);

	}

	@Override
	public List<Music> getMusic() {
		Session session = SessionUtil.getSession();
		Query query = session.createQuery("from Music");
		List<Music> musicList = query.list();
		session.close();
		return musicList;
	}

	@Override
	public List<Music> getMusicbyUser(int user_id) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		// String sql = "SELECT user.id user_id,user.username,user.name,music.id
		// as music_id, music.title, music.genre, music.published_date,
		// music.album_art_path, music.music_path from User user,Music music
		// WHERE music.user_id=user.id and music.user_id= :user_id";
		Query query = session.createQuery("from Music where user_id = :user_id and deleted = :deleted");
		query.setInteger("user_id", user_id);
		query.setBoolean("deleted", false);
		/*
		 * Query query = session.createSQLQuery(sql);
		 * query.setParameter("user_id", user_id);
		 * query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		 */
		List<Music> musicList = query.list();
		tx.commit();

		session.close();
		return musicList;
	}

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

	@Override
	public List<Music> searchMusic(String searchText) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Music where (title LIKE :title or genre LIKE :genre) and deleted = :deleted");
		query.setString("title", "%" + searchText + "%");
		query.setString("genre", "%" + searchText + "%");
		query.setBoolean("deleted", false);
		List<Music> musicList = query.list();
		tx.commit();
		session.close();
		return musicList;
	}

}
