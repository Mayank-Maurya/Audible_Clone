package com.payment.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.payment.dto.PaymentDTO;
import com.payment.entity.Payment;
import com.payment.entity.TransactionDetails;
import com.payment.repository.PaymentRepo;
import com.razorpay.Order;

@Service("paymentService")
@Transactional
public class PaymentService {
	
	 private static final String KEY = "rzp_test_I512oW1StjRK01";
	 private static final String KEY_SECRET = "w6KtHSJlRx07K81uzBFcmjR1";
	 private static final String CURRENCY = "INR";
	 
	 @Autowired
	 private PaymentRepo paymentRepo;
	 
	public PaymentDTO postTransactionDetails(PaymentDTO paymentDTO){
		
		LocalDateTime ldt=LocalDateTime.now();
		Payment payment=new Payment();
		payment.setAmount(paymentDTO.getAmount());
		payment.setCurrency(paymentDTO.getCurrency());
		payment.setOrderId(paymentDTO.getOrderId());
		payment.setTransactionId(paymentDTO.getTransactionId());
		payment.setTransactionTime(ldt);
		payment.setUserId(paymentDTO.getUserId());
		
		paymentRepo.save(payment);
		return paymentDTO;
		 
	 }
	 
	 
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
