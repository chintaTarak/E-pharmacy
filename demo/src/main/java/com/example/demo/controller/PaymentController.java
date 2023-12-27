package com.example.demo.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CardDto;
import com.example.demo.dto.PaymentDto;
import com.example.demo.exception.DemoException;
import com.example.demo.service.PaymentService;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping(value = "/addcard")
	public ResponseEntity<String> addCard(@RequestBody CardDto cardDTO) throws Exception{
		String str = paymentService.addCard(cardDTO);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{cardId}")
	public ResponseEntity<String> deleteCard(@PathVariable String cardId) throws DemoException{
		String str = paymentService.deleteCard(cardId);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}


	@GetMapping(value = "/getallcards/{email}")
	public ResponseEntity<List<CardDto>> viewCards(@PathVariable String email) throws DemoException{
		List<CardDto> cardDto = paymentService.viewCards(email);
		return new ResponseEntity<List<CardDto>>(cardDto, HttpStatus.OK);
	}


	@GetMapping(value = "/getpayment/{paymentId}")
	public ResponseEntity<PaymentDto> getPaymentDetails(@PathVariable Integer paymentId) throws DemoException {
		PaymentDto paymentDto = paymentService.getPaymentDetails(paymentId);
		return new ResponseEntity<PaymentDto>(paymentDto, HttpStatus.OK);
	}


	@GetMapping(value = "/getcard/{cardId}")
	public ResponseEntity<CardDto> getCardDetails(@PathVariable String cardId) throws DemoException{
		CardDto cardDto = paymentService.getCardDetails(cardId);
		return new ResponseEntity<CardDto>(cardDto, HttpStatus.OK);
	}

	@PostMapping(value = "/makepayment/{cardId}/{amountToPay}")
	public ResponseEntity<Integer> makePayment( @PathVariable String cardId,@PathVariable Double amountToPay)
	throws DemoException, NoSuchAlgorithmException{
		Integer i = paymentService.makePayment(cardId, amountToPay);
		return new ResponseEntity<Integer>(i, HttpStatus.OK);
	}

}
