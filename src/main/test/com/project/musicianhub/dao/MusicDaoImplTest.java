package com.project.musicianhub.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.project.musicianhub.model.Music;

public class MusicDaoImplTest {

	MusicDaoImpl musicDaoImpl;

	@Test
	public void testAddMusicMusic() {
		musicDaoImpl = new MusicDaoImpl();
		Music music1 = new Music(1, "Timilai", "pop", new Date(), "img/1.jpg", "music/1.mp3", false);
		music1.getUser().setId(1);
		Music music = musicDaoImpl.addMusic(music1);
		assertEquals(music1.getId(), music.getId());
	}

	@Test
	public void testAddMusicSessionMusic() {
		musicDaoImpl = new MusicDaoImpl();
		Session session = SessionUtil.getSession();
		Music music1 = new Music(1, "Timilai", "pop", new Date(), "img/1.jpg", "music/1.mp3", false);
		music1.getUser().setId(1);
		musicDaoImpl.addMusic(session, music1);
	}

	@Test
	public void testGetMusic() {
		musicDaoImpl = new MusicDaoImpl();
		List<Music> musics = musicDaoImpl.getMusic();
		assertEquals(2, musics.size());
		Music music = musics.get(0);
		assertEquals(1, music.getId());
		assertEquals("Timilai", music.getTitle());
		assertEquals("pop", music.getGenre());
		assertEquals(new Date(), new Date());
		assertEquals("img/1.jpg", music.getAlbum_art_path());
		assertEquals("music/1.mp3", music.getMusic_path());
		assertEquals(false, music.isDeleted());
	}

	@Test
	public void testGetMusicbyUser() {
		musicDaoImpl = new MusicDaoImpl();
		List<Music> musics = musicDaoImpl.getMusicbyUser(1, 0, false);
		assertEquals(2, musics.size());
		Music music = musics.get(0);
		assertEquals(1, music.getId());
		assertEquals("Timilai", music.getTitle());
		assertEquals("pop", music.getGenre());
		assertEquals(new Date(), new Date());
		assertEquals("img/1.jpg", music.getAlbum_art_path());
		assertEquals("music/1.mp3", music.getMusic_path());
		assertEquals(false, music.isDeleted());
	}

	@Test
	public void testGetMusicbyId() {
		musicDaoImpl = new MusicDaoImpl();
		Music music = musicDaoImpl.getMusicbyId(1);
		assertEquals(1, music.getId());
		assertEquals("Timilai", music.getTitle());
		assertEquals("pop", music.getGenre());
		assertEquals(new Date(), new Date());
		assertEquals("img/1.jpg", music.getAlbum_art_path());
		assertEquals("music/1.mp3", music.getMusic_path());
		assertEquals(false, music.isDeleted());
	}

	@Test
	public void testDeleteMusic() {
		musicDaoImpl = new MusicDaoImpl();
		int deleted = musicDaoImpl.deleteMusic(1);
		assertEquals(1, deleted);
	}

	@Test
	public void testUpdateMusic() {
		musicDaoImpl = new MusicDaoImpl();
		Music music1 = new Music(1, "Timilai", "pop", new Date(), "img/1.jpg", "music/1.mp3", false);
		int updated = musicDaoImpl.updateMusic(music1);
		assertEquals(1, updated);

	}

	@Test
	public void testGetLastInsertedMusicId() {
		musicDaoImpl = new MusicDaoImpl();
		int lastId = musicDaoImpl.getLastInsertedMusicId();
		assertEquals(1, lastId);
	}

	@Test
	public void testSearchMusic() {
		musicDaoImpl = new MusicDaoImpl();
		List<Music> musics = musicDaoImpl.searchMusic("timilai");
		assertEquals(2, musics.size());
		Music music = musics.get(0);
		assertEquals(1, music.getId());
		assertEquals("Timilai", music.getTitle());
		assertEquals("pop", music.getGenre());
		assertEquals(new Date(), new Date());
		assertEquals("img/1.jpg", music.getAlbum_art_path());
		assertEquals("music/1.mp3", music.getMusic_path());
		assertEquals(false, music.isDeleted());
	}

}
