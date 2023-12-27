package com.example.demo.dto;

import java.util.Set;


public class CartDto {
	
	private Integer cartId;
	private String customerEmailId;
	private Set<ProductDto> products;
	
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public String getCustomerEmailId() {
		return customerEmailId;
	}
	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	public Set<ProductDto> getProducts() {
		return products;
	}
	public void setProducts(Set<ProductDto> products) {
		this.products = products;
	}
	
	

}
