package com.project.musicianhub.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.musicianhub.dao.UserDaoImpl;
import com.project.musicianhub.exception.DataNotFoundException;
import com.project.musicianhub.model.Response;
import com.project.musicianhub.model.User;

public class UserServiceImpl implements UserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserDaoImpl userDaoImpl = new UserDaoImpl();

	@Override
	public Response addUser(User user) {
		Response response = new Response();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashedPassword = encoder.encode(user.getPassword());
		String unencryptedPassword = user.getPassword();
		user.setPassword(hashedPassword);
		boolean istableEmpty = userDaoImpl.checkIfTableEmpty();

		if (istableEmpty != true) {
			User userCheckObj = userDaoImpl.getUserByUserName(user);
			if (userCheckObj != null) {
				response.setCode("reg_failed");
				response.setMessage("User with same username already exist. Please choose other username!");
			} else {
				saveUserWithResponse(user, response, unencryptedPassword);
			}
		} else {
			saveUserWithResponse(user, response, unencryptedPassword);
		}
		return response;

	}

	private void saveUserWithResponse(User user, Response response, String unencryptedPassword) {
		User userObj = userDaoImpl.addUser(user);
		if (userObj != null) {
			user.setPassword(unencryptedPassword);
			response.setUser(user);
			response.setCode("reg_success");
			response.setMessage("User Successfully Registered...");
		} else {
			response.setCode("reg_failed");
			response.setMessage("User Registration Failed...");
		}
	}

	@Override
	public List<User> getUsers() {
		return userDaoImpl.getUsers();
	}

	@Override
	public int deleteUsers(int id) {
		return userDaoImpl.deleteUsers(id);
	}

	@Override
	public Response updateUsers(User user, HttpServletRequest request) {
		String serverPath = request.getServerName().concat(":") + request.getServerPort();
		String imagePath = serverPath.concat(File.separator).concat(user.getUser_img_path());

		Response response = new Response();

		if (user.getUser_img_path().isEmpty()) {
			User userInfo = this.getUserById(user.getId());
			user.setUser_img_path(userInfo.getUser_img_path());
			response.setUserImagePath(imagePath + user.getUser_img_path());
		}

		User userCheckObj = userDaoImpl.getUserByUserName(user);
		if (userCheckObj != null && userCheckObj.getId() != user.getId()) {
			response.setCode("reg_failed");
			response.setMessage("User with same username already exist. Please choose other username!");
		} else {
			updateUserWithResponse(user, response, imagePath);
		}
		return response;
	}

	private void updateUserWithResponse(User user, Response response, String imagePath) {
		int recordAffected = userDaoImpl.updateUsers(user);
		if (recordAffected > 0) {
			if (response.getUserImagePath() == null || response.getUserImagePath().isEmpty())
				response.setUserImagePath(imagePath);
			response.setCode("update_success");
			response.setMessage("Profile Successfully Updated...");
		} else {
			response.setCode("reg_failed");
			response.setMessage("Profile Update Failed...");
		}
	}

	@Override
	public User getUserById(int user_id) {

		User user = userDaoImpl.getUserById(user_id);
		if (user == null) {
			throw new DataNotFoundException("Record with user id " + user_id + " not found!");
		}
		return user;
	}

	@Override
	public String uploadUserImage(MultiPart multiPart, ServletContext context) {
		String id = "";
		BodyPart from = multiPart.getBodyParts().get(2);
		BodyPartEntity frombpe = (BodyPartEntity) from.getEntity();
		BufferedReader reader = new BufferedReader(new InputStreamReader(frombpe.getInputStream()));
		String line = "";

		try {
			while ((line = reader.readLine()) != null) {
				if (line.equalsIgnoreCase("register")) {
					int lastUserId = userDaoImpl.getLastInsertedUserId();
					id = String.valueOf(lastUserId + 1);
				} else {
					BodyPart idBodyPart = multiPart.getBodyParts().get(3);
					BodyPartEntity idBodyPartEntity = (BodyPartEntity) idBodyPart.getEntity();

					BufferedReader idBufferedReader = new BufferedReader(
							new InputStreamReader(idBodyPartEntity.getInputStream()));
					String userId = "";
					userId = idBufferedReader.readLine();
					User user = this.getUserById(Integer.valueOf(userId));
					if (user.getUser_img_path() == null || user.getUser_img_path().trim().length() == 0
							|| user.getId() == Integer.valueOf(userId)) {
						id = userId;
					}

				}

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String realPath = context.getRealPath(File.separator);
		String absoultePath = context.getContextPath();

		String uploadPath = realPath.concat(File.separator).concat("uploads").concat(File.separator).concat("img");
		String savePath = absoultePath + File.separator.concat("uploads").concat(File.separator).concat("img");

		BodyPart file = multiPart.getBodyParts().get(0);
		BodyPartEntity bpe = (BodyPartEntity) file.getEntity();
		InputStream stream = bpe.getInputStream();

		String filename = file.getContentDisposition().getFileName();
		String seperateFileMimeType = filename.split("\\.")[1];
		String finalFileName = id + "." + seperateFileMimeType;

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
	public int getLastInsertedUserId() {
		return userDaoImpl.getLastInsertedUserId();
	}

	@Override
	public Response getUserCredentials(User user, HttpServletRequest request) {
		Response response = new Response();
		User userObj = userDaoImpl.getUserByUserName(user);

		if (userObj != null) {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			boolean isSuccess = encoder.matches(user.getPassword(), userObj.getPassword());

			if (isSuccess == true) {
				String serverPath = request.getServerName().concat(":") + request.getServerPort();
				String imagePath = serverPath.concat(File.separator).concat(userObj.getUser_img_path());
				userObj.setUser_img_path(imagePath);
				response.setUser(userObj);
				response.setCode("login_success");
				response.setMessage("Login Successfull...");
			} else {
				response.setCode("login_failed");
				response.setMessage("User credential not found...");
			}
		} else {
			response.setCode("login_failed");
			response.setMessage("User credential not found...");
		}

		return response;

	}

}
