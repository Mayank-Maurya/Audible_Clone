package com.audiocart.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.audiocart.dto.UserDTO;
import com.audiocart.exception.UserException;
import com.audiocart.security.JwtHelper;
import com.audiocart.security.JwtRequest;
import com.audiocart.security.JwtResponse;
import com.audiocart.services.JwtService;
import com.audiocart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/user")
@CrossOrigin
public class UserController {
	
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtHelper helper;
    
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/login")
    public ResponseEntity<JwtResponse>  login(@RequestBody JwtRequest request) {
		System.out.println(request.getUsername() + "  " + request.getPassword());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(new JwtResponse(jwtService.generateToken(request.getUsername()), request.getUsername()),HttpStatus.OK) ;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
	
	
	

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
	
	@GetMapping(value="/{id}")
	public ResponseEntity<UserDTO> getUserDetails(@PathVariable Integer id){
		try {
			UserDTO result = userService.getUserDetails(id);
			return new ResponseEntity<>(result,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/username/{username}")
	public ResponseEntity<UserDTO> getUserDetails1(@PathVariable("username") String username){
		try {
			System.out.println("fetching data by username");
			
			UserDTO result = userService.getUserByUserName(username);
			return new ResponseEntity<>(result,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@PostMapping(value="/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO){
		try {
			Integer UserId = userService.saveUser(userDTO);
			return new ResponseEntity<>("User Registered Successfully with UserId : "+UserId,HttpStatus.CREATED);
		}catch(com.audiocart.exception.UserException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping(value="/register/image/{userId}")
	public ResponseEntity<String> imageUpload(@RequestParam("image") MultipartFile image,@PathVariable Integer userId){
		try {
			
			UserDTO user = userService.getUserDetails(userId);
			String imgUrl = userService.uploadImage("profileImg/", image);
			user.setImgUrl(imgUrl);
			userService.updateUser(user);
			return new ResponseEntity<>("User Profile image Uploaded Successfully",HttpStatus.CREATED);
		}catch(UserException e) {
			return new ResponseEntity<>("Exception :" + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(IOException e) {
			return new ResponseEntity<>("Exception :" + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	

}
