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

import com.project.musicianhub.model.Comment;
import com.project.musicianhub.service.CommentServiceImpl;

/**
 * Resource class for Comment
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {
	// CommentServiceImpl object
	CommentServiceImpl commentServiceImpl = new CommentServiceImpl();

	/**
	 * Gets all the comment for the music by music id
	 * 
	 * @param music_id
	 *            music's id
	 * @param request
	 *            gets current request parameters such as server name and port
	 * @return comment's list
	 */
	@GET
	@Path("/allComments")
	public List<Comment> getAllComments(@PathParam("music_id") int music_id, @Context HttpServletRequest request) {
		return commentServiceImpl.getCommentByMusic(music_id, request);
	}

	/**
	 * Create the comment for the music post
	 * 
	 * @param music_id
	 *            music's id
	 * @param comment
	 *            comment object
	 * @param uriInfo
	 *            URI information for current context
	 * @return comment object
	 */
	@POST
	@Path("/createComment")
	public Comment addComment(@PathParam("music_id") int music_id, Comment comment, @Context UriInfo uriInfo) {
		comment.getMusic().setId(music_id);
		return commentServiceImpl.addComment(comment);
	}
}
