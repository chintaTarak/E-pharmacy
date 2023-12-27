package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

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

import com.example.demo.dto.CustomerAddressDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.PrimePlansDto;
import com.example.demo.exception.DemoException;
import com.example.demo.service.CustomerService;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping(value = "/authenticate/{emailId}/{password}")
	public ResponseEntity<String> authenticateCustomer(@PathVariable String emailId,@PathVariable String password) throws Exception{
		String str = customerService.authenticateCustomer(emailId, password);
		return new ResponseEntity<String>(str, HttpStatus.OK);
}

	@PostMapping(value = "/register")
	public ResponseEntity<String> registerNewCustomer( @RequestBody CustomerDto customerDTO) throws Exception{
		String str = customerService.registerNewCustomer(customerDTO);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}


	@GetMapping(value = "/getcustomer/{email}")
	public ResponseEntity<CustomerDto> viewCustomer(@PathVariable String email) throws DemoException{
		CustomerDto cusDto = customerService.viewCustomer(email);
		return new ResponseEntity<CustomerDto>(cusDto, HttpStatus.OK);
	}


	@PutMapping(value = "/upgrade")
	public ResponseEntity<LocalDate> upgradeCustomerToPrime( @RequestBody CustomerDto customerDTO) throws DemoException{
		LocalDate locDate = customerService.upgradeCustomerToPrime(customerDTO);
		return new ResponseEntity<LocalDate>(locDate, HttpStatus.OK);
	}


	@GetMapping(value = "/getplan/{email}")
	public ResponseEntity<PrimePlansDto>  getPlan(@PathVariable String email)throws DemoException{
		PrimePlansDto primePlanDto = customerService.getPlan(email);
		return new ResponseEntity<PrimePlansDto>(primePlanDto, HttpStatus.OK);
	}


	
	@DeleteMapping(value = "/delete/{addressId}")
	public ResponseEntity<String>  deleteAddress(@PathVariable Integer addressId) throws DemoException{
		String str = customerService.deleteAddress(addressId);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}


	@PutMapping(value = "/addaddress/{email}")
	public ResponseEntity<String>  addCustomerAddress(@RequestBody CustomerAddressDto caDTO,@PathVariable String email) throws DemoException{
		String str = customerService.addCustomerAddress(caDTO, email);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}


	@GetMapping(value = "/addresses/{email}")
	public ResponseEntity<List<CustomerAddressDto>> viewAllAddress(@PathVariable String email) throws DemoException{
		List<CustomerAddressDto> customerAddressDto = customerService.viewAllAddress(email);
		return new ResponseEntity<List<CustomerAddressDto>>(customerAddressDto, HttpStatus.OK);
	}

		
}
