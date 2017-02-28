package com.project.musicianhub.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.MultiPart;

import com.project.musicianhub.dao.CommentDaoImpl;
import com.project.musicianhub.dao.FollowDaoImpl;
import com.project.musicianhub.dao.LikeDaoImpl;
import com.project.musicianhub.dao.MusicDaoImpl;
import com.project.musicianhub.model.Follow;
import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Response;
import com.project.musicianhub.model.User;

public class MusicServiceImpl implements MusicService {

	MusicDaoImpl musicDaoImpl = new MusicDaoImpl();
	LikeDaoImpl likeDaoImpl = new LikeDaoImpl();
	CommentDaoImpl commentDaoImpl = new CommentDaoImpl();
	FollowDaoImpl followDaoImpl = new FollowDaoImpl();

	@Override
	public Response addMusic(Music music) {
		Response response = new Response();
		Music musicObj = musicDaoImpl.addMusic(music);
		if (musicObj != null) {
			response.setMusic(musicObj);
			response.setCode("create_success");
			response.setMessage("Post Successfully Created...");
		} else {
			response.setCode("create_failed");
			response.setMessage("Post Failed...");
		}
		return response;
	}

	@Override
	public List<Music> getMusic() {
		return musicDaoImpl.getMusic();
	}

	@Override
	public List<Music> getMusicbyUser(int user_id, HttpServletRequest request) {
		List<Music> musicList = musicDaoImpl.getMusicbyUser(user_id);
		String serverPath = request.getServerName().concat(":") + request.getServerPort();

		List<Music> finalMusicList = new ArrayList<>();
		for (Music music : musicList) {
			String imagePath = serverPath.concat(File.separator).concat(music.getAlbum_art_path());
			String musicPath = serverPath.concat(File.separator).concat(music.getMusic_path());

			music.getUser().setCommentUserImagePath(serverPath);
			music.getUser().setUser_img_path(music.getUser().getUser_img_path());
			music.setAlbum_art_path(imagePath);
			music.setMusic_path(musicPath);

			music.getComments().addAll(commentDaoImpl.getCommentByMusic(music.getId()));
			music.getLikes().addAll(likeDaoImpl.getLikebyMusic(music.getId()));

			finalMusicList.add(music);
		}
		return finalMusicList;
	}

	@Override
	public Music getMusicbyId(int music_id, HttpServletRequest request) {
		Music music = musicDaoImpl.getMusicbyId(music_id);

		if (request != null) {
			String serverPath = request.getServerName().concat(":") + request.getServerPort();

			String imagePath = serverPath.concat(File.separator).concat(music.getAlbum_art_path());
			String musicPath = serverPath.concat(File.separator).concat(music.getMusic_path());

			String userImagePath = serverPath.concat(File.separator).concat(music.getUser().getUser_img_path());

			music.setAlbum_art_path(imagePath);
			music.setMusic_path(musicPath);
			music.getUser().setUser_img_path(userImagePath);

			music.getComments().addAll(commentDaoImpl.getCommentByMusic(music.getId()));
			music.getLikes().addAll(likeDaoImpl.getLikebyMusic(music.getId()));
		}

		return music;
	}

	@Override
	public Response deleteMusic(int id) {
		Response response = new Response();
		int affectedCount = musicDaoImpl.deleteMusic(id);
		if (affectedCount > 0) {

			response.setCode("delete_success");
			response.setMessage("Post Successfully Deleted...");
		} else {
			response.setCode("delete_failed");
			response.setMessage("Post Delete Failed...");
		}
		return response;
	}

	@Override
	public Response updateMusic(Music music) {
		Response response = new Response();
		int recordAffected = musicDaoImpl.updateMusic(music);
		if (recordAffected > 0) {
			response.setCode("update_success");
			response.setMessage("Post Successfully Updated...");
		} else {
			response.setCode("update_failed");
			response.setMessage("Post Update Failed...");
		}
		return response;
	}

	@Override
	public String uploadUserImage(MultiPart multiPart, ServletContext context) {

		String musicId = "";
		String userId = "";
		BodyPart from = multiPart.getBodyParts().get(2);
		BodyPartEntity frombpe = (BodyPartEntity) from.getEntity();
		BufferedReader reader = new BufferedReader(new InputStreamReader(frombpe.getInputStream()));
		String line = "";

		try {
			while ((line = reader.readLine()) != null) {
				if (line.equalsIgnoreCase("addMusic")) {
					int lastMusicId = musicDaoImpl.getLastInsertedMusicId();
					musicId = String.valueOf(lastMusicId + 1);
				} else {
					BodyPart idBodyPart = multiPart.getBodyParts().get(4);
					BodyPartEntity idBodyPartEntity = (BodyPartEntity) idBodyPart.getEntity();

					BufferedReader idBufferedReader = new BufferedReader(
							new InputStreamReader(idBodyPartEntity.getInputStream()));
					String music_id = "";
					music_id = idBufferedReader.readLine();
					Music music = this.getMusicbyId(Integer.valueOf(music_id), null);
					if (music.getAlbum_art_path() == null || music.getAlbum_art_path().trim().length() == 0
							|| music.getId() == Integer.valueOf(music_id)) {
						musicId = music_id;
					}

				}

			}

			BodyPart idBodyPart = multiPart.getBodyParts().get(3);
			BodyPartEntity idBodyPartEntity = (BodyPartEntity) idBodyPart.getEntity();

			BufferedReader idBufferedReader = new BufferedReader(
					new InputStreamReader(idBodyPartEntity.getInputStream()));

			userId = idBufferedReader.readLine();

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String realPath = context.getRealPath(File.separator);
		String absoultePath = context.getContextPath();

		String uploadPath = realPath.concat(File.separator).concat("uploads").concat(File.separator).concat("music")
				.concat(File.separator).concat(userId);
		String savePath = absoultePath + File.separator.concat("uploads").concat(File.separator).concat("music")
				.concat(File.separator).concat(userId);

		BodyPart file = multiPart.getBodyParts().get(0);
		BodyPartEntity bpe = (BodyPartEntity) file.getEntity();
		InputStream stream = bpe.getInputStream();

		String filename = file.getContentDisposition().getFileName();
		String seperateFileMimeType = filename.split("\\.")[1];
		String finalFileName = musicId + "." + seperateFileMimeType;

		try {
			createFolderIfNotExists(uploadPath);
		} catch (SecurityException se) {
			se.printStackTrace();
		}
		String target = uploadPath + File.separator + finalFileName;
		String finalPath = savePath + File.separator + finalFileName;

		saveToFile(stream, target);
		return finalPath;
	}

	@Override
	public String uploadUserMusic(MultiPart multiPart, ServletContext context) {

		String musicId = "";
		String userId = "";
		BodyPart from = multiPart.getBodyParts().get(2);
		BodyPartEntity frombpe = (BodyPartEntity) from.getEntity();
		BufferedReader reader = new BufferedReader(new InputStreamReader(frombpe.getInputStream()));
		String line = "";

		try {
			while ((line = reader.readLine()) != null) {
				if (line.equalsIgnoreCase("addMusic")) {
					int lastMusicId = musicDaoImpl.getLastInsertedMusicId();
					musicId = String.valueOf(lastMusicId + 1);
				} else {
					BodyPart idBodyPart = multiPart.getBodyParts().get(4);
					BodyPartEntity idBodyPartEntity = (BodyPartEntity) idBodyPart.getEntity();

					BufferedReader idBufferedReader = new BufferedReader(
							new InputStreamReader(idBodyPartEntity.getInputStream()));
					String music_id = "";
					music_id = idBufferedReader.readLine();
					Music music = this.getMusicbyId(Integer.valueOf(music_id), null);
					if (music.getMusic_path() == null || music.getMusic_path().trim().length() == 0
							|| music.getId() == Integer.valueOf(music_id)) {
						musicId = music_id;
					}

				}

			}

			BodyPart idBodyPart = multiPart.getBodyParts().get(3);
			BodyPartEntity idBodyPartEntity = (BodyPartEntity) idBodyPart.getEntity();

			BufferedReader idBufferedReader = new BufferedReader(
					new InputStreamReader(idBodyPartEntity.getInputStream()));

			userId = idBufferedReader.readLine();

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String realPath = context.getRealPath(File.separator);
		String absoultePath = context.getContextPath();

		String uploadPath = realPath.concat(File.separator).concat("uploads").concat(File.separator).concat("music")
				.concat(File.separator).concat(userId);
		String savePath = absoultePath + File.separator.concat("uploads").concat(File.separator).concat("music")
				.concat(File.separator).concat(userId);

		BodyPart file = multiPart.getBodyParts().get(0);
		BodyPartEntity bpe = (BodyPartEntity) file.getEntity();
		InputStream stream = bpe.getInputStream();

		String filename = file.getContentDisposition().getFileName();
		String seperateFileMimeType = filename.split("\\.")[1];
		String finalFileName = musicId + "." + seperateFileMimeType;

		try {
			createFolderIfNotExists(uploadPath);
		} catch (SecurityException se) {
			se.printStackTrace();
		}
		String target = uploadPath + File.separator + finalFileName;
		String finalPath = savePath + File.separator + finalFileName;

		saveToFile(stream, target);
		return finalPath;
	}

	private void saveToFile(InputStream inStream, String target) {
		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(target));
			while ((read = inStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	private void createFolderIfNotExists(String dirName) {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdirs();
		}
	}

	@Override
	public List<Music> searchMusic(String searchText, HttpServletRequest request) {
		List<Music> musicList = musicDaoImpl.searchMusic(searchText);
		String serverPath = request.getServerName().concat(":") + request.getServerPort();

		List<Music> finalMusicList = new ArrayList<>();
		for (Music music : musicList) {
			String imagePath = serverPath.concat(File.separator).concat(music.getAlbum_art_path());

			music.setAlbum_art_path(imagePath);

			finalMusicList.add(music);
		}
		return finalMusicList;

	}

	@Override
	public List<Music> getMusicByFollowingUser(int user_id, HttpServletRequest request) {
		List<Follow> followingUsers =  followDaoImpl.getFollowingByUser(user_id);
		List<Music> musicList = new ArrayList<>();
		for (Follow following : followingUsers) {
			User followingUser = following.getFollowingUser();
			musicList.addAll(this.getMusicbyUser(followingUser.getId(), request));
		}
		return musicList;
	}

}
