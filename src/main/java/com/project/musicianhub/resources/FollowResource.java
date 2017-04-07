package com.project.musicianhub.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.project.musicianhub.model.Follow;
import com.project.musicianhub.model.Response;
import com.project.musicianhub.service.FollowServiceImpl;

/**
 * Resource class for Follow
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowResource {

	FollowServiceImpl followServiceImpl = new FollowServiceImpl();

	/**
	 * Creates the followers for the user
	 * 
	 * @param user_id
	 *            user's id
	 * @param follow
	 *            follow object
	 * @param uriInfo
	 *            url information
	 * @return custom response
	 */
	@POST
	@Path("/createFollow")
	public Response addFollowing(@PathParam("user_id") int user_id, Follow follow, @Context UriInfo uriInfo) {
		Response response = new Response();
		follow.getFollowerUser().setId(user_id);
		followServiceImpl.addFollow(follow);
		response.setMessage("follow");
		return response;
	}

	/**
	 * Gets the follow object from follower and following user's id
	 * 
	 * @param user_id
	 *            user's id
	 * @param follow
	 *            follow object
	 * @return follow object
	 */
	@POST
	@Path("/followCheck")
	public Follow getFollowByFollowingAndFollowerId(@PathParam("user_id") int user_id, Follow follow) {
		follow.getFollowerUser().setId(user_id);
		return followServiceImpl.getFollowByFollowingAndFollowerId(follow);

	}

	/**
	 * Gets all the followers by user id
	 * 
	 * @param user_id
	 * @param request
	 *            http servlet request
	 * @return list of followers
	 */
	@GET
	@Path("/followers")
	public List<Follow> getFollowersByUserId(@PathParam("user_id") int user_id, @Context HttpServletRequest request) {
		return followServiceImpl.getFollowersByUser(user_id, request);
	}

	/**
	 * Gets all the followeing by user id
	 * 
	 * @param user_id
	 * @param request
	 *            http servlet request
	 * @return list of following users
	 */
	@GET
	@Path("/following")
	public List<Follow> getFollowingByUserId(@PathParam("user_id") int user_id, @Context HttpServletRequest request) {
		return followServiceImpl.getFollowingByUser(user_id, request);
	}

}
