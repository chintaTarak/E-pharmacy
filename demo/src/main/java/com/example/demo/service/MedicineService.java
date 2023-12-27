package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.MedicineDto;
import com.example.demo.exception.DemoException;

public interface MedicineService {
	
	List<MedicineDto> getAllMedicines() throws DemoException;
	
	List<MedicineDto> getMedicinesByCategory(String category)throws DemoException;

	MedicineDto getMedicineById(Integer medicineId) throws DemoException;

	String updateMedicineQuantityAfterOrder(Integer medicineId, Integer orderedQuantity) throws DemoException;

}
