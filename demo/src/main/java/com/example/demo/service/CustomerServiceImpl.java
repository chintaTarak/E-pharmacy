package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomerAddressDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.PrimePlansDto;
import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerAddress;
import com.example.demo.entity.PrimePlans;
import com.example.demo.exception.DemoException;
import com.example.demo.repository.CustomerAddressRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.PrimePlansRepository;

import jakarta.transaction.Transactional;

@Service(value = "customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRespository;
	@Autowired
	private CustomerAddressRepository customerAddressRepository;
	@Autowired
	private PrimePlansRepository primePlansRepository;

	@Override
	public String authenticateCustomer(String emailId, String password) throws Exception {
		Optional<Customer> optional = customerRespository.findByCustomerEmailId(emailId);
		Customer customer = optional.orElseThrow(()->new DemoException("EMAIL_NOT_FOUND"));
		if(!customer.getPassword().equals(password)) {
			
			throw new DemoException("INCORRECT_PASSWORD");
		}
		return "Valid User";
	}

	@Override
	public String registerNewCustomer(CustomerDto customerDTO) throws Exception {
		Customer customer = new Customer();
		customer.setContactNumber(customerDTO.getContactNumber());
		customer.setCustomerEmailId(customerDTO.getCustomerEmailId());
		customer.setCustomerId(customerDTO.getCustomerId());
		customer.setCustomerName(customerDTO.getCustomerName());
		customer.setDateOfBirth(customerDTO.getDateOfBirth());
		customer.setGender(customerDTO.getGender());
		customer.setPassword(customerDTO.getPassword());
		
		customerRespository.save(customer);
		
		return "Registered Successfully";
	}

	@Override
	public CustomerDto viewCustomer(String email) throws DemoException {
		Optional<Customer> optional = customerRespository.findByCustomerEmailId(email);
		Customer customer = optional.orElseThrow(()->new DemoException("EMAIL_NOT_FOUND"));
		CustomerDto customerDto = new CustomerDto();
		List<CustomerAddress> customerAddressList = customer.getAddressList();
		List<CustomerAddressDto> addressDtos = new ArrayList<>();
		if(customer.getAddressList().isEmpty()) {
			customerDto.setAddressList(Collections.emptyList());
		}
		else {
			for (CustomerAddress customerAddress : customerAddressList) {
				CustomerAddressDto customerAddressDto = new CustomerAddressDto();
				customerAddressDto.setAddressId(customerAddress.getAddressId());
				customerAddressDto.setAddressLine1(customerAddress.getAddressLine1());
				customerAddressDto.setAddressLine2(customerAddress.getAddressLine2());
				customerAddressDto.setAddressName(customerAddress.getAddressName());
				customerAddressDto.setArea(customerAddress.getArea());
				customerAddressDto.setCity(customerAddress.getCity());
				customerAddressDto.setPincode(customerAddress.getPincode());
				customerAddressDto.setState(customerAddress.getState());
				addressDtos.add(customerAddressDto);
			}
			customerDto.setAddressList(addressDtos);
		}
		if(!(customer.getPlan()==null)) {
			PrimePlans primePlans = customer.getPlan();
			PrimePlansDto primePlansDto = new PrimePlansDto();
			primePlansDto.setPlanDescription(primePlans.getPlanDescription());
			primePlansDto.setPlanId(primePlans.getPlanId());
			primePlansDto.setPlanName(primePlans.getPlanName());
			
			customerDto.setPlan(primePlansDto);
		}
		customerDto.setContactNumber(customer.getContactNumber());
		customerDto.setCustomerEmailId(customer.getCustomerEmailId());
		customerDto.setCustomerId(customer.getCustomerId());
		customerDto.setCustomerName(customer.getCustomerName());
		customerDto.setDateOfBirth(customer.getDateOfBirth());
		customerDto.setGender(customer.getGender());
		customerDto.setPassword(customer.getPassword());
		customerDto.setPlanExpiryDate(customer.getPlanExpiryDate());
			
		return customerDto;
	}

	@Override
	public LocalDate upgradeCustomerToPrime(CustomerDto customerDTO) throws DemoException {
		Optional<PrimePlans> optional = primePlansRepository.findByPlanName(customerDTO.getPlan().getPlanName());
		PrimePlans primePlans = optional.orElseThrow(()->new DemoException("PLAN_DOESNT_EXISTS"));
		Optional<Customer> optional2 = customerRespository.findByCustomerEmailId(customerDTO.getCustomerEmailId());
		Customer customer = optional2.orElseThrow(()->new DemoException("USER_DOESNT_EXISTS"));
		customer.setPlan(primePlans);
		if(primePlans.getPlanName().equals("YEARLY"))
			customer.setPlanExpiryDate(LocalDate.now().plusYears(1));
		else if(primePlans.getPlanName().equals("QUARTERLY"))
			customer.setPlanExpiryDate(LocalDate.now().plusMonths(3));
		else if(primePlans.getPlanName().equals("MONTHLY"))
			customer.setPlanExpiryDate(LocalDate.now().plusMonths(1));
		return customer.getPlanExpiryDate();
	}

	@Override
	public PrimePlansDto getPlan(String email) throws DemoException {
		Optional<Customer> optional = customerRespository.findByCustomerEmailId(email);
		Customer customer = optional.orElseThrow(()->new DemoException("EMAIL_NOT_FOUND"));
		PrimePlans primePlans = new PrimePlans();
		primePlans = customer.getPlan();
		if(primePlans==null)
			throw new DemoException("NO_PLAN_EXISTS");
		PrimePlansDto primePlansDto = new PrimePlansDto();
		primePlansDto.setPlanDescription(primePlans.getPlanDescription());
		primePlansDto.setPlanId(primePlans.getPlanId());
		primePlansDto.setPlanName(primePlans.getPlanName());
		return primePlansDto;
	}

	@Override
	public String deleteAddress(Integer addressId) throws DemoException {
		
		Optional<CustomerAddress> optional = customerAddressRepository.findById(addressId);
		CustomerAddress customerAddress = optional.orElseThrow(()-> new DemoException("ADDRESS_NOT_FOUND"));
		customerAddressRepository.delete(customerAddress);
		
		return "Address Deleted Successfully";
	}

	@Override
	public String addCustomerAddress(CustomerAddressDto caDTO, String email) throws DemoException {
		
		Optional<Customer> optional = customerRespository.findByCustomerEmailId(email);
		Customer customer = optional.orElseThrow(()->new DemoException("EMAIL_NOT_FOUND"));
		
		CustomerAddress customerAddress = new CustomerAddress();
		customerAddress.setAddressId(caDTO.getAddressId());
		customerAddress.setAddressLine1(caDTO.getAddressLine1());
		customerAddress.setAddressLine2(caDTO.getAddressLine2());
		customerAddress.setAddressName(caDTO.getAddressName());
		customerAddress.setArea(caDTO.getArea());
		customerAddress.setCity(caDTO.getCity());
		customerAddress.setPincode(caDTO.getPincode());
		customerAddress.setState(caDTO.getState());
		customer.getAddressList().add(customerAddress);
		
		return "ADDRESS ADDED SUCCESSFULLY";
	}

	@Override
	public List<CustomerAddressDto> viewAllAddress(String email) throws DemoException {
		Optional<Customer> optional = customerRespository.findByCustomerEmailId(email);
		Customer customer = optional.orElseThrow(()->new DemoException("USER_DOESNT_EXISTS"));
		List<CustomerAddress> customerAddressList = customer.getAddressList();
		List<CustomerAddressDto> addressDtos = new ArrayList<>();
		
		for (CustomerAddress customerAddress : customerAddressList) {
			CustomerAddressDto customerAddressDto = new CustomerAddressDto();
			customerAddressDto.setAddressId(customerAddress.getAddressId());
			customerAddressDto.setAddressLine1(customerAddress.getAddressLine1());
			customerAddressDto.setAddressLine2(customerAddress.getAddressLine2());
			customerAddressDto.setAddressName(customerAddress.getAddressName());
			customerAddressDto.setArea(customerAddress.getArea());
			customerAddressDto.setCity(customerAddress.getCity());
			customerAddressDto.setPincode(customerAddress.getPincode());
			customerAddressDto.setState(customerAddress.getState());
			
			addressDtos.add(customerAddressDto);
		}
		if(addressDtos.isEmpty())
			return Collections.emptyList();
		return addressDtos;
	}

	@Override
	public CustomerAddressDto getAddress(Integer deliveryId) throws DemoException {
		Optional<CustomerAddress> optional = customerAddressRepository.findById(deliveryId);
		CustomerAddress customerAddress = optional.orElseThrow(()->new DemoException("DELIVERY_ID_NOT_FOUND"));
		CustomerAddressDto customerAddressDto = new CustomerAddressDto();
		customerAddressDto.setAddressId(customerAddress.getAddressId());
		customerAddressDto.setAddressLine1(customerAddress.getAddressLine1());
		customerAddressDto.setAddressLine2(customerAddress.getAddressLine2());
		customerAddressDto.setAddressName(customerAddress.getAddressName());
		customerAddressDto.setArea(customerAddress.getArea());
		customerAddressDto.setCity(customerAddress.getCity());
		customerAddressDto.setPincode(customerAddress.getPincode());
		customerAddressDto.setState(customerAddress.getState());
		
		return customerAddressDto;
	}
	
	
	
}
