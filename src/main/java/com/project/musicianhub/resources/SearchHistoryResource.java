package com.project.musicianhub.resources;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.project.musicianhub.model.Music;
import com.project.musicianhub.model.SearchHistory;
import com.project.musicianhub.service.SearchHistoryServiceImpl;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchHistoryResource {

	SearchHistoryServiceImpl searchHistoryServiceImpl = new SearchHistoryServiceImpl();

	@GET
	@Path("/allSearchHistory")
	public List<Music> getSearchHistoryByUser(@PathParam("user_id") int user_id, @Context HttpServletRequest request) {
		return searchHistoryServiceImpl.getSearchHistoryByUser(user_id, request);
	}

	@POST
	@Path("/createSearchHistory")
	public Response addSearchHistory(@PathParam("user_id") int user_id, SearchHistory searchHistory,
			@Context UriInfo uriInfo) {
		searchHistory.getUser().setId(user_id);

		searchHistoryServiceImpl.addSearchHistory(searchHistory);

		String newId = String.valueOf(searchHistory.getId());
		URI absolutePath = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(absolutePath).build();
	}

	@DELETE
	@Path("/clearSearchHistory")
	public Response clearSearchHistory(@PathParam("user_id") int user_id, @Context UriInfo uriInfo) {
		searchHistoryServiceImpl.clearSearchHistory(user_id);
		String newId = String.valueOf(user_id);
		URI absolutePath = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(absolutePath).build();
	}

}
