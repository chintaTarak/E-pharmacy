package com.example.demo.repository;



import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;
import com.example.demo.exception.DemoException;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer>{
	Optional<Customer> findByCustomerEmailId(String EmailId) throws DemoException;
}
