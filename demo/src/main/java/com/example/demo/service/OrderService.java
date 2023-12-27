package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.OrderDto;
import com.example.demo.exception.DemoException;

public interface OrderService {
	
	List<OrderDto> viewOrders(String email) throws DemoException;
	
	public String placeOrder(OrderDto orderDTO) throws DemoException;
	
	public String cancelOrder(Integer orderId,String reason) throws DemoException;

	
}
