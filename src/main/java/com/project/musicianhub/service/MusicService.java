package com.project.musicianhub.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.media.multipart.MultiPart;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Response;

public interface MusicService {

	public Response addMusic(Music music);

	public List<Music> getMusic();

	public List<Music> getMusicbyUser(int user_id, HttpServletRequest request);

	public Response deleteMusic(int id);

	public Response updateMusic(Music music);

	public Music getMusicbyId(int music_id, HttpServletRequest request);

	public String uploadUserImage(MultiPart multiPart, ServletContext context);

	public String uploadUserMusic(MultiPart multiPart, ServletContext context);

	public List<Music> searchMusic(String searchText, HttpServletRequest request);

	public List<Music> getMusicByFollowingUser(int user_id, HttpServletRequest request);
}
