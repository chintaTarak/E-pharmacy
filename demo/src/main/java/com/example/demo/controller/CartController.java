package com.example.demo.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CartDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.exception.DemoException;
import com.example.demo.service.CartService;

@RestController
@RequestMapping(value = "/cart")
public class CartController {
	
	@Autowired
	private CartService cartservice;
	
	@PostMapping(value ="/addcart")
	public ResponseEntity<String> addMedicinesToCart( @RequestBody CartDto customerCartDTO)throws DemoException {
		String str = cartservice.addMedicinesToCart(customerCartDTO);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	@GetMapping(value = "/getproducts/{email}")
	public ResponseEntity<Set<ProductDto>> getMedicinesFromCart(@PathVariable String email)throws DemoException {
		Set<ProductDto> productDto = cartservice.getMedicinesFromCart(email);
		return new ResponseEntity<Set<ProductDto>>(productDto, HttpStatus.OK);
	}

	@PutMapping(value = "/modify/{email}/{productId}/{quantity}")
	public ResponseEntity<String> modifyQuantityOfMedicineInCart(@PathVariable String email,@PathVariable Integer productId , @PathVariable Integer quantity) throws DemoException {
		String str = cartservice.modifyQuantityOfMedicinesInCart(email, productId, quantity);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	
	@DeleteMapping(value = "/delete/{email}/{productId}")
	public ResponseEntity<String> deleteMedicineFromCart(@PathVariable String email,@PathVariable Integer productId) throws DemoException {
		String str = cartservice.deleteMedicineFromCart(email, productId);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}


	@DeleteMapping(value = "/deleteall/{email}")
	public ResponseEntity<String> deleteAllMedicinesFromCart(@PathVariable String email)throws DemoException {
		String str = cartservice.deleteAllMedicinesFromCart(email);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

}
