package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payment.dto.PaymentDTO;
import com.payment.entity.TransactionDetails;
import com.payment.service.PaymentService;

@RestController
@CrossOrigin
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/payment")
	public  ResponseEntity<PaymentDTO> postTransactionDetails(@RequestBody PaymentDTO paymentDTO){
		
		PaymentDTO payment = paymentService.postTransactionDetails(paymentDTO);
		
		return new ResponseEntity<PaymentDTO>(payment,HttpStatus.OK);
	}
	
	
	@GetMapping({"/createTransaction/{amount}"})
	public TransactionDetails createTransaction(@PathVariable(name = "amount") Double amount) {
		   return paymentService.createTransaction(amount);
		    }
}
