package com.audiocart.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.audiocart.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("Select u from User u where u.userName=?1")
	public Optional<User> findUserByUserName(String userName);
	
}
