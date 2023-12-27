package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CardDto;
import com.example.demo.dto.PaymentDto;
import com.example.demo.entity.Card;
import com.example.demo.entity.Payment;
import com.example.demo.exception.DemoException;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.utility.HashingUtility;

import jakarta.transaction.Transactional;

@Service(value = "paymentService")
@Transactional
public class PaymentServiceImpl implements PaymentService{
	

	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private CardRepository cardRepository;

	@Override
	public Integer makePayment(String cardId, Double amountToPay) throws DemoException {
		Optional<Card> optional = cardRepository.findByCardId(cardId);
		Card card = optional.orElseThrow(()->new DemoException("CARD_NOT_FOUND"));
		Payment payment = new Payment();
		payment.setCustomerEmailId(card.getCustomerEmailId());
		payment.setAmount(amountToPay);
		payment.setPaymentTime(LocalDateTime.now());
		payment.setCardId(cardId);

		paymentRepository.save(payment);
		
		return payment.getPaymentId();
	}

	@Override
	public String addCard(CardDto cardDTO) throws Exception {
		Optional<Card> optional = cardRepository.findByCardId(cardDTO.getCardId());
		
		if(optional.isPresent())
			throw new DemoException("CARD_ALREADY_EXISTS");
			
		Card card = new Card();
		card.setCardId(cardDTO.getCardId());
		card.setCustomerEmailId(cardDTO.getCustomerEmailId());
		card.setCvv(HashingUtility.getHashValue(cardDTO.getCvv()));
		card.setExpiryDate(cardDTO.getExpiryDate());
		card.setNameOnCard(cardDTO.getNameOnCard());
		card.setCardType(cardDTO.getCardType());
		cardRepository.save(card);

		return "Card Added Successfully";
	}

	@Override
	public String deleteCard(String cardId) throws DemoException {
		// TODO Auto-generated method stub
		Optional<Card> optional = cardRepository.findByCardId(cardId);
		Card card = optional.orElseThrow(()->new DemoException("CARD_NOT_FOUND"));
		cardRepository.delete(card);
		return "Deleted Successfully";
	}

	@Override
	public List<CardDto> viewCards(String email) throws DemoException {
		Iterable<Card> cardList = cardRepository.findByCustomerEmailId(email);
		List<CardDto> cardDtoList = new ArrayList<>();
		for (Card card : cardList) {
			CardDto cardDto = new CardDto();
			cardDto.setCardId(card.getCardId());
			cardDto.setCustomerEmailId(card.getCustomerEmailId());
			cardDto.setCvv(card.getCvv());
			cardDto.setCardType(card.getCardType());
			cardDto.setExpiryDate(card.getExpiryDate());
			cardDto.setNameOnCard(card.getNameOnCard());
			cardDtoList.add(cardDto);
		}
		
		return cardDtoList;
	}

	@Override
	public PaymentDto getPaymentDetails(Integer paymentId) throws DemoException {
		// TODO Auto-generated method stub
		Optional<Payment> optional = paymentRepository.findById(paymentId);
		Payment payment = optional.orElseThrow(()->new DemoException("PAYMENT_NOT_FOUND"));
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setAmount(payment.getAmount());
		paymentDto.setCustomerEmailId(payment.getCustomerEmailId());
		paymentDto.setPaymentId(payment.getPaymentId());
		paymentDto.setPaymentTime(payment.getPaymentTime());
		paymentDto.setCard(getCardDetails(payment.getCardId()));
		
		return paymentDto;
	}

	@Override
	public CardDto getCardDetails(String cardId) throws DemoException {
		// TODO Auto-generated method stub
		Optional<Card> optional = cardRepository.findByCardId(cardId);
		Card card = optional.orElseThrow(()->new DemoException("CARD_NOT_FOUND"));
		CardDto cardDto = new CardDto();
		cardDto.setCardId(card.getCardId());
		cardDto.setCustomerEmailId(card.getCustomerEmailId());
		cardDto.setCvv(card.getCvv());
		cardDto.setExpiryDate(card.getExpiryDate());
		cardDto.setNameOnCard(card.getNameOnCard());
		cardDto.setCardType(card.getCardType());
		return cardDto;
	}

}
