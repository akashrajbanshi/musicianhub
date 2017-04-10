package com.project.musicianhub.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Response;

public class MusicServiceImplTest {
	MusicServiceImpl musicServiceImpl;

	@Test
	public void testAddMusic() {
		musicServiceImpl = new MusicServiceImpl();
		Music music1 = new Music(1, "Timilai", "pop", new Date(), "img/1.jpg", "music/1.mp3", false);
		music1.getUser().setId(1);
		Response response = musicServiceImpl.addMusic(music1);
		assertEquals("Music added Successfully!", response.getMessage());
	}

	@Test
	public void testGetMusic() {
		musicServiceImpl = new MusicServiceImpl();
		List<Music> musics = musicServiceImpl.getMusic();
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
		musicServiceImpl = new MusicServiceImpl();
		List<Music> musics = musicServiceImpl.getMusicbyUser(1, null, 0, false);
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
		musicServiceImpl = new MusicServiceImpl();
		Music music = musicServiceImpl.getMusicbyId(1, null);
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
		musicServiceImpl = new MusicServiceImpl();
		Response response = musicServiceImpl.deleteMusic(1);
		assertEquals(response.getMessage(), "Music Deleted Successfully!");
	}

	@Test
	public void testUpdateMusic() {
		musicServiceImpl = new MusicServiceImpl();
		Music music1 = new Music(1, "Timilai", "pop", new Date(), "img/1.jpg", "music/1.mp3", false);
		Response response = musicServiceImpl.updateMusic(music1);
		assertEquals("Music updated Successfully!", response.getMessage());
	}

	@Test
	public void testUploadAlbumImage() {
		musicServiceImpl = new MusicServiceImpl();
		Music music1 = new Music(1, "Timilai", "pop", new Date(), "img/1.jpg", "music/1.mp3", false);
		String albumArtPath = musicServiceImpl.uploadAlbumImage(null, null);
		assertEquals(music1.getAlbum_art_path(), albumArtPath);
	}

	@Test
	public void testUploadUserMusic() {
		musicServiceImpl = new MusicServiceImpl();
		Music music1 = new Music(1, "Timilai", "pop", new Date(), "img/1.jpg", "music/1.mp3", false);
		String musicPath = musicServiceImpl.uploadUserMusic(null, null);
		assertEquals(music1.getMusic_path(), musicPath);
	}

	@Test
	public void testSearchMusic() {
		musicServiceImpl = new MusicServiceImpl();
		List<Music> musics = musicServiceImpl.searchMusic("timilai", null);
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
	public void testGetMusicByFollowingUser() {
		musicServiceImpl = new MusicServiceImpl();
		Music music1 = new Music(1, "Timilai", "pop", new Date(), "img/1.jpg", "music/1.mp3", false);
		Music music = musicServiceImpl.getMusicByFollowingUser(1, null, 0, music1);
		Music finalMusic = music.getMusics().get(0);
		assertEquals(1, finalMusic.getId());
		assertEquals("Timilai", finalMusic.getTitle());
		assertEquals("pop", finalMusic.getGenre());
		assertEquals(new Date(), new Date());
		assertEquals("img/1.jpg", finalMusic.getAlbum_art_path());
		assertEquals("music/1.mp3", finalMusic.getMusic_path());
		assertEquals(false, finalMusic.isDeleted());
	}

}
