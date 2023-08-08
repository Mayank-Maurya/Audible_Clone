package com.customer.services;



import java.io.IOException;


import org.springframework.web.multipart.MultipartFile;


import com.customer.dto.UserDTO;
import com.customer.exception.UserException;


public interface UserService {

	UserDTO getUserDetails(Integer userId) throws UserException;
	
	UserDTO getUserByUserName(String userName) throws UserException;
	
	Integer saveUser(UserDTO userDTO) throws UserException;
	
	String uploadImage(String path,MultipartFile file) throws IOException;
	
	void updateUser(UserDTO userDTO);
	
}
