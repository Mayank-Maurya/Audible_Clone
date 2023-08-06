package com.audiocart.services;

import java.io.File;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.audiocart.dto.UserDTO;
import com.audiocart.entity.User;
import com.audiocart.exception.UserException;
import com.audiocart.repository.UserRepository;


@Service("UserService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDTO getUserDetails(Integer userId) throws UserException {
		
		if(userId==null) {
			throw new UserException("user id is null");
		}
		
		Optional<User> user = userRepo.findById(userId);
		
		if(user.isEmpty()) {
			throw new UserException("Can't Find Any User by UserId : "+userId);
		}
		
		return user.get().toDTO();
		
	}

	
	@Override
	public UserDTO getUserByUserName(String userName) throws UserException {
		
		Optional<User> user = userRepo.findUserByUserName(userName);
		if(user.isEmpty()) {
			throw new UserException("User Not Found by User Name: "+userName);
		}
		
		return user.get().toDTO();
	}
	
	
	@Override
	public Integer saveUser(UserDTO userDTO) throws UserException {
		
		Optional<User> user = userRepo.findUserByUserName(userDTO.getUserName());
		
		if(user.isEmpty()) {
			User savedUser = userDTO.toEntity();
			savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));
			return userRepo.save(savedUser).getUserId();
		}else {
			throw new UserException("User Already Exists");
		}
		
	}

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException{
		
		String name = file.getOriginalFilename();
		String randomID = UUID.randomUUID().toString();
		
		String fileName = randomID.concat(name.substring(name.lastIndexOf('.')));
		String filepath = path + File.separator + fileName;
		
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		
		Files.copy(file.getInputStream(), Paths.get(filepath));
		
		return name;
		
		
	}


	@Override
	public void updateUser(UserDTO userDTO){
		userRepo.save(userDTO.toEntity());
	}





}
