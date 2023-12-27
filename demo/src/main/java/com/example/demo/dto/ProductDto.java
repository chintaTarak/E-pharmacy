package com.example.demo.dto;

public class ProductDto {
	
	private Integer productId;
	private MedicineDto medicineDto;
	private Integer quantity;
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public MedicineDto getMedicineDto() {
		return medicineDto;
	}
	public void setMedicineDto(MedicineDto medicineDto) {
		this.medicineDto = medicineDto;
	}
	
	
}
