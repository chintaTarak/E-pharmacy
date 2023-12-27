package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MedicineDto;
import com.example.demo.exception.DemoException;
import com.example.demo.service.MedicineService;

@RestController
@RequestMapping(value = "/medicine")
public class MedicineController {
	
	@Autowired
	private MedicineService medicineService;
	
	@GetMapping(value = "/getall")
	public ResponseEntity<List<MedicineDto>> getAllMedicines() throws DemoException{
		List<MedicineDto> medicineDto = medicineService.getAllMedicines();
		return new ResponseEntity<List<MedicineDto>>(medicineDto, HttpStatus.OK);
	}

	@GetMapping(value = "/getbycategory/{category}")
	public ResponseEntity<List<MedicineDto>> getMedicinesByCategory(@PathVariable String category)throws DemoException{
		List<MedicineDto> medicineDto = medicineService.getMedicinesByCategory(category);
		return new ResponseEntity<List<MedicineDto>>(medicineDto, HttpStatus.OK);
	}

	@GetMapping(value = "/getbyid/{medicineId}")
	public ResponseEntity<MedicineDto> getMedicineById(@PathVariable Integer medicineId) throws DemoException{
		MedicineDto medicineDto = medicineService.getMedicineById(medicineId);
		return new ResponseEntity<MedicineDto>(medicineDto, HttpStatus.OK);
	}

	@PutMapping(value = "/updatequantity/{medicineId}/{orderedQuantity}" )
	public ResponseEntity<String> updateMedicineQuantityAfterOrder(@PathVariable Integer medicineId,@PathVariable Integer orderedQuantity) throws DemoException{
			String str = medicineService.updateMedicineQuantityAfterOrder(medicineId, orderedQuantity);
			return new ResponseEntity<String>(str, HttpStatus.OK);
	}

}
