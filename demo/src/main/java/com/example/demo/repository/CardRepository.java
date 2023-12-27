package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Card;
import com.example.demo.exception.DemoException;

@Repository
public interface CardRepository extends CrudRepository<Card, String>{
	
	Iterable<Card> findByCustomerEmailId(String EmailId) throws DemoException;
	
	
	Optional<Card> findByCardId(String cardId) throws DemoException;
}
