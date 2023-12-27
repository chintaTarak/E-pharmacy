package com.example.demo.dto;

public class OrderedMedicineDto {
	
	private Integer orderedMedicineId;
	private Integer orderedQuantity;
	private MedicineDto medicine;
	
	public Integer getOrderedMedicineId() {
		return orderedMedicineId;
	}
	public void setOrderedMedicineId(Integer orderedMedicineId) {
		this.orderedMedicineId = orderedMedicineId;
	}
	public Integer getOrderedQuantity() {
		return orderedQuantity;
	}
	public void setOrderedQuantity(Integer orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}
	public MedicineDto getMedicine() {
		return medicine;
	}
	public void setMedicine(MedicineDto medicine) {
		this.medicine = medicine;
	}
	
}
