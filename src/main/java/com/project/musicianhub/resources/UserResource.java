package com.project.musicianhub.resources;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import org.glassfish.jersey.media.multipart.MultiPart;

import com.project.musicianhub.model.User;
import com.project.musicianhub.service.UserServiceImpl;

/**
 * Resource class for User/Profile
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 */

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class UserResource {

	// generated serial id
	private static final long serialVersionUID = 1L;

	// UserServiceImpl object
	UserServiceImpl userServiceImpl = new UserServiceImpl();

	/**
	 * Gets the users list
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @return user list
	 */
	@GET
	public List<User> getUsers() {
		return userServiceImpl.getUsers();
	}

	/**
	 * Gets user information by user's id
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * 
	 * @param user_id
	 *            user's id
	 * @param uriInfo
	 *            current context uri information
	 * @return user's object
	 */
	@GET
	@Path("/{user_id}")
	public User getUserById(@PathParam("user_id") int user_id, @Context UriInfo uriInfo) {

		User user = userServiceImpl.getUserById(user_id);
		user.addLink(getUriForSelf(uriInfo, user), "self");
		user.addLink(getUriForFollowers(uriInfo, user), "follow");
		return user;
	}

	/**
	 * Validates the user's credentials
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * 
	 * @param user
	 *            user's object
	 * @param request
	 *            gets current request parameters such as server name and port
	 * @return custom response object
	 */

	@POST
	@Path("/login")
	public com.project.musicianhub.model.Response getUserCredentials(User user, @Context HttpServletRequest request) {
		return userServiceImpl.getUserCredentials(user, request);
	}

	/**
	 * Gets the URI for followers of the user
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param uriInfo
	 *            gets the URI information object
	 * @param user
	 *            user's object
	 * @return URI of the followers of the user
	 */

	private String getUriForFollowers(UriInfo uriInfo, User user) {
		String uri = uriInfo.getBaseUriBuilder().path(UserResource.class).path(UserResource.class, "getFollowResource")
				.path(FollowResource.class, "getFollowersByUserId").resolveTemplate("user_id", user.getId()).build()
				.toString();
		return uri;
	}

	/**
	 * Gets the URI for followers of the self
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param uriInfo
	 *            gets the URI information object
	 * @param user
	 *            user's object
	 * @return URI of the followers of the user
	 */
	private String getUriForSelf(UriInfo uriInfo, User user) {
		String uri = uriInfo.getBaseUriBuilder().path(UserResource.class).path(String.valueOf(user.getId())).build()
				.toString();
		return uri;
	}

	/**
	 * Creates the user
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param user
	 *            user's object
	 * @param uriInfo
	 *            URI info of the current context
	 * @return custom Response object
	 */
	@POST
	@Path("/create")
	public com.project.musicianhub.model.Response addUser(User user, @Context UriInfo uriInfo) {
		return userServiceImpl.addUser(user);
	}

	/**
	 * Updates the user information
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param user
	 *            user's object
	 * @param request
	 *            gets current request parameters such as server name and port
	 * @return custom Response object
	 */
	@PUT
	@Path("/update")
	public com.project.musicianhub.model.Response updateUser(User user, @Context HttpServletRequest request) {
		return userServiceImpl.updateUsers(user, request);
	}

	/**
	 * Deletes the user information
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param id
	 *            user's id
	 * @return jersey's Response object
	 */
	@DELETE
	@Path("/delete/{id}")
	public Response deleteUser(@PathParam("id") int id) {
		int count = userServiceImpl.deleteUsers(id);
		if (count == 0) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.ok().build();
	}

	/**
	 * Upload user's image
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @param multiPart
	 *            user's upload information including image in multipart data
	 * @param context
	 *            current servlet context
	 * @return current image saved file path
	 */
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String uploadFile(MultiPart multiPart, @Context ServletContext context) {
		String imagePath = userServiceImpl.uploadUserImage(multiPart, context);
		return imagePath;
	}

	/**
	 * Gets the follow resource for the user
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @return FollowResource object
	 */
	@Path("/{user_id}/follow")
	public FollowResource getFollowResource() {
		return new FollowResource();
	}

	/**
	 * Gets the search history resource
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @return SearchHistoryResource object
	 */
	@Path("/{user_id}/searchHistory")
	public SearchHistoryResource getSearchHistoryResource() {
		return new SearchHistoryResource();
	}

	/**
	 * Gets the notification user
	 * 
	 * @author Akash Rajbanshi
	 * @since 1.0
	 * @return NotificationResource object
	 * 
	 */
	@Path("{user_id}/notification")
	public NotificationResource getNoficationResource() {
		return new NotificationResource();
	}

}
