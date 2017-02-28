package com.project.musicianhub.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Response;

public class MusicResourceTest {

	private static MusicResource mockedMusicResource;

	private static Music music1;
	private static Music music2;
	private static Response response;

	@BeforeClass
	public static void setUp() {
		mockedMusicResource = mock(MusicResource.class);
		music1 = new Music(1, "Timilai", "pop", new Date(), "img/1.jpg", "music/1.mp3", false);
		music1.getUser().setId(1);
		music2 = new Music(1, "Mann", "pop", new Date(), "img/2jpg", "music/2.mp3", false);
		music2.getUser().setId(1);
		response = new Response();
		response.setMessage("Music Updated Successfully!");
		when(mockedMusicResource.getMusic()).thenReturn(Arrays.asList(music1, music2));
		when(mockedMusicResource.getMusicById(1,null)).thenReturn(music1);
		when(mockedMusicResource.getMusicByUser(music1.getUser().getId(), null))
				.thenReturn(Arrays.asList(music1, music2));
		when(mockedMusicResource.addMusic(music1, null)).thenReturn(response);
		when(mockedMusicResource.updateMusic(music1)).thenReturn(response);
	}

	@Test
	public void testGetMusic() {
		List<Music> allMusic = mockedMusicResource.getMusic();
		assertEquals(2, allMusic.size());
		Music music = allMusic.get(0);
		assertEquals(1, music.getId());
		assertEquals("Timilai", music.getTitle());
		assertEquals("pop", music.getGenre());
		assertEquals(new Date(), new Date());
		assertEquals("img/1.jpg", music.getAlbum_art_path());
		assertEquals("music/1.mp3", music.getMusic_path());
		assertEquals(false, music.isDeleted());
	}

	@Test
	public void testGetMusicById() {
		Music music = mockedMusicResource.getMusicById(1,null);
		assertEquals(1, music.getId());
		assertEquals("Timilai", music.getTitle());
		assertEquals("pop", music.getGenre());
		assertEquals(new Date(), new Date());
		assertEquals("img/1.jpg", music.getAlbum_art_path());
		assertEquals("music/1.mp3", music.getMusic_path());
		assertEquals(false, music.isDeleted());
	}

	@Test
	public void testGetMusicByUser() {
		List<Music> musics = mockedMusicResource.getMusicByUser(music1.getUser().getId(), null);

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
	public void testAddMusic() {
		Response response = mockedMusicResource.addMusic(music1, null);
		assertNotNull(response);
		assertEquals("Music Added Successfully!", response);
	}

	@Test
	public void testUpdateMusic() {
		Response response = mockedMusicResource.updateMusic(music1);
		assertNotNull(response);
		assertEquals("Music Updated Successfully!", response);
	}

	@Test
	public void testDeleteMusic() {
		Response response = mockedMusicResource.deleteMusic(music1.getId());
		assertNotNull(response);
		assertEquals("Music Deleted Successfully!", response);
	}

}
