package com.project.musicianhub.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.MultiPart;

import com.project.musicianhub.model.Response;
import com.project.musicianhub.model.User;

public interface UserService {

	public Response addUser(User user);

	public List<User> getUsers();

	public int deleteUsers(int id);

	public Response updateUsers(User user, HttpServletRequest request);

	public User getUserById(int user_id);

	public String uploadUserImage(MultiPart multiPart, ServletContext context);

	public int getLastInsertedUserId();

	public Response getUserCredentials(User user, HttpServletRequest request);
}
