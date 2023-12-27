package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;


public class OrderDto {
	
	private Integer orderId;
	private Integer paymentId;
	private Double mrpTotal;
	private Double discountPercent;
	private Double discountedTotal;
	private LocalDateTime deliveryDate;
	private String customerEmailId;
	private OrderStatus orderStatus;
	private LocalDateTime orderDate;
	private String cancelReason;
	private List<OrderedMedicineDto> orderedMedicines;
	private CustomerAddressDto deliveryAddress;
	private CardDto card;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	public Double getMrpTotal() {
		return mrpTotal;
	}
	public void setMrpTotal(Double mrpTotal) {
		this.mrpTotal = mrpTotal;
	}
	public Double getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}
	public Double getDiscountedTotal() {
		return discountedTotal;
	}
	public void setDiscountedTotal(Double discountedTotal) {
		this.discountedTotal = discountedTotal;
	}
	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getCustomerEmailId() {
		return customerEmailId;
	}
	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public LocalDateTime getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public List<OrderedMedicineDto> getOrderedMedicines() {
		return orderedMedicines;
	}
	public void setOrderedMedicines(List<OrderedMedicineDto> orderedMedicines) {
		this.orderedMedicines = orderedMedicines;
	}
	public CustomerAddressDto getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(CustomerAddressDto deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public CardDto getCard() {
		return card;
	}
	public void setCard(CardDto card) {
		this.card = card;
	}
	
	
}
