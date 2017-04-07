package com.project.musicianhub.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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

/**
 * Service implementation for the Music
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class MusicServiceImpl implements MusicService {

	MusicDaoImpl musicDaoImpl = new MusicDaoImpl();
	LikeDaoImpl likeDaoImpl = new LikeDaoImpl();
	CommentDaoImpl commentDaoImpl = new CommentDaoImpl();
	FollowDaoImpl followDaoImpl = new FollowDaoImpl();

	/**
	 * Creates the music
	 * 
	 * @param music
	 *            music object
	 * @return custom response
	 */
	@Override
	public Response addMusic(Music music) {
		Response response = new Response();
		// creates the music
		Music musicObj = musicDaoImpl.addMusic(music);
		// if music is created successfully
		if (musicObj != null) {
			// sets the response of music created successfully
			response.setMusic(musicObj);
			response.setCode("create_success");
			response.setMessage("Post Successfully Created...");
		} else {
			// else post about the failure
			response.setCode("create_failed");
			response.setMessage("Post Failed...");
		}
		return response;
	}

	/**
	 * Gets all the music available
	 * 
	 * @return music list
	 */
	@Override
	public List<Music> getMusic() {
		return musicDaoImpl.getMusic();
	}

	/**
	 * Gets the music according to the user's id
	 * 
	 * @param user_id
	 *            user id
	 * @param request
	 *            http servlet request
	 * @param firstResult
	 *            start fetching data from this point
	 * @param isMaximumResultSet
	 *            check for setting the maximum result
	 * @return music list
	 */
	@Override
	public List<Music> getMusicbyUser(int user_id, HttpServletRequest request, int firstResult,
			boolean isMaximumResultSet) {
		// gets the music list by the user id
		List<Music> musicList = musicDaoImpl.getMusicbyUser(user_id, firstResult, isMaximumResultSet);
		// gets the server path
		String serverPath = request.getServerName().concat(":") + request.getServerPort();

		List<Music> finalMusicList = new ArrayList<>();

		for (Music music : musicList) {
			String imagePath = "";
			String musicPath = "";
			// sets the album art path and music path if the current music
			// object path is not empty
			if (!music.getAlbum_art_path().isEmpty() || !music.getAlbum_art_path().equals("")) {
				imagePath = serverPath.concat(File.separator).concat(music.getAlbum_art_path());
				musicPath = serverPath.concat(File.separator).concat(music.getMusic_path());
			}

			// sets the server path in the comment user image path just for the
			// data storage
			music.getUser().setCommentUserImagePath(serverPath);
			music.getUser().setUser_img_path(music.getUser().getUser_img_path());
			music.setAlbum_art_path(imagePath);
			music.setMusic_path(musicPath);
			// sets the comments and likes for the music
			music.getComments().addAll(commentDaoImpl.getCommentByMusic(music.getId()));
			music.getLikes().addAll(likeDaoImpl.getLikebyMusic(music.getId()));

			finalMusicList.add(music);
		}
		return finalMusicList;
	}

	/**
	 * Get Music by music id
	 * 
	 * @param music_id
	 *            music id
	 * @param request
	 *            http request
	 * @return music object
	 */
	@Override
	public Music getMusicbyId(int music_id, HttpServletRequest request) {
		// gets music by music id
		Music music = musicDaoImpl.getMusicbyId(music_id);

		// if http request in not null
		if (request != null) {
			// gets the server path
			String serverPath = request.getServerName().concat(":") + request.getServerPort();

			// sets the album art and the music path
			String imagePath = serverPath.concat(File.separator).concat(music.getAlbum_art_path());
			String musicPath = serverPath.concat(File.separator).concat(music.getMusic_path());

			String userImagePath = serverPath.concat(File.separator).concat(music.getUser().getUser_img_path());

			music.setAlbum_art_path(imagePath);
			music.setMusic_path(musicPath);
			music.getUser().setUser_img_path(userImagePath);

			// sets the comments and likes for the music
			music.getComments().addAll(commentDaoImpl.getCommentByMusic(music.getId()));
			music.getLikes().addAll(likeDaoImpl.getLikebyMusic(music.getId()));
		}

		return music;
	}

	/**
	 * Deletes the music by music's id
	 * 
	 * @param id
	 *            music id
	 * @return custom response
	 */
	@Override
	public Response deleteMusic(int id) {
		Response response = new Response();
		// deletes the current music
		int affectedCount = musicDaoImpl.deleteMusic(id);
		// if delete is successful
		if (affectedCount > 0) {
			// sets the delete success response
			response.setCode("delete_success");
			response.setMessage("Post Successfully Deleted...");
		} else {
			// else delete failed response is set
			response.setCode("delete_failed");
			response.setMessage("Post Delete Failed...");
		}
		return response;
	}

	/**
	 * Updates the music by music object
	 * 
	 * @param music
	 *            music object
	 * @return custom response
	 */
	@Override
	public Response updateMusic(Music music) {
		Response response = new Response();
		// updates the music
		int recordAffected = musicDaoImpl.updateMusic(music);
		// if the update is success
		if (recordAffected > 0) {
			// sets the update success response
			response.setCode("update_success");
			response.setMessage("Post Successfully Updated...");
		} else {
			// sets the update failed response
			response.setCode("update_failed");
			response.setMessage("Post Update Failed...");
		}
		return response;
	}

	/**
	 * Uploads the album art image for the music
	 * 
	 * @param multiPart
	 *            image's part from the client
	 * @param context
	 *            servlet context
	 * @return upload image path
	 */
	@Override
	public String uploadAlbumImage(MultiPart multiPart, ServletContext context) {

		String musicId = "";
		String userId = "";
		// gets the from part
		BodyPart from = multiPart.getBodyParts().get(2);
		// gets the entity of the body part
		BodyPartEntity frombpe = (BodyPartEntity) from.getEntity();
		// gets the reader
		BufferedReader reader = new BufferedReader(new InputStreamReader(frombpe.getInputStream()));
		String line = "";

		try {
			while ((line = reader.readLine()) != null) {
				// if line is not null and is from add music
				if (line.equalsIgnoreCase("addMusic")) {
					// get the last inserted music id
					int lastMusicId = musicDaoImpl.getLastInsertedMusicId();
					// sets the new music id
					musicId = String.valueOf(lastMusicId + 1);
				} else {
					// gets the music id
					BodyPart idBodyPart = multiPart.getBodyParts().get(4);
					BodyPartEntity idBodyPartEntity = (BodyPartEntity) idBodyPart.getEntity();

					BufferedReader idBufferedReader = new BufferedReader(
							new InputStreamReader(idBodyPartEntity.getInputStream()));
					String music_id = "";
					music_id = idBufferedReader.readLine();
					// gets the music object from the music id
					Music music = this.getMusicbyId(Integer.valueOf(music_id), null);
					// if album art path is null, not available and current
					// music id is equals to the music if from the client
					if (music.getAlbum_art_path() == null || music.getAlbum_art_path().trim().length() == 0
							|| music.getId() == Integer.valueOf(music_id)) {
						// sets the music id as current
						musicId = music_id;
					}

				}

			}

			// gets the user id from the client
			BodyPart idBodyPart = multiPart.getBodyParts().get(3);
			BodyPartEntity idBodyPartEntity = (BodyPartEntity) idBodyPart.getEntity();

			BufferedReader idBufferedReader = new BufferedReader(
					new InputStreamReader(idBodyPartEntity.getInputStream()));
			// sets the user id
			userId = idBufferedReader.readLine();

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String realPath = context.getRealPath(File.separator);
		String absoultePath = context.getContextPath();
		// sets upload path
		String uploadPath = realPath.concat(File.separator).concat("uploads").concat(File.separator).concat("music")
				.concat(File.separator).concat(userId);
		// sets actual save path of a file
		String savePath = absoultePath + File.separator.concat("uploads").concat(File.separator).concat("music")
				.concat(File.separator).concat(userId);

		// gets the actual file from the client
		BodyPart file = multiPart.getBodyParts().get(0);
		BodyPartEntity bpe = (BodyPartEntity) file.getEntity();
		InputStream stream = bpe.getInputStream();

		// gets the file name
		String filename = file.getContentDisposition().getFileName();
		String seperateFileMimeType = filename.split("\\.")[1];
		String finalFileName = musicId + "." + seperateFileMimeType;

		try {
			// creates folder
			createFolderIfNotExists(uploadPath);
		} catch (SecurityException se) {
			se.printStackTrace();
		}
		// uploads the file to the physical path
		String target = uploadPath + File.separator + finalFileName;
		// uploads the file to the database path
		String finalPath = savePath + File.separator + finalFileName;

		// saves the file
		saveToFile(stream, target);
		return finalPath;
	}

	/**
	 * Uploads the music to the server
	 * 
	 * @param multiPart
	 *            music part from the client
	 * @param context
	 *            servlet context
	 * @return upload music path
	 */
	@Override
	public String uploadUserMusic(MultiPart multiPart, ServletContext context) {

		String musicId = "";
		String userId = "";
		// gets the context of the current music upload task
		BodyPart from = multiPart.getBodyParts().get(2);
		BodyPartEntity frombpe = (BodyPartEntity) from.getEntity();
		BufferedReader reader = new BufferedReader(new InputStreamReader(frombpe.getInputStream()));
		String line = "";

		try {
			while ((line = reader.readLine()) != null) {
				// if it is from the add music
				if (line.equalsIgnoreCase("addMusic")) {
					// gets the last inserted id and sets as music id
					// incrementing it
					int lastMusicId = musicDaoImpl.getLastInsertedMusicId();
					musicId = String.valueOf(lastMusicId + 1);
				} else {
					// if not update using the current id
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

			// gets the user id from the client
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

		// gets the file part from the client
		BodyPart file = multiPart.getBodyParts().get(0);
		BodyPartEntity bpe = (BodyPartEntity) file.getEntity();
		InputStream stream = bpe.getInputStream();

		String filename = file.getContentDisposition().getFileName();
		String seperateFileMimeType = filename.split("\\.")[1];
		String finalFileName = musicId + "." + seperateFileMimeType;

		try {
			// create folder
			createFolderIfNotExists(uploadPath);
		} catch (SecurityException se) {
			se.printStackTrace();
		}
		// upload file path
		String target = uploadPath + File.separator + finalFileName;
		// final file path for storing in db
		String finalPath = savePath + File.separator + finalFileName;

		// save to the file
		saveToFile(stream, target);
		return finalPath;
	}

	/**
	 * Saves the file to the file path which is physical
	 * 
	 * @param inStream
	 *            input stream data
	 * @param target
	 *            file path to upload file
	 */
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

	/**
	 * Creates the folder if the folder does not exist
	 * 
	 * @param dirName
	 *            directory name
	 */
	private void createFolderIfNotExists(String dirName) {
		File theDir = new File(dirName);
		// if directory does not exist
		if (!theDir.exists()) {
			// make a new directory
			theDir.mkdirs();
		}
	}

	/**
	 * Gets the music list according to the search text from the client
	 * 
	 * @param searchText
	 *            user's text input from the client
	 * @param request
	 *            http request
	 * @return music list
	 */
	@Override
	public List<Music> searchMusic(String searchText, HttpServletRequest request) {
		// gets the music list from the search text
		List<Music> musicList = musicDaoImpl.searchMusic(searchText);
		// get the server path
		String serverPath = request.getServerName().concat(":") + request.getServerPort();

		List<Music> finalMusicList = new ArrayList<>();
		for (Music music : musicList) {
			// sets the album art image path
			String imagePath = serverPath.concat(File.separator).concat(music.getAlbum_art_path());
			music.setAlbum_art_path(imagePath);

			finalMusicList.add(music);
		}
		return finalMusicList;

	}

	/**
	 * Gets the music according to all the followed user of the current user
	 * 
	 * @param user_id
	 *            current user's id
	 * @param request
	 *            http request
	 * @param firstResult
	 *            start fetching data from this point
	 * @param music
	 *            music object
	 * @return music object
	 */
	@Override
	public Music getMusicByFollowingUser(int user_id, HttpServletRequest request, int firstResult, Music music) {
		// gets all the following user
		List<Follow> followingUsers = followDaoImpl.getFollowingByUser(user_id);

		List<Music> finalMusicList = new ArrayList<>();
		for (Follow following : followingUsers) {
			int firstResultCounter = 0;
			List<Music> musicList = new ArrayList<>();
			// get the following user from the following object
			User followingUser = following.getFollowingUser();
			// add all the music from the following user to new music list
			musicList.addAll(this.getMusicbyUser(followingUser.getId(), request, 0, false));
			// if music object from the client has music present
			if (music.getMusics().size() > 0) {
				// get the music from it
				for (Music musicFromApp : music.getMusics()) {
					// also get the music list from the db
					for (Music musicFromDB : musicList) {
						// if music id from app equals to the on of the db
						if (musicFromApp.getId() == musicFromDB.getId()) {
							// increment the first result so that it can get the
							// other remaining music
							firstResultCounter++;
						}
					}
				}
				// then add to the music list once the music is added using the
				// user's id
				finalMusicList.addAll(this.getMusicbyUser(followingUser.getId(), request, firstResultCounter, true));
			} else {
				// if application does not have loaded any music just get the
				// data from
				// the beginning
				finalMusicList.addAll(this.getMusicbyUser(followingUser.getId(), request, firstResultCounter, true));
			}

		}
		// sorts the music by date
		sortMusicListByDate(finalMusicList);
		Music musicObj = new Music();
		// sets the music list to music object
		musicObj.setMusics(finalMusicList);
		return musicObj;
	}

	/**
	 * Sorts the music list by the date, the latest date will on the top of the
	 * list
	 * 
	 * @param finalMusicList
	 *            music list
	 */
	private void sortMusicListByDate(List<Music> finalMusicList) {
		Collections.sort(finalMusicList, new Comparator<Music>() {
			public int compare(Music music1, Music music2) {
				return music1.getPublished_date().compareTo(music2.getPublished_date());
			}
		});
		Collections.reverse(finalMusicList);
	}

}
