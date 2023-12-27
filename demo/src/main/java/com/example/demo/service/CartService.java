package com.example.demo.service;

import java.util.Set;

import com.example.demo.dto.CartDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.exception.DemoException;

public interface CartService {
	String addMedicinesToCart(CartDto cart) throws DemoException;
	
	Set<ProductDto> getMedicinesFromCart(String email) throws DemoException;
	
	String modifyQuantityOfMedicinesInCart(String email, Integer productId , Integer quantity)throws DemoException;
	
	String deleteMedicineFromCart(String email, Integer productId) throws DemoException;
	
	String deleteAllMedicinesFromCart(String email) throws DemoException;

}
