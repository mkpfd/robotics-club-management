package com.roboticsclub.service;

import com.roboticsclub.model.Equipment;
import com.roboticsclub.model.EquipmentRequest;
import com.roboticsclub.repository.EquipmentRepository;
import com.roboticsclub.repository.EquipmentRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final EquipmentRequestRepository requestRepo;
    private final EquipmentRepository equipmentRepo;

    public RequestService(EquipmentRequestRepository requestRepo, EquipmentRepository equipmentRepo) {
        this.requestRepo = requestRepo;
        this.equipmentRepo = equipmentRepo;
    }

    // ----- READ -----

    public List<EquipmentRequest> getAllRequests() {
        return requestRepo.findAll();
    }

    public long getPendingRequestCount() {
        return requestRepo.countByStatus("PENDING");
    }

    public Optional<EquipmentRequest> getRequestById(Long id) {
        return requestRepo.findById(id);
    }

    // ----- STEP 1: Student submits a request -----

    public EquipmentRequest createRequest(EquipmentRequest request) {
        request.setStatus("PENDING");
        request.setRequestDate(LocalDate.now());
        return requestRepo.save(request);
    }

    // ----- STEP 2: Admin approves (checks stock, but does NOT deduct yet) -----

    public EquipmentRequest approveRequest(Long requestId) {
        EquipmentRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        Equipment equipment = equipmentRepo.findById(request.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        if (!"PENDING".equals(request.getStatus())) {
            throw new IllegalStateException("Only PENDING requests can be approved");
        }

        if (equipment.getAvailableQuantity() < request.getQuantity()) {
            throw new IllegalStateException("Not enough available quantity to approve this request");
        }

        request.setStatus("APPROVED");
        return requestRepo.save(request);
    }

    // ----- Admin rejects -----

    public EquipmentRequest rejectRequest(Long requestId) {
        EquipmentRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!"PENDING".equals(request.getStatus())) {
            throw new IllegalStateException("Only PENDING requests can be rejected");
        }

        request.setStatus("REJECTED");
        return requestRepo.save(request);
    }

    // ----- STEP 3: Hand over equipment (THIS is where inventory actually decreases) -----

    public EquipmentRequest handOverEquipment(Long requestId) {
        EquipmentRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!"APPROVED".equals(request.getStatus())) {
            throw new IllegalStateException("Only APPROVED requests can be handed over");
        }

        Equipment equipment = equipmentRepo.findById(request.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        if (equipment.getAvailableQuantity() < request.getQuantity()) {
            throw new IllegalStateException("Not enough available quantity to hand over");
        }

        // Deduct stock
        equipment.setAvailableQuantity(equipment.getAvailableQuantity() - request.getQuantity());
        equipmentRepo.save(equipment);

        request.setStatus("HANDED_OVER");
        request.setHandoverDate(LocalDate.now());
        return requestRepo.save(request);
    }

    // ----- STEP 4: Return equipment (inventory increases back) -----

    public EquipmentRequest returnEquipment(Long requestId) {
        EquipmentRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!"HANDED_OVER".equals(request.getStatus())) {
            throw new IllegalStateException("Only HANDED_OVER requests can be returned");
        }

        Equipment equipment = equipmentRepo.findById(request.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        // Add stock back
        equipment.setAvailableQuantity(equipment.getAvailableQuantity() + request.getQuantity());
        equipmentRepo.save(equipment);

        request.setStatus("RETURNED");
        request.setReturnDate(LocalDate.now());
        return requestRepo.save(request);
    }
}