package com.audiocart.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



@Builder
@Getter
@Setter
@AllArgsConstructor
public class JwtResponse implements Serializable {
	
	private static final long serialVersionUID = -8091879091924046844L;
	private  String jwttoken;
	private  String username;

	
}
