package com.audiocart.dto;

import com.audiocart.entity.User;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {

	Integer userId;
	String fullName;
	String email;
	String userName;
	String password;
	Integer cartId;
	String imgUrl;
	String roles;
	

	public User toEntity() {
		User user = new User();
		user.setUserId(this.userId);
		user.setEmail(this.email);
		user.setFullName(this.fullName);
		user.setPassword(this.password);
		user.setUserName(this.userName);
		user.setCartId(this.cartId);
		user.setImgUrl(this.imgUrl);
		user.setRoles(this.roles);
		return user;
	}
	
	
	
}
