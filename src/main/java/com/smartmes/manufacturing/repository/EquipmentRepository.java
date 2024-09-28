package com.smartmes.manufacturing.repository;

import com.smartmes.manufacturing.domain.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
