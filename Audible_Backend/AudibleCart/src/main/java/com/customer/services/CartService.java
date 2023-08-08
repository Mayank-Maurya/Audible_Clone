package com.customer.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.dto.CartDTO;
import com.customer.entity.Cart;
import com.customer.repository.CartRepo;
import com.razorpay.RazorpayClient;
import com.razorpay.Order;


@Service("cartService")
@Transactional
public class CartService {

}
