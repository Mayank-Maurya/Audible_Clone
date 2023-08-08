package com.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customer.dto.CartDTO;
import com.customer.services.CartService;

@RestController
@CrossOrigin
public class CartController {

	@Autowired
	private CartService cartService;

//	@PostMapping("/cart")
//	public ResponseEntity<String> addCart(@RequestBody CartDTO cartDTO) {
//		try {
//			String a = cartService.addCart(cartDTO);
//			return new ResponseEntity<String>(a, HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//	}
//
//	@GetMapping("/cart1/{custId}")
//	public ResponseEntity<List<Integer>> getallaudiobookId(@PathVariable Integer custId) {
//		List<Integer> a = cartService.getAllAudioBookId(custId);
//		return new ResponseEntity<List<Integer>>(a, HttpStatus.OK);
//
//	}
//	@GetMapping("cart/{cartId}/{type}")
//	public ResponseEntity<List<CartDTO>> getAllCartsByUser(@PathVariable Integer cartId,@PathVariable String type){
//		List<CartDTO> a = cartService.getAllCartsByUser(cartId,type);
//		return new ResponseEntity<List<CartDTO>>(a,HttpStatus.OK);
//	}
//
//	@GetMapping("/cart/{cartId}")
//	public ResponseEntity<CartDTO> getCart(@PathVariable Integer cartId) {
//		CartDTO a = cartService.getCart(cartId);
//		return new ResponseEntity<CartDTO>(a, HttpStatus.OK);
//	}
//
//	@DeleteMapping("/cart/{cartId}")
//	public ResponseEntity<String> deleteCart(@PathVariable Integer cartId) {
//		String a = cartService.deleteCart(cartId);
//		return new ResponseEntity<String>(a, HttpStatus.OK);
//	}
//	@PutMapping("/cart/{cartId}/{transactionId}")
//	public ResponseEntity<CartDTO> updateCart(@PathVariable Integer cartId,@PathVariable String transactionId){
//		CartDTO a = cartService.updateCartTransaction(cartId, transactionId);
//		return new ResponseEntity<CartDTO>(a,HttpStatus.ACCEPTED);
//	}
//
//
//	@GetMapping({"/createTransaction/{amount}"})
//	public TransactionDetails createTransaction(@PathVariable(name = "amount") Double amount) {
//		   return cartService.createTransaction(amount);
//		    }
//
//
//
//
//	@DeleteMapping("/cart")
//	public ResponseEntity<String> deleteAllCart() {
//		String a = cartService.deleteAllCartItem();
//		return new ResponseEntity<String>(a, HttpStatus.OK);
//	}

	
}
