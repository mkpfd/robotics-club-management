package com.roboticsclub.repository;

import com.roboticsclub.model.EquipmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipmentRequestRepository extends JpaRepository<EquipmentRequest, Long> {
    List<EquipmentRequest> findByMemberId(Long memberId);
    List<EquipmentRequest> findByStatus(String status);
}