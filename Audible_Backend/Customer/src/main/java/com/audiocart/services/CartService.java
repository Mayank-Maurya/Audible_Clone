package com.audiocart.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.audiocart.dto.CartDTO;
import com.audiocart.entity.Cart;
import com.audiocart.entity.TransactionDetails;
import com.audiocart.repository.CartRepo;
import com.razorpay.RazorpayClient;
import com.razorpay.Order;


@Service("cartService")
@Transactional
public class CartService {


	    private static final String KEY = "rzp_test_I512oW1StjRK01";
	    private static final String KEY_SECRET = "w6KtHSJlRx07K81uzBFcmjR1";
	    private static final String CURRENCY = "INR";
	
	@Autowired
	private CartRepo cartrepo; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	public String addCart(CartDTO cartDTO) throws Exception {
		try {
		Cart a = modelMapper.map(cartDTO, Cart.class);
		cartrepo.save(a);
		String b="cart added successfully";
		return b;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	public CartDTO getCart(Integer cartId) {
		Optional<Cart> a = cartrepo.findById(cartId);
		return modelMapper.map(a.get(), CartDTO.class);
	}
	 
	public String deleteCart(Integer cartId) {
		cartrepo.deleteById(cartId);
		return "deleted successfully";
	}
	public String deleteAllCartItem() {
		cartrepo.deleteAll();
		return "All AudioBooks In The Cart Deleted";
	}
	public List<CartDTO> getAllCartsbyCustomerId(Integer cusId){
		List<Cart> a = cartrepo.findByCustomerId(cusId);
		List<CartDTO> b = a.stream().map(a1->modelMapper.map(a, CartDTO.class)).collect(Collectors.toList());
		return b;
	}
	
	
	public List<Integer> getAllAudioBookId(Integer custid){
		List<Cart> a = cartrepo.findByCustomerId(custid);
		List<Integer> b=new ArrayList<Integer>();
		for(Cart each:a) {
			b.add(each.getAudioBookId());
		}
		return b;
	}
	
	public CartDTO updateCartTransaction(Integer cartId,String transactionId) {
		Optional<Cart> a = cartrepo.findById(cartId);
		Cart cart=a.get();
		cart.setTransactionId(transactionId);
		cart.setType("p");
		cartrepo.save(cart);
		
		CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
		return cartDTO;
	}
	
	public List<CartDTO> getAllCartsByUser(Integer userId,String type) {
		List<Cart> a = cartrepo.findByCustomerIdAndType(userId, type);
		List<CartDTO> b = a.stream().map(a1->modelMapper.map(a1,CartDTO.class)).collect(Collectors.toList());
		return b;
	}
	
	
	
	
	
	//creation of payment page
	 public TransactionDetails createTransaction(Double amount) {
	        try {

	            JSONObject jsonObject = new JSONObject();
	            jsonObject.put("amount", (amount * 100) );
	            jsonObject.put("currency", CURRENCY);
	            

	            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);

	            Order order = razorpayClient.orders.create(jsonObject);

	            TransactionDetails transactionDetails =  prepareTransactionDetails(order);
	            return transactionDetails;
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        return null;
	    }
	 private TransactionDetails prepareTransactionDetails(Order order) {
	        String orderId = order.get("id");
	        String currency = order.get("currency");
	        Integer amount = order.get("amount");

	        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
	        return transactionDetails;
	    }
	 
	 
	
	
	

}
