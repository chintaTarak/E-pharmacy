package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MedicineDto;
import com.example.demo.entity.Medicine;
import com.example.demo.exception.DemoException;
import com.example.demo.repository.MedicineRepository;

import jakarta.transaction.Transactional;

@Service(value = "medicineService")
@Transactional
public class MedicineServiceImpl implements MedicineService{
	
	@Autowired
	private MedicineRepository medicineRepository;

	@Override
	public List<MedicineDto> getAllMedicines() throws DemoException {
		Iterable<Medicine> allMedIterable = medicineRepository.findAll();
		List<MedicineDto> medicineDtoIter = new ArrayList<>();
		for (Medicine medicine : allMedIterable) {
			MedicineDto medicineDto = new MedicineDto();
			medicineDto.setCategory(medicine.getCategory());
			medicineDto.setDiscountPercent(medicine.getDiscountPercent());
			medicineDto.setExpiryDate(medicine.getExpiryDate());
			medicineDto.setManufacturer(medicine.getManufacturer());
			medicineDto.setManufacturingDate(medicine.getManufacturingDate());
			medicineDto.setMedicineId(medicine.getMedicineId());
			medicineDto.setMedicineName(medicine.getMedicineName());
			medicineDto.setPrice(medicine.getPrice());
			medicineDto.setQuantity(medicine.getQuantity());
			medicineDtoIter.add(medicineDto);
		}
		if(medicineDtoIter.isEmpty()) return Collections.emptyList();
		return medicineDtoIter;
	}

	@Override
	public List<MedicineDto> getMedicinesByCategory(String category) throws DemoException {
		Iterable<Medicine> medCatIterable = medicineRepository.findByCategory(category);
		List<MedicineDto> medicineDtoIter = new ArrayList<>();
		for (Medicine medicine : medCatIterable) {
			MedicineDto medicineDto = new MedicineDto();
			medicineDto.setCategory(medicine.getCategory());
			medicineDto.setDiscountPercent(medicine.getDiscountPercent());
			medicineDto.setExpiryDate(medicine.getExpiryDate());
			medicineDto.setManufacturer(medicine.getManufacturer());
			medicineDto.setManufacturingDate(medicine.getManufacturingDate());
			medicineDto.setMedicineId(medicine.getMedicineId());
			medicineDto.setMedicineName(medicine.getMedicineName());
			medicineDto.setPrice(medicine.getPrice());
			medicineDto.setQuantity(medicine.getQuantity());			
			medicineDtoIter.add(medicineDto);
		}
		if(medicineDtoIter.isEmpty()) return Collections.emptyList();
		return medicineDtoIter;
	}

	@Override
	public MedicineDto getMedicineById(Integer medicineId) throws DemoException {
		Optional<Medicine> medIdOptional = medicineRepository.findById(medicineId);
		Medicine medicine = medIdOptional.orElseThrow(()->new DemoException("MEDICINE_DOESNT_EXISTS"));
		MedicineDto medicineDto = new MedicineDto();
		medicineDto.setCategory(medicine.getCategory());
		medicineDto.setDiscountPercent(medicine.getDiscountPercent());
		medicineDto.setExpiryDate(medicine.getExpiryDate());
		medicineDto.setManufacturer(medicine.getManufacturer());
		medicineDto.setManufacturingDate(medicine.getManufacturingDate());
		medicineDto.setMedicineId(medicine.getMedicineId());
		medicineDto.setMedicineName(medicine.getMedicineName());
		medicineDto.setPrice(medicine.getPrice());
		medicineDto.setQuantity(medicine.getQuantity());
		return medicineDto;
	}

	@Override
	public String updateMedicineQuantityAfterOrder(Integer medicineId, Integer orderedQuantity) throws DemoException {
		Optional<Medicine> strOptional = medicineRepository.findById(medicineId);
		Medicine medicine = strOptional.orElseThrow(()-> new DemoException("MEDICINE_DOESNT_EXISTS"));
		medicine.setQuantity(medicine.getQuantity()-orderedQuantity);
		return "Quantity updated successfully";
	}

}
