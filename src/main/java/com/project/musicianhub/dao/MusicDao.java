package com.project.musicianhub.dao;

import java.util.List;

import org.hibernate.Session;

import com.project.musicianhub.model.Music;

public interface MusicDao {

	public Music addMusic(Music music);

	public void addMusic(Session session, Music music);

	public List<Music> getMusic();

	public List<Music> getMusicbyUser(int user_id);

	public int deleteMusic(int id);

	public int updateMusic(Music music);

	public Music getMusicbyId(int music_id);

	public int getLastInsertedMusicId();

	public List<Music> searchMusic(String searchText);

}
