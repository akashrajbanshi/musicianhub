package com.project.musicianhub.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.project.musicianhub.model.Like;
import com.project.musicianhub.service.LikeServiceImpl;

/**
 * Resource class for Like
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LikeResource {

	// LikeServiceImpl object
	LikeServiceImpl likeServiceImpl = new LikeServiceImpl();

	/**
	 * Gets the like list by music id
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param music_id
	 *            music's id
	 * @return user list
	 */
	@GET
	@Path("/allLikes")
	public List<Like> getAllLikes(@PathParam("music_id") int music_id) {
		return likeServiceImpl.getLikeByMusic(music_id);
	}

	/**
	 * Gets the like list by music id and user id
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param music_id
	 *            music's id
	 * @return user list
	 */
	@GET
	@Path("/allLikesByUser/{user_id}")
	public Like getAllLikesByUser(@PathParam("music_id") int music_id, @PathParam("user_id") int user_id) {
		return likeServiceImpl.getLikeByUser(music_id, user_id);
	}

	/**
	 * create the like for the music post
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param music_id
	 *            music's id
	 * @param like
	 *            like object
	 * @param uriInfo
	 *            URI information for current context
	 * @return jersey's response
	 */
	@POST
	@Path("/createLike")
	public com.project.musicianhub.model.Response addLike(@PathParam("music_id") int music_id, Like like,
			@Context UriInfo uriInfo) {
		like.getMusic().setId(music_id);
		return likeServiceImpl.addLike(like);

	}

	/**
	 * Update the like of the music post
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param id
	 *            music's id
	 * @param like
	 *            like object
	 * @return jersey's response
	 */
	@PUT
	@Path("/updateLike")
	public Response updateLike(@PathParam("music_id") int id, Like like) {

		int count = likeServiceImpl.updateLike(id, like);
		if (count == 0) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.ok().build();

	}

}
