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

/**
 * Service implementation for the User
 * 
 * @author Akash Rajbanshi
 * @since 1.0
 *
 */
public class UserServiceImpl implements UserService {

	UserDaoImpl userDaoImpl = new UserDaoImpl();

	/**
	 * Create user
	 * 
	 * @param user
	 *            user object
	 * @return custom response
	 */
	@Override
	public Response addUser(User user) {
		Response response = new Response();
		// gets the encoder class from spring security
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// hash the password
		String hashedPassword = encoder.encode(user.getPassword());
		// unencrypted password
		String unencryptedPassword = user.getPassword();

		// check if table is empty
		boolean istableEmpty = userDaoImpl.checkIfTableEmpty();

		// if table is not empty
		if (istableEmpty != true) {
			// get user by user name
			User userCheckObj = userDaoImpl.getUserByUserName(user);
			// check if the user is present
			if (userCheckObj != null) {
				// if user is present send the response
				response.setCode("reg_failed");
				response.setMessage("User with same username already exist. Please choose other username!");
			} else {
				// if the user is not present save the user
				saveUserWithResponse(user, response, unencryptedPassword);
			}
		} else {
			// just save the user if the table is empty
			saveUserWithResponse(user, response, unencryptedPassword);
		}
		return response;

	}

	/**
	 * Saves the user with desired response to the client
	 * 
	 * @param user
	 *            user object
	 * @param response
	 *            http servlet request
	 * @param unencryptedPassword
	 *            plain string password
	 */
	private void saveUserWithResponse(User user, Response response, String unencryptedPassword) {
		User userObj = userDaoImpl.addUser(user);
		// if user is saved successfully
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

	/**
	 * Gets all the users data
	 * 
	 * @return user list
	 */
	@Override
	public List<User> getUsers() {
		return userDaoImpl.getUsers();
	}

	/**
	 * Delete all the users
	 * 
	 * @param id
	 *            user id
	 * @return delete success/failure as int
	 */
	@Override
	public int deleteUsers(int id) {
		return userDaoImpl.deleteUsers(id);
	}

	/**
	 * Updates the user information
	 * 
	 * @param user
	 *            user object
	 * @param request
	 *            http servlet request
	 * @return custom response
	 */
	@Override
	public Response updateUsers(User user, HttpServletRequest request) {
		// get the server path
		String serverPath = request.getServerName().concat(":") + request.getServerPort();
		// set the final image path
		String imagePath = serverPath.concat(File.separator).concat(user.getUser_img_path());

		Response response = new Response();
		// if the user image path is empty
		if (user.getUser_img_path().isEmpty()) {
			// get user by user id
			User userInfo = this.getUserById(user.getId());
			// set user image path
			user.setUser_img_path(userInfo.getUser_img_path());
			response.setUserImagePath(imagePath + user.getUser_img_path());
		}

		// get user by user name
		User userCheckObj = userDaoImpl.getUserByUserName(user);
		// if update user and user by user name's id is not equal
		if (userCheckObj != null && userCheckObj.getId() != user.getId()) {
			// send registration failed
			response.setCode("update_failed");
			response.setMessage("Update cannot be done! Try again later");
		} else {
			// update the user
			updateUserWithResponse(user, response, imagePath);
		}
		return response;
	}

	/**
	 * Updates the user with a corresponding response
	 * 
	 * @param user
	 *            user objet
	 * @param response
	 *            http servlet request
	 * @param imagePath
	 *            user image path for upload
	 */
	private void updateUserWithResponse(User user, Response response, String imagePath) {
		// updates the user
		int recordAffected = userDaoImpl.updateUsers(user);
		// if update is successful
		if (recordAffected > 0) {
			// set user image if it is empty
			if (response.getUserImagePath() == null || response.getUserImagePath().isEmpty())
				response.setUserImagePath(imagePath);

			// set the success response
			response.setCode("update_success");
			response.setMessage("Profile Successfully Updated...");
		} else {
			// set the failure response
			response.setCode("reg_failed");
			response.setMessage("Profile Update Failed...");
		}
	}

	/**
	 * Get user object from user id
	 * 
	 * @param user_id
	 *            user id
	 * @return user object
	 */
	@Override
	public User getUserById(int user_id) {
		// gets the user object by user id
		User user = userDaoImpl.getUserById(user_id);
		if (user == null) {
			// throws data not found error
			throw new DataNotFoundException("Record with user id " + user_id + " not found!");
		}
		return user;
	}

	/**
	 * Uploads the user image
	 * 
	 * @param multiPart
	 *            image part
	 * @param context
	 *            servlet context
	 * @return user image path
	 */
	@Override
	public String uploadUserImage(MultiPart multiPart, ServletContext context) {
		String id = "";
		// gets the information on from where the method has been called
		BodyPart from = multiPart.getBodyParts().get(2);
		BodyPartEntity frombpe = (BodyPartEntity) from.getEntity();
		BufferedReader reader = new BufferedReader(new InputStreamReader(frombpe.getInputStream()));
		String line = "";

		try {
			while ((line = reader.readLine()) != null) {
				// if it is from the register
				if (line.equalsIgnoreCase("register")) {
					// gets the last inserted user id and increments it
					int lastUserId = userDaoImpl.getLastInsertedUserId();
					id = String.valueOf(lastUserId + 1);
				} else {
					// gets the user id from the bodypart
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

		// real path where user image is uploaded
		String realPath = context.getRealPath(File.separator);
		// absolute path for saving database image path
		String absoultePath = context.getContextPath();

		String uploadPath = realPath.concat(File.separator).concat("uploads").concat(File.separator).concat("img");
		String savePath = absoultePath + File.separator.concat("uploads").concat(File.separator).concat("img");

		// gets the file from the multipart
		BodyPart file = multiPart.getBodyParts().get(0);
		BodyPartEntity bpe = (BodyPartEntity) file.getEntity();
		InputStream stream = bpe.getInputStream();

		// renaming the file
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

		// saves the file
		saveToFile(stream, target);
		return finalPath;
	}

	/**
	 * Saves the file to the real path
	 * 
	 * @param inStream
	 *            input stream
	 * @param target
	 *            file upload path
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
	 * Creates the directory
	 * 
	 * @param dirName
	 *            directory name
	 */
	private void createFolderIfNotExists(String dirName) {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdirs();
		}
	}

	/**
	 * Gets the last inserted user id
	 * 
	 * @return user id
	 */
	@Override
	public int getLastInsertedUserId() {
		return userDaoImpl.getLastInsertedUserId();
	}

	/**
	 * Gets the user credentials
	 * 
	 * @param user
	 *            user object
	 * @param request
	 *            http servlet request
	 * @return custom response
	 */
	@Override
	public Response getUserCredentials(User user, HttpServletRequest request) {
		Response response = new Response();
		// gets the user from user name
		User userObj = userDaoImpl.getUserByUserName(user);
		// if user exists
		if (userObj != null) {
			// encodes the password and check if the password matches
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			boolean isSuccess = encoder.matches(user.getPassword(), userObj.getPassword());
			// if password matches
			if (isSuccess == true) {
				// gets server path and sets the image path
				String serverPath = request.getServerName().concat(":") + request.getServerPort();
				String imagePath = serverPath.concat(File.separator).concat(userObj.getUser_img_path());
				userObj.setUser_img_path(imagePath);

				// set the success response
				response.setUser(userObj);
				response.setCode("login_success");
				response.setMessage("Login Successfull...");
			} else {
				// sets the failure response
				response.setCode("login_failed");
				response.setMessage("User credential not found...");
			}
		} else {
			// if user does not exist
			response.setCode("login_failed");
			response.setMessage("User credential not found...");
		}

		return response;

	}

}
