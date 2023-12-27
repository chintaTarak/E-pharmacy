package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CardDto;
import com.example.demo.dto.CustomerAddressDto;
import com.example.demo.dto.MedicineDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.OrderStatus;
import com.example.demo.dto.OrderedMedicineDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Orders;
import com.example.demo.entity.OrderedMedicine;
import com.example.demo.exception.DemoException;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;

@Service(value = "orderService")
@Transactional
public class OrderServiceImpl implements OrderService{

	@Autowired
	private MedicineService medicineService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CartService cartService;
	@Autowired
	private PaymentService paymentService;
	
	
	
	
	@Override
	public List<OrderDto> viewOrders(String email) throws DemoException {
		// TODO Auto-generated method stub
		Iterable<Orders> optional = orderRepository.findByCustomerEmailId(email);
		List<OrderDto> list = new ArrayList<>();
		for (Orders order : optional) {
			OrderDto orderDTO = new OrderDto();
			orderDTO.setOrderId(order.getOrderId());
			orderDTO.setPaymentId(order.getPaymentId());
			orderDTO.setMrpTotal(order.getMrpTotal());
			orderDTO.setDiscountPercent(order.getDiscountPercent());
			orderDTO.setDiscountedTotal(order.getDiscountedTotal());
			orderDTO.setDeliveryDate(order.getDeliveryDate());;
			orderDTO.setCustomerEmailId(order.getCustomerEmailId());
			orderDTO.setOrderStatus(order.getOrderStatus());;
			orderDTO.setOrderDate(order.getOrderDate());
			orderDTO.setCancelReason(order.getCancelReason());
			//set all details get address
			CustomerAddressDto customerAddressDTO = customerService.getAddress(order.getDeliveryAddressId());
			orderDTO.setDeliveryAddress(customerAddressDTO);
			//get card details
			CardDto cardDTO = paymentService.getCardDetails(order.getCardId());
			orderDTO.setCard(cardDTO);
			//convert the list
			List<OrderedMedicine> orderedMedicines = order.getOrderedMedicines();
			List<OrderedMedicineDto> medicineDTOs = new ArrayList<>();
			for (OrderedMedicine orderedMedicine : orderedMedicines) {
				OrderedMedicineDto orderedMedicineDTO = new OrderedMedicineDto();
				orderedMedicineDTO.setOrderedQuantity(orderedMedicine.getOrderedQuantity());
				orderedMedicineDTO.setOrderedMedicineId(orderedMedicine.getOrderedMedicineId());
				//fetch the individual medicine details
				MedicineDto medicineDTO =medicineService.getMedicineById(orderedMedicine.getMedicineId());
				orderedMedicineDTO.setMedicine(medicineDTO);
				medicineDTOs.add(orderedMedicineDTO);
			}
			orderDTO.setOrderedMedicines(medicineDTOs);
			list.add(orderDTO);
		}
		return list;
	}

	@Override
	public String placeOrder(OrderDto orderDTO) throws DemoException {
		// TODO Auto-generated method stub
		
		//have an order entity
		Orders order = new Orders();
		order.setCustomerEmailId(orderDTO.getCustomerEmailId());
		order.setOrderDate(LocalDateTime.now());
		order.setOrderStatus(OrderStatus.CONFIRMED);
		order.setDeliveryDate(LocalDateTime.now().plusDays(15));
		// customeraddress dto is passed
		order.setDeliveryAddressId(orderDTO.getDeliveryAddress().getAddressId());
		//card dto is passed
		order.setCardId(orderDTO.getCard().getCardId()); 
		//find out plan from customer module
		String planName = customerService.viewCustomer(orderDTO.getCustomerEmailId()).
				getPlan().getPlanName();
		
		if(planName.equals("YEARLY")) order.setDiscountPercent(15.0);
		else if(planName.equals("QUARTERLY")) order.setDiscountPercent(10.0);
		else if(planName.equals("MONTHLY")) order.setDiscountPercent(5.0);
		else order.setDiscountPercent(0.0);
		double price =0.0;
		//set the discount percentage
		//get the products from cart - cartservice
		Set<ProductDto> products = cartService.getMedicinesFromCart(orderDTO.getCustomerEmailId());
		
		//convert products to orderedmedicinedto
		
		List<OrderedMedicineDto> dtos = new ArrayList<>();
		for (ProductDto dto : products) {
			OrderedMedicineDto orDto = new OrderedMedicineDto();
			orDto.setMedicine(dto.getMedicineDto());
			orDto.setOrderedQuantity(dto.getQuantity());
			dtos.add(orDto);
		}
	
		//create list of entities
		List<OrderedMedicine> medicines = new ArrayList<>();
		
		for (OrderedMedicineDto orderedMedicine : dtos) 
		{
			//check for the stock
			if(orderedMedicine.getMedicine().getQuantity()<orderedMedicine.getOrderedQuantity())
			{
				throw new DemoException("STOCK_NOT_AVAILABLE");
			}
			//create an object
			OrderedMedicine orderedMedicine2 = new OrderedMedicine();
			//medicine dto is available
			orderedMedicine2.setMedicineId(orderedMedicine.getMedicine().getMedicineId());
			orderedMedicine2.setOrderedQuantity(orderedMedicine.getOrderedQuantity());
			//modify the quantity of purchased product
			medicineService.updateMedicineQuantityAfterOrder(orderedMedicine.getMedicine().getMedicineId(),
					orderedMedicine.getOrderedQuantity());
			medicines.add(orderedMedicine2);
			//update the price
			price = price + orderedMedicine.getOrderedQuantity()*orderedMedicine.getMedicine().getPrice();
		}
		order.setOrderedMedicines(medicines);
		//remove the entire cart
		cartService.deleteAllMedicinesFromCart(orderDTO.getCustomerEmailId());
	
		order.setMrpTotal(price);
		
		order.setDiscountedTotal(price*(100 - order.getDiscountPercent())/100);
		

		
		//make payment
		Integer payment = paymentService.makePayment(orderDTO.getCard().getCardId(),order.getDiscountedTotal());
	
		order.setPaymentId(payment);
		
		//save it to repo
		orderRepository.save(order);
	
	
		return "order is placed with id: "+order.getOrderId();
	}

	@Override
	public String cancelOrder(Integer orderId, String reason) throws DemoException {
		// TODO Auto-generated method stub
		Optional<Orders> optional = orderRepository.findById(orderId);
		Orders order = optional.orElseThrow(()->new DemoException("ORDER_ID_DOESNT_EXISTS"));
		order.setOrderStatus(OrderStatus.CANCELLED);
		order.setCancelReason(reason);
			return "Order is cancelled";
	}

}
