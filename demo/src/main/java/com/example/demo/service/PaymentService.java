package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CardDto;
import com.example.demo.dto.PaymentDto;
import com.example.demo.exception.DemoException;

public interface PaymentService {
	
	public Integer makePayment(String cardId,Double amountToPay) throws DemoException;

	public String addCard(CardDto cardDTO) throws Exception;

	public String deleteCard(String cardId) throws DemoException;

	public List<CardDto> viewCards(String email) throws DemoException;

	PaymentDto getPaymentDetails(Integer paymentId) throws DemoException;

	CardDto getCardDetails(String cardId) throws DemoException;

}
