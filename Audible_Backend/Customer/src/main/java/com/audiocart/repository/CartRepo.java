package com.audiocart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.audiocart.entity.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {

	public List<Cart> findByCustomerId(Integer custId);
	public List<Cart> findByCustomerIdAndType(Integer Id,String type);
	
}
