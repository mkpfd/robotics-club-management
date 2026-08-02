package com.roboticsclub.service;

import com.roboticsclub.model.Equipment;
import com.roboticsclub.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    // Save Equipment
    public Equipment saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    // View All Equipment
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    // Find Equipment by ID
    public Optional<Equipment> getEquipmentById(Long id) {
        return equipmentRepository.findById(id);
    }

    // Update Equipment
    public Equipment updateEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    // Delete Equipment
    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }

}
