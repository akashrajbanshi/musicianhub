package com.project.musicianhub.resources;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.MultiPart;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.Response;
import com.project.musicianhub.service.MusicServiceImpl;

/**
 * Resource class for Music
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 */
@Path("music")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusicResource {
	// MusicServiceImpl object
	MusicServiceImpl musicServiceImpl = new MusicServiceImpl();

	/**
	 * Gets the music list
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @return user list
	 */
	@GET
	public List<Music> getMusic() {
		return musicServiceImpl.getMusic();
	}

	/**
	 * Gets music information by music's id
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param music_id
	 *            music's id
	 * @return music object
	 */

	@GET
	@Path("/{music_id}")
	public Music getMusicById(@PathParam("music_id") int music_id, @Context HttpServletRequest request) {
		return musicServiceImpl.getMusicbyId(music_id, request);

	}

	/**
	 * Gets the music from the following user's id
	 * 
	 * @param user_id
	 * @param request
	 * @param firstResult
	 * @param music
	 * @return
	 */
	@POST
	@Path("followingMusic/{user_id}/{firstResult}")
	public Music getMusicByFollowingUser(@PathParam("user_id") int user_id, @Context HttpServletRequest request,
			@PathParam("firstResult") int firstResult, Music music) {
		return musicServiceImpl.getMusicByFollowingUser(user_id, request, firstResult, music);

	}

	/**
	 * Gets the user's music by user's id
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param user_id
	 *            user's id
	 * @param request
	 *            gets current request parameters such as server name and port
	 * @return music list
	 */

	@Path("/usermusic/{user_id}/{firstResult}")
	public List<Music> getMusicByUser(@PathParam("user_id") int user_id, @Context HttpServletRequest request,
			@PathParam("firstResult") int firstResult) {
		return musicServiceImpl.getMusicbyUser(user_id, request, firstResult, false);
	}

	/**
	 * Creates the music post
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param music
	 *            music's object
	 * @param uriInfo
	 *            URI info of the current context
	 * @return custom Response object
	 */
	@POST
	@Path("/create")
	public Response addMusic(Music music, @Context UriInfo uriInfo) {
		return musicServiceImpl.addMusic(music);
	}

	/**
	 * Uploads the album art of the music post
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param multiPart
	 *            music's upload information including image in multipart data
	 * @param context
	 *            current servlet context
	 * @return current image saved file path
	 */
	@POST
	@Path("/uploadUserImage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String uploadImageFile(MultiPart multiPart, @Context ServletContext context) {
		String imagePath = musicServiceImpl.uploadAlbumImage(multiPart, context);
		return imagePath;
	}

	/**
	 * Uploads the album art of the music post
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param multiPart
	 *            music's upload information including image in multipart data
	 * @param context
	 *            current servlet context
	 * @return current music saved file path
	 */
	@POST
	@Path("/uploadUserMusic")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String uploadMusicFile(MultiPart multiPart, @Context ServletContext context) {
		String musicPath = musicServiceImpl.uploadUserMusic(multiPart, context);
		return musicPath;
	}

	/**
	 * Updates the music information
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param music
	 *            music's object
	 * @return custom Response object
	 */
	@PUT
	@Path("/update")
	public com.project.musicianhub.model.Response updateMusic(Music music) {
		return musicServiceImpl.updateMusic(music);
	}

	/**
	 * Deletes the music information
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param id
	 *            music's id
	 * @return jersey's Response object
	 */
	@PUT
	@Path("/delete/{id}")
	public Response deleteMusic(@PathParam("id") int id) {
		return musicServiceImpl.deleteMusic(id);
	}

	/**
	 * Search the music
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param searchText
	 *            search for the music
	 * @param request
	 * @return
	 */
	@GET
	@Path("/search/{searchText}")
	public List<Music> searchMusic(@PathParam("searchText") String searchText, @Context HttpServletRequest request) {
		return musicServiceImpl.searchMusic(searchText, request);
	}

	/**
	 * Gets the comments resource
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @return CommentResource object
	 */
	@Path("{music_id}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}

	/**
	 * Gets the LikeResource resource
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @return LikeResource object
	 */
	@Path("{music_id}/likes")
	public LikeResource getLikeResource() {
		return new LikeResource();
	}

}
