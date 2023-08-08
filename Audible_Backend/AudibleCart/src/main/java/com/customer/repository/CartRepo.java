package com.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.entity.Cart;


@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {
}
