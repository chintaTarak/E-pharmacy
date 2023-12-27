package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.dto.CustomerAddressDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.PrimePlansDto;
import com.example.demo.exception.DemoException;

public interface CustomerService {
	
	String authenticateCustomer(String emailId, String password) throws Exception;

	String registerNewCustomer(CustomerDto customerDTO) throws Exception;

	CustomerDto viewCustomer(String email) throws DemoException;

	LocalDate upgradeCustomerToPrime(CustomerDto customerDTO) throws DemoException;
	
	PrimePlansDto getPlan(String email)throws DemoException;
	
	String deleteAddress(Integer addressId) throws DemoException;
	
	String addCustomerAddress(CustomerAddressDto caDTO, String email) throws DemoException;
	
	List<CustomerAddressDto> viewAllAddress(String email) throws DemoException;
	
	CustomerAddressDto getAddress(Integer deliveryId) throws DemoException;

}
